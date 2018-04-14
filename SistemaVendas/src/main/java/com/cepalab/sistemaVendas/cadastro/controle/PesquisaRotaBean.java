package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Rotas;

@Named
@ViewScoped
public class PesquisaRotaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Funcionario> funcionarios;
	private List<Rota> listaRotas;
	private Funcionario funcionario;

	@Inject
	private Rotas rotas;

	@Inject
	private Funcionarios fun;

	@PostConstruct
	public void inicio() {
		limpar();
		funcionarios = fun.funcionarios();
	}

	public void limpar() {
		funcionarios = new ArrayList<>();
		listaRotas = new ArrayList<>();
		funcionario = new Funcionario();
	}

	public void pesquisa() {

		listaRotas = rotas.rotasPorFuncionario(funcionario);
	}

	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(Rota rota) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);

		List<String> lista = new ArrayList<>();
		lista.add("" + rota.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("rota", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/rotaDialogo", opcoes, params);
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Rota> getListaRotas() {
		return listaRotas;
	}

	public void setListaRotas(List<Rota> listaRotas) {
		this.listaRotas = listaRotas;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
