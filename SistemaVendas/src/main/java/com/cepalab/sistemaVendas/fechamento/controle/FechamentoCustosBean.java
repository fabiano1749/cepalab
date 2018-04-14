package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.repository.CustosViagens;

@ViewScoped
@Named
public class FechamentoCustosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CustoViagem> listaCustos = new ArrayList<>();
	private BigDecimal custoTotal = BigDecimal.ZERO;

	@Inject
	private FechamentoBean fechamentoResumo;

	@Inject
	private CustosViagens custos;

	public void criaListaCustos() {
		custoTotal = BigDecimal.ZERO;
		Funcionario fun = fechamentoResumo.getItem().getFuncionario();
		listaCustos = custos.porFuncionario(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());

		if (listaCustos != null) {
			for (CustoViagem c : listaCustos) {
				custoTotal = custoTotal.add(c.getValor());
			}
		}

	}

	public List<CustoViagem> getListaCustos() {
		return listaCustos;
	}

	public void setListaCustos(List<CustoViagem> listaCustos) {
		this.listaCustos = listaCustos;
	}

	public BigDecimal getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(BigDecimal custoTotal) {
		this.custoTotal = custoTotal;
	}

}
