package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.cepalab.sistemaVendas.financeiro.dominio.Cheque;
import com.cepalab.sistemaVendas.financeiro.dominio.Conta;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusCheque;
import com.cepalab.sistemaVendas.financeiro.dominio.Transferencia;
import com.cepalab.sistemaVendas.repository.Cheques;
import com.cepalab.sistemaVendas.repository.Contas;
import com.cepalab.sistemaVendas.repository.Transferencias;
import com.cepalab.sistemaVendas.repository.filter.TransferenciaFilter;


@Named
@ViewScoped
public class PesquisaTransferenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Cheque> listaCheques;
	private List<Transferencia> listaTransferencias;
	private  TransferenciaFilter filtro;
	
	@Inject
	private Transferencias transferencias;
	
	@Inject
	private Cheques cheques;
	
	@Inject
	private Contas contas;
	
	@PostConstruct
	public void inicio() {
		limpar();
		listaCheques = cheques.chequesEmCaixa();
	}

	public void limpar() {
		listaTransferencias = new ArrayList<>();
		filtro = new TransferenciaFilter();
	}

	public void pesquisa() {
		listaTransferencias = transferencias.filtradas(filtro);
	}
	
	public void fechaDialogo() {

		PrimeFaces.current().dialog().closeDynamic(null);	
	}
	
	public void abrirDialogo(Transferencia transf) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 500);
		opcoes.put("contentWidth", 1200);

		List<String> lista = new ArrayList<>();
		lista.add("" + transf.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("transf", lista);

		PrimeFaces.current().dialog().openDynamic("/dialogos/transferenciaDialogo", opcoes, params);	
	}
	
	public StatusCheque[] status() {
		return StatusCheque.values();
	}
	
	public List<Conta> contas(){
		return contas.contas();
	}
	
	public List<Cheque> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<Cheque> listaCheques) {
		this.listaCheques = listaCheques;
	}

	public List<Transferencia> getListaTransferencias() {
		return listaTransferencias;
	}

	public void setListaTransferencias(List<Transferencia> listaTransferencias) {
		this.listaTransferencias = listaTransferencias;
	}

	public TransferenciaFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(TransferenciaFilter filtro) {
		this.filtro = filtro;
	}
}
