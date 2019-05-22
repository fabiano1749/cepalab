package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.IntervaloAberturaTipoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.IntervaloColocacaoTipoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.IntervaloVendaConsignacaoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaAberturaTipoVendedorTipoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaColocacaoTipoVendedorTipoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorFormaPagamento;
import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoCalculoAberturaColocacao;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.repository.TiposProdutos;
import com.cepalab.sistemaVendas.service.CadastroTipoVendedorService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TipoVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoVendedor item;
	private List<Produto> listaProdutos;
	private List<TipoProduto> listaTiposProdutos;
	private PoliticaVendaConsignacaoTipoVendedorProduto politicaVCTVP;
	private IntervaloVendaConsignacaoProduto intervalo;
	
	private PoliticaAberturaTipoVendedorTipoProduto politicaATVTP;
	private IntervaloAberturaTipoProduto intervaloAbertura;
	
	private PoliticaColocacaoTipoVendedorTipoProduto politicaCTVTP;
	private IntervaloColocacaoTipoProduto intervaloColocacao;
	
	private PoliticaVendaConsignacaoTipoVendedorFormaPagamento politicaVCTVFP;
	
	
	@Inject
	private CadastroTipoVendedorService cadastroTipo;

	@Inject
	private Produtos produtos;
	
	@Inject
	private TiposProdutos tiposProdutos;


	public TipoVendedorBean() {
		limpar();
	}

	public void limpar() {
		item = new TipoVendedor();
		politicaVCTVP = new PoliticaVendaConsignacaoTipoVendedorProduto();
		intervalo = new IntervaloVendaConsignacaoProduto();
		
		politicaATVTP = new PoliticaAberturaTipoVendedorTipoProduto();
		intervaloAbertura = new IntervaloAberturaTipoProduto();
		
		politicaCTVTP = new PoliticaColocacaoTipoVendedorTipoProduto();
		intervaloColocacao = new IntervaloColocacaoTipoProduto();
		politicaVCTVFP = new PoliticaVendaConsignacaoTipoVendedorFormaPagamento();
	}

	public void inicio() {
		listaProdutos = produtos.produtos();
		listaTiposProdutos = tiposProdutos.tipos();
		iniciaListaPoliticas();
		iniciaListaPoliticasAberturas();
		iniciaListaPoliticasColocacoes();
		iniciaListaPoliticasFormaPagamento();
	}

	
	public void salvar() {
		try {
			cadastroTipo.salvar(item);
			item = new TipoVendedor();
			limpar();
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
				inicio();
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

	public PoliticaAberturaTipoVendedorTipoProduto getPoliticaATVTP() {
		return politicaATVTP;
	}

	public void setPoliticaATVTP(PoliticaAberturaTipoVendedorTipoProduto politicaATVTP) {
		this.politicaATVTP = politicaATVTP;
	}

	public IntervaloAberturaTipoProduto getIntervaloAbertura() {
		return intervaloAbertura;
	}

	public void setIntervaloAbertura(IntervaloAberturaTipoProduto intervaloAbertura) {
		this.intervaloAbertura = intervaloAbertura;
	}

	
	public PoliticaColocacaoTipoVendedorTipoProduto getPoliticaCTVTP() {
		return politicaCTVTP;
	}

	public void setPoliticaCTVTP(PoliticaColocacaoTipoVendedorTipoProduto politicaCTVTP) {
		this.politicaCTVTP = politicaCTVTP;
	}

	public IntervaloColocacaoTipoProduto getIntervaloColocacao() {
		return intervaloColocacao;
	}

	public void setIntervaloColocacao(IntervaloColocacaoTipoProduto intervaloColocacao) {
		this.intervaloColocacao = intervaloColocacao;
	}

	// Inicia a lista de politicas inserindo os produtos cadastrados
	private void iniciaListaPoliticas() {
		if (item.getPoliticasVCTVP().size() == 0) {
			for (Produto p : listaProdutos) {
				PoliticaVendaConsignacaoTipoVendedorProduto aux = new PoliticaVendaConsignacaoTipoVendedorProduto();
				aux.setProduto(p);
				aux.setTipoVendedor(item);
				item.getPoliticasVCTVP().add(aux);
			}//Caso haja novos produtos cadastrados
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

	// Inicia a lista de politicas por forma pagamento inserindo novas formas cadastradas
		private void iniciaListaPoliticasFormaPagamento() {
			if (item.getPoliticasVCTVFP().size() == 0) {
				for (FormaPagamento f : FormaPagamento.values()) {
					if(!f.equals(FormaPagamento.NENHUM)) {
						PoliticaVendaConsignacaoTipoVendedorFormaPagamento aux = new PoliticaVendaConsignacaoTipoVendedorFormaPagamento(item, f);
						item.getPoliticasVCTVFP().add(aux);
					}
				}//Caso sejam adicionadas novas formas de pagamento adicionadas
			} else if (item.getPoliticasVCTVFP().size() < FormaPagamento.values().length) {
				for (FormaPagamento f : FormaPagamento.values()){
					if(!f.equals(FormaPagamento.NENHUM)) {
						int i = 0;
						for (PoliticaVendaConsignacaoTipoVendedorFormaPagamento politica : item.getPoliticasVCTVFP()) {
							if (politica.getFormaPagamento().equals(f)) {
								i = 1;
							}
						}
						if (i == 0) {
							PoliticaVendaConsignacaoTipoVendedorFormaPagamento aux = new PoliticaVendaConsignacaoTipoVendedorFormaPagamento(item, f);
							item.getPoliticasVCTVFP().add(aux);
						}
					}
				}
			}
		}
	
	//Inicia a lista de politicas de Aberturas inserindo os tipos de produtos cadastrados.
	public void iniciaListaPoliticasAberturas() {
		
		
		if(item.getListaPoliticasATVTP().size() == 0) {
			for(TipoProduto t : listaTiposProdutos) {
				PoliticaAberturaTipoVendedorTipoProduto politica = new PoliticaAberturaTipoVendedorTipoProduto();
				politica.setTipoProduto(t);
				politica.setTipoVendedor(item);
				item.getListaPoliticasATVTP().add(politica);
			}
		}//Caso haja novos tipos de produtos cadastrados
		else if ( item.getListaPoliticasATVTP().size() < listaTiposProdutos.size()) {
			for(TipoProduto t : listaTiposProdutos) {
				PoliticaAberturaTipoVendedorTipoProduto aux = new PoliticaAberturaTipoVendedorTipoProduto();
				int i = 0;
				for(PoliticaAberturaTipoVendedorTipoProduto politica : item.getListaPoliticasATVTP()) {
					if(politica.getTipoProduto().equals(t)) {
						i = 1;
					}
				}
				if(i == 0) {
					aux.setTipoProduto(t);
					aux.setTipoVendedor(item);
					item.getListaPoliticasATVTP().add(aux);
				}
				
			}
		}
	}
	
	
	//Inicia a lista de politicas de colocações inserindo os tipos de produtos cadastrados.
		public void iniciaListaPoliticasColocacoes() {
			if(item.getListaPoliticasCTVTP().size() == 0) {
				for(TipoProduto t : listaTiposProdutos) {
					PoliticaColocacaoTipoVendedorTipoProduto politica = new PoliticaColocacaoTipoVendedorTipoProduto();
					politica.setTipoProduto(t);
					politica.setTipoVendedor(item);
					item.getListaPoliticasCTVTP().add(politica);
				}
			}//Caso haja novos tipos de produtos cadastrados
			else if ( item.getListaPoliticasCTVTP().size() < listaTiposProdutos.size()) {
				for(TipoProduto t : listaTiposProdutos) {
					PoliticaColocacaoTipoVendedorTipoProduto aux = new PoliticaColocacaoTipoVendedorTipoProduto();
					int i = 0;
					for(PoliticaColocacaoTipoVendedorTipoProduto politica : item.getListaPoliticasCTVTP()) {
						if(politica.getTipoProduto().equals(t)) {
							i = 1;
						}
					}
					if(i == 0) {
						aux.setTipoProduto(t);
						aux.setTipoVendedor(item);
						item.getListaPoliticasCTVTP().add(aux);
					}
					
				}
			}
		}
	
	
	
	// Métodos para a criação de uma nova política de venda e consignação

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
		for (IntervaloVendaConsignacaoProduto i : politicaVCTVP.getListaIntervalos()) {
			if (intervalo.getInicio() != i.getInicio()) {
				lista.add(i);
			}
		}
		politicaVCTVP.setListaIntervalos(lista);
	}
	
	//métodos para criar uma nova política de Aberturas por tipo de produto
	public void trocaPolicaAbertura(PoliticaAberturaTipoVendedorTipoProduto politica) {
		politicaATVTP = politica;
		intervaloAbertura = new IntervaloAberturaTipoProduto();
	}
	
	public void adicionarIntervaloAbertura() {
		int inicio = intervaloAbertura.getInicio();
		int fim = intervaloAbertura.getFim();

		if (validaComissaoAbertura() && validaPremiacaoAbertura()) {

			if (fim <= inicio) {
				FacesUtil.addErrorMessage("Intervalo inválido: Início <= Fim");
			} else if (inicio < 1) {
				FacesUtil.addErrorMessage("Intervalo inválido: Início < 1");
			} else {

				intervaloAbertura.setPoliticaAberturaTipoVendedorTipoProduto(politicaATVTP);
				if (!politicaATVTP.adicionaIntervaloAbertura(intervaloAbertura)) {
					FacesUtil.addErrorMessage("Início do intervalo é menor que o fim do último intervalo salvo!");
				}
				intervaloAbertura = new IntervaloAberturaTipoProduto();
			}
		} else {
			FacesUtil.addErrorMessage("Há valores incorretos no intevalo !");
		}
	}
	
	public boolean validaComissaoAbertura() {
		return intervaloAbertura.getValor().compareTo(BigDecimal.ZERO) >= 0;
	}
	
	public boolean validaPremiacaoAbertura() {
		return intervaloAbertura.getPremiacao().compareTo(BigDecimal.ZERO) >= 0;
	}
	
	public void excluiIntervaloAbertura(IntervaloAberturaTipoProduto intervalo) {
		List<IntervaloAberturaTipoProduto> lista = new ArrayList<>();
		for (IntervaloAberturaTipoProduto i : politicaATVTP.getListaIntervalos()) {
			if (intervalo.getInicio() != i.getInicio()) {
				lista.add(i);
			}
		}
		politicaATVTP.setListaIntervalos(lista);
	}
	

	//métodos para criar uma nova política de colocacao por tipo de produto
		public void trocaPolicaColocacao(PoliticaColocacaoTipoVendedorTipoProduto politica) {
			politicaCTVTP = politica;
			intervaloColocacao = new IntervaloColocacaoTipoProduto();
		}
		
		public void adicionarIntervaloColocacao() {
			int inicio = intervaloColocacao.getInicio();
			int fim = intervaloColocacao.getFim();

			if (validaComissaoColocacao() && validaPremiacaoColocacao()) {

				if (fim <= inicio) {
					FacesUtil.addErrorMessage("Intervalo inválido: Início <= Fim");
				} else if (inicio < 1) {
					FacesUtil.addErrorMessage("Intervalo inválido: Início < 1");
				} else {

					intervaloColocacao.setPoliticaColocacaoTipoVendedorTipoProduto(politicaCTVTP);
					if (!politicaCTVTP.adicionaIntervaloColocacao(intervaloColocacao)) {
						FacesUtil.addErrorMessage("Início do intervalo é menor que o fim do último intervalo salvo!");
					}
					intervaloColocacao = new IntervaloColocacaoTipoProduto();
				}
			} else {
				FacesUtil.addErrorMessage("Há valores incorretos no intevalo !");
			}
		}
		
		public boolean validaComissaoColocacao() {
			return intervaloColocacao.getValor().compareTo(BigDecimal.ZERO) >= 0;
		}
		
		public boolean validaPremiacaoColocacao() {
			return intervaloColocacao.getPremiacao().compareTo(BigDecimal.ZERO) >= 0;
		}
		
		public PoliticaVendaConsignacaoTipoVendedorFormaPagamento getPoliticaVCTVFP() {
			return politicaVCTVFP;
		}

		public void setPoliticaVCTVFP(PoliticaVendaConsignacaoTipoVendedorFormaPagamento politicaVCTVFP) {
			this.politicaVCTVFP = politicaVCTVFP;
		}

		public void excluiIntervaloColocacao(IntervaloColocacaoTipoProduto intervalo) {
			List<IntervaloColocacaoTipoProduto> lista = new ArrayList<>();
			for (IntervaloColocacaoTipoProduto i : politicaCTVTP.getListaIntervalos()) {
				if (intervalo.getInicio() != i.getInicio()) {
					lista.add(i);
				}
			}
			politicaCTVTP.setListaIntervalos(lista);
		}

	

}
