package ar.uba.fi.hemobilling.reportes.listaPrecios;

import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;

public class DatosReporteListaPrecios extends DatosReporte
{
	private String nombreLista;
	private String fechaVigencia;
	
	public String getFechaVigencia() {
		return fechaVigencia;
	}
	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}
	public String getNombreLista() {
		return nombreLista;
	}
	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}
	
	

}
