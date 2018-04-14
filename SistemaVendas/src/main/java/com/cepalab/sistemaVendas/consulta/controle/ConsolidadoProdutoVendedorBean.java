package com.cepalab.sistemaVendas.consulta.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.consulta.dominio.Consolidado;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.Produtos;

@ViewScoped
@Named
public class ConsolidadoProdutoVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Funcionario> listaFun = new ArrayList<>();
	private List<Produto> listaProdutos = new ArrayList<>();
	private Consolidado consolidado;
	
	
	
	@Inject
	private Funcionarios fun;

	@Inject
	private Produtos produtos;

	@Inject
	private Operacoes operacoes;
	
	@Inject
	private CustosViagens custos;
		
	
	@PostConstruct
	public void inicio() {
		consolidado = new Consolidado();
		listaFun = fun.funcionarios();
		listaProdutos = produtos.produtos();
		retiraTiposFuncionários();
		
	}

	public void pesquisa() {
		consolidado.criaListaFechamentos(listaFuncionarios, operacoes, custos, produtos);
	}
	
	
	public int quantFuncionarios() {
		return listaFuncionarios.size();
	}
	
	//Melhorar isso em versões posteriores
	private void retiraTiposFuncionários() {
		listaFuncionarios = new ArrayList<>();
		for(Funcionario f : listaFun) {
			if(!f.getTipoVendedor().getNome().equals("Interno-0")) {
				listaFuncionarios.add(f);
			}
		}
	}
	
	
	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}


	public Consolidado getConsolidado() {
		return consolidado;
	}


	public void setConsolidado(Consolidado consolidado) {
		this.consolidado = consolidado;
	}
	
}
