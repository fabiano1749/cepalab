package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.repository.TiposProdutos;
import com.cepalab.sistemaVendas.service.CadastroTipoProdutoService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TipoProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private TipoProduto item;

	@Inject
	private TiposProdutos tipos;

	@Inject
	private CadastroTipoProdutoService cadastroTipo;

	public TipoProdutoBean() {
		limpar();
	}

	public void limpar() {
		item = new TipoProduto();
	}

	public void salvar() {
		try {
			cadastroTipo.salvar(item);
			limpar();
			FacesUtil.addInfoMessage("Tipo de produto salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}

	}

	public TipoProduto getItem() {
		return item;
	}

	public void setItem(TipoProduto item) {
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("tipo");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = tipos.porId2(id);
		}
		if (item == null) {
			limpar();
		} else {
			this.item = item;

		}
	}

}
