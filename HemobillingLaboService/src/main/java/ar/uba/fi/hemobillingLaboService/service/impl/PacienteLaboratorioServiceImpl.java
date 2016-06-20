package ar.uba.fi.hemobillingLaboService.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteLaboratorioDTO;
import ar.uba.fi.hemobillingLaboService.dao.OperacionesLaboratorioDAO;
import ar.uba.fi.hemobillingLaboService.dao.PacienteLaboratorioDAO;
import ar.uba.fi.hemobillingLaboService.domain.AnalisisLaboratorio;
import ar.uba.fi.hemobillingLaboService.domain.EstudioLaboratorio;
import ar.uba.fi.hemobillingLaboService.domain.FiltroConsultaAnalisisLaboratorio;
import ar.uba.fi.hemobillingLaboService.domain.PacienteLaboratorio;
import ar.uba.fi.hemobillingLaboService.service.PacienteLaboratorioService;

@Service("pacienteLaboratorioService")
public class PacienteLaboratorioServiceImpl implements PacienteLaboratorioService 
{
	private static final String FORMATO_DDMMAAAA = "dd/MM/yyyy";
	
	@Resource( name="pacienteLaboratorioDAO")
	private PacienteLaboratorioDAO pacientesDAO;
	
	@Resource( name="operacionesLaboratorioDAO")
	private OperacionesLaboratorioDAO operacionesDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	private Logger logger = Logger.getLogger(PacienteLaboratorioServiceImpl.class);
	
	@Override
	public PacienteLaboratorioDTO getPaciente( Integer ID ) throws Exception
	{
		try 
		{
			logger.info("Se solicito obtener el paciente con numero de HC: " + ID.toString() );
			
			PacienteLaboratorio paciente = pacientesDAO.getPaciente(ID);
			
			if( paciente!=null )
			{
				logger.info("Se retorna el paciente con numero de HC: " + ID.toString() );
				
				PacienteLaboratorioDTO dto = mapper.map(paciente, PacienteLaboratorioDTO.class );
				return dto;
			}
			else
			{
				logger.info("No se encontro ningun paciente con numero de HC: " + ID.toString() );
				
				return null;
			}
			
		} 
		catch (DataAccessException e) 
		{
			logger.error("Error de conexion a la BD al obtener el paciente con numero de HC: " + ID.toString() , e );
			throw e;
		} 
		catch (ObjectNotFoundException e) 
		{
			logger.error("Error al obtener el paciente con numero de HC: " + ID.toString() + ". No se encontro el paciente." , e );
			return null;
		}
		catch (Exception e) 
		{
			logger.error("Error indeterminado al obtener el paciente con numero de HC: " + ID.toString() , e );
			throw e;
		}
		

	}

	@Override
	public Boolean actualizarNumeroHistoriaClinica(Integer codigoInicial, Integer nuevoNumHC) throws Exception
	{
		try 
		{
			logger.info("Se solicito cambiar el numero de HC del paciente con codigo " + codigoInicial.toString() + " a " + nuevoNumHC.toString() );
			
			PacienteLaboratorio paciente = pacientesDAO.getPaciente(codigoInicial);
			paciente.setNumHistoriaClinica(nuevoNumHC);
			pacientesDAO.actualizar(paciente);
			
			logger.info("Se actualizo correctamente el numero de HC del paciente con codigo " + codigoInicial.toString() + " a " + nuevoNumHC.toString() );
			
			return true;
		} 
		catch (DataAccessException e) 
		{
			logger.error("Error de acceso a la base de datos al momento de querer cambiar el numero de HC del paciente con codigo " + codigoInicial.toString() , e );
			throw e;
		} 
		
		catch (ObjectNotFoundException e) 
		{
			logger.error("Error al momento de querer cambiar el numero de HC del paciente con codigo " + codigoInicial.toString() + ". No se encontro el paciente." , e );
			return false;
		}
		
		catch (Exception e) 
		{
			logger.error("Error indeterminado al querer cambiar el numero de HC del paciente con codigo " + codigoInicial.toString() , e );
			throw e;
		}

		
	}

	@Override
	public Collection<EstudioLaboratorioDTO> buscarAnalisisLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtroDTO ) throws Exception 
	{
		try 
		{
			logger.info("Se solicito buscar analisis clinicos" );
			
			FiltroConsultaAnalisisLaboratorio filtro = mapper.map(filtroDTO, FiltroConsultaAnalisisLaboratorio.class);
			Collection<AnalisisLaboratorio> listaAnalisis = operacionesDAO.buscar(filtro);
			Collection<EstudioLaboratorioDTO> listaAnalisisDTO = new ArrayList<EstudioLaboratorioDTO>();
			
			Iterator<AnalisisLaboratorio> it = listaAnalisis.iterator();
			while( it.hasNext() )
			{
				AnalisisLaboratorio analisis = it.next();
				
				Iterator<EstudioLaboratorio> itEstudio = analisis.getResultados().iterator();
				while( itEstudio.hasNext() )
				{
					EstudioLaboratorio estudio = itEstudio.next();
					
					EstudioLaboratorioDTO estudioDTO = new EstudioLaboratorioDTO();
					
					estudioDTO.setIdEstudio( estudio.getTipoEstudio());
					estudioDTO.setResultado( estudio.getResultado() );
					estudioDTO.setNroHC( analisis.getNroHC() );
					estudioDTO.setId(estudio.getIdEstudio());
					
					SimpleDateFormat formatoFecha = new SimpleDateFormat( FORMATO_DDMMAAAA );
					estudioDTO.setFechaRealizacion( formatoFecha.format( analisis.getFechaRealizacion() ) );
					
					listaAnalisisDTO.add(estudioDTO);
				}
			}

			logger.info("Se retorna los resultados de la busqueda de analisis clinicos" );
			
			return listaAnalisisDTO;
		}
		catch (DataAccessException e) 
		{
			logger.error("Error de acceso a la base de datos al momento de querer buscar analisis clinicos" , e );
			throw e;
		} 
		catch (ObjectNotFoundException e) 
		{
			logger.error("No se encontraron resultados para la busqueda de analisis clinicos", e );
			return null;
		}
	}
	
}
