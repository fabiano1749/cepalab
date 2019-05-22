package com.cepalab.sistemaVendas.financeiro.dominio;

public enum TipoConta {
	
	CAIXA_CEPA("Caixa_Cepa"),
	CONTA_EM_BANCO("Conta_Em_Banco"),
	OUTROS("Outros");
	
	private String descricao;
	
	TipoConta(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
