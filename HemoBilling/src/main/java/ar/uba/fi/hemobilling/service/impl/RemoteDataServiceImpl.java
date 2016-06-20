package ar.uba.fi.hemobilling.service.impl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.remoteInterfaces.HBLaboratorioRemoteInterface;
import ar.uba.fi.hemobilling.commons.remoteInterfaces.HemoBillingHCRemoteInterface;
import ar.uba.fi.hemobilling.commons.rmi.PolicyFileLocator;
import ar.uba.fi.hemobilling.exception.domain.HBRemoteException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.service.RemoteDataService;
import ar.uba.fi.hemobilling.util.PropertiesReader;

@Service("remoteDataService")
public class RemoteDataServiceImpl implements RemoteDataService 
{
	private static final String IP_SISTEMA_LABO = "remote.config.sistemaLaboratorioDireccion";
	private static final String COD_NOMBRE_SISTEMA_LABO = "remote.config.sistemaLaboratorioName";
	
	private static final String IP_SISTEMA_HC = "remote.config.sistemaHCDireccion";
	private static final String COD_NOMBRE_SISTEMA_HC = "remote.config.sistemaHCName";
	
	private static Logger logger = Logger.getLogger(RemoteDataServiceImpl.class);
	
	@Resource(name = "messageSupport")
	protected PropertiesReader messageSupport;

	private void configurarPermisos( Class<? extends Remote> clase )
	{
		System.setProperty("java.rmi.server.codebase", clase.getProtectionDomain().getCodeSource().getLocation().toString());
		System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
		System.setProperty("sun.rmi.transport.connectionTimeout","100000");
		System.setProperty("sun.rmi.transport.proxy.connectTimeout","100000");
	        
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
	}
	
	@Override
	public PacienteLaboratorioDTO getInformacionPacienteSistemaLaboratorio(String nroHC) throws HBRemoteException, HBServiceException 
	{
		try 
		{
			logger.info("Llega solicitud de obtener informacion de paciente en el sistema de laboratorio");
			
			configurarPermisos( HBLaboratorioRemoteInterface.class );
			
			String name =  messageSupport.getProperty(COD_NOMBRE_SISTEMA_LABO);
			String ip =  messageSupport.getProperty(IP_SISTEMA_LABO);
			
			Registry registry = LocateRegistry.getRegistry( ip );
			logger.info("El registry es: "+registry);
			logger.info("list del registry es:"+ registry.list());
			logger.info("to string del registry es: "+registry.toString());
			logger.info("registry port es:"+registry.REGISTRY_PORT);
            HBLaboratorioRemoteInterface HBLabo = (HBLaboratorioRemoteInterface) registry.lookup(name);
            
            PacienteLaboratorioDTO paciente = HBLabo.getPacienteLaboratorio( Integer.parseInt(nroHC) );
            
            logger.info("Se devuelve la informacion del paciente en el sistema de laboratorio exitosamente");
            
            return paciente;
		}

		catch( RemoteException re )
		{
			logger.error("Se produjo una excepcion al momento de obtener el paciente. Posible problema de red" , re);
			HBRemoteException hbrex = new HBRemoteException(re);
			throw( hbrex );
		}
	            
		catch( Exception e )
		{
			logger.error("Se produjo una excepcion al momento de obtener el paciente. Problema desconocido en el servicio" , e);
			HBServiceException hbsex = new HBServiceException(e);
			throw( hbsex );
		}
	}
	
	
	

	@Override
	public PacienteHCDTO getInformacionPacienteSistemaHC(String nroHC) throws HBRemoteException, HBServiceException 
	{
		try 
		{
			logger.info("Llega solicitud de obtener informacion de paciente en el sistema de HC");
			
			configurarPermisos( HemoBillingHCRemoteInterface.class );
			
			String name =  messageSupport.getProperty(COD_NOMBRE_SISTEMA_HC);
			String ip =  messageSupport.getProperty(IP_SISTEMA_HC);
			
			Registry registry = LocateRegistry.getRegistry( ip );
			HemoBillingHCRemoteInterface HBLabo = (HemoBillingHCRemoteInterface) registry.lookup(name);
            
            PacienteHCDTO paciente = HBLabo.getPacienteHC( Integer.parseInt(nroHC) );
            
            logger.info("Se devuelve la informacion del paciente en el sistema de HC exitosamente");
            
            return paciente;
		}

		catch( RemoteException re )
		{
			logger.error("Se produjo una excepcion al momento de obtener el paciente. Posible problema de red" , re);
			HBRemoteException hbrex = new HBRemoteException(re);
			throw( hbrex );
		}
	            
		catch( Exception e )
		{
			logger.error("Se produjo una excepcion al momento de obtener el paciente. Problema desconocido en el servicio" , e);
			HBServiceException hbsex = new HBServiceException(e);
			throw( hbsex );
		}
	            
	}

	@Override
	public Boolean cambiarIDLaboratorioPorNumHC(String nroHC, String idSistemaLaboratorio) throws HBRemoteException, HBServiceException 
	{
		try 
		{
			logger.info("Llega solicitud de cambiar el ID de Paciente del Sistema de Laboratorio por el numero de HC");
			
			configurarPermisos( HBLaboratorioRemoteInterface.class );
			
			String name =  messageSupport.getProperty(COD_NOMBRE_SISTEMA_LABO);
			String ip =  messageSupport.getProperty(IP_SISTEMA_LABO);
			
			Registry registry = LocateRegistry.getRegistry( ip );
            HBLaboratorioRemoteInterface HBLabo = (HBLaboratorioRemoteInterface) registry.lookup(name);
            
            Boolean respuesta = HBLabo.actualizarNumeroHistoriaClinica( Integer.parseInt(idSistemaLaboratorio) ,   
            															Integer.parseInt(nroHC) );
            
            logger.info("Se devuelve la respuesta de la accion");
            
            return respuesta;
		}

		catch( RemoteException re )
		{
			logger.error("Se produjo una excepcion al momento de cambiar el ID. Posible problema de red" , re);
			HBRemoteException hbrex = new HBRemoteException(re);
			throw( hbrex );
		}
	            
		catch( Exception e )
		{
			logger.error("Se produjo una excepcion al momento de cambiar el ID. Problema desconocido en el servicio" , e);
			HBServiceException hbsex = new HBServiceException(e);
			throw( hbsex );
		}
	}

	@Override
	public Collection<EstudioLaboratorioDTO> buscarAnalisisLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtroDTO ) throws HBRemoteException, HBServiceException 
	{
		try 
		{
			logger.info("Llega solicitud de cambiar el ID de Paciente del Sistema de Laboratorio por el numero de HC");
			
			configurarPermisos( HBLaboratorioRemoteInterface.class );
			
			String name =  messageSupport.getProperty(COD_NOMBRE_SISTEMA_LABO);
			String ip =  messageSupport.getProperty(IP_SISTEMA_LABO);
			
			Registry registry = LocateRegistry.getRegistry( ip );
            HBLaboratorioRemoteInterface HBLabo = (HBLaboratorioRemoteInterface) registry.lookup(name);

            Collection<EstudioLaboratorioDTO> respuesta = HBLabo.buscarAnalisisLaboratorio(filtroDTO);
            
            logger.info("Se devuelve la respuesta de la accion");
            
            return respuesta;
		}

		catch( RemoteException re )
		{
			logger.error("Se produjo una excepcion al momento de cambiar el ID. Posible problema de red" , re);
			HBRemoteException hbrex = new HBRemoteException(re);
			throw( hbrex );
		}
	            
		catch( Exception e )
		{
			logger.error("Se produjo una excepcion al momento de cambiar el ID. Problema desconocido en el servicio",e );
			HBServiceException hbsex = new HBServiceException(e);
			throw( hbsex );
		}
	}

}
