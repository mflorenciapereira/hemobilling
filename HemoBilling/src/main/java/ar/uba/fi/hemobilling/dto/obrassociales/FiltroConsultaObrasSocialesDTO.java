package ar.uba.fi.hemobilling.dto.obrassociales;

import ar.uba.fi.hemobilling.dto.FiltroConsultaDTO;
import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;

public class FiltroConsultaObrasSocialesDTO extends FiltroConsultaDTO
{
	private  String codigo; 	
	private String nombre;
	private String codigoRNOS;
	private String sigla;
	private String prestador;
	private String cuit;
	private String localidad;
	private String provincia;
	private TipoIVADTO tipoIVA;
	
	
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

	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getPrestador() {
		return prestador;
	}
	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}

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


	public TipoIVADTO getTipoIVA() {
		return tipoIVA;
	}
	public void setTipoIVA(TipoIVADTO tipoIVA) {
		this.tipoIVA = tipoIVA;
	}
	public String getCodigoRNOS() {
		return codigoRNOS;
	}
	public void setCodigoRNOS(String codigoRNOS) {
		this.codigoRNOS = codigoRNOS;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
	

	
	

	

}
