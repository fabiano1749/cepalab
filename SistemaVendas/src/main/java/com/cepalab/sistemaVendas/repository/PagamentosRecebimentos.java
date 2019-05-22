package com.cepalab.sistemaVendas.repository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cepalab.sistemaVendas.financeiro.dominio.PagamentoRecebimento;
import com.cepalab.sistemaVendas.repository.filter.PagRecebFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;



public class PagamentosRecebimentos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public PagamentoRecebimento porId2(Long id) {
		return manager.createQuery("from PagamentoRecebimento where id= :id", PagamentoRecebimento.class).
				setParameter("id", id).getSingleResult();
	}
	
	public PagamentoRecebimento porId(Long id) {
		return manager.find(PagamentoRecebimento.class, id);
	}
	
	public PagamentoRecebimento adicionar (PagamentoRecebimento pagReceb) {
		return manager.merge(pagReceb);
	}
	
	public List<PagamentoRecebimento> pagamentosRecebimentos(){
		return manager.createQuery("FROM PagamentoRecebimento ORDER BY data", PagamentoRecebimento.class).getResultList();
	}
	
	@Transactional
	public void remover(PagamentoRecebimento pagReceb) {
		try {
			pagReceb = porId(pagReceb.getId());
			manager.remove(pagReceb);
			manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("Essa transação não pode ser excluída." + e.getMessage());
		}
	}
	
	
	public List<PagamentoRecebimento> filtrados(PagRecebFilter filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<PagamentoRecebimento> criteriaQuery = builder.createQuery(PagamentoRecebimento.class);
		Root<PagamentoRecebimento> p = criteriaQuery.from(PagamentoRecebimento.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro.getConta() != null) {
			predicates.add(builder.equal(p.get("conta"), filtro.getConta()));
		}
		
		if (filtro.getTipoTransacao() != null) {
			predicates.add(builder.equal(p.get("tipoTransacao"), filtro.getTipoTransacao()));
		}
		
		if(filtro.getInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(p.get("data"), filtro.getInicio()));
		}
		
		if(filtro.getFim() != null) {	
			predicates.add(builder.lessThanOrEqualTo(p.get("data"), filtro.getFim()));
		}
		
		
		criteriaQuery.select(p);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<PagamentoRecebimento> query = manager.createQuery(criteriaQuery);

		return query.getResultList();
	}
	
	
}