package ar.uba.fi.hemobilling.dto.prestacionesBrindadas;

import ar.uba.fi.hemobilling.dto.BasicDTO;


public class PrestacionBrindadaDTO extends BasicDTO
{
	private String codigo;
	
	private String fecha;
	private String fechaEgreso;
	private String fechaImportacion;
	
	private String idPaciente;
	private String nroHCPaciente;
	private String nombrePaciente;	
	
	private String cantidadDePrestaciones="1";
	
	private String codPrestacion;
	private String nombrePrestacion;
	
	private String autorizacion;
	private String profesional;
	
	private String observaciones;
	
	private String precioManual;
	
	private boolean repetida;
	
	private Long codigoEnLaboratorio;
	
	private boolean moduloAgregado;
	
	/**
	 * @return the codigoEnLaboratorio
	 */
	public Long getCodigoEnLaboratorio() {
		return codigoEnLaboratorio;
	}

	/**
	 * @param codigoEnLaboratorio the codigoEnLaboratorio to set
	 */
	public void setCodigoEnLaboratorio(Long codigoEnLaboratorio) {
		this.codigoEnLaboratorio = codigoEnLaboratorio;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCantidadDePrestaciones() {
		return cantidadDePrestaciones;
	}

	public void setCantidadDePrestaciones(String cantidadDePrestaciones) {
		this.cantidadDePrestaciones = cantidadDePrestaciones;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getNombrePaciente() {
		return nombrePaciente;
	}

	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}

	public String getCodPrestacion() {
		return codPrestacion;
	}

	public void setCodPrestacion(String codPrestacion) {
		this.codPrestacion = codPrestacion;
	}

	public String getNombrePrestacion() {
		return nombrePrestacion;
	}

	public void setNombrePrestacion(String nombrePrestacion) {
		this.nombrePrestacion = nombrePrestacion;
	}

	public String getNroHCPaciente() {
		return nroHCPaciente;
	}

	public void setNroHCPaciente(String nroHCPaciente) {
		this.nroHCPaciente = nroHCPaciente;
	}

	public String getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(String idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getProfesional() {
		return profesional;
	}

	public void setProfesional(String profesional) {
		this.profesional = profesional;
	}

	public String getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(String fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public String getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(String fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public String getPrecioManual() {
		return precioManual;
	}

	public void setPrecioManual(String precioManual) {
		this.precioManual = precioManual;
	}

	public boolean isRepetida() {
		return repetida;
	}

	public void setRepetida(boolean repetida) {
		this.repetida = repetida;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((autorizacion == null) ? 0 : autorizacion.hashCode());
		result = prime
				* result
				+ ((cantidadDePrestaciones == null) ? 0
						: cantidadDePrestaciones.hashCode());
		result = prime * result
				+ ((codPrestacion == null) ? 0 : codPrestacion.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime
				* result
				+ ((fechaImportacion == null) ? 0 : fechaImportacion.hashCode());
		result = prime * result
				+ ((idPaciente == null) ? 0 : idPaciente.hashCode());
		result = prime * result
				+ ((nombrePaciente == null) ? 0 : nombrePaciente.hashCode());
		result = prime
				* result
				+ ((nombrePrestacion == null) ? 0 : nombrePrestacion.hashCode());
		result = prime * result
				+ ((nroHCPaciente == null) ? 0 : nroHCPaciente.hashCode());
		result = prime * result
				+ ((observaciones == null) ? 0 : observaciones.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrestacionBrindadaDTO other = (PrestacionBrindadaDTO) obj;
		if (autorizacion == null) {
			if (other.autorizacion != null)
				return false;
		} else if (!autorizacion.equals(other.autorizacion))
			return false;
		if (cantidadDePrestaciones == null) {
			if (other.cantidadDePrestaciones != null)
				return false;
		} else if (!cantidadDePrestaciones.equals(other.cantidadDePrestaciones))
			return false;
		if (codPrestacion == null) {
			if (other.codPrestacion != null)
				return false;
		} else if (!codPrestacion.equals(other.codPrestacion))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (fechaImportacion == null) {
			if (other.fechaImportacion != null)
				return false;
		} else if (!fechaImportacion.equals(other.fechaImportacion))
			return false;
		if (idPaciente == null) {
			if (other.idPaciente != null)
				return false;
		} else if (!idPaciente.equals(other.idPaciente))
			return false;
		if (nombrePaciente == null) {
			if (other.nombrePaciente != null)
				return false;
		} else if (!nombrePaciente.equals(other.nombrePaciente))
			return false;
		if (nombrePrestacion == null) {
			if (other.nombrePrestacion != null)
				return false;
		} else if (!nombrePrestacion.equals(other.nombrePrestacion))
			return false;
		if (nroHCPaciente == null) {
			if (other.nroHCPaciente != null)
				return false;
		} else if (!nroHCPaciente.equals(other.nroHCPaciente))
			return false;
		if (observaciones == null) {
			if (other.observaciones != null)
				return false;
		} else if (!observaciones.equals(other.observaciones))
			return false;
		return true;
	}

	/**
	 * @return the moduloAgregado
	 */
	public boolean isModuloAgregado() {
		return moduloAgregado;
	}

	/**
	 * @param moduloAgregado the moduloAgregado to set
	 */
	public void setModuloAgregado(boolean moduloAgregado) {
		this.moduloAgregado = moduloAgregado;
	}
	
	
	
	

}
