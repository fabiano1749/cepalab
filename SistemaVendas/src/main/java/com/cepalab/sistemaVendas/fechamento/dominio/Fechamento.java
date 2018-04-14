package com.cepalab.sistemaVendas.fechamento.dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;

@SuppressWarnings("serial")
@Entity
@Table(name = "fechamento")
public class Fechamento extends GenericDTO {

	private Date inicio;
	private Date fim;
	private Funcionario funcionario;
	private BigDecimal faturamentoTotal;
	private BigDecimal comissaoTotal;
	private BigDecimal comissaoVendas;
	private BigDecimal comissaoAberturas;
	private BigDecimal comissaoColocacao;
	private BigDecimal faturamentoPremiacao;
	private BigDecimal premiacaoAberturas;
	private BigDecimal premiacaoColocacao;
	private BigDecimal premiacao;
	private BigDecimal repasse;
	private BigDecimal custosTotais;
	private BigDecimal despesasTotais;
	private BigDecimal comissaoRecolhida;
	private BigDecimal comissaoRessarcida;
	private BigDecimal salarioDescontado;
	private BigDecimal recebimentoInadimplente;

	private BigDecimal boleto;
	private BigDecimal cartao;
	private BigDecimal cheque;
	private BigDecimal dinheiro;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Column(precision = 10, scale = 2, name="faturamento_total")
	public BigDecimal getFaturamentoTotal() {
		return faturamentoTotal;
	}

	public void setFaturamentoTotal(BigDecimal faturamentoTotal) {
		this.faturamentoTotal = faturamentoTotal;
	}

	@Column(precision = 10, scale = 2, name="comissao_total")
	public BigDecimal getComissaoTotal() {
		return comissaoTotal;
	}

	public void setComissaoTotal(BigDecimal comissaoTotal) {
		this.comissaoTotal = comissaoTotal;
	}

	@Column(precision = 10, scale = 2, name="comissao_vendas")
	public BigDecimal getComissaoVendas() {
		return comissaoVendas;
	}

	public void setComissaoVendas(BigDecimal comissaoVendas) {
		this.comissaoVendas = comissaoVendas;
	}

	@Column(precision = 10, scale = 2, name="comissao_aberturas")
	public BigDecimal getComissaoAberturas() {
		return comissaoAberturas;
	}

	public void setComissaoAberturas(BigDecimal comissaoAberturas) {
		this.comissaoAberturas = comissaoAberturas;
	}

	@Column(precision = 10, scale = 2, name="comissao_colocacao")
	public BigDecimal getComissaoColocacao() {
		return comissaoColocacao;
	}

	public void setComissaoColocacao(BigDecimal comissaoColocacao) {
		this.comissaoColocacao = comissaoColocacao;
	}

	@Column(precision = 10, scale = 2, name="faturamento_premiacao")
	public BigDecimal getFaturamentoPremiacao() {
		return faturamentoPremiacao;
	}

	public void setFaturamentoPremiacao(BigDecimal faturamentoPremiacao) {
		this.faturamentoPremiacao = faturamentoPremiacao;
	}

	@Column(precision = 10, scale = 2, name="premiacao_aberturas")
	public BigDecimal getPremiacaoAberturas() {
		return premiacaoAberturas;
	}

	public void setPremiacaoAberturas(BigDecimal premiacaoAberturas) {
		this.premiacaoAberturas = premiacaoAberturas;
	}

	@Column(precision = 10, scale = 2, name="premiacao_colocacao")
	public BigDecimal getPremiacaoColocacao() {
		return premiacaoColocacao;
	}

	public void setPremiacaoColocacao(BigDecimal premiacaoColocacao) {
		this.premiacaoColocacao = premiacaoColocacao;
	}

	@Column(precision = 10, scale = 2, name="premiacao")
	public BigDecimal getPremiacao() {
		return premiacao;
	}

	public void setPremiacao(BigDecimal premiacao) {
		this.premiacao = premiacao;
	}

	@Column(precision = 10, scale = 2, name="repasse")
	public BigDecimal getRepasse() {
		return repasse;
	}

	public void setRepasse(BigDecimal repasse) {
		this.repasse = repasse;
	}

	@Column(precision = 10, scale = 2, name="custos_totais")
	public BigDecimal getCustosTotais() {
		return custosTotais;
	}

	public void setCustosTotais(BigDecimal custosTotais) {
		this.custosTotais = custosTotais;
	}

	@Column(precision = 10, scale = 2, name="despesas_totais")
	public BigDecimal getDespesasTotais() {
		return despesasTotais;
	}

	public void setDespesasTotais(BigDecimal despesasTotais) {
		this.despesasTotais = despesasTotais;
	}

	@Column(precision = 10, scale = 2, name="comissao_recolhida")
	public BigDecimal getComissaoRecolhida() {
		return comissaoRecolhida;
	}

	public void setComissaoRecolhida(BigDecimal comissaoRecolhida) {
		this.comissaoRecolhida = comissaoRecolhida;
	}

	@Column(precision = 10, scale = 2, name="comissao_ressarcida")
	public BigDecimal getComissaoRessarcida() {
		return comissaoRessarcida;
	}

	public void setComissaoRessarcida(BigDecimal comissaoRessarcida) {
		this.comissaoRessarcida = comissaoRessarcida;
	}

	@Column(precision = 10, scale = 2, name="salario_descontado")
	public BigDecimal getSalarioDescontado() {
		return salarioDescontado;
	}

	public void setSalarioDescontado(BigDecimal salarioDescontado) {
		this.salarioDescontado = salarioDescontado;
	}

	@Column(precision = 10, scale = 2, name="recebimento_Inadimplente")
	public BigDecimal getRecebimentoInadimplente() {
		return recebimentoInadimplente;
	}

	public void setRecebimentoInadimplente(BigDecimal recebimentoInadimplente) {
		this.recebimentoInadimplente = recebimentoInadimplente;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getBoleto() {
		return boleto;
	}

	public void setBoleto(BigDecimal boleto) {
		this.boleto = boleto;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getCartao() {
		return cartao;
	}

	public void setCartao(BigDecimal cartao) {
		this.cartao = cartao;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getCheque() {
		return cheque;
	}

	public void setCheque(BigDecimal cheque) {
		this.cheque = cheque;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(BigDecimal dinheiro) {
		this.dinheiro = dinheiro;
	}

}
