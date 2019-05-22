package com.cepalab.sistemaVendas.financeiro.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoTransacao", discriminatorType=DiscriminatorType.STRING)
public abstract class Transacao extends GenericDTO {

	private Date data;
	private BigDecimal valor;
	private String obs;
	private int numParcelas;
	private BigDecimal valorDinheiro;
	private BigDecimal valorCheque;
	private Conta conta;
	private TipoTransacao tipoTransacao;
	private List<Parcela> listaParcelas = new ArrayList<>();
	private List<Cheque> listaCheques = new ArrayList<>();
	
	
	
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	public Long getId() {
		return id;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	@Column(nullable=false, precision = 10, scale = 2)
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public String getObs() {
		return obs;
	}
	
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	public int getNumParcelas() {
		return numParcelas;
	}
	
	public void setNumParcelas(int numParcelas) {
		if(numParcelas <= 0) {
			this.numParcelas = 1;	
		}
		else {
			this.numParcelas = numParcelas;
		}
	}
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "conta_origem")
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@OneToMany(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Parcela> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<Parcela> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "trasacao_cheque", joinColumns = @JoinColumn(name = "transacao_id"), inverseJoinColumns = @JoinColumn(name = "cheque_id"))
	public List<Cheque> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<Cheque> listaCheques) {
		this.listaCheques = listaCheques;
	}
	
	@Column(name="valor_dinheiro")
	public BigDecimal getValorDinheiro() {
		return valorDinheiro;
	}


	public void setValorDinheiro(BigDecimal valorDinheiro) {
		this.valorDinheiro = valorDinheiro;
	}

	@Column(name="valor_cheque")
	public BigDecimal getValorCheque() {
		return valorCheque;
	}


	public void setValorCheque(BigDecimal valorCheque) {
		this.valorCheque = valorCheque;
	}

	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_transacao")
	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}


	@Transient
	public BigDecimal somaDosCheques() {
		BigDecimal soma = BigDecimal.ZERO; 
		
		if(this.listaCheques.isEmpty()) {
			return soma;
		}
		else {
			for(Cheque c : listaCheques) {
				soma = soma.add(c.getValor());
			}
			return soma;
		}
		
	}
	
	@Transient
	public BigDecimal somaDasParcelas() {
		BigDecimal soma = BigDecimal.ZERO; 
		
		if(this.listaParcelas.isEmpty()) {
			return soma;
		}
		else {
			for(Parcela p : listaParcelas) {
				soma = soma.add(p.getValor());
			}
			return soma;
		}
		
	}
	
	
	
	
}
