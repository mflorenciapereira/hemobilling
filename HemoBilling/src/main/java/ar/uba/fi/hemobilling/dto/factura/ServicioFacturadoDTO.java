package ar.uba.fi.hemobilling.dto.factura;


public class ServicioFacturadoDTO 
{
	private Integer id;

	private String fecha;

	private String codigo;

	private String cantidad;

	private String descripcion;

	private String importeUnitario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImporteUnitario() {
		return importeUnitario;
	}

	public void setImporteUnitario(String importeUnitario) {
		this.importeUnitario = importeUnitario;
	}
	
	

}
