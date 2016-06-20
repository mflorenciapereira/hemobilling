package ar.uba.fi.hemobilling.dto.prestaciones;

import org.springframework.util.AutoPopulatingList.ElementFactory;
import org.springframework.util.AutoPopulatingList.ElementInstantiationException;

public class PrestacionDTOFactory implements ElementFactory {

	@Override
	public Object createElement(int arg0) throws ElementInstantiationException {
		// set some default properties on the item
		PrestacionDTO prestacion = new PrestacionDTO();
		return prestacion;
	}

}
