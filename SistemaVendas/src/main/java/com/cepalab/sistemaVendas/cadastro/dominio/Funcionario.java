package com.cepalab.sistemaVendas.cadastro.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "funcionario")
public class Funcionario extends GenericDTO {

	private String nome;
	private TipoFuncionario tipo;
	private String cpf;
	private String telefone;
	private String email;
	private StatusVendedor status;
	private TipoVendedor tipoVendedor;
	private BigDecimal salario;
	private String placaCarro;
	private String senha;
	private List<Rota> rotas = new ArrayList<>();
	private List<ComissaoRecolhidaRessarcida> comissaoRecolhidaRessarcida;
	
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(nullable = false, length = 50)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@ManyToOne
	@JoinColumn(name = "tipoFuncionario_id")
	public TipoFuncionario getTipo() {
		return tipo;
	}

	public void setTipo(TipoFuncionario tipo) {
		this.tipo = tipo;
	}

	@Column(nullable = false, length = 15)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf.replace(".", "").replace("-", "");
	}

	@Column(length = 15)
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(length = 40)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	@ManyToOne
	@JoinColumn(name = "tipoVendedor_id", nullable=false)
	public TipoVendedor getTipoVendedor() {
		return tipoVendedor;
	}

	public void setTipoVendedor(TipoVendedor tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	@Column(name = "placa_carro", length = 15)
	public String getPlacaCarro() {
		return placaCarro;
	}

	public void setPlacaCarro(String placaCarro) {
		this.placaCarro = placaCarro;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public StatusVendedor getStatus() {
		return status;
	}

	public void setStatus(StatusVendedor status) {
		this.status = status;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}

	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ComissaoRecolhidaRessarcida> getComissaoRecolhidaRessarcida() {
		return comissaoRecolhidaRessarcida;
	}

	public void setComissaoRecolhidaRessarcida(List<ComissaoRecolhidaRessarcida> comissaoRecolhidaRessarcida) {
		this.comissaoRecolhidaRessarcida = comissaoRecolhidaRessarcida;
	}
	
	public String primeiroNome() {
		return nome.substring(0, nome.indexOf(" "));
	}
}
