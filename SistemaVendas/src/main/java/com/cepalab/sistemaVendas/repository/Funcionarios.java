package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.StatusVendedor;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoFuncionario;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Funcionarios implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Funcionario adicionar(Funcionario funcionario) {
		return manager.merge(funcionario);
	}

	public List<Funcionario> funcionarios() {

		return manager.createQuery("from Funcionario order by nome", Funcionario.class).getResultList();

	}

		
	
	public Funcionario porNome(String nome) {
		try {
			return manager.createQuery("from Funcionario where nome= :nome", Funcionario.class)
					.setParameter("nome", nome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Funcionario porCpf(String cpf) {
		Funcionario funcionario = null;

		try {
			funcionario = this.manager.createQuery("from Funcionario where lower(cpf)= :cpf", Funcionario.class)
					.setParameter("cpf", cpf).getSingleResult();
		} catch (NoResultException e) {
			//Nenhum Funcionario com o cpf foi encontrado
		}
		
		return funcionario;
	}
	
	public Funcionario porEmail(String email) {
		Funcionario funcionario = null;

		try {
			funcionario = this.manager.createQuery("from Funcionario where lower(email)= :email", Funcionario.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {

		}
		
		return funcionario;
	}


	public List<Funcionario> porTipo(TipoFuncionario tipo) {

		try {
			return manager.createQuery("from Funcionario where tipo= :tipo", Funcionario.class)
					.setParameter("tipo", tipo).getResultList();
		} catch (NoResultException e) {

		}
		
		return null;
	}

	public List<Funcionario> vendedor() {

		List<Funcionario> lista = funcionarios();
		ListIterator<Funcionario> it = lista.listIterator();
		while(it.hasNext()) {
			if(it.next().getTipoVendedor().getNome().equals("Interno-0")) {
				it.remove();
			}
		}
		return lista;
	}

	public List<Funcionario> vendedorAtivo() {

		List<Funcionario> lista = funcionarios();
		ListIterator<Funcionario> it = lista.listIterator();
		while(it.hasNext()) {
			Funcionario f = it.next(); 
			
			if(f.getTipoVendedor().getNome().equals("Interno-0") || f.getStatus() != StatusVendedor.ATIVO) {
				it.remove();
			}
			
		}
		return lista;
	}
	
	
	
	@Transactional
	public void remover(Funcionario funcionario) {
		try {
			funcionario = porId(funcionario.getId());
			manager.remove(funcionario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Funcionario não pode ser excluído.");
		}
	}

	public Funcionario porId(Long id) {
		return manager.find(Funcionario.class, id);
	}

	public Funcionario porId2(Long id) {
		return manager.createQuery("from Funcionario where id= :id", Funcionario.class).setParameter("id", id).getSingleResult();
	}


	
	
}
