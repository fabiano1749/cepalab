package com.cepalab.sistemaVendas.operacao.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;
import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.operacao.dominio.TipoOperacao;
import com.cepalab.sistemaVendas.operacao.dominio.Venda;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.CadastroOperacao;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class OperacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Operacao item;
	private List<Cliente> listaClientes;
	
	
	// private List<Rota> listaRotas;
	// private Rota rota;

	@Inject
	private CadastroOperacao cadastroOperacao;

	@Inject
	private Operacoes operacoes;

	@Inject
	private Seguranca seg;

	@Inject
	private VendaBean vendaBean;

	@Inject
	private ConsignacaoBean consignacaoBean;

	@Inject
	private Clientes clientes;

	@Inject
	private AberturaBean aberturaBean;

	@Inject
	private ResumoOperacaoBean resumo;

	@Inject
	private ReceitaBean receita;

	public void inicio() {
		item = new Operacao();
		alimentaListaClientes();
		// listaRotas = rotas.rotasPorFuncionario(seg.UsuarioLogado());
	}

	/*
	 * Alimenta a lista de clientes por rota + eficiente public void
	 * alimentaListaClientes() { listaClientes = clientes.porFuncionario(rota); rota
	 * = new Rota(); }
	 */
	// Método provisório até resolver a questão das pastas
	public void alimentaListaClientes() {
		listaClientes = clientes.porFuncionario(seg.UsuarioLogado());
	}

	public void preparaSalvar() {
		List<Consignacao> listaAuxConsignacao = new ArrayList<>();
		List<Venda> listaAuxVenda = new ArrayList<>();

		if (item.getConsignacoes() != null) {
			for (Consignacao c : item.getConsignacoes()) {
				if (!c.getConsignados().equals(new Long("0")) || c.getDevolvidos() != 0 || c.getVendidos() != 0) {

					listaAuxConsignacao.add(c);
				}
			}
			item.setConsignacoes(listaAuxConsignacao);
		}

		if (item.getVendas() != null) {
			for (Venda v : item.getVendas()) {
				if (v.getQuantidade() != 0) {
					listaAuxVenda.add(v);
				}
			}
			item.setVendas(listaAuxVenda);
		}
	}

	
	public void salvar() {
		preparaSalvar();
		item.setReceitaTotal(item.ReceitaTotal());
		item.setComissaoTotal(item.comissaoTotal());
		try {
			if(item.getId() == null) {
				item.setFuncionario(seg.UsuarioLogado());	
			}
			cadastroOperacao.salvar(item);
			inicio();
			FacesUtil.addInfoMessage("Operação salva com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public TipoOperacao[] tiposOperacoes() {
		return TipoOperacao.values();
	}

	public Operacao getItem() {
		return item;
	}

	public void setItem(Operacao operacao) {
		
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("operacao");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			operacao = operacoes.porId2(id);
		}

		
		
		if (operacao == null) {
			inicio();
			consignacaoBean.inicio();
			vendaBean.inicio();
			
		} else {
			this.item = operacao;
			// listaRotas = rotas.rotasPorFuncionario(item.getFuncionario());
			// rota = item.getCliente().getRota();
			listaClientes = clientes.porFuncionario(item.getFuncionario());
			vendaBean.criaListaVendasEdicao();
			consignacaoBean.criaListaConsignacaoEdicao();
			resumo.alimentaListaResumoConsignacaoVenda();
			receita.iniciaReceitaTotal();
			receita.setNumFormaPag(item.getReceitas().size());
			receita.setReceitaRestante(BigDecimal.ZERO);
			aberturaBean.inicio();
			// FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formOperacao");

		}
	}

	
	
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	
	
}
