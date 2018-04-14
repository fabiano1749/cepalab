package com.cepalab.sistemaVendas.cadastro.dominio;

public enum PodeConsignar {
	
	SIM("Sim"),
	NAO("Não");
	
	private String descricao;
	
	PodeConsignar(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
