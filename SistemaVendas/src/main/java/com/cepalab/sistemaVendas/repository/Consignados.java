package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.operacao.dominio.TipoOperacao;
import com.cepalab.sistemaVendas.repository.filter.ProdutosConsignadosFilter;

public class Consignados implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public BigDecimal porId(Long id) {
		try {
			return manager.createQuery("SELECT c.valorUnitario FROM Consignacao c  WHERE c.id= :id", BigDecimal.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Consignacao> porCliente(Cliente cliente) {
		List<Consulta> listaConsulta = null;
		List<Consignacao> listaConsignacao = new ArrayList<>();
		try {
			listaConsulta = manager.createQuery(
					"SELECT new com.cepalab.sistemaVendas.repository.Consulta (c.produto, MAX(c.id) , SUM(c.consignados) - SUM(c.vendidos) - SUM(c.devolvidos)) FROM Consignacao c   WHERE c.operacao.cliente= :cliente GROUP BY c.produto",
					Consulta.class).setParameter("cliente", cliente).getResultList();

			for (Consulta c : listaConsulta) {
				Consignacao con = new Consignacao();
				con.setProduto(c.getProduto());
				con.setTotalConsignado(c.getTotalProduto());
				con.setTotalAux(c.getTotalProduto());
				con.setValorUnitario(porId(c.getMaxId()));
				listaConsignacao.add(con);
			}

			return listaConsignacao;
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Consignacao> porFuncionario(Funcionario fun, Date inicio, Date fim) {
		try {
			return manager.createQuery(
					"FROM Consignacao  WHERE operacao.funcionario= :fun AND operacao.data >= :inicio AND operacao.data <= :fim ORDER BY data desc",
					Consignacao.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Consignacao> aumentoPorFuncionario(Funcionario fun, Date inicio, Date fim) {
		try {
			return manager.createQuery(
					"FROM Consignacao  WHERE operacao.funcionario= :fun AND operacao.data >= :inicio AND operacao.data <= :fim "
					+ "AND operacao.tipo =:tipo AND vendidos < consignados AND totalAux > 0",
					Consignacao.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim).setParameter("tipo", TipoOperacao.VISITA)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Consignacao> filtrados(ProdutosConsignadosFilter filtro) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Consignacao> criteriaQuery = builder.createQuery(Consignacao.class);
		Root<Consignacao> c = criteriaQuery.from(Consignacao.class);
		
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro.getProduto() != null) { 
			predicates.add(builder.equal(c.get("produto"), filtro.getProduto()));
		}
		
		if (filtro.getFuncionario() != null) { 
			predicates.add(builder.equal(c.get("operacao").get("funcionario"), filtro.getFuncionario()));
		}
		
		if (filtro.getRota() != null) { 
			predicates.add(builder.equal(c.get("operacao").get("cliente").get("rota"), filtro.getRota()));
		}
		
		if (filtro.getCliente() != null) { 
			predicates.add(builder.equal(c.get("operacao").get("cliente"), filtro.getCliente()));
		}
		
		if (filtro.getUf() != null) { 
			predicates.add(builder.equal(c.get("operacao").get("cliente").get("endereco").get("uf"), filtro.getUf().toString()));
		}
		
		if (StringUtils.isNotBlank(filtro.getCidade())) {
			predicates.add(builder.like(builder.lower(c.get("operacao").get("cliente").get("endereco").get("cidade")), "%"+ filtro.getCidade().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(c);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Consignacao> query = manager.createQuery(criteriaQuery);

		List<Consignacao> lista = query.getResultList();
		
		return lista;
		
		
		
	}
	
	
	
	
}
