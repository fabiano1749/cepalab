package com.cepalab.sistemaVendas.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.cepalab.sistemaVendas.Expedicao.dominio.StatusExpedicao;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;

public class ExpedicaoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;
	private StatusExpedicao status;
	private Date data;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public StatusExpedicao getStatus() {
		return status;
	}

	public void setStatus(StatusExpedicao status) {
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
