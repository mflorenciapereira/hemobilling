package ar.uba.fi.hemobilling.dto.prestaciones;


import org.springframework.util.AutoPopulatingList;


public class PrestacionDTO 
{
	private long codigo;
	private String descripcion;
	private	AutoPopulatingList prestacionesAsociadasAuto=new AutoPopulatingList(PrestacionDTO.class);
	private int cantidadPrestacionesAsociadas;

	
	
	
	public PrestacionDTO() {
		super();
		
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public AutoPopulatingList getPrestacionesAsociadasAuto() {
		return prestacionesAsociadasAuto;
	}
	public void setPrestacionesAsociadasAuto(AutoPopulatingList prestacionesAsociadasAuto) {
		this.prestacionesAsociadasAuto = prestacionesAsociadasAuto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codigo ^ (codigo >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrestacionDTO other = (PrestacionDTO) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}
	public int getCantidadPrestacionesAsociadas() {
		return cantidadPrestacionesAsociadas;
	}
	public void setCantidadPrestacionesAsociadas(
			int cantidadPrestacionesAsociadas) {
		this.cantidadPrestacionesAsociadas = cantidadPrestacionesAsociadas;
	}






	

}
