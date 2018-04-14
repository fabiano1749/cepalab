package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;
import com.cepalab.sistemaVendas.operacao.dominio.Receita;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ReceitaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Receita receita = new Receita();
	private BigDecimal receitaTotal = BigDecimal.ZERO;
	private BigDecimal receitaRestante = BigDecimal.ZERO;
	private int numFormaPag = 1;

	@Inject
	private ResumoOperacaoBean resumo;

	@Inject
	private OperacaoBean operacao;

	public void iniciaReceitaTotal() {
		receitaTotal = resumo.getReceitatotal();
		receitaRestante = new BigDecimal("0");
		receitaRestante = receitaRestante.add(receitaTotal);
	}

	public void criaListaReceitas() {
		iniciaReceitaTotal();
		operacao.getItem().setReceitas(new ArrayList<>());

		if (receitaTotal.compareTo(BigDecimal.ZERO) > 0) {

			for (int i = 0; i < numFormaPag; i++) {
				Receita r = new Receita();
				r.setOperacao(operacao.getItem());
				if (i == 0) {

					r.setValor(receitaTotal);
					receitaRestante = receitaRestante.subtract(receitaTotal);
				} else {
					r.setValor(new BigDecimal("0"));
				}

				//r.setFormaPagamento(null);
				operacao.getItem().getReceitas().add(r);
			}
		}
	}

	public BigDecimal receitasInformadas() {
		BigDecimal aux = new BigDecimal("0");
		for (Receita r : operacao.getItem().getReceitas()) {
			aux = aux.add(r.getValor());
		}
		return aux;
	}

	public void validaValor(Receita r) {
		BigDecimal aux = receitasInformadas();
		if (receitaTotal.compareTo(aux) < 0) {
			r.setValor(new BigDecimal("0"));
			FacesUtil.addErrorMessage("Soma de valores excede a receita total!");
		} else {
			receitaRestante = receitaTotal.subtract(aux);
		}

	}

	public boolean receitaMaiorQueZero() {
		return receitaTotal.compareTo(BigDecimal.ZERO) > 0;
	}

	public FormaPagamento[] formasPagamento() {
		return FormaPagamento.values();
	}

	public void confirmarOperacao() {
		if (receitaTotal.compareTo(BigDecimal.ZERO) > 0 && !operacao.getItem().getReceitas().isEmpty()) {
			BigDecimal aux = BigDecimal.ZERO;
			for (Receita r : operacao.getItem().getReceitas()) {
				aux = aux.add(r.getValor());
			}
			if (aux.compareTo(receitaTotal) == 0) {
				operacao.salvar();
			} else {
				FacesUtil.addErrorMessage("Soma de valores difere do total de receitas!");
			}
		} else {
			operacao.getItem().setReceitas(new ArrayList<>());
			operacao.salvar();
		}
	}

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}

	public BigDecimal getReceitaTotal() {
		return receitaTotal;
	}

	public void setReceitaTotal(BigDecimal receitaTotal) {
		this.receitaTotal = receitaTotal;
	}

	public int getNumFormaPag() {
		return numFormaPag;
	}

	public void setNumFormaPag(int numFormaPag) {
		this.numFormaPag = numFormaPag;
	}

	public BigDecimal getReceitaRestante() {
		return receitaRestante;
	}

	public void setReceitaRestante(BigDecimal receitaRestante) {
		this.receitaRestante = receitaRestante;
	}

}
