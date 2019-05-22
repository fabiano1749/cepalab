package com.cepalab.sistemaVendas.cadastro.dominio;

import java.io.Serializable;

public class TipoProdutoQuantidade implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoProduto tipo = new TipoProduto();
	private int quantidade;
	
	
	public TipoProduto getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void icrementaQuantidade() {
		quantidade ++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		TipoProdutoQuantidade other = (TipoProdutoQuantidade) obj;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}			
}
