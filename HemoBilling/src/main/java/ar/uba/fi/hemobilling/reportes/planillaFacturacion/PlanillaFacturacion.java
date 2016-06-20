package ar.uba.fi.hemobilling.reportes.planillaFacturacion;

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

public class PlanillaFacturacion extends Reporte 
{
	private static final String NOMBRE_ARCHIVO = "planillaFacturacion.nombreArchivo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE = "planillaFacturacion.template";
	private static final String TITULO_REPORTE = "planillaFacturacion.titulo";
	
	private static final String ATRIBUTO_PRESTACION_DTO = "prestacion";
	private static final String NOMBRE_COLUMNA_PRESTACION = "planillaFacturacion.columna.prestacion.titulo";
	private static final String ATRIBUTO_CANTIDAD_DTO = "cantidad";
	private static final String NOMBRE_COLUMNA_CANTIDAD = "planillaFacturacion.columna.cantidad.titulo";
	private static final String ATRIBUTO_FECHA_INGRESO_DTO = "fechaIngreso";
	private static final String NOMBRE_COLUMNA_FECHA_INGRESO = "planillaFacturacion.columna.ingreso.titulo";
	private static final String ATRIBUTO_FECHA_EGRESO_DTO = "fechaEgreso";
	private static final String NOMBRE_COLUMNA_FECHA_EGRESO = "planillaFacturacion.columna.egreso.titulo";
	private static final String ATRIBUTO_NOMBRE_AFILIADO_DTO = "nombreAfiliado";
	private static final String NOMBRE_COLUMNA_NOMBRE_AFILIADO = "planillaFacturacion.columna.afiliado.titulo";
	private static final String ATRIBUTO_NROAFILIADO_DTO = "nroBeneficiario";
	private static final String NOMBRE_COLUMNA_NROAFILIADO = "planillaFacturacion.columna.nroBeneficiario.titulo";
	private static final String ATRIBUTO_NROHC_DTO = "nroHC";
	private static final String NOMBRE_COLUMNA_NROHC = "planillaFacturacion.columna.numHC.titulo";
	private static final String ATRIBUTO_NROOP_DTO = "nroOp";
	private static final String NOMBRE_COLUMNA_NROOP = "planillaFacturacion.columna.numOP.titulo";
	private static final String ATRIBUTO_OBSERVACIONES_DTO = "observaciones";
	private static final String NOMBRE_COLUMNA_OBSERVACIONES = "planillaFacturacion.columna.observaciones.titulo";
	private static final String ATRIBUTO_CAPITA_DTO = "capita";
	private static final String NOMBRE_COLUMNA_CAPITA = "planillaFacturacion.columna.capita.titulo";
	private static final String ATRIBUTO_MONTO_DTO = "monto";
	private static final String NOMBRE_COLUMNA_MONTO = "planillaFacturacion.columna.monto.titulo";
	
	private static final String PARAMETRO_NOMBRE_PRESTADOR = "NOMBRE_PRESTADOR";
	private static final String PARAMETRO_NRO_FACTURA = "NRO_FACTURA";
	
	

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
		
		
		
		rb.addColumna(ATRIBUTO_PRESTACION_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_PRESTACION)),300,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_CANTIDAD_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_CANTIDAD)),100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_FECHA_INGRESO_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_FECHA_INGRESO)),120,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_FECHA_EGRESO_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_FECHA_EGRESO)),120,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_NOMBRE_AFILIADO_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_NOMBRE_AFILIADO)),300,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_NROAFILIADO_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_NROAFILIADO)),300,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_NROHC_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_NROHC)),100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_NROOP_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_NROOP)),100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_OBSERVACIONES_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_OBSERVACIONES)),300,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_CAPITA_DTO,String.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_CAPITA)),300,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.LEFT) );
		rb.addColumna(ATRIBUTO_MONTO_DTO,BigDecimal.class.getName(),HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(NOMBRE_COLUMNA_MONTO)),100,this.getHeaderStyle(HorizontalAlign.LEFT),this.getDataStyle(HorizontalAlign.RIGHT) );
		
		
		rb.agregarTotal(11, this.getDataStyle(HorizontalAlign.LEFT));

		return rb.build();
	}


	@Override
	protected Map<String, Object> generarParametros(DatosReporte datosDeReporte) 
	{

		Map<String, Object>  parametros = generadorReportesService.generarParametrosDefault();
		
		DatosReportePlanillaFacturacion dato = (DatosReportePlanillaFacturacion)datosDeReporte;
		parametros.put(PARAMETRO_NOMBRE_PRESTADOR, dato.getNombrePrestador() );
		parametros.put(PARAMETRO_NRO_FACTURA,dato.getNroFactura() );
		
		return parametros;
	}

	@Override
	protected String generarNombreReporte(DatosReporte datosDeReporte)
	{
		String nombreArchivo = messageSupport.getProperty(NOMBRE_ARCHIVO);
		
		DatosReportePlanillaFacturacion dato = (DatosReportePlanillaFacturacion)datosDeReporte;
		nombreArchivo += "_"+dato.getNroFactura();
		
		nombreArchivo = this.generadorReportesService.generarNombreArchivoExportacion(nombreArchivo, new Date(), datosDeReporte.getTipoReporte().getExtension() );
		
		return nombreArchivo;
	}
	
	public PlanillaFacturacion( GeneradorReportesService generadorService , PropertiesReader messageSupport )
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
		
	};


}
