package com.cepalab.sistemaVendas.cadastro.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
@Table(name = "politica_venda_consignacao_tipovendedor_produto")
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
	private TipoCalculoAberturaColocacao tipoAbertura = TipoCalculoAberturaColocacao.PRODUTO;
	private TipoCalculoAberturaColocacao tipoPremioAbertura = TipoCalculoAberturaColocacao.PRODUTO;
	private TipoCalculoAberturaColocacao tipoColocacao = TipoCalculoAberturaColocacao.PRODUTO;
	private TipoCalculoAberturaColocacao tipoPremioColocacao = TipoCalculoAberturaColocacao.PRODUTO;
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

	@OneToMany(mappedBy = "politicaTipoVendedorProduto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	public List<IntervaloVendaConsignacaoProduto> getListaIntervalos() {
		return listaIntervalos;
	}

	public void setListaIntervalos(List<IntervaloVendaConsignacaoProduto> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false, name = "tipo_abertura")
	public TipoCalculoAberturaColocacao getTipoAbertura() {
		return tipoAbertura;
	}

	public void setTipoAbertura(TipoCalculoAberturaColocacao tipoAbertura) {
		this.tipoAbertura = tipoAbertura;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false, name = "tipo_premio_abertura")
	public TipoCalculoAberturaColocacao getTipoPremioAbertura() {
		return tipoPremioAbertura;
	}

	public void setTipoPremioAbertura(TipoCalculoAberturaColocacao tipoPremioAbertura) {
		this.tipoPremioAbertura = tipoPremioAbertura;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false, name = "tipo_colocacao")
	public TipoCalculoAberturaColocacao getTipoColocacao() {
		return tipoColocacao;
	}

	public void setTipoColocacao(TipoCalculoAberturaColocacao tipoColocacao) {
		this.tipoColocacao = tipoColocacao;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false, name = "tipo_premio_colocacao")
	public TipoCalculoAberturaColocacao getTipoPremioColocacao() {
		return tipoPremioColocacao;
	}

	public void setTipoPremioColocacao(TipoCalculoAberturaColocacao tipoPremioColocacao) {
		this.tipoPremioColocacao = tipoPremioColocacao;
	}

	@Transient
	public BigDecimal taxaComissao(int quantidade, boolean prontaEntrega) {
		for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if (i.estaNoIntervalo(quantidade)) {
				return i.taxaComissao(prontaEntrega);
			}
		}
		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal taxaComissaoConsignacao(int quantidade) {
		for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if (i.estaNoIntervalo(quantidade)) {
				return i.getComissaoProntaEntrega();
			}
		}
		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal minVenda(int quantidade) {
		for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if (i.estaNoIntervalo(quantidade)) {
				return i.getMinVenda();
			}
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal menorValorConsignacao() {
		BigDecimal retorno = BigDecimal.ZERO;
		
			for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
				if (retorno.compareTo(BigDecimal.ZERO) == 0) {
					retorno = i.getMinConsignacao();
				}

				if (retorno.compareTo(i.getMinConsignacao()) > 0) {
					retorno = i.getMinConsignacao();
				}
			}
		
		return retorno;
	}

	@Transient
	public BigDecimal minConsignacao(int quantidade) {
		if(listaIntervalos == null) {
			return BigDecimal.ZERO;
		}
		else {
			
		for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if (i.estaNoIntervalo(quantidade)) {

				return i.getMinConsignacao();
			}
		}
		
		return menorValorConsignacao();
		
		}
	}

	@Transient
	public boolean adicionaIntervalo(IntervaloVendaConsignacaoProduto intervalo) {
		//Verifica se o novo intervalo esta dentro de um intervalo maior.
		if (listaIntervalos.size() != 0) {
			for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
				if (i.estaNoIntervalo(intervalo.getInicio())  || i.estaNoIntervalo(intervalo.getFim())) {
					return false;
				}
			}
		}
		//Verifica se o novo intervalo engloba os demais
		if(listaIntervalos.size() != 0) {
			for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
				if(i.getInicio() >= intervalo.getInicio() && i.getFim() <= intervalo.getFim()) {
					return false;
				}
			}
		}
		
		listaIntervalos.add(intervalo);
		return true;
	}

	@Transient
	public BigDecimal maiorTaxaComissao() {
		BigDecimal maior = BigDecimal.ZERO;
		for (IntervaloVendaConsignacaoProduto i : listaIntervalos) {
			if (i.getComissaoProntaEntrega().compareTo(maior) > 0) {
				maior = i.getComissaoProntaEntrega();
			}
		}
		return maior;
	}
}
