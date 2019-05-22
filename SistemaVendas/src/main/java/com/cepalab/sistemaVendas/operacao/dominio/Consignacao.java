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
@Table(name = "consignacao")
public class Consignacao extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private Long consignados = 0L;
	private BigDecimal valorUnitario;
	private int vendidos = 0;
	private Boolean prontaEntrega = true;
	private BigDecimal taxaComissao = BigDecimal.ZERO;
	private Boolean nota = true;
	private Boolean notaEmitida;
	private int devolvidos = 0;
	private Produto produto = new Produto();
	private Operacao operacao;
	private Long totalConsignado = 0L;
	private Long totalAux = 0L;
	

	@Transient
	public BigDecimal receita() {
		return valorUnitario.multiply(new BigDecimal(vendidos));
	}
	
	@Transient
	public BigDecimal comissao() {
		return receita().multiply(taxaComissao).divide(new BigDecimal("100"));
	}
	

	@Column(nullable = false, length = 3)
	public Long getConsignados() {
		return consignados;
	}

	public void setConsignados(Long consignados) {
		this.consignados = consignados;
	}

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	@Column(length = 3)
	public int getVendidos() {
		return vendidos;
	}

	public void setVendidos(int vendidos) {
		this.vendidos = vendidos;
	}

	@Column(nullable = false, name = "pronta_entrega", length = 4)
	public Boolean getProntaEntrega() {
		return prontaEntrega;
	}

	public void setProntaEntrega(Boolean prontaEntrega) {
		this.prontaEntrega = prontaEntrega;
	}

	@Column(name = "taxa_comissao", precision = 10, scale = 2)
	public BigDecimal getTaxaComissao() {
		return taxaComissao;
	}

	public void setTaxaComissao(BigDecimal taxaComissao) {
		this.taxaComissao = taxaComissao;
	}

	public Boolean getNota() {
		return nota;
	}

	public void setNota(Boolean nota) {
		this.nota = nota;
	}

	@Column(name = "nota_emitida")
	public Boolean getNotaEmitida() {
		return notaEmitida;
	}

	public void setNotaEmitida(Boolean notaEmitida) {
		this.notaEmitida = notaEmitida;
	}

	@Column(length = 4)
	public int getDevolvidos() {
		return devolvidos;
	}

	public void setDevolvidos(int devolvidos) {
		this.devolvidos = devolvidos;
	}

	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@ManyToOne
	@JoinColumn(name = "operacao_id", nullable = false)
	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	@Column(name = "total_consignado")
	public Long getTotalConsignado() {
		return totalConsignado;
	}

	public void setTotalConsignado(Long totalConsignado) {
		this.totalConsignado = totalConsignado;
	}

	public Long getTotalAux() {
		return totalAux;
	}

	public void setTotalAux(Long totalAux) {
		this.totalAux = totalAux;
	}

}
