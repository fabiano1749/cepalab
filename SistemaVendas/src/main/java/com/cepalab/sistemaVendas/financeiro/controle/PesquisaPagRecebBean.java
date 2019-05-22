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

import com.cepalab.sistemaVendas.financeiro.dominio.Conta;
import com.cepalab.sistemaVendas.financeiro.dominio.PagamentoRecebimento;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoTransacao;
import com.cepalab.sistemaVendas.repository.Contas;
import com.cepalab.sistemaVendas.repository.PagamentosRecebimentos;
import com.cepalab.sistemaVendas.repository.filter.PagRecebFilter;


@Named
@ViewScoped
public class PesquisaPagRecebBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<PagamentoRecebimento> listaPagReceb;
	private PagRecebFilter filtro;

	@Inject
	private PagamentosRecebimentos pagamentosRecebimentos;

	@Inject
	private Contas contas;
	
	@PostConstruct
	public void inicio() {
		limpar();
	}

	public void limpar() {
		listaPagReceb = new ArrayList<>();
		filtro = new PagRecebFilter();
	}

	public void pesquisa() {

		listaPagReceb = pagamentosRecebimentos.filtrados(filtro);
	}

	public TipoTransacao[] tipos() {
		TipoTransacao[] t = new TipoTransacao[2];
		t[0] = TipoTransacao.PAGAMENTO;
		t[1] = TipoTransacao.RECEBIMENTO;
		return t;
	}
	
	public void fechaDialogo() {

		PrimeFaces.current().dialog().closeDynamic(null);	
	}
	
	public void abrirDialogo(PagamentoRecebimento pagReceb) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 500);
		opcoes.put("contentWidth", 1200);

		List<String> lista = new ArrayList<>();
		lista.add("" + pagReceb.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("pagReceb", lista);

		PrimeFaces.current().dialog().openDynamic("/dialogos/pagRecebDialogo", opcoes, params);	
		
	}
	
	public List<Conta> contas(){
		return contas.contas();
	}
	
	public List<PagamentoRecebimento> getListaPagReceb() {
		return listaPagReceb;
	}

	public void setListaPagReceb(List<PagamentoRecebimento> listaPagReceb) {
		this.listaPagReceb = listaPagReceb;
	}

	public PagRecebFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(PagRecebFilter filtro) {
		this.filtro = filtro;
	}
	
	
}
