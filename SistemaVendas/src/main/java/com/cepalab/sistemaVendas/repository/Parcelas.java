package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cepalab.sistemaVendas.financeiro.dominio.Parcela;
import com.cepalab.sistemaVendas.repository.filter.ParcelaFilter;

public class Parcelas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Parcela porId(Long id) {
		return manager.find(Parcela.class, id);
	}

	public Parcela adicionar(Parcela parcela) {
		
		return manager.merge(parcela);
	}

	public List<Parcela> parcelas() {
		return manager.createQuery("FROM Parcela ORDER BY data", Parcela.class).getResultList();
	}

	
	//Para acessar valores especificos de uma subclass, use o operador TREAT:
	
	public List<Parcela> filtradas(ParcelaFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Parcela> criteriaQuery = builder.createQuery(Parcela.class);
		Root<Parcela> p = criteriaQuery.from(Parcela.class);

		List<Predicate> predicates = new ArrayList<>();

		if (filtro.getInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(p.get("data"), filtro.getInicio()));
		}

		if (filtro.getFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(p.get("data"), filtro.getFim()));
		}

		if (filtro.getTipoTransacao() != null) {
				predicates.add(builder.equal(p.get("transacao").get("tipoTransacao"),filtro.getTipoTransacao()));
		}
		
		if (filtro.getStatusParcela() != null) {
			predicates.add(builder.equal(p.get("status"),filtro.getStatusParcela()));
	}
		

		criteriaQuery.select(p);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Parcela> query = manager.createQuery(criteriaQuery);

		return query.getResultList();
	}

}