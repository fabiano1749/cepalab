package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;


public class ConsignadosConsulta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private List<ProdutoQuantidadeConsignados> listaConsignados = new ArrayList<>();
	private Rota rota;
	private Funcionario funcionario;
	private String uf;
	private String cidade;
	private int consignados;
	
	public ProdutoQuantidadeConsignados adcionaProduto(ProdutoQuantidadeConsignados produtoQuantidade) {
		int index = listaConsignados.indexOf(produtoQuantidade);
		ProdutoQuantidadeConsignados aux = listaConsignados.get(index);
		if(produtoQuantidade.getId() > aux.getId()) {
			aux.setQuantidade(produtoQuantidade.getQuantidade());
			
			ProdutoQuantidadeConsignados p = new ProdutoQuantidadeConsignados();
			p.setProduto(aux.getProduto());
			p.setQuantidade(produtoQuantidade.getQuantidade() - aux.getQuantidade());
			return p;
		}
		return null;
	}
	
	public int consignados(Produto produto) {
		ProdutoQuantidadeConsignados p = new ProdutoQuantidadeConsignados();
		p.setProduto(produto);
		if(listaConsignados.contains(p)) {
			int index = listaConsignados.indexOf(p);
			return listaConsignados.get(index).getQuantidade();
		}
		
		return 0;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Rota getRota() {
		return rota;
	}
	public void setRota(Rota rota) {
		this.rota = rota;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public int getConsignados() {
		return consignados;
	}
	public void setConsignados(int consignados) {
		this.consignados = consignados;
	}
	
	public List<ProdutoQuantidadeConsignados> getListaConsignados() {
		return listaConsignados;
	}
	public void setListaConsignados(List<ProdutoQuantidadeConsignados> listaConsignados) {
		this.listaConsignados = listaConsignados;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		ConsignadosConsulta other = (ConsignadosConsulta) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}
}
