package com.cepalab.sistemaVendas.operacao.dominio;

public enum TipoDespesa {

	PLANO_SAUDE("Plano de saúde"),
	OUTROS("Outros");
	
	private String descricao;
	
	TipoDespesa(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
