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
import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.filter.CustosViagemFilter;
import com.cepalab.sistemaVendas.security.Seguranca;


@Named
@ViewScoped
public class pesquisaCustosBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private CustosViagemFilter filtro;
	private List<CustoViagem> custosFiltrados;
	private List<Funcionario> listaFuncionarios;
	
	@Inject
	private Funcionarios fun;

	@Inject
	private Seguranca seg;
	
	@Inject
	private CustosViagens custos;

	@PostConstruct
	public void inicio() {
		filtro = new CustosViagemFilter();
		if (!isAdministrador()) {
			filtro.setFuncionario(seg.UsuarioLogado());
		} else {
			listaFuncionarios = fun.funcionarios();
		}

	}

	public void pesquisar() {
		custosFiltrados = custos.porFuncionarioFiltrados(filtro);
	}

	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(CustoViagem custo) {
		
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 300);
		opcoes.put("contentWidth", 400);

		List<String> lista = new ArrayList<>();
		lista.add("" + custo.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("custo", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/custosDialogo", opcoes, params);
	}

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}

	public CustosViagemFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(CustosViagemFilter filtro) {
		this.filtro = filtro;
	}

	public List<CustoViagem> getCustosFiltrados() {
		return custosFiltrados;
	}

	public void setCustosFiltrados(List<CustoViagem> custosFiltrados) {
		this.custosFiltrados = custosFiltrados;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

}
