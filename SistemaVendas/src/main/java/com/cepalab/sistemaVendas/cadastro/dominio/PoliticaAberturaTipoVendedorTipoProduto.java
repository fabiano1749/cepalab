package com.cepalab.sistemaVendas.cadastro.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;


@SuppressWarnings("serial")
@Entity
@Table(name = "politica_abertura_tipovendedor_tipo_produto")
public class PoliticaAberturaTipoVendedorTipoProduto extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private TipoVendedor tipoVendedor;
	private TipoProduto tipoProduto;
	private List<IntervaloAberturaTipoProduto> listaIntervalos = new ArrayList<>();
	private int quantMinimaPorProduto;
	
	@ManyToOne
	@JoinColumn(name = "tipo_vendedor_id", nullable = false)
	public TipoVendedor getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(TipoVendedor tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	@ManyToOne
	@JoinColumn(name = "tipo_produto_id", nullable = false)
	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	@OneToMany(mappedBy = "politicaAberturaTipoVendedorTipoProduto", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<IntervaloAberturaTipoProduto> getListaIntervalos() {
		return listaIntervalos;
	}

	public void setListaIntervalos(List<IntervaloAberturaTipoProduto> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	}
	
	@Column(name = "quant_minima_por_produto")
	public int getQuantMinimaPorProduto() {
		return quantMinimaPorProduto;
	}

	public void setQuantMinimaPorProduto(int quantMinimaPorProduto) {
		this.quantMinimaPorProduto = quantMinimaPorProduto;
	}

	@Transient
	public boolean adicionaIntervaloAbertura(IntervaloAberturaTipoProduto intervalo) {
		//Verifica se o novo intervalo esta dentro de um intervalo maior.
		if (listaIntervalos.size() != 0) {
			for (IntervaloAberturaTipoProduto i : listaIntervalos) {
				if (i.estaNoIntervalo(intervalo.getInicio())  || i.estaNoIntervalo(intervalo.getFim())) {
					return false;
				}
			}
		}
		//Verifica se o novo intervalo engloba os demais
		if(listaIntervalos.size() != 0) {
			for (IntervaloAberturaTipoProduto i : listaIntervalos) {
				if(i.getInicio() >= intervalo.getInicio() && i.getFim() <= intervalo.getFim()) {
					return false;
				}
			}
		}
		
		listaIntervalos.add(intervalo);
		return true;
	}
	
	//Retorna a comissão dado um intervalo
	public BigDecimal comissao(int quantidade) {
		for(IntervaloAberturaTipoProduto i : listaIntervalos) {
			if(i.estaNoIntervalo(quantidade)) {
				return i.getValor();
			}
		}
		return BigDecimal.ZERO;
		
	}
	
	//Retorna a premiacao dado um intervalo
		public BigDecimal premiacao(int quantidade) {
			for(IntervaloAberturaTipoProduto i : listaIntervalos) {
				if(i.estaNoIntervalo(quantidade)) {
					return i.getPremiacao();
				}
			}
			return BigDecimal.ZERO;
			
		}
	
	
	
	
}
