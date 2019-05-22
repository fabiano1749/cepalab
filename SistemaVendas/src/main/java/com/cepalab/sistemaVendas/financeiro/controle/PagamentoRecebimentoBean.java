package com.cepalab.sistemaVendas.financeiro.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.financeiro.dominio.Cheque;
import com.cepalab.sistemaVendas.financeiro.dominio.Conta;
import com.cepalab.sistemaVendas.financeiro.dominio.Parcela;
import com.cepalab.sistemaVendas.financeiro.dominio.PeriodoPagamento;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusCheque;
import com.cepalab.sistemaVendas.financeiro.dominio.StatusParcela;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoConta;
import com.cepalab.sistemaVendas.financeiro.dominio.TipoTransacao;
import com.cepalab.sistemaVendas.financeiro.dominio.PagamentoRecebimento;
import com.cepalab.sistemaVendas.repository.Cheques;
import com.cepalab.sistemaVendas.repository.Contas;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.PagamentosRecebimentos;
import com.cepalab.sistemaVendas.service.CadastroChequeService;
import com.cepalab.sistemaVendas.service.CadastroPagamentoRecebimentoService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentoRecebimentoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private PagamentoRecebimento item;
	private List<Cheque> listaCheques;
	private boolean favorecidoOrigemVendedor = true;

	@Inject
	private Contas contas;

	@Inject
	private CadastroPagamentoRecebimentoService cadastro;

	@Inject
	private PagamentosRecebimentos pagamentosRecebimentos;

	@Inject
	private Cheques cheques;

	@Inject
	private CadastroChequeService cadastroCheque;
	
	@Inject
	private Funcionarios funcionarios;

	@PostConstruct
	public void inicio() {
		this.item = new PagamentoRecebimento();
		this.item.setRealizado(true);
		this.item.setPeriodoPagamento(PeriodoPagamento.MENSAL);
		this.item.setTipoTransacao(TipoTransacao.PAGAMENTO);
	}

	public void salvar() {
		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Transação salva com sucesso!");
			inicio();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public void prepararSalvar() {
		if (favorecidoOrigemVendedor) {
			this.item.setFavorecidoOrigemOutros(null);
		} else {
			this.item.setFavorecidoOrigemVendedor(null);
		}

		if (this.item.getPeriodoPagamento() != PeriodoPagamento.OUTROS) {
			this.item.setPeriodoPagamentoNumerico(0);
		}

		if (!this.item.getParcelado()) {
			this.item.setNumParcelas(1);
			this.item.setPrimeiroVencimento(this.item.getData());
			this.item.setPeriodoPagamento(PeriodoPagamento.OUTROS);
			this.item.setPeriodoPagamentoNumerico(0);
		}

		if (valorParcelasIgualValorTransacao()) {
			this.item.setValorCheque(this.item.somaDosCheques());
			this.item.setValorDinheiro(this.item.getValor().subtract(this.item.getValorCheque()));
			salvar();
		} else {
			FacesUtil.addErrorMessage("A soma das parcelas não confere com o valor da transação!");
		}
	}

	public BigDecimal valorDinheiro() {
		if (this.item.getValor() == null) {
			return BigDecimal.ZERO;
		}
		if (this.item.getListaCheques().isEmpty()) {
			return item.getValor();
		} else {
			return this.item.getValor().subtract(this.item.somaDosCheques());
		}
	}

	public void validaValorParcela(Parcela p) {
		BigDecimal somaParcelas = this.item.somaDasParcelas();
		if (this.item.getValor().compareTo(somaParcelas) < 0) {
			FacesUtil.addErrorMessage("A soma das parcelas está excedendo o valor da transação!");
			p.setValor(BigDecimal.ZERO);
			p.setValor(this.item.getValor().subtract(this.item.somaDasParcelas()));
		}
	}

	public boolean valorParcelasIgualValorTransacao() {
		BigDecimal somaParcelas = this.item.somaDasParcelas();
		BigDecimal diferenca = somaParcelas.subtract(this.item.getValor());

		if (diferenca.compareTo(new BigDecimal(1)) <= 0 && diferenca.compareTo(new BigDecimal(-1)) >= 0) {
			return true;
		}

		return false;
	}

	public void favorecidoOrigemNaoVendedor() {
		if (!favorecidoOrigemVendedor) {
			this.item.setFavorecidoOrigemVendedor(null);

		}

		atualizaListaCheques();
	}

	public void atualizaListaCheques() {
		this.listaCheques = new ArrayList<>();

		if (this.item.getTipoTransacao() == TipoTransacao.PAGAMENTO
				&& this.item.getConta().getTipo() == TipoConta.CAIXA_CEPA) {
			listaCheques = cheques.chequesEmCaixa();

		} else if (this.item.getFavorecidoOrigemVendedor() != null
				&& this.item.getConta().getTipo() == TipoConta.CAIXA_CEPA) {
			listaCheques = cheques.chequesSemVinculoFuncionario(this.item.getFavorecidoOrigemVendedor());

		} else if (this.item.getConta().getTipo() == TipoConta.CAIXA_CEPA && !favorecidoOrigemVendedor) {

			listaCheques = cheques.chequesSemVinculoOutros();
		}
		if (this.item.getId() == null) {
			this.item.setListaCheques(new ArrayList<>());
			//iniciaParcelas();
		}
		
	}

	public void validaValor() {
		if (this.item.getValor().compareTo(this.item.somaDosCheques()) < 0) {
			this.item.setValor(this.item.somaDosCheques());
			FacesUtil.addErrorMessage("O valor informado foi reajustado para não ficar inferior à soma dos cheques!");

		}
		iniciaParcelas();
	}

	public void iniciaParcelas() {

		if (!this.item.getParcelado()) {
			this.item.setNumParcelas(1);
			this.item.setPrimeiroVencimento(this.item.getData());
			this.item.setListaParcelas(new ArrayList<>());
			Parcela parcela = new Parcela();
			parcela.setNumero(1);
			parcela.setTransacao(this.item);
			parcela.setValor(this.item.getValor());
			parcela.setData(this.item.getData());
			this.item.getListaParcelas().add(parcela);

		} else {
			if (this.item.getValor() != null && this.item.getNumParcelas() > 0
					&& this.item.getPrimeiroVencimento() != null) {

				this.item.setListaParcelas(new ArrayList<>());

				BigDecimal valorParcela = this.item.getValor().divide(new BigDecimal(this.item.getNumParcelas()), 2,
						BigDecimal.ROUND_UP);

				for (int i = 1; i <= item.getNumParcelas(); i++) {
					Parcela parcela = new Parcela();
					parcela.setNumero(i);
					parcela.setTransacao(this.item);
					parcela.setValor(valorParcela);

					if (this.item.isRealizado()) {
						parcela.setStatus(StatusParcela.REALIZADO);
					} else {
						parcela.setStatus(StatusParcela.PREVISTO);
					}

					Calendar cal = Calendar.getInstance();
					cal.setTime(this.item.getPrimeiroVencimento());

					if (this.item.getPeriodoPagamento() == PeriodoPagamento.MENSAL) {
						cal.add(Calendar.MONTH, i - 1);
					}

					if (this.item.getPeriodoPagamento() == PeriodoPagamento.QUINZENAL) {
						cal.add(Calendar.DAY_OF_MONTH, (i - 1) * 15);
					}

					if (this.item.getPeriodoPagamento() == PeriodoPagamento.SEMANAL) {
						cal.add(Calendar.WEEK_OF_MONTH, i - 1);
					}

					if (this.item.getPeriodoPagamento() == PeriodoPagamento.DIARIO) {
						cal.add(Calendar.DAY_OF_MONTH, i - 1);
					}

					if (this.item.getPeriodoPagamento() == PeriodoPagamento.OUTROS) {
						cal.add(Calendar.DAY_OF_MONTH, (i - 1) * this.item.getPeriodoPagamentoNumerico());
					}

					parcela.setData(cal.getTime());
					this.item.getListaParcelas().add(parcela);
				}
			}
		}
	}

	public void adicionarCheque(Cheque cheque) {

		if (this.item.getValor() == null) {
			this.item.setValor(BigDecimal.ZERO);
		}

		BigDecimal totalCheque = item.somaDosCheques().add(cheque.getValor());

		if (item.getValor().compareTo(totalCheque) >= 0) {

			if (item.getTipoTransacao() == TipoTransacao.PAGAMENTO) {
				cheque.setStatus(StatusCheque.COM_TERCEIRO);
			} else {
				cheque.setStatus(StatusCheque.NO_CAIXA);
			}

			this.item.getListaCheques().add(cheque);
			listaCheques.remove(cheque);
		} else {
			FacesUtil.addErrorMessage("A soma dos cheques está excedendo o valor da transação!");
		}
	}

	public void removerCheque(Cheque cheque) {

		if (item.getTipoTransacao() == TipoTransacao.PAGAMENTO) {
			cheque.setStatus(StatusCheque.NO_CAIXA);
		} else {
			cheque.setStatus(StatusCheque.SEM_VINCULO);
		}

		cheque = cadastroCheque.salvar(cheque);
		this.item.getListaCheques().remove(cheque);
		listaCheques.add(cheque);
	}

	public void confirmarStatusParcelas() {
		if (this.item.getListaParcelas() != null && this.item.getListaParcelas().size() > 0) {
			for (Parcela p : this.item.getListaParcelas()) {
				if (this.item.isRealizado()) {
					p.setStatus(StatusParcela.REALIZADO);
				} else {
					p.setStatus(StatusParcela.PREVISTO);
				}
			}
		}
	}

	public boolean periodoPagamentoIsOutro() {
		return (this.item.getPeriodoPagamento() == PeriodoPagamento.OUTROS);
	}

	public List<Conta> listaContas() {
		return contas.contas();
	}

	public StatusParcela[] statusParcela() {
		return StatusParcela.values();
	}

	public TipoTransacao[] tipos() {
		TipoTransacao t[] = new TipoTransacao[2];
		t[0] = TipoTransacao.PAGAMENTO;
		t[1] = TipoTransacao.RECEBIMENTO;
		return t;
	}

	public PeriodoPagamento[] periodos() {
		return PeriodoPagamento.values();
	}

	public List<Funcionario> listaVendedores() {
		return funcionarios.vendedorAtivo();
	}

	public PagamentoRecebimento getItem() {
		return item;
	}

	public void setItem(PagamentoRecebimento item) {

		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("pagReceb");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = pagamentosRecebimentos.porId2(id);
		}

		if (item == null) {
			inicio();
		} else {
			this.item = item;
			atualizaListaCheques();
		}
	}

	public List<Cheque> getListaCheques() {
		return listaCheques;
	}

	public void setListaCheques(List<Cheque> listaCheques) {
		this.listaCheques = listaCheques;
	}

	public boolean isFavorecidoOrigemVendedor() {
		return favorecidoOrigemVendedor;
	}

	public void setFavorecidoOrigemVendedor(boolean favorecidoOrigemVendedor) {
		this.favorecidoOrigemVendedor = favorecidoOrigemVendedor;
	}

}
