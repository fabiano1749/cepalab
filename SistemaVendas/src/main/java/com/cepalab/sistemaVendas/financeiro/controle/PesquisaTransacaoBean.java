package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.cepalab.sistemaVendas.financeiro.dominio.Parcela;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusParcela;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoTransacao;
import com.cepalab.sistemaVendas.repository.Parcelas;
import com.cepalab.sistemaVendas.repository.filter.ParcelaFilter;
import com.cepalab.sistemaVendas.service.CadastroParcelaService;

@Named
@ViewScoped
public class PesquisaTransacaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private ParcelaFilter filtro;
	private List<Parcela> listaParcelas;

	@Inject
	private Parcelas parcelas;

	@Inject
	private CadastroParcelaService cadastroParcela;

	@PostConstruct
	public void inicio() {
		filtro = new ParcelaFilter();
		listaParcelas = new ArrayList<>();
	}

	public void pesquisar() {
		listaParcelas = parcelas.filtradas(filtro);
	}

	public BigDecimal somaParcelas() {
		BigDecimal soma = BigDecimal.ZERO;
		if (listaParcelas != null && listaParcelas.size() > 0) {
			for (Parcela p : listaParcelas) {
				soma = soma.add(p.getValor());
			}
		}
		return soma;
	}

	public void editaStatusParcela(Parcela parcela) {
		// System.out.println(parcela.getId() + " "+ parcela.getValor()+"
		// "+parcela.getStatus().getDescricao() );
		if (parcela != null) {
			cadastroParcela.salvar(parcela);
		}
	}

	public TipoTransacao[] tipoTransacao() {
		return TipoTransacao.values();
	}

	public StatusParcela[] status() {
		return StatusParcela.values();
	}

	public ParcelaFilter getFiltro() {
		return filtro;
	}

	public void setFiltro(ParcelaFilter filtro) {
		this.filtro = filtro;
	}

	public List<Parcela> getListaParcelas() {
		return listaParcelas;
	}

	public void setListaParcelas(List<Parcela> listaParcelas) {
		this.listaParcelas = listaParcelas;
	}
}
