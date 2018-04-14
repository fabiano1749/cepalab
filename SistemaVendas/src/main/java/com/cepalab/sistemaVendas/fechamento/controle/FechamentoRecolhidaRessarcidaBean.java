package com.cepalab.sistemaVendas.fechamento.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.RecolhidaRessarcida;
import com.cepalab.sistemaVendas.repository.ComissoesRecolhidasRessarcidas;

@ViewScoped
@Named
public class FechamentoRecolhidaRessarcidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ComissaoRecolhidaRessarcida> listaRecolhidaRessarcida = new ArrayList<>();
	private BigDecimal recolhida = BigDecimal.ZERO;
	private BigDecimal ressarcida = BigDecimal.ZERO;

	@Inject
	private FechamentoBean fechamentoResumo;

	@Inject
	private ComissoesRecolhidasRessarcidas recolhidasRessarcidas;

	public void criaListaRecolhidaRessarcida() {
		Funcionario fun = fechamentoResumo.getItem().getFuncionario();
		listaRecolhidaRessarcida = recolhidasRessarcidas.porFuncionario(fun, fechamentoResumo.getItem().getInicio(),
				fechamentoResumo.getItem().getFim());

		recolhida = BigDecimal.ZERO;
		ressarcida = BigDecimal.ZERO;
		if (listaRecolhidaRessarcida != null) {
			for (ComissaoRecolhidaRessarcida c : listaRecolhidaRessarcida) {
				if (c.getTipo().equals(RecolhidaRessarcida.RECOLHIDA)) {
					recolhida = recolhida.add(c.getValor());
				} else {
					ressarcida = ressarcida.add(c.getValor());
				}
			}
		}

	}


	public List<ComissaoRecolhidaRessarcida> getListaRecolhidaRessarcida() {
		return listaRecolhidaRessarcida;
	}

	public void setListaRecolhidaRessarcida(List<ComissaoRecolhidaRessarcida> listaRecolhidaRessarcida) {
		this.listaRecolhidaRessarcida = listaRecolhidaRessarcida;
	}

	public BigDecimal getRecolhida() {
		return recolhida;
	}

	public void setRecolhida(BigDecimal recolhida) {
		this.recolhida = recolhida;
	}

	public BigDecimal getRessarcida() {
		return ressarcida;
	}

	public void setRessarcida(BigDecimal ressarcida) {
		this.ressarcida = ressarcida;
	}

}
