package com.cepalab.sistemaVendas.cadastro.dominio;

public enum TipoCalculoDeComissao {
	
	PRODUTO("Produto"),
	TIPO("Tipo");
	
	private String descricao;
	
	TipoCalculoDeComissao (String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
