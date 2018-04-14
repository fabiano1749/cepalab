package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class ProdutoQuantidade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private int vendidos;
	private BigDecimal receita = BigDecimal.ZERO;
	private BigDecimal comissao = BigDecimal.ZERO;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getVendidos() {
		return vendidos;
	}

	public void setVendidos(int vendidos) {
		this.vendidos = vendidos;
	}

	public BigDecimal getReceita() {
		return receita;
	}

	public void setReceita(BigDecimal receita) {
		this.receita = receita;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	
	public void incrementaVendidos(int quantidade) {
		vendidos = vendidos + quantidade;
	}
	
	public void incrementaReceita(BigDecimal valor) {
		this.receita = this.receita.add(valor);
	}
	
	public void incrementaComissao(BigDecimal valor) {
		this.comissao = this.comissao.add(valor);
	}
	
	@Override
	public String toString() {
		return "QT: "+" "+vendidos + "    " +"R$ :"+ " "+receita; 
	}
	
}
