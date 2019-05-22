package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;


public class AberturasProdutos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	
public List<AberturaProduto> porFuncionarioTodas(Funcionario fun, Date inicio, Date fim) {
		
		try {
			return  manager.createQuery(
					"FROM AberturaProduto  WHERE operacao.funcionario= :fun AND operacao.data >= :inicio AND operacao.data <= :fim",
					AberturaProduto.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}


public List<AberturaProduto> porOperacao(Operacao op){
	try {
		return  manager.createQuery(
				"FROM AberturaProduto  WHERE operacao = :op",
				AberturaProduto.class).setParameter("op", op).getResultList();
		
	} catch (NoResultException e) {
		return null;
	}
	catch(Exception e) {
		return null;
	}
}


}
