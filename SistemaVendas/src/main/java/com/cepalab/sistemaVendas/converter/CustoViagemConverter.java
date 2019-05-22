package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.operacao.dominio.CustoViagem;
import com.cepalab.sistemaVendas.repository.CustosViagens;


@FacesConverter(forClass = CustoViagem.class)
public class CustoViagemConverter implements Converter {

	@Inject
	private CustosViagens custos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		CustoViagem retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.custos.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			CustoViagem custo = (CustoViagem)value;
			return custo.getId() == null ? null : custo.getId().toString();
		}
		return "";
	}

}