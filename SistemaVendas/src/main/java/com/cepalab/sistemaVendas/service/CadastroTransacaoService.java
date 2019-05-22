package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.financeiro.dominio.Transacao;
import com.cepalab.sistemaVendas.repository.Transacoes;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroTransacaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Transacoes transacoes;
	
	@Transactional
	public Transacao salvar(Transacao transacao) {
		
		return transacoes.adicionar(transacao);
		
	}
	
}
