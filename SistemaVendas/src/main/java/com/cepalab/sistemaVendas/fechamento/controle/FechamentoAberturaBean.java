package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.operacao.dominio.TipoOperacao;
import com.cepalab.sistemaVendas.repository.AberturasProdutos;
import com.cepalab.sistemaVendas.repository.ComissoesTipoVendedores;
import com.cepalab.sistemaVendas.repository.Operacoes;

@ViewScoped
@Named
public class FechamentoAberturaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<AberturaProduto> listaAberturas = new ArrayList<>();
	private List<AberturaProduto> listaColocacao = new ArrayList<>();
	private List<AberturaProduto> listaGeral = new ArrayList<>();
	private List<PoliticaTipoVendedorProduto> listacomissaoTipoVendedorProduto = new ArrayList<>();
	private BigDecimal comissaoColocacao = BigDecimal.ZERO;
	private BigDecimal premiacaoColocacao = BigDecimal.ZERO;
	private BigDecimal comissaoAberturas = BigDecimal.ZERO;
	private BigDecimal premiacaoAbertura = BigDecimal.ZERO;
	
	private List<Operacao> listaOperacaoAberturas;
	
	@Inject
	private ComissoesTipoVendedores comissoesTipoVendedor;

	@Inject
	private AberturasProdutos aberturas;

	@Inject
	private Operacoes operacoes;
	
	@Inject
	private FechamentoBean fechamentoResumo;

	
	public void criaListaGeral() {
		listaAberturas = new ArrayList<>();
		listaColocacao = new ArrayList<>();

		listacomissaoTipoVendedorProduto = comissoesTipoVendedor
				.ComissoesPorTipoVendedor(fechamentoResumo.getItem().getFuncionario().getTipoVendedor());

		Funcionario fun = fechamentoResumo.getItem().getFuncionario();
		listaGeral = aberturas.porFuncionarioTodas(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());
	
		
		comissaoAberturas = operacoes.comissaoAberturas(fechamentoResumo.getItem().getFuncionario(), fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());
		premiacaoAbertura = comissaoAberturas.multiply(new BigDecimal(10));
		
		premiacaoColocacao = operacoes.premiacaoColocaoFitas(fechamentoResumo.getItem().getFuncionario(), fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim()); 
		
		fechamentoResumo.setFaturamentoPremiacao(fechamentoResumo.getFaturamentoPremiacao().add(premiacaoAbertura));
	
		BigDecimal aux = BigDecimal.ZERO;
		aux = aux.add(premiacaoColocacao);
		aux = aux.add(fechamentoResumo.getFaturamentoPremiacao());
		fechamentoResumo.setFaturamentoPremiacao(aux);
		criaListas();
	}


	
	
	
	public void criaListas() {
		if (listaGeral != null) {
			for (AberturaProduto a : listaGeral) {
				if (a.getOperacao().getTipo().equals(TipoOperacao.ABERTURA)) {
					listaAberturas.add(a);
				} else if (a.getColocacao() == true) {
					listaColocacao.add(a);
				}

			}

			/*
			if (listaAberturas != null) {
				calculaComissaoAberturas();
			}
			
			if (listaColocacao != null) {
				calculaComissaoColocacao();
			}
			*/
		}
	}


	/*
	public void calculaComissaoAberturas() {
		premiacaoAbertura = BigDecimal.ZERO;
		comissaoAberturas = BigDecimal.ZERO;
		for (ComissaoTipoVendedorProduto c : listacomissaoTipoVendedorProduto) {
			for (AberturaProduto a : listaAberturas) {
				if (c.getProduto().getNome().equals(a.getProduto().getNome())) {
					if (comissaoAberturas.compareTo(new BigDecimal("30")) < 0) {
						comissaoAberturas = comissaoAberturas.add(c.getAbertura());
						premiacaoAbertura = premiacaoAbertura.add(c.getAberturaPremiacao());
					}
				}

			}
		}

		BigDecimal aux = BigDecimal.ZERO;
		aux = aux.add(premiacaoAbertura);
		aux = aux.add(fechamentoResumo.getFaturamentoPremiacao());
		fechamentoResumo.setFaturamentoPremiacao(aux);
	}


	public void calculaComissaoColocacao() {
		
		comissaoColocacao = BigDecimal.ZERO;
		premiacaoColocacao = BigDecimal.ZERO;
		for (ComissaoTipoVendedorProduto c : listacomissaoTipoVendedorProduto) {
			for (AberturaProduto a : listaColocacao) {
				if (c.getProduto().getNome().equals(a.getProduto().getNome()) && !a.getProduto().getTipo().getNome().equals("Fita")) {
					comissaoColocacao = comissaoColocacao.add(c.getColocacao());
					premiacaoColocacao = premiacaoColocacao.add(c.getColocacaoPremiacao());
				}

			}
		}
		int i = numeroColocacaoFita();
		premiacaoColocacao = premiacaoColocacao.add(new BigDecimal(i).multiply(new BigDecimal("100")));
		BigDecimal aux = BigDecimal.ZERO;
		aux = aux.add(premiacaoColocacao);
		aux = aux.add(fechamentoResumo.getFaturamentoPremiacao());
		fechamentoResumo.setFaturamentoPremiacao(aux);
	}
	
	public int numeroColocacaoFita() {
		int f = 0;
		Operacao o = new Operacao();
		for(AberturaProduto a : listaColocacao) {
			if(a.getProduto().getTipo().getNome().equals("Fita") && f == 0 && a.getOperacao().getTipo() != TipoOperacao.ABERTURA ) {
				f++;
				o = a.getOperacao();
			}else if(a.getProduto().getTipo().getNome().equals("Fita") && o!= a.getOperacao() && a.getOperacao().getTipo() != TipoOperacao.ABERTURA) {
				f++;
				o = a.getOperacao();
			}
			
		}
		return f;
	}
	
*/	

	public List<AberturaProduto> getListaAberturas() {
		return listaAberturas;
	}

	public void setListaAberturas(List<AberturaProduto> listaAberturas) {
		this.listaAberturas = listaAberturas;
	}

	public List<AberturaProduto> getListaColocacao() {
		return listaColocacao;
	}

	public void setListaColocacao(List<AberturaProduto> listaColocacao) {
		this.listaColocacao = listaColocacao;
	}

	public BigDecimal getPremiacaoAbertura() {
		return premiacaoAbertura;
	}

	public void setPremiacaoAbertura(BigDecimal premiacaoAbertura) {
		this.premiacaoAbertura = premiacaoAbertura;
	}

	public BigDecimal getComissaoAberturas() {
		return comissaoAberturas;
	}

	public void setComissaoAberturas(BigDecimal comissaoAberturas) {
		this.comissaoAberturas = comissaoAberturas;
	}

	public BigDecimal getComissaoColocacao() {
		return comissaoColocacao;
	}

	public void setComissaoColocacao(BigDecimal comissaoColocacao) {
		this.comissaoColocacao = comissaoColocacao;
	}

	public BigDecimal getPremiacaoColocacao() {
		return premiacaoColocacao;
	}

	public void setPremiacaoColocacao(BigDecimal premiacaoColocacao) {
		this.premiacaoColocacao = premiacaoColocacao;
	}

	public List<Operacao> getListaOperacaoAberturas() {
		return listaOperacaoAberturas;
	}

	public void setListaOperacaoAberturas(List<Operacao> listaOperacaoAberturas) {
		this.listaOperacaoAberturas = listaOperacaoAberturas;
	}
	
}
