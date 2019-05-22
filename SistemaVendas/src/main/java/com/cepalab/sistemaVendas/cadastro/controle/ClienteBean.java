package com.cepalab.sistemaVendas.cadastro.controle;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Contato;
import com.cepalab.sistemaVendas.cadastro.dominio.Endereco;
import com.cepalab.sistemaVendas.cadastro.dominio.EstadosBrasileiros;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Grupo;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.cadastro.dominio.StatusCliente;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoPessoa;
import com.cepalab.sistemaVendas.repository.Clientes;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.repository.Rotas;
import com.cepalab.sistemaVendas.security.Seguranca;
import com.cepalab.sistemaVendas.service.CadastroClienteService;
import com.cepalab.sistemaVendas.service.NegocioException;
import com.cepalab.sistemaVendas.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cliente item;
	private List<Rota> rotasCliente;

	private Funcionario funcionario;
	private List<Funcionario> listaFuncionarios;

	private boolean pessoaJuridica = true;
	private boolean pessoaFisica = false;

	@Inject
	private Rotas rotas;

	@Inject
	private Funcionarios funcionarios;

	@Inject
	private CadastroClienteService cadastroCliente;
	
	@Inject
	private Clientes clientes;

	@Inject
	private Seguranca seg;

	public ClienteBean() {
		limpar();
	}

	public void limpar() {
		item = new Cliente();
		item.setContato(new Contato());
		item.setEndereco(new Endereco());
	}

	@PostConstruct
	public void inicio() {
		limpar();
		if(!isAdministradorOuRoot()) {
			setFuncionario(seg.UsuarioLogado());
			criaListaRotas();
		}else {
			listaFuncionarios = funcionarios.funcionarios();	
		}

	}

	public void criaListaRotas() {
		if (funcionario != null) {
			rotasCliente = rotas.rotasPorFuncionario(funcionario);
		}
	}

	public void salvar() {
		try {
			cadastroCliente.salvar(item);
			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
		} catch (NegocioException e) {
			FacesUtil.addErrorMessage(e.getMessage());
		} finally {
			inicio();
			limpar();
		}
	}

	public StatusCliente[] statusCliente() {
		return StatusCliente.values();
	}

	public TipoPessoa[] tipoPessoa() {
		return TipoPessoa.values();
	}

	public Cliente getItem() {
		return item;
	}

	public void setItem(Cliente item) {
		String paramResposta = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest()).getParameter("cliente");
		
		if (paramResposta != null && !paramResposta.isEmpty()) {
			Long id = Long.valueOf(paramResposta);
			item = clientes.porId2(id);
		}
		
		
		if (item == null) {
			limpar();
		} else {
			
			this.item = item;
			
			if(!isAdministradorOuRoot()) {
				setFuncionario(seg.UsuarioLogado());
				criaListaRotas();
			}else {
				listaFuncionarios = funcionarios.funcionarios();
				setFuncionario(item.getRota().getFuncionario());
				criaListaRotas();
			}
			
			if (item.getTipoPessoa() == TipoPessoa.FISICA) {
				pessoaFisica = true;
				pessoaJuridica = false;
			} else {
				pessoaFisica = false;
				pessoaJuridica = true;
			}

		}
	}

	public void pessoa() {
		boolean aux = pessoaJuridica;
		pessoaJuridica = pessoaFisica;
		pessoaFisica = aux;
	}

	public List<Rota> getRotasCliente() {
		return rotasCliente;
	}

	public boolean isAdministrador() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAdministradorOuRoot() {
		for (Grupo g : seg.UsuarioLogado().getTipo().getGrupos()) {
			if (g.getNome().equals("ADMINISTRADORES") || g.getNome().equals("ROOT")) {
				return true;
			}
		}
		return false;
	}
	

	public EstadosBrasileiros[] estados() {
		return EstadosBrasileiros.values();
	}
	
	public void setRotasCliente(List<Rota> rotasCliente) {
		this.rotasCliente = rotasCliente;
	}

	public boolean isPessoaJuridica() {
		return pessoaJuridica;
	}

	public boolean isPessoaFisica() {
		return pessoaFisica;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
