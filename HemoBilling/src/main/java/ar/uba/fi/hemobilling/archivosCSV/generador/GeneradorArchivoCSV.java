package ar.uba.fi.hemobilling.archivosCSV.generador;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.dto.ResultExportacionDTO;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ExportHelper;
import ar.uba.fi.hemobilling.reportes.reporteador.service.GeneradorReportesServiceImpl;
import ar.uba.fi.hemobilling.util.PropertiesReader;

@Service("generadorArchivoCSV")
public class GeneradorArchivoCSV 
{
	private static final String REPORTEADOR_MENSAJE_ERROR = "reporteador.mensajeError";
	
	private static final String ARCHIVO_CSV_PREFIJO_NOMBRE = "archivoCSV.nombreArchivo";
	private static final String ARCHIVO_CSV_EXTENSION = "archivoCSV.extensionArchivoSalida";
	
	@Resource(name = "messageSupport")
	private PropertiesReader messageSupport;
	
	private static Logger log = Logger.getLogger(GeneradorReportesServiceImpl.class);
	
	private String generarNombreArchivo( String idFactura )
	{
		String nombre = messageSupport.getProperty(ARCHIVO_CSV_PREFIJO_NOMBRE);
		nombre += idFactura;
		nombre += ".";
		nombre += messageSupport.getProperty(ARCHIVO_CSV_EXTENSION);
		
		return nombre;
	}
	
	private String getLineaCampo( CampoRegistroArchivoCSV campo )
	{
		String lineaCampo = "";
		Integer falta = campo.getLongitud()-campo.getDato().length();
		
		if( falta<0 )
		{
			//Recorto
			lineaCampo = campo.getDato().substring(0,campo.getLongitud()-1 );
		}
		else if( falta==0 )
		{
			//Copio justito y se termina todo
			lineaCampo =  campo.getDato();
		}
		else
		{
			//La long del registro es menor que la del dato. Debo rellenar
			char relleno = campo.getTipoRelleno();
			lineaCampo = campo.getDato();
			
			if( campo.getAlineacion().endsWith(CampoRegistroArchivoCSV.ALINEACION_DERECHA) )
			{
				//Relleno a izquierda
				for( int c=0 ; c<falta ; c++ )
					lineaCampo = relleno + lineaCampo;
			}
			else
			{
				//Relleno a derecha
				for( int c=0 ; c<falta ; c++ )
					lineaCampo += relleno;
			}			
		}
		
		return lineaCampo;
	}	
	
	
	private String getLineaRegistro( RegistroArchivoCSV registro , char separador )
	{
		Iterator<CampoRegistroArchivoCSV> it = registro.getCampos().iterator();
		String lineaRegistro = "";
		
		while( it.hasNext() )
		{
			CampoRegistroArchivoCSV campo = it.next();
			lineaRegistro += getLineaCampo(campo);
			lineaRegistro += separador;
		}
		
		return lineaRegistro;
	}
	
	
	
	public ResultExportacionDTO generarArchivo( ArchivoCSV archivo , String nroFactura , HttpServletResponse res)
	{
		Iterator<RegistroArchivoCSV> it = archivo.getRegistros().iterator();
		String lineaArchivo = "";
		ResultExportacionDTO resultado = new ResultExportacionDTO();
		
		log.info("Se comienza a generar el Archivo CSV: " + archivo.getNombreArchivo() );
		
		try 
		{
			while( it.hasNext() )
			{
				RegistroArchivoCSV registro = it.next();
				String lineaRegistro = getLineaRegistro(registro, archivo.getSeparadorCampo() );
				
				lineaArchivo += lineaRegistro;
				lineaArchivo += archivo.getFinLinea();
			}
			
			ExportHelper.generarRespuesta(res, generarNombreArchivo(nroFactura), lineaArchivo.getBytes() );
			log.info("Finaliza correctamente la generacion del Archivo CSV: " + archivo.getNombreArchivo() );
		}
		catch (Exception e) 
		{
			resultado.setError(Boolean.TRUE);
			resultado.setErrorMessage(messageSupport.getProperty(REPORTEADOR_MENSAJE_ERROR) );
			
			log.error("Error al generar el Archivo CSV: " + archivo.getNombreArchivo() , e );
		}
		
		return resultado;
	}
}
