package com.cepalab.sistemaVendas.operacao.dominio;

import java.util.List;

import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoCalculoAberturaColocacao;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;

public class OperacaoUtils {
	
	public static boolean aberturaPorProduto(Produto produto, TipoVendedor tipoVendedor) {
		for (PoliticaVendaConsignacaoTipoVendedorProduto p : tipoVendedor.getPoliticasVCTVP()) {
			if (p.getProduto().equals(produto)) {
				if (p.getTipoAbertura().equals(TipoCalculoAberturaColocacao.PRODUTO)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static void separaAberturaPorProdutoPorTipo(List<AberturaProduto> aberturas, List<AberturaProduto> aberturasPorProduto, List<AberturaProduto> aberturasPorTipoProduto) {
		for (AberturaProduto a : aberturas) {
			// Se o calculo da comissao de abertura for por produto
			if (aberturaPorProduto(a.getProduto(), a.getOperacao().getFuncionario().getTipoVendedor())) {
				aberturasPorProduto.add(a);
			} else {
				//Essa abertura só será inserida na lista para o cálculo, se o número de colocações for maior ou igual ao cadastrado
				if (a.getQuantidade().intValue() >= a.getOperacao().getFuncionario().getTipoVendedor().numeroMinimoDeProduto(a.getProduto().getTipo())) {
					aberturasPorTipoProduto.add(a);
				}
			}
		}
	}
}
