package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.IntervaloVendaConsignacaoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoCalculoAberturaColocacao;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.service.CadastroTipoVendedorService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TipoVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoVendedor item;
	private List<Produto> listaProdutos;
	private PoliticaVendaConsignacaoTipoVendedorProduto politicaVCTVP;
	private IntervaloVendaConsignacaoProduto intervalo;

	@Inject
	private CadastroTipoVendedorService cadastroTipo;

	@Inject
	private Produtos produtos;

	public TipoVendedorBean() {
		limpar();
	}

	public void limpar() {
		item = new TipoVendedor();
		politicaVCTVP = new PoliticaVendaConsignacaoTipoVendedorProduto();
	}

	@PostConstruct
	public void inicio() {
		listaProdutos = produtos.produtos();
		iniciaListaPoliticas();
		politicaVCTVP = new PoliticaVendaConsignacaoTipoVendedorProduto();
		intervalo = new IntervaloVendaConsignacaoProduto();
	}

	public void teste(PoliticaVendaConsignacaoTipoVendedorProduto politica) {
		System.out.println(politica.getListaIntervalos().size());
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

	public void remover(PoliticaVendaConsignacaoTipoVendedorProduto politica) {
		item.getPoliticasVCTVP().remove(politica);
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
			if (item.getPoliticasVCTVP().size() == 0) {
				iniciaListaPoliticas();
			}
		}
	}

	public TipoCalculoAberturaColocacao[] tipoAberturaColocacao() {
		return TipoCalculoAberturaColocacao.values();
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public PoliticaVendaConsignacaoTipoVendedorProduto getPoliticaVCTVP() {
		return politicaVCTVP;
	}

	public void setPoliticaVCTVP(PoliticaVendaConsignacaoTipoVendedorProduto politicaVCTVP) {
		this.politicaVCTVP = politicaVCTVP;
	}

	public IntervaloVendaConsignacaoProduto getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(IntervaloVendaConsignacaoProduto intervalo) {
		this.intervalo = intervalo;
	}

	// Inicia a lista de politicas inserindo os produtos cadastrados
	private void iniciaListaPoliticas() {
		if (item.getPoliticasVCTVP().size() == 0) {
			for (Produto p : listaProdutos) {
				PoliticaVendaConsignacaoTipoVendedorProduto aux = new PoliticaVendaConsignacaoTipoVendedorProduto();
				aux.setProduto(p);
				aux.setTipoVendedor(item);
				item.getPoliticasVCTVP().add(aux);

			}
		} else if (item.getPoliticasVCTVP().size() < listaProdutos.size()) {

			for (Produto p : listaProdutos) {
				PoliticaVendaConsignacaoTipoVendedorProduto aux = new PoliticaVendaConsignacaoTipoVendedorProduto();
				int i = 0;
				for (PoliticaVendaConsignacaoTipoVendedorProduto politica : item.getPoliticasVCTVP()) {
					if (politica.getProduto().equals(p)) {
						i = 1;
					}

				}
				if (i == 0) {
					aux.setProduto(p);
					aux.setTipoVendedor(item);
					item.getPoliticasVCTVP().add(aux);
				}

			}
		}

	}

	// Métodos para a criação de uma nova política

	public void troca(PoliticaVendaConsignacaoTipoVendedorProduto politica) {
		politicaVCTVP = politica;
		intervalo = new IntervaloVendaConsignacaoProduto();
	}

	public void adicionar() {
		int inicio = intervalo.getInicio();
		int fim = intervalo.getFim();

		if (validaMenorPrecoVenda() && validaMenorPrecoCOnsignacao() && validaComissoes()) {

			if (fim <= inicio) {
				FacesUtil.addErrorMessage("Intervalo inválido: Início <= Fim");
			} else if (inicio < 1) {
				FacesUtil.addErrorMessage("Intervalo inválido: Início < 1");
			} else {

				intervalo.setPoliticaTipoVendedorProduto(politicaVCTVP);
				if (!politicaVCTVP.adicionaIntervalo(intervalo)) {
					FacesUtil.addErrorMessage("Início do intervalo é menor que o fim do último intervalo salvo!");
				}
				intervalo = new IntervaloVendaConsignacaoProduto();
			}
		} else {
			FacesUtil.addErrorMessage("Há valores incorretos no intevalo !");
		}
	}

	public boolean validaMenorPrecoVenda() {
		if (intervalo.getMinVenda().compareTo(politicaVCTVP.getProduto().getCusto()) < 0) {
			return false;
		}
		return true;
	}

	public boolean validaMenorPrecoCOnsignacao() {
		if (intervalo.getMinConsignacao().compareTo(politicaVCTVP.getProduto().getCusto()) < 0) {
			return false;
		}
		return true;
	}

	public boolean validaComissoes() {
		BigDecimal prontaEntrega = intervalo.getComissaoProntaEntrega();
		BigDecimal transportadora = intervalo.getComissaoTransportadora();

		if (prontaEntrega.compareTo(BigDecimal.ZERO) < 0 || transportadora.compareTo(transportadora) < 0) {
			FacesUtil.addErrorMessage("Comissões não podem ser negativas !");
			return false;
		}
		return true;
	}

	public void excluiIntervalo(IntervaloVendaConsignacaoProduto intervalo) {
		List<IntervaloVendaConsignacaoProduto> lista = new ArrayList<>();
		for(IntervaloVendaConsignacaoProduto i : politicaVCTVP.getListaIntervalos()) {
			if(intervalo.getInicio() != i.getInicio()) {
				lista.add(i);
			}
		}
		politicaVCTVP.setListaIntervalos(lista);
	}
}
