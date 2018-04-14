package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;

public class ReceitaFormaPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormaPagamento formaPag;
	private BigDecimal receita = BigDecimal.ZERO;

	public FormaPagamento getFormaPag() {
		return formaPag;
	}

	public void setFormaPag(FormaPagamento formaPag) {
		this.formaPag = formaPag;
	}

	public BigDecimal getReceita() {
		return receita;
	}

	public void setReceita(BigDecimal receita) {
		this.receita = receita;
	}

	public void incrementaReceita(BigDecimal valor) {
		receita = receita.add(valor);
	}

}
