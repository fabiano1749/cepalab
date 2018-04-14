package com.cepalab.sistemaVendas.cadastro.controle;

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
import com.cepalab.sistemaVendas.cadastro.dominio.TipoFuncionario;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.TiposFuncionarios;



@Named
@ViewScoped
public class PesquisaFuncionarioBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Funcionario> listaFuncionarios; 
	private List<TipoFuncionario> listaTipos;
	private TipoFuncionario tipo;
	
	@Inject
	private Funcionarios funcionarios;
	
	@Inject
	private TiposFuncionarios tipos;
	
	@PostConstruct
	public void inicio() {
		limpar();
		listaTipos = tipos.tipos(); 
	}
	
	public void limpar() {
		tipo = new TipoFuncionario();
		listaFuncionarios = new ArrayList<>();
		listaTipos = new ArrayList<>();
	}
	
	public void pesquisa() {
		listaFuncionarios = funcionarios.porTipo(tipo);
	}
	
	
	
	public void fechaDialogo() {
		
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void abrirDialogo(Funcionario fun) {
		
		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		
		List<String> lista = new ArrayList<>();
		lista.add(""+fun.getId());
		
		 Map<String, List<String>> params = new HashMap<>();
		  params.put("fun", lista);
		
		RequestContext.getCurrentInstance().openDialog("/dialogos/funcionario", opcoes, params);
	}
	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<TipoFuncionario> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<TipoFuncionario> listaTipos) {
		this.listaTipos = listaTipos;
	}

	public TipoFuncionario getTipo() {
		return tipo;
	}

	public void setTipo(TipoFuncionario tipo) {
		this.tipo = tipo;
	}
	
}
