package ar.uba.fi.hemobilling.log4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;



public class Log4jInit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() 
	{
		String externalFile = System.getProperty("log4j.configuration");
		
		if( externalFile==null )
		{
			String prefix =  getServletContext().getRealPath("/");
			String file = getInitParameter("log4j-init-file");
		     
			if(file != null)
			{
				externalFile = prefix+file;
			}
		}
		
		if( externalFile!= null )
		{
			PropertyConfigurator.configure(externalFile);
			System.out.println("Log4J configurado con el archivo: " + externalFile);
		}
		else
		{
			System.out.println("No se ha encontrado un archivo de configuracion de Log4J");
		}
	}

	 public void doGet(HttpServletRequest req, HttpServletResponse res) {}
	}