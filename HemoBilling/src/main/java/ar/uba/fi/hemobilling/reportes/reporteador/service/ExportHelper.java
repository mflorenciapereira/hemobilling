package ar.uba.fi.hemobilling.reportes.reporteador.service;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class ExportHelper 
{
	private static final String PRIVATE_MUST_REVALIDATE = "private, must-revalidate";
	private static final String CACHE_CONTROL = "Cache-Control";
	private static final String PRIVATE = "private";
	private static final String PRAGMA = "Pragma";
	private static final String COMILLA_DE_CIERRE = "\"";
	private static final String ATTHACHMENT_FILENAME = "attachment; filename=\"";
	private static final String CONTENT_DISPOSITION = "Content-disposition";
	private static final String APPLICATION = "application/";
	
	public static final String OCTET_STREAM = "octet-stream";
	public static final String PDF = "pdf";

	/**
	 * 
	 * Genera el response para la descarga de un archivo.
	 *
	 * @param response response donde se escribe la respuesta del servidor.
	 * @param nombreArchivo nombre del archivo a descargar
	 * @param contenidoArchivo array de bytes que contiene el archivo a descargar.
	 * @param contentType content type del archivo a descargar
	 * @throws IOException
	 */
	public static void generarRespuesta(HttpServletResponse response, String nombreArchivo, byte[] contenidoArchivo, String contentType) throws IOException{
		response.setContentType(APPLICATION + contentType);
		response.setContentLength(contenidoArchivo.length);
		response.addHeader(CONTENT_DISPOSITION, ATTHACHMENT_FILENAME + nombreArchivo + COMILLA_DE_CIERRE);
		setCacheHeaders(response);
		OutputStream os  = response.getOutputStream();
		os.write(contenidoArchivo);
		os.flush();
		os.close();
	}
	
	/**
	 * 
	 * Genera el response para la descarga de un archivo de tipo octect-stream
	 * (puede ser cualquier tipo de archivo).
	 *
	 * @param response response donde se escribe la respuesta del servidor.
	 * @param nombreArchivo nombre del archivo a descargar
	 * @param contenidoArchivo array de bytes que contiene el archivo a descargar.
	 * @throws IOException
	 */
	public static void generarRespuesta(HttpServletResponse response, String nombreArchivo, byte[] contenidoArchivo) throws IOException{
		generarRespuesta(response, nombreArchivo, contenidoArchivo, OCTET_STREAM);
	}
	
	/** Seteo de los parametros de la respuesta para el cache, para evitar errores en IE al querer
	 * abrir directamente el archivo adjunto */
	private static void setCacheHeaders(HttpServletResponse response) {
		response.setHeader(PRAGMA, PRIVATE);
		response.setHeader(CACHE_CONTROL, PRIVATE_MUST_REVALIDATE);
	}
}
