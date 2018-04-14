package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Rotas;
import com.cepalab.sistemaVendas.service.CadastroRotaService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RotaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Rota item;
	private List<Funcionario> funcionarios;

	@Inject
	private CadastroRotaService cadastroRota;

	@Inject
	private Rotas rotas;
	
	@Inject
	private Funcionarios fun;

	private void limpar() {
		item = new Rota();
		getItem().setDescricao("");
	}
	
	public void salvar() {
		try {
			cadastroRota.salvar(item);
			FacesUtil.addInfoMessage("Rota salva com sucesso!");
			limpar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public Rota getItem() {
		return item;
	}

	public void setItem(Rota item) {
		funcionarios = fun.funcionarios();

		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("rota");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = rotas.porId2(id);
		}
		
		
		if (item != null) {
			this.item = item;
		}
		else {
			limpar();
		}

	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
