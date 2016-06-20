package ar.uba.fi.hemobilling.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.AsociacionListaPrecioObraSocialDAO;
import ar.uba.fi.hemobilling.dao.ObraSocialDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.listasprecio.ListaPrecio;
import ar.uba.fi.hemobilling.domain.obrassociales.AsociacionObraSociaListaPrecio;
import ar.uba.fi.hemobilling.domain.obrassociales.FiltroConsultaObrasSociales;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.domain.obrassociales.Plan;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;
import ar.uba.fi.hemobilling.dto.obrassociales.AsociacionObraSocialListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.FiltroConsultaObrasSocialesDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.PlanDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.service.ObraSocialService;


@Service("obraSocialService")
public class ObraSocialServiceImpl implements ObraSocialService 
{
	@Resource(name = "obraSocialDAO")
	private ObraSocialDAO obraSocialDAO;
	
	private static final String FORMATO_DIA = "dd/MM/yyyy";
	
	@Resource(name = "oslpDAO")
	private AsociacionListaPrecioObraSocialDAO oslpDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	private static Logger logger = Logger.getLogger(ObraSocialServiceImpl.class);
	
	
	private void agregarPlan(ObraSocialDTO osdto,PlanDTO planDTO){
		
		
			if(!obraSocialDAO.existePlan(osdto.getCodigo(), planDTO.getCodigo(), planDTO.getNombre())){
			
				Plan plan = mapper.map(planDTO, Plan.class );
				plan.setObraSocial(new ObraSocial(osdto.getCodigo()));
				obraSocialDAO.agregar(plan);
			}
					
		
		
	}
	


	@Override
	public void agregarObraSocial(ObraSocialDTO obraSocialDTO) throws HBDataAccessException, HBObjectExistsException, HBServiceException{	
		try 
		{
			ObraSocial obrasocial = mapper.map(obraSocialDTO, ObraSocial.class );			
			Serializable idS=obraSocialDAO.agregar(obrasocial);
			if(idS!=null){
				obraSocialDTO.setCodigo((Long) idS);
				this.agregarPlanes(obraSocialDTO);
				if(obraSocialDTO.getAsociacionActual().getListaPrecio().getCodigo()!=null)
					this.agregarAsociacion(obraSocialDTO);
			
			};
			
			logger.info("Se agrego la nueva obra social "+obrasocial.getCodigo()+" satisfactoriamente");
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria agregar la obra social "+obraSocialDTO.getCodigo(), e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectFoundException e) {
			logger.error("Se encontro una obra social que ya existia en la BD cuando se queria agregar la obra social"+obraSocialDTO.getCodigo() , e );
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		} 
	
	}


	private void agregarAsociacion(ObraSocialDTO obraSocialDTO) {
		AsociacionObraSociaListaPrecio asociacion=new AsociacionObraSociaListaPrecio();
		asociacion.setListaPrecio(new ListaPrecio(obraSocialDTO.getAsociacionActual().getListaPrecio().getCodigo()));
		asociacion.setObraSocial(new ObraSocial(obraSocialDTO.getCodigo()));
		java.sql.Date sqlToday = getToday();
		asociacion.setDesde(sqlToday);
		oslpDAO.agregar(asociacion);
		
	}



	private java.sql.Date getToday() {
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());
		return sqlToday;
	}



	private void agregarPlanes(ObraSocialDTO obraSocialDTO) 
	{
		@SuppressWarnings("unchecked")
		Iterator<PlanDTO> it=obraSocialDTO.getPlanesAuto().iterator();
		
		while(it.hasNext())
		{
			PlanDTO planDTO=it.next();
			if( planDTO.getCodigo()!=null || planDTO.getNombre()!=null)
			{
				this.agregarPlan(obraSocialDTO, planDTO);
				
			};
			
		}
		
	}



	@Override
	public void actualizarObraSocial(ObraSocialDTO obrasocial) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException, HBObjectExistsException {
		
		try 
		{
			ObraSocial obraSocialActualizada = mapper.map(obrasocial, ObraSocial.class );
			
			obraSocialDAO.actualizar(obraSocialActualizada);
			this.agregarPlanes(obrasocial);
			if( (obrasocial.getAsociacionActual().getListaPrecio().getCodigo()!=null) && 
				(!existeAsociacion(obrasocial.getCodigo(), obrasocial.getAsociacionActual().getListaPrecio().getCodigo() ) ) )
				this.asociar(obrasocial);
			
			eliminarPlanes(obrasocial);
			
			logger.info("Se actualizo la obra social"+obraSocialActualizada.getCodigo()+" satisfactoriamente");
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria actualizar la obra social"+obrasocial.getCodigo() , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la obra social en la BD cuando se queria actualizar la obra social"+obrasocial.getCodigo() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		} 

		
	}

	private void eliminarPlanes(ObraSocialDTO obrasocialdto) throws HBDataAccessException, HBObjectNotExistsException {
		
		try {
			Iterator<Plan> it= obraSocialDAO.getObraSocial(obrasocialdto.getCodigo()).getPlanes().iterator();
			while(it.hasNext()){
				Plan plan=it.next();
				if(!planContenido(obrasocialdto,plan))
					obraSocialDAO.eliminar(plan);
			}
		} catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar la obra social"+obrasocialdto.getCodigo() , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) 
		{
			logger.error("No se encontro la obra social en la BD cuando se queria eliminar la obra social"+obrasocialdto.getCodigo() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
		
		
	}



	@SuppressWarnings("unchecked")
	private boolean planContenido(ObraSocialDTO obrasocialdto, Plan plan) {
		Iterator<PlanDTO> it=obrasocialdto.getPlanesAuto().iterator();
		boolean encontrado=false;
		while((it.hasNext())&&(!encontrado)){
			PlanDTO plandto=it.next();
			if((plandto.getCodigo().compareTo(plan.getCodigo())==0)&&(plandto.getNombre().compareTo(plan.getNombre())==0)){
				encontrado=true;
			}
		}
		
		return encontrado;
	}



	private boolean existeAsociacion(Long idos,Long idlp) {
		AsociacionObraSociaListaPrecio ultima=oslpDAO.getUltimaAsociacion(idos);
		if(ultima!=null)
				return (ultima.getListaPrecio().getCodigo().compareTo(idlp)==0);
		else
			return false;
		
	}



	@Override
	public void eliminarObraSocial(ObraSocialDTO obrasocial) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation
	{
		
		try 
		{
			ObraSocial obraSocialEliminar = obraSocialDAO.getObraSocial(obrasocial.getCodigo());
			
			oslpDAO.eliminarAsociacionConListaPrecios( obrasocial.getCodigo() );
			obraSocialDAO.eliminar(obraSocialEliminar);
			
			logger.info("Se elimino la obra social "+obrasocial.getCodigo()+" satisfactoriamente");
		} 
		catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar la obra social" +obrasocial.getCodigo(), e );
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
			logger.error("No se encontro la prestacion en la BD cuando se queria eliminar la obra social"+obrasocial.getCodigo() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public Collection<ObraSocialDTO> getObrasSociales( FiltroConsultaObrasSocialesDTO filtroDTO , FiltroPaginadoDTO filtroPaginadoDTO ) throws HBDataAccessException {
		try
		{
			FiltroConsultaObrasSociales filtro = mapper.map(filtroDTO, FiltroConsultaObrasSociales.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO, FiltroPaginado.class);
			
			filtroPaginado.setCantTotalRegs( obraSocialDAO.getCantObrasSociales(filtro) );
			
			Collection<ObraSocial> obrassociales = obraSocialDAO.getObrasSociales(filtro,filtroPaginado);
			Collection<ObraSocialDTO> obrassocialesDTO = mapper.map( obrassociales , ObraSocialDTO.class );
			
			
			logger.info("Se leyo la lista de obras sociales satisfactoriamente");
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs( filtroPaginado.getCantTotalRegs() );
			return obrassocialesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de obras sociales" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}
	
	
	@Override
	public Collection<ObraSocialDTO> getObrasSociales() throws HBDataAccessException {
		try
		{
			
			Collection<ObraSocial> obrassociales = obraSocialDAO.getObrasSociales();
			Collection<ObraSocialDTO> obrassocialesDTO = mapper.map( obrassociales , ObraSocialDTO.class );
				
			logger.info("Se leyo la lista de obras sociales satisfactoriamente");
			
			return obrassocialesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de obras sociales" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}

	
	@Override
	public ObraSocialDTO getObraSocial(long codigo) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try {
			ObraSocial obrassocial = obraSocialDAO.getObraSocial(codigo);
			ObraSocialDTO obrasocialDTO = mapper.map(obrassocial, ObraSocialDTO.class);
			Iterator<Plan> it=obrassocial.getPlanes().iterator();
			while(it.hasNext()){
				Plan plan=it.next();
				PlanDTO planDTO=mapper.map(plan, PlanDTO.class);;
				obrasocialDTO.getPlanesAuto().add(planDTO);
			}
			
			if(obrasocialDTO.getTipoIVA()==null){
				obrasocialDTO.setTipoIVA(new TipoIVADTO());
			}
			
			logger.info("Se obtuvo la obra social "+codigo+" satisfactoriamente");
			return obrasocialDTO;
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener la obra social"+ codigo , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la obra social"+codigo , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}



	@Override
	public List<AsociacionObraSocialListaPrecioDTO> getAsociaciones(
			Long codigoOS) {
		Collection<AsociacionObraSociaListaPrecio> asociaciones=oslpDAO.getAsociacionesParaListar(codigoOS);
		List<AsociacionObraSocialListaPrecioDTO> asociacionesDTO = new ArrayList<AsociacionObraSocialListaPrecioDTO>();
		 asociacionesDTO.addAll(mapper.map( asociaciones , AsociacionObraSocialListaPrecioDTO.class ));
		return asociacionesDTO;
	}



	@Override
	public void asociar(ObraSocialDTO editarDTO) {
		
		AsociacionObraSociaListaPrecio ultima=oslpDAO.getUltimaAsociacion(editarDTO.getCodigo());
		if(ultima!=null){
			Calendar cal=Calendar.getInstance();
			cal.setTime(this.getToday() );
			cal.add(Calendar.DAY_OF_MONTH, -1);
			ultima.setHasta( new java.sql.Date(cal.getTimeInMillis()));
			oslpDAO.actualizar(ultima);
		}
		
		this.agregarAsociacion(editarDTO);
	}



	@Override
	public Collection<ObraSocialDTO> getObrasSocialesParaListar() throws HBDataAccessException 
	{
		try
		{
			Collection<ObraSocial> obrassociales = obraSocialDAO.getObrasSocialesParaListar();
			Iterator<ObraSocial> it = obrassociales.iterator();
			Collection<ObraSocialDTO> obrassocialesDTO = new ArrayList<ObraSocialDTO>();
			
			//Para lo que se lo utiliza, es mas performante que el mapper
			while( it.hasNext() )
			{
				ObraSocial os = it.next();
				
				ObraSocialDTO osDTO = new ObraSocialDTO();
				osDTO.setCodigo( os.getCodigo() );
				osDTO.setNombre( os.getNombre() );
				osDTO.setSigla( os.getSigla() );
				
				obrassocialesDTO.add(osDTO);
			}
			
			logger.info("Se leyo la lista de obras sociales para listar satisfactoriamente");
			
			return obrassocialesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de obras sociales para listar" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}



	@Override
	public AsociacionObraSocialListaPrecioDTO getAsociacionActual(Long codigo) {
		AsociacionObraSociaListaPrecio asociacion=oslpDAO.getAsociacionActual(codigo);
		AsociacionObraSocialListaPrecioDTO dto=null;
		if(asociacion!=null){
			dto=mapper.map( asociacion , AsociacionObraSocialListaPrecioDTO.class );
		}
		return dto;
	}



	@Override
	public void actualizarAsociaciones(List<AsociacionObraSocialListaPrecioDTO> lista, Long osid) throws ParseException {
		oslpDAO.borrarAsociaciones(osid);
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);

		Iterator<AsociacionObraSocialListaPrecioDTO> it=lista.iterator();
		while(it.hasNext()){
			AsociacionObraSocialListaPrecioDTO asoc=it.next();
			AsociacionObraSociaListaPrecio asocBD=new AsociacionObraSociaListaPrecio();
			asocBD.setListaPrecio(new ListaPrecio(asoc.getListaPrecio().getCodigo()));
			asocBD.setObraSocial(new ObraSocial(osid));
			Date desde=sdf.parse(asoc.getDesde());
			asocBD.setDesde(new java.sql.Date(desde.getTime()));
			if((asoc.getHasta()!=null)&&(asoc.getHasta().compareTo("")!=0)){
				Date hasta=sdf.parse(asoc.getHasta());
				asocBD.setHasta(new java.sql.Date(hasta.getTime()));	
			}else{
				asocBD.setHasta(null);
			}
			
			
			oslpDAO.agregar(asocBD);
			
		}
		
	}
	
	
	@Override
	public Collection<ObraSocialDTO> getObrasSocialesDetalladasParaListar() throws HBDataAccessException 
	{
		try
		{
			Collection<ObraSocial> obrassociales = obraSocialDAO.getObrasSocialesDetalladasParaListar();
			Iterator<ObraSocial> it = obrassociales.iterator();
			Collection<ObraSocialDTO> obrassocialesDTO = new ArrayList<ObraSocialDTO>();
			
			//Para lo que se lo utiliza, es mas performante que el mapper
			while( it.hasNext() )
			{
				ObraSocial os = it.next();
				
				ObraSocialDTO osDTO = new ObraSocialDTO();
				osDTO.setCodigo( os.getCodigo() );
				osDTO.setNombre( os.getNombre() );
				osDTO.setSigla( os.getSigla() );
				
				obrassocialesDTO.add(osDTO);
			}
			
			logger.info("Se leyo la lista de obras sociales para listar satisfactoriamente");
			
			return obrassocialesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de obras sociales para listar" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	



//	private boolean existeAsociacion(String desde, String hasta, Long codigolp,
//			Long codigoos) {
//		return (oslpDAO.existeAsociacion(desde,hasta,codigolp,codigoos));
//	}


}
