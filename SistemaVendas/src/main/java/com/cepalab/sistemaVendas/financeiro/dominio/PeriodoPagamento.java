package com.cepalab.sistemaVendas.financeiro.dominio;

public enum PeriodoPagamento {

	MENSAL("Mensal"),
	QUINZENAL("Quinzenal"),
	SEMANAL("Semanal"),
	DIARIO("Diário"),
	OUTROS("Outros");
	
	private String descricao;
	
	PeriodoPagamento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
