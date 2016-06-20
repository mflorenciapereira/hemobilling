package ar.uba.fi.hemobilling.dto;

public class BackupRealizadoDTO extends BasicDTO
{
	private Long id;
	private String fechaRealizado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFechaRealizado() {
		return fechaRealizado;
	}
	public void setFechaRealizado(String fechaRealizado) {
		this.fechaRealizado = fechaRealizado;
	}
	
	
}
