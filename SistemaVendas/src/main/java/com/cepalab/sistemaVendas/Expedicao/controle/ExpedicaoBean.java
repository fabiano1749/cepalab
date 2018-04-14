package com.cepalab.sistemaVendas.Expedicao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.Expedicao.dominio.ExpedProduto;
import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.Expedicao.dominio.StatusExpedicao;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.repository.Expedicoes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.service.CadastroExpedicaoService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ExpedicaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Expedicao item;
	private List<Produto> listaProdutos;
	private List<Funcionario> listaFuncionarios;
	private List<Expedicao> listaExpedicao = new ArrayList<>(); 
	private boolean statusFechado = false;
	
	
	
	@Inject
	private Produtos produtos;

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private CadastroExpedicaoService cadastro;
	
	@Inject
	private Expedicoes expedicoes;
	
	public void inicio() {
		this.item = new Expedicao();
		listaProdutos = produtos.produtos();
		listaFuncionarios = funcionarios.vendedor();
		iniciaListaExpedicaoProduto();
	}

	public void salvar() {
		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Expedição salva com sucesso!");
			this.item = new Expedicao();
			iniciaListaExpedicaoProduto();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	
	public void iniciaListaExpedicaoProduto() {
		item.setExpedProdutos(new ArrayList<>());
		if (listaProdutos != null) {
			for (Produto p : listaProdutos) {
				ExpedProduto ep = new ExpedProduto();
				ep.setProduto(p);
				ep.setExpedicao(item);
				item.getExpedProdutos().add(ep);
			}
		}
	}

	public StatusExpedicao[] status() {
		return StatusExpedicao.values();
	}

	public void statusFechado() {
		if(item.getStatus().equals(StatusExpedicao.FECHADO)) {
			this.statusFechado = true;
		}
		else {
			this.statusFechado = false;
			this.item.setAcerto(null);
		}
	}
	
	public Expedicao getItem() {
		return item;
	}

	public void setItem(Expedicao item) {
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("expedicao");
		
		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = expedicoes.porId2(id);
		}
		
		
		
		if (item == null) {
			inicio();
		} else {
			listaFuncionarios = funcionarios.funcionarios();
			this.item = item;
			statusFechado();
		}
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

	public boolean isStatusFechado() {
		return statusFechado;
	}

	public void setStatusFechado(boolean statusFechado) {
		this.statusFechado = statusFechado;
	}

	
}
