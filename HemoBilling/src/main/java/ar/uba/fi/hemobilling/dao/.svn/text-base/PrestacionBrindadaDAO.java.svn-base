package ar.uba.fi.hemobilling.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.DatosAdicionalesEstudioLaboratorioImportado;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.FiltroConsultaPrestacionesBrindadas;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.Observacion;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.PrestacionBrindada;

public interface PrestacionBrindadaDAO 
{
	public PrestacionBrindada obtener( Long id ) throws ObjectNotFoundException, DataAccessException;
	
	public void agregar( PrestacionBrindada newPrestacionBrindada ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( PrestacionBrindada updatedPrestacionBrindada ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantidadConsulta( FiltroConsultaPrestacionesBrindadas filtro ) throws DataAccessException;
	
	public Collection<PrestacionBrindada> consultar( FiltroConsultaPrestacionesBrindadas filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public void eliminar( PrestacionBrindada prestacionBrindada ) throws ObjectNotFoundException, DataAccessException;

	public Collection<Observacion> getObservaciones();

	public void agregarObservaciones(String observaciones);
	
	public Boolean tienePrecioAsignadoPBParaOSEnFecha( PrestacionBrindada pb ) throws DataAccessException;
	
	public DatosAdicionalesEstudioLaboratorioImportado getDatosAdicionalesEstudioImportado( PrestacionBrindada estudio ) throws DataAccessException , ObjectNotFoundException;
	
	public List<Object[]> getCodigosYaImportados(Date desde, Date hasta);

}
