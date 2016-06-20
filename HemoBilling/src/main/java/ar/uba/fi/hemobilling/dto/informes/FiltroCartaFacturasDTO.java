package ar.uba.fi.hemobilling.dto.informes;

import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;



public class FiltroCartaFacturasDTO 
{
	
	
	private String fechaDesde;
	
	private String fechaHasta;
	
	private ObraSocialDTO obraSocialActual = new ObraSocialDTO();
	
	public FiltroCartaFacturasDTO(){
		
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}


	public ObraSocialDTO getObraSocialActual() {
		return obraSocialActual;
	}

	public void setObraSocialActual(ObraSocialDTO obraSocialActual) {
		this.obraSocialActual = obraSocialActual;
	}
	
	
	
	
}
