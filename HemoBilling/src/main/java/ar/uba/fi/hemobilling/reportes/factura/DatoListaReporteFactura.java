package ar.uba.fi.hemobilling.reportes.factura;

/**
 * Esta clase contiene los campos de una fila del reporte Factura
 * Notar que las columnas del JRXML de este reporte estan mapeadas a los atributos de esta clase
 * (ver createDynamicReport del ReporteFactura )
 * 
 * @author Ale
 *
 */
public class DatoListaReporteFactura 
{
	private String fecha;
	private String codigo;
	private String cantidad;
	private String prestacion;
	private String importe;
	private String honorarios;
	private String total;
	private String vacio;
	
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
	public String getPrestacion() {
		return prestacion;
	}
	public void setPrestacion(String prestacion) {
		this.prestacion = prestacion;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getHonorarios() {
		return honorarios;
	}
	public void setHonorarios(String honorarios) {
		this.honorarios = honorarios;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getVacio() {
		return vacio;
	}
	public void setVacio(String vacio) {
		this.vacio = vacio;
	}
	
	

}
