package com.cepalab.sistemaVendas.fechamento.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;



public class Premiacao implements Serializable, Comparable<Premiacao>{

	private static final long serialVersionUID = 1L;

	
	private Funcionario funcionario = new Funcionario();
	private BigDecimal faturamentoTotal = BigDecimal.ZERO;
	private BigDecimal premiacaoAbertura = BigDecimal.ZERO;
	private BigDecimal premiacaoColocacao = BigDecimal.ZERO;
	private BigDecimal faturamentoPremiacao = BigDecimal.ZERO;
	

	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public BigDecimal getFaturamentoTotal() {
		return faturamentoTotal;
	}
	public void setFaturamentoTotal(BigDecimal faturamentoTotal) {
		this.faturamentoTotal = faturamentoTotal;
	}
	public BigDecimal getPremiacaoAbertura() {
		return premiacaoAbertura;
	}
	public void setPremiacaoAbertura(BigDecimal premiacaoAbertura) {
		this.premiacaoAbertura = premiacaoAbertura;
	}
	public BigDecimal getPremiacaoColocacao() {
		return premiacaoColocacao;
	}
	public void setPremiacaoColocacao(BigDecimal premiacaoColocacao) {
		this.premiacaoColocacao = premiacaoColocacao;
	}
	public BigDecimal getFaturamentoPremiacao() {
		return faturamentoPremiacao;
	}
	public void setFaturamentoPremiacao(BigDecimal faturamentoPremiacao) {
		this.faturamentoPremiacao = faturamentoPremiacao;
	}
	
	@Override
	public int compareTo(Premiacao o) {
		if(this.faturamentoPremiacao.compareTo(o.getFaturamentoPremiacao()) < 0) {
			return 1;
		}
		if(this.faturamentoPremiacao.compareTo(o.getFaturamentoPremiacao()) > 0) {
			return -1;
		}
		return 0;
	}

}
