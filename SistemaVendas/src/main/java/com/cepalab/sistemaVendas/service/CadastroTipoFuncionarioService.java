package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoFuncionario;
import com.cepalab.sistemaVendas.repository.TiposFuncionarios;
import com.cepalab.sistemaVendas.util.jpa.Transactional;


public class CadastroTipoFuncionarioService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private TiposFuncionarios tipos;
	
	@Transactional
	public TipoFuncionario salvar(TipoFuncionario tipo) {
		 
		TipoFuncionario tipoExistente = tipos.porNome(tipo.getNome());
		if( tipoExistente != null && tipo.getId() ==null) {
			throw new NegocioException("Já existe um tipo de funcionário com o nome informado.");
		}
		
		if(tipoExistente != null && !tipoExistente.equals(tipo)) {
			throw new NegocioException("Já existe um tipo de funcionário com o nome informado.");
		}
		
		
		return tipos.adicionar(tipo);
		
	}
	
}
	

