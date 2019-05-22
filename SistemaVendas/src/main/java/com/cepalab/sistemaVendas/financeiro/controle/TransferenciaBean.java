package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.financeiro.dominio.Cheque;
import com.cepalab.sistemaVendas.financeiro.dominio.Conta;
import com.cepalab.sistemaVendas.financeiro.dominio.Parcela;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusCheque;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusParcela;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoConta;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoTransacao;
import com.cepalab.sistemaVendas.financeiro.dominio.Transferencia;
import com.cepalab.sistemaVendas.repository.Cheques;
import com.cepalab.sistemaVendas.repository.Contas;
import com.cepalab.sistemaVendas.repository.Transferencias;
import com.cepalab.sistemaVendas.service.CadastroChequeService;
import com.cepalab.sistemaVendas.service.CadastroTransferenciaService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TransferenciaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Transferencia item;
	private List<Cheque> listaChequesEmCaixa;

	@Inject
	private Contas contas;

	@Inject
	private CadastroTransferenciaService cadastro;

	@Inject
	private Cheques cheques;

	@Inject
	private CadastroChequeService cadastroCheque;
	
	@Inject
	private Transferencias transferencias;
	
	@PostConstruct
	public void inicio() {
		this.item = new Transferencia();
		item.setNumParcelas(1);
		item.setListaCheques(new ArrayList<>());
		iniciaParcelas();
	}

	public void salvar() {
		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Transferência realizada com sucesso!");
			inicio();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void prepararSalvar() {
		if (item.getConta().equals(item.getDestino())) {
			FacesUtil.addErrorMessage("As contas Origem e destino devem ser diferentes!");
		} else {
			this.item.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
			this.item.setValorCheque(this.item.somaDosCheques());
			this.item.setValorDinheiro(this.item.getValor().subtract(this.item.getValorCheque()));
			salvar();
		}
	}

	public void atualizaChequeEmCaixa() {
		if(this.item.getConta().getTipo() == TipoConta.CAIXA_CEPA) {
			this.listaChequesEmCaixa = cheques.chequesEmCaixa();
		}
		else {
			this.listaChequesEmCaixa = new ArrayList<>();
		}
		if (this.item.getId() == null) {
			this.item.setListaCheques(new ArrayList<>());
		}
		
	}
	
	public void atualisaDataParcela() {
		item.getListaParcelas().get(0).setData(item.getData());
	}

	public void atualisaValorParcela() {
		item.getListaParcelas().get(0).setValor(item.getValor());
	}

	public void iniciaParcelas() {
		this.item.setListaParcelas(new ArrayList<>());

		Parcela parcela = new Parcela();
		parcela.setNumero(1);
		parcela.setTransacao(this.item);
		this.item.getListaParcelas().add(parcela);
	}

	public List<Conta> listaContas() {
		return contas.contas();
	}

	public StatusParcela[] statusParcela() {
		return StatusParcela.values();
	}

	public void adicionarCheque(Cheque cheque) {
		cheque.setStatus(StatusCheque.DEPOSITADO_EM_CONTA);
		this.item.getListaCheques().add(cheque);
		listaChequesEmCaixa.remove(cheque);
	}

	public void removerCheque(Cheque cheque) {
		cheque.setStatus(StatusCheque.NO_CAIXA);
		this.item.getListaCheques().remove(cheque);
		
		cheque = cadastroCheque.salvar(cheque);
		listaChequesEmCaixa.add(cheque);
	}


	public BigDecimal valorDinheiro() {
		if (this.item.getValor() == null) {
			return BigDecimal.ZERO;
		} 
		if(this.item.getListaCheques().isEmpty()) {
			return item.getValor();
		}
		else {
			return this.item.getValor().subtract(this.item.somaDosCheques());
		}
	}
	
	
	public Transferencia getItem() {
		return item;
	}

	public void setItem(Transferencia item) {
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("transf");
		
		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = transferencias.porId2(id);
		}
		
		
		if(item == null) {
			inicio();
		}
		else {
			this.item = item;
			atualizaChequeEmCaixa();
		}
	}

	public List<Cheque> getListaChequesEmCaixa() {
		return listaChequesEmCaixa;
	}

	public void setListaChequesEmCaixa(List<Cheque> listaChequesEmCaixa) {
		this.listaChequesEmCaixa = listaChequesEmCaixa;
	}
}
