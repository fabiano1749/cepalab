package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.operacao.dominio.TipoDespesa;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.service.CadastroDespesaVendedor;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class DespesaVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DespesaVendedor item;
	private List<Funcionario> funcionarios;

	@Inject
	private CadastroDespesaVendedor cadastroDespesa;

	@Inject
	private Funcionarios fun;

	public DespesaVendedorBean() {
		limpar();
	}

	public void limpar() {
		item = new DespesaVendedor();
	}

	
	public void inicio() {
		funcionarios = fun.funcionarios();
	}

	public void salvar() {

		try {
			cadastroDespesa.salvar(item);
			FacesUtil.addInfoMessage("Despesa salva com sucesso!");
			limpar();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public TipoDespesa[] tiposDespesas() {
		return TipoDespesa.values();
	}

	public DespesaVendedor getItem() {
		return item;
	}

	public void setItem(DespesaVendedor item) {
		if (item == null) {
			this.item = new DespesaVendedor();
			inicio();
		}else {
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
