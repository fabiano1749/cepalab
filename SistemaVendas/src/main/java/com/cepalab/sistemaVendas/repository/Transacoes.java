package com.cepalab.sistemaVendas.repository;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import com.cepalab.sistemaVendas.financeiro.dominio.Transacao;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;



public class Transacoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Transacao porId(Long id) {
		return manager.find(Transacao.class, id);
	}
	
	public Transacao adicionar (Transacao transacao) {
		return manager.merge(transacao);
	}
	
	public List<Transacao> transacaos(){
		return manager.createQuery("FROM Transacao ORDER BY data", Transacao.class).getResultList();
	}
	
	@Transactional
	public void remover(Transacao transacao) {
		try {
			transacao = porId(transacao.getId());
			manager.remove(transacao);
			manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("Transacao não pode ser excluída." + e.getMessage());
		}
	}
	
}