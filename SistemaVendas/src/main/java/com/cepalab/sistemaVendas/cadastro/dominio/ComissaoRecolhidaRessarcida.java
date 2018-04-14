package com.cepalab.sistemaVendas.cadastro.dominio;


import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
@Table(name = "comissao_ressarcida_devolvida")
public class ComissaoRecolhidaRessarcida extends GenericDTO {

	private Date data;
	private BigDecimal valor;
	private RecolhidaRessarcida tipo;
	private Funcionario funcionario;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(nullable=false, precision=10, scale=2)
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length = 20)
	public RecolhidaRessarcida getTipo() {
		return tipo;
	}

	public void setTipo(RecolhidaRessarcida tipo) {
		this.tipo = tipo;
	}

	@ManyToOne
	@JoinColumn(name="funcionario_id", nullable=false)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
