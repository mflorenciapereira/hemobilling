package ar.uba.fi.hemobilling.exception.domain;

public class HBDataAccessException extends HemoBillingDomainException {

	private static final long serialVersionUID = 1L;
	private static final String CODIGO = "HBDataAccessException.code";

	public HBDataAccessException( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBDataAccessException()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}

}
