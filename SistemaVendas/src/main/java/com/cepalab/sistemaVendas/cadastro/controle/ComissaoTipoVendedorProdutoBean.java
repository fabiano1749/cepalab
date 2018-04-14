package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;


@Named
@ViewScoped
public class ComissaoTipoVendedorProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produto;
	
	@Inject
	private TipoVendedor tipoVendedor;

	private ComissaoTipoVendedorProdutoBean item;
	private List<ComissaoTipoVendedorProdutoBean> items;
	
	
	@PostConstruct
	public void inicio() {

	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public TipoVendedor getTipoVendedor() {
		return tipoVendedor;
	}


	public void setTipoVendedor(TipoVendedor tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}


	public ComissaoTipoVendedorProdutoBean getItem() {
		return item;
	}


	public void setItem(ComissaoTipoVendedorProdutoBean item) {
		this.item = item;
	}


	public List<ComissaoTipoVendedorProdutoBean> getItems() {
		return items;
	}


	public void setItems(List<ComissaoTipoVendedorProdutoBean> items) {
		this.items = items;
	}
	

	
	

}
