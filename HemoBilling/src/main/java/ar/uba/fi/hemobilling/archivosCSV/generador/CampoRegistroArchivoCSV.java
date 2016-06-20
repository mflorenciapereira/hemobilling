package ar.uba.fi.hemobilling.archivosCSV.generador;

public class CampoRegistroArchivoCSV
{
	public static String ALINEACION_IZQUIERDA = "IZQUIERDA";
	public static String ALINEACION_DERECHA = "DERECHA";
	
	public static char TIPO_RELLENO_BLANCO = ' ';
	public static char TIPO_RELLENO_CERO = '0';
	
	private Integer longitud;
	private String alineacion;
	private char tipoRelleno;
	private String dato;
	
	public Integer getLongitud() {
		return longitud;
	}
	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}
	public String getAlineacion() {
		return alineacion;
	}
	public void setAlineacion(String alineacion) {
		this.alineacion = alineacion;
	}
	public char getTipoRelleno() {
		return tipoRelleno;
	}
	public void setTipoRelleno(char tipoRelleno) {
		this.tipoRelleno = tipoRelleno;
	}
	public String getDato() {
		return dato;
	}
	public void setDato(String dato) {
		this.dato = dato;
	}
	
	

}
