package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.financeiro.dominio.Transferencia;
import com.cepalab.sistemaVendas.repository.Transferencias;


@FacesConverter(forClass = Transferencia.class)
public class TransferenciaConverter implements Converter {

	@Inject
	private Transferencias transferencias;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Transferencia retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.transferencias.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Transferencia transf = (Transferencia)value;
			return transf.getId() == null ? null : transf.getId().toString();
		}
		return "";
	}

}