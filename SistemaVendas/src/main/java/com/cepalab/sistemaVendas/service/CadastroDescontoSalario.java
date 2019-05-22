package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.repository.DescontosSalarios;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroDescontoSalario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DescontosSalarios descontos;
	
	@Transactional
	public DescontoSalario salvar(DescontoSalario desconto) {
		return descontos.adicionar(desconto);
	}
	
}
