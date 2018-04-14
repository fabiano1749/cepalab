package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.cadastro.dominio.Rota;
import com.cepalab.sistemaVendas.repository.Rotas;


@FacesConverter(forClass = Rota.class)
public class RotaConverter implements Converter {

	@Inject
	private Rotas rotas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Rota retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.rotas.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Rota rota = (Rota)value;
			return rota.getId() == null ? null : rota.getId().toString();
		}
		return "";
	}

}