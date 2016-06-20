package ar.uba.fi.hemobilling.service;

import java.util.Collection;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.FiltroConsultaPrestacionesDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.PrestacionDTO;
import ar.uba.fi.hemobilling.exception.domain.AsociacionPrestacionDuplicadaException;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.exception.domain.PrestacionAsociadaPrestacionException;

public interface PrestacionService {
	
	
	public PrestacionDTO getPrestacion( long codigo ) throws HBDataAccessException, HBObjectNotExistsException;
	public PrestacionDTO getPrestacionParaListar(long codigo) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void agregarPrestacion( PrestacionDTO nuevaPrestacion ) throws HBDataAccessException, HBObjectExistsException, HBServiceException, ObjectNotFoundException, AsociacionPrestacionDuplicadaException;
	
	public void eliminarPrestacion( PrestacionDTO prestacion ) throws HBDataAccessException, HBObjectNotExistsException, PrestacionAsociadaPrestacionException, HBEntityRelationViolation;
	
	public void actualizarPrestacion( PrestacionDTO prestacion ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException, HBObjectExistsException, AsociacionPrestacionDuplicadaException;
	
	public Collection<PrestacionDTO> getPrestaciones( FiltroConsultaPrestacionesDTO filtro , FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;

	public Collection<PrestacionDTO> getPrestaciones() throws HBDataAccessException;
	
	public Collection<PrestacionDTO> getPrestacionesParaListar() throws HBDataAccessException;

	public Collection<PrestacionDTO> getPrestacionesById() throws HBDataAccessException;
	
}
