package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;


import com.cepalab.sistemaVendas.financeiro.dominio.Transferencia;
import com.cepalab.sistemaVendas.repository.Transferencias;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroTransferenciaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Transferencias transferencias;
	
	@Transactional
	public Transferencia salvar(Transferencia transferencia) {
		
		return transferencias.adicionar(transferencia);
		
	}
	
}
