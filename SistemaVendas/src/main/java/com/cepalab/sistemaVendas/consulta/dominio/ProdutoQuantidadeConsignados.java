package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;

public class ProdutoQuantidadeConsignados implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto;
	private int quantidade;
	private BigDecimal somaValoresDeVenda = BigDecimal.ZERO;
	private int id;

	public BigDecimal custoTotal() {
		return produto.getCusto().multiply(BigDecimal.valueOf(quantidade));
	}

	public BigDecimal valorMedioDeVenda() {
		if (this.quantidade != 0) {
			return somaValoresDeVenda.divide(BigDecimal.valueOf(quantidade), BigDecimal.ROUND_HALF_UP);
		}
		return BigDecimal.ZERO;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getSomaValoresDeVenda() {
		return somaValoresDeVenda;
	}

	public void setSomaValoresDeVenda(BigDecimal somaValoresDeVenda) {
		this.somaValoresDeVenda = somaValoresDeVenda;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoQuantidadeConsignados other = (ProdutoQuantidadeConsignados) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	public void incrementaQuantidade(ProdutoQuantidadeConsignados p) {
		this.quantidade = this.quantidade + p.getQuantidade();
	}

	public void incrementaSomaValorVenda(BigDecimal valor) {
		this.somaValoresDeVenda = this.somaValoresDeVenda.add(valor);
	}

}
