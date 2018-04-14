package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.repository.Rotas;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroRotaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Rotas rotas;
	
	@Transactional
	public Rota salvar(Rota rota) {
		
		return rotas.adicionar(rota);
		
	}
	
}
