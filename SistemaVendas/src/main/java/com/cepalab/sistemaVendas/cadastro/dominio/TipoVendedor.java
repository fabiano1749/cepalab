package com.cepalab.sistemaVendas.cadastro.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;

import com.cepalab.sistemaVendas.operacao.dominio.AberturaProduto;
import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.operacao.dominio.FormaPagamento;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;

@SuppressWarnings("serial")
@Entity
@Table(name = "tipo_vendedor")
public class TipoVendedor extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private String nome;
	private BigDecimal maiorTaxaVendas;
	private BigDecimal maiorValorComissaoAbertura;
	private BigDecimal maiorValorPremiacaoAbertura;
	private BigDecimal maiorValorComissaoColocacao;
	private BigDecimal maiorValorPremiacaoColocacao;
	private boolean ativaMVCA;
	private boolean ativaMVPA;
	private boolean ativaMVCC;
	private boolean ativaMVPC;
	private boolean maiorTaxa;
	private boolean comissaoPorProduto;
	private boolean comissaoPorFormaPagamento;
	
	
	private List<PoliticaVendaConsignacaoTipoVendedorProduto> politicasVCTVP = new ArrayList<>();
	private List<PoliticaVendaConsignacaoTipoVendedorFormaPagamento> politicasVCTVFP = new ArrayList<>();
	private List<PoliticaAberturaTipoVendedorTipoProduto> listaPoliticasATVTP = new ArrayList<>();
	private List<PoliticaColocacaoTipoVendedorTipoProduto> listaPoliticasCTVTP = new ArrayList<>();

	@Column(nullable = false, length = 20)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "maior_taxa", precision = 10, scale = 2)
	public BigDecimal getMaiorTaxaVendas() {
		return maiorTaxaVendas;
	}

	public void setMaiorTaxaVendas(BigDecimal maiorTaxaVendas) {
		this.maiorTaxaVendas = maiorTaxaVendas;
	}

	@Column(name = "maior_valor_comissao_abertura", precision = 10, scale = 2)
	public BigDecimal getMaiorValorComissaoAbertura() {
		return maiorValorComissaoAbertura;
	}

	public void setMaiorValorComissaoAbertura(BigDecimal maiorValorComissaoAbertura) {
		this.maiorValorComissaoAbertura = maiorValorComissaoAbertura;
	}

	@Column(name = "maior_valor_premiacao_abertura", precision = 10, scale = 2)
	public BigDecimal getMaiorValorPremiacaoAbertura() {
		return maiorValorPremiacaoAbertura;
	}

	public void setMaiorValorPremiacaoAbertura(BigDecimal maiorValorPremiacaoAbertura) {
		this.maiorValorPremiacaoAbertura = maiorValorPremiacaoAbertura;
	}

	@Column(name = "maior_valor_comissao_colocacao", precision = 10, scale = 2)
	public BigDecimal getMaiorValorComissaoColocacao() {
		return maiorValorComissaoColocacao;
	}

	public void setMaiorValorComissaoColocacao(BigDecimal maiorValorComissaoColocacao) {
		this.maiorValorComissaoColocacao = maiorValorComissaoColocacao;
	}

	@Column(name = "maior_valor_premiacao_colocacao", precision = 10, scale = 2)
	public BigDecimal getMaiorValorPremiacaoColocacao() {
		return maiorValorPremiacaoColocacao;
	}

	public void setMaiorValorPremiacaoColocacao(BigDecimal maiorValorPremiacaoColocacao) {
		this.maiorValorPremiacaoColocacao = maiorValorPremiacaoColocacao;
	}

	public boolean isMaiorTaxa() {
		return maiorTaxa;
	}

	public void setMaiorTaxa(boolean maiorTaxa) {
		this.maiorTaxa = maiorTaxa;
	}

	public boolean isAtivaMVCA() {
		return ativaMVCA;
	}

	public void setAtivaMVCA(boolean ativaMVCA) {
		this.ativaMVCA = ativaMVCA;
	}

	public boolean isAtivaMVPA() {
		return ativaMVPA;
	}

	public void setAtivaMVPA(boolean ativaMVPA) {
		this.ativaMVPA = ativaMVPA;
	}

	public boolean isAtivaMVCC() {
		return ativaMVCC;
	}

	public void setAtivaMVCC(boolean ativaMVCC) {
		this.ativaMVCC = ativaMVCC;
	}

	public boolean isAtivaMVPC() {
		return ativaMVPC;
	}

	public void setAtivaMVPC(boolean ativaMVPC) {
		this.ativaMVPC = ativaMVPC;
	}

	public boolean isComissaoPorProduto() {
		return comissaoPorProduto;
	}

	public void setComissaoPorProduto(boolean comissaoPorProduto) {
		this.comissaoPorProduto = comissaoPorProduto;
	}

	public boolean isComissaoPorFormaPagamento() {
		return comissaoPorFormaPagamento;
	}

	public void setComissaoPorFormaPagamento(boolean comissaoPorFormaPagamento) {
		this.comissaoPorFormaPagamento = comissaoPorFormaPagamento;
	}

	public void alteraComissionamentoFormaPag() {
		if(isComissaoPorFormaPagamento()) {
			setComissaoPorProduto(false);
		}
		else setComissaoPorProduto(true);
	}
	
	public void alteraComissionamentoProduto() {
		if(isComissaoPorProduto()) {
			setComissaoPorFormaPagamento(false);
		}
		else setComissaoPorFormaPagamento(true);
	}
	
	
	
	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<PoliticaVendaConsignacaoTipoVendedorProduto> getPoliticasVCTVP() {
		return politicasVCTVP;
	}
	
	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<PoliticaVendaConsignacaoTipoVendedorFormaPagamento> getPoliticasVCTVFP() {
		return politicasVCTVFP;
	}

	public void setPoliticasVCTVFP(List<PoliticaVendaConsignacaoTipoVendedorFormaPagamento> politicasVCTVFP) {
		this.politicasVCTVFP = politicasVCTVFP;
	}

	public void setPoliticasVCTVP(List<PoliticaVendaConsignacaoTipoVendedorProduto> politicas) {
		this.politicasVCTVP = politicas;
	}

	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<PoliticaAberturaTipoVendedorTipoProduto> getListaPoliticasATVTP() {
		return listaPoliticasATVTP;
	}

	public void setListaPoliticasATVTP(List<PoliticaAberturaTipoVendedorTipoProduto> listaPoliticasATVTP) {
		this.listaPoliticasATVTP = listaPoliticasATVTP;
	}

	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<PoliticaColocacaoTipoVendedorTipoProduto> getListaPoliticasCTVTP() {
		return listaPoliticasCTVTP;
	}

	public void setListaPoliticasCTVTP(List<PoliticaColocacaoTipoVendedorTipoProduto> listaPoliticasCTVTP) {
		this.listaPoliticasCTVTP = listaPoliticasCTVTP;
	}

	@Transient
	public BigDecimal taxaComissaoVenda(Venda v) {
		Produto produto = v.getProduto();
		int quantidade = v.getQuantidade();
		boolean prontaEntrega = v.getProntaEntrega();

		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVCTVP) {
			if (p.getProduto().equals(produto)) {
				return p.taxaComissao(quantidade, prontaEntrega);
			}
		}

		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal maiorTaxaComissao(Venda v) {
		Produto produto = v.getProduto();
		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVCTVP) {
			if (p.getProduto().equals(produto)) {
				return p.maiorTaxaComissao();

			}
		}
		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal maiorTaxaComissaoConsignacao(Consignacao c) {
		Produto produto = c.getProduto();
		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVCTVP) {
			if (p.getProduto().equals(produto)) {
				return p.maiorTaxaComissao();
			}
		}
		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal minVenda(Venda v) {
		Produto produto = v.getProduto();
		int quantidade = v.getQuantidade();

		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVCTVP) {
			if (p.getProduto().equals(produto)) {
				return p.minVenda(quantidade);
			}
		}

		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal minConsignacao(Consignacao c) {
		Produto produto = c.getProduto();
		int quantidade = c.getConsignados().intValue();

		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVCTVP) {
			if (p.getProduto().equals(produto)) {
				return p.minConsignacao(quantidade);
			}
		}

		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal taxaComissaoConsignacao(Consignacao c) {
		Produto produto = c.getProduto();
		int quantidade = c.getVendidos();
		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVCTVP) {
			if (p.getProduto().equals(produto)) {
				return p.taxaComissaoConsignacao(quantidade);
			}
		}

		return BigDecimal.ZERO;
	}

	// CALCULO DA COMISSAO ABERTURA
	@Transient
	public BigDecimal comissaoAbertura(List<AberturaProduto> lista, List<TipoProduto> tiposProdutos) {
		if (lista == null || lista.size() == 0) {
			return BigDecimal.ZERO;
		} else {
			AuxCalculoComissaoAbertura auxComissao = new AuxCalculoComissaoAbertura(this, lista, tiposProdutos);

			BigDecimal comissaoCalculada = auxComissao.principal();

			if (ativaMVCA == true && maiorValorComissaoAbertura != null) {
				if (comissaoCalculada.compareTo(maiorValorComissaoAbertura) > 0) {
					return maiorValorComissaoAbertura;
				}

			}
			return comissaoCalculada;

		}

	}

	// CALCULO DA PREMIACAO ABERTURA
	@Transient
	public BigDecimal premiacaoAbertura(List<AberturaProduto> lista, List<TipoProduto> tiposProdutos) {
		if (lista == null || lista.size() == 0) {
			return BigDecimal.ZERO;
		} else {
			AuxCalculoPremiacaoAbertura auxComissao = new AuxCalculoPremiacaoAbertura(this, lista);

			BigDecimal premiacaoCalculada = auxComissao.principal(tiposProdutos);

			if (ativaMVPA == true && maiorValorPremiacaoAbertura != null) {
				if (premiacaoCalculada.compareTo(maiorValorPremiacaoAbertura) > 0) {
					return maiorValorPremiacaoAbertura;
				}

			}
			return premiacaoCalculada;

		}

	}

	// CALCULO DA COMISSAO COLOCACAO
	@Transient
	public BigDecimal comissaoColocacao(List<AberturaProduto> lista, List<TipoProduto> tiposProdutos) {
		if (lista == null || lista.size() == 0) {
			return BigDecimal.ZERO;
		} else {
			AuxCalculoComissaoColocacao auxComissao = new AuxCalculoComissaoColocacao(this, lista);

			BigDecimal comissaoCalculada = auxComissao.principal(tiposProdutos);

			if (ativaMVCC == true && maiorValorComissaoColocacao != null) {
				if (comissaoCalculada.compareTo(maiorValorComissaoColocacao) > 0) {
					return maiorValorComissaoColocacao;
				}

			}
			return comissaoCalculada;

		}

	}

	// CALCULO DA PREMIACAO COLOCACAO
	@Transient
	public BigDecimal premiacaoColocacao(List<AberturaProduto> lista, List<TipoProduto> tiposProdutos) {
		if (lista == null || lista.size() == 0) {
			return BigDecimal.ZERO;
		} else {
			AuxCalculoPremiacaoColocacao auxComissao = new AuxCalculoPremiacaoColocacao(this, lista);

			BigDecimal premiacaoCalculada = auxComissao.principal(tiposProdutos);

			if (ativaMVPC == true && maiorValorPremiacaoColocacao != null) {
				if (premiacaoCalculada.compareTo(maiorValorPremiacaoColocacao) > 0) {
					return maiorValorPremiacaoColocacao;
				}

			}
			return premiacaoCalculada;

		}
	}
	
	//Retorna o número mínimo de um produto de um determinado tipo para que ele seja considerado para o calculo de abertura 
	public int numeroMinimoDeProduto(TipoProduto tipo) {
		if (listaPoliticasATVTP != null && listaPoliticasATVTP.size() > 0) {
			for (PoliticaAberturaTipoVendedorTipoProduto p : listaPoliticasATVTP) {
				if (p.getTipoProduto().equals(tipo)) {
					return p.getQuantMinimaPorProduto();
				}
			}
		}
		return 1;
	}

	public BigDecimal taxaComissaoFormaPagamento(FormaPagamento forma) {
		if(politicasVCTVFP != null) {
			for(PoliticaVendaConsignacaoTipoVendedorFormaPagamento p : politicasVCTVFP) {
				if(p.getFormaPagamento().equals(forma)) {
					return p.getTaxa().divide(new BigDecimal(100));
				}
			}
		}
		return BigDecimal.ZERO;
	}
	

}
