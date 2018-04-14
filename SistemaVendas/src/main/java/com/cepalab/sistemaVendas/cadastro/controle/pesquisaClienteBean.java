package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Rotas;
import com.cepalab.sistemaVendas.repository.filter.ClienteFilter;

@Named
@ViewScoped
public class pesquisaClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;

	@Inject
	private Rotas rotas;

	@Inject
	private Funcionarios funcionarios;

	private ClienteFilter filtro;
	private List<Cliente> clientesFiltrados;
	private List<Rota> listaRotas;
	private int total;

	public pesquisaClienteBean() {
		filtro = new ClienteFilter();
		filtro.setRota(new Rota());
		filtro.setFuncionario(new Funcionario());
		listaRotas = new ArrayList<>();
	}

	public void rota() {
		if (filtro.getFuncionario() != null) {
			listaRotas = rotas.rotasPorFuncionario(filtro.getFuncionario());
		}
	}

	public List<Funcionario> funcionarios() {
		return funcionarios.funcionarios();
	}

	public void pesquisar() {
		clientesFiltrados = clientes.filtrados(filtro);
		total = clientesFiltrados.size();
	}

	public void fechaDialogo() {

		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(Cliente cliente) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 450);
		opcoes.put("contentWidth", 900);

		List<String> lista = new ArrayList<>();
		lista.add("" + cliente.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("cliente", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/clienteDialogo", opcoes, params);
	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
		this.clientesFiltrados = clientesFiltrados;
	}

	public ClienteFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(ClienteFilter filtro) {
		this.filtro = filtro;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Rota> getListaRotas() {
		return listaRotas;
	}

	public void setListaRotas(List<Rota> listaRotas) {
		this.listaRotas = listaRotas;
	}

}
