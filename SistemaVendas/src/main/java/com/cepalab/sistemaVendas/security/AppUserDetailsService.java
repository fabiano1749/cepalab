package com.cepalab.sistemaVendas.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoFuncionario;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		Funcionarios funcionarios = CDIServiceLocator.getBean(Funcionarios.class);
		
		String cpf = usuario.replace(".", "").replace("-", "");
		Funcionario funcionario = funcionarios.porCpf(cpf);
		
		if(funcionario == null) {
			funcionario = funcionarios.porEmail(usuario.toLowerCase());
		}

		UsuarioSistema user = null;

		if (funcionario != null) {
			user = new UsuarioSistema(funcionario, getGrupos(funcionario.getTipo()));
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(TipoFuncionario tipo) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Grupo grupo : tipo.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
		}

		return authorities;
	}

}




/*
 
 Código Auxiliar para essa classe e tabém para a classe de funcionários.
 Se este código for utilizado a lista de grupos deve ser retirada da classe tipoFuncionário
 
 -------------------------------------------------------------------------------------------
 
 public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		Funcionarios funcionarios = CDIServiceLocator.getBean(Funcionarios.class);
		Funcionario funcionario = funcionarios.porCpf(cpf);

		UsuarioSistema user = null;

		if (funcionario != null) {
			user = new UsuarioSistema(funcionario, getGrupos(funcionario));
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Funcionario funcionario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Grupo grupo : funcionario.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
		}

		return authorities;
	}

}




Dados da tabela de funcionários

private List<Grupo> grupos = new ArrayList<>();

@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "funcionario_grupo", joinColumns = @JoinColumn(name = "funcionario_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

 
 
 
*/