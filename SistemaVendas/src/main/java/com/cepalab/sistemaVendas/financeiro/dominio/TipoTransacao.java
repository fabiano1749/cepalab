package com.cepalab.sistemaVendas.financeiro.dominio;

public enum TipoTransacao {
	
	TRANSFERENCIA("Transferência"),
	PAGAMENTO("Pagamento"),
	RECEBIMENTO("Recebimento");
	
	private String descricao;
	
	TipoTransacao(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

}
