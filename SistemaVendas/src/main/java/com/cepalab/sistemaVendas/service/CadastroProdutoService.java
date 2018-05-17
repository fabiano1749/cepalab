package com.cepalab.sistemaVendas.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.util.jpa.Transactional;


public class CadastroProdutoService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;
	
	@Transactional
	public Produto salvar(Produto produto) {
		
		Produto produtoExistente = produtos.porNome(produto.getNome());
		Produto codigoExistente = produtos.porPosicao(produto.getPosicao());
		
		if(produtoExistente != null && produto.getId() ==null) {
			throw new NegocioException("Já existe um produto com o nome informado!");
		}
		
		if(produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe um produto com o nome informado!");
		}
		
		if(codigoExistente != null && produto.getId() ==null) {
			throw new NegocioException("Já existe um produto com a posição informada");
		}
		
		if(codigoExistente != null && produto.getId() != codigoExistente.getId()) {
			throw new NegocioException("Já existe um produto com a posição informada");
		}
		
		return produtos.adicionar(produto);
		
	}
	
}
