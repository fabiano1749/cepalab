package com.cepalab.sistemaVendas.operacao.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.GenericDTO;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.cadastro.dominio.Transportadora;
import com.cepalab.sistemaVendas.consulta.dominio.FechamentoGeral;
import com.cepalab.sistemaVendas.consulta.dominio.ProdutoQuantidade;
import com.cepalab.sistemaVendas.consulta.dominio.ReceitaFormaPagamento;
import com.cepalab.sistemaVendas.fechamento.dominio.Fechamento;
import com.cepalab.sistemaVendas.fechamento.dominio.FormaPagamentoValor;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;

@SuppressWarnings("serial")
@Entity
@Table(name = "operacao")
public class Operacao extends GenericDTO {

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	private Date data;
	private String observacao;
	private Cliente cliente = new Cliente();
	private Funcionario funcionario = new Funcionario();
	private TipoOperacao tipo;
	private List<AberturaProduto> aberturasProdutos = new ArrayList<>();
	private List<Amostra> amostras = new ArrayList<>();
	private List<Consignacao> consignacoes = new ArrayList<>();
	private List<Receita> receitas = new ArrayList<>();
	private List<Venda> vendas = new ArrayList<>();
	private BigDecimal receitaTotal = BigDecimal.ZERO;
	private BigDecimal comissaoTotal = BigDecimal.ZERO;
	private boolean checado = false;
	private BigDecimal valorFrete = BigDecimal.ZERO;
	private Transportadora transportadora;
	private Date dataEnvio;

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false)
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(columnDefinition = "text")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@ManyToOne
	@JoinColumn(name = "funcionario_id", nullable = false)
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_operacao", nullable = false, length = 15)
	public TipoOperacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacao tipo) {
		this.tipo = tipo;
	}

	@OneToMany(mappedBy = "operacao", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Consignacao> getConsignacoes() {
		return consignacoes;
	}

	public void setConsignacoes(List<Consignacao> consignacoes) {
		this.consignacoes = consignacoes;
	}

	@OneToMany(mappedBy = "operacao", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	@OneToMany(mappedBy = "operacao", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<AberturaProduto> getAberturasProdutos() {
		return aberturasProdutos;
	}

	public void setAberturasProdutos(List<AberturaProduto> aberturasProdutos) {
		this.aberturasProdutos = aberturasProdutos;
	}

	@OneToMany(mappedBy = "operacao", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Amostra> getAmostras() {
		return amostras;
	}

	public void setAmostras(List<Amostra> amostras) {
		this.amostras = amostras;
	}

	@OneToMany(mappedBy = "operacao", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getReceitaTotal() {
		return receitaTotal;
	}

	public void setReceitaTotal(BigDecimal receitaTotal) {
		this.receitaTotal = receitaTotal;
	}

	@Column(nullable = false, precision = 10, scale = 2)
	public BigDecimal getComissaoTotal() {
		return comissaoTotal;
	}

	public void setComissaoTotal(BigDecimal comissaoTotal) {
		this.comissaoTotal = comissaoTotal;
	}

	public boolean isChecado() {
		return checado;
	}

	public void setChecado(boolean checado) {
		this.checado = checado;
	}

	@Column(name = "valor_frete", precision = 10, scale = 2)
	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	@OneToOne
	public Transportadora getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_envio")
	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	@Transient
	public BigDecimal ReceitaTotal() {
		BigDecimal receita = BigDecimal.ZERO;
		if (receitas != null && receitas.size() != 0) {
			for (Receita r : receitas) {
				receita = receita.add(r.getValor());
			}
		}
		return receita;

	}

	@Transient
	public BigDecimal comissaoTotal() {
		BigDecimal comissao = BigDecimal.ZERO;
		if (consignacoes != null && consignacoes.size() != 0) {
			for (Consignacao c : consignacoes) {
				comissao = comissao.add(c.comissao());
			}
		}

		if (vendas != null && vendas.size() != 0) {
			for (Venda v : vendas) {
				comissao = comissao.add(v.comissao());
			}
		}

		return comissao;
	}
/*
	@Transient
	public BigDecimal comisaoAbertura() {
		int quantProd = 0;
		int numFitas = 0;
		if (tipo == TipoOperacao.ABERTURA && aberturasProdutos != null) {
			quantProd = aberturasProdutos.size();
			for (AberturaProduto a : aberturasProdutos) {
				if (a.getProduto().getTipo().getNome().equals("Fita")) {
					numFitas++;
				}
			}
			if (numFitas > 1) {
				quantProd = quantProd - (numFitas - 1);
			}
		}
		if (quantProd > 3) {
			quantProd = 3;
		}
		return new BigDecimal("10").multiply(new BigDecimal(quantProd));
	}
*/
	
	// Falta implementar esse método
	@Transient
	public BigDecimal premiacaoColocacao() {
		return BigDecimal.ZERO;
	}

	@Transient
	public void valoresResumoVendedor(FechamentoGeral f, List<TipoProduto> tiposProdutos) {
		if (consignacoes != null && consignacoes.size() != 0) {
			for (Consignacao c : consignacoes) {
				for (ProdutoQuantidade p : f.getListaProdutoQuantidade()) {
					if (c.getProduto().getNome().equals(p.getProduto().getNome())) {
						p.incrementaVendidos(c.getVendidos());
						p.incrementaConsignados(c.getConsignados().intValue());
						p.incrementaReceita(c.getValorUnitario().multiply(new BigDecimal(c.getVendidos())));
						break;
					}
				}
			}
		}

		if (vendas != null && vendas.size() != 0) {
			for (Venda v : vendas) {
				for (ProdutoQuantidade p : f.getListaProdutoQuantidade()) {
					if (v.getProduto().getNome().equals(p.getProduto().getNome())) {
						p.incrementaVendidos(v.getQuantidade());
						p.incrementaReceita(v.getValorUnitario().multiply(new BigDecimal(v.getQuantidade())));
						break;
					}
				}
			}
		}

		if (receitas != null && receitas.size() != 0) {
			for (Receita r : receitas) {
				for (ReceitaFormaPagamento rfp : f.getListaReceitaFormaPagamento()) {
					if (r.getFormaPagamento().getDescricao().equals(rfp.getFormaPag().getDescricao())) {
						rfp.incrementaReceita(r.getValor());
						break;
					}
				}
			}
		}

		// Alimenta o número de aberturas
		if (tipo.equals(TipoOperacao.ABERTURA) && aberturasProdutos != null) {
			if (aberturasProdutos.size() == 1) {
				f.incrementaAberturas1p();

			} else if (aberturasProdutos.size() == 2) {
				f.incrementaAberturas2p();
			} else if (aberturasProdutos.size() >= 3) {
				f.incrementaAberturas3p();
			}
		}

		f.incrementaComissaoVendas(comissaoTotal());
		f.incrementaComissaoAberturas(funcionario.getTipoVendedor().comissaoAbertura(aberturasProdutos, tiposProdutos));
		f.incrementaPremiacaoAberturas(funcionario.getTipoVendedor().premiacaoAbertura(aberturasProdutos, tiposProdutos));
		f.incrementaPremiacaoColocacao(premiacaoColocacao());
		f.incrementaFaturamento(ReceitaTotal());
	}

	@Transient
	public void valoresFechamentoVendedor(Fechamento f, List<TipoProduto> tiposProdutos) {
		// Insere as consignações na lista de resumo
		if (consignacoes != null && consignacoes.size() != 0) {
			for (Consignacao c : consignacoes) {
				if (c.getVendidos() > 0) {
					ResumoConsignacaoVenda r = new ResumoConsignacaoVenda();
					r.setComissao(c.comissao());
					r.setOperacao(c.getOperacao());
					r.setProduto(c.getProduto());
					r.setReceita(c.receita());
					r.setTaxaComissao(c.getTaxaComissao());
					r.setVendidos(c.getVendidos());
					f.getListaResumoConsignacaoVenda().add(r);
				}
			}
		}

		// Insere as Vendas na lista de resumo
		if (vendas != null && vendas.size() != 0) {
			for (Venda v : vendas) {
				if (v.getQuantidade() > 0) {
					ResumoConsignacaoVenda r = new ResumoConsignacaoVenda();
					r.setComissao(v.comissao());
					r.setOperacao(v.getOperacao());
					r.setProduto(v.getProduto());
					r.setReceita(v.receita());
					r.setTaxaComissao(v.getTaxaComissao());
					r.setVendidos(v.getQuantidade());
					f.getListaResumoConsignacaoVenda().add(r);
				}
			}
		}

		// Alimenta a lista de receitas por forma de pagamento
		if (receitas != null && receitas.size() != 0) {
			for (Receita r : receitas) {
				for (FormaPagamentoValor rfp : f.getListaReceitaFormaPagamento()) {
					if (r.getFormaPagamento().equals(rfp.getForma())) {
						rfp.incrementaReceita(r.getValor());
						break;
					}
				}
			}
		}

		//Essalista é utilizada para calcular as comissões e premiações de abertura
		List<AberturaProduto> aberturasAuxiliares = new ArrayList<>();
		
		// Alimenta a lista de abertura de produtos / Clientes
		if (aberturasProdutos != null && aberturasProdutos.size() > 0 && tipo.equals(TipoOperacao.ABERTURA))
			for (AberturaProduto a : aberturasProdutos) {
				f.getListaAberturas().add(a);
				aberturasAuxiliares.add(a);
			}

		//Essalista é utilizada para calcular as comissões e premiações de colocacoes
		List<AberturaProduto> colocacoesAuxiliares = new ArrayList<>();
		
		// Alimenta a lista de colocação de produtos
		if (aberturasProdutos != null && aberturasProdutos.size() > 0 && tipo.equals(TipoOperacao.VISITA))
			for (AberturaProduto a : aberturasProdutos) {
				f.getListaColocacao().add(a);
				colocacoesAuxiliares.add(a);
			}
		
		// Alimenta o número de aberturas
		if (tipo.equals(TipoOperacao.ABERTURA) && aberturasProdutos != null) {
			if (aberturasProdutos.size() == 1) {
				f.incrementaAberturas1p();

			} else if (aberturasProdutos.size() == 2) {
				f.incrementaAberturas2p();
			} else if (aberturasProdutos.size() >= 3) {
				f.incrementaAberturas3p();
			}
		}

		f.incrementaComissaoVendas(comissaoTotal());
		f.incrementaComissaoAberturas(funcionario.getTipoVendedor().comissaoAbertura(aberturasAuxiliares, tiposProdutos));
		f.incrementaPremiacaoAberturas(funcionario.getTipoVendedor().premiacaoAbertura(aberturasAuxiliares, tiposProdutos));
		f.incrementaComissaoColocacao(funcionario.getTipoVendedor().comissaoColocacao(colocacoesAuxiliares, tiposProdutos));
		f.incrementaPremiacaoColocacao(funcionario.getTipoVendedor().premiacaoColocacao(colocacoesAuxiliares, tiposProdutos));
		f.incrementaFaturamento(ReceitaTotal());
	}

	@Transient
	public boolean possuiEnvioTransportadora() {
		for (Consignacao c : consignacoes) {
			if (c.getProntaEntrega() == false)
				return true;
		}

		for (Venda v : vendas) {
			if (v.getProntaEntrega() == false)
				return true;
		}
		return false;
	}

	@Transient
	public String ProdutoQuantidadeTransportadora() {
		String retorno = "";

		if (vendas != null) {
			for (Venda v : vendas) {
				if (v.getProntaEntrega() == false) {
					retorno = retorno + v.getProduto().getNome() + " : " + v.getQuantidade() + "   ";
				}
			}
		}
		if (consignacoes != null) {
			for (Consignacao c : consignacoes) {
				if (c.getProntaEntrega() == false) {
					retorno = retorno + c.getProduto().getNome() + " : " + c.getConsignados() + "   ";
				}
			}
		}

		return retorno;
	}

	@Transient
	public BigDecimal receitaTotalProdutosComFrete() {
		BigDecimal receita = BigDecimal.ZERO;

		if (vendas != null) {
			for (Venda v : vendas) {
				if (v.getProntaEntrega() == false) {
					receita = receita.add(v.receita());
				}
			}
		}

		if (consignacoes != null) {
			for (Consignacao c : consignacoes) {
				if (c.getProntaEntrega() == false) {
					receita = receita.add(c.receita());
				}
			}
		}
		return receita;
	}

	@Transient
	public BigDecimal calculaFrete(BigDecimal receitaTotal, BigDecimal receita, BigDecimal frete) {
		BigDecimal percentual = receita.divide(receitaTotal, 3, RoundingMode.FLOOR);
		return frete.multiply(percentual);
	}

	@Transient
	public void diluiFrete() {
		BigDecimal receitaTotal = receitaTotalProdutosComFrete();
		if (receitaTotal.compareTo(BigDecimal.ZERO) > 0) {
			if (vendas != null) {
				for (Venda v : vendas) {
					if (v.getProntaEntrega() == false) {
						v.setFreteDiluido(calculaFrete(receitaTotal, v.receita(), valorFrete));
					}
				}
			}

			if (consignacoes != null) {
				for (Consignacao c : consignacoes) {
					if (c.getProntaEntrega() == false) {
						c.setFreteDiluido(calculaFrete(receitaTotal, c.receita(), valorFrete));
					}
				}
			}
		}
	}

	@Transient
	public String formasPagamento() {
		String formas = "";
		if(receitas != null && !receitas.isEmpty()) {
			for(Receita r : receitas) {
				if(formas.equals("")) {
					formas = r.getFormaPagamento().getDescricao();
				}else {
					formas = formas + " - "+ r.getFormaPagamento().getDescricao();
				}
				
			}
		}
		return formas;
	}
	
	
}
