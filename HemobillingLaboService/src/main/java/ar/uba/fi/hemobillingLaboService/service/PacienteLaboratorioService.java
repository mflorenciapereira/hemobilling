package ar.uba.fi.hemobillingLaboService.service;

import java.util.Collection;

import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteLaboratorioDTO;

public interface PacienteLaboratorioService {
	
	public PacienteLaboratorioDTO getPaciente( Integer numHC ) throws Exception;
	
	public Boolean actualizarNumeroHistoriaClinica( Integer codigoInicial , Integer nuevoNumHC ) throws Exception;
	
	public Collection<EstudioLaboratorioDTO> buscarAnalisisLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtro ) throws Exception;

}
