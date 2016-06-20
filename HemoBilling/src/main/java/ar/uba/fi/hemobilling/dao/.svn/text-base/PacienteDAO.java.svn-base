package ar.uba.fi.hemobilling.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.paciente.FiltroConsultaPacientes;
import ar.uba.fi.hemobilling.domain.paciente.Paciente;

public interface PacienteDAO {
	
	public Paciente obtener( Long dni ) throws ObjectNotFoundException, DataAccessException;
	public Paciente obtenerPorNumeroHCParaListar(Long numHC) throws ObjectNotFoundException, DataAccessException;
	
	public Paciente obtenerPorNumeroHC( Long numHC ) throws ObjectNotFoundException, DataAccessException;
	
	public void agregar( Paciente newPaciente ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( Paciente updatedPaciente ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantidadConsulta( FiltroConsultaPacientes filtro ) throws DataAccessException;
	
	public Collection<Paciente> consultar( FiltroConsultaPacientes filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public void eliminar( Paciente paciente ) throws ObjectNotFoundException, DataAccessException;
	
	public Boolean existePaciente(long id) throws DataAccessException;
	public Collection<Paciente> getPacientesParaListar() throws DataAccessException;

}
