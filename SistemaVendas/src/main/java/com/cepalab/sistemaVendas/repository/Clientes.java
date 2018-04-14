package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
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

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.repository.filter.ClienteFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Clientes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cliente adicionar(Cliente cliente) {
		return manager.merge(cliente);
	}

	public List<Cliente> clientes() {

		return manager.createQuery("from Cliente", Cliente.class).getResultList();

	}

	public Cliente porCodigo(Long codigo) {
		try {
			return manager.createQuery("from Cliente where codigo= :codigo", Cliente.class)
					.setParameter("codigo", codigo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Cliente porCpfCnpj(String cpfCnpj) {
		try {
			return manager.createQuery("from Cliente where cpfCnpj= :cpfCnpj", Cliente.class)
					.setParameter("cpfCnpj", cpfCnpj).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	

	@Transactional
	public void remover(Cliente cliente) {
		try {
			cliente = porId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído.");
		}
	}

	public Cliente porId(Long id) {
		return manager.find(Cliente.class, id);
	}

	public Cliente porId2(Long id) {
		return manager.createQuery("from Cliente where id= :id", Cliente.class).setParameter("id", id).getSingleResult();
	}

	
	
	
	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);

		if (filtro.getCodigo() != null) {
			criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
		}

		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getRota() != null) {
			criteria.add(Restrictions.eq("rota", filtro.getRota()));
		}

		return criteria.addOrder(Order.asc("codigo")).list();

	}

	
	public List<Cliente> porRota(Rota rota) {

		try {
			return manager.createQuery("from Cliente where rota= :rota", Cliente.class).setParameter("rota", rota)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}
	
	public List<Cliente> porFuncionario(Funcionario fun) {

		try {
			return manager.createQuery("from Cliente where rota.funcionario= :fun", Cliente.class).setParameter("fun", fun)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}
	
}
