package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.operacao.dominio.ResumoConsignacaoVenda;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;
import com.cepalab.sistemaVendas.repository.Consignados;
import com.cepalab.sistemaVendas.repository.Vendas;

@ViewScoped
@Named
public class FechamentoVendasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Consignados consignados;

	@Inject
	private Vendas vendas;

	@Inject
	private FechamentoBean fechamentoResumo;

	private List<ResumoConsignacaoVenda> listaResumoConsignacaoVenda = new ArrayList<>();
	private List<Consignacao> listaConsignacao = new ArrayList<>();
	private List<Venda> listaVenda = new ArrayList<>();
	private BigDecimal comissao = BigDecimal.ZERO;
	
	
	
	public void criaListaVendas() {
		listaResumoConsignacaoVenda = new ArrayList<>();
		listaConsignacao = new ArrayList<>();
		comissao = BigDecimal.ZERO;
		
		Funcionario fun = fechamentoResumo.getItem().getFuncionario();
		listaConsignacao = consignados.porFuncionario(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());
		listaVenda = vendas.porFuncionario(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());

		if (listaConsignacao != null) {
			for (Consignacao c : listaConsignacao) {
				if (c.getVendidos() != 0) {
					ResumoConsignacaoVenda r = new ResumoConsignacaoVenda();
					r.setProduto(c.getProduto());
					r.setVendidos(c.getVendidos());
					r.setReceita(c.receita());
					r.setComissao(c.comissao());
					r.setTaxaComissao(c.getTaxaComissao());
					r.setOperacao(c.getOperacao());
					comissao = comissao.add(c.comissao());
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
					r.setOperacao(v.getOperacao());
					comissao = comissao.add(v.comissao());
					listaResumoConsignacaoVenda.add(r);
				}
			}
		}

	}

	public List<ResumoConsignacaoVenda> getListaResumoConsignacaoVenda() {
		return listaResumoConsignacaoVenda;
	}

	public void setListaResumoConsignacaoVenda(List<ResumoConsignacaoVenda> listaResumoConsignacaoVenda) {
		this.listaResumoConsignacaoVenda = listaResumoConsignacaoVenda;
	}

	public BigDecimal getComissao() {
		return comissao.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
}
