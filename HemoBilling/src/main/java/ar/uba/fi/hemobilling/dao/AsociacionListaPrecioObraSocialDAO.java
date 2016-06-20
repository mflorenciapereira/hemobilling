package ar.uba.fi.hemobilling.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.domain.obrassociales.AsociacionObraSociaListaPrecio;

public interface AsociacionListaPrecioObraSocialDAO 
{
	public void agregar(AsociacionObraSociaListaPrecio asociacion) throws DataAccessException;
	
	public Collection<AsociacionObraSociaListaPrecio> getAsociaciones(Long codObraSocial) throws DataAccessException;
	
	public Collection<AsociacionObraSociaListaPrecio> getAsociacionesParaListar(Long codObraSocial) throws DataAccessException;
	
	public AsociacionObraSociaListaPrecio getUltimaAsociacion(Long codObraSocial) throws DataAccessException;
	
	public void actualizar(AsociacionObraSociaListaPrecio ultima) throws DataAccessException;
	
	public void eliminarAsociacionConListaPrecios( Long codObraSocial ) throws DataAccessException;

	public AsociacionObraSociaListaPrecio getAsociacionActual(Long codigo);

	public boolean existeAsociacion(String desde, String hasta, Long codigolp,
			Long codigoos);

	public void borrarAsociaciones(Long codigo);

}
