package com.cepalab.sistemaVendas.consulta.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.consulta.dominio.FechamentoGeral;
import com.cepalab.sistemaVendas.repository.CustosViagens;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Operacoes;
import com.cepalab.sistemaVendas.repository.Produtos;
import com.cepalab.sistemaVendas.security.Seguranca;

@ViewScoped
@Named
public class ResumoVendedorBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Funcionario> listaFuncionarios = new ArrayList<>();
	private List<Funcionario> listaFun = new ArrayList<>();
	
	private FechamentoGeral fechamentoGeral;
	

	@Inject
	private Funcionarios fun;

	@Inject
	private Seguranca seg;

	@Inject
	private Operacoes operacoes;
	
	@Inject
	private CustosViagens custos;
	
	@Inject
	private Produtos produtos;
	
	
	@PostConstruct
	public void inicio() {
		fechamentoGeral = new FechamentoGeral();
		listaFun = fun.funcionarios();
		retiraTiposFuncionários();
	}

	public void pesquisa() {
		fechamentoGeral.criaResumo(operacoes, custos, produtos);
	}
	
	

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}


	//Melhorar isso em versões posteriores
		private void retiraTiposFuncionários() {
			listaFuncionarios = new ArrayList<>();
			for(Funcionario f : listaFun) {
				if(!f.getTipoVendedor().getNome().equals("Interno-0")) {
					listaFuncionarios.add(f);
				}
			}
		}
		
	
	public FechamentoGeral getFechamentoGeral() {
		return fechamentoGeral;
	}

	public void setFechamentoGeral(FechamentoGeral fechamentoGeral) {
		this.fechamentoGeral = fechamentoGeral;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	
	
	
}
