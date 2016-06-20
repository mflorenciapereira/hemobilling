package ar.uba.fi.hemobilling.reportes.planillaFacturacion;

import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;

public class DatosReportePlanillaFacturacion extends DatosReporte {

	private String nroFactura;
	private String nombrePrestador; //muestra el dato que es para PAMI
	
	public String getNroFactura() {
		return nroFactura;
	}
	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}
	public String getNombrePrestador() {
		return nombrePrestador;
	}
	public void setNombrePrestador(String nombrePrestador) {
		this.nombrePrestador = nombrePrestador;
	}
	
}
