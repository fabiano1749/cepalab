package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.repository.Grupos;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroGrupoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		 
		Grupo grupoExistente = grupos.porNome(grupo.getNome());
		
		if(grupoExistente != null && grupo.getId() ==null) {
			throw new NegocioException("Já existe um grupo com o nome informado!");
		}
		
		if(grupoExistente != null && !grupoExistente.equals(grupo)) {
			throw new NegocioException("Já existe um grupo com o nome informado!");
		}
		
		return grupos.adicionar(grupo);
		
	}
}
