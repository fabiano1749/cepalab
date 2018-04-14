package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;


import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;

public class Vendas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public BigDecimal porId(Long id) {
		try {
			return manager.createQuery("SELECT v.valorUnitario FROM Venda v  WHERE v.id= :id", BigDecimal.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Venda> porCliente(Cliente cliente) {
		List<Consulta> listaConsulta = null;
		List<Venda> listaVenda = new ArrayList<>();
		try {
			listaConsulta = manager.createQuery(
					"SELECT new com.cepalab.sistemaVendas.repository.Consulta (v.produto, MAX(v.id) , SUM(v.quantidade)) FROM Venda v   WHERE v.operacao.cliente= :cliente GROUP BY v.produto",
					Consulta.class).setParameter("cliente", cliente).getResultList();

			for (Consulta c : listaConsulta) {
				Venda v = new Venda();
				v.setProduto(c.getProduto());
				v.setTotalVendidos(c.getTotalProduto());
				v.setValorUnitario(porId(c.getMaxId()));
				listaVenda.add(v);
			}
			return listaVenda;
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Venda> porFuncionario(Funcionario fun, Date inicio, Date fim) {
		try {
			return manager.createQuery(
					"FROM Venda WHERE operacao.funcionario= :fun AND operacao.data >= :inicio AND operacao.data <= :fim ORDER BY data desc",
					Venda.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

}
