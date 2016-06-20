package ar.uba.fi.hemobilling.exception.domain;

public class HBEntityRelationViolation extends HemoBillingDomainException {
	
	private static final long serialVersionUID = 1L;
	private static final String CODIGO = "HBEntityRelationViolation.code";

	public HBEntityRelationViolation( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBEntityRelationViolation()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}

}
