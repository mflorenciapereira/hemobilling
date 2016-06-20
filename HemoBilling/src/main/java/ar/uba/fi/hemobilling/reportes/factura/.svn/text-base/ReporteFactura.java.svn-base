package ar.uba.fi.hemobilling.reportes.factura;

import java.util.Date;
import java.util.Map;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;
import ar.uba.fi.hemobilling.reportes.reporteador.Reporte;
import ar.uba.fi.hemobilling.reportes.reporteador.service.GeneradorReportesService;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ReportAlign;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ReporteBuilder;
import ar.uba.fi.hemobilling.util.PropertiesReader;

/**
 * Representa una factura, ya sea detallada o totalizada
 * 
 * @author Ale
 *
 */
public class ReporteFactura extends Reporte 
{
	private static final String NOMBRE_ARCHIVO = "factura.nombreArchivo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE_FONDO = "factura.template.fondo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE_SIN_FONDO = "factura.template.sin.fondo";
	
	private static final String ATRIBUTO_FECHA_DTO = "fecha";
	private static final String ATRIBUTO_CODIGO_DTO = "codigo";
	private static final String ATRIBUTO_CANTIDAD_DTO = "cantidad";
	private static final String ATRIBUTO_PRESTACION_DTO = "prestacion";
	private static final String ATRIBUTO_IMPORTE_DTO = "importe";
	private static final String ATRIBUTO_HONORARIOS_DTO = "honorarios";
	private static final String ATRIBUTO_TOTAL_DTO = "total";
	private static final String ATRIBUTO_VACIO = "vacio";
	
	private static final String PARAMETRO_NOMBRE_CLIENTE = "NOMBRE_CLIENTE";
	private static final String PARAMETRO_OBRA_SOCIAL = "OBRA_SOCIAL";
	private static final String PARAMETRO_NRO_FACTURA = "NRO_FACTURA";
	private static final String PARAMETRO_NRO_AFILIADO = "NRO_AFILIADO";
	private static final String PARAMETRO_DIRECCION = "DIRECCION";
	private static final String PARAMETRO_CUIT = "CUIT";
	private static final String PARAMETRO_RESP_INSCRIPTO = "RESP_INSCRIPTO";
	private static final String PARAMETRO_RESP_NO_INSCRIPTO = "RESP_NO_INSCRIPTO";
	private static final String PARAMETRO_EXCENTO = "EXCENTO";
	private static final String PARAMETRO_NO_RESP = "NO_RESP";
	private static final String PARAMETRO_CONS_FINAL = "CONS_FINAL";
	private static final String PARAMETRO_CONTADO = "CONTADO";
	private static final String PARAMETRO_CUENTA_CORRIENTE = "CUENTA_CORRIENTE";
	private static final String PARAMETRO_TOTAL = "TOTAL";
	private static final String PARAMETRO_DESCRIP_MONTO = "DESCRIP_MONTO";
	private static final String PARAMETRO_OBSERVACIONES = "OBSERVACIONES";
	private static final String PARAMETRO_FECHA_EMISION = "FECHA_EMISION";
	private static final String PARAMETRO_CODIGO_CONTABLE = "CODIGO_CONTABLE";
	private static final String PARAMETRO_PRESTADOR = "PRESTADOR";
	private static final String PARAMETRO_AJUSTE_CENTAVOS = "AJUSTE_CENTAVOS";


	@Override
	protected DynamicReport createDynamicReport( DatosReporte datosDeReporte ) throws ColumnBuilderException 
	{
		ReporteBuilder rb = new ReporteBuilder();
		DatosReporteFactura drf=(DatosReporteFactura)datosDeReporte;
		if(drf.getFondo()){
				rb.setTemplateReporte(messageSupport.getProperty(NOMBRE_ARCHIVO_TEMPLATE_FONDO));			
		}else{
			rb.setTemplateReporte(messageSupport.getProperty(NOMBRE_ARCHIVO_TEMPLATE_SIN_FONDO));
		}
		
		
		rb.setAltoEncabezado(315);
		rb.setAltoDetalle(20);
		rb.setAltoPie(500);
		
		rb.addColumnaTextoSmall(ATRIBUTO_FECHA_DTO, " ",13, ReportAlign.ALIGN_LEFT); //FECHA
		rb.addColumnaTextoSmall(ATRIBUTO_CODIGO_DTO, " ", 7, ReportAlign.ALIGN_LEFT);
		rb.addColumnaTextoSmall(ATRIBUTO_CANTIDAD_DTO, " ", 6, ReportAlign.ALIGN_RIGHT);
		rb.addColumnaTextoSmall(ATRIBUTO_PRESTACION_DTO, " ", 32, ReportAlign.ALIGN_LEFT);
		rb.addColumnaTextoSmall(ATRIBUTO_IMPORTE_DTO," ", 12, ReportAlign.ALIGN_RIGHT);
		rb.addColumnaTextoSmall(ATRIBUTO_HONORARIOS_DTO, " ", 11, ReportAlign.ALIGN_CENTER);
		rb.addColumnaTextoSmall(ATRIBUTO_TOTAL_DTO, " ", 12, ReportAlign.ALIGN_RIGHT);
		rb.addColumnaTextoSmall(ATRIBUTO_VACIO, " ", 8, ReportAlign.ALIGN_RIGHT);
		
		rb.setPortrait();
		return rb.build();
	}

	@Override
	protected Map<String, Object> generarParametros( DatosReporte datosDeReporte) 
	{

		Map<String, Object>  parametros = generadorReportesService.generarParametrosDefault();
		
		DatosReporteFactura dato = (DatosReporteFactura)datosDeReporte;
		parametros.put(PARAMETRO_NOMBRE_CLIENTE, dato.getCliente() );
		parametros.put(PARAMETRO_OBRA_SOCIAL,dato.getObraSocial() );
		parametros.put(PARAMETRO_NRO_FACTURA,dato.getNroFactura() );
		parametros.put(PARAMETRO_NRO_AFILIADO,dato.getNroAfiliado() );
		parametros.put(PARAMETRO_DIRECCION,dato.getDireccion() );
		parametros.put(PARAMETRO_CUIT,dato.getCUIT() );
		parametros.put(PARAMETRO_RESP_INSCRIPTO,dato.getRespInsc() );
		parametros.put(PARAMETRO_RESP_NO_INSCRIPTO,dato.getResNoInsc() );
		parametros.put(PARAMETRO_EXCENTO,dato.getExcento() );
		parametros.put(PARAMETRO_NO_RESP,dato.getNoResp() );
		parametros.put(PARAMETRO_CONS_FINAL,dato.getConsFinal() );
		parametros.put(PARAMETRO_CONTADO,dato.getContado() );
		parametros.put(PARAMETRO_CUENTA_CORRIENTE,dato.getCuentaCorriente() );
		parametros.put(PARAMETRO_TOTAL,dato.getTotal() );
		parametros.put(PARAMETRO_DESCRIP_MONTO,dato.getDescripcionMonto() );
		parametros.put(PARAMETRO_OBSERVACIONES,dato.getObservaciones() );
		parametros.put(PARAMETRO_FECHA_EMISION,dato.getFechaEmision());
		parametros.put(PARAMETRO_CODIGO_CONTABLE,dato.getCodigoContable());
		parametros.put(PARAMETRO_PRESTADOR,dato.getPrestador());
		parametros.put(PARAMETRO_AJUSTE_CENTAVOS,dato.getAjusteCentavos());
		
		
		
		return parametros;
	}

	@Override
	protected String generarNombreReporte(DatosReporte datosDeReporte ) 
	{
		String nombreArchivo = messageSupport.getProperty(NOMBRE_ARCHIVO);
		
		DatosReporteFactura dato = (DatosReporteFactura)datosDeReporte;
		nombreArchivo += "_"+dato.getNroFactura();
		
		nombreArchivo = this.generadorReportesService.generarNombreArchivoExportacion(nombreArchivo, new Date(), datosDeReporte.getTipoReporte().getExtension() );
		
		return nombreArchivo;
	}
	
	public ReporteFactura( GeneradorReportesService generadorService , PropertiesReader messageSupport )
	{
		super(generadorService, messageSupport);
	}


}
