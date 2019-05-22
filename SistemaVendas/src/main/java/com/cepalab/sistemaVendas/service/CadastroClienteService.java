package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroClienteService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		 
		Cliente clienteExistente = clientes.porCpfCnpj(cliente.getCpfCnpj());
		
		if(clienteExistente != null && cliente.getId() ==null) {
			throw new NegocioException("Já existe um cliente com o CNPJ informado.");
		}
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente com o CNPJ informado!");
		}
		
		return clientes.adicionar(cliente);
		
	}
	
	
}
