<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <link rel="stylesheet" href="estilos/cupertino/datepicker.css">
<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.datepicker-es.js"></script>
<script src="js/jquery/datepicker.js"></script>

<script src="js/jquery/form/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery/modal/jqModal.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="estilos/jquery-ui-1.8.22.custom.css" />


<script src="js/ajaxForm.js" type="text/javascript"></script>
<script src="js/hemoUtils.js" type="text/javascript"></script>

<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.button.js"></script>
<script src="js/jquery/ui/jquery.ui.position.js"></script>
<script src="js/jquery/ui/jquery.ui.autocomplete.js"></script>


 
 <script>
 
 <!-- AUTOCOMPLETAR -->
	var osPosiblesSource=${listaPosiblesOSDTO};
	var index;
	
	function autocompletar(divid){
	
	$( "#"+divid+"\\.nombre" ).autocomplete({
		minLength: 0,
		source: osPosiblesSource,
		select: function( event, ui ) {
			$( "#"+divid+"\\.nombre" ).val( ui.item.desc );
			$( "#"+divid+"\\.codigo" ).val( ui.item.value );
			
	
			return false;
		}
	})
	.data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
			.data( "item.autocomplete", item )
			.append( "<a>" + item.label +"</a>" )
			.appendTo( ul );
	};
	
	};
 
 var errorFunction = function errorFunction(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var successFunction = function successFunction(json)
	{

		var result = json.data[0];
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			//Me voy y muestro el mensaje de confirmacion
			$('#errorResultID').val( result.error );
			$('#errorMessageResultID').val( result.errorMessage );
			$('#volverPrestacionesForm').submit();
		}
		

		 
	};

 

 
 function hideAllMessageError() {			
		hideMessage("fechaDesdeError");
		hideMessage("fechaHastaError");
		
		hideMessage("errorMessageDivID");
		hideMessage("exitoMessageDivID");
	}
	
	function validaryComitearForm()
	{
		
		hideAllMessageError();
		
		if( $('#fechaDesdeID').val() == "" )
		{
			error = true;
			showMessage( "La fecha es obligatoria" , "fechaDesdeError" );
		};
		
		if( $('#fechaHastaID').val() == "" )
		{
			error = true;
			showMessage( "La fecha es obligatoria" , "fechaHastaError" );
		};
		
	
			
		
		if( !error )
		{
			$('#facturasEmitidasForm').submit();
		}
			
		
		
		
	}
	
	
 
 
 
 
 	//FUNCIONES PRINCIPALES ---------------------------------------------------------------------------------------------
 	
 	
	
	$(document).ready(function(){
		
		autocompletar('obraSocialActual');
		
		
		$("#fechaDesdeID").datepicker($.datepicker.regional['es']);
		$("#fechaHastaID").datepicker($.datepicker.regional['es']);
		
		$('#facturasEmitidasForm').submit(function(e) {
			
			var error = false;
			if( $('#fechaDesdeID').val() == "" )
			{
				error = true;
				showMessage( "La fecha es obligatoria" , "fechaDesdeError" );
			};
			
			if( $('#fechaHastaID').val() == "" )
			{
				error = true;
				showMessage( "La fecha es obligatoria" , "fechaHastaError" );
			};
			
			if( $('#obraSocialActual\\.nombre').val() == "" )
			{
				error = true;
				showMessage( "La obra social es obligatoria." , "obraSocialActualErrorID" );
			};
			
			if( error )
			{
				e.preventDefault();
			}
		});
		
		var error = "${resultOperacionDTO.error}"; 
		var message = "${resultOperacionDTO.errorMessage}";
		
		if( error=="true" )
		{
			if( message!="" )
			{
				showMessage( message , "errorMessageDivID" );
				setTimeout(function(){hideMessage("errorMessageDivID");},10000);
			}
		}
		else
		{
			if( message!="" )
			{
				showMessage( message , "exitoMessageDivID" );
				setTimeout(function(){hideMessage("exitoMessageDivID");},3000);
			}
		}
		
		
		
		
	});
	

</script>
 

<div class="center_content">
	<h2>Obtener Carta de facturas</h2>
      
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>

	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>
	
         <div class="form" >
         
         <form:form id="facturasEmitidasForm" action="doGenerarInformeCartaFacturas.htm" method="post" cssClass="niceform" modelAttribute="filtroFacturasEmitidasDTO" commandName="filtroCartaFacturaDTO">
         
			<fieldset>
         

                    <dl>
                        <dt><label for="fechaDesde">Fecha desde:</label></dt>
                        <dd>
                       		<table>
								<tr> <td><form:input path="fechaDesde" id="fechaDesdeID" size="10" /></td></tr>
								<tr> <td><div id="fechaDesdeError" class="divError"></div></td></tr>
							</table>
							
                        	
                        </dd>
                    </dl>
                    
                    <dl>
                    	<dt><label for="fechaHasta">Fecha hasta:</label></dt>						
                        
                        <dd>
                      		<table>
								<tr> <td><form:input path="fechaHasta" id="fechaHastaID" size="10" /></td></tr>
								<tr> <td><div id="fechaHastaError" class="divError"></div></td></tr>
							</table>
                        </dd>
                    </dl>
                    
                    <dl>
				<dt>
					<label for="osActual">Obra Social: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="obraSocialActual.codigo" size="10" id="obraSocialActual.codigo" readonly="true"/></td></tr>
					<tr><td><textarea cols="50" rows="6" id="obraSocialActual.nombre"></textarea></td>
					</tr>
					<tr> <td><div id="obraSocialActualErrorID" class="divError"></div></td></tr>
					</table>
					
					
				</dd>
			</dl>
                     
                   
		<dl class="submit">
				<dt> 
					<input type="submit" name="submit" id="submit" value="Generar informe"  />
				</dt>
			</dl>
                     
                      
                     
        
                     
                    
		</fieldset>
                
         </form:form>
         
         </div>
         </div>
         
         
      
