package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.financeiro.dominio.PagamentoRecebimento;
import com.cepalab.sistemaVendas.repository.PagamentosRecebimentos;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroPagamentoRecebimentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentosRecebimentos pagamentosRecebimentos;
	
	@Transactional
	public PagamentoRecebimento salvar(PagamentoRecebimento pagReceb) {
		
		return pagamentosRecebimentos.adicionar(pagReceb);
		
	}
	
}
