package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;


import com.cepalab.sistemaVendas.cadastro.dominio.TipoProduto;
import com.cepalab.sistemaVendas.repository.TiposProdutos;


@FacesConverter(forClass = TipoProduto.class)
public class TipoProdutoConverter implements Converter {

	@Inject
	private TiposProdutos tipos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TipoProduto retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.tipos.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			TipoProduto tipo = (TipoProduto)value;
			return tipo.getId() == null ? null : tipo.getId().toString();
		}
		return "";
	}

}