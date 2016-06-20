package ar.uba.fi.hemobilling.service;

import java.util.Collection;

import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.Observacion;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.FiltroConsultaPrestacionBrindadaDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.PrestacionBrindadaDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.ResultConsultaAnalisisLaboratorioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBPrestacionSinPrecioException;
import ar.uba.fi.hemobilling.exception.domain.HBRemoteException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;

public interface PrestacionBrindadaService {
	
	public static final String OBSERVACION_ANALISIS_LABORATORIO = "Analisis de Laboratorio";

	public PrestacionBrindadaDTO get( Long id ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void agregar( PrestacionBrindadaDTO nuevaPrestacionBrindada ) throws HBDataAccessException, HBObjectExistsException, HBServiceException, HBPrestacionSinPrecioException;
	
	public void eliminar( PrestacionBrindadaDTO prestacionBrindada ) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation;
	
	public void actualizar( PrestacionBrindadaDTO prestacionBrindada ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException, HBPrestacionSinPrecioException;
	
	public Collection<PrestacionBrindadaDTO> consultar( FiltroConsultaPrestacionBrindadaDTO filtro , FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;
	
	public ResultConsultaAnalisisLaboratorioDTO importarDesdeLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtro ) throws HBServiceException, HBDataAccessException, HBRemoteException;
	
	public void guardarPrestacionesDeLaboratorioAceptadas( ResultConsultaAnalisisLaboratorioDTO resultAceptado ) throws HBDataAccessException;

	public Collection<Observacion> getObservaciones();

	public ResultConsultaAnalisisLaboratorioDTO importarDesdeLaboratorioOmitirImportadas(
			FiltroConsultaEstudiosLaboratorioDTO filtroConsultaDTO) throws HBServiceException, HBDataAccessException, HBRemoteException;
	
}
