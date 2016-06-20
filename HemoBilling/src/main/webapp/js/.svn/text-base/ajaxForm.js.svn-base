/**
 * Funcion ajaxForm
 * 
 * Permite ejecutar un formulario en modo Ajax por jQuery, pudiendo definirle funciones CallBacks para
 * la respuesta.
 * 
 * Requiere:
 * 
 * libreria jQuery Core y jquery.form
 * 
 */


function ajaxForm( formId , sucessCallBackFunction , customValidationFunction , errorCallBackFunction ) 
{
	//Se define las opciones de la invocacion Ajax
	var opciones= {
			beforeSubmit: handleValidations, //funcion que se ejecuta antes de enviar el form
			success: handleAjaxSucess, //funcion que se ejecuta cuando vuelve a respuesta del server 
			error: handleAjaxError, //funcion que se ejecuta cuando vuelve a respuesta de error del server 
    };
	
	//Se asigna el plugin ajaxForm al formulario indicado por parametro, con las opciones
	$('#'+formId ).ajaxForm(opciones) ; 
    
     //Function que maneja el caso de validacion. Ejecuta la funcion del cliente si la definio 
     function handleValidations()
     {
    	 if( customValidationFunction!=null )
    	{
    		 customValidationFunction();
    		 }
     };
     
	// Todos los resultados de tipo json comienzan con una llave.
 	function getJSON(result) 
 	{
 		if (result.charAt(0) == "{") 
 		{
 			if($.browser.msie && $.browser.version >= "7.0") 
 			{
 				var pattern = /\r/g;
 				result = result.replace(pattern, '\\r'); 
 			}			
 			var json_convertido = eval("(" + result + ")");
 			return json_convertido.response; //Todos los Json responses tienen un response adentro
 		} 
 		else 
 		{
 			return null;
 		}
 	};
 	
	//Function que maneja el caso de respuesta exitosa. Ejecuta la funcion del cliente si la definio 
	function handleAjaxSucess(responseText)
	{
		if( sucessCallBackFunction!=null )
		{
			var json = getJSON(responseText);
			
			if( json!=null )
				sucessCallBackFunction(json);
			else
				sucessCallBackFunction(responseText);
		}
	};

	//Function que maneja el caso de error. Ejecuta la funcion del cliente si la definio 
	function handleAjaxError(responseText) 
	{
		if( errorCallBackFunction!=null )
		{
			errorCallBackFunction(responseText);
		}
	};
}
