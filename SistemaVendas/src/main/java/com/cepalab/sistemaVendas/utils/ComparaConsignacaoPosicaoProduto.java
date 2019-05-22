package com.cepalab.sistemaVendas.utils;

import java.util.Comparator;

import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;;

public class ComparaConsignacaoPosicaoProduto implements Comparator<Consignacao>{

	@Override
	public int compare(Consignacao c1, Consignacao c2) {
		if(c1.getProduto().getPosicao() < c2.getProduto().getPosicao()) {
			return -1;
		}
		if(c1.getProduto().getPosicao() > c2.getProduto().getPosicao()) {
			return 1;
		}
		return 0;
	}

}
