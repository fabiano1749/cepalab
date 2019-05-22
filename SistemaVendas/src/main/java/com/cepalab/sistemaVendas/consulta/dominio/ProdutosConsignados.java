package com.cepalab.sistemaVendas.consulta.dominio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cepalab.sistemaVendas.operacao.dominio.Consignacao;

public class ProdutosConsignados implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Consignacao> listaConsignados = new ArrayList<>();
	private List<ConsignadosConsulta> listaConsignadosConsulta = new ArrayList<>();
	private List<ProdutoQuantidadeConsignados> listaTotalConsignados = new ArrayList<>();
	
	
	
	public List<ConsignadosConsulta> listaSemRepeticao(List<ProdutoQuantidadeConsignados> lista) {
		for(Consignacao c : listaConsignados) {
			
			ConsignadosConsulta cc = new ConsignadosConsulta();
			cc.setCliente(c.getOperacao().getCliente());
			cc.setFuncionario(c.getOperacao().getFuncionario());
			cc.setRota(c.getOperacao().getCliente().getRota());
			cc.setUf(c.getOperacao().getCliente().getEndereco().getUf());
			cc.setCidade(c.getOperacao().getCliente().getEndereco().getCidade());
			cc.setConsignados(c.getTotalConsignado().intValue());
			ProdutoQuantidadeConsignados p = new ProdutoQuantidadeConsignados();
			p.setProduto(c.getProduto());
			p.setQuantidade(c.getTotalConsignado().intValue());
			p.incrementaSomaValorVenda(c.getValorUnitario().multiply(new BigDecimal(c.getTotalConsignado())));
			p.setId(c.getId().intValue());
			
			//Caso o cliente ainda não pertença a lista
			if(!listaConsignadosConsulta.contains(cc)) {
				cc.getListaConsignados().add(p);
				listaConsignadosConsulta.add(cc);
				incrementaTotalConsignado(p, lista);
			}
			
			//Caso o cliente já pertença a lista
			else {
				int index = listaConsignadosConsulta.indexOf(cc);
				//Caso o cliente ja perteca a lista mas o produto ainda não
				if(!listaConsignadosConsulta.get(index).getListaConsignados().contains(p)) {
					listaConsignadosConsulta.get(index).getListaConsignados().add(p);
					incrementaTotalConsignado(p, lista);
				}
				//Caso o cliente e o produto ja perteça a lista
				else {
					ProdutoQuantidadeConsignados pc;
					pc = listaConsignadosConsulta.get(index).adcionaProduto(p);
					if(pc != null) {
						incrementaTotalConsignado(pc, lista);
					}
				}
			}
		}	
		
		return listaConsignadosConsulta;
	}

	public void incrementaTotalConsignado(ProdutoQuantidadeConsignados p, List<ProdutoQuantidadeConsignados> lista) {
		for(ProdutoQuantidadeConsignados pc : lista) {
			if(pc.getProduto().equals(p.getProduto())) {
				pc.incrementaQuantidade(p);
				pc.incrementaSomaValorVenda(p.getSomaValoresDeVenda());
			}
		}
	}


	public List<Consignacao> getListaConsignados() {
		return listaConsignados;
	}

	public void setListaConsignados(List<Consignacao> listaConsignados) {
		this.listaConsignados = listaConsignados;
	}

	public List<ConsignadosConsulta> getListaConsignadosConsulta() {
		return listaConsignadosConsulta;
	}
	
	public void setListaConsignadosConsulta(List<ConsignadosConsulta> listaConsignadosConsulta) {
		this.listaConsignadosConsulta = listaConsignadosConsulta;
	}

	public List<ProdutoQuantidadeConsignados> getListaTotalConsignados() {
		return listaTotalConsignados;
	}

	public void setListaTotalConsignados(List<ProdutoQuantidadeConsignados> listaTotalConsignados) {
		this.listaTotalConsignados = listaTotalConsignados;
	}
	
	
	
}
