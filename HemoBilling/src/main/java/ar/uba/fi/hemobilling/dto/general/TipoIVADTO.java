package ar.uba.fi.hemobilling.dto.general;




public class TipoIVADTO 
{
	
	private Long id;
	private String descripcion;
	
	public TipoIVADTO(){
		id=-1L;
		descripcion="Seleccionar uno";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
