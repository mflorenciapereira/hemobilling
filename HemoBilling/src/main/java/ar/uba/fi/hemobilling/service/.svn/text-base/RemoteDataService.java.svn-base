package ar.uba.fi.hemobilling.service;

import java.util.Collection;

import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteLaboratorioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBRemoteException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;

public interface RemoteDataService {
	
	PacienteLaboratorioDTO getInformacionPacienteSistemaLaboratorio( String nroHC ) throws HBRemoteException, HBServiceException;
	
	PacienteHCDTO getInformacionPacienteSistemaHC( String nroHC ) throws HBRemoteException, HBServiceException;
	
	Boolean cambiarIDLaboratorioPorNumHC( String nroHC , String idSistemaLaboratorio ) throws HBRemoteException, HBServiceException;
	
	Collection<EstudioLaboratorioDTO> buscarAnalisisLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtroDTO ) throws HBRemoteException, HBServiceException;
	
}
