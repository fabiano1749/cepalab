package com.cepalab.sistemaVendas.financeiro.dominio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("transferencia")
public class Transferencia extends Transacao {

	private Conta destino;
	

	@ManyToOne
	@JoinColumn(name="destino")
	public Conta getDestino() {
		return destino;
	}
	
	public void setDestino(Conta destino) {
		this.destino = destino;
	}
	
}
