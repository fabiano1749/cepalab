package com.cepalab.sistemaVendas.Expedicao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.Expedicao.dominio.StatusExpedicao;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.repository.Expedicoes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.filter.ExpedicaoFilter;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaExpedicaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Expedicao> listaExpedicao = new ArrayList<>();
	private ExpedicaoFilter filtroExpedicao;
	private Expedicao expedicao = new Expedicao();
	
	
	@Inject
	private Funcionarios funcionarios;
	
	@Inject
	private Expedicoes expedicoes;
	
	@PostConstruct
	public void inicio() {
		listaFuncionarios = funcionarios.vendedor();
		filtroExpedicao = new ExpedicaoFilter();
	}
	
	public void excluir() {
		try {
			expedicoes.remover(this.expedicao);
			pesquiza();
			FacesUtil.addInfoMessage("Expedição excluida com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
		catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	
	public void pesquiza() {
		listaExpedicao = expedicoes.ExpedicoesFiltradas(filtroExpedicao);
	}
	
	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void abrirDialogo(Expedicao expedicao) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 600);
		opcoes.put("contentWidth", 850);

		List<String> lista = new ArrayList<>();
		lista.add("" + expedicao.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("expedicao", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/expedicaoDialogo", opcoes, params);
	}
	
	
	public StatusExpedicao[] status() {
		return StatusExpedicao.values();
	}

	public void alimentaExpedicao(Expedicao expedicao) {
		this.expedicao = expedicao;
	}
	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Expedicao> getListaExpedicao() {
		return listaExpedicao;
	}

	public void setListaExpedicao(List<Expedicao> listaExpedicao) {
		this.listaExpedicao = listaExpedicao;
	}

	public ExpedicaoFilter getFiltroExpedicao() {
		return filtroExpedicao;
	}

	public void setFiltroExpedicao(ExpedicaoFilter filtroExpedicao) {
		this.filtroExpedicao = filtroExpedicao;
	}

	public Expedicao getExpedicao() {
		return expedicao;
	}

	public void setExpedicao(Expedicao expedicao) {
		this.expedicao = expedicao;
	}
	

}
