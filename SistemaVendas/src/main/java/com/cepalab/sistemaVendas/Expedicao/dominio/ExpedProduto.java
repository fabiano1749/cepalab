package com.cepalab.sistemaVendas.Expedicao.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

@SuppressWarnings("serial")
@Entity
@Table(name="exped_produto")
public class ExpedProduto extends GenericDTO{
	private Expedicao expedicao;
	private Produto produto;
	private int saida;
	private int chegada;
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToOne
	@JoinColumn(name = "produto_id", nullable=false)
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getSaida() {
		return saida;
	}

	public void setSaida(int saida) {
		this.saida = saida;
	}

	public int getChegada() {
		return chegada;
	}

	public void setChegada(int chegada) {
		this.chegada = chegada;
	}

	@ManyToOne
	@JoinColumn(name = "expedicao_id", nullable = false)
	public Expedicao getExpedicao() {
		return expedicao;
	}

	public void setExpedicao(Expedicao expedicao) {
		this.expedicao = expedicao;
	}
}
