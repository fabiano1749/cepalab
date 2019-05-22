package com.cepalab.sistemaVendas.operacao.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;

@SuppressWarnings("serial")
@Entity
@Table(name="receita")
public class Receita extends GenericDTO{

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private BigDecimal valor = BigDecimal.ZERO;
	private FormaPagamento formaPagamento;
	private Operacao operacao;
	
	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="forma_pagamento", nullable=false)
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	@ManyToOne
	@JoinColumn(name = "operacao_id", nullable=false)
	public Operacao getOperacao() {
		return operacao;
	}
	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
}
