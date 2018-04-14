package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.fechamento.dominio.Premiacao;
import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.operacao.dominio.TipoOperacao;
import com.cepalab.sistemaVendas.repository.AberturasProdutos;
import com.cepalab.sistemaVendas.repository.ComissoesTipoVendedores;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Operacoes;

@ViewScoped
@Named
public class FechamentoPremiacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal faturamentoPremiacao = BigDecimal.ZERO;
	private BigDecimal premiacaoAbertura = BigDecimal.ZERO;
	private BigDecimal premiacaoColocacao = BigDecimal.ZERO;
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Premiacao> listaPremiacao = new ArrayList<>();
	private List<Operacao> listaOperacoes = new ArrayList<>();

	private List<AberturaProduto> listaAberturas = new ArrayList<>();
	private List<AberturaProduto> listaColocacao = new ArrayList<>();
	private List<AberturaProduto> listaGeral = new ArrayList<>();
	private List<PoliticaTipoVendedorProduto> listacomissaoTipoVendedorProduto = new ArrayList<>();

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Operacoes operacoes;

	@Inject
	private ComissoesTipoVendedores comissoesTipoVendedor;

	@Inject
	private AberturasProdutos aberturas;

	@Inject
	private FechamentoBean fechamento;

	public void inicio() {
		listaPremiacao = new ArrayList<>();
		listaFuncionarios = funcionarios.funcionarios();

		if (listaFuncionarios != null) {
			for (Funcionario f : listaFuncionarios) {
				if (f.getTipo().getNome().equals("Vendedor")) {
					Premiacao premiacao = new Premiacao();
					premiacao.setFuncionario(f);
					premiacao.setFaturamentoTotal(
							calculaFaturamento(f, fechamento.getItem().getInicio(), fechamento.getItem().getFim()));

					criaListaGeral(f, fechamento.getItem().getInicio(), fechamento.getItem().getFim());
					premiacao.setPremiacaoAbertura(premiacaoAbertura);
					premiacao.setPremiacaoColocacao(premiacaoColocacao);

					faturamentoPremiacao = BigDecimal.ZERO;
					faturamentoPremiacao = faturamentoPremiacao.add(premiacaoAbertura).add(premiacaoColocacao)
							.add(premiacao.getFaturamentoTotal());
					premiacao.setFaturamentoPremiacao(faturamentoPremiacao);

					listaPremiacao.add(premiacao);
				}
			}

			Collections.sort(listaPremiacao);
		}

	}

	// Cálculo do faturamento

	public BigDecimal calculaFaturamento(Funcionario f, Date inicio, Date fim) {

		listaOperacoes = operacoes.resumo(f, inicio, fim);
		if (listaOperacoes != null) {
			BigDecimal aux = BigDecimal.ZERO;
			for (Operacao o : listaOperacoes) {
				aux = aux.add(o.getReceitaTotal());
			}
			return aux;
		}
		return BigDecimal.ZERO;

	}

	// Cálculo das aberturas e das colocações para a premiação
	public void criaListaGeral(Funcionario f, Date inicio, Date fim) {
		listaAberturas = new ArrayList<>();
		listaColocacao = new ArrayList<>();
		listacomissaoTipoVendedorProduto = new ArrayList<>();

		listacomissaoTipoVendedorProduto = comissoesTipoVendedor.ComissoesPorTipoVendedor(f.getTipoVendedor());

		listaGeral = aberturas.porFuncionarioTodas(f, inicio, fim);
		criaListas();

		premiacaoAbertura = operacoes.comissaoAberturas(f, inicio, fim).multiply(new BigDecimal(10));

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

			if (listaColocacao != null) {
				calculaComissaoColocacao();
			}
		}
	}

	public void calculaComissaoAberturas() {
		premiacaoAbertura = BigDecimal.ZERO;

		for (PoliticaTipoVendedorProduto c : listacomissaoTipoVendedorProduto) {
			for (AberturaProduto a : listaAberturas) {
				if (c.getProduto().getNome().equals(a.getProduto().getNome())) {

					// premiacaoAbertura = premiacaoAbertura.add(c.getAberturaPremiacao());

				}

			}
		}
	}

	public void calculaComissaoColocacao() {
		premiacaoColocacao = BigDecimal.ZERO;
		for (PoliticaTipoVendedorProduto c : listacomissaoTipoVendedorProduto) {
			for (AberturaProduto a : listaColocacao) {
				if (c.getProduto().getNome().equals(a.getProduto().getNome())) {
					premiacaoColocacao = premiacaoColocacao.add(c.getColocacaoPremiacao());
				}

			}
		}
	}

	public List<Premiacao> getListaPremiacao() {
		return listaPremiacao;
	}

	public void setListaPremiacao(List<Premiacao> listaPremiacao) {
		this.listaPremiacao = listaPremiacao;
	}

}
