package ar.uba.fi.hemobillingLaboService.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobillingLaboService.domain.AnalisisLaboratorio;
import ar.uba.fi.hemobillingLaboService.domain.FiltroConsultaAnalisisLaboratorio;

public interface OperacionesLaboratorioDAO 
{

	Collection<AnalisisLaboratorio> buscar( FiltroConsultaAnalisisLaboratorio filtro ) throws DataAccessException, ObjectNotFoundException;

}
