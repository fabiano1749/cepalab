package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.operacao.dominio.DescontoSalario;
import com.cepalab.sistemaVendas.repository.DescontosSalarios;


@FacesConverter(forClass = DescontoSalario.class)
public class DescontoSalarioConverter implements Converter {

	@Inject
	private DescontosSalarios descontos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		DescontoSalario retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.descontos.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			DescontoSalario desconto = (DescontoSalario)value;
			return desconto.getId() == null ? null : desconto.getId().toString();
		}
		return "";
	}

}