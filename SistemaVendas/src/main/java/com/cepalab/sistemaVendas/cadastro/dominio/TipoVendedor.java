package com.cepalab.sistemaVendas.cadastro.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private List<PoliticaVendaConsignacaoTipoVendedorProduto> politicasVendaConsignacaoTipoVendedorProduto = new ArrayList<>();
	private List<PoliticaAberturaTipoVendedorTipoProduto> listaPoliticaAberturaTipoProduto = new ArrayList<>();
	private List<PoliticaColocacaoTipoVendedorTipoProduto> listaPoliticaColocacaoTipoProduto = new ArrayList<>();
	
	
	@Column(nullable = false, length = 20)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<PoliticaVendaConsignacaoTipoVendedorProduto> getPoliticasVendaConsignacaoTipoVendedorProduto() {
		return politicasVendaConsignacaoTipoVendedorProduto;
	}

	public void setPoliticasVendaConsignacaoTipoVendedorProduto(List<PoliticaVendaConsignacaoTipoVendedorProduto> politicasVendaConsignacaoTipoVendedorProduto) {
		this.politicasVendaConsignacaoTipoVendedorProduto = politicasVendaConsignacaoTipoVendedorProduto;
	}
	
	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<PoliticaAberturaTipoVendedorTipoProduto> getListaPoliticaAberturaTipoProduto() {
		return listaPoliticaAberturaTipoProduto;
	}

	public void setListaPoliticaAberturaTipoProduto(
			List<PoliticaAberturaTipoVendedorTipoProduto> listaPoliticaAberturaTipoProduto) {
		this.listaPoliticaAberturaTipoProduto = listaPoliticaAberturaTipoProduto;
	}
	
	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<PoliticaColocacaoTipoVendedorTipoProduto> getListaPoliticaColocacaoTipoProduto() {
		return listaPoliticaColocacaoTipoProduto;
	}

	public void setListaPoliticaColocacaoTipoProduto(
			List<PoliticaColocacaoTipoVendedorTipoProduto> listaPoliticaColocacaoTipoProduto) {
		this.listaPoliticaColocacaoTipoProduto = listaPoliticaColocacaoTipoProduto;
	}

	@Transient
	public BigDecimal taxaComissao(Produto produto, int quantidade, boolean prontaEntrega) {

		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVendaConsignacaoTipoVendedorProduto) {
			if (p.getProduto().equals(produto)) {
				p.taxaComissao(quantidade, prontaEntrega);
			}
		}

		return BigDecimal.ZERO;
	}
	
	@Transient
	public BigDecimal minVenda(Produto produto, int quantidade) {

		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVendaConsignacaoTipoVendedorProduto) {
			if (p.getProduto().equals(produto)) {
				p.minVenda(quantidade);
			}
		}

		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal minConsignacao(Produto produto, int quantidade) {

		for (PoliticaVendaConsignacaoTipoVendedorProduto p : politicasVendaConsignacaoTipoVendedorProduto) {
			if (p.getProduto().equals(produto)) {
				p.minConsignacao(quantidade);
			}
		}

		return BigDecimal.ZERO;
	}

	
	

}
