package ar.uba.fi.hemobilling.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.PrestacionDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.prestaciones.FiltroConsultaPrestaciones;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.FiltroConsultaPrestacionesDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.PrestacionDTO;
import ar.uba.fi.hemobilling.exception.domain.AsociacionPrestacionDuplicadaException;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.exception.domain.PrestacionAsociadaPrestacionException;
import ar.uba.fi.hemobilling.service.PrestacionService;


@Service("prestacionService")
public class PrestacionServiceImpl implements PrestacionService {

	@Resource(name = "prestacionDAO")
	private PrestacionDAO prestacionDAO;
	
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	private static Logger logger = Logger.getLogger(PrestacionServiceImpl.class);
	
	

	@Override
	public void agregarPrestacion(PrestacionDTO prestacionDTO) throws HBDataAccessException, HBObjectExistsException, HBServiceException, ObjectNotFoundException, AsociacionPrestacionDuplicadaException 
	{	
		try 
		{
			Prestacion prestacion = mapper.map(prestacionDTO, Prestacion.class );
			agregarPrestacionesAsociadas(prestacionDTO, prestacion);
			prestacionDAO.agregar(prestacion);
			
			logger.info("Se agrego una nueva prestacion satisfactoriamente");
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria agregar una prestacion" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectFoundException e) {
			logger.error("Se encontro una prestacion que ya existia en la BD cuando se queria agregar una prestacion" , e );
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		} 
	
	}

	@SuppressWarnings("unchecked")
	private void agregarPrestacionesAsociadas(PrestacionDTO prestacionDTO,
			Prestacion prestacion) throws HBDataAccessException,
			HBObjectExistsException, HBServiceException,
			ObjectNotFoundException, AsociacionPrestacionDuplicadaException {
		
		
		List<PrestacionDTO> lista=prestacionDTO.getPrestacionesAsociadasAuto().subList(0,prestacionDTO.getCantidadPrestacionesAsociadas());
		if(hayAsociacionesDuplicadas(lista)) throw new AsociacionPrestacionDuplicadaException();
		Iterator<PrestacionDTO> it=lista.iterator();
		while(it.hasNext()){
			PrestacionDTO prestacionSimpleDTO=it.next();
			if(prestacionSimpleDTO.getCodigo()!=-1){
				Prestacion prestacionSimple=obtenerPrestacionSimple(prestacionSimpleDTO);
				prestacion.getPrestacionesAsociadas().add(prestacionSimple);
				
			};
			
			
			
			
		}
	}

	private boolean hayAsociacionesDuplicadas(List<PrestacionDTO> asoc) {
		Set<PrestacionDTO> set = new HashSet<PrestacionDTO>(asoc);
			return(set.size() < asoc.size());
				

	}

	private Prestacion obtenerPrestacionSimple(PrestacionDTO prestacionSimpleDTO) throws HBDataAccessException, HBObjectExistsException, HBServiceException, DataAccessException, ObjectNotFoundException, AsociacionPrestacionDuplicadaException{
		Prestacion prestacionSimple=null;
		try{
			prestacionSimple=prestacionDAO.getPrestacion(prestacionSimpleDTO.getCodigo());
		}catch(ObjectNotFoundException e){
			
			this.agregarPrestacion(prestacionSimpleDTO);
			prestacionSimple = prestacionDAO.getPrestacion(prestacionSimpleDTO.getCodigo());
						
		}
		
		return prestacionSimple;
	}

	@Override
	public void actualizarPrestacion(PrestacionDTO prestacion) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException, HBObjectExistsException, AsociacionPrestacionDuplicadaException {
		
		try 
		{
			Prestacion prestacionActualizada = mapper.map(prestacion, Prestacion.class );
			this.agregarPrestacionesAsociadas(prestacion, prestacionActualizada);
			prestacionDAO.actualizar(prestacionActualizada);
			logger.info("Se actualizo la prestacion satisfactoriamente");
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria actualizar una prestacion" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el usuario en la BD cuando se queria actualizar la prestacion" , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		} 

		
	}

	@Override
	public void eliminarPrestacion(PrestacionDTO prestacion) throws HBDataAccessException, HBObjectNotExistsException, PrestacionAsociadaPrestacionException, HBEntityRelationViolation
	{
		Prestacion prestacionEliminar = mapper.map(prestacion, Prestacion.class );
		if(prestacionDAO.estaAsociadaAPrestacion(prestacionEliminar)) throw new PrestacionAsociadaPrestacionException();
		try {
			
			prestacionDAO.eliminar(prestacionEliminar);
			logger.info("Se elimino la prestacion satisfactoriamente");
		} 
		catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar la prestacion" , e );
			if( e.getCause() instanceof ConstraintViolationException )
			{
				HBEntityRelationViolation ex = new HBEntityRelationViolation(e);
				throw ex;
			}
			else
			{
				HBDataAccessException ex = new HBDataAccessException(e);
				throw ex;
			}
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la prestacion en la BD cuando se queria eliminar una prestacion" , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		} ;
	}

	@Override
	public Collection<PrestacionDTO> getPrestaciones( FiltroConsultaPrestacionesDTO filtroDTO , FiltroPaginadoDTO filtroPaginadoDTO ) throws HBDataAccessException {
		try
		{
			FiltroConsultaPrestaciones filtro = mapper.map(filtroDTO, FiltroConsultaPrestaciones.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO, FiltroPaginado.class);
			
			filtroPaginado.setCantTotalRegs( prestacionDAO.getCantPrestaciones(filtro) );
			
			Collection<Prestacion> prestaciones = prestacionDAO.getPrestaciones(filtro,filtroPaginado);
			Collection<PrestacionDTO> prestacionesDTO = mapper.map( prestaciones , PrestacionDTO.class );
			
			
			logger.info("Se leyo la lista de prestaciones satisfactoriamente");
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs( filtroPaginado.getCantTotalRegs() );
			return prestacionesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de prestaciones" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}
	
	
	@Override
	public Collection<PrestacionDTO> getPrestaciones() throws HBDataAccessException {
		try
		{
			
			Collection<Prestacion> prestaciones = prestacionDAO.getPrestaciones();
			Collection<PrestacionDTO> prestacionesDTO = mapper.map( prestaciones , PrestacionDTO.class );
				
			logger.info("Se leyo la lista de posibles prestaciones satisfactoriamente");
			
			return prestacionesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de prestaciones" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}

	
	@Override
	public PrestacionDTO getPrestacion(long codigo) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try {
			Prestacion prestacion = prestacionDAO.getPrestacion(codigo);
			PrestacionDTO prestacionDTO = mapper.map(prestacion, PrestacionDTO.class);
			Iterator<Prestacion> it=prestacion.getPrestacionesAsociadas().iterator();
			while(it.hasNext()){
				Prestacion prestacionSimple=it.next();
				PrestacionDTO prestacionSimpleDTO=mapper.map(prestacionSimple, PrestacionDTO.class);;
				prestacionDTO.getPrestacionesAsociadasAuto().add(prestacionSimpleDTO);
			}
					
			logger.info("Se obtuvo la prestacion satisfactoriamente");
			return prestacionDTO;
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener una prestacion" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la prestacion cuando se queria obtener una prestacion" , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}
	
	public PrestacionDTO getPrestacionParaListar(long codigo) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try {
			Prestacion prestacion = prestacionDAO.getPrestacionParaListar(codigo);
			PrestacionDTO prestacionDTO = mapper.map(prestacion, PrestacionDTO.class);
			
			logger.info("Se obtuvo la prestacion satisfactoriamente para listar");
			return prestacionDTO;
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener una prestacion para listar" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la prestacion cuando se queria obtener una prestacion para listar" , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public Collection<PrestacionDTO> getPrestacionesById() throws HBDataAccessException {
		try
		{
			
			Collection<Prestacion> prestaciones = prestacionDAO.getPrestacionesById();
			Collection<PrestacionDTO> prestacionesDTO = mapper.map( prestaciones , PrestacionDTO.class );
				
			logger.info("Se leyo la lista de posibles prestaciones satisfactoriamente");
			
			return prestacionesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de prestaciones" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public Collection<PrestacionDTO> getPrestacionesParaListar() throws HBDataAccessException {
		try
		{
			
			Collection<Prestacion> prestaciones = prestacionDAO.getPrestacionesParaListar();
			Collection<PrestacionDTO> prestacionesDTO = mapper.map( prestaciones , PrestacionDTO.class );
				
			logger.info("Se leyo la lista de posibles prestaciones satisfactoriamente");
			
			return prestacionesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de prestaciones" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}


}
