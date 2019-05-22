package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.cadastro.dominio.ComissaoRecolhidaRessarcida;
import com.cepalab.sistemaVendas.repository.ComissoesRecolhidasRessarcidas;


@FacesConverter(forClass = ComissaoRecolhidaRessarcida.class)
public class ComissaoRecolhidaRessarcidaConverter implements Converter {

	@Inject
	private ComissoesRecolhidasRessarcidas comissoes;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		ComissaoRecolhidaRessarcida retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.comissoes.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			ComissaoRecolhidaRessarcida comissao = (ComissaoRecolhidaRessarcida)value;
			return comissao.getId() == null ? null : comissao.getId().toString();
		}
		return "";
	}

}