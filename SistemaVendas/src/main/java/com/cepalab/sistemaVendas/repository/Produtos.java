package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto adicionar(Produto produto) {
		return manager.merge(produto);
	}

	public List<Produto> produtos() {

		return manager.createQuery("from Produto", Produto.class).getResultList();

	}

	public Produto porNome(String nome) {
		try {
			return manager.createQuery("from Produto where nome= :nome", Produto.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public void remover(Produto produto) {
		try {
		produto = porId(produto.getId());
		manager.remove(produto);
		manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído.");
		}
	}

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}
	
}
