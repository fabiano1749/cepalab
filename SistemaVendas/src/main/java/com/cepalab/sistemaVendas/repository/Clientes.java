package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.consulta.dominio.UltimaVisitaAoCliente;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.filter.ClienteFilter;
import com.cepalab.sistemaVendas.repository.filter.VisitasFilter;
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
		return manager.createQuery("from Cliente where id= :id", Cliente.class).setParameter("id", id)
				.getSingleResult();
	}

	public List<Cliente> filtrados2(ClienteFilter filtro) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = builder.createQuery(Cliente.class);
		Root<Cliente> c = criteriaQuery.from(Cliente.class);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(c.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		}

		if (filtro.getFuncionario() != null) {
			predicates.add(builder.equal(c.get("rota").get("funcionario"), filtro.getFuncionario()));
		}

		if (filtro.getCodigo() != null) {
			predicates.add(builder.equal(c.get("codigo"), filtro.getCodigo()));
		}

		if (filtro.getRota() != null) {
			predicates.add(builder.equal(c.get("rota"), filtro.getRota()));
		}

		if (filtro.getUf() != null) {
			predicates.add(builder.equal(c.get("endereco").get("uf"), filtro.getUf().toString()));
		}

		if (StringUtils.isNotBlank(filtro.getCidade())) {
			predicates.add(builder.like(builder.lower(c.get("endereco").get("cidade")),
					"%" + filtro.getCidade().toLowerCase() + "%"));
		}

		if (StringUtils.isNotBlank(filtro.getCnpj())) {
			predicates.add(builder.like(builder.lower(c.get("cpfCnpj")), "%" + filtro.getCnpj().toLowerCase() + "%"));
		}

		criteriaQuery.select(c);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Cliente> query = manager.createQuery(criteriaQuery);

		return query.getResultList();

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
			return manager.createQuery("from Cliente where rota.funcionario= :fun", Cliente.class)
					.setParameter("fun", fun).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Cliente> nuncaVisitadosJPQL(VisitasFilter filtro) {
		// No caso do funcionário e da rota serem diferentes de null
		if (filtro.getFuncionario() != null && filtro.getRota() != null) {
			String query = "SELECT c FROM Cliente c LEFT JOIN Operacao o ON c = o.cliente WHERE c.rota.funcionario =:funcionario AND c.rota =:rota  AND o.id IS NULL";
			return manager.createQuery(query, Cliente.class).setParameter("funcionario", filtro.getFuncionario())
					.setParameter("rota", filtro.getRota()).getResultList();
		}

		// No caso do funcionário ser diferente de null e da rota ser igual a null
		else if (filtro.getFuncionario() != null && filtro.getRota() == null) {
			String query = "SELECT c FROM Cliente c LEFT JOIN Operacao o ON c = o.cliente WHERE c.rota.funcionario =:funcionario AND o.id IS NULL";
			return manager.createQuery(query, Cliente.class).setParameter("funcionario", filtro.getFuncionario())
					.getResultList();
		}

		// Retorna todos os clientes que nunca foram visitados
		else {
			String query = "SELECT c FROM Cliente c LEFT JOIN Operacao o ON c = o.cliente WHERE o.id IS NULL";
			return manager.createQuery(query, Cliente.class).getResultList();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Cliente> nuncaVisitadosCriteria(VisitasFilter filtro){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);
		criteria.createAlias("rota", "r");
		
		DetachedCriteria criteriaOperacao = DetachedCriteria.forClass(Operacao.class);
		criteriaOperacao.setProjection(Projections.property("cliente"));
		criteriaOperacao.createAlias("cliente", "c");
		
		// No caso do funcionário e da rota serem diferentes de null
		if (filtro.getFuncionario() != null && filtro.getRota() != null) {
			criteria.add(Restrictions.eq("rota", filtro.getRota()));
			
			criteriaOperacao.add(Restrictions.eq("c.rota", filtro.getRota()));
		}
		else if (filtro.getFuncionario() != null && filtro.getRota() == null) {
			criteria.add(Restrictions.eq("r.funcionario", filtro.getFuncionario()));
			
			criteriaOperacao.add(Restrictions.eq("funcionario", filtro.getFuncionario()));	
		}
		
		criteria.add(Property.forName("id").notIn(criteriaOperacao));
		
		return criteria.list();
		
	}
	
	
	
	public List<UltimaVisitaAoCliente> ultimaVisitaAntesDeJPQL(VisitasFilter filtro) {

		if(filtro.getData() == null) {
			return null;
		}
		
		// No caso do funcionário e da rota serem diferentes de null
		else if (filtro.getFuncionario() != null && filtro.getRota() != null) {
			String query = "SELECT new com.cepalab.sistemaVendas.consulta.dominio.UltimaVisitaAoCliente (o.cliente, MAX(o.data)) FROM Operacao o WHERE o.data <:data AND o.funcionario =:funcionario AND o.cliente.rota =:rota GROUP BY o.cliente";
			return manager.createQuery(query, UltimaVisitaAoCliente.class).setParameter("data", filtro.getData())
					.setParameter("funcionario", filtro.getFuncionario()).setParameter("rota", filtro.getRota())
					.getResultList();
		}
		// No caso do funcionário ser diferente de null e da rota ser igual a null
		else if (filtro.getFuncionario() != null && filtro.getRota() == null) {
			String query = "SELECT new com.cepalab.sistemaVendas.consulta.dominio.UltimaVisitaAoCliente (o.cliente, MAX(o.data)) FROM Operacao o WHERE o.data <:data AND o.funcionario =:funcionario GROUP BY o.cliente";
			return manager.createQuery(query, UltimaVisitaAoCliente.class).setParameter("data", filtro.getData())
					.setParameter("funcionario", filtro.getFuncionario())
					.getResultList();
		}

		// Retorna todos os clientes de todos os funcionários em que a ultima visita ocorreu antes do período informado
		else {
			String query = "SELECT new com.cepalab.sistemaVendas.consulta.dominio.UltimaVisitaAoCliente (o.cliente, MAX(o.data)) FROM Operacao o WHERE o.data <:data GROUP BY o.cliente";
			return manager.createQuery(query, UltimaVisitaAoCliente.class).setParameter("data", filtro.getData())
					.getResultList();
		}

	}

	public List<Cliente> periodoVisitas(VisitasFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = builder.createQuery(Cliente.class);
		Root<Cliente> c = criteriaQuery.from(Cliente.class);
		Root<Operacao> o = criteriaQuery.from(Operacao.class);

		Join<Operacao, Cliente> operacaoJoin = o.join("cliente");

		List<Predicate> predicates = new ArrayList<>();

		if (filtro.getFuncionario() != null) {
			predicates.add(builder.equal(c.get("rota").get("funcionario"), filtro.getFuncionario()));
		}

		if (filtro.getRota() != null) {
			predicates.add(builder.equal(c.get("rota"), filtro.getRota()));
		}

		if (filtro.getUf() != null) {
			predicates.add(builder.equal(c.get("endereco").get("uf"), filtro.getUf().toString()));
		}

		if (StringUtils.isNotBlank(filtro.getCidade())) {
			predicates.add(builder.like(builder.lower(c.get("endereco").get("cidade")),
					"%" + filtro.getCidade().toLowerCase() + "%"));
		}

		predicates.add(builder.lessThanOrEqualTo(operacaoJoin.get("data"), filtro.getData()));

		criteriaQuery.select(c);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		return manager.createQuery(criteriaQuery).getResultList();
	}

}
