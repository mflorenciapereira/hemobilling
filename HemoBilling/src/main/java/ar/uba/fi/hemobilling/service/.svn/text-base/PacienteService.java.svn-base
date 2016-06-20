package ar.uba.fi.hemobilling.service;

import java.util.Collection;

import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.paciente.FiltroConsultaPacienteDTO;
import ar.uba.fi.hemobilling.dto.paciente.PacienteDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;

public interface PacienteService {

	public PacienteDTO getPaciente(Long id) throws HBDataAccessException, HBObjectNotExistsException;
	
	public PacienteDTO getPacientePorNumeroHC( Long numHC ) throws HBDataAccessException, HBObjectNotExistsException;
	public PacienteDTO getPacientePorNumeroHCParaListar(Long numHC) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void agregarPaciente( PacienteDTO nuevoPaciente ) throws HBDataAccessException, HBObjectExistsException, HBServiceException;
	
	public void eliminarPaciente( PacienteDTO paciente ) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation;
	
	public void actualizarPaciente( PacienteDTO paciente ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException;
	
	public Collection<PacienteDTO> getPacientes( FiltroConsultaPacienteDTO filtro , FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;

	public Collection<PacienteDTO> getPacientesParaListar() throws HBDataAccessException;
	
}
