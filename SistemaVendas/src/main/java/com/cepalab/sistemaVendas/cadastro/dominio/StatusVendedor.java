package com.cepalab.sistemaVendas.cadastro.dominio;

public enum StatusVendedor {
	
	ATIVO("Ativo"),
	DESATIVADO("Desativado"),
	AFASTADO("Afastado");
	
	private String descricao;
	
	StatusVendedor(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
