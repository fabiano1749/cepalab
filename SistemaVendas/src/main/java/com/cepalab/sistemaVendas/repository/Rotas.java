package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Rotas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Rota adicionar(Rota rota) {
		return manager.merge(rota);
	}

	public List<Rota> rotas() {

		return manager.createQuery("from Rota order by descricao", Rota.class).getResultList();

	}

	@Transactional
	public void remover(Rota rota) {
		try {
			rota = porId(rota.getId());
			manager.remove(rota);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("A rota não pode ser excluída.");
		}
	}

	public Rota porNome(String nome) {
		try {
			return manager.createQuery("from Rota where nome= :nome", Rota.class).setParameter("nome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Rota porId(Long id) {
		return manager.find(Rota.class, id);
	}
	
	public Rota porId2(Long id) {
		return manager.createQuery("from Rota where id=:id", Rota.class).setParameter("id", id).getSingleResult();
	}
	
	public List<Rota> rotasPorFuncionario(Funcionario fun) {

		return manager.createQuery("from Rota where funcionario= :fun order by numero", Rota.class).setParameter("fun", fun).getResultList();

	}
	
	
}
