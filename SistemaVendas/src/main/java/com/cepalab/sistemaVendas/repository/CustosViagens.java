package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.repository.filter.CustosViagemFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class CustosViagens implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public CustoViagem adicionar(CustoViagem custo) {
		return manager.merge(custo);
	}

	public List<CustoViagem> custos() {

		return manager.createQuery("from CustoViagem", CustoViagem.class).getResultList();

	}

	public CustoViagem porNome(String nome) {
		try {
			return manager.createQuery("from CustoViagem where nome= :nome", CustoViagem.class)
					.setParameter("nome", nome).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public void remover(CustoViagem custo) {
		try {
			custo = porId(custo.getId());
			manager.remove(custo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("O custo viagem não pode ser excluído.");
		}
	}

	public CustoViagem porId(Long id) {
		return manager.find(CustoViagem.class, id);
	}

	public CustoViagem porId2(Long id) {
		return manager.createQuery("from CustoViagem where id= :id", CustoViagem.class).setParameter("id", id).getSingleResult();
	}
	
	
	public List<CustoViagem> porFuncionario(Funcionario fun, Date inicio, Date fim) {

		try {
			return manager.createQuery(
					"FROM CustoViagem  WHERE funcionario= :fun AND data >= :inicio AND data <= :fim ORDER BY tipo",
					CustoViagem.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<CustoViagem> porFuncionarioFiltrados(CustosViagemFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(CustoViagem.class);

		if (filtro.getFuncionario() != null) {
			criteria.add(Restrictions.eq("funcionario", filtro.getFuncionario()));
		}

		if (filtro.getInicio() != null) {
			criteria.add(Restrictions.ge("data", filtro.getInicio()));
		}

		if (filtro.getFim() != null) {
			criteria.add(Restrictions.le("data", filtro.getFim()));
		}

		return criteria.list();

	}

}
