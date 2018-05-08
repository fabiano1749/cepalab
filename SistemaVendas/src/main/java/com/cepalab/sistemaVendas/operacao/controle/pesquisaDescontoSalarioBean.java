package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.repository.DescontosSalarios;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.filter.DescontoSalarioFilter;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class pesquisaDescontoSalarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DescontoSalarioFilter filtro;
	private List<DescontoSalario> descontosFiltrados;
	private List<Funcionario> listaFuncionarios;
	private DescontoSalario desconto;

	@Inject
	private Funcionarios fun;

	@Inject
	private DescontosSalarios descontos;

	@PostConstruct
	public void inicio() {
		filtro = new DescontoSalarioFilter();
		listaFuncionarios = fun.funcionarios();
	}

	public void pesquisar() {
		descontosFiltrados = new ArrayList<>();
		descontosFiltrados = descontos.descontosFiltrados(filtro);
	}
	
	

	public void excluir() {
		try {
			descontos.remover(desconto);
			FacesUtil.addInfoMessage("Desconto de salário excluído com sucesso");
			pesquisar();
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void troca(DescontoSalario desconto) {
		this.desconto = desconto;
	}

	public DescontoSalarioFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(DescontoSalarioFilter filtro) {
		this.filtro = filtro;
	}

	public List<DescontoSalario> getDescontosFiltrados() {
		return descontosFiltrados;
	}

	public void setDescontosFiltrados(List<DescontoSalario> descontosFiltrados) {
		this.descontosFiltrados = descontosFiltrados;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public DescontoSalario getDesconto() {
		return desconto;
	}

	public void setDesconto(DescontoSalario desconto) {
		this.desconto = desconto;
	}

}
