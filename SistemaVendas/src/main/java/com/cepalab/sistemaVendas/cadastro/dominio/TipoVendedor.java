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
	private List<ComissaoTipoVendedorProduto> comissaoTipoVendedor = new ArrayList<ComissaoTipoVendedorProduto>();

	@Column(nullable = false, length = 20)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(mappedBy = "tipoVendedor", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ComissaoTipoVendedorProduto> getComissaoTipoVendedor() {
		return comissaoTipoVendedor;
	}

	public void setComissaoTipoVendedor(List<ComissaoTipoVendedorProduto> comissaoTipoVendedor) {
		this.comissaoTipoVendedor = comissaoTipoVendedor;
	}

}
