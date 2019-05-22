package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.repository.TiposProdutos;
import com.cepalab.sistemaVendas.util.jpa.Transactional;


public class CadastroTipoProdutoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private TiposProdutos tipos;
	
	@Transactional
	public TipoProduto salvar(TipoProduto tipo) {
		
		
		TipoProduto tipoExistente = tipos.porNome(tipo.getNome());
		if( tipoExistente != null && tipo.getId() ==null) {
			throw new NegocioException("Já existe um tipo de funcionário com o nome informado.");
		}
		
		if(tipoExistente != null && !tipoExistente.equals(tipo)) {
			throw new NegocioException("Já existe um tipo de funcionário com o nome informado.");
		}
		
		
		return tipos.adicionar(tipo);
		
	}
	
}
	

