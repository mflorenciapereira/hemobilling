package ar.uba.fi.hemobillingHCService.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;
import ar.uba.fi.hemobillingHCService.dao.ObraSocialHCDAO;
import ar.uba.fi.hemobillingHCService.dao.PacienteHCDAO;
import ar.uba.fi.hemobillingHCService.domain.ObraSocialHC;
import ar.uba.fi.hemobillingHCService.domain.PacienteHC;
import ar.uba.fi.hemobillingHCService.service.PacienteHCService;

@Service("pacienteHCService")
public class PacienteHCServiceImpl implements PacienteHCService {

	@Resource(name = "pacienteHCDAO")
	private PacienteHCDAO pacienteDAO;
	
	@Resource(name = "obraSocialHCDAO")
	private ObraSocialHCDAO osHCDAO; 
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	private Logger logger = Logger.getLogger(PacienteHCServiceImpl.class);
	
	@Override
	public PacienteHCDTO getPaciente(Integer numHC) throws Exception
	{
		try 
		{
			logger.info("Se solicito obtener el paciente con numero de HC: " + numHC.toString() );
			
			PacienteHC paciente = pacienteDAO.getPaciente(numHC);
			
			if( paciente!=null )
			{
				logger.info("Se retorna el paciente con numero de HC: " + numHC.toString() );
				
				try
				{
					if( paciente.getObraSocial()==null )
						paciente.setObraSocial( "Paciente sin OS" );
					else
					{
						ObraSocialHC os = this.osHCDAO.getObraSocialHC( Integer.parseInt(paciente.getObraSocial()) );
						if( os!=null )
							paciente.setObraSocial( os.getNombre() );
						else
							paciente.setObraSocial( "Paciente sin OS" );
					}
				}
				
				catch (DataAccessException e) 
				{
					logger.error("Error de conexion a la BD al obtener la OS con numero: " + paciente.getObraSocial() , e );
					throw e;
				} 
				catch (ObjectNotFoundException e) 
				{
					logger.error("Error al obtener la OS con numero: " + paciente.getObraSocial() + ". No se la encontro." , e );
					return null;
				}
				catch (Exception e) 
				{
					logger.error("Error indeterminado al obtener la obra social con numero: " + paciente.getObraSocial() , e );
					throw e;
				}
				
				PacienteHCDTO dto = mapper.map(paciente, PacienteHCDTO.class );
				return dto;
			}
			else
			{
				logger.info("No se encontro ningun paciente con numero de HC: " + numHC.toString() );
				
				return null;
			}

		} 
		catch (DataAccessException e) 
		{
			logger.error("Error de conexion a la BD al obtener el paciente con numero de HC: " + numHC.toString() , e );
			throw e;
		} 
		catch (ObjectNotFoundException e) 
		{
			logger.error("Error al obtener el paciente con numero de HC: " + numHC.toString() + ". No se encontro el paciente." , e );
			return null;
		}
		catch (Exception e) 
		{
			logger.error("Error indeterminado al obtener el paciente con numero de HC: " + numHC.toString() , e );
			throw e;
		}

	}

}
