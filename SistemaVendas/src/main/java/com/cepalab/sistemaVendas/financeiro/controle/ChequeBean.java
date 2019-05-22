package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.util.Date;
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
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.service.CadastroChequeService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ChequeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cheque item;
	private List<Banco> listaBancos;
	private boolean chequeDeVendedor = true;
	
	@Inject
	private CadastroChequeService cadastro;

	@Inject
	private Bancos bancos;

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Clientes clientes;

	@PostConstruct
	public void inicio() {
		this.item = new Cheque();
		listaBancos = bancos.bancos();

	}

	public void limpa() {
		this.item = new Cheque();
	}

	public void salvar() {

		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Cheque salvo com sucesso!");
			limpa();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void prepararSalvar() {
		if (!this.chequeDeVendedor) {
			this.item.setCliente(null);
			this.item.setFuncionario(null);
		} else {
			this.item.setOrigem(null);
		}
		if (item.getId() == null) {
			item.setStatus(StatusCheque.SEM_VINCULO);
		}

		item.setDataCadastro(new Date());
		salvar();
	}

	public List<Funcionario> funcionarios() {
		return funcionarios.vendedorAtivo();
	}

	public List<Cliente> clientes() {
		if (this.item.getFuncionario() != null) {
			return clientes.porFuncionario(this.item.getFuncionario());
		}
		return null;
	}

	public Cheque getItem() {
		return item;
	}

	public void setItem(Cheque item) {
		this.item = item;
	}

	public List<Banco> getListaBancos() {
		return listaBancos;
	}

	public void setListaBancos(List<Banco> listaBancos) {
		this.listaBancos = listaBancos;
	}

	public boolean isChequeDeVendedor() {
		return chequeDeVendedor;
	}

	public void setChequeDeVendedor(boolean chequeDeVendedor) {
		this.chequeDeVendedor = chequeDeVendedor;
	}
}
