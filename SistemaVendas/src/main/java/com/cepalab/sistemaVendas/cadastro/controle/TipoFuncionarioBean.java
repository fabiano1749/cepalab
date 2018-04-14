package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoFuncionario;
import com.cepalab.sistemaVendas.repository.Grupos;
import com.cepalab.sistemaVendas.repository.TiposFuncionarios;
import com.cepalab.sistemaVendas.service.CadastroTipoFuncionarioService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TipoFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoFuncionario item;
	private List<TipoFuncionario> itens;
	private TipoFuncionario itemAux;
	private List<Grupo> grupoUsuarios; 
	
	@Inject
	private Grupo grupo;
	
	@Inject
	private Grupos grupos;
	
	
	
	@Inject
	private TiposFuncionarios tipos;

	@Inject
	private CadastroTipoFuncionarioService cadastroTipo;

	public TipoFuncionarioBean() {
		limpar();
	}

	public void limpar() {
		item = new TipoFuncionario();
		itemAux = new TipoFuncionario();
		grupo = new Grupo();
	}

	@PostConstruct
	public void inicio() {
		itens = tipos.tipos();
		grupoUsuarios = grupos.grupos();
		
	}

	public void salvar() {

		try {
			item.getGrupos().add(grupo);
			cadastroTipo.salvar(item);
			inicio();
			limpar();
			FacesUtil.addInfoMessage("Tipo de funcionário salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}

	}

	public void editar() {

		try {
			cadastroTipo.salvar(itemAux);
			FacesUtil.addInfoMessage("Tipo de funcionário alterado com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}finally {
			inicio();
			limpar();
		}

	}

	public void excluir() {
		try {
			tipos.remover(itemAux);
			inicio();
			limpar();
			FacesUtil.addInfoMessage("Tipo de funcionário excluido com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}

	}

	public void troca(TipoFuncionario itemAux) {
		this.itemAux = itemAux;
	}

	public TipoFuncionario getItem() {
		return item;
	}

	public void setItem(TipoFuncionario item) {
		this.item = item;
	}

	public List<TipoFuncionario> getItens() {
		return itens;
	}

	public void setItens(List<TipoFuncionario> itens) {
		this.itens = itens;
	}

	public TipoFuncionario getItemAux() {
		return itemAux;
	}

	public void setItemAux(TipoFuncionario itemAux) {
		this.itemAux = itemAux;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Grupos getGrupos() {
		return grupos;
	}

	public void setGrupos(Grupos grupos) {
		this.grupos = grupos;
	}

	public List<Grupo> getGrupoUsuarios() {
		return grupoUsuarios;
	}

	public void setGrupoUsuarios(List<Grupo> grupoUsuarios) {
		this.grupoUsuarios = grupoUsuarios;
	}
	
	
	
}
