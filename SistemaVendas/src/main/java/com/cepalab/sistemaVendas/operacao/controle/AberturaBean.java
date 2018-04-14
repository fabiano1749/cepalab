package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.repository.AberturasProdutos;

@Named
@ViewScoped
public class AberturaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private OperacaoBean operacao;

	@Inject
	private AberturasProdutos aberturas;

	public void inicio() {
		operacao.getItem().setAberturasProdutos(aberturas.porOperacao(operacao.getItem()));
	}

}
