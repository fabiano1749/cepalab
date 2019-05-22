package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.repository.DespesasVendedores;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroDespesaVendedor implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private DespesasVendedores despesas;
	
	@Transactional
	public DespesaVendedor salvar(DespesaVendedor despesa) {
		return despesas.adicionar(despesa);
	}
}
