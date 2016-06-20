package ar.uba.fi.hemobillingHCService.remote;

import java.rmi.RemoteException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;
import ar.uba.fi.hemobilling.commons.remoteInterfaces.HemoBillingHCRemoteInterface;
import ar.uba.fi.hemobillingHCService.service.PacienteHCService;

@Component("hemoBillingHCServer")
public class HemoBillingHCServer implements HemoBillingHCRemoteInterface{

	@Resource( name="pacienteHCService")
	private PacienteHCService pacienteService;
	
	private Logger logger = Logger.getLogger(HemoBillingHCServer.class);
	
	@Override
	public PacienteHCDTO getPacienteHC(Integer ID) throws RemoteException 
	{
		try
		{
			logger.info("Llega solicitud para obtener un paciente");
			
			PacienteHCDTO paciente = pacienteService.getPaciente(ID);
			
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

}
