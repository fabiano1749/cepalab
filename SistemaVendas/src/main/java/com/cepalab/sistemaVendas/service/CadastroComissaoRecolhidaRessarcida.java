package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.repository.ComissoesRecolhidasRessarcidas;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroComissaoRecolhidaRessarcida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ComissoesRecolhidasRessarcidas comissao;
	
	@Transactional
	public ComissaoRecolhidaRessarcida salvar(ComissaoRecolhidaRessarcida comissaoRecolhidaRessarcida) {
		 
		return comissao.adicionar(comissaoRecolhidaRessarcida);
		
	}
	
	
}
