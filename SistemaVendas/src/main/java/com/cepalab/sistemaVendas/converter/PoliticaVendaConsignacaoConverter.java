package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.cadastro.dominio.PoliticaVendaConsignacaoTipoVendedorProduto;
import com.cepalab.sistemaVendas.repository.PoliticasVendasConsignacoes;


@FacesConverter(forClass = PoliticaVendaConsignacaoTipoVendedorProduto.class)
public class PoliticaVendaConsignacaoConverter implements Converter {

	@Inject
	private PoliticasVendasConsignacoes politicas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		PoliticaVendaConsignacaoTipoVendedorProduto retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.politicas.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			PoliticaVendaConsignacaoTipoVendedorProduto politica = (PoliticaVendaConsignacaoTipoVendedorProduto)value;
			return politica.getId() == null ? null : politica.getId().toString();
		}
		return "";
	}

}