package com.cepalab.sistemaVendas.cadastro.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;

public class AuxCalculoPremiacaoColocacao implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoVendedor tipoVendedor;
	private List<AberturaProduto> aberturas;
	
	public AuxCalculoPremiacaoColocacao(TipoVendedor tipoVendedor,List<AberturaProduto> aberturas) {
		this.tipoVendedor = tipoVendedor;
		this.aberturas = aberturas;
	}
	
	public BigDecimal principal(List<TipoProduto> tiposProdutos) {
		List<AberturaProduto> aberturasPorProduto = new ArrayList<>();
		List<AberturaProduto> aberturasPorTipoProduto = new ArrayList<>();
		criaDuasListas(aberturasPorProduto, aberturasPorTipoProduto);
		
		List<TipoProdutoQuantidade> listaTipoQuantidade = criaListaTipoProdutoQuantidade(aberturasPorTipoProduto, tiposProdutos);
		
		
		
		BigDecimal premiacaoPorProduto = retornaPremiacaoColocacaoPorProduto(aberturasPorProduto);
		BigDecimal premiacaoPorTipoProduto = retornaPremiacaoColocacaoPorTipoProduto(listaTipoQuantidade);
		BigDecimal total = premiacaoPorProduto.add(premiacaoPorTipoProduto);
		
		return total;
	}
	
	
	// Retorna true se o calculo da premiacao de colocacao desse produto for por produto
		public boolean premioColocacaoPorProduto(Produto produto) {
			for (PoliticaVendaConsignacaoTipoVendedorProduto p : tipoVendedor.getPoliticasVCTVP()) {
				if (p.getProduto().equals(produto)) {
					if (p.getTipoPremioColocacao().equals(TipoCalculoAberturaColocacao.PRODUTO)) {
						return true;
					}
				}
			}
			return false;
		}
	
	
	//Recebe a lista de aberturas e separa as aberturas em que a premiacao colocacao é por produto
	//da lista em que a premiacao colocacao é por tipo de produto.
	public  void  criaDuasListas(List<AberturaProduto> listaPorProduto, List<AberturaProduto> listaPorTipoProduto){
		for(AberturaProduto a : aberturas) {
			//Se o calculo da comissao de abertura for por produto
			if(premioColocacaoPorProduto(a.getProduto())) {
				listaPorProduto.add(a);
			}
			else {
				listaPorTipoProduto.add(a);
			}
		}
	}

	
	
	//Recebe um produto e retorna a premiacao de colocacao desse produto
	public BigDecimal premiacaoColocacaoProduto(Produto p) {
		for(PoliticaVendaConsignacaoTipoVendedorProduto politica : tipoVendedor.getPoliticasVCTVP()) {
			if(p.equals(politica.getProduto())) {
				return politica.getColocacaoPremiacao();
			}
		}
		return BigDecimal.ZERO;
	}
	
	
	
	//Recebe uma lista de aberturas em que a premiacao de colocacao é calculada por produto
	//e retorna a soma das premiações de colocacoes dessa lista
	public BigDecimal retornaPremiacaoColocacaoPorProduto(List<AberturaProduto> lista) {
		BigDecimal aux = BigDecimal.ZERO;
		if (lista == null || lista.size() == 0) {
			return aux;
		}
		
		else {
			
			for(AberturaProduto a : lista) {
				aux = aux.add(premiacaoColocacaoProduto(a.getProduto()));
			}
		}
		return aux;
		
	}

	
//MÉTODOS PARA CALCULAR AS PREMIAÇÕES DE COLOCACAO POR TIPO DE PRODUTO	
	
	//Recebe uma lista de abertura de produtos em que a premiacao de colocacao é paga por tipo de 
	//produto e cria uma lista de TipoProdutoQuantidade
	//Nessa Lista consta a quantidade de produtos de cada Tipo de Produto.
	//Cria lista tipoProdutoQuantidade PREENCHIDA
		public List<TipoProdutoQuantidade> criaListaTipoProdutoQuantidade(List<AberturaProduto>listaPorTipoProduto, List<TipoProduto> tipos){
			List<TipoProdutoQuantidade> tipoQuantidade = new ArrayList<>();
			for(TipoProduto t : tipos) {
				TipoProdutoQuantidade tipo = new TipoProdutoQuantidade();
				tipo.setTipo(t);
				tipo.setQuantidade(0);
				tipoQuantidade.add(tipo);
			}
			
			for(AberturaProduto a : listaPorTipoProduto) {
				for(TipoProdutoQuantidade tipoQuant : tipoQuantidade) {
					if(a.getProduto().getTipo().equals(tipoQuant.getTipo())) {
						tipoQuant.icrementaQuantidade();
					}
				}
			}
			
			List<TipoProdutoQuantidade> tipoQuantidadeEnxuta = new ArrayList<>();
			for(TipoProdutoQuantidade tpq : tipoQuantidade) {
				if(tpq.getQuantidade() != 0) {
					tipoQuantidadeEnxuta.add(tpq);
				}
			}
			
			
			return tipoQuantidadeEnxuta;
		}
	
	
		
		//Recebe uma lista de aberturas em que a premiacao de colocacao é calculada por Tipo de Produto
		//e retorna a soma das premiações de colocacoes dessa lista
		public BigDecimal retornaPremiacaoColocacaoPorTipoProduto(List<TipoProdutoQuantidade> lista) {
			BigDecimal aux = BigDecimal.ZERO;
			
			for(TipoProdutoQuantidade t : lista) {
					aux = aux.add(premiacaoColocacaoTipoProduto(t));
				}
			
			return aux;
			
		}

		
		//Recebe um Tipo de Produto Quantidade e retorna a premiacao de colocacao do Tipo de Produto correspondente
		public BigDecimal premiacaoColocacaoTipoProduto(TipoProdutoQuantidade t) {
			for(PoliticaColocacaoTipoVendedorTipoProduto politica : tipoVendedor.getListaPoliticasCTVTP()) {
				if(t.getTipo().equals(politica.getTipoProduto())) {
					return politica.premiacao(t.getQuantidade());
				}
			}
			return BigDecimal.ZERO;
		}
		
	
}
