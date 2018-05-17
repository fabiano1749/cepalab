package com.cepalab.sistemaVendas.fechamento.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.RecolhidaRessarcida;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;
import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;
import com.cepalab.sistemaVendas.operacao.dominio.ResumoConsignacaoVenda;
import com.cepalab.sistemaVendas.repository.ComissoesRecolhidasRessarcidas;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.DescontosSalarios;
import com.cepalab.sistemaVendas.repository.DespesasVendedores;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.RecebimentosInadiplentes;

public class Fechamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario = new Funcionario();
	private Date inicio;
	private Date fim;

	private List<Operacao> listaOperacoes = new ArrayList<>();
	private List<FormaPagamentoValor> listaReceitaFormaPagamento;
	private List<ResumoConsignacaoVenda> listaResumoConsignacaoVenda = new ArrayList<>();
	private List<AberturaProduto> listaAberturas = new ArrayList<>();
	private List<AberturaProduto> listaColocacao = new ArrayList<>();
	private List<CustoViagem> listaCustos = new ArrayList<>();
	private List<DespesaVendedor> listaDespesas = new ArrayList<>();
	private List<ComissaoRecolhidaRessarcida> listaRecolhidaRessarcida = new ArrayList<>();
	private List<DescontoSalario> listaDescontosSalarios = new ArrayList<>();
	private List<RecebimentoInadiplente> listaRecebimentoInadimplente = new ArrayList<>();

	private BigDecimal faturamento = BigDecimal.ZERO;
	private BigDecimal comissoesTotais = BigDecimal.ZERO;
	private BigDecimal comissaoVendas = BigDecimal.ZERO;
	private BigDecimal comissaoAberturas = BigDecimal.ZERO;
	private BigDecimal comissaoColocacao = BigDecimal.ZERO;

	private BigDecimal premiacaoAberturas = BigDecimal.ZERO;
	private BigDecimal premiacaoColocacao = BigDecimal.ZERO;
	private BigDecimal faturamentoPremiacao = BigDecimal.ZERO;

	private BigDecimal premiacao = BigDecimal.ZERO;
	private BigDecimal repasse = BigDecimal.ZERO;

	private BigDecimal custosTotais = BigDecimal.ZERO;
	private BigDecimal despesasTotais = BigDecimal.ZERO;
	private BigDecimal comissoesRecolhidas = BigDecimal.ZERO;
	private BigDecimal comissoesRessarcidas = BigDecimal.ZERO;
	private BigDecimal salarioDescontado = BigDecimal.ZERO;
	private BigDecimal recebimentoInadimplente = BigDecimal.ZERO;
	private BigDecimal diferencaProdutos = BigDecimal.ZERO;
	private String observacao;

	private int aberturas3p;
	private int aberturas2p;
	private int aberturas1p;

	public void limpa() {
		this.listaOperacoes = new ArrayList<>();
		this.listaReceitaFormaPagamento = new ArrayList<>();
		this.listaResumoConsignacaoVenda = new ArrayList<>();
		this.listaAberturas = new ArrayList<>();
		this.listaColocacao = new ArrayList<>();
		this.listaCustos = new ArrayList<>();
		this.listaDespesas = new ArrayList<>();
		this.listaRecolhidaRessarcida = new ArrayList<>();
		this.listaDescontosSalarios = new ArrayList<>();
		this.listaRecebimentoInadimplente = new ArrayList<>();

		faturamento = BigDecimal.ZERO;
		comissoesTotais = BigDecimal.ZERO;
		comissaoVendas = BigDecimal.ZERO;
		comissaoAberturas = BigDecimal.ZERO;
		comissaoColocacao = BigDecimal.ZERO;

		premiacaoAberturas = BigDecimal.ZERO;
		premiacaoColocacao = BigDecimal.ZERO;
		faturamentoPremiacao = BigDecimal.ZERO;

		premiacao = BigDecimal.ZERO;
		repasse = BigDecimal.ZERO;

		custosTotais = BigDecimal.ZERO;
		despesasTotais = BigDecimal.ZERO;
		comissoesRecolhidas = BigDecimal.ZERO;
		comissoesRessarcidas = BigDecimal.ZERO;
		salarioDescontado = BigDecimal.ZERO;
		recebimentoInadimplente = BigDecimal.ZERO;
		diferencaProdutos = BigDecimal.ZERO;
		observacao = new String();

		aberturas3p = 0;
		aberturas2p = 0;
		aberturas1p = 0;
	}

	public void start(Operacoes operacoes, CustosViagens custos, DespesasVendedores despesas,
			ComissoesRecolhidasRessarcidas recolhidaRessarcida, DescontosSalarios descontos,
			RecebimentosInadiplentes recebimentoInadimplente, List<TipoProduto> tiposProdutos) {
		limpa();

		this.listaOperacoes = operacoes.resumo(funcionario, inicio, fim);
		criaListaReceitasFormaPagamento();
		this.listaCustos = custos.porFuncionario(funcionario, inicio, fim);
		this.listaDespesas = despesas.porFuncionario(funcionario, inicio, fim);
		this.listaRecolhidaRessarcida = recolhidaRessarcida.porFuncionario(funcionario, inicio, fim);
		this.listaDescontosSalarios = descontos.porFuncionario(funcionario, inicio, fim);
		this.listaRecebimentoInadimplente = recebimentoInadimplente.porFuncionario(funcionario, inicio, fim);
		calculaResumo(tiposProdutos);
	}

	public void criaListaReceitasFormaPagamento() {
		for (FormaPagamento f : FormaPagamento.values()) {
			FormaPagamentoValor r = new FormaPagamentoValor(f, BigDecimal.ZERO);
			listaReceitaFormaPagamento.add(r);
		}

	}

	public void calculaResumo(List<TipoProduto> tiposProdutos) {
		if (listaOperacoes != null && listaOperacoes.size() != 0) {
			for (Operacao o : listaOperacoes) {
				o.valoresFechamentoVendedor(this, tiposProdutos);
			}
		}
		calculaValoresFechamento();
		// calculaRepasse();
	}

	public void calculaValoresFechamento() {

		// Cálculo das comissões totais
		comissoesTotais = comissoesTotais.add(comissaoVendas).add(comissaoAberturas).add(comissaoColocacao);

		// Cálculo do faturamento premiação
		faturamentoPremiacao = faturamentoPremiacao.add(faturamento).add(premiacaoAberturas).add(premiacaoColocacao);

		// Implementar calculo do repasse

		// Cálculo dos custos totais
		if (listaCustos != null && listaCustos.size() != 0) {
			for (CustoViagem c : listaCustos) {
				custosTotais = custosTotais.add(c.getValor());
			}
		}

		// Cálculo das despesas totais
		if (listaDespesas != null && listaDespesas.size() != 0) {
			for (DespesaVendedor d : listaDespesas) {
				despesasTotais = despesasTotais.add(d.getValor());
			}
		}

		// Cálculo das comissões recolhidas e ressarcidas
		if (listaRecolhidaRessarcida != null && listaRecolhidaRessarcida.size() != 0) {
			for (ComissaoRecolhidaRessarcida c : listaRecolhidaRessarcida) {
				if (c.getTipo().equals(RecolhidaRessarcida.RECOLHIDA)) {
					comissoesRecolhidas = comissoesRecolhidas.add(c.getValor());
				} else {
					comissoesRessarcidas = comissoesRessarcidas.add(c.getValor());
				}
			}
		}

		// Cálculo dos descontos de salários
		if (listaDescontosSalarios != null && listaDescontosSalarios.size() != 0) {
			for (DescontoSalario d : listaDescontosSalarios) {
				salarioDescontado = salarioDescontado.add(d.getValor());
			}
		}

		// Cálculo dos recebimentos inadimplentes
		if (listaRecebimentoInadimplente != null && listaRecebimentoInadimplente.size() != 0) {
			for (RecebimentoInadiplente r : listaRecebimentoInadimplente) {
				if (r.getFormaPagamento()!= null && r.getFormaPagamento().equals(FormaPagamento.DINHEIRO)) {
					recebimentoInadimplente = recebimentoInadimplente.add(r.getValor());
				}
				else {
					incrementaReceitaFormaPagamento(r);
				}
			}
		}
	}

	public void calculaRepasse() {
		this.repasse = BigDecimal.ZERO;

		this.repasse = this.repasse.subtract(comissoesTotais);
		this.repasse = this.repasse.subtract(premiacao);
		this.repasse = this.repasse.subtract(custosTotais);
		this.repasse = this.repasse.subtract(comissoesRessarcidas);
		this.repasse = this.repasse.add(despesasTotais);
		this.repasse = this.repasse.add(comissoesRecolhidas);
		this.repasse = this.repasse.add(salarioDescontado);
		this.repasse = this.repasse.add(recebimentoInadimplente);
		this.repasse = this.repasse.add(diferencaProdutos);
		this.repasse = this.repasse.add(receitaDinheiro());
	}

	public BigDecimal receitaDinheiro() {
		for (FormaPagamentoValor f : listaReceitaFormaPagamento) {
			if (f.getForma().equals(FormaPagamento.DINHEIRO)) {
				return f.getValor();
			}
		}
		return BigDecimal.ZERO;
	}
	
	//Coloca as receitas de recebimento inadimplente que nãosão em espécies na lista de receitaFormaPagamento
	public void incrementaReceitaFormaPagamento(RecebimentoInadiplente r) {
		for(FormaPagamentoValor f: listaReceitaFormaPagamento) {
			if(f.getForma().equals(r.getFormaPagamento())) {
				f.incrementaReceita(r.getValor());
			}
		}
	}
	

	public void incrementaComissaoVendas(BigDecimal valor) {
		comissaoVendas = comissaoVendas.add(valor);
	}

	public void incrementaComissaoAberturas(BigDecimal valor) {
		comissaoAberturas = comissaoAberturas.add(valor);
	}

	public void incrementaComissaoColocacao(BigDecimal valor) {
		comissaoColocacao = comissaoColocacao.add(valor);
	}

	public void incrementaPremiacaoAberturas(BigDecimal valor) {
		premiacaoAberturas = premiacaoAberturas.add(valor);
	}

	public void incrementaPremiacaoColocacao(BigDecimal valor) {
		premiacaoColocacao = premiacaoColocacao.add(valor);
	}

	public void incrementaFaturamento(BigDecimal valor) {
		faturamento = faturamento.add(valor);
	}

	public void incrementaAberturas1p() {
		this.aberturas1p++;
	}

	public void incrementaAberturas2p() {
		this.aberturas2p++;
	}

	public void incrementaAberturas3p() {
		this.aberturas3p++;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public List<Operacao> getListaOperacoes() {
		return listaOperacoes;
	}

	public void setListaOperacoes(List<Operacao> listaOperacoes) {
		this.listaOperacoes = listaOperacoes;
	}

	public List<FormaPagamentoValor> getListaReceitaFormaPagamento() {
		return listaReceitaFormaPagamento;
	}

	public void setListaReceitaFormaPagamento(List<FormaPagamentoValor> listaReceitaFormaPagamento) {
		this.listaReceitaFormaPagamento = listaReceitaFormaPagamento;
	}

	public List<ResumoConsignacaoVenda> getListaResumoConsignacaoVenda() {
		return listaResumoConsignacaoVenda;
	}

	public void setListaResumoConsignacaoVenda(List<ResumoConsignacaoVenda> listaResumoConsignacaoVenda) {
		this.listaResumoConsignacaoVenda = listaResumoConsignacaoVenda;
	}

	public List<AberturaProduto> getListaAberturas() {
		return listaAberturas;
	}

	public void setListaAberturas(List<AberturaProduto> listaAberturas) {
		this.listaAberturas = listaAberturas;
	}

	public List<AberturaProduto> getListaColocacao() {
		return listaColocacao;
	}

	public void setListaColocacao(List<AberturaProduto> listaColocacao) {
		this.listaColocacao = listaColocacao;
	}

	public List<CustoViagem> getListaCustos() {
		return listaCustos;
	}

	public void setListaCustos(List<CustoViagem> listaCustos) {
		this.listaCustos = listaCustos;
	}

	public List<DespesaVendedor> getListaDespesas() {
		return listaDespesas;
	}

	public void setListaDespesas(List<DespesaVendedor> listaDespesas) {
		this.listaDespesas = listaDespesas;
	}

	public List<ComissaoRecolhidaRessarcida> getListaRecolhidaRessarcida() {
		return listaRecolhidaRessarcida;
	}

	public void setListaRecolhidaRessarcida(List<ComissaoRecolhidaRessarcida> listaRecolhidaRessarcida) {
		this.listaRecolhidaRessarcida = listaRecolhidaRessarcida;
	}

	public List<DescontoSalario> getListaDescontosSalarios() {
		return listaDescontosSalarios;
	}

	public void setListaDescontosSalarios(List<DescontoSalario> listaDescontosSalarios) {
		this.listaDescontosSalarios = listaDescontosSalarios;
	}

	public List<RecebimentoInadiplente> getListaRecebimentoInadimplente() {
		return listaRecebimentoInadimplente;
	}

	public void setListaRecebimentoInadimplente(List<RecebimentoInadiplente> listaRecebimentoInadimplente) {
		this.listaRecebimentoInadimplente = listaRecebimentoInadimplente;
	}

	public BigDecimal getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(BigDecimal faturamento) {
		this.faturamento = faturamento;
	}

	public BigDecimal getComissoesTotais() {
		return comissoesTotais;
	}

	public void setComissoesTotais(BigDecimal comissoesTotais) {
		this.comissoesTotais = comissoesTotais;
	}

	public BigDecimal getComissaoVendas() {
		return comissaoVendas;
	}

	public void setComissaoVendas(BigDecimal comissaoVendas) {
		this.comissaoVendas = comissaoVendas;
	}

	public BigDecimal getComissaoAberturas() {
		return comissaoAberturas;
	}

	public void setComissaoAberturas(BigDecimal comissaoAberturas) {
		this.comissaoAberturas = comissaoAberturas;
	}

	public BigDecimal getComissaoColocacao() {
		return comissaoColocacao;
	}

	public void setComissaoColocacao(BigDecimal comissaoColocacao) {
		this.comissaoColocacao = comissaoColocacao;
	}

	public BigDecimal getPremiacaoAberturas() {
		return premiacaoAberturas;
	}

	public void setPremiacaoAberturas(BigDecimal premiacaoAberturas) {
		this.premiacaoAberturas = premiacaoAberturas;
	}

	public BigDecimal getPremiacaoColocacao() {
		return premiacaoColocacao;
	}

	public void setPremiacaoColocacao(BigDecimal premiacaoColocacao) {
		this.premiacaoColocacao = premiacaoColocacao;
	}

	public BigDecimal getFaturamentoPremiacao() {
		return faturamentoPremiacao;
	}

	public void setFaturamentoPremiacao(BigDecimal faturamentoPremiacao) {
		this.faturamentoPremiacao = faturamentoPremiacao;
	}

	public BigDecimal getPremiacao() {
		return premiacao;
	}

	public void setPremiacao(BigDecimal premiacao) {
		this.premiacao = premiacao;
	}

	public BigDecimal getRepasse() {
		return repasse;
	}

	public void setRepasse(BigDecimal repasse) {
		this.repasse = repasse;
	}

	public BigDecimal getCustosTotais() {
		return custosTotais;
	}

	public void setCustosTotais(BigDecimal custosTotais) {
		this.custosTotais = custosTotais;
	}

	public BigDecimal getDespesasTotais() {
		return despesasTotais;
	}

	public void setDespesasTotais(BigDecimal despesasTotais) {
		this.despesasTotais = despesasTotais;
	}

	public BigDecimal getComissoesRecolhidas() {
		return comissoesRecolhidas;
	}

	public void setComissoesRecolhidas(BigDecimal comissoesRecolhidas) {
		this.comissoesRecolhidas = comissoesRecolhidas;
	}

	public BigDecimal getComissoesRessarcidas() {
		return comissoesRessarcidas;
	}

	public void setComissoesRessarcidas(BigDecimal comissoesRessarcidas) {
		this.comissoesRessarcidas = comissoesRessarcidas;
	}

	public BigDecimal getSalarioDescontado() {
		return salarioDescontado;
	}

	public void setSalarioDescontado(BigDecimal salarioDescontado) {
		this.salarioDescontado = salarioDescontado;
	}

	public BigDecimal getRecebimentoInadimplente() {
		return recebimentoInadimplente;
	}

	public void setRecebimentoInadimplente(BigDecimal recebimentoInadimplente) {
		this.recebimentoInadimplente = recebimentoInadimplente;
	}

	public BigDecimal getDiferencaProdutos() {
		return diferencaProdutos;
	}

	public void setDiferencaProdutos(BigDecimal diferencaProdutos) {
		this.diferencaProdutos = diferencaProdutos;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getAberturas3p() {
		return aberturas3p;
	}

	public void setAberturas3p(int aberturas3p) {
		this.aberturas3p = aberturas3p;
	}

	public int getAberturas2p() {
		return aberturas2p;
	}

	public void setAberturas2p(int aberturas2p) {
		this.aberturas2p = aberturas2p;
	}

	public int getAberturas1p() {
		return aberturas1p;
	}

	public void setAberturas1p(int aberturas1p) {
		this.aberturas1p = aberturas1p;
	}

}
