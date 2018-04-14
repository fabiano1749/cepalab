package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.Transportadora;
import com.cepalab.sistemaVendas.repository.Transportadoras;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroTransportadoraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Transportadoras transportadoras;
	
	@Transactional
	public Transportadora salvar(Transportadora transportadora) {
		
		return transportadoras.adicionar(transportadora);
		
	}
	
}
