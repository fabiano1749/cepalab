package com.cepalab.sistemaVendas.operacao.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class ResumoConsignacaoVenda implements Serializable{

	private static final long serialVersionUID = 1L;

	private Operacao operacao;
	private Produto produto;
	private int vendidos;
	private BigDecimal receita;
	private BigDecimal comissao;
	private BigDecimal taxaComissao;
	
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
	public BigDecimal getTaxaComissao() {
		return taxaComissao;
	}
	public void setTaxaComissao(BigDecimal taxaComissao) {
		this.taxaComissao = taxaComissao;
	}
	public Operacao getOperacao() {
		return operacao;
	}
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}	
	
	
	
}
