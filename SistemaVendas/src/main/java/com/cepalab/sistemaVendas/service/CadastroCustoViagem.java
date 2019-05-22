package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroCustoViagem implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private CustosViagens custos;
	
	@Transactional
	public CustoViagem salvar(CustoViagem custo) {
		
		return custos.adicionar(custo);
		
	}

}
