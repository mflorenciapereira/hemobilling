package ar.uba.fi.hemobilling.reportes.factura;

import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;

//En este reporte no hay datos adicionales, solo la lista de elementos
public class DatosReporteFactura extends DatosReporte 
{
	private String cliente;
	private String obraSocial;
	private String nroFactura;
	private String nroAfiliado;
	private String direccion;
	private String fechaEmision;
	
	private String CUIT;
	private String respInsc;
	private String resNoInsc;
	private String excento;
	private String noResp;
	private String consFinal;

	private String contado;
	private String cuentaCorriente;
	
	private String codigoContable;
	
	private String total;
	private String descripcionMonto;
	private String observaciones;
	private String prestador;
	private String vacio;
	
	private String ajusteCentavos;
	
	private boolean fondo=true;
	
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getObraSocial() {
		return obraSocial;
	}
	public void setObraSocial(String obraSocial) {
		this.obraSocial = obraSocial;
	}
	public String getNroFactura() {
		return nroFactura;
	}
	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}
	public String getNroAfiliado() {
		return nroAfiliado;
	}
	public void setNroAfiliado(String nroAfiliado) {
		this.nroAfiliado = nroAfiliado;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCUIT() {
		return CUIT;
	}
	public void setCUIT(String cUIT) {
		CUIT = cUIT;
	}
	public String getRespInsc() {
		return respInsc;
	}
	public void setRespInsc(String respInsc) {
		this.respInsc = respInsc;
	}
	public String getResNoInsc() {
		return resNoInsc;
	}
	public void setResNoInsc(String resNoInsc) {
		this.resNoInsc = resNoInsc;
	}
	public String getExcento() {
		return excento;
	}
	public void setExcento(String excento) {
		this.excento = excento;
	}
	public String getNoResp() {
		return noResp;
	}
	public void setNoResp(String noResp) {
		this.noResp = noResp;
	}
	public String getConsFinal() {
		return consFinal;
	}
	public void setConsFinal(String consFinal) {
		this.consFinal = consFinal;
	}
	public String getContado() {
		return contado;
	}
	public void setContado(String contado) {
		this.contado = contado;
	}
	public String getCuentaCorriente() {
		return cuentaCorriente;
	}
	public void setCuentaCorriente(String cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDescripcionMonto() {
		return descripcionMonto;
	}
	public void setDescripcionMonto(String descripcionMonto) {
		this.descripcionMonto = descripcionMonto;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Boolean getFondo() {
		return fondo;
	}
	public void setFondo(Boolean fondo) {
		this.fondo = fondo;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getCodigoContable() {
		return codigoContable;
	}
	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}
	public String getPrestador() {
		return prestador;
	}
	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}
	public String getAjusteCentavos() {
		return ajusteCentavos;
	}
	public void setAjusteCentavos(String ajusteCentavos) {
		this.ajusteCentavos = ajusteCentavos;
	}
	
	
}
