package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.PodeConsignar;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;
import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.repository.Consignados;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ConsignacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Consignacao consignacao = new Consignacao();
	private List<Produto> listaProdutos = new ArrayList<>();
	private AberturaProduto abertura = new AberturaProduto();

	@Inject
	private OperacaoBean operacao;

	@Inject
	private Consignados consignados;

	@Inject
	ResumoOperacaoBean resumoOperacaoBean;

	@Inject
	ReceitaBean receitaBean;

	@Inject
	private Produtos produtos;

	public void iniciaConsignacao() {
		consignacao = new Consignacao();
	}
	
	public void inicio() {
		listaProdutos = criaListaProduto();
		consignacao = new Consignacao();
	}

	public void criaListaConsignados() {
		inicio();
		operacao.getItem().setConsignacoes((consignados.porCliente(operacao.getItem().getCliente())));
		for (Consignacao c : operacao.getItem().getConsignacoes()) {
			c.setOperacao(operacao.getItem());
		}
	}

	public void criaListaConsignacaoEdicao() {
		inicio();
		List<Consignacao> listaAux = consignados.porCliente(operacao.getItem().getCliente());

		if (operacao.getItem().getConsignacoes().isEmpty() && listaAux != null) {
			for (Consignacao c : listaAux) {
				c.setOperacao(operacao.getItem());
			}
			operacao.getItem().setConsignacoes(listaAux);

		} else {

			if (listaAux != null && !listaAux.isEmpty()) {
				for (Consignacao c : listaAux) {
					if (!pertenceListaConsignados(c)) {
						c.setOperacao(operacao.getItem());
						operacao.getItem().getConsignacoes().add(c);
					}
				}

			}
		}
	}

	public boolean pertenceListaConsignados(Consignacao c) {
		for (Consignacao a : operacao.getItem().getConsignacoes()) {
			if (c.getProduto().getNome().equals(a.getProduto().getNome())) {
				return true;
			}
		}
		return false;
	}

	public List<Produto> criaListaProduto() {
		List<Produto> lista = produtos.produtos();
		if (lista != null) {
			for (Produto p : produtos.produtos()) {
				if (p.getPodeConsignar().equals(PodeConsignar.NAO)) {
					lista.remove(p);
				}
			}
			return lista;
		}
		return null;
	}

	public void atualizaConsignados(Consignacao aux) {
		if (aux != null) {
			Long totalConsignados = aux.getTotalAux();
			int vendidos = aux.getVendidos();
			int devolvidos = aux.getDevolvidos();
			Long novaConsignacao = aux.getConsignados();
			Long resul = totalConsignados + novaConsignacao - vendidos - devolvidos;

			if (vendidos > totalConsignados) {
				aux.setVendidos(0);
				FacesUtil.addErrorMessage("Vendidos maior que consignados!");
			} else if (devolvidos > totalConsignados) {
				aux.setDevolvidos(0);
				FacesUtil.addErrorMessage("Devolvidos é maior que o total consignados!");
			} else if (resul < 0) {
				aux.setDevolvidos(0);
				aux.setConsignados(0L);
				atualizaConsignados(aux);
				FacesUtil.addErrorMessage("Os valores informados são inconsistentes!");
			}

			else {
				aux.setTotalConsignado(resul);
				for (AberturaProduto a : operacao.getItem().getAberturasProdutos()) {
					if (a.getProduto().getNome().equals(aux.getProduto().getNome())) {
						a.setQuantidade(aux.getConsignados());
					}
				}

			}

		}
	}

	public Consignacao getConsignacao() {
		return consignacao;
	}

	public void setConsignacao(Consignacao consignacao) {
		this.consignacao = consignacao;
	}

	public boolean isConsignado(Produto p) {
		for (Consignacao c : operacao.getItem().getConsignacoes()) {
			if (c.getProduto().getNome().equals(p.getNome())) {
				return true;
			}

		}
		return false;
	}

	public void adicionaConsignacao() {
		if (!isConsignado(consignacao.getProduto())) {
			consignacao.setTotalConsignado(consignacao.getConsignados());
			consignacao.setTaxaComissao(operacao.getTipoVendedor().taxaComissaoConsignacao(consignacao));

			consignacao.setOperacao(operacao.getItem());
			operacao.getItem().getConsignacoes().add(consignacao);

			abertura.setProduto(consignacao.getProduto());
			abertura.setQuantidade(consignacao.getConsignados());
			abertura.setOperacao(operacao.getItem());
			operacao.getItem().getAberturasProdutos().add(abertura);

			consignacao = new Consignacao();
			abertura = new AberturaProduto();
		} else {
			FacesUtil.addErrorMessage("O produto informado já pertence aos produtos consignados!");
		}

	}

	public void removeConsignacao() {
		List<Consignacao> listaConsignacao = new ArrayList<>();
		for (Consignacao c : operacao.getItem().getConsignacoes()) {
			if (!c.getProduto().getNome().equals(consignacao.getProduto().getNome())) {
				listaConsignacao.add(c);
			}
		}

		operacao.getItem().setConsignacoes(listaConsignacao);

		if (consignacao.getVendidos() != 0) {
			resumoOperacaoBean.alimentaListaResumoConsignacaoVenda();
			receitaBean.criaListaReceitas();
		}

		if (operacao.getItem().getAberturasProdutos() != null) {
			List<AberturaProduto> aberturas = new ArrayList<>();
			for (AberturaProduto abertura : operacao.getItem().getAberturasProdutos()) {
				if (!abertura.getProduto().getNome().equals(consignacao.getProduto().getNome())) {
					aberturas.add(abertura);
				}
				operacao.getItem().setAberturasProdutos(aberturas);

			}
		}
		consignacao = new Consignacao();
	}

	public void trocaItem(Consignacao c) {
		consignacao = c;
	}

	public void nota(Consignacao c) {
		if (!c.getNota()) {
			c.setNotaEmitida(false);
		}
	}

	public Boolean exibeNotaEmitida(Consignacao c) {
		if (c != null) {
			if (c.getNota()) {
				return false;
			} else {
				return true;
			}
		}

		return true;
	}

	public Boolean notaEmitidaNovo() {
		
		if (consignacao != null && consignacao.getNota() == true) {
			return false;
		} else {
			return true;
		}

	}

	public void validaPreco(Consignacao aux) {
		if (aux != null) {
			BigDecimal valorInformado = aux.getValorUnitario();
			BigDecimal minValorConsignacao = operacao.getTipoVendedor().minConsignacao(aux);
			if (valorInformado == null || valorInformado.compareTo(minValorConsignacao) < 0) {
				// Caso a cepa não permita venda abaixo do menor preço cadastrado habilitar a
				// linha abaixo
				// aux.setValorUnitario(minValorConsignacao);
				FacesUtil.addErrorMessage("Valor unitário é menor que o mínimo permitido para esse produto!");
			}
		}
	}

	public Boolean habilitaPrecoUnitario() {
		if (consignacao != null) {
			if (consignacao.getProduto() == null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void validaPrecoNovaConsignacao() {
		BigDecimal valor = consignacao.getValorUnitario();
		BigDecimal minConsignacao = operacao.getTipoVendedor().minConsignacao(consignacao);

		int resul = valor.compareTo(minConsignacao);
		if (resul < 0) {
			FacesUtil.addErrorMessage("Valor unitário é menor que o mínimo permitido para esse produto!");
			// Caso a cepa não permita venda abaixo do menor preço cadastrado habilitar a
			// linha abaixo
			// consignacao.setValorUnitario(minConsignacao);
		}

	}

	public BigDecimal totalProdutoConsignacao(Consignacao c) {
		if (c != null) {
			return c.getValorUnitario().multiply(new BigDecimal(c.getVendidos()));
		}
		return new BigDecimal("0");
	}

	public void taxaComissao(Consignacao c) {
		if (c != null) {
			c.setTaxaComissao(operacao.getTipoVendedor().taxaComissaoConsignacao(c));
			resumoOperacaoBean.alimentaListaResumoConsignacaoVenda();
		}
	}

	public void alteraTaxa(Consignacao c) {
		if (c != null) {
			BigDecimal maiorComissaoCadastrada = operacao.getTipoVendedor().maiorTaxaComissaoConsignacao(c);
			if (c.getTaxaComissao().compareTo(maiorComissaoCadastrada) > 0) {
				taxaComissao(c);
			} else {
				resumoOperacaoBean.alimentaListaResumoConsignacaoVenda();
			}
		}

	}

	public BigDecimal menorValorConsignacao() {

		if (consignacao != null) {
			if (consignacao.getProduto() != null) {
				return operacao.getTipoVendedor().minConsignacao(consignacao);
			}

		}
		return BigDecimal.ZERO;
	}

	public void ataulizaValorConsignacao() {
		consignacao.setValorUnitario(menorValorConsignacao());
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public AberturaProduto getAbertura() {
		return abertura;
	}

	public void setAbertura(AberturaProduto abertura) {
		this.abertura = abertura;
	}

}
