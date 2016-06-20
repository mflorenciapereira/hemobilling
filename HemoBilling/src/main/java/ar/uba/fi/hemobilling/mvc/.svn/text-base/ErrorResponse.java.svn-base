package ar.uba.fi.hemobilling.mvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class ErrorResponse extends Response {
    
	public static String CODE_REAUTHENTICATE = "modal";
	public static String CODE_REAUTHENTICATE_BLOQUED_USER = "modalBU";
	public static String CODE_REAUTHENTICATE_EXPIRED_PASSWORD = "modalEP";
	
    /** Mensaje asociado al error producido. */
    private String errorMessage;
    /** Lista de datos que se deben presentar en la vista. */
	private List<Object> data;
	/** Codigo del error utilizado en el frontend */
	private String code = "";
    
    public ErrorResponse() {
        super();
    }
    
    public ErrorResponse(String message) {
        this.errorMessage = message;
    }
    
    public ErrorResponse(String message, String code) {
    	this.errorMessage = message;
    	this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }
    
    private List<Object> getData() {
        if(this.data == null) {
            this.data = new ArrayList<Object>();
        }	    
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public void addData(Object o) {
	    this.getData().add(o);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addAllData(Collection o) {
		this.getData().addAll(o);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
