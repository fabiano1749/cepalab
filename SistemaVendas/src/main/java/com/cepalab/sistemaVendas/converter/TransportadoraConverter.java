package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.cadastro.dominio.Transportadora;
import com.cepalab.sistemaVendas.repository.Transportadoras;


@FacesConverter(forClass = Transportadora.class)
public class TransportadoraConverter implements Converter {

	@Inject
	private Transportadoras transportadoras;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Transportadora retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.transportadoras.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Transportadora transportadora = (Transportadora)value;
			return transportadora.getId() == null ? null : transportadora.getId().toString();
		}
		return "";
	}

}