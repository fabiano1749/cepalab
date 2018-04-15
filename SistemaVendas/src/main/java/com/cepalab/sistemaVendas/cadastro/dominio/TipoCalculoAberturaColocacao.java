package com.cepalab.sistemaVendas.cadastro.dominio;

public enum TipoCalculoAberturaColocacao {
	
	PRODUTO("Produto"),
	TIPO("Tipo");
	
	private String descricao;
	
	TipoCalculoAberturaColocacao (String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
