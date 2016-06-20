package ar.uba.fi.hemobilling.dozer.converter;

import org.dozer.CustomConverter;

public class CustomBooleanConverter implements CustomConverter {
	
	private static String OPCION_SI = "Si";
	private static String OPCION_NO = "No";

	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {

		if (source == null || ((source instanceof String)) && ((String)source).isEmpty() ) {
			return null;
		}

		if (source instanceof String) 
		{
			String original = ((String)source).trim();
			
			if( original.equals(OPCION_SI) )
				return true;
			else if( original.equals(OPCION_NO) )
				return false;
			else
				return false;
		}
		else if (source instanceof Boolean)
		{ //Asumo que es Long
			Boolean opcion = (Boolean)source;
			
			if( opcion.equals( Boolean.TRUE) )
				return OPCION_SI;
			else
				return OPCION_NO;
		}
		else
			return null;
	}

}
