package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class TiposProdutos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TipoProduto adicionar(TipoProduto tipo) {
		return manager.merge(tipo);
	}

	public List<TipoProduto> tipos() {

		return manager.createQuery("from TipoProduto", TipoProduto.class).getResultList();

	}

	@Transactional
	public void remover(TipoProduto tipo) {
		try {
			tipo = porId(tipo.getId());
			manager.remove(tipo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("TipoProduto não pode ser excluído.");
		}
	}

	public TipoProduto porId(Long id) {
		return manager.find(TipoProduto.class, id);
	}

	public TipoProduto porId2(Long id) {
		return manager.createQuery("from TipoProduto where id= :id", TipoProduto.class).setParameter("id", id)
				.getSingleResult();
	}

	public TipoProduto porNome(String nome) {
		if (nome == null) {
			nome = "";
		}
		try {
			return manager.createQuery("from TipoProduto where nome= :nome", TipoProduto.class)
					.setParameter("nome", nome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
