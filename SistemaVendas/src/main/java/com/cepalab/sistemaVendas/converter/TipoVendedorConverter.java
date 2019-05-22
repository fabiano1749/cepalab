package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.cadastro.dominio.TipoVendedor;
import com.cepalab.sistemaVendas.repository.TiposVendedores;


@FacesConverter(forClass = TipoVendedor.class)
public class TipoVendedorConverter implements Converter {

	@Inject
	private TiposVendedores tipos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TipoVendedor retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.tipos.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			TipoVendedor cliente = (TipoVendedor)value;
			return cliente.getId() == null ? null : cliente.getId().toString();
		}
		return "";
	}

}