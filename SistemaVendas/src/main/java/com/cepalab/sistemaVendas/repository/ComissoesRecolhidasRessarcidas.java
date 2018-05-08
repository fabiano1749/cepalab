package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;


public class ComissoesRecolhidasRessarcidas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	
	public ComissaoRecolhidaRessarcida adicionar(ComissaoRecolhidaRessarcida comissao) {
		return manager.merge(comissao);
	}

	public List<ComissaoRecolhidaRessarcida> comissoes() {

		return manager.createQuery("from ComissaoRecolhidaRessarcida", ComissaoRecolhidaRessarcida.class).getResultList();

	}
	
	
	@SuppressWarnings("unchecked")
	public List<ComissaoRecolhidaRessarcida> porFuncionario(Funcionario funcionario) {

		try {
			return manager.createQuery("from ComissaoRecolhidaRessarcida where funcionario= :funcionario order by data desc",
					ComissaoRecolhidaRessarcida.class).setParameter("funcionario", funcionario).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public ComissaoRecolhidaRessarcida porId(Long id) {
		try {
			return manager.createQuery("from ComissaoRecolhidaRessarcida where id= :id", ComissaoRecolhidaRessarcida.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
public List<ComissaoRecolhidaRessarcida> porFuncionario(Funcionario fun, Date inicio, Date fim) {
		
		try {
			return  manager.createQuery(
					"FROM ComissaoRecolhidaRessarcida  WHERE funcionario= :fun AND data >= :inicio AND data <= :fim ORDER BY tipo",
					ComissaoRecolhidaRessarcida.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
