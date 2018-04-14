package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.repository.TiposVendedores;
import com.cepalab.sistemaVendas.service.CadastroTipoVendedorService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TipoVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoVendedor item;
	private List<TipoVendedor> itens;
	private TipoVendedor itemAux;
	private List<Produto> listaProdutos;
	private ComissaoTipoVendedorProduto comissaoProduto;
	
	@Inject
	private TiposVendedores tipos;

	@Inject
	private CadastroTipoVendedorService cadastroTipo;

	@Inject
	private Produtos produtos;

	public TipoVendedorBean() {
		limpar();
	}

	public void limpar() {
		item = new TipoVendedor();
		itemAux = new TipoVendedor();
		comissaoProduto = new ComissaoTipoVendedorProduto();
	}

	@PostConstruct
	public void inicio() {
		itens = tipos.tipos();
		listaProdutos = produtos.produtos();
		listaComissao();
		
		
	}
	
	public void salvar() {
		try {
			cadastroTipo.salvar(item);
			item = new TipoVendedor();
			inicio();
			FacesUtil.addInfoMessage("Tipo de Vendedor salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} 
	}

	public void adicionar() {
		comissaoProduto.setTipoVendedor(item);
		item.getComissaoTipoVendedor().add(comissaoProduto);
		comissaoProduto = new ComissaoTipoVendedorProduto();

	}

	public void remover(ComissaoTipoVendedorProduto comissao) {
		item.getComissaoTipoVendedor().remove(comissao);
	}

	public void editar() {

		try {
			cadastroTipo.salvar(itemAux);
			FacesUtil.addInfoMessage("Tipo de vendedor alterado com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			inicio();
			limpar();
		}

	}

	public void excluir() {
		try {
			tipos.remover(itemAux);
			inicio();
			limpar();
			FacesUtil.addInfoMessage("Tipo de vendedor excluido com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}

	}

	public void troca(TipoVendedor itemAux) {
		this.itemAux = itemAux;
	}

	public TipoVendedor getItem() {
		return item;
	}

	public void setItem(TipoVendedor item) {
		if (item == null) {
			limpar();
			inicio();
		} else {
			this.item = item;
			listaComissao();
			
		}
	}

	public List<TipoVendedor> getItens() {
		return itens;
	}

	public void setItens(List<TipoVendedor> itens) {
		this.itens = itens;
	}

	public TipoVendedor getItemAux() {
		return itemAux;
	}

	public void setItemAux(TipoVendedor itemAux) {
		this.itemAux = itemAux;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public ComissaoTipoVendedorProduto getComissaoProduto() {
		return comissaoProduto;
	}

	public void setComissaoProduto(ComissaoTipoVendedorProduto comissaoProduto) {
		this.comissaoProduto = comissaoProduto;
	}
	

	private void listaComissao() {
		if(item.getComissaoTipoVendedor().size() == 0) {
			for(Produto p : listaProdutos) {
				ComissaoTipoVendedorProduto aux = new ComissaoTipoVendedorProduto();
				aux.setProduto(p);
				aux.setTipoVendedor(item);
				item.getComissaoTipoVendedor().add(aux);
				
			}	
		}
		
		else if(item.getComissaoTipoVendedor().size() < listaProdutos.size()) {
			
			for(Produto p : listaProdutos) {
				ComissaoTipoVendedorProduto aux = new ComissaoTipoVendedorProduto();
				int i = 0;
				for(ComissaoTipoVendedorProduto c :item.getComissaoTipoVendedor() ) {
					if(c.getProduto().getNome().equals(p.getNome())) {
						i = 1;
					}
					
				}
				if(i == 0) {
					aux.setProduto(p);
					aux.setTipoVendedor(item);
					item.getComissaoTipoVendedor().add(aux);
				}
				
			}
		}
	}
}
