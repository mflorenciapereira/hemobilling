package ar.uba.fi.hemobilling.domain.listasprecio;

import java.sql.Date;

import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;

public class FiltroConsultaListasPrecio extends FiltroConsulta 
{
	private  String codigo; 	
	private String nombre;
	private Date desde;
	private Date hasta;
	private	ObraSocial obraSocial;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getDesde() {
		return desde;
	}
	public void setDesde(Date desde) {
		this.desde = desde;
	}
	public Date getHasta() {
		return hasta;
	}
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
	public ObraSocial getObraSocial() {
		return obraSocial;
	}
	public void setObraSocial(ObraSocial obraSocial) {
		this.obraSocial = obraSocial;
	}

	
	

	

}
