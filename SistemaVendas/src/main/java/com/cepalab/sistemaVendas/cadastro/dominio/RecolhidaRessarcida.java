package com.cepalab.sistemaVendas.cadastro.dominio;

public enum RecolhidaRessarcida {
	
	RECOLHIDA("Recolhida"),
	RESSARCIDA("Ressarcida");
	
	private String descricao;
	
	RecolhidaRessarcida(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
