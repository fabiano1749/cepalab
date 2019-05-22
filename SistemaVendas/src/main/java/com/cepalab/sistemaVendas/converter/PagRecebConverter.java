package com.cepalab.sistemaVendas.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.financeiro.dominio.PagamentoRecebimento;
import com.cepalab.sistemaVendas.repository.PagamentosRecebimentos;


@FacesConverter(forClass = PagamentoRecebimento.class)
public class PagRecebConverter implements Converter {

	@Inject
	private PagamentosRecebimentos pagamentosRecebimentos;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		PagamentoRecebimento retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.pagamentosRecebimentos.porId(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			PagamentoRecebimento pagReceb = (PagamentoRecebimento)value;
			return pagReceb.getId() == null ? null : pagReceb.getId().toString();
		}
		return "";
	}

}