package ar.uba.fi.hemobilling.controller;

import java.util.Collection;

import javax.annotation.Resource;

import ar.uba.fi.hemobilling.mvc.Response;
import ar.uba.fi.hemobilling.mvc.SuccessResponse;
import ar.uba.fi.hemobilling.util.PropertiesReader;

public class AbstractController 
{
	protected static final String MENSAJE_ERROR_DB = "general.errorConexionBD";
	protected static final String MENSAJE_ERROR_SERVICIO = "general.errorServicio";
	protected static final String MENSAJE_ERROR_SISTEMA_REMOTO = "general.errorConexionSistemasRemotos";
	
	protected static final Integer cantidadRegistrosPorPagina = 20;
	
	@Resource(name = "messageSupport")
	protected PropertiesReader messageSupport;
	
	protected Response createDataResponse(Object data) {
		SuccessResponse response = new SuccessResponse();
		response.addData(data);
		return response;
	}
	
	protected Response createCollectionDataResponse(Collection<? extends Object> collectionData) {
		SuccessResponse response = new SuccessResponse();
		response.addAllData(collectionData);
		return response;
	}
	

}
