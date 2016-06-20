package ar.uba.fi.hemobilling.domain.prestacionesBrindadas;

import java.math.BigInteger;

public class DatosAdicionalesEstudioLaboratorioImportado 
{
	private BigInteger id;
	
	private String nombreyApellido;
	
	private String descripcion;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getNombreyApellido() {
		return nombreyApellido;
	}

	public void setNombreyApellido(String nombreyApellido) {
		this.nombreyApellido = nombreyApellido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	
}
