package com.cepalab.sistemaVendas.repository;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.financeiro.dominio.Conta;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;



public class Contas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Conta porId(Long id) {
		return manager.find(Conta.class, id);
	}
	
	public Conta adicionar (Conta conta) {
		return manager.merge(conta);
	}
	
	public List<Conta> contas(){
		return manager.createQuery("FROM Conta ORDER BY nome", Conta.class).getResultList();
	}
	
	@Transactional
	public void remover(Conta conta) {
		try {
			conta = porId(conta.getId());
			manager.remove(conta);
			manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("Conta não pode ser excluída." + e.getMessage());
		}
	}
	
}