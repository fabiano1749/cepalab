package com.cepalab.sistemaVendas.repository.filter;

import java.io.Serializable;

import com.cepalab.sistemaVendas.cadastro.dominio.EstadosBrasileiros;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;

public class ClienteFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String nome;
	private Funcionario funcionario;
	private Rota rota;
	private EstadosBrasileiros uf;
	private String cidade;
	private String cnpj;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public EstadosBrasileiros getUf() {
		return uf;
	}

	public void setUf(EstadosBrasileiros uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	
}
