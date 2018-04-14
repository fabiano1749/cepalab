package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.CadastroFuncionarioService;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class TrocaSenhaBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String senha;
	private String senha2;

	private Funcionario funcionario;
	private List<Funcionario> listaFuncionarios;

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private CadastroFuncionarioService cadastroFuncionario;

	@Inject
	private Seguranca seg;

	@PostConstruct
	public void inicio() {
		senha = senha2 = "";
		funcionario = new Funcionario();
		listaFuncionarios = funcionarios.funcionarios();
	}

	public void trocaSenha() {
		if (!isAdministrador()) {
			funcionario = seg.UsuarioLogado();
		}

		if (senha.length() >= 4 && senha.equals(senha2)) {
			funcionario.setSenha(senha);
			cadastroFuncionario.salvar(funcionario);
			FacesUtil.addInfoMessage("Senha alterada com sucesso!");
			inicio();
		}

		else {
			inicio();
			FacesUtil.addErrorMessage("As senhas não conferem ou possuem menos que 4 caracteres!");
		}
	}

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

}
