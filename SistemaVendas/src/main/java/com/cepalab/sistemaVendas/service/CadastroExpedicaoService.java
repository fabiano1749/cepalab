package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.repository.Expedicoes;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroExpedicaoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Expedicoes expedicoes;
	
	@Transactional
	public Expedicao salvar(Expedicao expedicao) {
		 
		return expedicoes.adicionar(expedicao);
		
	}
	
	
}
