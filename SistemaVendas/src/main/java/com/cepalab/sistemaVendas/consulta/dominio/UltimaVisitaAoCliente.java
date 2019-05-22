package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.util.Date;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;

public class UltimaVisitaAoCliente implements Serializable {

	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Date data;

	public UltimaVisitaAoCliente (Cliente cliente, Date data) {
		this.cliente = cliente;
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
