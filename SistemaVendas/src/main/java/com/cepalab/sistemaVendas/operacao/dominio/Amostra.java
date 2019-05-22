package com.cepalab.sistemaVendas.operacao.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

@SuppressWarnings("serial")
@Entity
@Table(name="amostra")
public class Amostra extends GenericDTO{

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private int quantidade;
	private String observacao;
	private Produto produto;
	private Operacao operacao;
	
	@Column(nullable = false, length = 3)
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@ManyToOne
	@JoinColumn(name="produto_id", nullable=false)
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
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
