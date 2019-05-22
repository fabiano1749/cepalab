package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.financeiro.dominio.Banco;
import com.cepalab.sistemaVendas.financeiro.dominio.Cheque;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusCheque;
import com.cepalab.sistemaVendas.repository.Bancos;
import com.cepalab.sistemaVendas.repository.Cheques;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.filter.ChequeFilter;
import com.cepalab.sistemaVendas.service.CadastroChequeService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaChequeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Cheque> listaCheques;
	private ChequeFilter filtro;
	private Cheque chequeEdicao;
	private Cheque chequeExclusao;
	private boolean chequeDeVendedor;
	
	

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Bancos bancos;
	
	@Inject
	private Clientes clientes;
	
	@Inject
	private Cheques cheques;
	
	@Inject
	private CadastroChequeService cadastro;
	
	@PostConstruct
	public void inicio() {
		limpar();
	}

	public void limpar() {
		listaCheques = new ArrayList<>();
		filtro = new ChequeFilter();
		chequeEdicao = new Cheque();
	}

	public void pesquisa() {

		listaCheques = cheques.filtrados(filtro);
	}

	public void editar() {

		try {
			if(!chequeDeVendedor) {
				chequeEdicao.setFuncionario(null);
				chequeEdicao.setCliente(null);
			}else {
				chequeEdicao.setOrigem(null);
			}
			
			cadastro.salvar(chequeEdicao);
			FacesUtil.addInfoMessage("Cheque editado com sucesso!");
			chequeEdicao = new Cheque();
			pesquisa();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	public void excluir() {
	
		 if(chequeExclusao.getStatus() != StatusCheque.SEM_VINCULO) {
			FacesUtil.addErrorMessage("O cheque não pode ser excluído porque foi utilizado em alguma transação!");
		}
		else {
			cheques.remover(chequeExclusao);
			FacesUtil.addInfoMessage("Cheque excluído com sucesso!");
			pesquisa();
		}
		
	}
	
	
	public List<Banco> listaBancos(){
		return bancos.bancos();
	}
	
	public List<Funcionario> vendedores(){
		return funcionarios.vendedor();
	}
	
	public List<Cliente> clientes(){
		if(this.chequeEdicao.getFuncionario() != null) {
			return clientes.porFuncionario(this.chequeEdicao.getFuncionario());
		}
		return null;
	}
	
	
	public StatusCheque[] status() {
		return StatusCheque.values();
	}
	
	public List<Cheque> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<Cheque> listaCheques) {
		this.listaCheques = listaCheques;
	}

	public ChequeFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(ChequeFilter filtro) {
		this.filtro = filtro;
	}

	public Cheque getChequeEdicao() {
		return chequeEdicao;
	}

	public void setChequeEdicao(Cheque chequeEdicao) {
		this.chequeEdicao = chequeEdicao;
		if(chequeEdicao.getFuncionario() != null) {
			chequeDeVendedor = true;
		}
		else {
			chequeDeVendedor = false;
		}
	}

	public boolean isChequeDeVendedor() {
		return chequeDeVendedor;
	}

	public void setChequeDeVendedor(boolean chequeDeVendedor) {
		this.chequeDeVendedor = chequeDeVendedor;
	}

	public Cheque getChequeExclusao() {
		return chequeExclusao;
	}

	public void setChequeExclusao(Cheque chequeExclusao) {
		this.chequeExclusao = chequeExclusao;
	}
}
