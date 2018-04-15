package com.cepalab.sistemaVendas.cadastro.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


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

	@OneToMany(mappedBy = "politicaAberturaTipoVendedorTipoProduto", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<IntervaloAberturaTipoProduto> getListaIntervalos() {
		return listaIntervalos;
	}

	public void setListaIntervalos(List<IntervaloAberturaTipoProduto> listaIntervalos) {
		this.listaIntervalos = listaIntervalos;
	}
}
