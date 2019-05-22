package com.cepalab.sistemaVendas.cadastro.dominio;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@SuppressWarnings("serial")
@Entity
@Table(name = "cliente")
public class Cliente extends GenericDTO {

	private Long codigo;
	private TipoPessoa tipoPessoa;
	private String nome;
	private Rota rota;
	private String nomeFantasia;
	private String cpfCnpj;
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private String responsavelContato;
	private Date dataCadastro;
	private StatusCliente status;
	private String observacao;
	private Endereco endereco;
	private Contato contato;
	private Boolean bloqueado = false;
	
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@Column(length = 100, nullable=false)
	public String getNome() {
		return nome;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Column(length = 20, nullable=false)
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	@Column(length = 40, name="inscricao_estadual")
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@Column(length = 40, name="inscricao_municipal")
	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	@Column(length = 40, name="responsavel_contato")
	public String getResponsavelContato() {
		return responsavelContato;
	}

	public void setResponsavelContato(String responsavelContato) {
		this.responsavelContato = responsavelContato;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable=false)
	public StatusCliente getStatus() {
		return status;
	}

	public void setStatus(StatusCliente status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(nullable = false, name = "rota_id")
	public Rota getRota() {
		return rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}
	
	@Column(length = 100, name="nome_fantasia")
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Column(columnDefinition="text")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Embedded
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Embedded
	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}


	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}	
	
	public String codigoOuCnpj() {
		if(codigo != null) {
			return codigo.toString();
		}
		else return cpfCnpj;
	}
	
	public String cpfCnpjSoNumero() {
		return cpfCnpj.replace(".", "").replaceAll("-", "").replace("/", "");
	}
	
}
