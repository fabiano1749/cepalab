package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.fechamento.dominio.Fechamento;
import com.cepalab.sistemaVendas.repository.ComissoesRecolhidasRessarcidas;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.DescontosSalarios;
import com.cepalab.sistemaVendas.repository.DespesasVendedores;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.RecebimentosInadiplentes;
import com.cepalab.sistemaVendas.repository.TiposProdutos;
import com.cepalab.sistemaVendas.security.Seguranca;

@ViewScoped
@Named
public class FechamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fechamento item;
	private Funcionario funcionario = new Funcionario();
	private Date inicio = new Date();
	private Date fim = new Date();
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Funcionario> listaFun = new ArrayList<>();

	@Inject
	private Funcionarios fun;

	@Inject
	private Operacoes operacoes;

	@Inject
	private CustosViagens custos;

	@Inject
	private DespesasVendedores despesas;

	@Inject
	private ComissoesRecolhidasRessarcidas recolhidaRessarcida;

	@Inject
	private DescontosSalarios descontos;

	@Inject
	private RecebimentosInadiplentes recebimentoInadimplente;

	@Inject
	private FechamentoAcertoProdutoBean acertoProduto;

	@Inject
	private TiposProdutos tiposProdutos;
	
	@Inject
	private Seguranca seg;

	@PostConstruct
	public void inicio() {

		if (!isAdministrador()) {
			funcionario = (seg.UsuarioLogado());
			limpa();
		} else {
			listaFun = fun.funcionarios();
			retiraTiposFuncionários();
			limpa();
		}
		
		

	}

	public void limpa() {
		item = new Fechamento();
	}

	public void criaFechamento() {
		limpa();
		item.setFuncionario(funcionario);
		item.setInicio(inicio);
		item.setFim(fim);

		item.start(operacoes, custos, despesas, recolhidaRessarcida, descontos, recebimentoInadimplente, tiposProdutos.tipos());
		acertoProduto.inicio();
		item.calculaRepasse();

	}

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}

	// Melhorar isso em versões posteriores
	private void retiraTiposFuncionários() {
		listaFuncionarios = new ArrayList<>();
		for (Funcionario f : listaFun) {
			if (!f.getTipoVendedor().getNome().equals("Interno-0")) {
				listaFuncionarios.add(f);
			}
		}
	}

	public void atualizaRepasse() {
		if (item.getPremiacao() == null) {
			item.setPremiacao(BigDecimal.ZERO);
			item.calculaRepasse();
		} else {
			item.calculaRepasse();
		}

	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Funcionario> getListaFun() {
		return listaFun;
	}

	public void setListaFun(List<Funcionario> listaFun) {
		this.listaFun = listaFun;
	}

	public Fechamento getItem() {
		return item;
	}

	public void setItem(Fechamento item) {
		this.item = item;
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
}
