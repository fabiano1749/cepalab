package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.repository.DescontosSalarios;


@ViewScoped
@Named
public class FechamentoDescontoSalarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DescontoSalario> listaDescontos = new ArrayList<>();
	private BigDecimal desconto = BigDecimal.ZERO;

	@Inject
	private FechamentoBean fechamentoResumo;

	@Inject
	private DescontosSalarios descontos;

	public void criaListaDescontos() {
		desconto = BigDecimal.ZERO;
		Funcionario fun = fechamentoResumo.getItem().getFuncionario();
		listaDescontos = descontos.porFuncionario(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());
		
		if(listaDescontos != null) {
			for (DescontoSalario d : listaDescontos) {
				desconto = desconto.add(d.getValor());
			}	
		}
	}

	public List<DescontoSalario> getListaDescontos() {
		return listaDescontos;
	}

	public void setListaDescontos(List<DescontoSalario> listaDescontos) {
		this.listaDescontos = listaDescontos;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
}
