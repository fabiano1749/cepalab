package com.cepalab.sistemaVendas.consulta.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.EstadosBrasileiros;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.consulta.dominio.ConsignadosConsulta;
import com.cepalab.sistemaVendas.consulta.dominio.ProdutoQuantidadeConsignados;
import com.cepalab.sistemaVendas.consulta.dominio.ProdutosConsignados;
import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Consignados;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.repository.Rotas;
import com.cepalab.sistemaVendas.repository.filter.ProdutosConsignadosFilter;

@ViewScoped
@Named
public class ConsignadosPorProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Produto> listaProdutos = new ArrayList<>();
	private List<Cliente> listaClientes = new ArrayList<>();
	private List<Rota> listaRotas = new ArrayList<>();
	private ProdutosConsignadosFilter filtro = new ProdutosConsignadosFilter();
	private List<Consignacao> listaConsignacoes = new ArrayList<>();
	private List<ConsignadosConsulta> listaConsulta;
	private List<ProdutoQuantidadeConsignados> listaTotalConsignados;
	private boolean sintetico = false;

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Produtos produtos;

	@Inject
	private Clientes clientes;

	@Inject
	private Rotas rotas;

	@Inject
	private Consignados consignados;

	@PostConstruct
	public void inicio() {
		listaFuncionarios = funcionarios.vendedorAtivo();
		listaProdutos = produtos.podeConsignar();
		listaClientes = clientes.clientes();
	}

	public void alimentaRotas() {
		listaRotas = rotas.rotasPorFuncionario(filtro.getFuncionario());
	}

	public void pesquisa() {
		criaListaTotalConsignado();
		ProdutosConsignados p = new ProdutosConsignados();
		p.setListaConsignados(consignados.filtrados(filtro));
		listaConsulta = p.listaSemRepeticao(listaTotalConsignados);
	}

	public EstadosBrasileiros[] estados() {
		return EstadosBrasileiros.values();
	}

	public void clientesPorFuncionario() {
		listaClientes = clientes.porFuncionario(filtro.getFuncionario());
	}

	public void clienteSelecionado() {
		if (filtro.getFuncionario() != null) {
			alimentaRotas();
			clientesPorFuncionario();
		}
	}

	public int consignadosClienteProduto(Cliente cliente, Produto produto) {

		ConsignadosConsulta c = new ConsignadosConsulta();
		c.setCliente(cliente);
		int index = listaConsulta.indexOf(c);
		return listaConsulta.get(index).consignados(produto);

	}

	private void criaListaTotalConsignado() {
		listaTotalConsignados = new ArrayList<>();
		for (Produto p : listaProdutos) {
			ProdutoQuantidadeConsignados pc = new ProdutoQuantidadeConsignados();
			pc.setProduto(p);
			pc.setQuantidade(0);
			listaTotalConsignados.add(pc);
		}
	}

	public int totalConsignado(Produto p) {
		for (ProdutoQuantidadeConsignados pc : listaTotalConsignados) {
			if (pc.getProduto().equals(p)) {
				return pc.getQuantidade();
			}
		}
		return 0;
	}

	public BigDecimal totalPorProduto() {
		BigDecimal aux = BigDecimal.ZERO;
		if (listaTotalConsignados != null) {
			for (ProdutoQuantidadeConsignados pq : listaTotalConsignados) {
				aux = aux.add(pq.custoTotal());
			}
		}
		return aux;
	}

	public int totalProduto() {
		int aux = 0;
		if (listaTotalConsignados != null) {
			for (ProdutoQuantidadeConsignados pq : listaTotalConsignados) {
				aux = aux + pq.getQuantidade();
			}
		}
		return aux;
	}
	
	public BigDecimal totalReceita() {
		BigDecimal aux = BigDecimal.ZERO;
		if (listaTotalConsignados != null) {
			for (ProdutoQuantidadeConsignados pq : listaTotalConsignados) {
				aux = aux.add(pq.getSomaValoresDeVenda());
			}
		}
		return aux;
	}
	

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ProdutosConsignadosFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(ProdutosConsignadosFilter filtro) {
		this.filtro = filtro;
	}

	public List<Rota> getListaRotas() {
		return listaRotas;
	}

	public void setListaRotas(List<Rota> listaRotas) {
		this.listaRotas = listaRotas;
	}

	public List<Consignacao> getListaConsignacoes() {
		return listaConsignacoes;
	}

	public void setListaConsignacoes(List<Consignacao> listaConsignacoes) {
		this.listaConsignacoes = listaConsignacoes;
	}

	public List<ConsignadosConsulta> getListaConsulta() {
		return listaConsulta;
	}

	public void setListaConsulta(List<ConsignadosConsulta> listaConsulta) {
		this.listaConsulta = listaConsulta;
	}

	public List<ProdutoQuantidadeConsignados> getListaTotalConsignados() {
		return listaTotalConsignados;
	}

	public void setListaTotalConsignados(List<ProdutoQuantidadeConsignados> listaTotalConsignados) {
		this.listaTotalConsignados = listaTotalConsignados;
	}

	public boolean isSintetico() {
		return sintetico;
	}

	public void setSintetico(boolean sintetico) {
		this.sintetico = sintetico;
	}

}
