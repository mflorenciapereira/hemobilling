package ar.uba.fi.hemobilling.dozer.converter;

import org.dozer.CustomConverter;

public class CustomLongConverter implements CustomConverter {

	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {

		if (source == null || ((source instanceof String)) && ((String)source).isEmpty() ) {
			return null;
		}

		if (source instanceof String) 
		{
			String original = ((String)source).trim();
			Long valor = Long.parseLong(original);
			return valor;
		}
		else 
		{ //Asumo que es Long
			Long monto = (Long)source;
			destination =  String.valueOf(monto);
			return destination;
		}
	}

}
