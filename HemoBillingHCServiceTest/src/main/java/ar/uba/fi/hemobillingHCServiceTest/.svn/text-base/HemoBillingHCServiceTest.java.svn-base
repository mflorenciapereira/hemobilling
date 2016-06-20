package ar.uba.fi.hemobillingHCServiceTest;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;
import ar.uba.fi.hemobilling.commons.remoteInterfaces.HBLaboratorioRemoteInterface;
import ar.uba.fi.hemobilling.commons.remoteInterfaces.HemoBillingHCRemoteInterface;
import ar.uba.fi.hemobilling.commons.rmi.PolicyFileLocator;

public class HemoBillingHCServiceTest 
{
    public static void main( String[] args )
    {
        System.setProperty("java.rmi.server.codebase", HBLaboratorioRemoteInterface.class.getProtectionDomain().getCodeSource().getLocation().toString());
        System.setProperty("java.security.policy", PolicyFileLocator.getLocationOfPolicyFile());
        
    	if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try 
        {
            String name = "HemoBillingHCServer";
            Registry registry = LocateRegistry.getRegistry();
            HemoBillingHCRemoteInterface HBHCService = (HemoBillingHCRemoteInterface) registry.lookup(name);
            
            
            
            //PRUEBA DE OBTENER UN PACIENTE QUE NO EXISTE ------------------------------------------------------------
            try
            {
                Integer HC = 10010;
                PacienteHCDTO paciente = HBHCService.getPacienteHC(HC); 
                
                if( paciente == null )
                {
                	System.out.println("El usuario no existe");
                }
                else
                {
                	System.out.println(paciente.getApellidoNombre() );
                }
            }
            
            catch( RemoteException re )
            {
            	System.out.println("Se produjo una excepcion al momento de obtener el paciente. Es posible que el Servicio haya tenido un problema");
            }
            
            
            
            //PRUEBA DE OBTENER UN PACIENTE QUE SI EXISTE ------------------------------------------------------------
            try
            {
                Integer HC = 874;
                PacienteHCDTO paciente = HBHCService.getPacienteHC(HC); 
                
                if( paciente == null )
                {
                	System.out.println("El usuario no existe");
                }
                else
                {
                	System.out.println(paciente.getApellidoNombre() );
                }
            }
            
            catch( RemoteException re )
            {
            	System.out.println("Se produjo una excepcion al momento de obtener el paciente. Es posible que el Servicio haya tenido un problema");
            }
            
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
