package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.Produtos;

public class FechamentoGeral implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario = new Funcionario();
	private List<ProdutoQuantidade> listaProdutoQuantidade;
	private List<ReceitaFormaPagamento> listaReceitaFormaPagamento;
	private List<Operacao> listaOperacoes = new ArrayList<>();
	private List<CustoViagem> listaCustos = new ArrayList<>();
	private Date inicio;
	private Date fim;
	
	
	
	private BigDecimal faturamento = BigDecimal.ZERO;
	private BigDecimal comissaoVendas = BigDecimal.ZERO;
	private BigDecimal comissaoAberturas = BigDecimal.ZERO;
	private BigDecimal comissaoColocacao = BigDecimal.ZERO;
	private BigDecimal premiacaoAberturas = BigDecimal.ZERO;
	private BigDecimal premiacaoColocacao = BigDecimal.ZERO;

	private BigDecimal comissoesTotais = BigDecimal.ZERO;

	private BigDecimal custosViagem = BigDecimal.ZERO;
	private BigDecimal faturamentoLiquido = BigDecimal.ZERO;
	private BigDecimal faturamentoPremiacao = BigDecimal.ZERO;

	private int aberturas3p;
	private int aberturas2p;
	private int aberturas1p;

	public void limpa() {

		faturamento = BigDecimal.ZERO;
		comissaoVendas = BigDecimal.ZERO;
		comissaoAberturas = BigDecimal.ZERO;
		comissaoColocacao = BigDecimal.ZERO;
		premiacaoAberturas = BigDecimal.ZERO;
		premiacaoColocacao = BigDecimal.ZERO;

		comissoesTotais = BigDecimal.ZERO;

		custosViagem = BigDecimal.ZERO;
		faturamentoLiquido = BigDecimal.ZERO;
		faturamentoPremiacao = BigDecimal.ZERO;

		aberturas3p = 0;
		aberturas2p = 0;
		aberturas1p = 0;

		
		
	}
	
	
	public void criaListaOperacoes(Operacoes op) {
		this.listaOperacoes = new ArrayList<>();
		this.listaOperacoes = op.resumo(funcionario, inicio, fim);
	}
	
	public void criaListaCustos(CustosViagens custos) {
		listaCustos = new ArrayList<>();
		listaCustos = custos.porFuncionario(funcionario, inicio, fim);
	}
	
	public void criaListaProdutoQuantidade(Produtos produtos) {
		List<Produto> listaProdutos = produtos.produtos();
		listaProdutoQuantidade = new ArrayList<>();
		for (Produto p : listaProdutos) {
			ProdutoQuantidade pq = new ProdutoQuantidade();
			pq.setProduto(p);
			listaProdutoQuantidade.add(pq);
		}

	}
	
	public void criaListaReceitasFormaPagamento(){
		listaReceitaFormaPagamento = new ArrayList<>();
		for(FormaPagamento f : FormaPagamento.values()) {
			ReceitaFormaPagamento r = new ReceitaFormaPagamento();
			r.setFormaPag(f);
			r.setReceita(new BigDecimal("0"));
			listaReceitaFormaPagamento.add(r);
		}
		
	}
	
	public void calculaCustos() {
		if(listaCustos != null && listaCustos.size() != 0) {
			for(CustoViagem c : listaCustos) {
				incrementaCustosViagens(c.getValor());
			}
		}
	}
	
	
	
	public void calculaResumo() {
		if (listaOperacoes != null && listaOperacoes.size() != 0) {
			for (Operacao o : listaOperacoes) {
				o.valoresResumoVendedor(this);
			}
		}
		calculaComissaoesTotais();
		calculaCustos();
		calculaFaturamentoLiquido();
		calculaFaturamentoPremiacao();
	}
	
	
	public void criaResumo(Operacoes operacoes, CustosViagens custos, Produtos produtos) {
		limpa();
		criaListaOperacoes(operacoes);
		criaListaCustos(custos);
		criaListaProdutoQuantidade(produtos);
		criaListaReceitasFormaPagamento();
		calculaResumo();
	}
	
	
	
	public void calculaFaturamentoPremiacao() {
	
		faturamentoPremiacao = faturamentoPremiacao.add(faturamento).add(premiacaoAberturas).add(premiacaoColocacao);
	}

	public void calculaFaturamentoLiquido() {
	
		faturamentoLiquido = faturamento.subtract(comissoesTotais).subtract(custosViagem);
	}

	public void calculaComissaoesTotais() {
	
		comissoesTotais = comissoesTotais.add(comissaoVendas).add(comissaoAberturas).add(comissaoColocacao); 
	}
	
	public void incrementaComissaoVendas(BigDecimal valor) {
		comissaoVendas = comissaoVendas.add(valor);
	}

	public void incrementaComissaoAberturas(BigDecimal valor) {
		comissaoAberturas = comissaoAberturas.add(valor);
	}

	public void incrementaComissaoColocacao(BigDecimal valor) {
		comissaoColocacao = comissaoColocacao.add(valor);
	}

	public void incrementaPremiacaoAberturas(BigDecimal valor) {
		premiacaoAberturas = premiacaoAberturas.add(valor);
	}

	public void incrementaPremiacaoColocacao(BigDecimal valor) {
		premiacaoColocacao = premiacaoColocacao.add(valor);
	}

	public void incrementaFaturamento(BigDecimal valor) {
		faturamento = faturamento.add(valor);
	}

	public void incrementaCustosViagens(BigDecimal valor) {
		custosViagem = custosViagem.add(valor);
	}
	
	public void incrementaAberturas1p() {
		aberturas1p ++;
	}
	
	public void incrementaAberturas2p() {
		aberturas2p ++;
	}
	
	public void incrementaAberturas3p() {
		aberturas3p ++;
	}
	
	public BigDecimal receitaBoleto() {
		for(ReceitaFormaPagamento r : listaReceitaFormaPagamento) {
			if(r.getFormaPag().equals(FormaPagamento.BOLETO)) {
				return r.getReceita();
			}
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal receitaDinheiro() {
		for(ReceitaFormaPagamento r : listaReceitaFormaPagamento) {
			if(r.getFormaPag().equals(FormaPagamento.DINHEIRO)) {
				return r.getReceita();
			}
		}
		return BigDecimal.ZERO;
	}
	
	
	public BigDecimal receitaCheque() {
		for(ReceitaFormaPagamento r : listaReceitaFormaPagamento) {
			if(r.getFormaPag().equals(FormaPagamento.CHEQUE)) {
				return r.getReceita();
			}
		}
		return BigDecimal.ZERO;
	}
	
	
	public ProdutoQuantidade retornaProdutoQuantidade(Produto p) {
		for(ProdutoQuantidade pq : listaProdutoQuantidade) {
			if(pq.getProduto().equals(p)) {
				return pq;
			}
			
		}
		return null;
	}
	
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<ProdutoQuantidade> getListaProdutoQuantidade() {
		return listaProdutoQuantidade;
	}

	public void setListaProdutoQuantidade(List<ProdutoQuantidade> listaProdutoQuantidade) {
		this.listaProdutoQuantidade = listaProdutoQuantidade;
	}

	public BigDecimal getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(BigDecimal faturamento) {
		this.faturamento = faturamento;
	}

	

	public BigDecimal getComissaoVendas() {
		return comissaoVendas.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setComissaoVendas(BigDecimal comissaoVendas) {
		this.comissaoVendas = comissaoVendas;
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

	public BigDecimal getComissoesTotais() {
		return comissoesTotais.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setComissoesTotais(BigDecimal comissoesTotais) {
		this.comissoesTotais = comissoesTotais;
	}

	public BigDecimal getCustosViagem() {
		return custosViagem;
	}

	public void setCustosViagem(BigDecimal custosViagem) {
		this.custosViagem = custosViagem;
	}

	public BigDecimal getFaturamentoLiquido() {
		return faturamentoLiquido.setScale(2, BigDecimal.ROUND_UP);
	}

	public void setFaturamentoLiquido(BigDecimal faturamentoLiquido) {
		this.faturamentoLiquido = faturamentoLiquido;
	}

	public BigDecimal getFaturamentoPremiacao() {
		return faturamentoPremiacao;
	}

	public void setFaturamentoPremiacao(BigDecimal faturamentoPremiacao) {
		this.faturamentoPremiacao = faturamentoPremiacao;
	}

	public int getAberturas3p() {
		return aberturas3p;
	}

	public void setAberturas3p(int aberturas3p) {
		this.aberturas3p = aberturas3p;
	}

	public int getAberturas2p() {
		return aberturas2p;
	}

	public void setAberturas2p(int aberturas2p) {
		this.aberturas2p = aberturas2p;
	}

	public int getAberturas1p() {
		return aberturas1p;
	}

	public void setAberturas1p(int aberturas1p) {
		this.aberturas1p = aberturas1p;
	}

	public List<ReceitaFormaPagamento> getListaReceitaFormaPagamento() {
		return listaReceitaFormaPagamento;
	}

	public void setListaReceitaFormaPagamento(List<ReceitaFormaPagamento> listaReceitaFormaPagamento) {
		this.listaReceitaFormaPagamento = listaReceitaFormaPagamento;
	}

	public BigDecimal getPremiacaoAberturas() {
		return premiacaoAberturas;
	}

	public void setPremiacaoAberturas(BigDecimal premiacaoAberturas) {
		this.premiacaoAberturas = premiacaoAberturas;
	}

	public BigDecimal getPremiacaoColocacao() {
		return premiacaoColocacao;
	}

	public void setPremiacaoColocacao(BigDecimal premiacaoColocacao) {
		this.premiacaoColocacao = premiacaoColocacao;
	}

	public List<Operacao> getListaOperacoes() {
		return listaOperacoes;
	}

	public void setListaOperacoes(List<Operacao> listaOperacoes) {
		this.listaOperacoes = listaOperacoes;
	}

	public List<CustoViagem> getListaCustos() {
		return listaCustos;
	}

	public void setListaCustos(List<CustoViagem> listaCustos) {
		this.listaCustos = listaCustos;
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
}
