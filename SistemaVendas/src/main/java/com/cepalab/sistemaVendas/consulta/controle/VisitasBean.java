package com.cepalab.sistemaVendas.consulta.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.EstadosBrasileiros;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Rotas;
import com.cepalab.sistemaVendas.repository.filter.VisitasFilter;

@ViewScoped
@Named
public class VisitasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Cliente> listaClientes = new ArrayList<>();
	private List<Rota> listaRotas = new ArrayList<>();
	private VisitasFilter filtro = new VisitasFilter();
	
	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Clientes clientes;

	@Inject
	private Rotas rotas;

	@PostConstruct
	public void inicio() {
		listaFuncionarios = funcionarios.vendedorAtivo();
	}

	public void pesquisa() {
		listaClientes = clientes.nuncaVisitadosJPQL(filtro);
	}

	public void alimentaRotas() {
		if (filtro.getFuncionario() != null) {
			listaRotas = rotas.rotasPorFuncionario(filtro.getFuncionario());
		}
		else {
			listaRotas = new ArrayList<>();
		}
	}
	
	
	public EstadosBrasileiros[] estados() {
		return EstadosBrasileiros.values();
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Rota> getListaRotas() {
		return listaRotas;
	}

	public void setListaRotas(List<Rota> listaRotas) {
		this.listaRotas = listaRotas;
	}
	
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public VisitasFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(VisitasFilter filtro) {
		this.filtro = filtro;
	}
}
