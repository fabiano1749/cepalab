package com.cepalab.sistemaVendas.cadastro.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Contato implements Serializable{

	private static final long serialVersionUID = 1L;

	private String celular;
	private String fixo;
	private String email1;
	private String email2;

	@Column(length=15)
	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Column(length=15)
	public String getFixo() {
		return fixo;
	}

	public void setFixo(String fixo) {
		this.fixo = fixo;
	}
	

	@Column(length=40, name="email_1")
	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
	@Column(length=40, name="email_2")
	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
}
