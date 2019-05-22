package com.cepalab.sistemaVendas.financeiro.dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
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

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;

@SuppressWarnings("serial")
@Entity
@Table(name = "cheque")
public class Cheque extends GenericDTO{

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	public Long getId() {
		return id;
	}
	
	private String numero;
	private Banco banco;
	private String agencia;
	private String conta;
	private BigDecimal valor;
	private Date dataCadastro;
	private Date bomPara;
	private String nome;
	private String origem;
	private Funcionario funcionario;
	private Cliente cliente;
	private StatusCheque status;
	private String observacao;
	
	@Column(nullable=false)
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@ManyToOne
	@JoinColumn(name = "banco_id", nullable=false)
	public Banco getBanco() {
		return banco;
	}
	
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	@Column(nullable=false)
	public String getAgencia() {
		return agencia;
	}
	
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	
	@Column(nullable=false)
	public String getConta() {
		return conta;
	}
	
	public void setConta(String conta) {
		this.conta = conta;
	}
	
	@Column(nullable=false, precision = 10, scale = 2)
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", nullable = false)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "bom_para", nullable = false)
	public Date getBomPara() {
		return bomPara;
	}
	
	public void setBomPara(Date bomPara) {
		this.bomPara = bomPara;
	}
	
	@Column(nullable = false)
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getOrigem() {
		return origem;
	}
	
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	@ManyToOne
	@JoinColumn(name="funcionario_id", nullable = true)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@ManyToOne
	@JoinColumn(name="cliente_id", nullable = true)
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable=false)
	public StatusCheque getStatus() {
		return status;
	}
	
	public void setStatus(StatusCheque status) {
		this.status = status;
	}
	
	@Column(length = 200)
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}
