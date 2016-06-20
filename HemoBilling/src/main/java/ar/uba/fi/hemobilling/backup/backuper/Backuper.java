package ar.uba.fi.hemobilling.backup.backuper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ar.uba.fi.hemobilling.util.PropertiesReader;

@Component("backuper")
public class Backuper 
{
	private static final String COMANDO_BACKUP= "backuper.comandoBackup";
	private static final String DIRECTORIO_SALIDA_BACKUP= "backuper.directorioSalidaBackup";
	private static final String PREFIJO_ARCHIVO_SALIDA_BACKUP= "backuper.prefijoArchivoSalidaBackup";
	private static final String EXTENSION_ARCHIVO_SALIDA_BACKUP= "backuper.extensionArchivoSalidaBackup";
	
	
	@Resource(name = "messageSupport")
	private PropertiesReader messageSupport;
	
	private static Logger logger = Logger.getLogger(Backuper.class);
	
	private String armarNombreArchivo()
	{
		String nombre=messageSupport.getProperty(DIRECTORIO_SALIDA_BACKUP)+"/"+messageSupport.getProperty(PREFIJO_ARCHIVO_SALIDA_BACKUP);
		
		Calendar hoy = Calendar.getInstance();
		nombre += hoy.get( Calendar.DAY_OF_MONTH );
		nombre += "-";
		nombre += hoy.get( Calendar.MONTH );
		nombre += "-";
		nombre += hoy.get( Calendar.YEAR );
		nombre += "-";
		nombre += hoy.get( Calendar.HOUR_OF_DAY );
		nombre += "-";
		nombre += hoy.get( Calendar.MINUTE );
		nombre += "-";
		nombre += hoy.get( Calendar.SECOND );
		
		nombre +="."+messageSupport.getProperty(EXTENSION_ARCHIVO_SALIDA_BACKUP);
		return nombre;
	}
	
	private void guardarSalida(InputStream is) throws IOException 
	{
        if (is != null) 
        {
        	String nombreArchivo = armarNombreArchivo();
			File file = new File( nombreArchivo );
			FileOutputStream fop = new FileOutputStream(file);
			
			logger.info("Se realizara backup en el archivo: "+nombreArchivo );
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
        	byte[] buffer = new byte[1024];
               
        	try 
        	{
        		int n;
        		while ((n = is.read(buffer)) != -1) 
        		{
        			fop.write(buffer, 0, n);
        		}
                
        	} 
        	
        	finally 
        	{
        		is.close();
    			fop.flush();
    			fop.close();
        	}
        }
	}

	
	public Boolean doBackup()
	{
		try 
		{
			logger.info("Se comienza con el backup de la base de datos" );
			String comando = messageSupport.getProperty(COMANDO_BACKUP);
			Process process = Runtime.getRuntime().exec(comando);
			
			guardarSalida( process.getInputStream() );
			
			logger.info("El backup se realizo exitosamente" );
			
			return true;
		} 
		
		catch( Exception e )
		{
			logger.error("No se pudo realizar el backup" , e );
			return false;
		}
	}

}
