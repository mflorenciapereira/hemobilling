package ar.uba.fi.hemobillingLaboService.remote;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.remoteInterfaces.HBLaboratorioRemoteInterface;
import ar.uba.fi.hemobillingLaboService.service.PacienteLaboratorioService;

@Component("HBLaboratorioServer")
public class HBLaboratorioServer implements HBLaboratorioRemoteInterface 
{
	@Resource( name="pacienteLaboratorioService")
	private PacienteLaboratorioService pacientesService;
	
	private Logger logger = Logger.getLogger(HBLaboratorioServer.class);
	
	@Override
	public PacienteLaboratorioDTO getPacienteLaboratorio( Integer ID ) throws RemoteException
	{
		try
		{
			logger.info("Llega solicitud para obtener un paciente");
			
			PacienteLaboratorioDTO paciente = pacientesService.getPaciente(ID);
			
			logger.info("Se responde solicitud para obtener un paciente");
			
			return paciente;
		}
		catch( Exception e )
		{
			RemoteException re = new RemoteException("Excpecion al querer obtener un paciente" , e );
			logger.error("Se produjo una Excepcion al momento de querer leer un paciente. Se devuelve Remote Excepcion");
			throw re;
		}
	}
	
	public Boolean actualizarNumeroHistoriaClinica( Integer codigoViejo , Integer nuevoNumHC ) throws RemoteException
	{
		try
		{
			logger.info("Llega solicitud para actualizar un codigo viejo a un numero de HC");
			
			Boolean retorno = this.pacientesService.actualizarNumeroHistoriaClinica(codigoViejo, nuevoNumHC);
			
			logger.info("Se responde solicitud para actualizar un codigo viejo a un numero de HC");
	
			return retorno;
		
		}
		catch( Exception e )
		{
			RemoteException re = new RemoteException("Excepcion al querer actualizar el numero de HC de un paciente" , e );
			logger.error("Se produjo una Excepcion al querer actualizar el numero de HC de un paciente. Se devuelve Remote Excepcion");
			throw re;
		}
	}

	@Override
	public Collection<EstudioLaboratorioDTO> buscarAnalisisLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtro) throws RemoteException 
	{
		try
		{
			logger.info("Llega solicitud para buscar analisis de laboratorio");
			
			Collection<EstudioLaboratorioDTO> retorno = this.pacientesService.buscarAnalisisLaboratorio(filtro);
			
			logger.info("Se responde solicitud para buscar analisis de laboratorio");
	
			return retorno;
		
		}
		catch( Exception e )
		{
			RemoteException re = new RemoteException("Excepcion al querer buscar analisis de laboratorio" , e );
			logger.error("Excepcion al querer buscar analisis de laboratorio. Se devuelve Remote Excepcion");
			throw re;
		}
	}

}
