package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.financeiro.dominio.Conta;
import com.cepalab.sistemaVendas.repository.Contas;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroContaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Contas contas;
	
	@Transactional
	public Conta salvar(Conta conta) {
		
		return contas.adicionar(conta);
		
	}
	
}
