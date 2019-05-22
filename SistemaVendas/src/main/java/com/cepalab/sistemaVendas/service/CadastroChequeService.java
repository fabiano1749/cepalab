package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.financeiro.dominio.Cheque;
import com.cepalab.sistemaVendas.repository.Cheques;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroChequeService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Cheques cheques;
	
	@Transactional
	public Cheque salvar(Cheque cheque) {
		
		return cheques.adicionar(cheque);
		
	}
	
}
