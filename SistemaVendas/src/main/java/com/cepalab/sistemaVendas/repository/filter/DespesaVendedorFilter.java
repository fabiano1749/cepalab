package com.cepalab.sistemaVendas.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;

public class DespesaVendedorFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;
	private Date inicio;
	private Date fim;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

}
