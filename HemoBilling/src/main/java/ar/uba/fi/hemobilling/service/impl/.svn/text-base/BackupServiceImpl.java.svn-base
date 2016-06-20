package ar.uba.fi.hemobilling.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.backup.backuper.Backuper;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.BackupDAO;
import ar.uba.fi.hemobilling.domain.backup.BackupRealizado;
import ar.uba.fi.hemobilling.dto.BackupRealizadoDTO;
import ar.uba.fi.hemobilling.service.BackupService;

@Service("backupService")
public class BackupServiceImpl implements BackupService 
{
	@Resource(name="backuper")
	private Backuper backuper;
	
	@Resource(name="backupDAOImpl")
	private BackupDAO backupDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;

	@Override
	public Boolean doBackup() 
	{
		Boolean exito = backuper.doBackup();
		
		if( exito )
		{
			BackupRealizado backup = new BackupRealizado();
			
			try 
			{
				backupDAO.agregar(backup);
			}
			catch (ObjectFoundException e) 
			{
				//Esto no va a suceder, la PK es incremental en el Domain Object
			}
		}
		
		return exito;		
	}

	@Override
	public Collection<BackupRealizadoDTO> getBackupsRealizados() {
		return mapper.map( backupDAO.getBackups() , BackupRealizadoDTO.class );
	}
	

}
