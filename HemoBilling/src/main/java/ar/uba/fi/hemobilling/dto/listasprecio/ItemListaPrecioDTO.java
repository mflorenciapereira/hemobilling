package ar.uba.fi.hemobilling.dto.listasprecio;

import ar.uba.fi.hemobilling.dto.prestaciones.PrestacionDTO;

public class ItemListaPrecioDTO {
	
	private Long id;
	private String codigo;
	private Double precio;
	private PrestacionDTO prestacion = new PrestacionDTO();
	private boolean incluido;
	private Long idlistaPrecioPertenece;
	

	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public PrestacionDTO getPrestacion() {
		return prestacion;
	}
	public void setPrestacion(PrestacionDTO prestacion) {
		this.prestacion = prestacion;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isIncluido() {
		return incluido;
	}
	public void setIncluido(boolean incluido) {
		this.incluido = incluido;
	}
	public Long getIdlistaPrecioPertenece() {
		return idlistaPrecioPertenece;
	}
	public void setIdlistaPrecioPertenece(Long idlistaPrecioPertenece) {
		this.idlistaPrecioPertenece = idlistaPrecioPertenece;
	}

	
}
