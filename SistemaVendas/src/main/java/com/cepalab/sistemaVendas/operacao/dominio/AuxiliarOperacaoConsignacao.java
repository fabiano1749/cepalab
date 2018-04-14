package com.cepalab.sistemaVendas.operacao.dominio;

import java.io.Serializable;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class AuxiliarOperacaoConsignacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProdutoQuantidade produtoQuantidade;
	private Consignacao consignacao;

	public AuxiliarOperacaoConsignacao(ProdutoQuantidade produtoQuantidade, Consignacao consignacao) {

		this.produtoQuantidade = produtoQuantidade;
		this.consignacao = consignacao;
	}

	public AuxiliarOperacaoConsignacao() {
		this.produtoQuantidade = new ProdutoQuantidade();
		this.produtoQuantidade.setProduto(new Produto());
		this.produtoQuantidade.setQuantAux(0L);
		this.produtoQuantidade.setQuantidade(0L);
		
		this.consignacao = new Consignacao();
		this.consignacao.setProduto(new Produto());
	}

	public ProdutoQuantidade getProdutoQuantidade() {
		return produtoQuantidade;
	}

	public void setProdutoQuantidade(ProdutoQuantidade produtoQuantidade) {
		this.produtoQuantidade = produtoQuantidade;
	}

	public Consignacao getConsignacao() {
		return consignacao;
	}

	public void setConsignacao(Consignacao consignacao) {
		this.consignacao = consignacao;
	}
}
