package ar.uba.fi.hemobilling.reportes.cartaDeFacturas;

import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;


public class DatosReporteCartaDeFacturas extends DatosReporte  {
	
	private String fecha;
	private String obraSocialDestino;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getObraSocialDestino() {
		return obraSocialDestino;
	}
	public void setObraSocialDestino(String obraSocialDestino) {
		this.obraSocialDestino = obraSocialDestino;
	}
	
	
}
