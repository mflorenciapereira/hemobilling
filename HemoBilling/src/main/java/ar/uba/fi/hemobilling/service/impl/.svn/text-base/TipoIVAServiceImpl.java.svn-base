package ar.uba.fi.hemobilling.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.TipoIVADAO;
import ar.uba.fi.hemobilling.domain.general.TipoIVA;
import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.service.TipoIVAService;


@Service("tipoIVAService")
public class TipoIVAServiceImpl implements TipoIVAService {

	@Resource(name = "tipoIVADAO")
	private TipoIVADAO tipoIVADAO;
	
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	private static Logger logger = Logger.getLogger(TipoIVAServiceImpl.class);
	
	

	
	@Override
	public Collection<TipoIVADTO> getTiposIVA() throws HBDataAccessException {
		try
		{
			
			Collection<TipoIVA> tipos = tipoIVADAO.getTiposIVA();
			Collection<TipoIVADTO> tiposDTO = mapper.map( tipos , TipoIVADTO.class );
				
			logger.info("Se leyo la lista de tipos de IVA satisfactoriamente.");
			
			return tiposDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de tipos de IVA." , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}

	
	@Override
	public TipoIVADTO getTipoIVA(long id) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try {
			TipoIVA tipo = tipoIVADAO.getTipoIVA(id);
			TipoIVADTO tipoIVADTO = mapper.map(tipo, TipoIVADTO.class);
								
			logger.info("Se obtuvo el tipo de IVA "+id+" satisfactoriamente");
			return tipoIVADTO;
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener el tipo de IVA"+ id , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el tipo de IVA "+id , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}


}
