package ar.uba.fi.hemobilling.commons.remoteInterfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;

public interface HemoBillingHCRemoteInterface extends Remote {
	
	public PacienteHCDTO getPacienteHC( Integer ID ) throws RemoteException;

}
