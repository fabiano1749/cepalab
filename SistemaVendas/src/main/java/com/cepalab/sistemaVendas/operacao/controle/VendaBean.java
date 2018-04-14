package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;
import com.cepalab.sistemaVendas.repository.ComissoesTipoVendedores;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.repository.Vendas;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Venda venda = new Venda();
	private List<Produto> listaProdutos = new ArrayList<>();

	@Inject
	private OperacaoBean operacao;

	@Inject
	private ResumoOperacaoBean resumoOperacao;

	@Inject
	private ReceitaBean receitaBean;

	@Inject
	private Seguranca seg;

	@Inject
	private ComissoesTipoVendedores taxaComissao;

	@Inject
	private Vendas vendas;

	@Inject
	private Produtos produtos;

	public void inicio() {
		listaProdutos = produtos.produtos();
		venda = new Venda();
	}

	public void criaListaVendas() {
		inicio();
		operacao.getItem().setVendas(vendas.porCliente(operacao.getItem().getCliente()));
		for (Venda v : operacao.getItem().getVendas()) {
			v.setTaxaComissao(taxaComissao.taxaComissaoFuncionarioProduto(seg.UsuarioLogado(), v.getProduto()));
			v.setOperacao(operacao.getItem());
		}
	}

	public void criaListaVendasEdicao() {
		inicio();
		List<Venda> listaAux = vendas.porCliente(operacao.getItem().getCliente());

		if (operacao.getItem().getVendas().isEmpty() && listaAux != null) {
			for (Venda v : listaAux) {
				v.setTaxaComissao(taxaComissao.taxaComissaoFuncionarioProduto(seg.UsuarioLogado(), v.getProduto()));
				v.setOperacao(operacao.getItem());
			}
			operacao.getItem().setVendas(listaAux);
		} else {

			if (listaAux != null && !listaAux.isEmpty()) {
				for (Venda v : listaAux) {
					if (!pertenceListaVendas(v)) {
						v.setTaxaComissao(
								taxaComissao.taxaComissaoFuncionarioProduto(seg.UsuarioLogado(), v.getProduto()));
						v.setOperacao(operacao.getItem());
						operacao.getItem().getVendas().add(v);
					}
				}

			}
		}
	}

	public boolean pertenceListaVendas(Venda v) {
		for (Venda a : operacao.getItem().getVendas()) {
			if (v.getProduto().getNome().equals(a.getProduto().getNome())) {
				return true;
			}
		}
		return false;
	}

	public boolean isVendidos(Produto p) {
		for (Venda v : operacao.getItem().getVendas()) {
			if (v.getProduto().getNome().equals(p.getNome())) {
				return true;
			}
		}
		return false;
	}

	public void adicionaVenda() {
		if (!isVendidos(venda.getProduto())) {
			venda.setOperacao(operacao.getItem());
			operacao.getItem().getVendas().add(venda);
			taxaComissao(venda);
			venda = new Venda();
		} else {
			FacesUtil.addErrorMessage("O produto informado já pertence aos produtos vendidos!");
		}

	}

	public void removeVenda() {
		List<Venda> listaVenda = new ArrayList<>();
		for(Venda v : operacao.getItem().getVendas()) {
			if(!v.getProduto().getNome().equals(venda.getProduto().getNome())) {
				listaVenda.add(v);
			}
		}

		operacao.getItem().setVendas(listaVenda);

		if (venda.getQuantidade() != 0) {
			resumoOperacao.alimentaListaResumoConsignacaoVenda();
			receitaBean.criaListaReceitas();
		}

		if (operacao.getItem().getAberturasProdutos() != null) {
			List<AberturaProduto> aberturas = new ArrayList<>();
			for (AberturaProduto abertura : operacao.getItem().getAberturasProdutos()) {
				if (!abertura.getProduto().getNome().equals(venda.getProduto().getNome())) {
					aberturas.add(abertura);
				}
				operacao.getItem().setAberturasProdutos(aberturas);

			}
		}
		venda = new Venda();
	}

	public void trocaItem(Venda v) {
		venda = v;
	}

	public void nota(Venda v) {
		if (!v.getNota()) {
			v.setNotaEmitida(false);
		}
	}

	public Boolean notaFiscal(Venda v) {
		if (v != null) {
			if (v.getNota()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public Boolean notaEmitidaNovo() {
		if (venda.getNota() == true) {
			return false;
		} else {
			return true;
		}

	}

	public void validaPreco(Venda aux) {
		BigDecimal valorInformado = aux.getValorUnitario();
		BigDecimal minValorVenda = aux.getProduto().getMinValorVenda();
		if (valorInformado == null || valorInformado.compareTo(minValorVenda) < 0) {
			// aux.setValorUnitario(minValorVenda);
			FacesUtil.addErrorMessage("Valor unitário e menor que o mínimo permitido para esse produto!");
		}

	}

	public Boolean habilitaPrecoUnitario() {

		if (venda.getProduto() == null) {
			return true;
		} else {
			return false;
		}

	}

	public void validaPrecoNovaVenda() {
		BigDecimal valor = venda.getValorUnitario();
		BigDecimal minVenda = venda.getProduto().getMinValorVenda();

		int resul = valor.compareTo(minVenda);
		if (resul < 0) {
			FacesUtil.addErrorMessage("Valor unitário é menor que o mínimo permitido para esse produto!");
			// venda.setValorUnitario(minVenda);
		}

	}

	public BigDecimal totalProdutosVendidos(Venda v) {
		return v.getValorUnitario().multiply(new BigDecimal(v.getQuantidade()));
	}

	public void taxaComissao(Venda v) {
		if (v.getProntaEntrega()) {
			v.setTaxaComissao(taxaComissao.taxaComissaoFuncionarioProduto(seg.UsuarioLogado(), v.getProduto()));
		} else {
			v.setTaxaComissao(taxaComissao.taxaComissaoFuncionarioProdutoEnvio(seg.UsuarioLogado(), v.getProduto()));
		}
		resumoOperacao.alimentaListaResumoConsignacaoVenda();
		receitaBean.criaListaReceitas();

	}

	public void alteraTaxa(Venda v) {
		if (v.getTaxaComissao().compareTo(new BigDecimal("10")) > 0) {
			taxaComissao(v);
		}
	}

	public BigDecimal calculaComissao(Venda v) {
		return totalProdutosVendidos(v).multiply(v.getTaxaComissao().divide(new BigDecimal("100")));
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
