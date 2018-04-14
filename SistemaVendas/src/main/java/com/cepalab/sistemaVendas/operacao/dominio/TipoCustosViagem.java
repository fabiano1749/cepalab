package com.cepalab.sistemaVendas.operacao.dominio;

public enum TipoCustosViagem {
	GASOLINA("Gasolina"), ALIMENTACAO("Alimentação"), HOTEL("Hotel"), PEDAGIO("Pedágio"), OUTROS("Outros");

	private String descricao;

	TipoCustosViagem(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
