package ar.uba.fi.hemobilling.reportes.cartaDeFacturas;

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
import ar.uba.fi.hemobilling.util.PropertiesReader;

public class CartaDeFacturas  extends Reporte 
{
	private static final String NOMBRE_ARCHIVO = "cartaFacturas.nombreArchivo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE = "cartaFacturas.template";
	
	private static final String ATRIBUTO_NRO_FACTURA_DTO = "nroFactura";
	private static final String ATRIBUTO_FECHA_DTO = "fecha";
	private static final String ATRIBUTO_DESTINATARIO_DTO = "destinatario";
	private static final String ATRIBUTO_CODIGO_DTO = "codigo";
	private static final String ATRIBUTO_MONTO_DTO = "monto";

	private static final String PARAMETRO_NOMBRE_OS = "OBRA_SOCIAL";
	private static final String PARAMETRO_FECHA = "FECHA";	
	
	
	public CartaDeFacturas(GeneradorReportesService generadorService, PropertiesReader messageSupport) {
		super(generadorService, messageSupport);
	}

	@Override
	protected DynamicReport createDynamicReport(DatosReporte datosDeReporte) throws ColumnBuilderException {
		ReporteBuilder rb = new ReporteBuilder();
		
		rb.setTemplateReporte(messageSupport.getProperty(NOMBRE_ARCHIVO_TEMPLATE));
		
		rb.setAltoEncabezado(300);
		rb.setAltoDetalle(20);
		rb.setAltoPie(70);
		
		rb.addColumnaTexto(ATRIBUTO_NRO_FACTURA_DTO, " ", 80, ReportAlign.ALIGN_CENTER );
		rb.addColumnaTexto(ATRIBUTO_FECHA_DTO, " ", 50, ReportAlign.ALIGN_CENTER);
		rb.addColumnaTexto(ATRIBUTO_DESTINATARIO_DTO, " ", 100, ReportAlign.ALIGN_LEFT);
		rb.addColumnaTexto(ATRIBUTO_CODIGO_DTO, " ", 70, ReportAlign.ALIGN_CENTER);
		rb.addColumna(ATRIBUTO_MONTO_DTO,BigDecimal.class.getName()," ",100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.agregarTotalPesos(5, this.getHeaderStyle(HorizontalAlign.LEFT));
		return rb.build();
	}

	@Override
	protected Map<String, Object> generarParametros(DatosReporte datosDeReporte) 
	{
		DatosReporteCartaDeFacturas dato = (DatosReporteCartaDeFacturas)datosDeReporte;
		
		Map<String, Object>  parametros = generadorReportesService.generarParametrosDefault();
		
		parametros.put(PARAMETRO_NOMBRE_OS, dato.getObraSocialDestino() );
		parametros.put(PARAMETRO_FECHA,dato.getFecha() );
		
		
		return parametros;
	}

	@Override
	protected String generarNombreReporte(DatosReporte datosDeReporte)
	{
		String nombreArchivo = messageSupport.getProperty(NOMBRE_ARCHIVO);
		
		DatosReporteCartaDeFacturas dato = (DatosReporteCartaDeFacturas)datosDeReporte;
		nombreArchivo += "_" + dato.getObraSocialDestino();
		
		nombreArchivo = this.generadorReportesService.generarNombreArchivoExportacion(nombreArchivo, new Date(), datosDeReporte.getTipoReporte().getExtension() );
		
		return nombreArchivo;
	}
	
	public Style getHeaderStyle(HorizontalAlign alineacion){
		Style estiloColumna = new Style();
		estiloColumna.setHorizontalAlign(HorizontalAlign.LEFT);
		estiloColumna.setVerticalAlign(VerticalAlign.MIDDLE);
		estiloColumna.setStretchWithOverflow(true);
		estiloColumna.setBlankWhenNull(true);
		
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
		
		estiloColumna.setFont(new Font(8, Font._FONT_ARIAL, false));
		estiloColumna.setHorizontalAlign(alineacion);
		return estiloColumna;		
		
	}

}
