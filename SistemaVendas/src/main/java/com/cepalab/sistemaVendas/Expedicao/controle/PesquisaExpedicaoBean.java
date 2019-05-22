package com.cepalab.sistemaVendas.Expedicao.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.Expedicao.dominio.StatusExpedicao;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.repository.Expedicoes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.filter.ExpedicaoFilter;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;


@Named
@ViewScoped
public class PesquisaExpedicaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Expedicao> listaExpedicao = new ArrayList<>();
	private ExpedicaoFilter filtroExpedicao;
	private Expedicao expedicao = new Expedicao();

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private Expedicoes expedicoes;

	@Inject
	private Seguranca seg;

	@PostConstruct
	public void inicio() {
		filtroExpedicao = new ExpedicaoFilter();
		if (isVendedor()) {
			filtroExpedicao.setFuncionario(seg.UsuarioLogado());
		} else {
			listaFuncionarios = funcionarios.vendedorAtivo();
		}
	}

	public void excluir() {
		try {
			if (podeExcluir()) {
				expedicoes.remover(this.expedicao);
				pesquiza();
				FacesUtil.addInfoMessage("Expedição excluida com sucesso!");
			} else {
				FacesUtil.addErrorMessage("Você não possui permissão para realizar essa operação!");
			}

		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} catch (Exception e) {
			FacesUtil.addErrorMessage(e.getMessage());
		}
	}

	public void pesquiza() {
		listaExpedicao = expedicoes.ExpedicoesFiltradas(filtroExpedicao);
	
		if(listaExpedicao == null) {
			FacesUtil.addErrorMessage("Não foram encontrados registros para os filtros informados !");
		}else if(listaExpedicao.isEmpty()) {
			FacesUtil.addErrorMessage("Não foram encontrados registros para os filtros informados !");
		}
	}

	public void fechaDialogo() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void abrirDialogo(Expedicao expedicao) {

		Map<String, Object> opcoes = new HashMap<>();
		opcoes.put("modal", true);
		opcoes.put("resizable", false);
		opcoes.put("contentHeight", 500);
		opcoes.put("contentWidth", 1200);

		List<String> lista = new ArrayList<>();
		lista.add("" + expedicao.getId());

		Map<String, List<String>> params = new HashMap<>();
		params.put("expedicao", lista);

		RequestContext.getCurrentInstance().openDialog("/dialogos/expedicaoDialogo", opcoes, params);
	}

	public boolean isVendedor() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("VENDEDORES")) {
				return true;
			}
		}
		return false;
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

	public boolean podeExcluir() {
		if (isRoot() || (isExpedicao() && this.expedicao.getStatus() == StatusExpedicao.ABERTO
				&& this.expedicao.isConferidoChegadaVendedor() == false && this.expedicao.isConferidoSaidaVendedor() == false)) {
			return true;
		} else {
			return false;
		}
	}

	public StatusExpedicao[] status() {
		return StatusExpedicao.values();
	}

	public void alimentaExpedicao(Expedicao expedicao) {
		this.expedicao = expedicao;
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

	public ExpedicaoFilter getFiltroExpedicao() {
		return filtroExpedicao;
	}

	public void setFiltroExpedicao(ExpedicaoFilter filtroExpedicao) {
		this.filtroExpedicao = filtroExpedicao;
	}

	public Expedicao getExpedicao() {
		return expedicao;
	}

	public void setExpedicao(Expedicao expedicao) {
		this.expedicao = expedicao;
	}

}
