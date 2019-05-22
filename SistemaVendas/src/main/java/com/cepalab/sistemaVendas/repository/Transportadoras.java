package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Transportadora;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Transportadoras implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Transportadora adicionar(Transportadora transportadora) {
		return manager.merge(transportadora);
	}

	public List<Transportadora> transportadoras() {

		return manager.createQuery("from Transportadora order by nome", Transportadora.class).getResultList();

	}

	@Transactional
	public void remover(Transportadora transportadora) {
		try {
			transportadora = porId(transportadora.getId());
			manager.remove(transportadora);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("A Transportadora não pode ser excluída.");
		}
	}

	public Transportadora porNome(String nome) {
		try {
			return manager.createQuery("from Transportadora where nome= :nome", Transportadora.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Transportadora porId(Long id) {
		return manager.find(Transportadora.class, id);
	}
	
	public Transportadora porId2(Long id) {
		return manager.createQuery("from Transportadora where id=:id", Transportadora.class).setParameter("id", id).getSingleResult();
	}
	
}
