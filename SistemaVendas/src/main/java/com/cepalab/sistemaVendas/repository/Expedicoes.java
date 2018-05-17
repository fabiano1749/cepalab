package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.Expedicao.dominio.StatusExpedicao;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.repository.filter.ExpedicaoFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Expedicoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Expedicao adicionar(Expedicao expedicao) {
		return manager.merge(expedicao);
	}

	public List<Expedicao> expedicoes() {

		return manager.createQuery("from Expedicao", Expedicao.class).getResultList();

	}

	public List<Expedicao> expedicoesAberto() {

		return manager.createQuery("from Expedicao where status=:ABERTO", Expedicao.class)
				.setParameter("ABERTO", StatusExpedicao.ABERTO).getResultList();

	}

	@Transactional
	public void remover(Expedicao expedicao) {
		try {
			expedicao = porId(expedicao.getId());
			manager.remove(expedicao);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Expedicao não pode ser excluída.");
		} catch (Exception ex) {
			throw new NegocioException("Expedicao não pode ser excluída");
		}
	}

	public Expedicao porId(Long id) {
		return manager.find(Expedicao.class, id);
	}

	public Expedicao porId2(Long id) {
		return manager.createQuery("from Expedicao where id= :id", Expedicao.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Expedicao> ExpedicoesFiltradas(ExpedicaoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Expedicao.class);

		if (filtro.getFuncionario() != null) {
			criteria.add(Restrictions.eq("funcionario", filtro.getFuncionario()));
		}

		if (filtro.getStatus() != null) {
			criteria.add(Restrictions.eq("status", filtro.getStatus()));
		}

		if (filtro.getData() != null) {
			criteria.add(Restrictions.between("abertura", filtro.getData(), new Date()));
		}

		return criteria.addOrder(Order.desc("abertura")).list();
	}

	@SuppressWarnings("unchecked")
	public List<Expedicao> ultimasExpedicoesFechadasFuncionario(Funcionario fun) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Expedicao.class);

		if (fun != null) {
			criteria.add(Restrictions.eq("funcionario", fun));
		}

		if (fun != null) {
			criteria.add(Restrictions.eq("status", StatusExpedicao.FECHADO));
		}

		criteria.setMaxResults(3);

		criteria.addOrder(Order.desc("abertura"));

		return criteria.list();
	}

	public Expedicao UltimaAberta(Funcionario fun) {
		StatusExpedicao status = StatusExpedicao.ABERTO;
		List<Expedicao> listaAbertas = manager
				.createQuery("from Expedicao where funcionario= :fun and status =:status", Expedicao.class)
				.setParameter("fun", fun).setParameter("status", status).getResultList();
		if(listaAbertas == null) {
			return null;
		}
		else {
			Expedicao exped = new Expedicao();
			for(Expedicao e : listaAbertas) {
				if(exped.getAbertura() == null) {
					exped = e;
				}
				else if(exped.getAbertura().before(e.getAbertura())) {
					exped = e;
				}
			}
			return exped;
		}
	}
}