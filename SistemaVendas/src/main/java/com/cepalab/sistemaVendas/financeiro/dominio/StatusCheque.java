package com.cepalab.sistemaVendas.financeiro.dominio;

public enum StatusCheque {
	
	SEM_VINCULO("Sem_Vinculo"),
	NO_CAIXA("No_Caixa"),
	SEM_FUNDO("Sem_Fundo"),
	DEPOSITADO_EM_CONTA("Depositado_Em_Conta"),
	COM_TERCEIRO("Com_Terceiro");
	
	
	
	private String descricao;
	
	StatusCheque(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
