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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "politica_venda_comissao_tipovendedor_produto")
public class PoliticaVendaConsignacaoTipoVendedorProduto extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	
	private BigDecimal abertura = BigDecimal.ZERO;
	private BigDecimal aberturaPremiacao = BigDecimal.ZERO;
	private BigDecimal colocacao = BigDecimal.ZERO;
	private BigDecimal colocacaoPremiacao = BigDecimal.ZERO;
	private boolean aberturaPorProduto = true;
	private boolean premioAberturaPorProduto = true;
	private boolean colocacaoPorProduto = true;
	private boolean premioColocacaoPorProduto = true;
	private TipoVendedor tipoVendedor;
	private Produto produto = new Produto();
	private List<IntervaloVendaConsignacaoProduto> listaIntervalos = new ArrayList<>();
	
	
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

	@OneToMany(mappedBy = "politicaTipoVendedorProduto", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<IntervaloVendaConsignacaoProduto> getListaIntervalos() {
		return listaIntervalos;
	}

	public void setListaIntervalos(List<IntervaloVendaConsignacaoProduto> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	}
	
	@Column(name = "abertura_por_produto")
	public boolean isAberturaPorProduto() {
		return aberturaPorProduto;
	}

	public void setAberturaPorProduto(boolean aberturaPorProduto) {
		this.aberturaPorProduto = aberturaPorProduto;
	}

	@Column(name = "premiacao_abertura_por_produto")
	public boolean isPremioAberturaPorProduto() {
		return premioAberturaPorProduto;
	}
	
	public void setPremioAberturaPorProduto(boolean premioAberturaPorProduto) {
		this.premioAberturaPorProduto = premioAberturaPorProduto;
	}

	@Column(name = "colocacao_por_produto")
	public boolean isColocacaoPorProduto() {
		return colocacaoPorProduto;
	}

	public void setColocacaoPorProduto(boolean colocacaoPorProduto) {
		this.colocacaoPorProduto = colocacaoPorProduto;
	}

	@Column(name = "premiacao_colocacao_por_produto")
	public boolean isPremioColocacaoPorProduto() {
		return premioColocacaoPorProduto;
	}

	public void setPremioColocacaoPorProduto(boolean premioColocacaoPorProduto) {
		this.premioColocacaoPorProduto = premioColocacaoPorProduto;
	}

	@Transient
	public BigDecimal taxaComissao(int quantidade, boolean prontaEntrega) {
		for(IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if(i.estaNoIntervalo(quantidade)) {
				return i.taxaComissao(prontaEntrega);
			}
		}
		return BigDecimal.ZERO;
	}
	
	@Transient
	public BigDecimal minVenda(int quantidade) {
		for(IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if(i.estaNoIntervalo(quantidade)) {
				return i.getMinVenda();
			}
		}
		return BigDecimal.ZERO;
	}
	
	@Transient
	public BigDecimal minConsignacao(int quantidade) {
		for(IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if(i.estaNoIntervalo(quantidade)) {
				return i.getMinConsignacao();
			}
		}
		return BigDecimal.ZERO;
	}
	
	

}
