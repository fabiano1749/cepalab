package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.repository.Grupos;
import com.cepalab.sistemaVendas.service.CadastroGrupoService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class GrupoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private Grupo item;
	private List<Grupo> itens;
	private Grupo itemAux;

	@Inject
	private Grupos grupos;

	@Inject
	private CadastroGrupoService cadastroGrupo;
	
	

	public GrupoBean() {
		limpar();
	}

	public void limpar() {
		item = new Grupo();
		itemAux = new Grupo();
	}

	@PostConstruct
	public void inicio() {
		itens = grupos.grupos();
	}

	public void salvar() {
		try {
			cadastroGrupo.salvar(item);
			FacesUtil.addInfoMessage("Grupo salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}finally {
			inicio();
			limpar();
		}

	}

	public void editar() {
		try {
			cadastroGrupo.salvar(itemAux);
			FacesUtil.addInfoMessage("Grupo alterado com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}finally {
			inicio();
			limpar();
		}

	}

	
	
	public void excluir() {
		try {
			grupos.remover(itemAux);
			inicio();
			limpar();
			FacesUtil.addInfoMessage("Grupo excluido com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}

	}

	public void troca(Grupo item) {
		this.itemAux = item;
	}

	public Grupo getitem() {
		return item;
	}

	public void setItem(Grupo item) {
		this.item = item;
	}

	public List<Grupo> getItens() {
		return itens;
	}

	public void setItens(List<Grupo> itens) {
		this.itens = itens;
	}

	public Grupo getItemAux() {
		return itemAux;
	}

	public void setItemAux(Grupo itemAux) {
		this.itemAux = itemAux;
	}

	
	
}
