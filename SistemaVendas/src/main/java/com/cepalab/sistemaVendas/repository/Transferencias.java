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

import com.cepalab.sistemaVendas.financeiro.dominio.Transferencia;
import com.cepalab.sistemaVendas.repository.filter.TransferenciaFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;



public class Transferencias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Transferencia porId(Long id) {
		return manager.find(Transferencia.class, id);
	}
	
	public Transferencia porId2(Long id) {
		return manager.createQuery("from Transferencia where id= :id", Transferencia.class).setParameter("id", id).getSingleResult();
	}
	
	public Transferencia adicionar (Transferencia transferencia) {
		return manager.merge(transferencia);
	}
	
	public List<Transferencia> transferencias(){
		return manager.createQuery("FROM Transferencia ORDER BY data", Transferencia.class).getResultList();
	}

	public List<Transferencia> filtradas(TransferenciaFilter filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Transferencia> criteriaQuery = builder.createQuery(Transferencia.class);
		Root<Transferencia> t = criteriaQuery.from(Transferencia.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro.getOrigem() != null) {
			predicates.add(builder.equal(t.get("conta_origem"), filtro.getOrigem()));
		}
		
		if (filtro.getDestino() != null) {
			predicates.add(builder.equal(t.get("destino"), filtro.getDestino()));
		}
		
		if(filtro.getInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(t.get("data"), filtro.getInicio()));
		}
		
		if(filtro.getFim() != null) {	
			predicates.add(builder.lessThanOrEqualTo(t.get("data"), filtro.getFim()));
		}
			
		criteriaQuery.select(t);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Transferencia> query = manager.createQuery(criteriaQuery);

		return query.getResultList();
	}
	
	
	@Transactional
	public void remover(Transferencia transferencia) {
		try {
			transferencia = porId(transferencia.getId());
			manager.remove(transferencia);
			manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("Transferencia não pode ser excluída." + e.getMessage());
		}
	}
	
}