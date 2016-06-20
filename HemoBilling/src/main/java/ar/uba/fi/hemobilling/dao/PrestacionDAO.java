package ar.uba.fi.hemobilling.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.prestaciones.FiltroConsultaPrestaciones;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;

public interface PrestacionDAO {
	
	public Prestacion getPrestacion( long codigo ) throws ObjectNotFoundException, DataAccessException;
	public Prestacion getPrestacionParaListar(long codigo) throws ObjectNotFoundException, DataAccessException;
	
	public Boolean existePrestacion( long codigo ) throws DataAccessException;
	
	public void agregar( Prestacion prestacion ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( Prestacion prestacion ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantPrestaciones( FiltroConsultaPrestaciones filtro ) throws DataAccessException;
	
	public Collection<Prestacion> getPrestaciones( FiltroConsultaPrestaciones filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public void eliminar( Prestacion prestacion ) throws ObjectNotFoundException, DataAccessException;

	public Collection<Prestacion> getPrestaciones() throws DataAccessException;
	
	public Collection<Prestacion> getPrestacionesParaListar() throws DataAccessException;

	public Collection<Prestacion> getPrestacionesById();

	public boolean estaAsociadaAPrestacion(Prestacion prestacionEliminar);
	public List<Prestacion> obtenerModulosAsociados(
			Long prestacionDAO);
	
	
}