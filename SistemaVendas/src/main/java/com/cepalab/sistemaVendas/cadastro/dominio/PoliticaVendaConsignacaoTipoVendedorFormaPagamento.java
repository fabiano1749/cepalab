package com.cepalab.sistemaVendas.cadastro.dominio;

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

import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;

@SuppressWarnings("serial")
@Entity
@Table(name = "politica_venda_consignacao_tipovendedor_forma_pagamento")
public class PoliticaVendaConsignacaoTipoVendedorFormaPagamento extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private BigDecimal taxa = BigDecimal.ZERO;
	private TipoVendedor tipoVendedor;
	private FormaPagamento formaPagamento;

	public  PoliticaVendaConsignacaoTipoVendedorFormaPagamento(TipoVendedor tipoVendedor, FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
		this.tipoVendedor = tipoVendedor;
		this.taxa = BigDecimal.ZERO;
		
	}
	
	public PoliticaVendaConsignacaoTipoVendedorFormaPagamento() {
	}
	
	
	@ManyToOne
	@JoinColumn(name = "tipo_vendedor_id", nullable = false)
	public TipoVendedor getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(TipoVendedor tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false, name = "forma_pagamento")
	public FormaPagamento getFormaPagamento() {
		return this.formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getTaxa() {
		return this.taxa;
	}
	
	public void setTaxa(BigDecimal taxa) {
		this.taxa = taxa;
	}
}
