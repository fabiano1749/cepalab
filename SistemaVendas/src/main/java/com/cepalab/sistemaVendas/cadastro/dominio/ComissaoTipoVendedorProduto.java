package com.cepalab.sistemaVendas.cadastro.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "comissao_tipovendedor_produto")
public class ComissaoTipoVendedorProduto extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private BigDecimal taxaComissao = BigDecimal.ZERO;
	private BigDecimal taxaComissaoEnvio = BigDecimal.ZERO;
	private BigDecimal abertura = BigDecimal.ZERO;
	private BigDecimal aberturaPremiacao = BigDecimal.ZERO;
	private BigDecimal colocacao = BigDecimal.ZERO;
	private BigDecimal colocacaoPremiacao = BigDecimal.ZERO;
	private TipoVendedor tipoVendedor;
	private Produto produto = new Produto();

	@Column(name = "taxa_comissao", nullable = false, precision = 10, scale = 2)
	public BigDecimal getTaxaComissao() {
		return taxaComissao;
	}

	public void setTaxaComissao(BigDecimal taxaComissao) {
		this.taxaComissao = taxaComissao;
	}

	@Column(name = "taxa_comissao_Envio", nullable = false, precision = 10, scale = 2)
	public BigDecimal getTaxaComissaoEnvio() {
		return taxaComissaoEnvio;
	}

	public void setTaxaComissaoEnvio(BigDecimal taxaComissaoEnvio) {
		this.taxaComissaoEnvio = taxaComissaoEnvio;
	}

	@ManyToOne
	@JoinColumn(name = "tipo_vendedor_id", nullable = false)
	public TipoVendedor getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(TipoVendedor tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getAbertura() {
		return abertura;
	}

	public void setAbertura(BigDecimal abertura) {
		this.abertura = abertura;
	}

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getAberturaPremiacao() {
		return aberturaPremiacao;
	}

	public void setAberturaPremiacao(BigDecimal aberturaPremiacao) {
		this.aberturaPremiacao = aberturaPremiacao;
	}

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getColocacao() {
		return colocacao;
	}

	public void setColocacao(BigDecimal colocacao) {
		this.colocacao = colocacao;
	}

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getColocacaoPremiacao() {
		return colocacaoPremiacao;
	}

	public void setColocacaoPremiacao(BigDecimal colocacaoPremiacao) {
		this.colocacaoPremiacao = colocacaoPremiacao;
	}
	

}
