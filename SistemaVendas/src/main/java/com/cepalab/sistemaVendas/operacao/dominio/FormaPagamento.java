package com.cepalab.sistemaVendas.operacao.dominio;

public enum FormaPagamento {

	DINHEIRO("Dinheiro"),
	CHEQUE("Cheque"),
	BOLETO("Boleto");
	
private String descricao;
	
	FormaPagamento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
