package com.cepalab.sistemaVendas.util.diversos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cepalab.sistemaVendas.cadastro.dominio.Cliente;
import com.cepalab.sistemaVendas.cadastro.dominio.Contato;
import com.cepalab.sistemaVendas.cadastro.dominio.Endereco;
import com.cepalab.sistemaVendas.cadastro.dominio.Funcionario;
import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.cadastro.dominio.StatusCliente;
import com.cepalab.sistemaVendas.cadastro.dominio.TipoPessoa;
import com.cepalab.sistemaVendas.repository.Funcionarios;
import com.cepalab.sistemaVendas.service.CadastroClienteService;
import com.cepalab.sistemaVendas.service.NegocioException;

@Named
@ViewScoped
public class ImportaClientes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Funcionarios fun;

	@Inject
	private CadastroClienteService cadastroCliente;
	
	private String nomeArquivo;
	private List<Funcionario> listaFuncionario;
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	private Funcionario retornaFuncionario(String nome) {
		if (listaFuncionario != null) {
			for (Funcionario f : listaFuncionario) {
				if (f.getNome().toUpperCase().equals(nome.toUpperCase())) {
					return f;
				}
			}
		}
		return null;
	}

	private Rota retornaRota(Funcionario f, int r) {
		if (f != null && f.getRotas() != null) {
			for (Rota rota : f.getRotas()) {
				if(rota.getNumero() == r) {
					return rota;
				}
			}
		}
		return null;
	}
	
	public boolean retornaBloqueado(String s) {
		if(s.equals("NAO")) {
			return false;
		}
		else {
			return true;
		}
	}
	

	private Cliente retornaCliente(String s) throws ParseException {
		Cliente cliente = new Cliente();
		Endereco endereco = new Endereco();
		Contato contato = new Contato();
		String c[] = s.split(",");

		cliente.setDataCadastro(formato.parse(c[0]));
		cliente.setCodigo(Long.parseLong(c[1]));
		cliente.setTipoPessoa(TipoPessoa.valueOf(c[2]));
		cliente.setNome(c[3]);
		cliente.setRota(retornaRota(retornaFuncionario(c[11]), Integer.parseInt(c[4])));
		cliente.setNomeFantasia(c[6]);
		cliente.setCpfCnpj(c[7]);
		cliente.setInscricaoEstadual(c[8]);
		cliente.setInscricaoMunicipal(c[9]);
		cliente.setResponsavelContato(c[10]);
		endereco.setLogradouro(c[12]);
		endereco.setNumero(c[13]);
		endereco.setComplemento(c[14]);
		endereco.setCidade(c[15]);
		endereco.setBairro(c[16]);
		endereco.setCep(c[17]);
		endereco.setUf(c[18]);
		
		cliente.setEndereco(endereco);
		
		contato.setFixo(c[19]);
		contato.setEmail1(c[20]);
		contato.setEmail2("");
		contato.setCelular("");
		
		cliente.setContato(contato);
		
		cliente.setBloqueado(retornaBloqueado(c[22]));
		cliente.setStatus(StatusCliente.valueOf(c[23].toUpperCase()));
		
		
		return cliente;

	}

	public void importaCliente() throws IOException, ParseException {
		listaFuncionario = fun.funcionarios();

		String linha = "";
		BufferedReader arquivo = new BufferedReader(new FileReader("/home/fabiano/Área de Trabalho/" + nomeArquivo));

		while ((linha = arquivo.readLine()) != null) {
			try {
				cadastroCliente.salvar(retornaCliente(linha));
			} catch (NegocioException e) {
				System.out.println("O cliente" + linha + " " + "já esta cadastrado");
			}
		}
		
		arquivo.close();
		

	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

}

