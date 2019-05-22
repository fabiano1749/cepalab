package com.cepalab.sistemaVendas.operacao.dominio;

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

import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

@SuppressWarnings("serial")
@Entity
@Table(name="venda")
public class Venda extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private int quantidade = 0;
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	private boolean prontaEntrega = true;
	private BigDecimal taxaComissao = BigDecimal.ZERO;
	private Boolean nota = true;
	private Boolean notaEmitida = false;
	private Produto produto = new Produto();
	private Operacao operacao;
	private Long totalVendidos = 0L;
	private Long devolvidos = 0L;
	private Long repostos =0L; 
	
	
	
	@Transient
	public BigDecimal receita() {
		return valorUnitario.multiply(new BigDecimal(quantidade));
	}
	
	@Transient
	public BigDecimal comissao() {
		return receita().multiply(taxaComissao).divide(new BigDecimal("100"));
	}
	
	@Column(nullable = false, length = 3)
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@Column(nullable=false, name="pronta_entrega")
	public Boolean getProntaEntrega() {
		return prontaEntrega;
	}
	public void setProntaEntrega(Boolean prontaEntrega) {
		this.prontaEntrega = prontaEntrega;
	}
	
	@Column(name="taxa_comissao", nullable = false, precision = 10, scale = 2)
	public BigDecimal getTaxaComissao() {
		return taxaComissao;
	}
	public void setTaxaComissao(BigDecimal taxaComissao) {
		this.taxaComissao = taxaComissao;
	}
	
	
	@Column(nullable=false)
	public Boolean getNota() {
		return nota;
	}
	public void setNota(Boolean nota) {
		this.nota = nota;
	}
	
	
	@Column(name="nota_emitida", nullable=false)
	public Boolean getNotaEmitida() {
		return notaEmitida;
	}
	public void setNotaEmitida(Boolean notaEmitida) {
		this.notaEmitida = notaEmitida;
	}
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable=false)
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
	public Long getTotalVendidos() {
		return totalVendidos;
	}
	public void setTotalVendidos(Long totalVendidos) {
		this.totalVendidos = totalVendidos;
	}
	public Long getDevolvidos() {
		return devolvidos;
	}
	public void setDevolvidos(Long devolvidos) {
		this.devolvidos = devolvidos;
	}
	public Long getRepostos() {
		return repostos;
	}
	public void setRepostos(Long repostos) {
		this.repostos = repostos;
	}

}



