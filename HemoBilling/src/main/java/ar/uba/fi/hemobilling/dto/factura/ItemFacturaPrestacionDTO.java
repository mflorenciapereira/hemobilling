package ar.uba.fi.hemobilling.dto.factura;


public class ItemFacturaPrestacionDTO 
{
	private Long codigoPrestacionBrindada;
	private String fecha;
	private String cantidad;
	
	private Long  idPaciente;
	private String nombrePaciente;
	private String numeroHC;
	
	private Long codigoPrestacion;
	private String descripcionPrestacion;
	private String codigoPrestacionSegunOS;

	private String precio;

	public Long getCodigoPrestacionBrindada() {
		return codigoPrestacionBrindada;
	}

	public void setCodigoPrestacionBrindada(Long codigoPrestacionBrindada) {
		this.codigoPrestacionBrindada = codigoPrestacionBrindada;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	public String getNumeroHC() {
		return numeroHC;
	}

	public void setNumeroHC(String numeroHC) {
		this.numeroHC = numeroHC;
	}

	public Long getCodigoPrestacion() {
		return codigoPrestacion;
	}

	public void setCodigoPrestacion(Long codigoPrestacion) {
		this.codigoPrestacion = codigoPrestacion;
	}

	public String getDescripcionPrestacion() {
		return descripcionPrestacion;
	}

	public void setDescripcionPrestacion(String descripcionPrestacion) {
		this.descripcionPrestacion = descripcionPrestacion;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getCodigoPrestacionSegunOS() {
		return codigoPrestacionSegunOS;
	}

	public void setCodigoPrestacionSegunOS(String codigoPrestacionSegunOS) {
		this.codigoPrestacionSegunOS = codigoPrestacionSegunOS;
	}

}
