package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.repository.DespesasVendedores;

@ViewScoped
@Named
public class FechamentoDespesasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DespesaVendedor> listaDespesas = new ArrayList<>();
	private BigDecimal despesaTotal = BigDecimal.ZERO;
	
	@Inject
	private FechamentoBean fechamentoResumo;

	@Inject
	private DespesasVendedores despesas;

	public void criaListaDespesas() {
		despesaTotal = BigDecimal.ZERO;
		Funcionario fun = fechamentoResumo.getItem().getFuncionario();
		listaDespesas = despesas.porFuncionario(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());
		if(listaDespesas != null) {
			for(DespesaVendedor d : listaDespesas) {
				despesaTotal = despesaTotal.add(d.getValor());
			}
		}
		
	}

	public List<DespesaVendedor> getListaDespesas() {
		return listaDespesas;
	}

	public void setListaDespesas(List<DespesaVendedor> listaDespesas) {
		this.listaDespesas = listaDespesas;
	}

	public BigDecimal getDespesaTotal() {
		return despesaTotal;
	}

	public void setDespesaTotal(BigDecimal despesaTotal) {
		this.despesaTotal = despesaTotal;
	}



}
