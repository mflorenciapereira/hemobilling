package ar.uba.fi.hemobillingLaboService.dao;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobillingLaboService.domain.PacienteLaboratorio;

public interface PacienteLaboratorioDAO {
	
	public PacienteLaboratorio getPaciente( Integer ID ) throws DataAccessException,ObjectNotFoundException;

	public void actualizar( PacienteLaboratorio paciente ) throws DataAccessException,ObjectNotFoundException;
}
