package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class ProdutoQuantidade implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private int vendidos;
	private BigDecimal receita = BigDecimal.ZERO;
	private BigDecimal comissao = BigDecimal.ZERO;
	private int consignados;
	
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
	public void incrementaConsignados(int quantidade) {
		consignados = consignados + quantidade;
	}
	
	public void incrementaReceita(BigDecimal valor) {
		this.receita = this.receita.add(valor);
	}
	
	public void incrementaComissao(BigDecimal valor) {
		this.comissao = this.comissao.add(valor);
	}
	
	public int getConsignados() {
		return consignados;
	}

	public void setConsignados(int consignados) {
		this.consignados = consignados;
	}

	@Override
	public String toString() {
		DecimalFormat decF = new java.text.DecimalFormat("#,###,##0.00");
		
		return "QT: "+" "+vendidos + "    " +"R$ :"+ " "+decF.format(receita); 
	}
	
}
