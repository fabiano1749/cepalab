package com.cepalab.sistemaVendas.fechamento.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cepalab.sistemaVendas.Expedicao.dominio.ExpedProduto;

public class AcertoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	private ExpedProduto expedProduto;
	private int vendidos;
	private int consignados;
	private int devolvidos;
	private int repostos;
	private int amostras;
	private int estoqueEsperado;
	private int diferenca;
	private BigDecimal desconto = BigDecimal.ZERO;

	public void calculaEstoqueEsperado() {
		this.estoqueEsperado = expedProduto.getSaida() - vendidos - consignados + devolvidos - repostos - amostras;
	}

	public void calculaDiferenca() {
		this.diferenca = estoqueEsperado - expedProduto.getChegada();
	}

	public void calculaDesconto() {
		if (diferenca > 0) {
			setDesconto(new BigDecimal(diferenca).multiply(expedProduto.getProduto().getCusto()));
		}
	}

	public void adicionaConsignado(int c) {
		this.consignados = this.consignados + c;
	}

	public void adicionaDevolvidos(int d) {
		this.devolvidos = this.devolvidos + d;
	}

	public void adicionaVendidos(int v) {
		this.vendidos = this.vendidos + v;
	}

	public void adicionaRepostos(int r) {
		this.repostos = this.repostos + r;
	}

	public void adicionaAmostra(int a) {
		this.amostras = this.amostras + a;
	}

	public ExpedProduto getExpedProduto() {
		return expedProduto;
	}

	public void setExpedProduto(ExpedProduto expedProduto) {
		this.expedProduto = expedProduto;
	}

	public int getVendidos() {
		return vendidos;
	}

	public void setVendidos(int vendidos) {
		this.vendidos = vendidos;
	}

	public int getDevolvidos() {
		return devolvidos;
	}

	public void setDevolvidos(int devolvidos) {
		this.devolvidos = devolvidos;
	}

	public int getRepostos() {
		return repostos;
	}

	public void setRepostos(int repostos) {
		this.repostos = repostos;
	}

	public int getAmostras() {
		return amostras;
	}

	public void setAmostras(int amostras) {
		this.amostras = amostras;
	}

	public int getEstoqueEsperado() {
		return estoqueEsperado;
	}

	public void setEstoqueEsperado(int estoqueEsperado) {
		this.estoqueEsperado = estoqueEsperado;
	}

	public int getDiferenca() {
		return diferenca;
	}

	public void setDiferenca(int diferenca) {
		this.diferenca = diferenca;
	}

	public int getConsignados() {
		return consignados;
	}

	public void setConsignados(int consignados) {
		this.consignados = consignados;
	}

	public BigDecimal getDesconto() {
		return desconto.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

}
