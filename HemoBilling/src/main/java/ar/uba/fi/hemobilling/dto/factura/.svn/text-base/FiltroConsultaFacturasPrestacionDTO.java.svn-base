package ar.uba.fi.hemobilling.dto.factura;

import org.springframework.util.AutoPopulatingList;

import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;


public class FiltroConsultaFacturasPrestacionDTO 
{
	private String numero;
	
	private String fechaDesde;
	
	private String fechaHasta;
	
	private	AutoPopulatingList obrasSociales=new AutoPopulatingList(ObraSocialDTO.class);

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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

	public AutoPopulatingList getObrasSociales() {
		return obrasSociales;
	}

	public void setObrasSociales(AutoPopulatingList obrasSociales) {
		this.obrasSociales = obrasSociales;
	}
	
	
}
