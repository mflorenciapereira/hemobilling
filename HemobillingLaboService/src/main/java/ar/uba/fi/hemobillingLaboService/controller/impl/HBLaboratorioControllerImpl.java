package ar.uba.fi.hemobillingLaboService.controller.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.remoteInterfaces.HBLaboratorioRemoteInterface;
import ar.uba.fi.hemobilling.commons.rmi.PolicyFileLocator;
import ar.uba.fi.hemobillingLaboService.controller.HBLaboratorioController;
import ar.uba.fi.hemobillingLaboService.remote.HBLaboratorioServer;

@Service("hemoBillingServiceController")
public class HBLaboratorioControllerImpl implements HBLaboratorioController {

	@Resource( name = "HBLaboratorioServer")
	private HBLaboratorioServer server;

	private Logger logger = Logger.getLogger(HBLaboratorioControllerImpl.class);

	@Override
	public void iniciar() throws RemoteException 
	{
		logger.info("Inicio el Controlador.");

        System.setProperty("java.rmi.server.codebase", HBLaboratorioRemoteInterface.class.getProtectionDomain().getCodeSource().getLocation().toString());
        System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        String name = "HBLaboratorioServer";
        HBLaboratorioRemoteInterface stub = (HBLaboratorioRemoteInterface) UnicastRemoteObject.exportObject(server, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind(name, stub);
            
    	logger.info("Sistema RMI montado. Esperando clientes");		
	}
	
}
