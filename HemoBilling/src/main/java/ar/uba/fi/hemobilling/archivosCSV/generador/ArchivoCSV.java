package ar.uba.fi.hemobilling.archivosCSV.generador;

import java.util.Collection;

public class ArchivoCSV 
{
	public static char SEPARADOR_COMA = ',';
	public static char SEPARADOR_PUNTO_COMA = ';';
	
	public static String FIN_LINEA_WINDOWS = "\n\r";
	public static String FIN_LINEA_UNIX = "\n";
	
	private Collection<RegistroArchivoCSV> registros;
	private char separadorCampo;
	private String finLinea;
	private String nombreArchivo;

	public Collection<RegistroArchivoCSV> getRegistros() {
		return registros;
	}

	public void setRegistros(Collection<RegistroArchivoCSV> registros) {
		this.registros = registros;
	}

	public char getSeparadorCampo() {
		return separadorCampo;
	}

	public void setSeparadorCampo(char separadorCampo) {
		this.separadorCampo = separadorCampo;
	}

	public String getFinLinea() {
		return finLinea;
	}

	public void setFinLinea(String finLinea) {
		this.finLinea = finLinea;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	

}
