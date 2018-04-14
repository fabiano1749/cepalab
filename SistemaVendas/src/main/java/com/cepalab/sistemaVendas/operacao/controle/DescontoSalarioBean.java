package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.operacao.dominio.TipoDespesa;
import com.cepalab.sistemaVendas.repository.DescontosSalarios;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.service.CadastroDescontoSalario;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class DescontoSalarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private DescontoSalario item;
	private DescontoSalario itemAux;
	private List<DescontoSalario> itens;
	private List<Funcionario> funcionarios; 

	@Inject
	private DescontosSalarios descontos;
	
	@Inject
	private CadastroDescontoSalario cadastroDesconto;
	
	@Inject
	private Funcionarios fun;
	
	
	public DescontoSalarioBean() {
		limpar();
	}
	
	public void limpar() {
		item = new DescontoSalario();
		itemAux = new DescontoSalario();
	}
	
	@PostConstruct
	public void inicio() {
		itens = descontos.descontos();
		funcionarios = fun.funcionarios();
	}
	
	public void salvar() {

		try {
			cadastroDesconto.salvar(item);
			FacesUtil.addInfoMessage("Desconto salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}finally {
			inicio();
			limpar();
		}

	}

	
	public void editar() {
		try {
		
			cadastroDesconto.salvar(itemAux);
			FacesUtil.addInfoMessage("Desconto alterado com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}finally {
			inicio();
			limpar();
		}

	}
	
	
	public void excluir() {
		try {
			descontos.remover(itemAux);
			inicio();
			limpar();
			FacesUtil.addInfoMessage("Desconto excluido com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());

		}finally {
			inicio();
			limpar();
		}

	}
	
	public void troca(DescontoSalario item) {
		this.itemAux = item;
	}
	
	public TipoDespesa[] tiposDespesas() {
		return TipoDespesa.values();
	}
	
	
	public DescontoSalario getItem() {
		return item;
	}

	public void setItem(DescontoSalario item) {
		this.item = item;
	}

	public DescontoSalario getItemAux() {
		return itemAux;
	}

	public void setItemAux(DescontoSalario itemAux) {
		this.itemAux = itemAux;
	}

	public List<DescontoSalario> getItens() {
		return itens;
	}

	public void setItens(List<DescontoSalario> itens) {
		this.itens = itens;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}


}
