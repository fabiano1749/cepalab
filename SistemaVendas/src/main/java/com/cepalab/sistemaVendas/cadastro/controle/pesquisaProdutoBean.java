package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.repository.Produtos;

@Named
@ViewScoped
public class pesquisaProdutoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;
	

	private List<Produto> todosProdutos;
		
	public pesquisaProdutoBean() {
		todosProdutos = produtos.produtos();
	}

	public List<Produto> getTodosProdutos() {
		return todosProdutos;
	}

	public void setTodosProdutos(List<Produto> todosProdutos) {
		this.todosProdutos = todosProdutos;
	}
	

}
