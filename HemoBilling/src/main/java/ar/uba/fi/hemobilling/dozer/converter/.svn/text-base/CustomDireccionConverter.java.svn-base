package ar.uba.fi.hemobilling.dozer.converter;

import org.apache.commons.lang.StringUtils;
import org.dozer.CustomConverter;

import ar.uba.fi.hemobilling.domain.clientes.Cliente;
import ar.uba.fi.hemobilling.domain.general.Direccion;

public class CustomDireccionConverter implements CustomConverter {

	@Override
	@SuppressWarnings("rawtypes")
	public Object convert(Object destination, Object source, Class destClass, Class sourceClass)
	{
		String dirCompleta = StringUtils.EMPTY;
		
		if( Cliente.class.equals(sourceClass) )
		{
			Direccion dir = (Direccion)source;
			
			dirCompleta = 	dir.getDireccion() +", "+ 
							dir.getLocalidad() +", "+
							dir.getProvincia()+", "+
							dir.getPais()+". CP: "+
							dir.getCodigoPostal();
			
			return dirCompleta;
		}
		
		return null;
	}

}
