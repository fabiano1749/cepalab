package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.financeiro.dominio.Conta;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoConta;
import com.cepalab.sistemaVendas.repository.Contas;
import com.cepalab.sistemaVendas.service.CadastroContaService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ContaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Conta item;
	private List<Conta> itens;

	@Inject
	private Contas contas;

	@Inject
	private CadastroContaService cadastro;

	@PostConstruct
	public void inicio() {
		this.item = new Conta();
		itens = contas.contas();
	}

	public void salvar() {

		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Conta salva com sucesso!");
			inicio();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}
	
	public void excluir() {
		try {
			contas.remover(this.item);
			inicio();	
			FacesUtil.addInfoMessage("Conta excluida com sucesso!");
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		
	}

	public TipoConta[] tipos() {
		return TipoConta.values();
	}
	
	public void limpa() {
		this.item = new Conta();
	}
	
	public Conta getItem() {
		return item;
	}

	public void setItem(Conta item) {
		this.item = item;
	}

	public List<Conta> getItens() {
		return itens;
	}

	public void setItens(List<Conta> itens) {
		this.itens = itens;
	}
	
}
