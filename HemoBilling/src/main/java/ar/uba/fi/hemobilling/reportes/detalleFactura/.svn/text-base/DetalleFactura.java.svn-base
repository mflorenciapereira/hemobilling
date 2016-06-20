package ar.uba.fi.hemobilling.reportes.detalleFactura;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;
import ar.uba.fi.hemobilling.reportes.reporteador.Reporte;
import ar.uba.fi.hemobilling.reportes.reporteador.service.GeneradorReportesService;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ReportAlign;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ReporteBuilder;
import ar.uba.fi.hemobilling.util.HTMLEntities;
import ar.uba.fi.hemobilling.util.PropertiesReader;

/**
 * Representa cada anexo detalle por paciente de la fatura totalizada
 * de PAMI
 * @author Ale
 *
 */
public class DetalleFactura extends Reporte 
{
	private static final String NOMBRE_ARCHIVO = "detalleFactura.nombreArchivo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE = "detalleFactura.template";
	private static final String TITULO_REPORTE = "detalleFactura.titulo";
	
	private static final String ATRIBUTO_FECHA_DTO = "fecha";
	private static final String NOMBRE_COLUMNA_FECHA = "detalleFactura.columna.fecha.titulo";
	private static final String ATRIBUTO_CODIGO_DTO = "codigo";
	private static final String NOMBRE_COLUMNA_CODIGO = "detalleFactura.columna.codigo.titulo";
	private static final String ATRIBUTO_CANTIDAD_DTO = "cantidad";
	private static final String NOMBRE_COLUMNA_CANTIDAD = "detalleFactura.columna.cantidad.titulo";
	private static final String ATRIBUTO_PRESTACION_DTO = "prestacion";
	private static final String NOMBRE_COLUMNA_PRESTACION = "detalleFactura.columna.prestacion.titulo";
	private static final String ATRIBUTO_IMPORTE_DTO = "importe";
	private static final String NOMBRE_COLUMNA_IMPORTE = "detalleFactura.columna.importe.titulo";
	private static final String ATRIBUTO_TOTAL_DTO = "total";
	private static final String NOMBRE_COLUMNA_TOTAL = "detalleFactura.columna.total.titulo";
	
	private static final String PARAMETRO_PACIENTE = "PACIENTE";
	private static final String PARAMETRO_OBRA_SOCIAL = "OBRA_SOCIAL";
	private static final String PARAMETRO_NRO_AFILIADO = "NRO_AFILIADO";
	
	
	
	@Override
	protected DynamicReport createDynamicReport(DatosReporte datosDeReporte) throws ColumnBuilderException {
		ReporteBuilder rb = new ReporteBuilder();
		
		rb.setTemplateReporte(messageSupport.getProperty(NOMBRE_ARCHIVO_TEMPLATE));
		
		rb.addTitulo(HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(TITULO_REPORTE)), ReporteBuilder.getEstiloTitulo(), 30);
		rb.setMargenes(50, 50, 50,50);
		
		rb.setAltoEncabezado(150);
		rb.setAltoDetalle(20);
		rb.setAltoPie(100);
		rb.addColumnaTexto(ATRIBUTO_FECHA_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_FECHA)), 110, ReportAlign.ALIGN_CENTER );
		rb.addColumnaTexto(ATRIBUTO_CODIGO_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_CODIGO)), 70, ReportAlign.ALIGN_LEFT);
		rb.addColumnaTexto(ATRIBUTO_CANTIDAD_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_CANTIDAD)), 70, ReportAlign.ALIGN_RIGHT);
		rb.addColumnaTexto(ATRIBUTO_PRESTACION_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_PRESTACION)), 300, ReportAlign.ALIGN_LEFT);
		rb.addColumnaTexto(ATRIBUTO_IMPORTE_DTO, HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_IMPORTE)), 300, ReportAlign.ALIGN_LEFT);
		rb.addColumna(ATRIBUTO_TOTAL_DTO,BigDecimal.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_TOTAL)),100,this.getDataStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.agregarTotalPesos(6, this.getHeaderStyle(HorizontalAlign.LEFT));
		return rb.build();
	}

	private Style getHeaderStyle(HorizontalAlign lEFT) {
		Style estiloColumna = new Style();
		estiloColumna.setHorizontalAlign(HorizontalAlign.RIGHT);
		estiloColumna.setVerticalAlign(VerticalAlign.MIDDLE);
		estiloColumna.setStretchWithOverflow(true);
		estiloColumna.setBlankWhenNull(true);
		estiloColumna.setFont(new Font(10, Font._FONT_ARIAL, true));
		estiloColumna.setHorizontalAlign(lEFT);
		return estiloColumna;
	}

	@Override
	protected Map<String, Object> generarParametros(DatosReporte datosDeReporte) 
	{

		Map<String, Object>  parametros = generadorReportesService.generarParametrosDefault();
		
		DatosReporteDetalleFactura dato = (DatosReporteDetalleFactura)datosDeReporte;
		parametros.put(PARAMETRO_PACIENTE, dato.getPaciente() );
		parametros.put(PARAMETRO_OBRA_SOCIAL,dato.getObraSocial() );
		parametros.put(PARAMETRO_NRO_AFILIADO,dato.getNroAfiliado() );
		
		
		return parametros;
	}

	@Override
	protected String generarNombreReporte(DatosReporte datosDeReporte) 
	{
		String nombreArchivo = messageSupport.getProperty(NOMBRE_ARCHIVO);
		
		DatosReporteDetalleFactura dato = (DatosReporteDetalleFactura)datosDeReporte;
		nombreArchivo += "_"+dato.getNroHC();
		
		nombreArchivo = this.generadorReportesService.generarNombreArchivoExportacion(nombreArchivo, new Date(), datosDeReporte.getTipoReporte().getExtension() );
		
		return nombreArchivo;
	}
	
	public DetalleFactura( GeneradorReportesService generadorService , PropertiesReader messageSupport )
	{
		super(generadorService, messageSupport);
	}
	
	public Style getDataStyle(HorizontalAlign alineacion){
		Style estiloColumna = new Style();
		estiloColumna.setHorizontalAlign(HorizontalAlign.RIGHT);
		estiloColumna.setVerticalAlign(VerticalAlign.MIDDLE);
		estiloColumna.setStretchWithOverflow(true);
		estiloColumna.setBlankWhenNull(true);
		estiloColumna.setFont(new Font(8, Font._FONT_ARIAL, false));
		estiloColumna.setHorizontalAlign(alineacion);
		return estiloColumna;		
		
	}

}
