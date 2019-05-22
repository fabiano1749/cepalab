package com.cepalab.sistemaVendas.repository.filter;

import java.io.Serializable;
import java.util.Date;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusCheque;

public class ChequeFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private Funcionario funcionario;
	private Cliente cliente;
	private StatusCheque status;
	private Date bomParaInicio;
	private Date bomParaFim;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public StatusCheque getStatus() {
		return status;
	}
	public void setStatus(StatusCheque status) {
		this.status = status;
	}
	public Date getBomParaInicio() {
		return bomParaInicio;
	}
	public void setBomParaInicio(Date bomParaInicio) {
		this.bomParaInicio = bomParaInicio;
	}
	public Date getBomParaFim() {
		return bomParaFim;
	}
	public void setBomParaFim(Date bomParaFim) {
		this.bomParaFim = bomParaFim;
	}
}
