package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;
import com.cepalab.sistemaVendas.repository.filter.RecebimentoInadimplenteFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class RecebimentosInadiplentes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public RecebimentoInadiplente adicionar(RecebimentoInadiplente recebimento) {
		return manager.merge(recebimento);
	}

	public List<RecebimentoInadiplente> recebimentos() {

		return manager.createQuery("from RecebimentoInadiplente", RecebimentoInadiplente.class).getResultList();

	}

	public List<RecebimentoInadiplente> porFuncionario(Funcionario fun, Date inicio, Date fim) {

		try {
			return manager.createQuery(
					"FROM RecebimentoInadiplente  WHERE funcionario= :fun AND data >= :inicio AND data <= :fim ORDER BY data desc",
					RecebimentoInadiplente.class).setParameter("fun", fun).setParameter("inicio", inicio)
					.setParameter("fim", fim).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public RecebimentoInadiplente porId(Long id) {
		try {
			return manager
					.createQuery("from RecebimentoInadiplente where id= :id", RecebimentoInadiplente.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<RecebimentoInadiplente> recebimentosFiltrados(RecebimentoInadimplenteFilter filtro) {
		try {
		return manager
				.createQuery("from RecebimentoInadiplente where funcionario= :fun and data >= :inicio and data <= :fim order by data desc",
						RecebimentoInadiplente.class)
				.setParameter("fun", filtro.getFuncionario()).setParameter("inicio", filtro.getInicio()).setParameter("fim", filtro.getFim()).getResultList();

		}catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public void remover(RecebimentoInadiplente recebimento) {
		try {
			recebimento = porId(recebimento.getId());
			manager.remove(recebimento);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("O recebimento inadimplente não pode ser excluído.");
		}
	}
	
}
