package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.service.CadastroDescontoSalario;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class DescontoSalarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private DescontoSalario item;
	private List<Funcionario> funcionarios; 
	
	@Inject
	private CadastroDescontoSalario cadastro;
	
	@Inject
	private Funcionarios fun;
	
	public void inicio() {
		funcionarios = fun.funcionarios();
	}
	
	public void limpar() {
		this.item = new DescontoSalario();
	}
	
	public void salvar() {

		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Desconto salvo com sucesso!");
			item = new DescontoSalario();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}


	public DescontoSalario getItem() {
		return item;
	}

	public void setItem(DescontoSalario item) {
		if(item == null) {
			this.item = new DescontoSalario();
			inicio();
		}
		else {
			inicio();
			this.item = item;
		}
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	

}
