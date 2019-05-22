package com.cepalab.sistemaVendas.repository;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import com.cepalab.sistemaVendas.financeiro.dominio.Banco;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;



public class Bancos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Banco porId(Long id) {
		return manager.find(Banco.class, id);
	}
	
	public Banco adicionar (Banco banco) {
		return manager.merge(banco);
	}
	
	public List<Banco> bancos(){
		return manager.createQuery("FROM Banco ORDER BY nome", Banco.class).getResultList();
	}
	
	@Transactional
	public void remover(Banco banco) {
		try {
			banco = porId(banco.getId());
			manager.remove(banco);
			manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("Banco não pode ser excluído." + e.getMessage());
		}
	}
	
}