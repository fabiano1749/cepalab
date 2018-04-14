package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;
import com.cepalab.sistemaVendas.repository.RecebimentosInadiplentes;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroRecebimentoInadiplente implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private RecebimentosInadiplentes recebimentos;
	
	@Transactional
	public RecebimentoInadiplente salvar(RecebimentoInadiplente recebimento) {
		
		return recebimentos.adicionar(recebimento);
		
	}

}
