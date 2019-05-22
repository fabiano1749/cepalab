package com.cepalab.sistemaVendas.Expedicao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.Expedicao.dominio.ExpedProduto;
import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.Expedicao.dominio.StatusExpedicao;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.cadastro.dominio.Produto;
import com.cepalab.sistemaVendas.repository.Expedicoes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.CadastroExpedicaoService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ExpedicaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Expedicao item;
	private List<Produto> listaProdutos;
	private List<Funcionario> listaFuncionarios;
	private List<Expedicao> listaExpedicao = new ArrayList<>();
	private boolean statusFechado = false;
	private String senhaVendedor;
	private boolean confirmaSaida;
	private boolean confirmaChegada;

	@Inject
	private Seguranca seg;

	@Inject
	private Produtos produtos;

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private CadastroExpedicaoService cadastro;

	@Inject
	private Expedicoes expedicoes;

	@Inject
	private PesquisaExpedicaoBean pesquisaExpedicaoBean;

	public void inicio() {
		this.item = new Expedicao();
		listaProdutos = produtos.produtos();
		listaFuncionarios = funcionarios.vendedorAtivo();
		iniciaListaExpedicaoProduto();
	}

	public void salvar() {

		try {
			cadastro.salvar(item);
			FacesUtil.addInfoMessage("Expedição salva com sucesso!");
			this.item = new Expedicao();
			iniciaListaExpedicaoProduto();
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public void possuiExpedicaoAberta() {
		Expedicao exp = expedicoes.expedicaoAbertaVendedor(item.getFuncionario());
		if (item.getId() == null && exp != null) {
			FacesUtil.addErrorMessage(
					"O vendedor Já possui uma expedição em aberto para o funcionário informado. Feche a expedição em aberto para realizar uma nova expedição! ");
			item.setFuncionario(null);
		}

	}

	public void iniciaListaExpedicaoProduto() {
		item.setExpedProdutos(new ArrayList<>());
		if (listaProdutos != null) {
			for (Produto p : listaProdutos) {
				ExpedProduto ep = new ExpedProduto();
				ep.setProduto(p);
				ep.setExpedicao(item);
				item.getExpedProdutos().add(ep);
			}
		}
	}

	public StatusExpedicao[] status() {
		return StatusExpedicao.values();
	}

	public void statusFechado() {
		if (item.getStatus().equals(StatusExpedicao.FECHADO)) {
			this.statusFechado = true;
		} else {
			this.statusFechado = false;
			this.item.setAcerto(null);
		}
	}

	public boolean podeFecharStatus() {
		if (isRoot() || (isExpedicao() && confirmaChegada == true)) {
			return true;
		} else {
			return false;
		}
	}

	public void confirmaSaida() {
		if (senhaVendedor.equals(item.getFuncionario().getSenha())) {
			item.setConferidoSaidaVendedor(confirmaSaida);
			salvar();
			pesquisaExpedicaoBean.fechaDialogo();
		} else {
			confirmaSaida = false;
			FacesUtil.addErrorMessage("Senha não confere!");
		}
	}

	public void confirmaChegada() {
		if (senhaVendedor.equals(item.getFuncionario().getSenha()) && confirmaChegada) {
			item.setConferidoChegadaVendedor(true);
			item.setStatus(StatusExpedicao.FECHADO);
			item.setAcerto(new Date());
			salvar();
		} else {
			confirmaChegada = false;
			FacesUtil.addErrorMessage("Senha não confere!");
		}
	}

	public boolean isRoot() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ROOT")) {
				return true;
			}
		}
		return false;
	}

	public boolean isExpedicao() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("EXPEDICAO")) {
				return true;
			}
		}
		return false;
	}

	public boolean isVendedor() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("VENDEDORES")) {
				return true;
			}
		}
		return false;
	}

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}

	public boolean podeEditarSaida() {
		if (isRoot() || (isExpedicao() && !item.isConferidoSaidaVendedor())) {
			return true;
		}
		return false;
	}

	public boolean podeEditarChegada() {
		if (isRoot() || (isExpedicao() && item.isConferidoSaidaVendedor() && !item.isConferidoChegadaVendedor())) {
			return true;
		}
		return false;
	}

	public boolean podeConfirmarSaida() {
		if (isRoot() || (isVendedor() && !item.isConferidoSaidaVendedor() && !item.isConferidoChegadaVendedor())) {
			return true;
		}
		return false;
	}

	public boolean podeConfirmarChegada() {
		if (isRoot() || (isVendedor() && item.isConferidoSaidaVendedor() && !item.isConferidoChegadaVendedor())) {
			return true;
		}
		return false;
	}

	public boolean podeLiberarEdicao() {
		if (isRoot() || isAdministrador()) {
			return true;
		} else
			return false;
	}

	public boolean podeEditarVendedorDataSaida() {
		if (isRoot() || (isExpedicao() && !item.isConferidoSaidaVendedor() && !item.isConferidoChegadaVendedor())) {
			return true;
		} else
			return false;
	}

	public boolean podeSalvar() {
		if (isRoot() || (isExpedicao() && item.getStatus() == StatusExpedicao.ABERTO)) {
			return true;
		} else
			return false;
	}

	public void liberarEdicao() {
		this.item.setConferidoChegadaVendedor(false);
		this.item.setConferidoSaidaVendedor(false);
		this.item.setStatus(StatusExpedicao.ABERTO);
		salvar();
	}

	public boolean statusAberto() {
		return item.getStatus() == StatusExpedicao.ABERTO;
	}

	public Expedicao getItem() {
		return item;
	}

	public void setItem(Expedicao item) {
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("expedicao");

		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = expedicoes.porId2(id);
		}

		if (item == null) {
			inicio();
		} else {
			listaFuncionarios = funcionarios.funcionarios();
			this.item = item;
			this.confirmaSaida = this.item.isConferidoSaidaVendedor();
			this.confirmaChegada = this.item.isConferidoChegadaVendedor();
			statusFechado();

		}
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public List<Expedicao> getListaExpedicao() {
		return listaExpedicao;
	}

	public void setListaExpedicao(List<Expedicao> listaExpedicao) {
		this.listaExpedicao = listaExpedicao;
	}

	public boolean isStatusFechado() {
		return statusFechado;
	}

	public void setStatusFechado(boolean statusFechado) {
		this.statusFechado = statusFechado;
	}

	public String getSenhaVendedor() {
		return senhaVendedor;
	}

	public void setSenhaVendedor(String senhaVendedor) {
		this.senhaVendedor = senhaVendedor;
	}

	public boolean isConfirmaSaida() {
		return confirmaSaida;
	}

	public void setConfirmaSaida(boolean confirmaSaida) {
		this.confirmaSaida = confirmaSaida;
	}

	public boolean isConfirmaChegada() {
		return confirmaChegada;
	}

	public void setConfirmaChegada(boolean confirmaChegada) {
		this.confirmaChegada = confirmaChegada;
	}

}
