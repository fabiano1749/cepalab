package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.cepalab.sistemaVendas.cadastro.dominio.IntervaloVendaConsignacaoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;

@Named
@ViewScoped
public class PoliticaVendaConsignacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private PoliticaVendaConsignacaoTipoVendedorProduto item = new PoliticaVendaConsignacaoTipoVendedorProduto();
	private IntervaloVendaConsignacaoProduto intervalo = new IntervaloVendaConsignacaoProduto();

	
	public void limpar() {
		item = new PoliticaVendaConsignacaoTipoVendedorProduto();
		item.setListaIntervalos(new ArrayList<>());
	}

	public void adicionar() {
		System.out.println(intervalo.getInicio());
		intervalo.setPoliticaTipoVendedorProduto(item);
		item.getListaIntervalos().add(intervalo);
		intervalo = new IntervaloVendaConsignacaoProduto();
	}

	public void confirmarCriacaoDePolitica() {
		RequestContext.getCurrentInstance().closeDialog(item);
	}
	
	public void abrirDialogoPolitica(PoliticaVendaConsignacaoTipoVendedorProduto politica) {
			
		System.out.println("Chegou em abrir Dialogo");

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("height", 450);
		opcoes.put("width", 1000);
		opcoes.put("contentHeight", "100%");
		opcoes.put("contentWidth", "100%");

		
		
		List<String> lista = new ArrayList<>();
		lista.add("" + "BUCETA");

		Map<String, List<String>> params = new HashMap<>();
		params.put("politica", lista);
		RequestContext.getCurrentInstance().openDialog("/dialogos/politicaVendaConsignacaoDialogo", opcoes, params);

	}

	
	
	public PoliticaVendaConsignacaoTipoVendedorProduto getItem() {
		return item;
	}

	public void setItem(PoliticaVendaConsignacaoTipoVendedorProduto item) {
	
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("politica");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			//Long id = Long.valueOf(paramResposta);
			System.out.println(paramResposta);
			//item = rotas.porId2(id);
		}
		
		this.item = new PoliticaVendaConsignacaoTipoVendedorProduto();
		this.item.setListaIntervalos(new ArrayList<>());
	}

	public IntervaloVendaConsignacaoProduto getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(IntervaloVendaConsignacaoProduto intervalo) {
		this.intervalo = intervalo;
	}
	
}
