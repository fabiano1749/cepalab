package com.cepalab.sistemaVendas.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.cepalab.sistemaVendas.financeiro.dominio.Conta;


public class TransferenciaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date inicio;
	private Date fim;
	private Conta origem;
	private Conta destino;
	
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getFim() {
		return fim;
	}
	public void setFim(Date fim) {
		this.fim = fim;
	}
	public Conta getOrigem() {
		return origem;
	}
	public void setOrigem(Conta origem) {
		this.origem = origem;
	}
	public Conta getDestino() {
		return destino;
	}
	public void setDestino(Conta destino) {
		this.destino = destino;
	}
}
