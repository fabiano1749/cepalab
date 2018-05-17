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

		return manager.createQuery("from Produto order by posicao", Produto.class).getResultList();

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
		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído.");
		}
	}

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}

	public Produto porPosicao(int posicao) {
		try {
			return manager.createQuery("from Produto where posicao =:posicao", Produto.class)
					.setParameter("posicao", posicao).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public int ultimaPosicao() {
		List<Produto> produtos = manager.createQuery("from Produto order by pocicao desc", Produto.class)
				.getResultList();

		if (produtos != null) {
			return produtos.get(0).getPosicao();
		}
		return 0;
	}
}
