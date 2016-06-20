package ar.uba.fi.hemobilling.commons.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import ar.uba.fi.hemobilling.commons.dto.EstudioLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteLaboratorioDTO;

public interface HBLaboratorioRemoteInterface extends Remote {
	
	public PacienteLaboratorioDTO getPacienteLaboratorio( Integer ID ) throws RemoteException;
	
	public Boolean actualizarNumeroHistoriaClinica( Integer codigoViejo , Integer nuevoNumHC ) throws RemoteException;
	
	public Collection<EstudioLaboratorioDTO> buscarAnalisisLaboratorio( FiltroConsultaEstudiosLaboratorioDTO filtro )  throws RemoteException;

}
