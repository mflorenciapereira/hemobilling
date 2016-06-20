<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery/form/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery/modal/jqModal.js" type="text/javascript"></script>

<script src="js/ajaxForm.js" type="text/javascript"></script>
<script src="js/hemoUtils.js" type="text/javascript"></script>

<script type="text/javascript">	
	
	/* Funciones de manejo del modal de Roles ---------------------------------------------- */
	
	function cargarRoles()
	{
		var rolesDesc = "";
		var rolesCods = "";
		var cantRoles = parseInt( $('#cantRoles').val() );
		
		for( r=1 ; r<=cantRoles ; r++ )
		{
			var nombreDiv = '#rol'+r;
			if( $(nombreDiv).is(':checked') )
			{
				if( rolesDesc != "" ) rolesDesc += ", ";
				rolesDesc += $(nombreDiv).attr('name');
				
				if( rolesCods != "" ) rolesCods += "-";
				rolesCods += $(nombreDiv).attr('value');
			}
		}
		
		$('#codigosRolesSerializadosID').val(rolesCods);
		$('#codigosRolesSerializadosDescID').val(rolesDesc);
		
		$('#rolesPosibles').jqmHide();
	}
	
	function mostrarRolesPosibles()
	{
		$('#rolesPosibles').jqmShow();
	}
	
	
	/* Funciones de Validacion ---------------------------------------------- */

	
	function hideAllMessageError() {			
		hideMessage("nombreUsuarioErrorID");
		hideMessage("passwordErrorID");
		hideMessage("passwordConfErrorID");
		hideMessage("nombreCompletoErrorID");
		hideMessage("rolErrorID");	
		
		hideMessage("errorMessageDivID");	
		hideMessage("exitoMessageDivID");
	}
	
	function volverUsuarios()
	{
		 location.href = "usuarios.htm"; 
	}
	
	function validaryComitearForm()
	{
		var error = false;
		hideAllMessageError();
		
		if( $('#nombreUsuarioID').val()=="" )
		{
			error = true;
			showMessage( "El nombre de usuario es obligatorio" , "nombreUsuarioErrorID" );
		}
		
		if( $('#passwordID').val()=="" )
		{
			error = true;
			showMessage( "El password es obligatorio" , "passwordErrorID" );
		}
		
		if( $('#passwordConfID').val()=="" )
		{
			error = true;
			showMessage( "El password es obligatorio" , "passwordConfErrorID" );
		}
		
		if( $('#passwordID').val() != $('#passwordConfID').val() )
		{
			error = true;
			showMessage( "Los passwords deben coincidir" , "passwordErrorID" );
			showMessage( "Los passwords deben coincidir" , "passwordConfErrorID" );
		}
		
		if( $('#nombreCompletoID').val() == "" )
		{
			error = true;
			showMessage( "El nombre completo es obligatorio" , "nombreCompletoErrorID" );
		}
		
		if( $('#codigosRolesSerializadosID').val() == "" )
		{
			error = true;
			showMessage( "Se debe asignar al menos un rol al usuario" , "rolErrorID" );
		}
		
		if( !error )
		{
			$('#nuevoUsuarioForm').submit();
		}
	}

	
	/*CALLBACKS del form de nuevo usuario-----------------------------------------------------*/
	
	var errorAgregandoUsuario = function errorAgregandoUsuario(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var usuarioAgregado = function usuarioAgregado(json)
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
			$('#volverUsuariosForm').submit();
		}
		

		 
	};

	
	/* Funcion inicial ---------------------------------------------- */
	
	$(document).ready(function()
	{
		$('#rolesPosibles').jqm({modal: true});
		ajaxForm( 'nuevoUsuarioForm' , usuarioAgregado , null , errorAgregandoUsuario );
		
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



<script type="text/javascript">

 </script>

<div class="center_content">

	<h2>Datos del Nuevo Usuario</h2>
	
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


	<form:form cssClass="niceform" method="post" name="nuevoUsuarioForm" id="nuevoUsuarioForm" action="doNuevoUsuario.htm" modelAttribute="nuevoUsuarioDTO">
	
		<fieldset>
			<dl>
				<dt>
					<label for="nombreUsuario">Nombre de usuario *:</label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="nombreUsuario" size="20" id="nombreUsuarioID" /></td></tr>
					<tr> <td><div id="nombreUsuarioErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt>
					<label for="password">Contraseña *:</label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:password path="password" size="40" id="passwordID"/></td></tr>
					<tr> <td><div id="passwordErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
						
			<dl>
				<dt>
					<label for="passwordConf">Confirmar contraseña *:</label>
				</dt>
				<dd>
					<table>
					<tr> <td><input type="password" size="40" id="passwordConfID" /></td></tr>
					<tr> <td><div id="passwordConfErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt>
					<label for="nombreCompleto">Nombre completo *:</label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="nombreCompleto" size="50" id="nombreCompletoID" /></td> </tr>
					<tr> <td><div id="nombreCompletoErrorID" class="divError"></div></td></tr>
					</table> 
				</dd>
			</dl>

			<dl>
				<dt>
					<label for="habilitado">Habilitado:</label>
				</dt>
				<dd>
					<form:checkbox path="habilitado"/>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="codigosRolesSerializados">Roles *:</label>
				</dt>
				<dd>
					<table>
						<tr> <td> <form:hidden path="codigosRolesSerializados" id="codigosRolesSerializadosID"/></td></tr>
						<tr> <td> <form:input path="descripRolesSerializados" id="codigosRolesSerializadosDescID" readonly="true" size="50"/></td> </tr>
						<tr> <td> <div id="rolErrorID" class="divError"></div></td></tr>
						<tr> <td> <input type="button" name="cargarRoles" id="cargarRoles" value="Cargar Roles" onclick="mostrarRolesPosibles()" /></td></tr>
					</table>
				</dd>
			</dl>

			<dl><dt></dt></dl>
			<dl class="submit">
				<dt><input type="button" name="submit" id="submit" value="Cargar" onclick="validaryComitearForm()"/></dt>
				<dt><input type="button" name="volver" id="volver" value="Volver" onclick="volverUsuarios()"/></dt>
			</dl>

		</fieldset>



	</form:form>
	
	
	<div id="rolesPosibles" style="display:none;" class="jqmWindow">
		<form:form cssClass="niceform">
		<table style="width: auto;">
			<c:forEach items="${posiblesRolesUsuariosDTO}" var="rol" varStatus="index">
				<tr> <td> <input type="checkbox" id="rol${index.count}" value="${rol.codigo}" name="${rol.descripcion}" >${rol.descripcion}</td> </tr>
			</c:forEach>
		<tr>
		<td><input type="hidden" id="cantRoles" value="${fn:length(posiblesRolesUsuariosDTO)}"/></td>
		<td><input type="button" onclick="cargarRoles()" value="Aceptar" name="submit" id="submit" /></td>
		</tr>
		</table>

		</form:form>
	</div>
	
	<form:form action="volverUsuarios.htm" method="post" id="volverUsuariosForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID"/>
		<form:hidden path="errorMessage" id="errorMessageResultID"/>	
	</form:form>
	
	
</div>

