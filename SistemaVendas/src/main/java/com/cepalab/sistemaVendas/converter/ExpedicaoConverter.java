package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.Expedicao.dominio.Expedicao;
import com.cepalab.sistemaVendas.repository.Expedicoes;


@FacesConverter(forClass = Expedicao.class)
public class ExpedicaoConverter implements Converter {

	@Inject
	private Expedicoes expedicoes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Expedicao retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.expedicoes.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Expedicao expedicao = (Expedicao)value;
			return expedicao.getId() == null ? null : expedicao.getId().toString();
		}
		return "";
	}

}