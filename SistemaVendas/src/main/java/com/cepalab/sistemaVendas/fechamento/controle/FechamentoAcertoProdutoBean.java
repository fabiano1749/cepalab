package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.Expedicao.dominio.ExpedProduto;
import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.fechamento.dominio.AcertoProduto;
import com.cepalab.sistemaVendas.operacao.dominio.Amostra;
import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;
import com.cepalab.sistemaVendas.repository.Amostras;
import com.cepalab.sistemaVendas.repository.Consignados;
import com.cepalab.sistemaVendas.repository.Expedicoes;
import com.cepalab.sistemaVendas.repository.Vendas;

@ViewScoped
@Named
public class FechamentoAcertoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<AcertoProduto> listaAcertoProduto = new ArrayList<>();
	private List<Consignacao> listaConsiganacao = new ArrayList<>();
	private List<Venda> listaVendas = new ArrayList<>();
	private List<Amostra> listaAmostra = new ArrayList<>();
	private List<Expedicao> listaExpedicao = new ArrayList<>();
	private Expedicao expedicao;
	private BigDecimal descontoTotal = BigDecimal.ZERO;

	@Inject
	private Expedicoes expedicoes;

	@Inject
	private FechamentoBean fechamentoBean;

	@Inject
	private Consignados consignados;

	@Inject
	private Vendas vendas;

	@Inject
	private Amostras amostras;

	private void limpa() {
		listaAcertoProduto = new ArrayList<>();
		listaConsiganacao = new ArrayList<>();
		listaVendas = new ArrayList<>();
		listaAmostra = new ArrayList<>();
		descontoTotal = BigDecimal.ZERO;
	}

	public void inicio() {
		limpa();
		if(fechamentoBean.isAdministrador()) {
			expedicao = expedicoes.porId(expedicao.getId());
		}
		else {
			Long id = expedicoes.idUltimaExpedicao(fechamentoBean.getItem().getFuncionario());
			expedicao = expedicoes.porId(id);
		}
		
		if (expedicao != null) {
			listaConsiganacao = consignados.porFuncionario(fechamentoBean.getItem().getFuncionario(),
					fechamentoBean.getItem().getInicio(), fechamentoBean.getItem().getFim());
			listaVendas = vendas.porFuncionario(fechamentoBean.getItem().getFuncionario(),
					fechamentoBean.getItem().getInicio(), fechamentoBean.getItem().getFim());
			listaAmostra = amostras.porFuncionario(fechamentoBean.getItem().getFuncionario(),
					fechamentoBean.getItem().getInicio(), fechamentoBean.getItem().getFim());
			
			for (ExpedProduto ex : expedicao.getExpedProdutos()) {
			
				AcertoProduto acerto = new AcertoProduto();
				acerto.setExpedProduto(ex);
				alimentaQuantidades(acerto);
				acerto.calculaEstoqueEsperado();
				acerto.calculaDiferenca();
				acerto.calculaDesconto();
				descontoTotal = descontoTotal.add(acerto.getDesconto());
				listaAcertoProduto.add(acerto);
				fechamentoBean.getItem().setDiferencaProdutos(descontoTotal);
			}
		}
	}

	public void alimentaQuantidades(AcertoProduto acerto) {
		
		if (listaConsiganacao != null) {
			
			for (Consignacao c : listaConsiganacao) {
				if (c.getProduto().getId().equals(acerto.getExpedProduto().getProduto().getId())) {
					if (c.getConsignados() != null && c.getProntaEntrega() == true) {
						acerto.adicionaConsignado(c.getConsignados().intValue());
					}
					acerto.adicionaDevolvidos(c.getDevolvidos());
				}
			}

		}

		if (listaVendas != null) {
			
			for (Venda v : listaVendas) {

				if (v.getProduto().getId().equals(acerto.getExpedProduto().getProduto().getId())) {
					if (v.getProntaEntrega() == true) {
						acerto.adicionaVendidos(v.getQuantidade());
					}
					if (v.getDevolvidos() != null) {
						acerto.adicionaDevolvidos(v.getDevolvidos().intValue());
					}
					if (v.getRepostos() != null) {
						acerto.adicionaRepostos(v.getRepostos().intValue());
					}
				}
			}

		}

		if (listaAmostra != null) {
			for (Amostra a : listaAmostra) {
				if (a.getProduto().getId().equals(acerto.getExpedProduto().getProduto().getId())) {
					acerto.adicionaAmostra(a.getQuantidade());
				}
			}
		}

	}

	public boolean semExpedicao() {
		if (expedicao == null) {
			return false;
		} else
			return true;
	}

	public void criaListaExpedicao() {
		listaExpedicao = expedicoes.ultimasExpedicoesFechadasFuncionario(fechamentoBean.getFuncionario());
	}
	
	public Expedicao getExpedicao() {
		return expedicao;
	}

	public void setExpedicao(Expedicao expedicao) {
		this.expedicao = expedicao;
	}

	public List<AcertoProduto> getListaAcertoProduto() {
		return listaAcertoProduto;
	}

	public void setListaAcertoProduto(List<AcertoProduto> listaAcertoProduto) {
		this.listaAcertoProduto = listaAcertoProduto;
	}

	public BigDecimal getDescontoTotal() {
		return descontoTotal.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setDescontoTotal(BigDecimal descontoTotal) {
		this.descontoTotal = descontoTotal;
	}

	public List<Expedicao> getListaExpedicao() {
		return listaExpedicao;
	}

	public void setListaExpedicao(List<Expedicao> listaExpedicao) {
		this.listaExpedicao = listaExpedicao;
	}

	
	
}
