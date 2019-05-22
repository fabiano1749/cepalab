package com.cepalab.sistemaVendas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.cepalab.sistemaVendas.cadastro.dominio.Categoria;
import com.cepalab.sistemaVendas.repository.Categorias;


@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter  implements Converter{
	
	    @Inject
		private Categorias categorias;
		
		@Override
		public Object getAsObject(FacesContext context, UIComponent component, String value) {
			Categoria retorno = null;
			
			if (StringUtils.isNotEmpty(value)) {
				retorno = this.categorias.porId(new Long(value));
			}
			
			return retorno;
		}

		@Override
		public String getAsString(FacesContext context, UIComponent component, Object value) {
			if (value != null) {
				return ((Categoria) value).getId().toString();
			}
			
			return "";
	}
	
	

}
