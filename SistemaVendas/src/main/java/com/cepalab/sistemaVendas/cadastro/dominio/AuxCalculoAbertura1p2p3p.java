package com.cepalab.sistemaVendas.cadastro.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cepalab.sistemaVendas.fechamento.dominio.Fechamento;
import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;

public class AuxCalculoAbertura1p2p3p implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoVendedor tipoVendedor;
	private List<AberturaProduto> aberturas;

	public AuxCalculoAbertura1p2p3p(TipoVendedor tipoVendedor, List<AberturaProduto> aberturas) {
		this.tipoVendedor = tipoVendedor;
		this.aberturas = aberturas;
	}

	public int principal(List<TipoProduto> tiposProdutos) {
		List<AberturaProduto> aberturasPorProduto = new ArrayList<>();
		List<AberturaProduto> aberturasPorTipoProduto = new ArrayList<>();
		criaDuasListas(aberturasPorProduto, aberturasPorTipoProduto);
		
		return aberturasPorProduto.size() + aberturasPorTipoProduto.size(); 
	}

	// Retorna true se o calculo da comissao de abertura desse produto for por
	// produto
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

	// Recebe a lista de aberturas e separa as aberturas em que a comissão abertura
	// é por produto
	// da lista em que a comissão abertura é por tipo de produto.
	public void criaDuasListas(List<AberturaProduto> listaPorProduto, List<AberturaProduto> listaPorTipoProduto) {
		for (AberturaProduto a : aberturas) {
			// Se o calculo da comissao de abertura for por produto
			if (aberturaPorProduto(a.getProduto())) {
				listaPorProduto.add(a);
			} else {
				//Essa abertura só será inserida na lista para o cálculo, se o número de colocações for maior ou igual ao cadastrado
				if (a.getQuantidade().intValue() >= tipoVendedor.numeroMinimoDeProduto(a.getProduto().getTipo())) {
					listaPorTipoProduto.add(a);
				}
			}
		}
	}

}
