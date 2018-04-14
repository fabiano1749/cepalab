package com.cepalab.sistemaVendas.cadastro.dominio;

public enum StatusCliente {
	
	ATIVO("Ativo"),
	INATIVO("Inativo"),
	FECHADO("Fechado");
	
	private String descricao;
	
	StatusCliente(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
