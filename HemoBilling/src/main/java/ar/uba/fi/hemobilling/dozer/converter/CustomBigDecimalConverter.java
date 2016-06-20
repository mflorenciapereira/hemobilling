package ar.uba.fi.hemobilling.dozer.converter;

import java.math.BigDecimal;

import org.dozer.CustomConverter;

import ar.uba.fi.hemobilling.util.ImporteUtils;

public class CustomBigDecimalConverter implements CustomConverter 
{
	private ImporteUtils importeUtils;

	public CustomBigDecimalConverter()
	{
		importeUtils = new ImporteUtils();
	}
	
	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {

		if (source == null) {
			return null;
		}

		if (source instanceof String) {
			String original = ((String)source).trim();
			if(!original.isEmpty()){
				destination = importeUtils.getImporteFromString(original);
				return destination;
			}
		} else { //Asumo que es BigDecimal
			BigDecimal monto = (BigDecimal)source;
			destination =  importeUtils.formatearImporte(monto);
			return destination;
		}
		
		return null;
	}

}
