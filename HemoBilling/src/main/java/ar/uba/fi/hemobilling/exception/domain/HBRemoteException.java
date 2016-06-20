package ar.uba.fi.hemobilling.exception.domain;

public class HBRemoteException extends HemoBillingDomainException{

	private static final String CODIGO = "HBObjectNotExistsException.code";
	private static final long serialVersionUID = 1L;

	public HBRemoteException( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBRemoteException()
	{
		super.innerException = null;
	}
	@Override
	public String getCode() {
		return CODIGO;
	}

}
