package ar.uba.fi.hemobilling.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.factura.FacturaServicio;
import ar.uba.fi.hemobilling.domain.factura.FiltroConsultaFacturaServicio;

public interface FacturaServicioDAO {

	public FacturaServicio obtener( Long id ) throws ObjectNotFoundException, DataAccessException;
	
	public void agregar( FacturaServicio newFactura ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( FacturaServicio updatedFactura ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantidadConsulta( FiltroConsultaFacturaServicio filtro ) throws DataAccessException;
	
	public Collection<FacturaServicio> consultar( FiltroConsultaFacturaServicio filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public void eliminar( FacturaServicio factura ) throws ObjectNotFoundException, DataAccessException;
	
	public Boolean existe(long id) throws DataAccessException;

	
	
}
