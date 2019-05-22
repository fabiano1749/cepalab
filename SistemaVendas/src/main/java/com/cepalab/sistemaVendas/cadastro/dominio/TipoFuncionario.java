package com.cepalab.sistemaVendas.cadastro.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tipo_funcionario")
public class TipoFuncionario extends GenericDTO {

	private String nome;
	private List<Grupo> grupos = new ArrayList<>();

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(nullable = false, length = 20)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tipoFuncionario_grupo", joinColumns = @JoinColumn(name = "tipoFuncionario_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Grupo primeiroElemento() {

		if (!grupos.isEmpty()) {
			return grupos.get(0);
		}
		else return null;
	}

}
