package ar.uba.fi.hemobilling.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/main.htm")
	public String redirect()
	{
		return "main";
	}
	

}