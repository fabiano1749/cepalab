package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.operacao.dominio.DespesaVendedor;
import com.cepalab.sistemaVendas.repository.DespesasVendedores;


@FacesConverter(forClass = DespesaVendedor.class)
public class DespesaVendedorConverter implements Converter {

	@Inject
	private DespesasVendedores despesas;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		DespesaVendedor retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.despesas.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			DespesaVendedor despesa = (DespesaVendedor)value;
			return despesa.getId() == null ? null : despesa.getId().toString();
		}
		return "";
	}

}