package com.cepalab.sistemaVendas.financeiro.dominio;

public enum StatusParcela {

	REALIZADO("Realizado"),
	PREVISTO("Previsto");
	
	
	private String descricao;
	
	StatusParcela(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
