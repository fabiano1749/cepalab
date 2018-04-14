package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class TiposVendedores implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public TipoVendedor adicionar(TipoVendedor tipo) {
		return manager.merge(tipo);
	}
	
	public List<TipoVendedor> tipos(){
		return manager.createQuery("from TipoVendedor", TipoVendedor.class).getResultList();
	}
	
	public TipoVendedor porNome(String nome) {
		try {
			return manager.createQuery("from TipoVendedor where nome= :nome", TipoVendedor.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	@Transactional
	public void remover(TipoVendedor tipo) {
		try {
		tipo = porId(tipo.getId());
		manager.remove(tipo);
		manager.flush();
		}catch(PersistenceException e) {
			throw new NegocioException("O tipo de vendedor não pode ser excluído.");
		}
	}
	
	public TipoVendedor porId(Long id) {
		return manager.find(TipoVendedor.class, id);
	}

}
