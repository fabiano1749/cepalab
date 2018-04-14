package com.cepalab.sistemaVendas.cadastro.dominio;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tipo_produto")
public class TipoProduto extends GenericDTO{

	private static final long serialVersionUID = 1L;
		
	private String nome;
	private BigDecimal comissaoAbertura = BigDecimal.ZERO;
	private BigDecimal premiacaoAbertura = BigDecimal.ZERO;
	private BigDecimal comissaoColocacao = BigDecimal.ZERO;
	private BigDecimal premiacaoColocacao = BigDecimal.ZERO;
	private List<Produto> produtos = new ArrayList<>();
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@Column(nullable=false, length=40)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	

	@Column(precision=10, scale=2, name="comissao_abertura")
	public BigDecimal getComissaoAbertura() {
		return comissaoAbertura;
	}

	public void setComissaoAbertura(BigDecimal comissaoAbertura) {
		this.comissaoAbertura = comissaoAbertura;
	}

	@Column(precision=10, scale=2, name="premiacao_abertura")
	public BigDecimal getPremiacaoAbertura() {
		return premiacaoAbertura;
	}

	public void setPremiacaoAbertura(BigDecimal premiacaoAbertura) {
		this.premiacaoAbertura = premiacaoAbertura;
	}

	@Column(precision=10, scale=2, name="comissao_colocacao")
	public BigDecimal getComissaoColocacao() {
		return comissaoColocacao;
	}

	public void setComissaoColocacao(BigDecimal comissaoColocacao) {
		this.comissaoColocacao = comissaoColocacao;
	}

	@Column(precision=10, scale=2, name="premiacao_colocacao")
	public BigDecimal getPremiacaoColocacao() {
		return premiacaoColocacao;
	}

	public void setPremiacaoColocacao(BigDecimal premiacaoColocacao) {
		this.premiacaoColocacao = premiacaoColocacao;
	}

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	@Transient
	public int quantProdutos() {
		return produtos.size();
	}
	
	
	
}
