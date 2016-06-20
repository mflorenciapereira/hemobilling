package ar.uba.fi.hemobilling.dto.obrassociales;




public class PlanDTO 
{
	private Long id;
	private String codigo;
	private String nombre;
	private ObraSocialDTO obraSocial;
	


	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public ObraSocialDTO getObraSocial() {
		return obraSocial;
	}
	public void setObraSocial(ObraSocialDTO obraSocial) {
		this.obraSocial = obraSocial;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	






	

}
