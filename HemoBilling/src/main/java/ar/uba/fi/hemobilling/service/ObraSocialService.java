package ar.uba.fi.hemobilling.service;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.AsociacionObraSocialListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.FiltroConsultaObrasSocialesDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;

public interface ObraSocialService {
	
	
	public ObraSocialDTO getObraSocial( long codigo ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void agregarObraSocial( ObraSocialDTO nuevaObraSocial ) throws HBDataAccessException, HBObjectExistsException, HBServiceException;
	
	public void eliminarObraSocial( ObraSocialDTO obraSocial ) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation;
	
	public void actualizarObraSocial( ObraSocialDTO obraSocial ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException, HBObjectExistsException;
	
	public Collection<ObraSocialDTO> getObrasSociales( FiltroConsultaObrasSocialesDTO filtro , FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;

	Collection<ObraSocialDTO> getObrasSociales() throws HBDataAccessException;
	
	Collection<ObraSocialDTO> getObrasSocialesParaListar() throws HBDataAccessException;

	public List<AsociacionObraSocialListaPrecioDTO> getAsociaciones(Long codigoOS);

	public void asociar(ObraSocialDTO editarDTO);

	public AsociacionObraSocialListaPrecioDTO getAsociacionActual(Long codigo);

	

	void actualizarAsociaciones(List<AsociacionObraSocialListaPrecioDTO> lista,
			Long osid) throws ParseException;

	public Collection<ObraSocialDTO> getObrasSocialesDetalladasParaListar() throws HBDataAccessException;
	
	
	
}
