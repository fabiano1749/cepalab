package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.repository.TiposVendedores;

@Named
@ViewScoped
public class TesteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	TipoVendedor tipo = new TipoVendedor();
	
	@Inject
	private TiposVendedores tipos;
	
	@PostConstruct
	public void inicio() {
		tipo = tipos.porNome("Vendedor-1");
	}

	/*
	public void teste() {
		
		for(PoliticaVendaConsignacaoTipoVendedorProduto c : tipo.getComissaoTipoVendedor()) {
			System.out.println(c.getProduto().getNome() + " "+c.getTaxaComissao());
		}
	}
	
	*/
}
