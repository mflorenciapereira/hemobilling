package ar.uba.fi.hemobilling.dto.prestacionesBrindadas;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.AutoPopulatingList;

import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.dto.BasicDTO;

public class ResultConsultaAnalisisLaboratorioDTO extends BasicDTO 
{
	private AutoPopulatingList analisisAprobados = new AutoPopulatingList(PrestacionBrindadaDTO.class);
	private Collection<EstudioLaboratorioDTO> analisisRechazados = new ArrayList<EstudioLaboratorioDTO>();
	
	private Integer cantidadPrestacionesAprobadas;
	
	public AutoPopulatingList getAnalisisAprobados() {
		return analisisAprobados;
	}
	public void setAnalisisAprobados(AutoPopulatingList analisisAprobados) {
		this.analisisAprobados = analisisAprobados;
	}
	public Integer getCantidadPrestacionesAprobadas() {
		return cantidadPrestacionesAprobadas;
	}
	public void setCantidadPrestacionesAprobadas(Integer cantidadPrestacionesAprobadas) {
		this.cantidadPrestacionesAprobadas = cantidadPrestacionesAprobadas;
	}
	public Collection<EstudioLaboratorioDTO> getAnalisisRechazados() {
		return analisisRechazados;
	}
	public void setAnalisisRechazados(Collection<EstudioLaboratorioDTO> analisisRechazados) {
		this.analisisRechazados = analisisRechazados;
	}
	
	
}
