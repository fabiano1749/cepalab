package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.financeiro.dominio.Banco;
import com.cepalab.sistemaVendas.repository.Bancos;
import com.cepalab.sistemaVendas.service.CadastroBancoService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class BancoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Banco item;
	private List<Banco> itens;

	@Inject
	private Bancos bancos;

	@Inject
	private CadastroBancoService cadastro;

	@PostConstruct
	public void inicio() {
		this.item = new Banco();
		itens = bancos.bancos();
	}

	public void salvar() {

		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Banco salvo com sucesso!");
			inicio();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}
	
	public void excluir() {
		try {
			bancos.remover(this.item);
			inicio();	
			FacesUtil.addInfoMessage("Banco excluido com sucesso!");
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}

	public void limpa() {
		this.item = new Banco();
	}
	
	public Banco getItem() {
		return item;
	}

	public void setItem(Banco item) {
		this.item = item;
	}

	public List<Banco> getItens() {
		return itens;
	}

	public void setItens(List<Banco> itens) {
		this.itens = itens;
	}
	
}
