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
	private List<PoliticaTipoVendedorProduto> politicaTipoVendedor = new ArrayList<PoliticaTipoVendedorProduto>();

	@Column(nullable = false, length = 20)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<PoliticaTipoVendedorProduto> getPoliticaTipoVendedor() {
		return politicaTipoVendedor;
	}

	public void setPoliticaTipoVendedor(List<PoliticaTipoVendedorProduto> politicaTipoVendedor) {
		this.politicaTipoVendedor = politicaTipoVendedor;
	}

	@Transient
	public BigDecimal taxaComissao(Produto produto, int quantidade, boolean prontaEntrega) {

		for (PoliticaTipoVendedorProduto p : politicaTipoVendedor) {
			if (p.getProduto().equals(produto)) {
				p.taxaComissao(quantidade, prontaEntrega);
			}
		}

		return BigDecimal.ZERO;
	}
	
	@Transient
	public BigDecimal minVenda(Produto produto, int quantidade) {

		for (PoliticaTipoVendedorProduto p : politicaTipoVendedor) {
			if (p.getProduto().equals(produto)) {
				p.minVenda(quantidade);
			}
		}

		return BigDecimal.ZERO;
	}

	@Transient
	public BigDecimal minConsignacao(Produto produto, int quantidade) {

		for (PoliticaTipoVendedorProduto p : politicaTipoVendedor) {
			if (p.getProduto().equals(produto)) {
				p.minConsignacao(quantidade);
			}
		}

		return BigDecimal.ZERO;
	}

	
	

}
