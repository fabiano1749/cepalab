package com.cepalab.sistemaVendas.Expedicao.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Transportadora;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.Transportadoras;
import com.cepalab.sistemaVendas.repository.filter.EnvioTransportadoraFilter;
import com.cepalab.sistemaVendas.service.CadastroOperacao;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EnvioTransportadoraBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Operacao> listaOperacoes = new ArrayList<>(); //Lista de operações que possuem envio de transportadora
	private List<Transportadora> listaTransportadoras = new ArrayList<>();
	private EnvioTransportadoraFilter filtro;
	
	
	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Operacoes operacoes;

	@Inject
	private Transportadoras transportadoras;

	@Inject
	private CadastroOperacao cadastroOperacao;

	@PostConstruct
	public void inicio() {
		listaFuncionarios = funcionarios.vendedorAtivo();
		listaTransportadoras = transportadoras.transportadoras();
		filtro = new EnvioTransportadoraFilter();
	}

	public void limpar() {
		listaOperacoes = new ArrayList<>();
		filtro = new EnvioTransportadoraFilter();
	}

	
	public void pesquisa() {
		listaOperacoes = new ArrayList<>();
		listaOperacoes = operacoes.operacaoEnvioTransportadora(filtro);
		if (!filtro.isTodos()) {
			if(filtro.isEnviados()) {
				listaEnviados();
			}else {
				listaNaoEnviados();
			}
		}
	}
	
	
	public void listaEnviados() {
		List<Operacao> lista = new ArrayList<>();
		for (Operacao operacao : listaOperacoes) {
			if (operacao.getTransportadora() != null) {
				lista.add(operacao);
			}
		}
		listaOperacoes = lista;
	}

	public void listaNaoEnviados() {
		List<Operacao> lista = new ArrayList<>();
		for (Operacao operacao : listaOperacoes) {
			if (operacao.getTransportadora() == null) {
				lista.add(operacao);
			}
		}
		listaOperacoes = lista;
	}

	 
	public void confirmar(Operacao o) {
		if (o.getDataEnvio() != null && o.getTransportadora() != null
				&& o.getValorFrete().compareTo(BigDecimal.ZERO) > 0) {
			
			cadastroOperacao.salvar(o);
			FacesUtil.addInfoMessage("Confirmado !");
		} else {
			FacesUtil.addErrorMessage("Dados de envio incompletos");
		}

	}

	
	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public EnvioTransportadoraFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(EnvioTransportadoraFilter filtro) {
		this.filtro = filtro;
	}

	public List<Transportadora> getListaTransportadoras() {
		return listaTransportadoras;
	}

	public void setListaTransportadoras(List<Transportadora> listaTransportadoras) {
		this.listaTransportadoras = listaTransportadoras;
	}

	public List<Operacao> getListaOperacoes() {
		return listaOperacoes;
	}

	public void setListaOperacoes(List<Operacao> listaOperacoes) {
		this.listaOperacoes = listaOperacoes;
	}
}
