package com.cepalab.sistemaVendas.financeiro.dominio;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;

@SuppressWarnings("serial")
@Entity
@Table(name = "conta")
public class Conta extends GenericDTO {

	private String nome;
	private String obs;
	private TipoConta tipo;
	private List<Transacao> listaTransacoes;
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	public Long getId() {
		return id;
	}

	@Column(nullable = false)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Enumerated(EnumType.STRING)
	public TipoConta getTipo() {
		return tipo;
	}

	public void setTipo(TipoConta tipo) {
		this.tipo = tipo;
	}

	public String getObs() {
		return obs;
	}
	
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Transacao> getListaTransacoes() {
		return listaTransacoes;
	}
	
	public void setListaTransacoes(List<Transacao> listaTransacoes) {
		this.listaTransacoes = listaTransacoes;
	}
	
	public BigDecimal saldo() {
		BigDecimal saldo = BigDecimal.ZERO;
		
		if(listaTransacoes != null && listaTransacoes.size() > 0) {
				
		}
		return saldo;
	}
	
}
