package com.cepalab.sistemaVendas.operacao.dominio;

import java.io.Serializable;

public class AuxiliarOperacaoVenda implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProdutoQuantidade produtoQuantidade;
	private Venda venda;

	public AuxiliarOperacaoVenda(ProdutoQuantidade produtoQuantidade, Venda venda) {

		this.produtoQuantidade = produtoQuantidade;
		this.venda = venda;
	}

	public AuxiliarOperacaoVenda() {

	}

	public ProdutoQuantidade getProdutoQuantidade() {
		return produtoQuantidade;
	}

	public void setProdutoQuantidade(ProdutoQuantidade produtoQuantidade) {
		this.produtoQuantidade = produtoQuantidade;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	
}
