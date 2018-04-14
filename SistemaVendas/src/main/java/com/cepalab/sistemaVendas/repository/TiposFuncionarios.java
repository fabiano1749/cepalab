package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoFuncionario;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class TiposFuncionarios implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public TipoFuncionario adicionar(TipoFuncionario tipo) {
		return manager.merge(tipo);
	}
	
	public List<TipoFuncionario> tipos(){
		return manager.createQuery("from TipoFuncionario", TipoFuncionario.class).getResultList();
	}
	
	public TipoFuncionario porNome(String nome) {
		try {
			return manager.createQuery("from TipoFuncionario where nome= :nome", TipoFuncionario.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	@Transactional
	public void remover(TipoFuncionario tipo) {
		try {
		tipo = porId(tipo.getId());
		manager.remove(tipo);
		manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("O tipo de funcionário não pode ser excluído.");
		}
	}
	
	public TipoFuncionario porId(Long id) {
		return manager.find(TipoFuncionario.class, id);
	}
	
}
