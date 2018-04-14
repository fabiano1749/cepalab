package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.operacao.dominio.TipoDespesa;
import com.cepalab.sistemaVendas.repository.DespesasVendedores;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.service.CadastroDespesaVendedor;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class DespesaVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private DespesaVendedor item;
	private DespesaVendedor itemAux;
	private List<DespesaVendedor> itens;
	private List<Funcionario> funcionarios; 

	@Inject
	private DespesasVendedores despesas;
	
	@Inject
	private CadastroDespesaVendedor cadastroDespesa;
	
	@Inject
	private Funcionarios fun;
	
	
	public DespesaVendedorBean() {
		limpar();
	}
	
	public void limpar() {
		item = new DespesaVendedor();
		itemAux = new DespesaVendedor();
	}
	
	@PostConstruct
	public void inicio() {
		itens = despesas.despesas();
		funcionarios = fun.funcionarios();
	}
	
	public void salvar() {

		try {
			cadastroDespesa.salvar(item);
			FacesUtil.addInfoMessage("Despesa salva com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}finally {
			inicio();
			limpar();
		}

	}

	
	public void editar() {
		try {
		
			cadastroDespesa.salvar(itemAux);
			FacesUtil.addInfoMessage("Despesa alterada com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}finally {
			inicio();
			limpar();
		}

	}
	
	
	public void excluir() {
		try {
			despesas.remover(itemAux);
			inicio();
			limpar();
			FacesUtil.addInfoMessage("Despesa excluida com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}finally {
			inicio();
			limpar();
		}

	}
	
	public void troca(DespesaVendedor item) {
		this.itemAux = item;
	}
	
	public TipoDespesa[] tiposDespesas() {
		return TipoDespesa.values();
	}
	
	
	public DespesaVendedor getItem() {
		return item;
	}

	public void setItem(DespesaVendedor item) {
		this.item = item;
	}

	public DespesaVendedor getItemAux() {
		return itemAux;
	}

	public void setItemAux(DespesaVendedor itemAux) {
		this.itemAux = itemAux;
	}

	public List<DespesaVendedor> getItens() {
		return itens;
	}

	public void setItens(List<DespesaVendedor> itens) {
		this.itens = itens;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	
	
	
}
