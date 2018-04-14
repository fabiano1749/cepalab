package com.cepalab.sistemaVendas.cadastro.dominio;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="intervalo_valor")
public class IntervaloValor extends GenericDTO{

	private static final long serialVersionUID = 1L;
		
	private int inicio;
	private int fim;
	private BigDecimal valor;
	
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@Column(nullable=false)
	public int getInicio() {
		return inicio;
	}
	
	@Column(nullable=false)
	public int getFim() {
		return fim;
	}
		
	@Column(precision=10, scale=2)
	public BigDecimal getValor() {
		return valor;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public void setFim(int fim) {
		this.fim = fim;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
