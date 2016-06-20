package ar.uba.fi.hemobilling.exception.domain;

public abstract class HemoBillingDomainException extends Exception {
	
	protected Exception innerException;

	private static final long serialVersionUID = 1L;
	
	abstract public String getCode();

	public Exception getInnerException() {
		return innerException;
	}

	public void setInnerException(Exception innerException) {
		this.innerException = innerException;
	}
	
	

}
