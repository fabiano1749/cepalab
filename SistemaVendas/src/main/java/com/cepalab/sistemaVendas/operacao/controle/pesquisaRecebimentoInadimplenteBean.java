package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.RecebimentosInadiplentes;
import com.cepalab.sistemaVendas.repository.filter.RecebimentoInadimplenteFilter;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class pesquisaRecebimentoInadimplenteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private RecebimentoInadimplenteFilter filtro;
	private List<RecebimentoInadiplente> recebimentosFiltrados = new ArrayList<>();
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private RecebimentoInadiplente recebimento = new RecebimentoInadiplente();

	@Inject
	private RecebimentosInadiplentes recebimentos;

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Seguranca seg;

	@PostConstruct
	public void inicio() {
		filtro = new RecebimentoInadimplenteFilter();
		
		if (isVendedor()) {
			filtro.setFuncionario(seg.UsuarioLogado());
		} else {
			listaFuncionarios = funcionarios.vendedorAtivo();
		}
	}

	public void pesquisar() {
		recebimentosFiltrados = recebimentos.recebimentosFiltrados(filtro);

	}

	public boolean isVendedor() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("VENDEDORES")) {
				return true;
			}

		}
		return false;
	}

	public void troca(RecebimentoInadiplente recebimento) {
		this.recebimento = recebimento;
	}
	
	public void excluir() {
		try {
			recebimentos.remover(recebimento);
			FacesUtil.addInfoMessage("Recebimento inadimplente excluído com sucesso");
			pesquisar();
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}	
	}
	
	public List<RecebimentoInadiplente> getRecebimentosFiltrados() {
		return recebimentosFiltrados;
	}

	public void setRecebimentosFiltrados(List<RecebimentoInadiplente> recebimentosFiltrados) {
		this.recebimentosFiltrados = recebimentosFiltrados;
	}

	public RecebimentoInadimplenteFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(RecebimentoInadimplenteFilter filtro) {
		this.filtro = filtro;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public RecebimentoInadiplente getRecebimento() {
		return recebimento;
	}

	public void setRecebimento(RecebimentoInadiplente recebimento) {
		this.recebimento = recebimento;
	}

	
	
	
}
