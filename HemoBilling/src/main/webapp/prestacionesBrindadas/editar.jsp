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


<link rel="stylesheet" type="text/css"
	href="estilos/jquery-ui-1.8.22.custom.css" />
	


<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.button.js"></script>
<script src="js/jquery/ui/jquery.ui.position.js"></script>
<script src="js/jquery/ui/jquery.ui.autocomplete.js"></script>

<link rel="stylesheet" href="estilos/cupertino/datepicker.css">
<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.datepicker-es.js"></script>
<script src="js/jquery/datepicker.js"></script>



<script type="text/javascript">

<!-- VOLVER -->
function volverPrestacionesBrindadas() {
	location.href = "prestacionesBrindadas.htm";
}



<!-- VALIDACIONES -->

function isDate(fecha){
	
	return true;
}

function hideAllMessageError() {
	
	
	   
	
	hideMessage("fechaErrorID");
	hideMessage("autorizacionErrorID");
	hideMessage("profesionalErrorID");
	hideMessage("observacionesErrorID");
	hideMessage("idPacienteErrorID");
	hideMessage("nroHCPacienteErrorID");
	hideMessage("nombrePacienteErrorID");
	hideMessage("codPrestacionErrorID");
	hideMessage("nombrePrestacionErrorID");
	hideMessage("cantidadDePrestacionesErrorID");
	
	hideMessage("errorMessageDivID");
	hideMessage("exitoMessageDivID");
}

function validaryComitearForm() {
	var error = false;
	hideAllMessageError();
	
	if (($('#fechaID').val() == "")) {
		error = true;
		showMessage("La fecha es obligatoria.", "fechaErrorID");
	}

	
	

	
	
	if ($('#cantidadDePrestacionesID').val() == "") {
		error = true;
		showMessage("Debe ingresar una cantidad de prestaciones.", "cantidadDePrestacionesErrorID");
	}
	
	
	
	if (($('#cantidadDePrestacionesID').val() != "")&&(!isNumber($('#cantidadDePrestacionesID').val()))) {
		error = true;
		showMessage("El campo cantidad de prestaciones debe ser un n&uacute;mero entero.", "cantidadDePrestacionesErrorID");
	}
	
	
	


	if (!error) {
		$('#nuevoForm').submit();
	}
}


var errorAgregandoPrestacion = function errorAgregandoPrestacion(json) {
	showMessage('<spring:message code="general.errorContactoController"/>',
			"errorMessageDivID");
};

var agregado = function agregado(json) {
	var result = json.data[0];

	if (result.error == true) {

		showMessage(result.errorMessage, "errorMessageDivID");
	} else {

		$('#errorResultID').val(result.error);
		$('#errorMessageResultID').val(result.errorMessage);
		$('#volverPrestacionesBrindadasForm').submit();
	}

};

	$(document).ready(
			function() {
				
				
				<!-- AUTOCOMPLETAR -->
				
				
				
				<!-- observaciones -->
				
				observacionesList =${observacionesPosiblesString};
				
				var observacionesSource = [];
				$.each(observacionesList, function(key,value) { 
					observacionesSource.push( value.descripcion );
				});
				
				
				$( "#observacionesID" ).autocomplete({source:observacionesSource});


				
				
				
				
				
				<!-- DATEPICKER -->
				
				$("#fechaID").datepicker($.datepicker.regional['es']);
				$("#fechaEgresoID").datepicker($.datepicker.regional['es']);
				
				
				<!-- AJAX -->
				ajaxForm('nuevoForm', agregado, null,
						errorAgregandoPrestacion);

				var error = "${resultOperacionDTO.error}";
				var message = "${resultOperacionDTO.errorMessage}";

				if (error == "true") {
					if (message != "") {
						showMessage(message, "errorMessageDivID");
						setTimeout(function() {
							hideMessage("errorMessageDivID");
						}, 10000);
					}
				} else {
					if (message != "") {
						showMessage(message, "exitoMessageDivID");
						setTimeout(function() {
							hideMessage("exitoMessageDivID");
						}, 3000);
					}
				}
			});
	
	
	
	


	
	
</script>

<div class="center_content">

	<h2>Datos de la nueva prestaci&oacute;n brindada</h2>

	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


	<form:form cssClass="niceform" method="post" name="nuevoForm"
		id="nuevoForm" action="doEditarPrestacionBrindada.htm"
		modelAttribute="actualizarprestacionbrindadaDTO">

		<fieldset>
		<form:hidden path="codigo" />
			<dl>
				<dt>
					<label for="fecha">Fecha:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="fecha" size="20" id="fechaID" /></td>
						</tr>
						<tr>
							<td><div id="fechaErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="fechaEgreso">Fecha de Egreso:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="fechaEgreso" size="20" id="fechaEgresoID" /></td>
						</tr>
						<tr>
							<td><div id="fechaEgresoErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="autorizacion">Autorizaci&oacute;n:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="autorizacion" size="30" id="autorizacionID" /></td>
						</tr>
						<tr>
							<td><div id="autorizacionErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
				<dl>
				<dt>
					<label for="profesional">Profesional:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:textarea path="profesional" cols="50" rows="6" id="profesionalID" /></td>
						</tr>
						<tr>
							<td><div id="profesionalErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="observaciones">Observaciones:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:textarea path="observaciones" cols="50" rows="6" id="observacionesID" /></td>
						</tr>
						<tr>
							<td><div id="observacionesErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
			
			</fieldset>
			
			<div><h4>Datos del paciente</h4></div>
			
			<fieldset>			


			<form:hidden path="idPaciente" id="idPacienteID" />
			
			<dl>
				<dt>
					<label for="nroHCPaciente">Historia cl&iacute;nica:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input readonly="true" path="nroHCPaciente" size="10"
									id="nroHCPacienteID" /></td>
						</tr>
						<tr>
							<td><div id="nroHCPacienteErrorID" class="divError"></div></td>
						</tr> 
					</table>
				</dd>
			</dl>
			
			
			<dl>
				<dt> 
					<label for="nombrePaciente">Nombre:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input readonly="true" path="nombrePaciente" size="70" id="nombrePacienteID" /></td>
						</tr>
						<tr>
							<td><div id="nombrePacienteErrorID" class="divError"></div></td>
						</tr> 
					</table>
				</dd>
			</dl>
			
			</fieldset>
			
			<div><h4>Datos de la prestaci&oacute;n</h4></div>
			
			<fieldset>	
			
				<dl>
				<dt>
					<label for="codPrestacion">C&oacute;digo:</label>
				</dt>
				<dd> 
					<table>
						<tr>
							<td><form:input readonly="true" path="codPrestacion" size="10"
									id="codPrestacionID" /></td>
						</tr>
						<tr>
							<td><div id="codPrestacionErrorID" class="divError"></div></td>
						</tr> 
					</table>
				</dd>
			</dl>
			
				<dl>
				<dt>
					<label for="nombrePrestacion">Nombre:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:textarea readonly="true" path="nombrePrestacion" cols="50" rows="6" 
									id="nombrePrestacionID" /></td>
						</tr>
						<tr>
							<td><div id="nombrePrestacionErrorID" class="divError"></div></td>
						</tr> 
					</table>
				</dd>
			</dl>
			
			
			<dl>
				<dt>
					<label for="cantidadDePrestaciones">Cantidad de prestaciones:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input  path="cantidadDePrestaciones" size="10"
									id="cantidadDePrestacionesID" /></td>
						</tr>
						<tr>
							<td><div id="cantidadDePrestacionesErrorID" class="divError"></div></td>
						</tr> 
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="precioManual">Precio Manual (*):</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input  path="precioManual" size="10"
									id="precioManualID" /></td>
						</tr>
						<tr>
							<td>(*) En caso de colocar un precio de forma manual a esta prestaci&oacute;n <br> 
							brindada, se ignorar&aacute; el precio que la OS del paciente le puso a <br>
							esta prestaci&oacute;n que se brind&oacute;	en la fecha que se brind&oacute;.</td>
						</tr> 
					</table>
				</dd>
			</dl>			
			
			


			
			<dl>
				<dt></dt>
			</dl>
			<dl class="submit">
				<dt>
					<input type="button" name="submit" id="submit" value="Modificar"
						onclick="validaryComitearForm()" />
				</dt>
				<dt>
					<input type="button" name="volver" id="volver" value="Volver"
						onclick="volverPrestacionesBrindadas()" />
				</dt>
			</dl>




		</fieldset>
		
		




	</form:form>



	<form:form action="volverPrestacionesBrindadas.htm" method="post"
		id="volverPrestacionesBrindadasForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID" />
		<form:hidden path="errorMessage" id="errorMessageResultID" />
	</form:form>







</div>


