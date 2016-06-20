<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery/form/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery/modal/jqModal.js" type="text/javascript"></script>

<script src="js/ajaxForm.js" type="text/javascript"></script>
<script src="js/hemoUtils.js" type="text/javascript"></script>
<script src="js/inputValidators.js" type="text/javascript"></script>

<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.button.js"></script>
<script src="js/jquery/ui/jquery.ui.position.js"></script>
<script src="js/jquery/ui/jquery.ui.autocomplete.js"></script>


<link rel="stylesheet" type="text/css" href="estilos/jquery-ui-1.8.22.custom.css" />

<script type="text/javascript">	
	
	
	/* Funciones de Validacion ---------------------------------------------- */
	
	var iraPrecios;
			
	
	function hideAllMessageError() {			
		hideMessage("codigoErrorID");
		hideMessage("nombreErrorID");
		
		
		hideMessage("errorMessageDivID");
		hideMessage("exitoMessageDivID");
	}
	
	function volverListaPrecios()
	{
		 location.href = "volverListasPrecio.htm"; 
	}
	
	function validaryComitearForm( cargarPrecios )
	{
		var error = false;
		hideAllMessageError();
	
		if ($('#codigoID').val() == "") {
			error = true;
			showMessage("El codigo es obligatorio", "codigoErrorID");
		}
		
		if ( ($('#codigoID').val() != "") && (!isNumber( $('#codigoID').val()) )) {
			error = true;
			showMessage("El codigo debe ser un n&uacutemero.", "codigoErrorID");
		}

		if ($('#nombreID').val() == "") {
			error = true;
			showMessage("El nombre de la lista de precios es obligatorio", "nombreErrorID");
		}
		
		if( !error )
		{
			iraPrecios = cargarPrecios;
			$('#edicionListaPrecioForm').submit();
		}
	}
	
	
	
	
	/* ----- CALLBACKS -----*/
	
	var errorEditando = function errorEditando(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var ListaPrecioModificada = function ListaPrecioModificada(json)
	{
		var result = json.data[0];
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			if( !iraPrecios )
			{
				$('#errorResultID').val(result.error);
				$('#errorMessageResultID').val(result.errorMessage);
				$('#volverListasPrecioForm').submit();
			}
			else
			{
				$('#codigoPrecioID').val(result.errorMessage);
				$('#verPreciosForm').submit();
			}
		}
		

		 
	};

	
	
	
	$(document).ready(function()		
	{
		
		ajaxForm( 'edicionListaPrecioForm' , ListaPrecioModificada , null , errorEditando );
		
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

	<h2>Modificar una lista de precio</h2>

	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>

	<form:form cssClass="niceform" method="post"
		name="edicionListaPrecioForm" id="edicionListaPrecioForm"
		action="doEditarListaPrecio.htm" commandName="actualizarListaPrecioDTO"
		modelAttribute="actualizarListaPrecioDTO">

		
		<fieldset>
			<dl>
				<dt>
					<label for="codigo">Codigo:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input readonly="true" path="codigo" size="20" id="codigoID" /></td>
						</tr>
						<tr>
							<td><div id="codigoErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>


			<dl>
				<dt>
					<label for="nombre">Nombre:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="nombre" size="50"
									id="nombreID" /></td>
						</tr>
						<tr>
							<td><div id="nombreErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
				


			<dl>
				<dt></dt>
			</dl>

		</fieldset>
		
				<table style="width: 100%">
				<tr>
				<td width="10%"></td>
				<td width="25%" align="left"> <input type="button" name="submit" id="submit" value="Modificar datos de Lista" onclick="validaryComitearForm( false )" /> </td>
				<td width="25%" align="left"> <input type="button" name="volver" id="volver" value="Volver" onclick="volverListaPrecios()" /> </td>
				</tr>
				
				</table>
		
	</form:form>
	
	<form:form action="volverListasPrecio.htm" method="post" id="volverListasPrecioForm" modelAttribute="resultOperacionDTO">
	<form:hidden path="error" id="errorResultID" />
	<form:hidden path="errorMessage" id="errorMessageResultID" />
	</form:form>

</div>

