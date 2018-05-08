package com.cepalab.sistemaVendas.fechamento.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;

public class FormaPagamentoValor implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormaPagamento forma;
	private BigDecimal valor;

	
	public FormaPagamentoValor(FormaPagamento forma, BigDecimal valor) {
		this.forma = forma;
		this.valor = valor;
	}
	
	public FormaPagamentoValor() {
		
	}
	
	public void incrementaReceita(BigDecimal valor) {
		this.valor = this.valor.add(valor);
	}
	
	public FormaPagamento getForma() {
		return forma;
	}

	public void setForma(FormaPagamento forma) {
		this.forma = forma;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
