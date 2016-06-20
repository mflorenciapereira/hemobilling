package ar.uba.fi.hemobilling.reportes.sintesis;

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

public class SintesisHC extends Reporte 
{
	private static final String NOMBRE_ARCHIVO = "sintesis.nombreArchivo";
	private static final String NOMBRE_ARCHIVO_TEMPLATE = "sintesis.template";
	
	private static final String ATRIBUTO_FECHA_DTO = "fecha";
	private static final String ATRIBUTO_PRESTACION_DTO = "prestacion";
	
	private static final String PARAMETRO_OBRA_SOCIAL = "OBRA_SOCIAL";
	private static final String PARAMETRO_PACIENTE = "PACIENTE";
	private static final String PARAMETRO_NROAFILIADO = "NROAFILIADO";
	private static final String PARAMETRO_DIAGNOSTICO = "DIAGNOSTICO";
	
	public SintesisHC(GeneradorReportesService generadorService, PropertiesReader messageSupport) {
		super(generadorService, messageSupport);
	}
	
	@Override
	protected DynamicReport createDynamicReport(DatosReporte datosDeReporte) throws ColumnBuilderException {
		ReporteBuilder rb = new ReporteBuilder();
		
		rb.setTemplateReporte(messageSupport.getProperty(NOMBRE_ARCHIVO_TEMPLATE));
		
		//rb.addTitulo(HTMLEntities.unhtmlentities_custom(messageSupport.getProperty(TITULO_REPORTE)), ReporteBuilder.getEstiloTitulo(), 30);
		
		rb.setAltoEncabezado(200);
		
		rb.setAltoDetalle(20);
		rb.setAltoPie(100);		
		rb.addColumnaTexto(ATRIBUTO_FECHA_DTO, " ", 80, ReportAlign.ALIGN_CENTER );
		rb.addColumnaTexto(ATRIBUTO_PRESTACION_DTO, " ", 70, ReportAlign.ALIGN_LEFT);
		
		return rb.build();
	}
	@Override
	protected Map<String, Object> generarParametros(DatosReporte datosDeReporte) {
		//Ademas de los default, se debe mostrar el nombre de la lista y su fecha de vigencia
		
		Map<String, Object>  parametros = generadorReportesService.generarParametrosDefault();
		
		DatosReporteSintesisHC dato = (DatosReporteSintesisHC)datosDeReporte;
		parametros.put(PARAMETRO_OBRA_SOCIAL, dato.getObraSocial() );
		parametros.put(PARAMETRO_PACIENTE,dato.getPaciente() );
		parametros.put(PARAMETRO_NROAFILIADO, dato.getNroAfiliado() );
		parametros.put(PARAMETRO_DIAGNOSTICO,dato.getDiagnostico() );
		
		return parametros;
	}
	@Override
	protected String generarNombreReporte(DatosReporte datosDeReporte)
	{
		String nombreArchivo = messageSupport.getProperty(NOMBRE_ARCHIVO);
		
		DatosReporteSintesisHC dato = (DatosReporteSintesisHC)datosDeReporte;
		nombreArchivo += "_" + dato.getObraSocial();
		
		nombreArchivo = this.generadorReportesService.generarNombreArchivoExportacion(nombreArchivo, new Date(), datosDeReporte.getTipoReporte().getExtension() );
		
		return nombreArchivo;
	}
	

}
