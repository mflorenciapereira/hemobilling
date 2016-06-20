package ar.uba.fi.hemobilling.reportes.planillaFacturacion;

import java.math.BigDecimal;

/**
 * Esta clase contiene los campos de una fila del reporte Planilla de Facturacion
 * Notar que las columnas del JRXML de este reporte estan mapeadas a los atributos de esta clase
 * (ver createDynamicReport del PlanillaFacturacion)
 * 
 * @author Ale
 *
 */
public class DatoListaReportePlanillaFacturacion {
	
	private String prestacion;
	private String cantidad;
	private String fechaIngreso;
	private String fechaEgreso;
	private String nombreAfiliado;
	private String nroBeneficiario;
	private String nroHC;
	private String nroOp;
	private String observaciones;
	private String capita;
	private BigDecimal monto;
	
	public String getPrestacion() {
		return prestacion;
	}
	public void setPrestacion(String prestacion) {
		this.prestacion = prestacion;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getFechaEgreso() {
		return fechaEgreso;
	}
	public void setFechaEgreso(String fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}
	public String getNombreAfiliado() {
		return nombreAfiliado;
	}
	public void setNombreAfiliado(String nombreAfiliado) {
		this.nombreAfiliado = nombreAfiliado;
	}
	public String getNroBeneficiario() {
		return nroBeneficiario;
	}
	public void setNroBeneficiario(String nroBeneficiario) {
		this.nroBeneficiario = nroBeneficiario;
	}
	public String getNroHC() {
		return nroHC;
	}
	public void setNroHC(String nroHC) {
		this.nroHC = nroHC;
	}
	public String getNroOp() {
		return nroOp;
	}
	public void setNroOp(String nroOp) {
		this.nroOp = nroOp;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getCapita() {
		return capita;
	}
	public void setCapita(String capita) {
		this.capita = capita;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	
	
	
	

}
