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

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.Clientes;
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
	private List<Cliente> listaClientes = new ArrayList<>();
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
	private Clientes clientes;

	@Inject
	private Seguranca seg;

	@PostConstruct
	public void inicio() {
		filtro = new OperacaoFilter();
		if (isVendedor()) {
			filtro.setFuncionario(seg.UsuarioLogado());
			listaClientes = clientes.porFuncionario(seg.UsuarioLogado());
		} else {
			listaFuncionarios = fun.vendedorAtivo();
			listaClientes = clientes.clientes();
		}

	}

	public void pesquisar() {
		operacoesFiltradas = operacoes.filtradas(filtro);
		if (operacoesFiltradas == null) {
			FacesUtil.addErrorMessage("Não foram encontrados registros para os filtros informados !");
		} else {
			if (operacoesFiltradas.isEmpty()) {
				FacesUtil.addErrorMessage("Não foram encontrados registros para os filtros informados !");
			}
		}
	}

	public void alimentaListaCliente() {
		if (filtro.getFuncionario() != null) {
			listaClientes = clientes.porFuncionario(filtro.getFuncionario());
		}

	}

	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(Operacao operacao) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("width", 1200);
		opcoes.put("height", 600);
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

	public boolean isVendedor() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("VENDEDORES")) {
				return true;
			}
		}
		return false;
	}

	public boolean isRoot() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ROOT")) {
				return true;
			}
		}
		return false;
	}

	public boolean podeExcluir(Operacao o) {
		if ((o.getFuncionario().equals(seg.UsuarioLogado()) && o.isChecado() == false) || isRoot()) {
			return true;
		}
		return false;
	}

	public boolean podeConferir() {
		if (isRoot() || isAdministrador()) {
			return true;
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

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
}
