package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.operacao.dominio.Amostra;
import com.cepalab.sistemaVendas.repository.Produtos;


@Named
@ViewScoped
public class AmostraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Amostra amostra;
	private List<Produto> listaProdutos;


	@Inject
	private Produtos produtos;
	
	@Inject
	private OperacaoBean operacao;

	@PostConstruct
	public void inicio() {
		listaProdutos = produtos.produtos();
		amostra = new Amostra();
	}

	public void AdicionaAmostra() {
		amostra.setOperacao(operacao.getItem());
		operacao.getItem().getAmostras().add(amostra);
		amostra = new Amostra();
	}

	public void trocaItem(Amostra amostra) {
		this.amostra = amostra;
	}
	
	public void removeAmostra() {
		List<Amostra> listaAmostra = new ArrayList<>();
		if(operacao.getItem().getAmostras() != null && !operacao.getItem().getAmostras().isEmpty()) {
			for(Amostra a : operacao.getItem().getAmostras()) {
				if(!a.getProduto().getNome().equals(amostra.getProduto().getNome())) {
					listaAmostra.add(a);
				}
			}
		}
		operacao.getItem().setAmostras(listaAmostra);
		amostra = new Amostra();
	}
	
	
	
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Amostra getAmostra() {
		return amostra;
	}

	public void setAmostra(Amostra amostra) {
		this.amostra = amostra;
	}
}
