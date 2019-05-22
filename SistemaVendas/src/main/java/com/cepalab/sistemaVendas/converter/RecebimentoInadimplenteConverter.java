package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;


import com.cepalab.sistemaVendas.operacao.dominio.RecebimentoInadiplente;
import com.cepalab.sistemaVendas.repository.RecebimentosInadiplentes;


@FacesConverter(forClass = RecebimentoInadiplente.class)
public class RecebimentoInadimplenteConverter implements Converter {

	@Inject
	private RecebimentosInadiplentes recebimentos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		RecebimentoInadiplente retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.recebimentos.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			RecebimentoInadiplente recebimento = (RecebimentoInadiplente)value;
			return recebimento.getId() == null ? null : recebimento.getId().toString();
		}
		return "";
	}

}