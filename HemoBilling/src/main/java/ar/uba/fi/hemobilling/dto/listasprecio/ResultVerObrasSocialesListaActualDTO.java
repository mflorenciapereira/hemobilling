package ar.uba.fi.hemobilling.dto.listasprecio;

import org.springframework.util.AutoPopulatingList;

import ar.uba.fi.hemobilling.domain.obrassociales.AsociacionObraSociaListaPrecio;
import ar.uba.fi.hemobilling.dto.BasicDTO;

public class ResultVerObrasSocialesListaActualDTO extends BasicDTO
{
	private AutoPopulatingList obrasSociales = new AutoPopulatingList(AsociacionObraSociaListaPrecio.class);

	public AutoPopulatingList getObrasSociales() {
		return obrasSociales;
	}

	public void setObrasSociales(AutoPopulatingList obrasSociales) {
		this.obrasSociales = obrasSociales;
	}


}
