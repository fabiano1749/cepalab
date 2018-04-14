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

import com.cepalab.sistemaVendas.cadastro.dominio.Transportadora;
import com.cepalab.sistemaVendas.repository.Transportadoras;

@Named
@ViewScoped
public class PesquisaTransportadoraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Transportadora> lista;

	@Inject
	private Transportadoras transportadoras;

	

	@PostConstruct
	public void inicio() {
		lista = new ArrayList<>();
		lista = transportadoras.transportadoras();
	}

	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(Transportadora transportadora) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);

		List<String> lista = new ArrayList<>();
		lista.add("" + transportadora.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("transportadora", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/transportadoraDialogo", opcoes, params);
	}

	public List<Transportadora> getLista() {
		return lista;
	}

	public void setLista(List<Transportadora> lista) {
		this.lista = lista;
	}
}
