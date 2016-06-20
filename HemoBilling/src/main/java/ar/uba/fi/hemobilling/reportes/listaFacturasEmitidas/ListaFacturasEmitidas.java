package ar.uba.fi.hemobilling.reportes.listaFacturasEmitidas;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;
import ar.uba.fi.hemobilling.reportes.reporteador.Reporte;
import ar.uba.fi.hemobilling.reportes.reporteador.service.GeneradorReportesService;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ReporteBuilder;
import ar.uba.fi.hemobilling.util.HTMLEntities;
import ar.uba.fi.hemobilling.util.PropertiesReader;

/**
 * Representa el reporte de facturas emitidas
 * 
 * @author Ale
 *
 */
public class ListaFacturasEmitidas extends Reporte 
{
	private static final String NOMBRE_ARCHIVO = "listadoFacturaEmitidas.nombreArchivo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE = "listadoFacturaEmitidas.template";
	private static final String TITULO_REPORTE = "listadoFacturaEmitidas.titulo";
	
	private static final String ATRIBUTO_FECHA_DTO = "fecha";
	private static final String NOMBRE_COLUMNA_FECHA = "listadoFacturaEmitidas.columna.fecha.titulo";
	private static final String ATRIBUTO_NRO_FACTURA_DTO = "nroFactura";
	private static final String NOMBRE_COLUMNA_NRO_FACTURA = "listadoFacturaEmitidas.columna.numFactura.titulo";
	private static final String ATRIBUTO_DESTINATARIO_DTO = "destinatario";
	private static final String NOMBRE_COLUMNA_DESTINATARIO = "listadoFacturaEmitidas.columna.destinatario.titulo";
	private static final String ATRIBUTO_OBRASOCIAL_DTO = "obraSocial";
	private static final String NOMBRE_COLUMNA_OBRASOCIAL = "listadoFacturaEmitidas.columna.obraSocial.titulo";
	private static final String ATRIBUTO_MONTO_OBRAS_SOCIALES_DTO = "montoObraSocial";
	private static final String NOMBRE_COLUMNA_MONTO_OBRAS_SOCIALES = "listadoFacturaEmitidas.columna.montoObrasSociales.titulo";
	
	private static final String ATRIBUTO_MONTO_OTROS_DTO = "montoOtros";
	private static final String NOMBRE_COLUMNA_MONTO_OTROS = "listadoFacturaEmitidas.columna.montoOtros.titulo";
	
	
	private static final String ATRIBUTO_CODIGO_CONTABLE_DTO = "codigoContable";
	private static final String NOMBRE_COLUMNA_CODIGO_CONTABLE = "listadoFacturaEmitidas.columna.codigoContable.titulo";
	
	private static final String PARAMETRO_PERIODO_DESDE = "periodoDesde";
	private static final String PARAMETRO_PERIODO_HASTA = "periodoHasta";
	

	@Override
	protected DynamicReport createDynamicReport(DatosReporte datosDeReporte) throws ColumnBuilderException 
	{
		ReporteBuilder rb = new ReporteBuilder();
		
		rb.setTemplateReporte(messageSupport.getProperty(NOMBRE_ARCHIVO_TEMPLATE));
		
		rb.addTitulo(HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(TITULO_REPORTE)), ReporteBuilder.getEstiloTitulo(), 30);
		

		
		
		
		rb.setAltoEncabezado(10);
		rb.setAltoDetalle(20);
		rb.setAltoPie(24);
		
		
		
		rb.setLandscape();
		rb.setMargenes(20, 20, 20, 20);
		rb.addColumna(ATRIBUTO_FECHA_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_FECHA)),50,this.getHeaderStyle(HorizontalAlign.LEFT),this.getHeaderStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_NRO_FACTURA_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_NRO_FACTURA)),70,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		
		rb.addColumna(ATRIBUTO_DESTINATARIO_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_DESTINATARIO)),300,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_CODIGO_CONTABLE_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_CODIGO_CONTABLE)),70,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_OBRASOCIAL_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_OBRASOCIAL)),100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_MONTO_OBRAS_SOCIALES_DTO,BigDecimal.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_MONTO_OBRAS_SOCIALES)),100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_MONTO_OTROS_DTO,BigDecimal.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_MONTO_OTROS)),100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		
		
		rb.agregarTotal(6, this.getHeaderStyle(HorizontalAlign.LEFT));
		rb.agregarTotal(7, this.getDataStyle(HorizontalAlign.LEFT));
		
		

		return rb.build();
	}

	@Override
	protected Map<String, Object> generarParametros(DatosReporte datosDeReporte) 
	{
				
		Map<String, Object>  parametros = generadorReportesService.generarParametrosDefault();
		DatosReporteListaFacturasEmitidas dato = (DatosReporteListaFacturasEmitidas)datosDeReporte;
		parametros.put(PARAMETRO_PERIODO_DESDE, dato.getPeriodoDesde() );
		parametros.put(PARAMETRO_PERIODO_HASTA,dato.getPeriosoHasta() );
				
		return parametros;
	}

	@Override
	protected String generarNombreReporte(DatosReporte datosDeReporte) 
	{
		String nombreArchivo = messageSupport.getProperty(NOMBRE_ARCHIVO);
		nombreArchivo = this.generadorReportesService.generarNombreArchivoExportacion(nombreArchivo, new Date(), datosDeReporte.getTipoReporte().getExtension() );
		
		return nombreArchivo;
	}
	
	public ListaFacturasEmitidas( GeneradorReportesService generadorService , PropertiesReader messageSupport )
	{
		super(generadorService, messageSupport);
	}

	public Style getHeaderStyle(HorizontalAlign alineacion){
		Style estiloColumna = new Style();
		estiloColumna.setHorizontalAlign(HorizontalAlign.LEFT);
		estiloColumna.setVerticalAlign(VerticalAlign.MIDDLE);
		estiloColumna.setStretchWithOverflow(true);
		estiloColumna.setBlankWhenNull(true);
		estiloColumna.setBorder(Border.THIN);
		estiloColumna.setFont(new Font(8, Font._FONT_ARIAL, true));
		estiloColumna.setHorizontalAlign(alineacion);
		return estiloColumna;		
		
	}
	
	public Style getDataStyle(HorizontalAlign alineacion){
		Style estiloColumna = new Style();
		estiloColumna.setHorizontalAlign(HorizontalAlign.LEFT);
		estiloColumna.setVerticalAlign(VerticalAlign.MIDDLE);
		estiloColumna.setStretchWithOverflow(true);
		estiloColumna.setBlankWhenNull(true);
		estiloColumna.setBorder(Border.THIN);
		estiloColumna.setFont(new Font(8, Font._FONT_ARIAL, false));
		estiloColumna.setHorizontalAlign(alineacion);
		return estiloColumna;		
		
	}
}
