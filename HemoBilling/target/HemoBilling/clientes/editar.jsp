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



<link rel="stylesheet" type="text/css" href="estilos/jquery-ui-1.8.22.custom.css" />


	
<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.button.js"></script>
<script src="js/jquery/ui/jquery.ui.position.js"></script>
<script src="js/jquery/ui/jquery.ui.autocomplete.js"></script>
<script type="text/javascript">	




	
	/* Funciones de Validacion ---------------------------------------------- */
	
function hideAllMessageError() {
		hideMessage("codigoErrorID");
		hideMessage("nombreErrorID");
		hideMessage("codigoContableErrorID");
		hideMessage("cuitErrorID");
		hideMessage("telefonoErrorID");
		hideMessage("telefonoGratuitoErrorID");
		hideMessage("emailErrorID");
		hideMessage("websiteErrorID");
		

		hideMessage("errorMessageDivID");
		hideMessage("exitoMessageDivID");
	}	
	function volverClientes()
	{
		 location.href = "volverClientes.htm"; 
	}
	
	function validaryComitearForm()
	{
		var error = false;
		hideAllMessageError();

	


		if ($('#nombreID').val() == "") {
			error = true;
			showMessage("El nombre es obligatorio.", "nombreErrorID");
		}
		
	
		if( !error )
		{
			$('#editarForm').submit();
		}
	}

	
	
	
	var errorEditando = function errorEditando(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var elementoModificado = function elementoModificado(json)
	{
		var result = json.data[0];
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			
			
			
			$('#errorResultID').val( result.error );
			$('#errorMessageResultID').val( result.errorMessage );
			$('#volverClientesForm').submit();
			
		}
		

		 
	};

	

	/* Funcion inicial ---------------------------------------------- */
	
	$(document).ready(function()
			
			
	{
		
		
			//autocompletar paises
		
		    $.getJSON("json/paises.json", function(json){
		    	var paisesSource=json;
		    	   $("#paisID").autocomplete( {
				    	minLength: 0,
				    	source: paisesSource
				    
				    });
             
          });
			
			
			//autocompletar provincias argentinas
			
		    $.getJSON("json/provinciasArgentinas.json", function(json){
		    	var provinciasSource=json;
		    	   $("#provinciaID").autocomplete( {
				    	minLength: 0,
				    	source: provinciasSource
				    
				    });
             
          });

			
		
		ajaxForm( 'editarForm' , elementoModificado , null , errorEditando );
		
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

	<h2>Modificar un cliente</h2>

	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>

	<form:form cssClass="niceform" method="post"
		name="editarForm" id="editarForm"
		action="doEditarCliente.htm" commandName="actualizarclienteDTO"
		modelAttribute="actualizarclienteDTO">

		<fieldset>
			<dl>
				<dt>
					<label for="codigo">Codigo:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="codigo" size="20" id="codigoID" readonly="true" /></td>
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
							<td><form:textarea path="nombre" cols="50" rows="6" id="nombreID" /></td>
						</tr>
						<tr>
							<td><div id="nombreErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
			
			
			<dl>
				<dt>
					<label for="codigoRNOS">C&oacute;odigo Contable:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="codigoContable" size="50" id="codigoContableID" /></td>
						</tr>
						<tr>
							<td><div id="codigoContableErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
			
						<dl>
				<dt>
					<label for="cuit">CUIT:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="cuit"  size="20" id="cuitID" /></td>
						</tr>
						<tr>
							<td><div id="cuitErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
									<dl>
				<dt>
					<label for="website">Tipo de IVA:</label>
				</dt>
									
				<dd>
					
						
							<form:select id="tipoIVAid" path="tipoIVA.id">
							<form:option value="-1" label="Seleccionar uno" />
							<form:options  items="${tiposivaDTO}" itemLabel="descripcion" itemValue="id" />
							</form:select>
							
						
				</dd>
			</dl>
			
			
			</fieldset>
			
			<div><h4>Direcci&oacute;n</h4></div>
			
			<fieldset>
			
			<dl>
				
				<dt>
					<label for="direccion">Direccion:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:textarea path="direccion"  cols="50" rows="2" id="calleID" /></td>
						</tr>
						<tr>
							<td><div id="calleErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="localidad">Localidad:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="localidad"  size="50" id="localidadID" /></td>
						</tr>
						<tr>
							<td><div id="localidadErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
			<dl>
				<dt>
					<label for="provincia">Provincia:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="provincia" size="50" id="provinciaID" /></td>
						</tr>
						<tr>
							<td><div id="provinciaErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
									<dl>
				<dt>
					<label for="codigoPostal">C&oacute;digo postal:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="codigoPostal"  size="20" id="codigoPostalID" /></td>
						</tr>
						<tr>
							<td><div id="codigoPostalErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
						<dl>
				<dt>
					<label for="pais">Pa&iacute;s:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="pais"  size="50" id="paisID" /></td>
						</tr>
						<tr>
							<td><div id="paisErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			</fieldset>
			
			<div><h4>Contacto</h4></div>
			
			<fieldset>
			
			
			
			<dl>
				<dt>
					<label for="telefono">Tel&eacute;fono:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="telefono"  size="50" id="telefonoID" /></td>
						</tr>
						<tr>
							<td><div id="telefonoErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="telefonoGratuito">Tel&eacute;fono gratuito:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="telefonoGratuito"  size="50" id="telefonoGratuitoID" /></td>
						</tr>
						<tr>
							<td><div id="telefonoGratuitoErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
			
			
			
						<dl>
				<dt>
					<label for="email">Email:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="email"  size="50" id="emailID" /></td>
						</tr>
						<tr>
							<td><div id="emailErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			
									<dl>
				<dt>
					<label for="website">Website:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="website"  size="50" id="websiteID" /></td>
						</tr>
						<tr>
							<td><div id="websiteErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			

			
			
			<dl class="submit">
				<dt>
					<input type="button" name="submit" id="submit" value="Modificar"
						onclick="validaryComitearForm()" />
				</dt>
				<dt>
					<input type="button" name="volver" id="volver" value="Volver"
						onclick="volverClientes();" />
				</dt>
			</dl>

		</fieldset>
	
		


	</form:form>




	<form:form action="volverClientes.htm" method="post"
		id="volverClientesForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID" />
		<form:hidden path="errorMessage" id="errorMessageResultID" />
	</form:form>


</div>

