package com.cepalab.sistemaVendas.utils;

import java.util.Comparator;

import com.cepalab.sistemaVendas.operacao.dominio.Venda;

public class ComparaVendaPosicaoProduto implements Comparator<Venda>{

	@Override
	public int compare(Venda v1, Venda v2) {
		if(v1.getProduto().getPosicao() < v2.getProduto().getPosicao()) {
			return -1;
		}
		if(v1.getProduto().getPosicao() > v2.getProduto().getPosicao()) {
			return 1;
		}
		return 0;
	}

}
