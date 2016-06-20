package ar.uba.fi.hemobilling.dto.clientes;

import ar.uba.fi.hemobilling.dto.FiltroConsultaDTO;
import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;

public class FiltroConsultaClientesDTO extends FiltroConsultaDTO
{
	private  String codigo; 	
	private String nombre;
	private String codigoContable;
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

	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	public String getCodigoContable() {
		return codigoContable;
	}
	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}
	
	

	
	

	

}
