package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;
import com.cepalab.sistemaVendas.repository.RecebimentosInadiplentes;

@ViewScoped
@Named
public class FechamentoRecebimentoInadimplenteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RecebimentoInadiplente> listaRecebimentos = new ArrayList<>();
	private BigDecimal total = BigDecimal.ZERO;

	@Inject
	private FechamentoBean fechamentoResumo;

	@Inject
	private RecebimentosInadiplentes recebimentos;

	public void criaListaRecebimentos() {
		total = BigDecimal.ZERO;	
		Funcionario fun = fechamentoResumo.getItem().getFuncionario();
		listaRecebimentos = recebimentos.porFuncionario(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());
		
		if(listaRecebimentos != null) {
			for(RecebimentoInadiplente r : listaRecebimentos) {
				total = total.add(r.getValor());
			}
		}
		
	}

		
	public List<RecebimentoInadiplente> getListaRecebimentos() {
		return listaRecebimentos;
	}

	public void setListaRecebimentos(List<RecebimentoInadiplente> listaRecebimentos) {
		this.listaRecebimentos = listaRecebimentos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	
}
