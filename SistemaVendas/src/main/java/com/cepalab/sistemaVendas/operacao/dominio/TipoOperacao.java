package com.cepalab.sistemaVendas.operacao.dominio;

public enum TipoOperacao {
	VISITA("Visita"),
	ABERTURA("Abertura");
	
	private String descricao;
	
	TipoOperacao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
