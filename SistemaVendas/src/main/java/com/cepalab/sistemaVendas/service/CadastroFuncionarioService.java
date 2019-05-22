package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CadastroFuncionarioService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private Funcionarios funcionarios;
	
	@Transactional
	public Funcionario salvar(Funcionario funcionario) {
		 
		Funcionario funcionarioExistente = funcionarios.porNome(funcionario.getNome());
		
		if(funcionarioExistente != null && funcionario.getId() ==null) {
			throw new NegocioException("Já existe um funcionario com o nome informado.");
		}
		
		if(funcionarioExistente != null && !funcionarioExistente.equals(funcionario)) {
			throw new NegocioException("Já existe um funcionario com o nome informado!");
		}
		
		return funcionarios.adicionar(funcionario);
		
	}
	
}
