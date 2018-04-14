package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.repository.ComissoesRecolhidasRessarcidas;
import com.cepalab.sistemaVendas.repository.Funcionarios;



@Named
@ViewScoped
public class pesquisaRecolhidaRessarcidaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;
	private List<Funcionario> listaFuncionario;
	private List<ComissaoRecolhidaRessarcida> itens;
	
	@Inject
	private Funcionarios funcionarios;

	@Inject
	private ComissoesRecolhidasRessarcidas reolhidasRessarcidas;
	
	@PostConstruct
	public void inicio() {
		listaFuncionario = funcionarios.funcionarios();
		itens = new ArrayList<ComissaoRecolhidaRessarcida>();
	}
	
	public void criaListaItens(Funcionario fun) {
		itens = reolhidasRessarcidas.porFuncionario(funcionario);
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}

	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}

	
	public List<ComissaoRecolhidaRessarcida> getItens() {
		return itens;
	}

	public void setItens(List<ComissaoRecolhidaRessarcida> itens) {
		this.itens = itens;
	}
	
}
