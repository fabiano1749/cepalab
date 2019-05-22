package com.cepalab.sistemaVendas.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;

	public UsuarioSistema(Funcionario funcionario, Collection<? extends GrantedAuthority> authorities) {
		super(funcionario.getCpf(), funcionario.getSenha(), authorities);
		this.funcionario = funcionario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

}
