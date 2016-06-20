package ar.uba.fi.hemobilling.commons.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltroConsultaEstudiosLaboratorioDTO implements Serializable {

	private static final long serialVersionUID = 4941658324739749028L;
	
	private Date fechaInicio;
	
	private Date fechaFinal;

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	

}
