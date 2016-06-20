package ar.uba.fi.hemobilling.controller;

import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.uba.fi.hemobilling.dto.BackupRealizadoDTO;
import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.service.BackupService;

@Controller
public class BackupController extends AbstractController 
{
	private static final String JSP_BACKUPS = "paginaBackups";
	
	private static final String LISTABACKUPSDTO = "backupsRealizadosDTO";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	
	private static final String MENSAJE_EXITO_BACKUP = "backuper.backupExitoso";
	private static final String MENSAJE_ERROR_BACKUP = "backuper.backupFallido";
	
	@Resource(name="backupService")
	private BackupService backupService;
	
	private ModelAndView getPaginaListaBackups( BasicDTO resultOperacion )
	{
		ModelAndView mav = new ModelAndView(JSP_BACKUPS);
		
		Collection<BackupRealizadoDTO> backups = backupService.getBackupsRealizados();
		
		if( resultOperacion==null )
		{
			resultOperacion = new BasicDTO();
			resultOperacion.setError(false);
			resultOperacion.setErrorMessage("");
		}
		
		mav.addObject(LISTABACKUPSDTO,backups);
		mav.addObject(RESULTOPERACIONDTO,resultOperacion);
		
		return mav;
	}
	
	@RequestMapping("/backup.htm")
	public ModelAndView getPaginaBackup(HttpServletRequest req,HttpSession session)
	{
		return getPaginaListaBackups(null);
	}
	
	
	@RequestMapping(value = "/doBackup.htm")
	public ModelAndView doBackup(HttpServletRequest req,HttpSession session) 
	{
		Boolean exito = backupService.doBackup();
		
		BasicDTO result = new BasicDTO();
		result.setError( !exito );
		
		if( exito )
			result.setErrorMessage( messageSupport.getProperty(MENSAJE_EXITO_BACKUP) );
		else
			result.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_BACKUP)  );
		
		return getPaginaListaBackups(result);
	}	

}
