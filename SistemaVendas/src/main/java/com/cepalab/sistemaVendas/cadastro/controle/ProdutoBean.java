package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.PodeConsignar;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoCalculoAberturaColocacao;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.repository.TiposProdutos;
import com.cepalab.sistemaVendas.service.CadastroProdutoService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Produto item;
	private List<Produto> itens;
	private List<TipoProduto> listaTipos;
	private Produto itemAuxiliar;
	private boolean podeConsignar = false;

	@Inject
	private Produtos produtos;

	@Inject
	private CadastroProdutoService cadastroProduto;

	@Inject
	private TiposProdutos tipos;

	public ProdutoBean() {
		limpar();
	}

	public void limpar() {
		item = new Produto();
		itemAuxiliar = new Produto();
		podeConsignar = false;
	}

	@PostConstruct
	public void inicio() {
		itens = produtos.produtos();
		listaTipos = tipos.tipos();
	}

	public void salvar() {
		try {
			item.setPosicao(ultimaPosicao());
			cadastroProduto.salvar(item);
			FacesUtil.addInfoMessage("Produto salvo com sucesso!");
			limpar();
			inicio();

		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void editar() {
		try {
			cadastroProduto.salvar(itemAuxiliar);
			FacesUtil.addInfoMessage("Produto alterado com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			inicio();
			limpar();
		}

	}

	public void excluir() {
		try {
			produtos.remover(itemAuxiliar);
			inicio();
			limpar();
			FacesUtil.addInfoMessage("Produto excluido com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}

	}

	public PodeConsignar[] podeConsignar() {
		return PodeConsignar.values();
	}

	public TipoCalculoAberturaColocacao[] calculoComissao() {
		return TipoCalculoAberturaColocacao.values();
	}

	public void troca(Produto item) {
		this.itemAuxiliar = item;
	}

	public void podeConsignarValor() {

		if (item.getPodeConsignar().equals(PodeConsignar.SIM)) {
			podeConsignar = false;
		} else {
			podeConsignar = true;
		}
	}

	public int ultimaPosicao() {
		if (itens == null || itens.isEmpty()) {
			return 1;
		}
		else return itens.get(itens.size()-1).getPosicao() + 1;
	}
	
	
	public Produto getitem() {
		return item;
	}

	public void setItem(Produto item) {
		this.item = item;
	}

	public List<Produto> getItens() {
		return itens;
	}

	public void setItens(List<Produto> itens) {
		this.itens = itens;
	}

	public Produto getItemAuxiliar() {
		return itemAuxiliar;
	}

	public void setItemAuxiliar(Produto itemAuxiliar) {
		this.itemAuxiliar = itemAuxiliar;
	}

	public boolean isPodeConsignar() {
		return podeConsignar;
	}

	public void setPodeConsignar(boolean podeConsignar) {
		this.podeConsignar = podeConsignar;
	}

	public List<TipoProduto> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<TipoProduto> listaTipos) {
		this.listaTipos = listaTipos;
	}

}
