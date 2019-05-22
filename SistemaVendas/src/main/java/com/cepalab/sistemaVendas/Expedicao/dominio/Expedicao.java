package com.cepalab.sistemaVendas.Expedicao.dominio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;

@SuppressWarnings("serial")
@Entity
@Table(name="expedicao")
public class Expedicao extends GenericDTO{
	private Funcionario funcionario;
	private Date abertura = new Date();
	private Date acerto;
	private StatusExpedicao status = StatusExpedicao.ABERTO;
	private List<ExpedProduto> expedProdutos = new ArrayList<>();

	private boolean conferidoSaidaVendedor = false;
	private boolean conferidoChegadaVendedor = false;
	
	
	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToOne
	@JoinColumn(name = "funcionario_id", nullable=false)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	public Date getAbertura() {
		return abertura;
	}

	public void setAbertura(Date abertura) {
		this.abertura = abertura;
	}

	
	@Temporal(TemporalType.DATE)
	public Date getAcerto() {
		return acerto;
		
	}

	public void setAcerto(Date acerto) {
		this.acerto = acerto;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public StatusExpedicao getStatus() {
		return status;
	}

	public void setStatus(StatusExpedicao status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "expedicao", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
	public List<ExpedProduto> getExpedProdutos() {
		return expedProdutos;
	}

	public void setExpedProdutos(List<ExpedProduto> expedProdutos) {
		this.expedProdutos = expedProdutos;
	}

	@Column(name="conferido_saida_vendedor", nullable = false)
	public boolean isConferidoSaidaVendedor() {
		return conferidoSaidaVendedor;
	}

	public void setConferidoSaidaVendedor(boolean conferidoSaidaVendedor) {
		this.conferidoSaidaVendedor = conferidoSaidaVendedor;
	}

	@Column(name="conferido_chegada_vendedor", nullable = false)
	public boolean isConferidoChegadaVendedor() {
		return conferidoChegadaVendedor;
	}

	public void setConferidoChegadaVendedor(boolean conferidoChegadaVendedor) {
		this.conferidoChegadaVendedor = conferidoChegadaVendedor;
	}
	
	
	
}
