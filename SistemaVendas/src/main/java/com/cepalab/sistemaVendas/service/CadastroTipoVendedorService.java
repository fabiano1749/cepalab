package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.repository.TiposVendedores;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroTipoVendedorService implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Inject
	private TiposVendedores tipos;
	
	@Transactional
	public TipoVendedor salvar(TipoVendedor tipo) {
		 
		TipoVendedor tipoExistente = tipos.porNome(tipo.getNome());
		
		if(tipoExistente != null && tipo.getId() ==null) {
			throw new NegocioException("Já existe um tipo de vendedor com o nome informado.");
		}
		
		if(tipoExistente != null && !tipoExistente.equals(tipo)) {
			throw new NegocioException("Já existe um tipo de vendedor com o nome informado.");
		}
		
		return tipos.adicionar(tipo);
		
	}
	

}
