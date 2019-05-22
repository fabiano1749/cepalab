package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class PoliticasVendasConsignacoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public PoliticaVendaConsignacaoTipoVendedorProduto adicionar(PoliticaVendaConsignacaoTipoVendedorProduto politica) {
		return manager.merge(politica);
	}

	public List<PoliticaVendaConsignacaoTipoVendedorProduto> politicas() {

		return manager.createQuery("from PoliticaVendaConsignacaoTipoVendedorProduto",
				PoliticaVendaConsignacaoTipoVendedorProduto.class).getResultList();

	}

	@Transactional
	public void remover(PoliticaVendaConsignacaoTipoVendedorProduto politica) {
		try {
			politica = porId(politica.getId());
			manager.remove(politica);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("A Politica não pode ser excluída.");
		}
	}

	public PoliticaVendaConsignacaoTipoVendedorProduto porId(Long id) {
		return manager.find(PoliticaVendaConsignacaoTipoVendedorProduto.class, id);
	}

	public PoliticaVendaConsignacaoTipoVendedorProduto porId2(Long id) {
		return manager.createQuery("from PoliticaVendaConsignacaoTipoVendedorProduto where id=:id",
				PoliticaVendaConsignacaoTipoVendedorProduto.class).setParameter("id", id).getSingleResult();
	}

}
