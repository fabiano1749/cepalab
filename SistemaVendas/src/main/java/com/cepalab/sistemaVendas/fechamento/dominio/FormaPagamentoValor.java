package com.cepalab.sistemaVendas.fechamento.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;
import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;

public class FormaPagamentoValor implements Serializable {

	private static final long serialVersionUID = 1L;

	private FormaPagamento forma;
	private BigDecimal valor;
	private BigDecimal comissao;
	private BigDecimal taxaComissao;
	
	public FormaPagamentoValor(FormaPagamento forma, BigDecimal valor) {
		this.forma = forma;
		this.valor = valor;
	}
	
	public FormaPagamentoValor() {
		
	}
	
	public static List<FormaPagamentoValor> resumoFormaPagamento(List<RecebimentoInadiplente> inadimplentes){
		List <FormaPagamentoValor> list = new ArrayList<>();
		if(inadimplentes != null && !inadimplentes.isEmpty()) {
			 Map<FormaPagamento, Double> sum = inadimplentes.stream().collect(
		                Collectors.groupingBy(RecebimentoInadiplente::getFormaPagamento, Collectors.summingDouble(RecebimentoInadiplente::getValorDouble)));
			
			for(FormaPagamento key : sum.keySet()) {
				FormaPagamentoValor f = new FormaPagamentoValor(key,  BigDecimal.valueOf(sum.get(key)));
				list.add(f);
			}
		}
		return list;
	}
	
	public static BigDecimal dinheiroInadimplentes(List<RecebimentoInadiplente> inadimplentes){
		if(inadimplentes != null && !inadimplentes.isEmpty()) {
			Double dinheiro = inadimplentes.stream().filter(x -> x.isDinheiro()).map(x -> x.getValor())
					.mapToDouble(BigDecimal::doubleValue).sum();
			return BigDecimal.valueOf(dinheiro);					
		}
		return BigDecimal.ZERO;
	}
	
	public void incrementaReceita(BigDecimal valor) {
		this.valor = this.valor.add(valor);
	}
	
	public FormaPagamento getForma() {
		return forma;
	}

	public void setForma(FormaPagamento forma) {
		this.forma = forma;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getTaxaComissao() {
		return taxaComissao;
	}

	public void setTaxaComissao(BigDecimal taxaComissao) {
		this.taxaComissao = taxaComissao;
	}
	
	public String taxaComissaoPercentualFormatada() {
		if(this.taxaComissao != null) {
			DecimalFormat df = new DecimalFormat("#,###.00");
			return  df.format(this.taxaComissao.multiply(new BigDecimal(100)).doubleValue()) + " %";
		}
		return "0.0";
		
		}
}
