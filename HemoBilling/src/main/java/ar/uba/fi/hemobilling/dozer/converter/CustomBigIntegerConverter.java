package ar.uba.fi.hemobilling.dozer.converter;

import java.math.BigInteger;

import org.dozer.CustomConverter;

public class CustomBigIntegerConverter implements CustomConverter 
{
	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {

		if (source == null) {
			return null;
		}

		if (source instanceof String) 
		{
			String original = ((String)source).trim();
			if(!original.isEmpty())
			{
				return new BigInteger(original); 
			}
			else
			{
				return new BigInteger("0"); 
			}
		} 
		else if( source instanceof BigInteger ) 
		{ 
			BigInteger dato = (BigInteger)source;
			return dato.toString();
		}
		else
			return null;
	}

}
