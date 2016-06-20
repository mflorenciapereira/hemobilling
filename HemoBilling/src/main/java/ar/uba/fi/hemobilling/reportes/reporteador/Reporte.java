package ar.uba.fi.hemobilling.reportes.reporteador;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.uba.fi.hemobilling.dto.ResultExportacionDTO;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ExportHelper;
import ar.uba.fi.hemobilling.reportes.reporteador.service.GeneradorReportesService;
import ar.uba.fi.hemobilling.util.PropertiesReader;

/**
 * Clase padre de todos los reportes.
 * Implementa la funcion de crear los reportes y delega la configuracion del mismo, 
 * la generacion de sus parametros y de nombre del reporte a las clases hijas, quienes
 * lo implementan para cada reporte en particular.
 * 
 * 
 */
public abstract class Reporte 
{
	protected GeneradorReportesService generadorReportesService;
	protected PropertiesReader messageSupport;
	
	protected static Logger log = Logger.getLogger(Reporte.class);
	
	/*
	 * Devuelve la configuracion total de reporte a emitir
	 * Paso los datos del reporte por si son necesarios
	 */
	protected abstract DynamicReport createDynamicReport( DatosReporte datosDeReporte ) throws ColumnBuilderException;
	
	/*
	 * Mapea los datos (o algunos) del reporte a un mapa apto para Jasper
	 */
	protected abstract Map<String, Object> generarParametros( DatosReporte datosDeReporte );	
	
	/*
	 * Genera el nombre del reporte. 
	 * Paso los datos del reporte por si son necesarios
	 */
	protected abstract String generarNombreReporte( DatosReporte datosDeReporte );
	
	
	
	@SuppressWarnings("rawtypes")
	protected ResultExportacionDTO reportarError( Exception e , Class clase )
	{
		ResultExportacionDTO result = new ResultExportacionDTO();
		
		result.setError(Boolean.TRUE);
		
		
		String mensaje = "Se produjo un error cuando el reporteador "+ clase.getName()+" quizo emitir un reporte. StackTrace: "+e.getMessage();
		log.error(mensaje);
		
		return result;
		
	}
	
	public Reporte( GeneradorReportesService generadorService , PropertiesReader messageSupport )
	{ 
		this.generadorReportesService = generadorService;
		this.messageSupport = messageSupport;
	}
	
	/*
	 * Permite la creacion de del reporte
	 * Se basa en la implementacion de los metodos anteriores, que son dependiente del tipo de reporte
	 */
	public ResultExportacionDTO exportar( DatosReporte datosDeReporte , HttpServletResponse res )
	{
		try
		{	
			log.info("Se comienza con la generacion de un reporte "+datosDeReporte.getTipoReporte().toString() );
			
			Map<String, Object> parametros = generarParametros( datosDeReporte );
			String nombreArchivo = generarNombreReporte( datosDeReporte );
			DynamicReport dynamicReport = createDynamicReport(datosDeReporte);
			Collection<?> listaDatos = datosDeReporte.getDatosAListar();
			
			byte[] datosGenerados = generadorReportesService.generarExportacion( datosDeReporte.getTipoReporte() , dynamicReport , listaDatos , parametros );
			
			ExportHelper.generarRespuesta(res, nombreArchivo, datosGenerados);
			
			log.info("Finaliza correctamente la generacion de un reporte "+datosDeReporte.getTipoReporte().toString() );
			
			return new ResultExportacionDTO();
			
		} 
	
		catch (ColumnBuilderException e) 
		{
			log.error("Error al generar las columnas del reporte "+datosDeReporte.getTipoReporte().toString() , e );
			return reportarError( e , Reporte.class );
		}
		
		catch (JRException e) 
		{
			log.error("Error de la JRE al generar el reporte "+datosDeReporte.getTipoReporte().toString() , e );
			return reportarError( e , Reporte.class );
		}
		
		catch (IOException e) 
		{
			log.error("Error de IO al generar el reporte "+datosDeReporte.getTipoReporte().toString() ,e );
			return reportarError( e , Reporte.class );
		}
		
		
	}
	
	
	/*
	 * Permite la creacion de un jasperprint
	 * 
	 */
	public JasperPrint armarJasperPrint( DatosReporte datosDeReporte)
	{
		try
		{	
			log.info("Se comienza con la generacion de un reporte "+datosDeReporte.getTipoReporte().toString() );
			
			Map<String, Object> parametros = generarParametros( datosDeReporte );
			DynamicReport dynamicReport = createDynamicReport(datosDeReporte);
			Collection<?> listaDatos = datosDeReporte.getDatosAListar();
			
			return generadorReportesService.obtenerJasperPrint( datosDeReporte.getTipoReporte() , dynamicReport , listaDatos , parametros );
			
				
		} 
	
		catch (ColumnBuilderException e) 
		{
			log.error("Error al generar las columnas del reporte "+datosDeReporte.getTipoReporte().toString() , e );
			return null;
		}
		
		catch (JRException e) 
		{
			log.error("Error de la JRE al generar el reporte "+datosDeReporte.getTipoReporte().toString() , e );
			return null;
		}
		
		
		
		
	}
	
	
	
	
	


}
