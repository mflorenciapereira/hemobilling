package ar.uba.fi.hemobilling.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.dao.PrestacionBrindadaDAO;
import ar.uba.fi.hemobilling.dao.PrestacionDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.paciente.Paciente;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.DatosAdicionalesEstudioLaboratorioImportado;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.FiltroConsultaPrestacionesBrindadas;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.Observacion;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.PrestacionBrindada;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.FiltroConsultaPrestacionBrindadaDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.PrestacionBrindadaDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.ResultConsultaAnalisisLaboratorioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBPrestacionSinPrecioException;
import ar.uba.fi.hemobilling.exception.domain.HBRemoteException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.service.PrestacionBrindadaService;
import ar.uba.fi.hemobilling.service.RemoteDataService;
import ar.uba.fi.hemobilling.util.HTMLEntities;
import ar.uba.fi.hemobilling.util.PropertiesReader;

@Service("prestacionBrindadaService")
public class PrestacionBrindadaServiceImpl implements PrestacionBrindadaService 
{
	

	@Resource(name = "prestacionBrindadaDAO")
	private PrestacionBrindadaDAO prestacionBrindadaDAO;
	
	@Resource(name = "prestacionDAO")
	private PrestacionDAO prestacionDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;

	@Resource( name="remoteDataService" )
	private RemoteDataService rds;
	
	@Resource(name = "messageSupport")
	protected PropertiesReader messageSupport;
	
	private static Logger logger = Logger.getLogger(PrestacionBrindadaServiceImpl.class);
	
	@Override
	public PrestacionBrindadaDTO get(Long id) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try 
		{
			PrestacionBrindada pb = prestacionBrindadaDAO.obtener(id);
			PrestacionBrindadaDTO pbdto = mapper.map(pb, PrestacionBrindadaDTO.class );
			
			logger.info("Se obtuvo la prestacion brindada con codigo: "+id.toString()+" satisfactoriamente");
			
			return pbdto;
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener la prestacion brindada con codigo: "+id.toString(), e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la prestacion brindada con codigo: "+id.toString() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public void agregar(PrestacionBrindadaDTO nuevaPrestacionBrindada) throws HBDataAccessException, HBObjectExistsException,HBServiceException, HBPrestacionSinPrecioException
	{
		try 
		{
			PrestacionBrindada pb = mapper.map(nuevaPrestacionBrindada, PrestacionBrindada.class );
			
//			if( !prestacionBrindadaDAO.tienePrecioAsignadoPBParaOSEnFecha(pb) && pb.getPrecioManual()==null )
//			{
//				throw new HBPrestacionSinPrecioException();
//			}
			prestacionBrindadaDAO.agregar(pb);
			prestacionBrindadaDAO.agregarObservaciones(pb.getObservaciones());
	
			logger.info("Se agrego la prestacion brindada con codigo: "+ nuevaPrestacionBrindada.getCodigo()+ " satisfactoriamente");
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria agregar la prestacion brindada con codigo: "+ nuevaPrestacionBrindada.getCodigo(), e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectFoundException e) {
			logger.error("Se encontro una prestacion brindada que ya existia en la BD cuando se queria agregar la prestacion brindada con codigo: "+ nuevaPrestacionBrindada.getCodigo() , e );
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		}
		
	}

	@Override
	public void eliminar(PrestacionBrindadaDTO prestacionBrindada) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation
	{
		try 
		{
			PrestacionBrindada pb = mapper.map(prestacionBrindada, PrestacionBrindada.class );
			prestacionBrindadaDAO.eliminar(pb);
			
			logger.info("Se elimino la prestacion brindada con codigo: "+ prestacionBrindada.getCodigo()+" satisfactoriamente");
		} 
		catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar la prestacion brindada con codigo: "+ prestacionBrindada.getCodigo(), e );
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
		catch (ObjectNotFoundException e) 
		{
			logger.error("No se encontro la prestacion brindada en la BD cuando se queria eliminar la prestacion brindada con codigo: "+ prestacionBrindada.getCodigo() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
		
	}

	@Override
	public void actualizar(PrestacionBrindadaDTO prestacionBrindada) throws HBDataAccessException, HBObjectNotExistsException, 	HBServiceException, HBPrestacionSinPrecioException
	{
		try 
		{
			PrestacionBrindada pb = mapper.map(prestacionBrindada, PrestacionBrindada.class );
			
			if( !prestacionBrindadaDAO.tienePrecioAsignadoPBParaOSEnFecha(pb) && pb.getPrecioManual()==null )
			{
				throw new HBPrestacionSinPrecioException();
			}
			
			prestacionBrindadaDAO.actualizar(pb);
			prestacionBrindadaDAO.agregarObservaciones(pb.getObservaciones());
			
			logger.info("Se elimino la prestacion brindada con codigo: "+ prestacionBrindada.getCodigo()+" satisfactoriamente");
		} 
		catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria actualizar la prestacion brindada con codigo: "+ prestacionBrindada.getCodigo(), e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) 
		{
			logger.error("No se encontro la prestacion brindada en la BD cuando se queria actualizar la prestacion brindada con codigo: "+ prestacionBrindada.getCodigo() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
		
	}

	@Override
	public Collection<PrestacionBrindadaDTO> consultar(FiltroConsultaPrestacionBrindadaDTO filtroDTO , FiltroPaginadoDTO filtroPaginadoDTO ) throws HBDataAccessException 
	{		
		try
		{
			FiltroConsultaPrestacionesBrindadas filtro = mapper.map(filtroDTO, FiltroConsultaPrestacionesBrindadas.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO, FiltroPaginado.class);
			
			filtroPaginado.setCantTotalRegs( prestacionBrindadaDAO.getCantidadConsulta(filtro) );
			
			Collection<PrestacionBrindada> prestaciones = prestacionBrindadaDAO.consultar(filtro,filtroPaginado);
			Collection<PrestacionBrindadaDTO> prestacionesDTO = mapper.map( prestaciones , PrestacionBrindadaDTO.class );
			
			logger.info("Se realizo la consulta de prestaciones brindadas satisfactoriamente");
			
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs( filtroPaginado.getCantTotalRegs() );
			return prestacionesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria realizar una consulta de prestaciones brindadas" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}
	
	private PrestacionBrindada getPrestacionBrindada( EstudioLaboratorioDTO estudio )
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		PrestacionBrindada pb = new PrestacionBrindada();
		
		try {
			pb.setFecha( sdf.parse( estudio.getFechaRealizacion() )  );
		}
		catch (ParseException e) {
			pb.setFecha( Calendar.getInstance().getTime()  );
		}
		
		pb.setObservaciones( estudio.getResultado() );
		
		Paciente pac = new Paciente();
		pac.setNumHistoriaClinica( estudio.getNroHC().longValue() );
		pb.setPaciente(pac);
		
		Prestacion pres = new Prestacion();
		pres.setCodigo(estudio.getIdEstudio().longValue() );
		pb.setPrestacion(pres);
		
		return pb;
	}
	
	public ResultConsultaAnalisisLaboratorioDTO importarDesdeLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtro ) throws HBServiceException, HBDataAccessException, HBRemoteException
	{
		logger.info("Se solicita importar estudios de laboratorio" );
		
		ResultConsultaAnalisisLaboratorioDTO resultado = new ResultConsultaAnalisisLaboratorioDTO();
		Collection<EstudioLaboratorioDTO> estudios = rds.buscarAnalisisLaboratorio(filtro);
		
		
		
		if( estudios!=null )
		{
			for( EstudioLaboratorioDTO estudio: estudios )
			{
				//Removi los mappers por cuestiones de performance
				
				PrestacionBrindadaDTO pbDTO = new PrestacionBrindadaDTO();
				pbDTO.setFecha( estudio.getFechaRealizacion() );
				pbDTO.setObservaciones( estudio.getResultado() );
				pbDTO.setNroHCPaciente( estudio.getNroHC().toString() );
				pbDTO.setCodPrestacion( estudio.getIdEstudio().toString() );
				pbDTO.setCodigoEnLaboratorio(estudio.getId().longValue());
				
				//Valido si la prestacion existe 
				try
				{
					PrestacionBrindada pb = getPrestacionBrindada(estudio );
					
					DatosAdicionalesEstudioLaboratorioImportado datos = prestacionBrindadaDAO.getDatosAdicionalesEstudioImportado(pb);
					
					pbDTO.setNombrePrestacion( HTMLEntities.htmlentities( datos.getDescripcion() ) );
					pbDTO.setIdPaciente( datos.getId().toString() );
					pbDTO.setNombrePaciente( HTMLEntities.htmlentities( datos.getNombreyApellido()) );
					pbDTO.setCantidadDePrestaciones("1");	
					
					resultado.getAnalisisAprobados().add( pbDTO );

				}
				catch (ObjectNotFoundException e) 
				{
					resultado.getAnalisisRechazados().add(estudio);
				}
				catch (DataAccessException e) 
				{
					logger.error("Se produjo un problema de acceso a la base de datos cuando se importaban estudios de laboratorio" , e );
					HBDataAccessException ex = new HBDataAccessException(e);
					throw ex;
				}
			}
		}

		logger.info("Se devuelve el resultado de la importacion de estudios de laboratorio" );
		
		Set<PrestacionBrindadaDTO> modulos = agregarModulos(resultado);
		resultado.getAnalisisAprobados().addAll(modulos);
		
		
		return resultado;
	}

	private EstudioLaboratorioDTO crearEstudio(String fecha, int codPrestacion, int hc) {
		EstudioLaboratorioDTO estudioDTO = new EstudioLaboratorioDTO();
		estudioDTO.setFechaRealizacion(fecha);
		estudioDTO.setIdEstudio(codPrestacion);
		estudioDTO.setNroHC(hc);
		estudioDTO.setResultado("1");
		estudioDTO.setId(1);
		return estudioDTO;
	}
	
	private Set<PrestacionBrindadaDTO> agregarModulos(ResultConsultaAnalisisLaboratorioDTO resultado) {
		Set<PrestacionBrindadaDTO> set = new HashSet<PrestacionBrindadaDTO>();
		Iterator it = resultado.getAnalisisAprobados().iterator();
		while(it.hasNext()){
			PrestacionBrindadaDTO estudio = (PrestacionBrindadaDTO) it.next();
			Collection<Prestacion> modulos = prestacionDAO.obtenerModulosAsociados(Long.valueOf(estudio.getCodPrestacion()));
			for(Prestacion modulo : modulos){
				PrestacionBrindadaDTO pbdto = new PrestacionBrindadaDTO();
				pbdto.setCodPrestacion(modulo.getCodigo().toString());
				pbdto.setNombrePrestacion(modulo.getDescripcion());
				pbdto.setCantidadDePrestaciones("1");

				pbdto.setFecha(estudio.getFecha());
				pbdto.setFechaImportacion(estudio.getFechaImportacion());
				pbdto.setIdPaciente(estudio.getIdPaciente());
				pbdto.setNombrePaciente(estudio.getNombrePaciente());
				pbdto.setNroHCPaciente(estudio.getNroHCPaciente());
				pbdto.setObservaciones(estudio.getObservaciones());
				pbdto.setModuloAgregado(true);
				
				set.add(pbdto);
			}
			
		}
		
		return set;
		
	}

	@SuppressWarnings("unchecked")
	public void guardarPrestacionesDeLaboratorioAceptadas( ResultConsultaAnalisisLaboratorioDTO resultAceptado ) throws HBDataAccessException
	{
		Iterator<PrestacionBrindadaDTO> iterador = (Iterator<PrestacionBrindadaDTO>)resultAceptado.getAnalisisAprobados().iterator();
		Date hoy = Calendar.getInstance().getTime();
		
		try 
		{
			while( iterador.hasNext() )
			{
				PrestacionBrindadaDTO prestacionDTO = iterador.next();
				
				if( prestacionDTO!=null )
				{
					PrestacionBrindada prestacion = mapper.map( prestacionDTO , PrestacionBrindada.class );
					prestacion.setCantidadDePrestaciones( new BigInteger("1") );
					
					try
					{
						prestacion.setObservaciones(OBSERVACION_ANALISIS_LABORATORIO);
						prestacion.setFechaImportacion(hoy);
						prestacionBrindadaDAO.agregar( prestacion );
					}
					catch (ObjectFoundException e) 
					{
						//Esto nunca va a suceder, porque la clave es autoincrement
					}	
				}
			}
		} 
		catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria realizar almacenar una prestacion importada de laboratorio" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
	}

	@Override
	public Collection<Observacion> getObservaciones() {
		return prestacionBrindadaDAO.getObservaciones();
	}

	

	@Override
	public ResultConsultaAnalisisLaboratorioDTO importarDesdeLaboratorioOmitirImportadas(
			FiltroConsultaEstudiosLaboratorioDTO filtroConsultaDTO) throws HBServiceException, HBDataAccessException, HBRemoteException {
		List<Object[]> yaImportados = prestacionBrindadaDAO.getCodigosYaImportados(filtroConsultaDTO.getFechaInicio(), filtroConsultaDTO.getFechaFinal());
		yaImportados.contains(4L);
		ResultConsultaAnalisisLaboratorioDTO resultado = this.importarDesdeLaboratorio(filtroConsultaDTO);
		
		Iterator<PrestacionBrindadaDTO> it = resultado.getAnalisisAprobados().iterator();
		while(it.hasNext()){
			if(yaImportados.contains(it.next().getCodigoEnLaboratorio()))
				it.remove();
		}
		
		return resultado;
	
	}

	
}
