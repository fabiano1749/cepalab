package com.cepalab.sistemaVendas.repository.filter;

import java.io.Serializable;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.EstadosBrasileiros;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;


public class ProdutosConsignadosFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;
	private Produto produto;
	private Cliente cliente;
	private Rota rota;
	private String cidade;
	private EstadosBrasileiros uf;
	
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public EstadosBrasileiros getUf() {
		return uf;
	}

	public void setUf(EstadosBrasileiros uf) {
		this.uf = uf;
	}

}
