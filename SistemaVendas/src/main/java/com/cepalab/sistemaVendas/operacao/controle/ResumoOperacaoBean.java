package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.operacao.dominio.ResumoConsignacaoVenda;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;


@Named
@ViewScoped
public class ResumoOperacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal receitatotal = BigDecimal.ZERO;
	private BigDecimal comissaototal = BigDecimal.ZERO;
	private List<ResumoConsignacaoVenda> listaResumoConsignacaoVenda = new ArrayList<>();
	private List<Consignacao> listaConsignacao = new ArrayList<>();
	private List<Venda> listaVenda = new ArrayList<>();

	@Inject
	private OperacaoBean operacao;
	

	public void inicio() {
		receitatotal = BigDecimal.ZERO;
		comissaototal = BigDecimal.ZERO;
	}

	public void alimentaListaResumoConsignacaoVenda() {
		inicio();
		listaResumoConsignacaoVenda = new ArrayList<>();
		listaConsignacao = operacao.getItem().getConsignacoes();
		listaVenda = operacao.getItem().getVendas();
		if (listaConsignacao != null) {
			for (Consignacao c : listaConsignacao) {
				if (c.getVendidos() != 0) {
					ResumoConsignacaoVenda r = new ResumoConsignacaoVenda();
					r.setProduto(c.getProduto());
					r.setVendidos(c.getVendidos());
					r.setReceita(c.receita());
					r.setComissao(c.comissao());
					r.setTaxaComissao(c.getTaxaComissao());
					receitatotal = receitatotal.add(c.receita());
					comissaototal = comissaototal.add(c.comissao());
					listaResumoConsignacaoVenda.add(r);
				}
			}
		}

		if (listaVenda != null) {
			for (Venda v : listaVenda) {
				if (v.getQuantidade() != 0) {
					ResumoConsignacaoVenda r = new ResumoConsignacaoVenda();
					r.setProduto(v.getProduto());
					r.setVendidos(v.getQuantidade());
					r.setReceita(v.receita());
					r.setComissao(v.comissao());
					r.setTaxaComissao(v.getTaxaComissao());
					receitatotal = receitatotal.add(v.receita());
					comissaototal = comissaototal.add(v.comissao());
					listaResumoConsignacaoVenda.add(r);
				}
			}
		}
	}

	public Boolean receitaMaiorQueZero() {
		return receitatotal.compareTo(BigDecimal.ZERO) > 0;
	}
	
	
	public BigDecimal getReceitatotal() {
		return receitatotal.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setReceitatotal(BigDecimal receitatotal) {
		this.receitatotal = receitatotal;
	}

	public BigDecimal getComissaototal() {
		return comissaototal.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setComissaototal(BigDecimal comissaototal) {
		this.comissaototal = comissaototal;
	}

	public List<ResumoConsignacaoVenda> getListaResumoConsignacaoVenda() {
		return listaResumoConsignacaoVenda;
	}

	public void setListaResumoConsignacaoVenda(List<ResumoConsignacaoVenda> listaResumoConsignacaoVenda) {
		this.listaResumoConsignacaoVenda = listaResumoConsignacaoVenda;
	}
	
}
