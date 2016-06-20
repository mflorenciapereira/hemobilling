package ar.uba.fi.hemobillingHCService.dao;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobillingHCService.domain.PacienteHC;

public interface PacienteHCDAO {
	
	PacienteHC getPaciente( Integer ID ) throws DataAccessException,ObjectNotFoundException;
}
