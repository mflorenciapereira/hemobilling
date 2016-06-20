package ar.uba.fi.hemobilling.util;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;

public class PropertiesReader {

	private static final String PUNTO = ".";

	private MessageSource propertySource;

	private MessageSourceAccessor accesor;
	
	public PropertiesReader() {
	}
	
	/**
	 * Decripcion: Obtiene el mensaje correspondiente definido en el
	 * messageSource de la configuracion de Spring
	 */
	public String getProperty(String code) {
		if (accesor == null)
			accesor = new MessageSourceAccessor(propertySource);
		return accesor.getMessage(code);
	}
	
	public String getProperty(String code,Object[] args) {
		if (accesor == null)
			accesor = new MessageSourceAccessor(propertySource);
		return accesor.getMessage(code,args);
	}
	
	public String getProperty(String module, String code) {
		String msg = "";
		if (accesor == null)
			accesor = new MessageSourceAccessor(propertySource);
		try{
			msg = accesor.getMessage(module + PUNTO +code);
		}catch(NoSuchMessageException e){
			//Si no encuentra el mensaje del code busca el generico del module
			msg = accesor.getMessage(module);
		}
		
		return msg;
	}

	public MessageSource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(MessageSource messageSource) {
		this.propertySource = messageSource;
	}

	public MessageSourceAccessor getAccesor() {
		return accesor;
	}

	public void setAccesor(MessageSourceAccessor accesor) {
		this.accesor = accesor;
	}

	public String getRealCode(String module, String code) {
		String codeResult = null;
		if (accesor == null)
			accesor = new MessageSourceAccessor(propertySource);
		try{
			accesor.getMessage(module + PUNTO +code);
			codeResult = module + PUNTO +code;
		}catch(NoSuchMessageException e){
			//Si no encuentra el mensaje del code busca el generico del module
			accesor.getMessage(module);
			codeResult = module;
		}
		
		return codeResult;
	}
	
}