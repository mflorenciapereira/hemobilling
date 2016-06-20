package ar.uba.fi.hemobilling.reportes.reporteador;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.dto.ResultExportacionDTO;
import ar.uba.fi.hemobilling.exception.domain.HBErrorCreandoReporte;
import ar.uba.fi.hemobilling.exception.domain.HBReporteNoDefinido;
import ar.uba.fi.hemobilling.reportes.cartaDeFacturas.CartaDeFacturas;
import ar.uba.fi.hemobilling.reportes.cartaDeFacturas.DatosReporteCartaDeFacturas;
import ar.uba.fi.hemobilling.reportes.detalleFactura.DatosReporteDetalleFactura;
import ar.uba.fi.hemobilling.reportes.detalleFactura.DetalleFactura;
import ar.uba.fi.hemobilling.reportes.factura.DatosReporteFactura;
import ar.uba.fi.hemobilling.reportes.factura.ReporteFactura;
import ar.uba.fi.hemobilling.reportes.listaFacturasEmitidas.DatosReporteListaFacturasEmitidas;
import ar.uba.fi.hemobilling.reportes.listaFacturasEmitidas.ListaFacturasEmitidas;
import ar.uba.fi.hemobilling.reportes.listaPrecios.DatosReporteListaPrecios;
import ar.uba.fi.hemobilling.reportes.listaPrecios.ListaPrecios;
import ar.uba.fi.hemobilling.reportes.planillaFacturacion.DatosReportePlanillaFacturacion;
import ar.uba.fi.hemobilling.reportes.planillaFacturacion.PlanillaFacturacion;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ExportHelper;
import ar.uba.fi.hemobilling.reportes.reporteador.service.GeneradorReportesService;
import ar.uba.fi.hemobilling.reportes.sintesis.DatosReporteSintesisHC;
import ar.uba.fi.hemobilling.reportes.sintesis.SintesisHC;
import ar.uba.fi.hemobilling.util.PropertiesReader;

/**
 * Reporteador
 * 
 * Clase que se encarga de crear el reporte (en base al dato suministrado) y ejecutar su 
 * exportacion.
 * 
 * La idea es generar el Dato del reporte deseado e invocar a este bean inyectado
 * para la creacion del reporte y su posterior exportacion
 * 
 * NOTAS:
 * 
 *	- Por cada reporte nuevo, se debe agregar la asociacion con su dato de reporte
 *  - Todos los reportes deben tener su constructor explicito. Usa Reflection para la 
 *    creacion de los mismos.
 *    
 * @author Ale
 *
 */

@Service("reporteador")
public class Reporteador
{
	private static final String REPORTEADOR_MENSAJE_ERROR = "reporteador.mensajeError";
	
	@SuppressWarnings("rawtypes")
	private static Map<Class,Class> mapaAsociacionDatoReporte;
	
	@Resource(name = "messageSupport")
	private PropertiesReader messageSupport;
	
	@Resource(name = "generadorReportesService")
	protected GeneradorReportesService generadorReportesService;
	
	protected static Logger log = Logger.getLogger(Reporteador.class);
	
	@SuppressWarnings("rawtypes")
	private static void inicializar()
	{
		mapaAsociacionDatoReporte = new HashMap<Class, Class>();
		
		/* Asocio todos los datos de reporte a sus correspondientes reportes.
		 * NOTA: Si se crean mas, hay que ponerlos en esta lista 
		 * */
		mapaAsociacionDatoReporte.put( DatosReporteFactura.class , ReporteFactura.class );
		mapaAsociacionDatoReporte.put( DatosReporteListaFacturasEmitidas.class , ListaFacturasEmitidas.class );
		mapaAsociacionDatoReporte.put( DatosReporteDetalleFactura.class , DetalleFactura.class );
		mapaAsociacionDatoReporte.put( DatosReporteListaPrecios.class , ListaPrecios.class );
		mapaAsociacionDatoReporte.put( DatosReportePlanillaFacturacion.class , PlanillaFacturacion.class );
		mapaAsociacionDatoReporte.put( DatosReporteSintesisHC.class , SintesisHC.class );
		mapaAsociacionDatoReporte.put( DatosReporteCartaDeFacturas.class , CartaDeFacturas.class );
	}
	
	
	public Reporteador()
	{
		Reporteador.inicializar();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResultExportacionDTO exportarReporte( DatosReporte datosDeReporte , HttpServletResponse res ) throws HBReporteNoDefinido, HBErrorCreandoReporte
	{
		ResultExportacionDTO resultadoExportacion = null;
		try 
		{
			log.info("Se comienza a generar el reporte" );
			
			Class claseReporte = mapaAsociacionDatoReporte.get( datosDeReporte.getClass() );
			if( claseReporte==null ) throw new HBReporteNoDefinido();
			Reporte reporte = (Reporte) claseReporte.getConstructor( GeneradorReportesService.class , PropertiesReader.class ).newInstance( generadorReportesService , messageSupport );
			resultadoExportacion = reporte.exportar( datosDeReporte, res);
			
			if( resultadoExportacion.getError() )
			{
				resultadoExportacion.setErrorMessage(messageSupport.getProperty(REPORTEADOR_MENSAJE_ERROR) );
			}
			
			log.info("Finaliza la generacion del reporte" );
			
			return resultadoExportacion;
		}
		catch( HBReporteNoDefinido e ){
			log.error("El dato suministrado no esta asociado a un reporte" , e );
			throw e;
		}
		catch (Exception e) {
			log.error("Error al generar el reporte" , e );
			throw new HBErrorCreandoReporte(e);
		}
		
		
		
	}


	public ResultExportacionDTO exportarReporte(
			ArrayList<DatosReporte> facturasGeneradas,
			HttpServletResponse res) throws HBReporteNoDefinido, HBErrorCreandoReporte {
		ResultExportacionDTO resultadoExportacion =new ResultExportacionDTO();;
		
		try 
		{
			log.info("Se comienza a generar el reporte de varias facturas" );
			JasperPrint primero=this.obtenerJasperPrint(facturasGeneradas.get(0));
			
			if( primero==null )
			{
				resultadoExportacion.setErrorMessage(messageSupport.getProperty(REPORTEADOR_MENSAJE_ERROR) );
				return resultadoExportacion;
			}

			Iterator<DatosReporte> it=facturasGeneradas.iterator();
			it.next(); //salteo el primero que ya lo tengo generado
			while(it.hasNext()){
				JasperPrint jp=obtenerJasperPrint(it.next());
				if( jp==null )
				{
					resultadoExportacion.setErrorMessage(messageSupport.getProperty(REPORTEADOR_MENSAJE_ERROR) );
					return resultadoExportacion;
				}else
					this.agregarPaginas(primero,jp);
				
								
			}
			
			byte[] bytesobtenidos=generadorReportesService.generarPdfUnico(primero);
			ExportHelper.generarRespuesta(res, generadorReportesService.generarNombreArchivoExportacion("facturacion", new Date(), "pdf"), bytesobtenidos);
			log.info("Finaliza la generacion del reporte" );
			
			return resultadoExportacion;
		}
		catch( HBReporteNoDefinido e ){
			log.error("El dato suministrado no esta asociado a un reporte" , e );
			throw e;
		}
		catch (Exception e) {
			log.error("Error al generar el reporte" , e );
			throw new HBErrorCreandoReporte(e);
		}
		
		
	}


	private void agregarPaginas(JasperPrint primero, JasperPrint jp) {
		@SuppressWarnings("unchecked")
		Iterator<JRPrintPage> it=jp.getPages().iterator();
        while(it.hasNext()){
        	JRPrintPage object = it.next();
        	primero.addPage(object);
        };

		
		
	};


	private JasperPrint obtenerJasperPrint(DatosReporte datosReporteFactura)
			throws HBReporteNoDefinido, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		@SuppressWarnings("rawtypes")
		Class claseReporte = mapaAsociacionDatoReporte.get( datosReporteFactura.getClass() );
		if( claseReporte==null ) throw new HBReporteNoDefinido();
		@SuppressWarnings("unchecked")
		Reporte reporte = (Reporte) claseReporte.getConstructor( GeneradorReportesService.class , PropertiesReader.class ).newInstance( generadorReportesService , messageSupport );
		return reporte.armarJasperPrint( datosReporteFactura);
	}


//	public ResultExportacionDTO exportarReporteDetalle(
//			ArrayList<DatosReporteDetalleFactura> datosReporte,
//			HttpServletResponse res) throws HBReporteNoDefinido, HBErrorCreandoReporte {
//		ResultExportacionDTO resultadoExportacion =new ResultExportacionDTO();;
//		try 
//		{
//			log.info("Se comienza a generar el reporte de detalles" );
//			JasperPrint primero=this.obtenerJasperPrint(datosReporte.get(0));
//			
//			if( primero==null )
//			{
//				resultadoExportacion.setErrorMessage(messageSupport.getProperty(REPORTEADOR_MENSAJE_ERROR) );
//				return resultadoExportacion;
//			}
//
//			Iterator<DatosReporteDetalleFactura> it=datosReporte.iterator();
//			it.next(); //salteo el primero que ya lo tengo generado
//			while(it.hasNext()){
//				JasperPrint jp=obtenerJasperPrint(it.next());
//				if( jp==null )
//				{
//					resultadoExportacion.setErrorMessage(messageSupport.getProperty(REPORTEADOR_MENSAJE_ERROR) );
//					return resultadoExportacion;
//				}else
//					this.agregarPaginas(primero,jp);
//				
//								
//			}
//			
//			byte[] bytesobtenidos=generadorReportesService.generarPdfUnico(primero);
//			ExportHelper.generarRespuesta(res, generadorReportesService.generarNombreArchivoExportacion("facturacion", new Date(), "pdf"), bytesobtenidos);
//			log.info("Finaliza la generacion del reporte" );
//			
//			return resultadoExportacion;
//		}
//		catch( HBReporteNoDefinido e ){
//			log.error("El dato suministrado no esta asociado a un reporte" , e );
//			throw e;
//		}
//		catch (Exception e) {
//			log.error("Error al generar el reporte" , e );
//			throw new HBErrorCreandoReporte(e);
//		}
//		
//	}

}
