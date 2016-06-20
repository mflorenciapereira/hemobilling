package ar.uba.fi.hemobilling.reportes.reporteador;

import java.util.Collection;

import ar.uba.fi.hemobilling.reportes.reporteador.service.ExportType;

/**
 * Esta clase contiene toda la informacion necesaria para emitir el reporte
 * Como minimo tiene que tener la lista de DTOs a mostrar, luego, las 
 * clases que la extiendan agregaran sus datos particulares
 * 
 * @author Ale
 *
 */
public class DatosReporte {

	private Collection<?> datosAListar;
	
	private ExportType tipoReporte;

	public Collection<?> getDatosAListar() {
		return datosAListar;
	}

	public void setDatosAListar(Collection<?> datosAListar) {
		this.datosAListar = datosAListar;
	}

	public ExportType getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(ExportType tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	
	
	
}
