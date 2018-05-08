package com.cepalab.sistemaVendas.cadastro.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;

public class AuxCalculoComissaoAbertura implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoVendedor tipoVendedor;
	private List<AberturaProduto> aberturas;
	
	public AuxCalculoComissaoAbertura(TipoVendedor tipoVendedor,List<AberturaProduto> aberturas) {
		this.tipoVendedor = tipoVendedor;
		this.aberturas = aberturas;
	}
	
	public BigDecimal principal(List<TipoProduto> tiposProdutos) {
		List<AberturaProduto> aberturasPorProduto = new ArrayList<>();
		List<AberturaProduto> aberturasPorTipoProduto = new ArrayList<>();
		criaDuasListas(aberturasPorProduto, aberturasPorTipoProduto);
		
		List<TipoProdutoQuantidade> listaTipoQuantidade = criaListaTipoProdutoQuantidade(aberturasPorTipoProduto, tiposProdutos);
		
		
		
		BigDecimal comissaoPorProduto = retornaComissaoAberturaPorProduto(aberturasPorProduto);
		BigDecimal comissaoPorTipoProduto = retornaComissaoAberturaPorTipoProduto(listaTipoQuantidade);
		BigDecimal total = comissaoPorProduto.add(comissaoPorTipoProduto);
		
		return total;
	}
	
	
	// Retorna true se o calculo da comissao de abertura desse produto for por produto
		public boolean aberturaPorProduto(Produto produto) {
			for (PoliticaVendaConsignacaoTipoVendedorProduto p : tipoVendedor.getPoliticasVCTVP()) {
				if (p.getProduto().equals(produto)) {
					if (p.getTipoAbertura().equals(TipoCalculoAberturaColocacao.PRODUTO)) {
						return true;
					}
				}
			}
			return false;
		}
	
	
	//Recebe a lista de aberturas e separa as aberturas em que a comissão abertura é por produto
	//da lista em que a comissão abertura é por tipo de produto.
	public  void  criaDuasListas(List<AberturaProduto> listaPorProduto, List<AberturaProduto> listaPorTipoProduto){
		for(AberturaProduto a : aberturas) {
			//Se o calculo da comissao de abertura for por produto
			if(aberturaPorProduto(a.getProduto())) {
				listaPorProduto.add(a);
			}
			else {
				listaPorTipoProduto.add(a);
			}
		}
	}

	
	
	//Recebe um produto e retorna a comissão de abertura desse produto
	public BigDecimal comissaoAberturaProduto(Produto p) {
		for(PoliticaVendaConsignacaoTipoVendedorProduto politica : tipoVendedor.getPoliticasVCTVP()) {
			if(p.equals(politica.getProduto())) {
				return politica.getAbertura();
			}
		}
		return BigDecimal.ZERO;
	}
	
	
	
	//Recebe uma lista de aberturas em que a comissao de abertura é paga por produto
	//e retorna a soma das comissões de aberturas dessa lista
	public BigDecimal retornaComissaoAberturaPorProduto(List<AberturaProduto> lista) {
		BigDecimal aux = BigDecimal.ZERO;
		if (lista == null || lista.size() == 0) {
			return aux;
		}
		
		else {
			
			for(AberturaProduto a : lista) {
				aux = aux.add(comissaoAberturaProduto(a.getProduto()));
			}
		}
		return aux;
		
	}

	
//MÉTODOS PARA CALCULAR AS ABERTURAS POR TIPO DE PRODUTO	
	
	//Recebe uma lista de abertura de produtos em que a comissão de abertura é paga por tipo de 
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
	
	
		
		//Recebe uma lista de aberturas em que a comissao de abertura é paga por Tipo de Produto
		//e retorna a soma das comissões de aberturas dessa lista
		public BigDecimal retornaComissaoAberturaPorTipoProduto(List<TipoProdutoQuantidade> lista) {
			BigDecimal aux = BigDecimal.ZERO;
			
			
			
			
			for(TipoProdutoQuantidade t : lista) {
					aux = aux.add(comissaoAberturaTipoProduto(t));
				}
			
			return aux;
			
		}

		
		//Recebe um Tipo de Produto Quantidade e retorna a comissão de abertura do Tipo de Produto correspondente
		public BigDecimal comissaoAberturaTipoProduto(TipoProdutoQuantidade t) {
			for(PoliticaAberturaTipoVendedorTipoProduto politica : tipoVendedor.getListaPoliticasATVTP()) {
				if(t.getTipo().equals(politica.getTipoProduto())) {
					return politica.comissao(t.getQuantidade());
				}
			}
			return BigDecimal.ZERO;
		}
		
	
}
