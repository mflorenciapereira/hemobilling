package ar.uba.fi.hemobillingLaboService.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.uba.fi.hemobillingLaboService.controller.HBLaboratorioController;



public class HBLaboService 
{
	private static ApplicationContext context;
	private static Logger logger = Logger.getLogger(HBLaboService.class);
	private static HBLaboratorioController controlador;
	
    public static void main( String[] args )
    {  
    	try
    	{
    		PropertyConfigurator.configure( System.getProperty("propertyPath") );
    		
    		logger.info("Iniciando HemoBilling Laboratorio Service");
    		
    	    context = new ClassPathXmlApplicationContext("spring.xml");
    	    
    	    logger.info("Contexto de ejecucion cargado correctamente");

            controlador = (HBLaboratorioController)context.getBean("hemoBillingServiceController");
            
            logger.info("Controlador creado correctamente. Iniciando.....");
            
            controlador.iniciar();
  	    
            logger.info("HemoBilling Laboratorio Service iniciado correctamente.");
    	}
    		
    	catch (Exception e) 
    	{
    		logger.error("Se produjo una excepcion al ejecutar HemoBilling Laboratorio Service." , e );
    	}
    	
    }
}
