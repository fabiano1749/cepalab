package com.cepalab.sistemaVendas.cadastro.dominio;

public enum StatusMovimento {

	ABERTO("Aberto"),
	FECHADO("Fechado");
	
	private String descricao;
	
	StatusMovimento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
