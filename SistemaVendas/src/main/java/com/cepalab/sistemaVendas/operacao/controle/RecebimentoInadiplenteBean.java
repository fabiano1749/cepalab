package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Rotas;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.CadastroRecebimentoInadiplente;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RecebimentoInadiplenteBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private RecebimentoInadiplente item;
	private List<RecebimentoInadiplente> itens;
	//private List<Rota> listaRotas = new ArrayList<>();
	private List<Cliente> listaClientes = new ArrayList<>();
	//private Rota rota = new Rota();
	
	
	@Inject
	private Seguranca seg;
	
	@Inject
	private Rotas rotas;
	
	@Inject
	private CadastroRecebimentoInadiplente cadastro;
	
	@Inject
	private Clientes clientes;
	
	public void limpar() {
		item = new RecebimentoInadiplente();
	}
	
	@PostConstruct
	public void inicio() {
		limpar();
		criaListaClientes();
		//listaRotas = rotas.rotasPorFuncionario(seg.UsuarioLogado());
	}
	
	public void criaListaClientes(){
		listaClientes = clientes.porFuncionario(seg.UsuarioLogado());
	}
	
	public void salvar() {
		try {
			item.setFuncionario(seg.UsuarioLogado());
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Recebimento salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public RecebimentoInadiplente getItem() {
		return item;
	}

	public void setItem(RecebimentoInadiplente item) {
		if(item == null) {
			inicio();
		}else {
			//rota = item.getCliente().getRota();
			//listaRotas = rotas.rotasPorFuncionario(item.getFuncionario());
			criaListaClientes();
			this.item = item;
		}
	}

	public List<RecebimentoInadiplente> getItens() {
		return itens;
	}

	public void setItens(List<RecebimentoInadiplente> itens) {
		this.itens = itens;
	}

	
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
}
