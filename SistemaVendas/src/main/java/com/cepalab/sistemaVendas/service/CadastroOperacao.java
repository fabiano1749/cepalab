package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroOperacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Operacoes operacoes;
	
	@Transactional
	public Operacao salvar(Operacao operacao) {
		
		return operacoes.adicionar(operacao);
		
	}
	
}
