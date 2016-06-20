package ar.uba.fi.hemobilling.dto;

public class BasicDTO {
	Boolean error = false;
	String errorMessage = "";
	
	public BasicDTO(){
		error = false;
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String getErrorMessage) {
		this.errorMessage = getErrorMessage;
	}
	
	

}
