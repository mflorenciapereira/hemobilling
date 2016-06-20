package ar.uba.fi.hemobilling.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.factura.DatosFacturacionPrestacionesBrindadas;
import ar.uba.fi.hemobilling.domain.factura.FacturaPrestacion;
import ar.uba.fi.hemobilling.domain.factura.FiltroConsultaFacturasPrestacion;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;
import ar.uba.fi.hemobilling.reportes.factura.DatoListaReporteFactura;

public interface FacturaPrestacionDAO 
{
	public FacturaPrestacion obtener( Long id ) throws ObjectNotFoundException, DataAccessException;
	
	public void agregar( FacturaPrestacion newFactura ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( FacturaPrestacion updatedFactura ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantidadConsulta( FiltroConsultaFacturasPrestacion filtro ) throws DataAccessException;
	
	public Collection<FacturaPrestacion> consultar( FiltroConsultaFacturasPrestacion filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public Collection<FacturaPrestacion> consultarMinimo(FiltroConsultaFacturasPrestacion filtro, FiltroPaginado filtroPaginado) throws DataAccessException;
	
	public void eliminar( FacturaPrestacion factura ) throws ObjectNotFoundException, DataAccessException;
	
	public Boolean existe(long id) throws DataAccessException;
	
	public Collection<DatosFacturacionPrestacionesBrindadas> getDatosFacturacion( FiltroConsultaFacturasPrestacion filtro ) throws DataAccessException;

	public FacturaPrestacion obtenerOrdenado(Long id) throws DataAccessException, ObjectNotFoundException;

	public List<DatoListaReporteFactura> obtenerItemsTotalizados(Long id);

	public void eliminar(Long[] idsFacturas);

	public Collection<Prestacion> obtenerModulosConPrestacionesAsociadas(Collection<Long> obtenerIdsItems);

}
