package ar.uba.fi.hemobillingLaboService.domain;

import java.util.Date;

public class FiltroConsultaAnalisisLaboratorio 
{
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
