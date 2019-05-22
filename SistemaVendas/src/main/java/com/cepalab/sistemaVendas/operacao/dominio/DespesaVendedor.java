package com.cepalab.sistemaVendas.operacao.dominio;

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

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;

@SuppressWarnings("serial")
@Entity
@Table(name="despesa_vendedor")
public class DespesaVendedor extends GenericDTO{

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private Date data;
	private BigDecimal valor;
	private Funcionario funcionario;
	private TipoDespesa tipoDespesa;
	private String observacao;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
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
	
	@ManyToOne
	@JoinColumn(name="vendedor_id", nullable=false)
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length=20)
	public TipoDespesa getTipoDespesa() {
		return tipoDespesa;
	}
	public void setTipoDespesa(TipoDespesa tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	
	@Column(columnDefinition="text")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
