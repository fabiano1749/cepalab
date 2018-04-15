package com.cepalab.sistemaVendas.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;

public class ComissoesTipoVendedores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<PoliticaVendaConsignacaoTipoVendedorProduto> ComissoesPorTipoVendedor(TipoVendedor tipo) {

		try {
			return manager.createQuery("from ComissaoTipoVendedorProduto where tipoVendedor= :tipo", PoliticaVendaConsignacaoTipoVendedorProduto.class)
					.setParameter("tipo", tipo).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public BigDecimal taxaComissaoFuncionarioProduto(Funcionario func, Produto produto){
		List<PoliticaVendaConsignacaoTipoVendedorProduto> lista = ComissoesPorTipoVendedor(func.getTipoVendedor()) ;
		BigDecimal taxa = new BigDecimal("0");
		
		for(PoliticaVendaConsignacaoTipoVendedorProduto c : lista) {
			if(c.getProduto().getNome().equals(produto.getNome())){
				taxa = c.getTaxaComissao();
			}
		}
		
		return taxa;	
	}
	
	public BigDecimal taxaComissaoFuncionarioProdutoEnvio(Funcionario func, Produto produto){
		List<PoliticaVendaConsignacaoTipoVendedorProduto> lista = ComissoesPorTipoVendedor(func.getTipoVendedor()) ;
		BigDecimal taxa = new BigDecimal("0");
		
		for(PoliticaVendaConsignacaoTipoVendedorProduto c : lista) {
			if(c.getProduto().getNome().equals(produto.getNome())){
				taxa = c.getTaxaComissaoEnvio();
			}
		}
		
		return taxa;	
	}
	
	
}
