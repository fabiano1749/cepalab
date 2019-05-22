package com.cepalab.sistemaVendas.fechamento.dominio;

import java.io.Serializable;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class AumentoConsignacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Produto produto;
	private int quantAnterior;
	private int quantAtual;
	private int aumento;
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public int getQuantAnterior() {
		return quantAnterior;
	}
	public void setQuantAnterior(int quantAnterior) {
		this.quantAnterior = quantAnterior;
	}
	public int getQuantAtual() {
		return quantAtual;
	}
	public void setQuantAtual(int quantAtual) {
		this.quantAtual = quantAtual;
	}
	public int getAumento() {
		return aumento;
	}
	public void setAumento(int aumento) {
		this.aumento = aumento;
	}
	
	
}
