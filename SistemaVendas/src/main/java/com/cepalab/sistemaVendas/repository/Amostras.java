package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.Amostra;

public class Amostras implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<Amostra> porFuncionario(Funcionario fun, Date inicio, Date fim) {
		try {
			return manager.createQuery(
					"FROM Amostra WHERE operacao.funcionario= :fun AND operacao.data >= :inicio AND operacao.data <= :fim",
					Amostra.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
