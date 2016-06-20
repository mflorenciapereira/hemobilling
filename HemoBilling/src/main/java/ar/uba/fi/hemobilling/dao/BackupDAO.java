package ar.uba.fi.hemobilling.dao;

import java.util.Collection;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.domain.backup.BackupRealizado;

public interface BackupDAO 
{
	public void agregar( BackupRealizado backup ) throws ObjectFoundException;

	public Collection<BackupRealizado> getBackups();

}
