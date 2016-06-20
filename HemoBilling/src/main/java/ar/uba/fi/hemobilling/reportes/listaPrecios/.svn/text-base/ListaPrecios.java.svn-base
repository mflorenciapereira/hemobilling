package ar.uba.fi.hemobilling.reportes.listaPrecios;

import java.util.Date;
import java.util.Map;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;
import ar.uba.fi.hemobilling.reportes.reporteador.Reporte;
import ar.uba.fi.hemobilling.reportes.reporteador.service.GeneradorReportesService;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ReportAlign;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ReporteBuilder;
import ar.uba.fi.hemobilling.util.HTMLEntities;
import ar.uba.fi.hemobilling.util.PropertiesReader;

/**
 * Representa la lista de precios
 * 
 * Por el momento, este reporte no se implemento
 * ya que no es obligatorio para la entrega inicial
 * 
 * @author Ale
 *
 */
public class ListaPrecios extends Reporte 
{
	private static final String NOMBRE_ARCHIVO = "listaPrecios.nombreArchivo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE = "listaPrecios.template";
	private static final String TITULO_REPORTE = "listaPrecios.titulo";
	
	private static final String ATRIBUTO_CODIGO_DTO = "codigo";
	private static final String NOMBRE_COLUMNA_CODIGO = "listaPrecios.columna.codigo.titulo";
	private static final String ATRIBUTO_DENOMICACION_DTO = "denominacion";
	private static final String NOMBRE_COLUMNA_DENOMINACION = "listaPrecios.columna.denominacion.titulo";
	private static final String ATRIBUTO_PRECIO_DTO = "precio";
	private static final String NOMBRE_COLUMNA_PRECIO = "listaPrecios.columna.Precio.titulo";
	
	private static final String PARAMETRO_NOMBRE_LISTA = "NOMBRE_LISTA";
	private static final String PARAMETRO_FECHA_VIGENCIA = "FECHA_VIGENCIA";

	@Override
	protected DynamicReport createDynamicReport(DatosReporte datosDeReporte) throws ColumnBuilderException 
	{
		ReporteBuilder rb = new ReporteBuilder();
		
		rb.setTemplateReporte(messageSupport.getProperty(NOMBRE_ARCHIVO_TEMPLATE));
		
		rb.addTitulo(HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(TITULO_REPORTE)), ReporteBuilder.getEstiloTitulo(), 30);
		
		rb.setAltoEncabezado(23);
		rb.setAltoDetalle(20);
		rb.setAltoPie(24);
		
		rb.addColumnaTexto(ATRIBUTO_CODIGO_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_CODIGO)), 80, ReportAlign.ALIGN_CENTER );
		rb.addColumnaTexto(ATRIBUTO_DENOMICACION_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_DENOMINACION)), 70, ReportAlign.ALIGN_LEFT);
		rb.addColumnaTexto(ATRIBUTO_PRECIO_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_PRECIO)), 70, ReportAlign.ALIGN_CENTER);

		return rb.build();
	}

	@Override
	protected Map<String, Object> generarParametros(DatosReporte datosDeReporte) 
	{
		//Ademas de los default, se debe mostrar el nombre de la lista y su fecha de vigencia
		
		Map<String, Object>  parametros = generadorReportesService.generarParametrosDefault();
		
		DatosReporteListaPrecios dato = (DatosReporteListaPrecios)datosDeReporte;
		parametros.put(PARAMETRO_NOMBRE_LISTA, dato.getNombreLista() );
		parametros.put(PARAMETRO_FECHA_VIGENCIA,dato.getFechaVigencia() );
		
		return parametros;
	}

	@Override
	protected String generarNombreReporte(DatosReporte datosDeReporte) 
	{
		String nombreArchivo = messageSupport.getProperty(NOMBRE_ARCHIVO);
		
		DatosReporteListaPrecios dato = (DatosReporteListaPrecios)datosDeReporte;
		nombreArchivo += "_" + dato.getNombreLista();
		
		nombreArchivo = this.generadorReportesService.generarNombreArchivoExportacion(nombreArchivo, new Date(), datosDeReporte.getTipoReporte().getExtension() );
		
		return nombreArchivo;
	}
	
	public ListaPrecios( GeneradorReportesService generadorService , PropertiesReader messageSupport )
	{
		super(generadorService, messageSupport);
	}

}
