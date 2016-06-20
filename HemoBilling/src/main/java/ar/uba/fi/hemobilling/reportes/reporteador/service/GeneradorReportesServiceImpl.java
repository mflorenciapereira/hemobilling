package ar.uba.fi.hemobilling.reportes.reporteador.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.lang.CharEncoding;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;


@Service("generadorReportesService")
public class GeneradorReportesServiceImpl implements GeneradorReportesService {
	
	/**
	 * 
	 */
	private static final String SEPARADOR_DE_LINEAS = "\r\n";

	private static Logger log = Logger.getLogger(GeneradorReportesServiceImpl.class);

	private static final String HORA_CONSULTA = "HORA_CONSULTA";
	private static final String FECHA_CONSULTA = "FECHA_CONSULTA";
	
	private static final String FILENAME_FORMAT = "%s_%s.%s";
	private static final String FORMATO_FECHA_Y_HORA = "yyyy_MM_dd_HH_mm_ss";
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private static final String FORMATO_HORA = "HH:mm";
	
	private static final String GENERAR_TXT_LOG = "generarTxt";
	private static final String GENERAR_XLS_LOG = "generarXLS";
	private static final String GENERAR_CSV_LOG = "generarCSV";
	private static final String GENERAR_PDF_LOG = "generarPdf";
	private static final String COMIENZA_LOG = "Comienza ";
	private static final String FINALIZA_LOG = "Finaliza ";
	private static final String NOMBRE_DE_ARCHIVO_GENERADO_LOG = "Nombre de archivo generado: ";
	private static final String GENERAR_EXPORTACION_CON_JRXML = "generarExportacion con jrxml";
	private static final String GENERAR_EXPORTACION_CON_DYNAMIC_REPORT = "generarExportacion con DynamicReport";
	
	private JasperPrint getJasperPrint (DynamicReport dr, Collection<?> datos, Map<?, ?> parametros) throws JRException{
		
		JasperReport jr = this.generateJasperReport(dr, parametros);
		
		JRDataSource ds = new JRBeanCollectionDataSource(datos);
		
		JasperPrint jp = JasperFillManager.fillReport(jr, parametros, ds);

		return jp;
	}

	private synchronized JasperReport generateJasperReport(DynamicReport dr, Map<?, ?> parametros) throws JRException {
		return DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), parametros);
	}
	
	private JasperPrint getJasperPrint (String reportTemplatePath, Collection<?> datos, Map<?, ?> parametros) throws JRException, IOException{
		
		JasperReport jr = JasperCompileManager.compileReport(new ClassPathResource(reportTemplatePath).getInputStream());
		JRDataSource ds = new JRBeanCollectionDataSource(datos);
		
		JasperPrint jp = JasperFillManager.fillReport(jr, parametros, ds);
		
		return jp;
	}
	
	private byte[] generarExportacion(ExportType tipoExportacion, JasperPrint jp) throws JRException{
		byte[] rta = null;
		
		try{
			switch(tipoExportacion){
			case TXT:
				rta = this.generarTxt(jp);
				break;
			case CSV:
				rta = this.generarCSV(jp);
				break;
			case EXCEL:
				rta = this.generarXLS(jp);
				break;
			case PDF:
				rta = this.generarPdf(jp);
				break;
			}
		}catch (Exception e){
			log.error("Error al querer generar el archivo de exportacion", e);
			rta = this.getArchivoError(tipoExportacion);
		}
		
		return rta;
	}
	
	private byte[] generarTxt(JasperPrint jp) throws JRException {
		log.info(COMIENZA_LOG + GENERAR_TXT_LOG);
		
		StringBuffer rta = new StringBuffer();
		
		JRTextExporter txtExporter = new JRTextExporter();
		txtExporter.setParameter(JRTextExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		txtExporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, Integer.valueOf(6));
		txtExporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, Integer.valueOf(10));
		txtExporter.setParameter(JRTextExporterParameter.JASPER_PRINT, jp);
		txtExporter.setParameter(JRTextExporterParameter.OUTPUT_STRING_BUFFER, rta);
		txtExporter.setParameter(JRTextExporterParameter.LINE_SEPARATOR, SEPARADOR_DE_LINEAS);
		txtExporter.exportReport();

		log.info(FINALIZA_LOG + GENERAR_TXT_LOG);
		return rta.toString().getBytes();
	}

	private byte[] generarXLS(JasperPrint jp) throws JRException {
		log.info(COMIENZA_LOG + GENERAR_XLS_LOG);
			
		ByteArrayOutputStream rta = new ByteArrayOutputStream();
		
		JRXlsExporter xlsExporter = new JRXlsExporter();
		xlsExporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, rta);
		
		xlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		xlsExporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
		xlsExporter.exportReport();
		
		log.info(FINALIZA_LOG + GENERAR_XLS_LOG);
		return rta.toByteArray();
	}
	
	private byte[] generarCSV(JasperPrint jp) throws JRException {
		log.info(COMIENZA_LOG + GENERAR_CSV_LOG);
		StringBuffer rta = new StringBuffer();
		jp.setProperty(JRCsvExporterParameter.PROPERTY_RECORD_DELIMITER, SEPARADOR_DE_LINEAS);
		
		JRCsvExporter csvExporter = new JRCsvExporter();
		csvExporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING, "UTF-8");
		csvExporter.setParameter(JRCsvExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		csvExporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, ";");
		csvExporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jp);
		csvExporter.setParameter(JRCsvExporterParameter.OUTPUT_STRING_BUFFER, rta);
		csvExporter.exportReport();
		
		log.info(FINALIZA_LOG + GENERAR_CSV_LOG);
		return rta.toString().getBytes();
	}

	private byte[] generarPdf(JasperPrint jp) throws JRException{
		log.info(COMIENZA_LOG + GENERAR_PDF_LOG);
		ByteArrayOutputStream rta = new ByteArrayOutputStream();
		
		JRPdfExporter exporter = new JRPdfExporter();
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, rta);
		exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, CharEncoding.UTF_8);
		exporter.exportReport();
        
		log.info(FINALIZA_LOG + GENERAR_PDF_LOG);
		return rta.toByteArray();
	}
	
	
	/*
	 * Implementacion de la interfaz ---------------------------------------------------------------------------------
	 * 
	 */
	
	@Override
	public byte[] generarExportacion(ExportType tipoExportacion,
			String reportTemplatePath, Collection<?> datos, Map<?, ?> parametros)
			throws IOException  {
		log.info(COMIENZA_LOG + GENERAR_EXPORTACION_CON_JRXML);
		try{
			JasperPrint jp = getJasperPrint (reportTemplatePath,datos,parametros);
			
			log.info(FINALIZA_LOG + GENERAR_EXPORTACION_CON_JRXML);
			return generarExportacion(tipoExportacion, jp);
		}catch (JRException e){
			log.error("Error al querer generar el archivo de exportacion", e);
			return new byte[]{};
		}
	}
	
	@Override
	public byte[] generarExportacion(ExportType tipoExportacion, DynamicReport templateDinamicoReporte, Collection<?> datos, Map<?, ?> parametros) throws JRException {
		log.info(COMIENZA_LOG + GENERAR_EXPORTACION_CON_DYNAMIC_REPORT);
		
		if(tipoExportacion.equals(ExportType.PDF)){
			//Agregado del paginado
//			AutoText at = new AutoText(AutoText.AUTOTEXT_PAGE_X, AutoText.POSITION_FOOTER, HorizontalBandAlignment.RIGHT);
//			at.setFixedWith(true);
//			List<AutoText> autoTexts = new ArrayList<AutoText>();
//			autoTexts.add(at);
//			templateDinamicoReporte.setAutoTexts(autoTexts);
		}
		
		JasperPrint jp = getJasperPrint (templateDinamicoReporte, datos,parametros);
		log.info(FINALIZA_LOG + GENERAR_EXPORTACION_CON_DYNAMIC_REPORT);
		return generarExportacion(tipoExportacion, jp);
	}
	

	
	@Override
	public byte[] getArchivoError(ExportType tipoExportacion){
		byte[] rta = null;
		InputStream  input = GeneradorReportesServiceImpl.class.getResourceAsStream("/reportes/reporteError."+tipoExportacion.getExtension());
		try {
			rta = IOUtils.toByteArray(input);
		} catch (IOException e1) {
			log.error("Error al querer obtener el archivo de error de exportacion", e1);
			rta = new byte[]{};
		}
		return rta;
	}
	

	@Override
	public String generarNombreArchivoExportacion(String nombreReporte, Date fechaGeneracion, String extension){
		DateFormat df = new SimpleDateFormat(FORMATO_FECHA_Y_HORA);
		String rta = String.format(FILENAME_FORMAT, nombreReporte, df.format(fechaGeneracion), extension);
		log.info(NOMBRE_DE_ARCHIVO_GENERADO_LOG + rta);
		return rta;
	}
	
	@Override
	public Map<String, Object> generarParametrosDefault() {
		
		Date fechaGeneracion = new Date();
		
		DateFormat dfFecha = new SimpleDateFormat(FORMATO_FECHA);
		DateFormat dfHora = new SimpleDateFormat(FORMATO_HORA);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put(FECHA_CONSULTA, dfFecha.format(fechaGeneracion) );
		parametros.put(HORA_CONSULTA,dfHora.format(fechaGeneracion) );
		
		return parametros;
	}
	
	@Override
	public JasperPrint obtenerJasperPrint(ExportType tipoExportacion, DynamicReport templateDinamicoReporte, Collection<?> datos, Map<?, ?> parametros) throws JRException {
		JasperPrint jp = getJasperPrint (templateDinamicoReporte, datos,parametros);
		return jp;
	}
	
	
	@Override
	public byte[] generarPdfUnico(JasperPrint jp) throws JRException{
	
		return this.generarPdf(jp);
	}
	
}
