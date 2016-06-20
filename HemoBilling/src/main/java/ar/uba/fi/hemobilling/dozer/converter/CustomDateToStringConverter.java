package ar.uba.fi.hemobilling.dozer.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.CustomConverter;

public class CustomDateToStringConverter implements CustomConverter {


	private static final String PATTERN = "dd/MM/yyyy";

	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
		
		SimpleDateFormat df = new SimpleDateFormat(PATTERN);
		
		return convert(destination, source,destClass,sourceClass, df);
	}

	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass,
			SimpleDateFormat df) {
		if (source == null) {
			return null;
		}

		if (source instanceof String) {
			String original = ((String)source).trim();
			if(!original.isEmpty()){
				try {
					destination = df.parse( (String) original);
				} catch (ParseException e) {
					destination = null;
				}
				return destination;
			}
		} else { //Asumo que es Date
			Date date = (Date)source;
			destination = df.format(date);
			return destination;
		}
		
		return null;
	}
	
}
