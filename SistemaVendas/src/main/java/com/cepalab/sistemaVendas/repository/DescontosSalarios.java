package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class DescontosSalarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public DescontoSalario adicionar(DescontoSalario desconto) {
		return manager.merge(desconto);
	}

	public List<DescontoSalario> descontos() {

		return manager.createQuery("from DescontoSalario", DescontoSalario.class).getResultList();

	}

	@Transactional
	public void remover(DescontoSalario desconto) {
		try {
			desconto = porId(desconto.getId());
			manager.remove(desconto);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("O desconto não pode ser excluído.");
		}
	}

	public DescontoSalario porId(Long id) {
		return manager.find(DescontoSalario.class, id);
	}

	public List<DescontoSalario> porFuncionario(Funcionario fun, Date inicio, Date fim) {

		try {
			return manager.createQuery(
					"FROM DescontoSalario  WHERE funcionario= :fun AND data >= :inicio AND data <= :fim ORDER BY data",
					DescontoSalario.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
