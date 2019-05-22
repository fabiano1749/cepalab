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

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.financeiro.dominio.Cheque;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusCheque;
import com.cepalab.sistemaVendas.repository.filter.ChequeFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;



public class Cheques implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Cheque porId(Long id) {
		return manager.find(Cheque.class, id);
	}
	
	public Cheque adicionar (Cheque cheque) {
		return manager.merge(cheque);
	}
	
	public List<Cheque> cheques(){
		return manager.createQuery("FROM Cheque ORDER BY status", Cheque.class).getResultList();
	}
	
	public List<Cheque> chequesEmCaixa(){
		return manager.createQuery("FROM Cheque WHERE status = :status ORDER BY valor", Cheque.class).setParameter("status", StatusCheque.NO_CAIXA).getResultList();
	}
	
	public List<Cheque> chequesSemVinculoOutros(){
		return manager.createQuery("FROM Cheque WHERE status = :status AND funcionario is NULL ORDER BY valor", Cheque.class).setParameter("status", StatusCheque.SEM_VINCULO).getResultList();
	}
	
	public List<Cheque> chequesSemVinculoTodos(){
		return manager.createQuery("FROM Cheque WHERE status = :status ORDER BY valor", Cheque.class).setParameter("status", StatusCheque.SEM_VINCULO).getResultList();
	}
	
	
	public List<Cheque> chequesSemVinculoFuncionario(Funcionario fun){
		System.out.println("CHEGOU NOS CHEQUES");
		return manager.createQuery("FROM Cheque WHERE status = :status AND funcionario = :funcionario ORDER BY valor", Cheque.class).setParameter("status", StatusCheque.SEM_VINCULO).setParameter("funcionario", fun).getResultList();
	}
	
	@Transactional
	public void remover(Cheque cheque) {
		try {
			cheque = porId(cheque.getId());
			manager.remove(cheque);
			manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("Cheque não pode ser excluído." + e.getMessage());
		}
	}
	
	public List<Cheque> filtrados(ChequeFilter filtro){
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cheque> criteriaQuery = builder.createQuery(Cheque.class);
		Root<Cheque> c = criteriaQuery.from(Cheque.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro.getFuncionario() != null) {
			predicates.add(builder.equal(c.get("funcionario"), filtro.getFuncionario()));
		}
		
		if (filtro.getStatus() != null) {
			predicates.add(builder.equal(c.get("status"), filtro.getStatus()));
		}
		
		if(filtro.getBomParaInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(c.get("bomPara"), filtro.getBomParaInicio()));
		}
		
		if(filtro.getBomParaFim() != null) {	
			predicates.add(builder.lessThanOrEqualTo(c.get("bomPara"), filtro.getBomParaFim()));
		}
		
		
		criteriaQuery.select(c);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Cheque> query = manager.createQuery(criteriaQuery);

		return query.getResultList();
	}
	
	
}