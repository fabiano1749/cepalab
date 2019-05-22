package com.cepalab.sistemaVendas.Expedicao.dominio;

public enum StatusExpedicao {
	
	ABERTO("Aberto"),
	FECHADO("Fechado");
	private String descricao;
	
	StatusExpedicao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	

}
