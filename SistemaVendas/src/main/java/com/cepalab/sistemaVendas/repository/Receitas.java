package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.fechamento.dominio.FormaPagamentoValor;



public class Receitas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public List<FormaPagamentoValor> resumoReceitas(Funcionario fun, Date inicio, Date fim){
		
		try {
			return manager.createQuery("SELECT new com.cepalab.sistemaVendas.fechamento.dominio.FormaPagamentoValor(r.formaPagamento, SUM(r.valor)) FROM Receita r  where r.operacao.funcionario= :fun and r.operacao.data >= :inicio and r.operacao.data <= :fim GROUP BY r.formaPagamento", FormaPagamentoValor.class)
					.setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
