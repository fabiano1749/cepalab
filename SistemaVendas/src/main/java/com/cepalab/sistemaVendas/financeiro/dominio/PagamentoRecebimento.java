package com.cepalab.sistemaVendas.financeiro.dominio;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("pagamento_recebimento")
public class PagamentoRecebimento extends Transacao {

	private String favorecidoOrigemOutros;
	private Funcionario favorecidoOrigemVendedor; 
	private Date primeiroVencimento;
	private PeriodoPagamento periodoPagamento;
	private int periodoPagamentoNumerico;
	private boolean parcelado;
	private boolean realizado;
	
	@Column(name="favorecido_origem_Outros")
	public String getFavorecidoOrigemOutros() {
		return favorecidoOrigemOutros;
	}
	
	public void setFavorecidoOrigemOutros(String favorecidoOrigemOutros) {
		this.favorecidoOrigemOutros = favorecidoOrigemOutros;
	}
	
	@ManyToOne
	@JoinColumn(name = "favorecido_origem_vendedor")
	public Funcionario getFavorecidoOrigemVendedor() {
		return favorecidoOrigemVendedor;
	}

	public void setFavorecidoOrigemVendedor(Funcionario favorecidoOrigemVendedor) {
		this.favorecidoOrigemVendedor = favorecidoOrigemVendedor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="primeiro_vencimento")
	public Date getPrimeiroVencimento() {
		return primeiroVencimento;
	}

	public void setPrimeiroVencimento(Date primeiroVencimento) {
		this.primeiroVencimento = primeiroVencimento;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, name="periodo_pagamento")
	public PeriodoPagamento getPeriodoPagamento() {
		return periodoPagamento;
	}

	public void setPeriodoPagamento(PeriodoPagamento periodoPagamento) {
		this.periodoPagamento = periodoPagamento;
	}

	@Column(name="periodo_pagamento_numerico")
	public int getPeriodoPagamentoNumerico() {
		return periodoPagamentoNumerico;
	}

	public void setPeriodoPagamentoNumerico(int periodoPagamentoNumerico) {
		if(periodoPagamentoNumerico < 0) {
			this.periodoPagamentoNumerico = 1;	
		}
		else {
			this.periodoPagamentoNumerico = periodoPagamentoNumerico;
		}
		
	}

	public boolean getParcelado() {
		return parcelado;
	}

	public void setParcelado(boolean parcelado) {
		this.parcelado = parcelado;
	}

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}
}
