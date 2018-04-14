package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Vendedores implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Funcionario adicionar(Funcionario vendedor) {
		return manager.merge(vendedor);
	}

	public List<Funcionario> vendedors() {

		return manager.createQuery("from Vendedor", Funcionario.class).getResultList();

	}

	public Funcionario porNome(String nome) {
		try {
			return manager.createQuery("from Vendedor where nome= :nome", Funcionario.class)
					.setParameter("nome", nome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public void remover(Funcionario vendedor) {
		try {
			vendedor = porId(vendedor.getId());
			manager.remove(vendedor);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Vendedor não pode ser excluído.");
		}
	}

	public Funcionario porId(Long id) {
		return manager.find(Funcionario.class, id);
	}

}
