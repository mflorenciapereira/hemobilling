package ar.uba.fi.hemobilling.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContabilidadController extends AbstractController{

	private static final String JSP_EXPORTAR_ARCHIVO = "exportarArchivo";

	@RequestMapping("/exportarArchivo.htm")
	public ModelAndView exportarArchivo()
	{
		ModelAndView mav = new ModelAndView( JSP_EXPORTAR_ARCHIVO );
		
		return mav;
	}
	




}