package com.cepalab.sistemaVendas.operacao.dominio;

import java.io.Serializable;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class ProdutoQuantidade implements Serializable {

	private static final long serialVersionUID = 1L;
	private Produto produto = new Produto();
	private Long quantidade;
	private Long quantAux;

	public ProdutoQuantidade(Produto produto, Long quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public ProdutoQuantidade() {
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Long getQuantAux() {
		return quantAux;
	}

	public void setQuantAux(Long quantAux) {
		this.quantAux = quantAux;
	}

}
