package ar.uba.fi.hemobilling.reportes.reporteador.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import ar.com.fdvs.dj.domain.DynamicReport;

public interface GeneradorReportesService {
	
	/**
	 * 
	 * Genera el nombre del archivo a exportar.
	 * 
	 * @param nombreReporte nombre del reporte a exportar.
	 * @param fechaGeneracion fecha de generacion de la exportacion.
	 * @param extension extension del archivo a exportar.
	 * @return nombre del archivo a exportar
	 */
	public String generarNombreArchivoExportacion(String nombreReporte, Date fechaGeneracion, String extension);
		
	/**
	 * 
	 * Genera el reporte como un array de bytes.
	 *
	 * @param tipoExportacion tipo de exportacion a realizar.
	 * @param templateDinamicoReporte template del reporte.
	 * @param datos datos de la consulta a exportar.
	 * @param parametros parametros a pasarle al reporte
	 * @return reporte como un array de bytes.
	 * @throws JRException
	 */
	public byte[] generarExportacion(ExportType tipoExportacion, DynamicReport templateDinamicoReporte, Collection<?> datos, Map<?, ?> parametros) throws JRException;

	/**
	 * 
	 * Genera el reporte como un array de bytes.
	 *
	 * @param tipoExportacion
	 * @param reportTemplatePath ruta del template del reporte (de extension .jrxml)
	 * @param datos datos de la consulta a exportar.
	 * @param parametros parametros a pasarle al reporte
	 * @return reporte como un array de bytes.
	 * @throws JRException
	 * @throws IOException
	 */
	public byte[] generarExportacion(ExportType tipoExportacion, String reportTemplatePath, Collection<?> datos, Map<?, ?> parametros) throws JRException, IOException ;

	/**
	 * 
	 * Genera el map con los parametros por default a pasarle al reporte. Es importante aclarar que este tipo de parametros tienen que modificarse con IReport. Los 
	 * items que no se repiten se crean como parametros y no como campos.
	 *
	 */
	public Map<String, Object> generarParametrosDefault();

	
	/**
	 * Devuelve un archivo generico indicando que se produjo un error al generarlo
	 * @param tipoExportacion
	 * @return
	 */
	byte[] getArchivoError(ExportType tipoExportacion);

	JasperPrint obtenerJasperPrint(ExportType tipoExportacion,
			DynamicReport templateDinamicoReporte, Collection<?> datos,
			Map<?, ?> parametros) throws JRException;

	byte[] generarPdfUnico(JasperPrint jp) throws JRException;
}