package ar.uba.fi.hemobilling.dto.paciente;

import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;
import ar.uba.fi.hemobilling.dto.BasicDTO;

public class ResultConsultaPacienteHCDTO {
	
	private BasicDTO result;
	private PacienteHCDTO paciente;
	
	public BasicDTO getResult() {
		return result;
	}
	public void setResult(BasicDTO result) {
		this.result = result;
	}
	public PacienteHCDTO getPaciente() {
		return paciente;
	}
	public void setPaciente(PacienteHCDTO paciente) {
		this.paciente = paciente;
	}
	
	

}
