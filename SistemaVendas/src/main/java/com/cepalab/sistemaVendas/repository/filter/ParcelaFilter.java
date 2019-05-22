package com.cepalab.sistemaVendas.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.cepalab.sistemaVendas.financeiro.dominio.StatusParcela;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoTransacao;



public class ParcelaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoTransacao tipoTransacao;
	private StatusParcela statusParcela;
	private Date inicio;
	private Date fim;


	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}
	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}
	public StatusParcela getStatusParcela() {
		return statusParcela;
	}
	public void setStatusParcela(StatusParcela statusParcela) {
		this.statusParcela = statusParcela;
	}
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
	
}
