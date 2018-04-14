package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Grupos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Grupo adicionar(Grupo grupo) {
		return manager.merge(grupo);
	}

	public List<Grupo> grupos() {

		return manager.createQuery("from Grupo", Grupo.class).getResultList();

	}

	@Transactional
	public void remover(Grupo grupo) {
		try {
			grupo = porId(grupo.getId());
			manager.remove(grupo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("O grupo não pode ser excluído.");
		}
	}

	public Grupo porNome(String nome) {
		try {
			return manager.createQuery("from Grupo where nome= :nome", Grupo.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

}
