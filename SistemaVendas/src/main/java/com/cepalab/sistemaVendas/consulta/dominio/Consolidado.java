package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.Produtos;

public class Consolidado implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date inicio;
	private Date fim;
	private List<FechamentoGeral> listaFechamento = new ArrayList<>();

	// Cria Lista com os fechamentos de todos os funcionários
	public void criaListaFechamentos(List<Funcionario> lista, Operacoes operacoes, CustosViagens custos,
			Produtos produtos) {
		listaFechamento = new ArrayList<>();
		for (Funcionario f : lista) {
			FechamentoGeral fecha = new FechamentoGeral();
			fecha.setInicio(inicio);
			fecha.setFim(fim);
			fecha.setFuncionario(f);
			fecha.criaResumo(operacoes, custos, produtos);
			listaFechamento.add(fecha);
		}
	}

	public ProdutoQuantidade quantidadeReceita(Funcionario fun, Produto prod) {
		for (FechamentoGeral f : listaFechamento) {
			if (f.getFuncionario().equals(fun)) {
				return f.retornaProdutoQuantidade(prod);
			}
		}
		return null;
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

	public List<FechamentoGeral> getListaFechamento() {
		return listaFechamento;
	}

	public void setListaFechamento(List<FechamentoGeral> listaFechamento) {
		this.listaFechamento = listaFechamento;
	}
}
