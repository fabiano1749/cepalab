package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.financeiro.dominio.Banco;
import com.cepalab.sistemaVendas.repository.Bancos;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroBancoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Bancos bancos;
	
	@Transactional
	public Banco salvar(Banco banco) {
		
		return bancos.adicionar(banco);
		
	}
	
}
