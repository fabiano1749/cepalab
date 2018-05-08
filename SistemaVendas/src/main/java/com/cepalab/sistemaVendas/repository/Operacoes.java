package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.operacao.dominio.TipoOperacao;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;
import com.cepalab.sistemaVendas.repository.filter.EnvioTransportadoraFilter;
import com.cepalab.sistemaVendas.repository.filter.OperacaoFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jpa.Transactional;

public class Operacoes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private Clientes clientes;

	public Operacao adicionar(Operacao operacao) {
		return manager.merge(operacao);
	}

	public List<Operacao> operacoes() {
		return manager.createQuery("from Operacao", Operacao.class).getResultList();
	}

	@Transactional
	public void remover(Operacao operacao) {
		try {
			operacao = porId(operacao.getId());
			manager.remove(operacao);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("A operacao não pode ser excluída.");
		}
	}

	public Operacao porId(Long id) {
		return manager.find(Operacao.class, id);
	}

	public Operacao porId2(Long id) {
		return manager.createQuery("from Operacao where id= :id", Operacao.class).setParameter("id", id)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Operacao> porCliente(Cliente cliente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Operacao.class);

		if (cliente != null) {
			criteria.add(Restrictions.eq("cliente", cliente));
		}

		return criteria.list();
	}

	public List<Venda> vendasPorCliente(Cliente cliente) {
		List<Operacao> operacaoCliente = porCliente(cliente);
		List<Venda> vendasAgrupadasClientes = new ArrayList<Venda>();
		return null;
	}

	public List<Operacao> resumo(Funcionario fun, Date inicio, Date fim) {
		try {
			return manager
					.createQuery("from Operacao where funcionario= :fun and data >= :inicio and data <= :fim",
							Operacao.class)
					.setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Operacao> operacaoAberturas(Funcionario fun, Date inicio, Date fim) {
		try {
			return manager.createQuery(
					"from Operacao where funcionario= :fun and data >= :inicio and data <= :fim and tipo =:tipo",
					Operacao.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim)
					.setParameter("tipo", TipoOperacao.ABERTURA).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Operacao> operacaoVisitas(Funcionario fun, Date inicio, Date fim) {
		try {
			return manager.createQuery(
					"from Operacao where funcionario= :fun and data >= :inicio and data <= :fim and tipo =:tipo",
					Operacao.class).setParameter("fun", fun).setParameter("inicio", inicio).setParameter("fim", fim)
					.setParameter("tipo", TipoOperacao.VISITA).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	/* 
	public BigDecimal comissaoAberturas(Funcionario fun, Date inicio, Date fim) {
		List<Operacao> aberturas = operacaoAberturas(fun, inicio, fim);
		BigDecimal comissao = BigDecimal.ZERO;
		if (aberturas != null) {
			for (Operacao o : aberturas) {
				comissao = comissao.add(o.comisaoAbertura());
			}
		}
		return comissao;
	}
*/
	
	public List<Operacao> operacoesFiltradas(OperacaoFilter filtro) {
		try {
			if (filtro.getCodigoCliente() == null) {
				return manager.createQuery(
						"from Operacao where funcionario= :fun and data >= :inicio and data <= :fim order by cliente.codigo",
						Operacao.class).setParameter("fun", filtro.getFuncionario())
						.setParameter("inicio", filtro.getInicio()).setParameter("fim", filtro.getFim())
						.getResultList();
			} else {
				return manager.createQuery(
						"from Operacao where funcionario= :fun and data >= :inicio and data <= :fim and cliente.codigo = :codigo",
						Operacao.class).setParameter("fun", filtro.getFuncionario())
						.setParameter("inicio", filtro.getInicio()).setParameter("fim", filtro.getFim())
						.setParameter("codigo", filtro.getCodigoCliente().longValue()).getResultList();
			}

		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Operacao> filtradas(OperacaoFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Operacao.class);

		if (filtro.getFuncionario() != null) {
			criteria.add(Restrictions.eq("funcionario", filtro.getFuncionario()));
		}

		if (filtro.getCodigoCliente() != null) {
			Cliente cliente = clientes.porCodigo(filtro.getCodigoCliente().longValue());
			if (cliente != null) {
				criteria.add(Restrictions.eq("cliente", cliente));
			}
		}

		if (filtro.getInicio() != null) {
			criteria.add(Restrictions.ge("data", filtro.getInicio()));
		}

		if (filtro.getFim() != null) {
			criteria.add(Restrictions.le("data", filtro.getFim()));
		}

		return criteria.list();

	}

	public List<Operacao> operacaoEnvioTransportadora(EnvioTransportadoraFilter filtro) {
		List<Operacao> lista = new ArrayList<>();
		lista = resumo(filtro.getFuncionario(), filtro.getInicio(), filtro.getFim());
		List<Operacao> lista2 = new ArrayList<>();

		if (lista != null) {
			for (Operacao o : lista) {
				if (o.possuiEnvioTransportadora()) {
					lista2.add(o);
				}
			}
		}
		return lista2;

	}

}
