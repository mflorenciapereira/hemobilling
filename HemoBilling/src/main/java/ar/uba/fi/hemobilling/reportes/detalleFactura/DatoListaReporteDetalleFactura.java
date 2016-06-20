package ar.uba.fi.hemobilling.reportes.detalleFactura;

import java.math.BigDecimal;

/**
 * Esta clase contiene los campos de una fila del reporte Lista de Detalle de Factura
 * Notar que las columnas del JRXML de este reporte estan mapeadas a los atributos de esta clase
 * (ver createDynamicReport del DetalleFactura )
 * 
 * @author Ale
 *
 */
public class DatoListaReporteDetalleFactura {
	
	private String fecha;
	private String codigo;
	private String cantidad;
	private String prestacion;
	private String importe;
	private BigDecimal total;
	
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
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	

}
