package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.fechamento.dominio.Fechamento;
import com.cepalab.sistemaVendas.fechamento.dominio.FormaPagamentoValor;
import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.Receitas;
import com.cepalab.sistemaVendas.security.Seguranca;

@ViewScoped
@Named
public class FechamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Fechamento item;
	private BigDecimal totalReceita = BigDecimal.ZERO;
	private BigDecimal comissaoTotal = BigDecimal.ZERO;
	private BigDecimal comissoesVendas = BigDecimal.ZERO;
	private BigDecimal faturamentoPremiacao = BigDecimal.ZERO;
	private BigDecimal repasse = BigDecimal.ZERO;
	private BigDecimal premiacao = BigDecimal.ZERO;
	private List<Operacao> listaOperacoes = new ArrayList<>();
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Funcionario> listaFun = new ArrayList<>();
	private List<FormaPagamentoValor> formaPagValor = new ArrayList<>();

	@Inject
	private Funcionarios fun;

	@Inject
	private Operacoes operacoes;

	@Inject
	private FechamentoAberturaBean aberturaBean;

	@Inject
	private Receitas receitas;

	@Inject
	private FechamentoCustosBean fechamentoCustoBean;

	@Inject
	private FechamentoDespesasBean fechamentoDespesaBean;

	@Inject
	private FechamentoRecolhidaRessarcidaBean recolhidaRessarcidaBean;

	@Inject
	private FechamentoDescontoSalarioBean descontoSalarioBean;

	@Inject
	private FechamentoVendasBean fechamentoVendasBean;

	@Inject
	private FechamentoRecebimentoInadimplenteBean fechamentoRecebimentoBean;

	@Inject
	private FechamentoAcertoProdutoBean fechamentoAcertoProdutoBean;

	@Inject
	private Seguranca seg;

	@PostConstruct
	public void inicio() {
		listaFun = fun.funcionarios();
		retiraTiposFuncionários();
		item = new Fechamento();

		if (!isAdministrador()) {
			item.setFuncionario(seg.UsuarioLogado());
		}

		limpa();
	}

	public void limpa() {
		totalReceita = new BigDecimal("0");
		comissaoTotal = new BigDecimal("0");
		comissoesVendas = new BigDecimal("0");
		faturamentoPremiacao = new BigDecimal("0");
		repasse = new BigDecimal("0");
		premiacao = new BigDecimal("0");
		listaOperacoes = new ArrayList<>();
	}

	public void criaListaOperacoes() {
		limpa();

		listaOperacoes = operacoes.resumo(item.getFuncionario(), item.getInicio(), item.getFim());
		formaPagValor = receitas.resumoReceitas(item.getFuncionario(), item.getInicio(), item.getFim());
		aberturaBean.criaListaGeral();
		fechamentoCustoBean.criaListaCustos();
		fechamentoDespesaBean.criaListaDespesas();
		recolhidaRessarcidaBean.criaListaRecolhidaRessarcida();
		descontoSalarioBean.criaListaDescontos();
		fechamentoVendasBean.criaListaVendas();
		fechamentoRecebimentoBean.criaListaRecebimentos();
		fechamentoAcertoProdutoBean.inicio();

		totalReceitaComissao();
		calculaComissaoTotal();
		calculaRepasse();

	}

	public void calculaRepasse() {
		repasse = BigDecimal.ZERO;
		for (FormaPagamentoValor f : formaPagValor) {
			if (f.getForma().equals(FormaPagamento.DINHEIRO)) {
				repasse = repasse.add(f.getValor());
			}
		}
		if(premiacao == null) { premiacao = BigDecimal.ZERO;}
		
		repasse = repasse.subtract(comissaoTotal).subtract(recolhidaRessarcidaBean.getRessarcida())
				.subtract(fechamentoCustoBean.getCustoTotal()).subtract(premiacao)
				.add(fechamentoDespesaBean.getDespesaTotal()).add(recolhidaRessarcidaBean.getRecolhida())
				.add(descontoSalarioBean.getDesconto()).add(fechamentoRecebimentoBean.getTotal())
				.add(fechamentoAcertoProdutoBean.getDescontoTotal());

	}

	private void calculaComissaoTotal() {
		comissaoTotal = comissaoTotal.add(comissoesVendas).add(aberturaBean.getComissaoAberturas())
				.add(aberturaBean.getComissaoColocacao());
	}

	public void totalReceitaComissao() {

		if (listaOperacoes != null) {
			for (Operacao o : listaOperacoes) {
				totalReceita = totalReceita.add(o.ReceitaTotal());
				comissoesVendas = comissoesVendas.add(o.comissaoTotal());
			
			}
			faturamentoPremiacao = faturamentoPremiacao.add(totalReceita);
		}
	}

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}
	
	
	//Melhorar isso em versões posteriores
		private void retiraTiposFuncionários() {
			listaFuncionarios = new ArrayList<>();
			for(Funcionario f : listaFun) {
				if(!f.getTipoVendedor().getNome().equals("Interno-0")) {
					listaFuncionarios.add(f);
				}
			}
		}
	
	

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public Fechamento getItem() {
		return item;
	}

	public void setItem(Fechamento item) {
		this.item = item;
	}

	public BigDecimal getTotalReceita() {
		return totalReceita;
	}

	public void setTotalReceita(BigDecimal totalReceita) {
		this.totalReceita = totalReceita;
	}

	public BigDecimal getComissoesVendas() {
		return comissoesVendas;
	}

	public void setComissoesVendas(BigDecimal comissoesVendas) {
		this.comissoesVendas = comissoesVendas;
	}

	public List<FormaPagamentoValor> getFormaPagValor() {
		return formaPagValor;
	}

	public void setFormaPagValor(List<FormaPagamentoValor> formaPagValor) {
		this.formaPagValor = formaPagValor;
	}

	public BigDecimal getComissaoTotal() {
		return comissaoTotal;
	}

	public void setComissaoTotal(BigDecimal comissaoTotal) {
		this.comissaoTotal = comissaoTotal;
	}

	public BigDecimal getFaturamentoPremiacao() {
		return faturamentoPremiacao;
	}

	public void setFaturamentoPremiacao(BigDecimal faturamentoPremiacao) {
		this.faturamentoPremiacao = faturamentoPremiacao;
	}

	public BigDecimal getRepasse() {
		return repasse;
	}

	public void setRepasse(BigDecimal repasse) {
		this.repasse = repasse;
	}

	public BigDecimal getPremiacao() {
		return premiacao;
	}

	public void setPremiacao(BigDecimal premiacao) {
		this.premiacao = premiacao;
	}

}
