package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.repository.TiposVendedores;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaTipoVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TipoVendedor> tiposVendedores;
	private TipoVendedor tipoVendedor;
	
	
	@Inject
	private TiposVendedores tipos;
	
	
	@PostConstruct
	public void inicio() {
		tiposVendedores = tipos.tipos();
		tipoVendedor = new TipoVendedor();
	}

	public void troca(TipoVendedor tipo) {
		tipoVendedor = tipo; 
	}
	
	public void excluir() {
		try {
			tipos.remover(tipoVendedor);
			inicio();
			FacesUtil.addInfoMessage("Tipo de Vendedor Excluído com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}
	
	
	public List<TipoVendedor> getTiposVendedores() {
		return tiposVendedores;
	}

	public void setTiposVendedores(List<TipoVendedor> tiposVendedores) {
		this.tiposVendedores = tiposVendedores;
	}


	public TipoVendedor getTipoVendedor() {
		return tipoVendedor;
	}


	public void setTipoVendedor(TipoVendedor tipoVendedor) {
		this.tipoVendedor = tipoVendedor;
	}
	
	

/*
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

*/
	
	

}
