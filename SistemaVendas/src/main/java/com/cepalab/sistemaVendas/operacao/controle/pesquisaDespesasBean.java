package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.repository.DespesasVendedores;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.filter.DespesaVendedorFilter;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class pesquisaDespesasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DespesaVendedorFilter filtro;
	private List<DespesaVendedor> despesasFiltradas;
	private List<Funcionario> listaFuncionarios;
	private DespesaVendedor despesa;

	@Inject
	private Funcionarios fun;

	@Inject
	private DespesasVendedores despesas;

	@PostConstruct
	public void inicio() {
		filtro = new DespesaVendedorFilter();
		listaFuncionarios = fun.funcionarios();
	}

	public void pesquisar() {
		despesasFiltradas = new ArrayList<>();
		despesasFiltradas = despesas.despesasFiltradas(filtro);
	}
	
	

	public void excluir() {
		try {
			despesas.remover(despesa);
			FacesUtil.addInfoMessage("Despesa excluída com sucesso");
			pesquisar();
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void troca(DespesaVendedor despesa) {
		this.despesa = despesa;
	}

	public DespesaVendedorFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(DespesaVendedorFilter filtro) {
		this.filtro = filtro;
	}

	public List<DespesaVendedor> getDespesasFiltradas() {
		return despesasFiltradas;
	}

	public void setDespesasFiltradas(List<DespesaVendedor> despesasFiltradas) {
		this.despesasFiltradas = despesasFiltradas;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public DespesaVendedor getDespesa() {
		return despesa;
	}

	public void setDespesa(DespesaVendedor despesa) {
		this.despesa = despesa;
	}
}
