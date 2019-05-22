package com.cepalab.sistemaVendas.cadastro.dominio;


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

@Entity
@Table(name="tipo_produto")
public class TipoProduto extends GenericDTO{

	private static final long serialVersionUID = 1L;
		
	private String nome;
	private List<Produto> produtos = new ArrayList<>();
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@Column(nullable=false, length=40)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	@Transient
	public int quantProdutos() {
		return produtos.size();
	}
	
	
	
}
