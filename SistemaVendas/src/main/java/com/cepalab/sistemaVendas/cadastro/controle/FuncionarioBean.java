package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;


import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.StatusVendedor;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoFuncionario;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.TiposFuncionarios;
import com.cepalab.sistemaVendas.repository.TiposVendedores;
import com.cepalab.sistemaVendas.service.CadastroFuncionarioService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;
import com.cepalab.sistemaVendas.utils.GeraTestaCpfCnpj;
import com.cepalab.sistemaVendas.utils.ValidaSenha;

@Named
@ViewScoped
public class FuncionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Funcionario item;
	private List<TipoFuncionario> tiposFuncionarios;
	private List<TipoVendedor> tiposVend;

	@Inject
	private CadastroFuncionarioService cadastroFuncionario;

	@Inject
	private TiposFuncionarios tiposFun;

	@Inject
	private TiposVendedores tiposVendedores;

	@Inject
	private Funcionarios funcionarios;

	public void limpar() {
		item = new Funcionario();
	}

	public void inicio() {
		tiposFuncionarios = tiposFun.tipos();
		tiposVend = tiposVendedores.tipos();
	}

	
	
	public void salvar() {
		try {

			cadastroFuncionario.salvar(item);
			FacesUtil.addInfoMessage("Funcionario salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			inicio();
			limpar();
		}

	}

	public StatusVendedor[] statusFuncionario() {
		return StatusVendedor.values();
	}

	public Funcionario getitem() {
		return item;
	}

	public void setItem(Funcionario item) {
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("fun");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = funcionarios.porId2(id);
		}

		if (item == null) {
			
			limpar();
			inicio();
		}

		else {
			inicio();
			this.item = item;
		}
	}

	public void validaCPF() {
		GeraTestaCpfCnpj teste = new GeraTestaCpfCnpj();
		if(teste.isCPF(item.getCpf()) == false) {
			FacesUtil.addErrorMessage("O CPF informado não é válido!");
			item.setCpf("");
		}	
	}
	
	public void validaSenha() {
		if(ValidaSenha.senhaValida(item.getSenha()) == false) {
			FacesUtil.addErrorMessage("A senha deve possuir números e letras e conter 6 ou mais caracteres !");
			item.setSenha("");
		}
	}
	
	public List<TipoFuncionario> getTiposFuncionarios() {
		return tiposFuncionarios;
	}

	public List<TipoVendedor> getTiposVend() {
		return tiposVend;
	}

	public void setTiposVend(List<TipoVendedor> tiposVend) {
		this.tiposVend = tiposVend;
	}

}
