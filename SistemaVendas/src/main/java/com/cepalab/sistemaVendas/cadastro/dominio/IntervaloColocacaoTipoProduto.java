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
@Table(name="intervalo_colocacao_tipo_produto")
public class IntervaloColocacaoTipoProduto extends GenericDTO{

	private static final long serialVersionUID = 1L;
		
	private int inicio;
	private int fim;
	private BigDecimal valor = BigDecimal.ZERO;
	private BigDecimal premiacao = BigDecimal.ZERO;
	private PoliticaColocacaoTipoVendedorTipoProduto politicaColocacaoTipoVendedorTipoProduto;
	
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
	
	@Column(precision=10, scale=2)
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(precision=10, scale=2)
	public BigDecimal getPremiacao() {
		return premiacao;
	}

	public void setPremiacao(BigDecimal premiacao) {
		this.premiacao = premiacao;
	}

	
	@ManyToOne
	@JoinColumn(name = "politica_colocacao_tv_tp_id")
	public PoliticaColocacaoTipoVendedorTipoProduto getPoliticaColocacaoTipoVendedorTipoProduto() {
		return politicaColocacaoTipoVendedorTipoProduto;
	}

	public void setPoliticaColocacaoTipoVendedorTipoProduto(
			PoliticaColocacaoTipoVendedorTipoProduto politicaColocacaoTipoVendedorTipoProduto) {
		this.politicaColocacaoTipoVendedorTipoProduto = politicaColocacaoTipoVendedorTipoProduto;
	}

	@Transient
	public boolean estaNoIntervalo(int quantidade) {
		if(quantidade >= inicio && quantidade <= fim) {
			return true;
		}
	
		return false;
	}
	
}
