package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.RecolhidaRessarcida;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.service.CadastroComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ComissaoRecolhidaRessarcidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ComissaoRecolhidaRessarcida item;
	private List<ComissaoRecolhidaRessarcida> itens;
	private List<Funcionario> listaFuncionario;

	@Inject
	private CadastroComissaoRecolhidaRessarcida cadastro;

	@Inject
	private Funcionarios funcionarios;

	@PostConstruct
	public void inicio() {
		item = new ComissaoRecolhidaRessarcida();
		itens = new ArrayList<ComissaoRecolhidaRessarcida>();
		listaFuncionario = funcionarios.funcionarios();
	}

	public void limpar() {
		item = new ComissaoRecolhidaRessarcida();
	}
	
	public void salvar() {
		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Comissão Recolhida Ressarcida salva com sucesso!");
			item = new ComissaoRecolhidaRessarcida();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public RecolhidaRessarcida[] tipo() {
		return RecolhidaRessarcida.values();
	}

	public ComissaoRecolhidaRessarcida getItem() {
		return item;
	}

	public void setItem(ComissaoRecolhidaRessarcida item) {
		if(item == null) {
		limpar();
		}
		else {
			this.item = item;
		}
	}

	public List<ComissaoRecolhidaRessarcida> getItens() {
		return itens;
	}

	public void setItens(List<ComissaoRecolhidaRessarcida> itens) {
		this.itens = itens;
	}

	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}

	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}
}
