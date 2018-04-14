package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.filter.OperacaoFilter;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.CadastroOperacao;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class pesquisaOperacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private OperacaoFilter filtro;
	private List<Operacao> operacoesFiltradas;
	private List<Funcionario> listaFuncionarios;
	private List<Funcionario> listaFun = new ArrayList<>();
	private int numero;
	private Operacao operacao;
	private boolean checado;

	@Inject
	private Operacoes operacoes;

	@Inject
	private CadastroOperacao cadastroOperacao;

	@Inject
	private Funcionarios fun;

	@Inject
	private Seguranca seg;

	@PostConstruct
	public void inicio() {
		filtro = new OperacaoFilter();
		if (!isAdministrador()) {
			filtro.setFuncionario(seg.UsuarioLogado());
		} else {
			listaFun = fun.funcionarios();
			retiraTiposFuncionários();

		}

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

	public void pesquisar() {
		operacoesFiltradas = operacoes.filtradas(filtro);
	}

	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(Operacao operacao) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 600);
		opcoes.put("contentWidth", 1200);

		List<String> lista = new ArrayList<>();
		lista.add("" + operacao.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("operacao", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/operacaoDialogo", opcoes, params);
	}

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}

	public void troca(Operacao o) {
		setOperacao(o);
	}

	public void excluir() {
		try {
			operacoes.remover(operacao);
			pesquisar();
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void editaChecado(Operacao o) {
		// o.setChecado(checado);
		cadastroOperacao.salvar(o);

	}

	public List<Operacao> getOperacoesFiltradas() {
		return operacoesFiltradas;
	}

	public void setOperacoesFiltradas(List<Operacao> operacoesFiltradas) {
		this.operacoesFiltradas = operacoesFiltradas;
	}

	public OperacaoFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(OperacaoFilter filtro) {
		this.filtro = filtro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public boolean isChecado() {
		return checado;
	}

	public void setChecado(boolean checado) {
		this.checado = checado;
	}

}
