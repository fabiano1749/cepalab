package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.operacao.dominio.Operacao;
import com.cepalab.sistemaVendas.repository.Operacoes;


@FacesConverter(forClass = Operacao.class)
public class OperacaoConverter implements Converter {

	@Inject
	private Operacoes operacoes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Operacao retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.operacoes.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Operacao operacao = (Operacao)value;
			return operacao.getId() == null ? null : operacao.getId().toString();
		}
		return "";
	}

}