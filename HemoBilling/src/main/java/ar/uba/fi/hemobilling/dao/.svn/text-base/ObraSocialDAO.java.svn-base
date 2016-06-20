package ar.uba.fi.hemobilling.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.obrassociales.FiltroConsultaObrasSociales;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.domain.obrassociales.Plan;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;

public interface ObraSocialDAO {
	
	public ObraSocial getObraSocial( long codigo ) throws ObjectNotFoundException, DataAccessException;
	
	public Serializable agregar( ObraSocial obrasocial ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( ObraSocial obrasocial ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantObrasSociales( FiltroConsultaObrasSociales filtro ) throws DataAccessException;
	
	public Collection<ObraSocial> getObrasSociales( FiltroConsultaObrasSociales filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public void eliminar( ObraSocial obrasocial ) throws ObjectNotFoundException, DataAccessException;

	public Collection<ObraSocial> getObrasSociales() throws DataAccessException;

	boolean existePlan(Long codigoOS, String codigoPlan, String nombre) throws DataAccessException;

	public void agregar(Plan plan) throws DataAccessException;

	public Collection<ObraSocial> getObrasSocialesParaListar() throws DataAccessException;

	public void eliminar(Plan plan) throws ObjectNotFoundException;

	public Collection<ObraSocial> getObrasSocialesDetalladasParaListar();

	public List<ObraSocialDTO> getObrasSocialesActuales(Long codigo);
	
	
	
}
