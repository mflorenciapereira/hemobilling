package ar.uba.fi.hemobillingLaboServiceTest;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.Collection;

import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.remoteInterfaces.HBLaboratorioRemoteInterface;
import ar.uba.fi.hemobilling.commons.rmi.PolicyFileLocator;

public class HBLaboServiceTest 
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
            String name = "HBLaboratorioServer";
            Registry registry = LocateRegistry.getRegistry();
            HBLaboratorioRemoteInterface HBLabo = (HBLaboratorioRemoteInterface) registry.lookup(name);
            
            
            //PRUEBA DE OBTENER UN PACIENTE QUE NO EXISTE ------------------------------------------------------------
//            try
//            {
//                Integer HC = 10010;
//                PacienteLaboratorioDTO paciente = HBLabo.getPacienteLaboratorio( HC );  
//                
//                if( paciente == null )
//                {
//                	System.out.println("El usuario no existe");
//                }
//                else
//                {
//                	System.out.println(paciente.getNombre());
//                }
//            }
//            
//            catch( RemoteException re )
//            {
//            	System.out.println("Se produjo una excepcion al momento de obtener el paciente. Es posible que el Servicio haya tenido un problema");
//            }
            
            
            
            //PRUEBA DE OBTENER UN PACIENTE QUE SI EXISTE ------------------------------------------------------------
//            try
//            {
//                Integer HC = 874;
//                PacienteLaboratorioDTO paciente = HBLabo.getPacienteLaboratorio( HC );  
//                
//                if( paciente == null )
//                {
//                	System.out.println("El usuario no existe");
//                }
//                else
//                {
//                	System.out.println(paciente.getNombre());
//                }
//            }
//            
//            catch( RemoteException re )
//            {
//            	System.out.println("Se produjo una excepcion al momento de obtener el paciente. Es posible que el Servicio haya tenido un problema");
//            }
            
            
            
            //PRUEBA DE ACTUALIZAR EL NUMERO DE HC A UN PACIENTE QUE NO EXISTE ------------------------------------------------------------
//            try
//            {
//                if( HBLabo.actualizarNumeroHistoriaClinica(10020, 77777) )
//                {
//                	System.out.println("Se pudo actualizar el numero de HC");
//                }
//                else
//                {
//                	System.out.println("NO Se pudo actualizar el numero de HC");
//                }
//            }
//            
//            catch( RemoteException re )
//            {
//            	System.out.println("Se produjo una excepcion al momento de actualizar el numero de HC el paciente. Es posible que el Servicio haya tenido un problema");
//            }
            
            
            
            //PRUEBA DE ACTUALIZAR EL NUMERO DE HC A UN PACIENTE QUE SI EXISTE ------------------------------------------------------------
//            try
//            {
//                if( HBLabo.actualizarNumeroHistoriaClinica(874, 6666) )
//                {
//                	System.out.println("Se pudo actualizar el numero de HC");
//                }
//                else
//                {
//                	System.out.println("NO Se pudo actualizar el numero de HC");
//                }
//            }
//            
//            catch( RemoteException re )
//            {
//            	System.out.println("Se produjo una excepcion al momento de actualizar el numero de HC el paciente. Es posible que el Servicio haya tenido un problema");
//            }
            
            
            
            //PRUEBA DE BUSQUEDA DE ANALISIS CLINICOS EN UN RANGO EXISTENTE ------------------------------------------------------------
            try
            {
            	Calendar inicio = Calendar.getInstance();
            	Calendar fin = Calendar.getInstance();
            	
            	inicio.set( Calendar.MONTH , 5 );
            	fin.set( Calendar.MONTH , 5 );
            	
            	inicio.set( Calendar.DAY_OF_MONTH , 10 );
            	fin.set( Calendar.DAY_OF_MONTH , 15 );
            	
            	FiltroConsultaEstudiosLaboratorioDTO filtro = new FiltroConsultaEstudiosLaboratorioDTO();
            	filtro.setFechaInicio( inicio.getTime() );
            	filtro.setFechaFinal( fin.getTime() );
            	
            	Collection<EstudioLaboratorioDTO> analisis = HBLabo.buscarAnalisisLaboratorio(filtro);
            	
                if( analisis!=null )
                {
                	System.out.println("Se encontraron "+String.valueOf(analisis.size()) + " analisis");
                }
                else
                {
                	System.out.println("NO se encontraron analisis clinicos");
                }
            }
            
            catch( RemoteException re )
            {
            	System.out.println("Se produjo una excepcion al momento de buscar analisis clinicos. Es posible que el Servicio haya tenido un problema");
            }
            
            
            //PRUEBA DE BUSQUEDA DE ANALISIS CLINICOS EN UN RANGO NO EXISTENTE ------------------------------------------------------------
            try
            {
            	Calendar inicio = Calendar.getInstance();
            	Calendar fin = Calendar.getInstance();
            	
            	FiltroConsultaEstudiosLaboratorioDTO filtro = new FiltroConsultaEstudiosLaboratorioDTO();
            	filtro.setFechaInicio( inicio.getTime() );
            	filtro.setFechaFinal( fin.getTime() );
            	
            	Collection<EstudioLaboratorioDTO> analisis = HBLabo.buscarAnalisisLaboratorio(filtro);
            	
                if( analisis!=null )
                {
                	System.out.println("Se encontraron "+String.valueOf(analisis.size()) + " analisis");
                }
                else
                {
                	System.out.println("NO se encontraron analisis clinicos");
                }
            }
            
            catch( RemoteException re )
            {
            	System.out.println("Se produjo una excepcion al momento de buscar analisis clinicos. Es posible que el Servicio haya tenido un problema");
            }           
        } 
        
        catch (Exception e) 
        {
        	System.out.println("Excepcion al realizar las pruebas: ");
            e.printStackTrace();
        }
    }
}
