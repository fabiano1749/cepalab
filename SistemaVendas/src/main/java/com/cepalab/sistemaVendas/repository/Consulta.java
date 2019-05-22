package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class Consulta implements Serializable{

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private Long maxId;
	private Long totalProduto;
	
	public Consulta(Produto p, Long max, Long totalProduto) {
		this.produto = p;
		this.maxId = max;
		this.totalProduto = totalProduto;
	}
	
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Long getMaxId() {
		return maxId;
	}
	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}
	public Long getTotalProduto() {
		return totalProduto;
	}
	public void setTotalProduto(Long totalProduto) {
		this.totalProduto = totalProduto;
	}
		
}
