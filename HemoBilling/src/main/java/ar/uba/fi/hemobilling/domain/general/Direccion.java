package ar.uba.fi.hemobilling.domain.general;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Direccion {

	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "localidad")
	private String localidad;
	
	@Column(name = "provincia")
	private String provincia;

	@Column(name = "pais")
	private String pais;
	
	@Column(name = "codigoPostal")
	private String codigoPostal;

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	@Override
	public String toString() {
		
		return this.direccion+" "+this.getLocalidad()+" "+this.getProvincia()+" "+this.getPais()+" ("+this.getCodigoPostal()+")";
	}

	
}
