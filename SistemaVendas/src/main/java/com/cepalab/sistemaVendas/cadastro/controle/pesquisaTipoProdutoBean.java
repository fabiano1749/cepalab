package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;


import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.repository.TiposProdutos;


@Named
@ViewScoped
public class pesquisaTipoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TipoProduto> listaTipos;

	@Inject
	private TiposProdutos tiposProdutos;

	@PostConstruct
	public void inicio() {
		listaTipos = tiposProdutos.tipos();

	}

	public void pesquisar() {
		listaTipos = tiposProdutos.tipos();
	}

	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(TipoProduto tipo) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 100);
		opcoes.put("contentWidth", 400);

		List<String> lista = new ArrayList<>();
		lista.add("" + tipo.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("tipo", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/tipoProdutoDialogo", opcoes, params);
	}

	public List<TipoProduto> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<TipoProduto> listaTipos) {
		this.listaTipos = listaTipos;
	}

	
	
}
