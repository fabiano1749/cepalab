package com.cepalab.sistemaVendas.cadastro.dominio;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="politica_venda_consignacao")
public class PoliticaVendaConsignacao extends GenericDTO{

	private static final long serialVersionUID = 1L;
		
	private int inicio;
	private int fim;
	private BigDecimal minVenda = BigDecimal.ZERO;
	private BigDecimal minConsignacao = BigDecimal.ZERO;
	private BigDecimal comissaoProntaEntrega = BigDecimal.ZERO;
	private BigDecimal comissaoTransportadora = BigDecimal.ZERO;
	private PoliticaTipoVendedorProduto comissaoTipoVendedorProduto;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setInicio(int inicio) {
		this.inicio = inicio;
	}
	
	@Column(nullable=false)
	public int getInicio() {
		return inicio;
	}
	
	public void setFim(int fim) {
		this.fim = fim;
	}
	
	@Column(nullable=false)
	public int getFim() {
		return fim;
	}
	
	public void setMinVenda(BigDecimal minVenda) {
		this.minVenda = minVenda;
	}
	
	@Column(name="min_venda", precision=10, scale=2)
	public BigDecimal getMinVenda() {
		return minVenda;
	}

	@Column(name="min_consignacao", precision=10, scale=2)
	public BigDecimal getMinConsignacao() {
		return minConsignacao;
	}

	public void setMinConsignacao(BigDecimal minConsignacao) {
		this.minConsignacao = minConsignacao;
	}

	@Column(name="comissao_pronta_entrega", precision=10, scale=2)
	public BigDecimal getComissaoProntaEntrega() {
		return comissaoProntaEntrega;
	}

	public void setComissaoProntaEntrega(BigDecimal comissaoProntaEntrega) {
		this.comissaoProntaEntrega = comissaoProntaEntrega;
	}

	@Column(name="comissao_transportadora", precision=10, scale=2)
	public BigDecimal getComissaoTransportadora() {
		return comissaoTransportadora;
	}

	public void setComissaoTransportadora(BigDecimal comissaoTransportadora) {
		this.comissaoTransportadora = comissaoTransportadora;
	}

	@ManyToOne
	@JoinColumn(name = "comissao_tipo_vendedor_id", nullable = false)
	public PoliticaTipoVendedorProduto getComissaoTipoVendedorProduto() {
		return comissaoTipoVendedorProduto;
	}

	public void setComissaoTipoVendedorProduto(PoliticaTipoVendedorProduto comissaoTipoVendedorProduto) {
		this.comissaoTipoVendedorProduto = comissaoTipoVendedorProduto;
	}
	
	@Transient
	public boolean estaNoIntervalo(int quantidade) {
		if(quantidade >= inicio && quantidade <= fim) {
			return true;
		}
	
		return false;
	}
	
	@Transient
	public BigDecimal taxaComissao(boolean prontraEntrega) {
		if(prontraEntrega) {
			return comissaoProntaEntrega;
		}
		
		return comissaoTransportadora;
	}	
}
