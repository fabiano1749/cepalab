package com.cepalab.sistemaVendas.cadastro.dominio;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "movimento")
public class Movimento extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)
	public Long getId() {
		return id;
	}

	private Date inicio;
	private Date fim;
	private StatusMovimento status;
	private Funcionario funcionario;
	
		
}
