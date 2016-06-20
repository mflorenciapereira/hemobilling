package ar.uba.fi.hemobilling.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.ObraSocialDAO;
import ar.uba.fi.hemobilling.dao.PacienteDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.domain.paciente.FiltroConsultaPacientes;
import ar.uba.fi.hemobilling.domain.paciente.Paciente;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.dto.paciente.FiltroConsultaPacienteDTO;
import ar.uba.fi.hemobilling.dto.paciente.PacienteDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.service.PacienteService;
import ar.uba.fi.hemobilling.util.HTMLEntities;

@Service("pacienteService")
public class PacienteServiceImpl implements PacienteService 
{
	@Resource(name = "pacienteDAO")
	private PacienteDAO pacienteDAO;
	
	@Resource(name = "obraSocialDAO")
	private ObraSocialDAO osDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	private static Logger logger = Logger.getLogger(PacienteServiceImpl.class);

	@Override
	public PacienteDTO getPaciente(Long id) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try 
		{
			Paciente paciente = pacienteDAO.obtener( id );
			PacienteDTO pacienteDTO = mapper.map(paciente, PacienteDTO.class );
			
			pacienteDTO.setDireccion( HTMLEntities.htmlentities(pacienteDTO.getDireccion()));
			pacienteDTO.setLocalidad( HTMLEntities.htmlentities(pacienteDTO.getLocalidad() ));
			pacienteDTO.setNombreyApellido( HTMLEntities.htmlentities(pacienteDTO.getNombreyApellido() ));
			pacienteDTO.setPais( HTMLEntities.htmlentities(pacienteDTO.getPais() ));
			pacienteDTO.setProvincia( HTMLEntities.htmlentities(pacienteDTO.getProvincia() ));			
			
			Iterator<ObraSocial> it=paciente.getObrasSocialesAdheridas().iterator();
			while(it.hasNext()){
				ObraSocial osAdherida=it.next();
				ObraSocialDTO osAdheridaDTO=mapper.map(osAdherida, ObraSocialDTO.class);;
				pacienteDTO.getObrasSocialesAdheridasAuto().add(osAdheridaDTO);
			}
			
			logger.info("Se obtuvo el paciente con DNI: "+id.toString()+" satisfactoriamente");
			
			return pacienteDTO;
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener el paciente con DNI: "+id.toString(), e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el paciente con numero de DNI: "+id.toString() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
		
	}

	@Override
	public void agregarPaciente(PacienteDTO nuevoPaciente) throws HBDataAccessException, HBObjectExistsException,HBServiceException 
	{
		try 
		{
			Paciente paciente = mapper.map(nuevoPaciente, Paciente.class );
			
			@SuppressWarnings("unchecked")
			Iterator<ObraSocialDTO> it = (Iterator<ObraSocialDTO>)nuevoPaciente.getObrasSocialesAdheridasAuto().iterator();
			Collection<ObraSocial> oss = new ArrayList<ObraSocial>();
			
			while( it.hasNext() )
			{
				ObraSocialDTO osDTO = it.next();
				ObraSocial os = null;
				
				try 
				{
					os = osDAO.getObraSocial( osDTO.getCodigo() );
				} 
				catch (ObjectNotFoundException e) {}
				
				oss.add( os );
			}
			
			paciente.setObrasSocialesAdheridas(oss);
			pacienteDAO.agregar(paciente);
			
			logger.info("Se agrego el paciente con DNI "+paciente.getNroDNI()+" satisfactoriamente");
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria agregar el paciente con DNI "+nuevoPaciente.getNroDNI(), e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectFoundException e) {
			logger.error("Se encontro un paciente que ya existia en la BD cuando se queria agregar el paciente con DNI "+nuevoPaciente.getNroDNI() , e );
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		}
		
	}

	@Override
	public void eliminarPaciente(PacienteDTO paciente) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation
	{
		Paciente pacienteEliminar = mapper.map(paciente, Paciente.class );
		
		@SuppressWarnings("unchecked")
		Iterator<ObraSocialDTO> it = (Iterator<ObraSocialDTO>)paciente.getObrasSocialesAdheridasAuto().iterator();
		Collection<ObraSocial> oss = new ArrayList<ObraSocial>();
		
		while( it.hasNext() )
		{
			ObraSocialDTO osDTO = it.next();
			ObraSocial os = null;
			
			try 
			{
				os = osDAO.getObraSocial( osDTO.getCodigo() );
			} 
			catch (ObjectNotFoundException e) {}
			
			oss.add( os );
		}
		
		pacienteEliminar.setObrasSocialesAdheridas(oss);
		
		
		try {
			pacienteDAO.eliminar(pacienteEliminar);
			logger.info("Se elimino el paciente con DNI "+pacienteEliminar.getNroDNI()+" satisfactoriamente");
		} 
		catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar al paciente con DNI " +pacienteEliminar.getNroDNI(), e );
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
			logger.error("No se encontro el paciente en la BD cuando se queria eliminar el paciente con DNI "+pacienteEliminar.getNroDNI() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
		
	}

	@Override
	public void actualizarPaciente(PacienteDTO paciente) throws HBDataAccessException, HBObjectNotExistsException,HBServiceException 
	{
		try 
		{
			Paciente pacienteActualizado = mapper.map(paciente, Paciente.class );
			
			@SuppressWarnings("unchecked")
			Iterator<ObraSocialDTO> it = (Iterator<ObraSocialDTO>)paciente.getObrasSocialesAdheridasAuto().iterator();
			Collection<ObraSocial> oss = new ArrayList<ObraSocial>();
			Integer contador = 0;
			Integer max = paciente.getCantObrasSocialesAdheridas();
			
			while( it.hasNext() )
			{
				if( max==null || contador<max  )
				{
					ObraSocialDTO osDTO = it.next();
					ObraSocial os = null;
					
					try 
					{
						os = osDAO.getObraSocial( osDTO.getCodigo() );
					} 
					catch (ObjectNotFoundException e) {}
					
					oss.add( os );
					contador++;
				}
				else
					it.next();
			}
			
			pacienteActualizado.setObrasSocialesAdheridas(oss);
			
			
			pacienteDAO.actualizar(pacienteActualizado);
			logger.info("Se actualizo el paciente con DNI "+paciente.getNroDNI()+" satisfactoriamente");
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria actualizar el paciente con DNI "+paciente.getNroDNI() , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el paciente en la BD cuando se queria actualizar el paciente con DNI "+paciente.getNroDNI() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		} 
		
	}

	@Override
	public Collection<PacienteDTO> getPacientes( FiltroConsultaPacienteDTO filtroDTO, FiltroPaginadoDTO filtroPaginadoDTO ) throws HBDataAccessException 
	{
		try
		{
			FiltroConsultaPacientes filtro = mapper.map(filtroDTO, FiltroConsultaPacientes.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO, FiltroPaginado.class);
			
			filtroPaginado.setCantTotalRegs( pacienteDAO.getCantidadConsulta(filtro) );
			
			Collection<Paciente> pacientes = pacienteDAO.consultar(filtro,filtroPaginado);
			Collection<PacienteDTO> pacientesDTO = mapper.map( pacientes , PacienteDTO.class );
			
			logger.info("Se realizo la consulta de pacientes satisfactoriamente");
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs( filtroPaginado.getCantTotalRegs() );
			return pacientesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria realizar una consulta de pacientes" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public PacienteDTO getPacientePorNumeroHC(Long numHC) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try 
		{
			Paciente paciente = pacienteDAO.obtenerPorNumeroHC(numHC);
			PacienteDTO pacienteDTO = mapper.map(paciente, PacienteDTO.class );
			
			Iterator<ObraSocial> it=paciente.getObrasSocialesAdheridas().iterator();
			while(it.hasNext()){
				ObraSocial osAdherida=it.next();
				ObraSocialDTO osAdheridaDTO=mapper.map(osAdherida, ObraSocialDTO.class);;
				pacienteDTO.getObrasSocialesAdheridasAuto().add(osAdheridaDTO);
			}
			
			logger.info("Se obtuvo el paciente con numero de HC: "+numHC+" satisfactoriamente");
			
			return pacienteDTO;
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener el paciente con numero de HC: "+numHC, e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el paciente con numero de HC: "+numHC, e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}
	
	@Override
	public PacienteDTO getPacientePorNumeroHCParaListar(Long numHC) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try 
		{
			Paciente paciente = pacienteDAO.obtenerPorNumeroHCParaListar(numHC);
			PacienteDTO pacienteDTO = mapper.map(paciente, PacienteDTO.class );
			
			logger.info("Se obtuvo el paciente con numero de HC: "+numHC+" para listar satisfactoriamente");
			
			return pacienteDTO;
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener el paciente para listar con numero de HC: "+numHC, e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el paciente para listar con numero de HC: "+numHC, e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}
	
	
	@Override
	public Collection<PacienteDTO> getPacientesParaListar() throws HBDataAccessException 
	{
		try
		{			
			Collection<Paciente> pacientes = pacienteDAO.getPacientesParaListar();
			Collection<PacienteDTO> pacientesDTO = new ArrayList<PacienteDTO>();
			
			/* Solo por cuestiones de performance. Esto es mas rapido que Dozer */
			Iterator<Paciente> it = pacientes.iterator();
			while( it.hasNext() )
			{
				Paciente paciente = it.next();
				PacienteDTO pacienteDTO = new PacienteDTO();
				
				pacienteDTO.setId( paciente.getId().toString() );
				pacienteDTO.setNombreyApellido(paciente.getNombreyApellido());
				pacienteDTO.setNumHistoriaClinica( paciente.getNumHistoriaClinica().toString() );
				
				pacientesDTO.add(pacienteDTO);
			}
						
			logger.info("Se realizo la consulta de pacientes minima satisfactoriamente");
			
			return pacientesDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria realizar una consulta de pacientes" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}


}
