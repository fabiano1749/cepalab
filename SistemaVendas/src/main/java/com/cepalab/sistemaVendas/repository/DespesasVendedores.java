package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class DespesasVendedores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public DespesaVendedor adicionar(DespesaVendedor despesa) {
		return manager.merge(despesa);
	}

	public List<DespesaVendedor> despesas() {

		return manager.createQuery("from DespesaVendedor", DespesaVendedor.class).getResultList();

	}
	
	@Transactional
	public void remover(DespesaVendedor despesa) {
		try {
		despesa = porId(despesa.getId());
		manager.remove(despesa);
		manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("A despesa de viagem não pode ser excluída.");
		}
	}

	public DespesaVendedor porId(Long id) {
		return manager.find(DespesaVendedor.class, id);
	}
	

public List<DespesaVendedor> porFuncionario(Funcionario fun, Date inicio, Date fim) {
		
		try {
			return  manager.createQuery(
					"FROM DespesaVendedor  WHERE funcionario= :fun AND data >= :inicio AND data <= :fim ORDER BY tipoDespesa",
					DespesaVendedor.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
}
