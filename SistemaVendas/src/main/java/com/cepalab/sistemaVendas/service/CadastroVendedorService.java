package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.repository.Vendedores;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroVendedorService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Vendedores vendedores;

	@Transactional
	public Funcionario salvar(Funcionario vendedor) {

		Funcionario vendedorExistente = vendedores.porNome(vendedor.getNome());

		if (vendedorExistente != null && vendedor.getId() == null) {
			throw new NegocioException("Já existe um vendedor com o nome informado.");
		}

		if (vendedorExistente != null && !vendedorExistente.equals(vendedor)) {
			throw new NegocioException("Já existe um vendedor com o nome informado!");
		}

		return vendedores.adicionar(vendedor);

	}

}
