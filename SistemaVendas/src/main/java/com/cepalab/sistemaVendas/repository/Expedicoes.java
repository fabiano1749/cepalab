package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.Expedicao.dominio.StatusExpedicao;
import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
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
		return manager.createQuery("from Expedicao where id= :id", Expedicao.class).setParameter("id", id).getSingleResult();
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

	// Preciso saber como retornar um único registro para não precisar usar o método
	// fechadaMaiorDataPorFuncionario
	public List<Expedicao> listaFechadaFuncionario(Funcionario fun, Date inicio, Date fim) {
		return manager.createQuery(
				"from Expedicao where status=:FECHADO and funcionario=:fun and acerto >=:inicio and acerto <=:fim",
				Expedicao.class).setParameter("FECHADO", StatusExpedicao.FECHADO).setParameter("fun", fun)
				.setParameter("inicio", inicio).setParameter("fim", fim).getResultList();
		// return manager.createNativeQuery("select* from expedicao where abertura =
		// select(MAX(abertura) from Expedicao where status = 'FECHADO' and
		// funcionario_id = 1", Expedicao.class).getSingleResult();
	}

	// Método precisa ser melhorado pois ele é muito ineficiente.
	public Expedicao fechadaMaiorDataPorFuncionario(Funcionario fun, Date inicio, Date fim) {
		List<Expedicao> lista = listaFechadaFuncionario(fun, inicio, fim);
		Expedicao e = null;
		if (lista != null) {
			for (Expedicao ex : lista) {
				if (e == null) {
					e = ex;
				} else if (ex.getAbertura().compareTo(e.getAbertura()) > 0) {
					e = ex;
				}
			}
		}
		return e;
	}

	public Long idUltimaFechadaFuncionario(Funcionario fun) {

		String sql = "select MAX(id) from Expedicao where funcionario=:fun and status=:FECHADO";
		try {
			return manager.createQuery(sql, Long.class).setParameter("FECHADO", StatusExpedicao.FECHADO)
					.setParameter("fun", fun).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Expedicao ultimaFechadaFuncionario(Funcionario fun) {
		Long id = idUltimaFechadaFuncionario(fun);
		String sql = "from Expedicao where id=:id";
		if (id != null) {
			return manager.createQuery(sql, Expedicao.class).setParameter("id", id).getSingleResult();
		}else {
			return null;
		}
	}

	/*
	 * 
	 * @SuppressWarnings("unchecked") public Expedicao
	 * UltimaExpedicaoFechada(Funcionario fun) { Session session =
	 * manager.unwrap(Session.class); Criteria criteria =
	 * session.createCriteria(Expedicao.class);
	 * 
	 * 
	 * criteria.add(Restrictions.eq("funcionario", fun));
	 * 
	 * 
	 * 
	 * criteria.add(Restrictions.eq("status", StatusExpedicao.FECHADO));
	 * 
	 * 
	 * criteria.add(Restrictions.);
	 * 
	 * 
	 * return criteria.addOrder(Order.desc("abertura")).list(); }
	 * 
	 */

}