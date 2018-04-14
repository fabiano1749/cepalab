package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.cadastro.dominio.Transportadora;
import com.cepalab.sistemaVendas.repository.Transportadoras;
import com.cepalab.sistemaVendas.service.CadastroTransportadoraService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TransportadoraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Transportadora item;

	@Inject
	private CadastroTransportadoraService cadastroTransportadora;

	@Inject
	private Transportadoras transportadoras;
	
	private void limpar() {
		item = new Transportadora();
	}
	
	public void salvar() {
		try {
			cadastroTransportadora.salvar(item);
			FacesUtil.addInfoMessage("Transportadora salva com sucesso!");
			limpar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public Transportadora getItem() {
		return item;
	}

	public void setItem(Transportadora item) {
		
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("transportadora");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = transportadoras.porId2(id);
		}
		
		if (item != null) {
			this.item = item;
		}
		else {
			limpar();
		}

	}

}
