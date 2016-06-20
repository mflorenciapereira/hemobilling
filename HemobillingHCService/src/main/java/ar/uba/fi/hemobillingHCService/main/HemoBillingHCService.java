package ar.uba.fi.hemobillingHCService.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.uba.fi.hemobillingHCService.controller.HemoBillingHCController;

public class HemoBillingHCService 
{

	private static ApplicationContext context;
	private static Logger logger = Logger.getLogger(HemoBillingHCService.class);
	private static HemoBillingHCController controlador;
	
    public static void main( String[] args )
    {  
    	try
    	{
    	    PropertyConfigurator.configure( System.getProperty("propertyPath") );
    	    
    		logger.info("Iniciando HemoBilling HC Service");	
    	    context = new ClassPathXmlApplicationContext( "spring.xml" );
    	    
    	    logger.info("Contexto de ejecucion cargado correctamente");

            controlador = (HemoBillingHCController)context.getBean("hemoBillingHCController");
            
            logger.info("Controlador creado correctamente. Iniciando.....");
            
            controlador.iniciar();
  	    
            logger.info("HemoBilling HC Service iniciado correctamente.");
  
    	}
    		
    	catch (Exception e) 
    	{
    		logger.error("Se produjo una excepcion al ejecutar HemoBilling HC Service." , e );
    	}
    	
    }
}
