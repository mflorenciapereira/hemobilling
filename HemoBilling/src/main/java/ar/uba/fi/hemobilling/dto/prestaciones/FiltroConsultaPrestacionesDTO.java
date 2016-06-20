package ar.uba.fi.hemobilling.dto.prestaciones;

import ar.uba.fi.hemobilling.dto.FiltroConsultaDTO;

public class FiltroConsultaPrestacionesDTO extends FiltroConsultaDTO
{
	protected String codigo; 		
	protected String descripcion;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	} 
	

	

}
