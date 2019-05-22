package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.operacao.dominio.TipoCustosViagem;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.CadastroCustoViagem;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CustoViagemBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private CustoViagem item;
	private List<Funcionario> listaFuncionarios;
	
	
	@Inject
	private Seguranca seg;

	@Inject
	private CustosViagens custos;

	@Inject
	private CadastroCustoViagem cadastroCusto;

	
	@Inject
	private Funcionarios funcionarios;
	
	public CustoViagemBean() {
		limpar();
	}	
	
	public void inicio() {
		limpar();
		listaFuncionarios = funcionarios.funcionarios();
	}
	
	public void limpar() {
		item = new CustoViagem();
	}

	public void salvar() {
		try {
			item.setFuncionario(seg.UsuarioLogado());
			cadastroCusto.salvar(item);
			FacesUtil.addInfoMessage("Custo salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public TipoCustosViagem[] tiposCustos() {
		return TipoCustosViagem.values();
	}

	
	public boolean isRoot() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ROOT")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isVendedor() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("VENDEDORES")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean podeSalvar() {
		if(isRoot() || isVendedor()) {
			return true;
		}
		return false;
	}
	
	public CustoViagem getItem() {
		return item;
	}

	public void setItem(CustoViagem item) {
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("custo");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = custos.porId2(id);
		}
		if (item == null) {
			limpar();
		} else {
			this.item = item;

		}
	
		listaFuncionarios = funcionarios.funcionarios();

	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}
}
