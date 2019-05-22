package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.financeiro.dominio.Parcela;
import com.cepalab.sistemaVendas.repository.Parcelas;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroParcelaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Parcelas parcelas;
	
	@Transactional
	public Parcela salvar(Parcela parcela) {
		
		return parcelas.adicionar(parcela);
		
	}
	
}
