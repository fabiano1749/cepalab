package com.cepalab.sistemaVendas.operacao.dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;

@SuppressWarnings("serial")
@Entity
@Table(name="custos_viagem")
public class CustoViagem extends GenericDTO {

	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	private Date data;
	private BigDecimal valor;
	private String observacao;
	private TipoCustosViagem tipo;
	private Funcionario funcionario;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data", nullable=false)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	@Column(name="valor", precision=10, scale=2, nullable=false)
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Column(columnDefinition="text")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	public TipoCustosViagem getTipo() {
		return tipo;
	}
	public void setTipo(TipoCustosViagem tipo) {
		this.tipo = tipo;
	}
	
	@ManyToOne
	@JoinColumn(name="vendedor_id", nullable=false)
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
}
