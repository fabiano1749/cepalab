package com.cepalab.sistemaVendas.operacao.dominio;

public enum FormaPagamento {

	NENHUM(""),
	BOLETO("Boleto"),
	CHEQUE("Cheque"),
	DINHEIRO("Dinheiro");
	
private String descricao;
	
	FormaPagamento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
