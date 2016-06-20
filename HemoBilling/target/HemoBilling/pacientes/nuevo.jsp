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

<link rel="stylesheet" href="estilos/cupertino/datepicker.css">
<script src="js/jquery/ui/jquery.ui.datepicker-es.js"></script>
<script src="js/jquery/datepicker.js"></script>

<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.button.js"></script>
<script src="js/jquery/ui/jquery.ui.position.js"></script>
<script src="js/jquery/ui/jquery.ui.autocomplete.js"></script>

<script src="js/jquery/ui/jquery.ui.selectmenu.js"></script>

<link rel="stylesheet" type="text/css" href="estilos/jquery-ui-1.8.22.custom.css" />


<script type="text/javascript">	
	
	<!-- AUTOCOMPLETAR -->
	var osPosiblesSource=${listaPosiblesOSDTO};
	
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


	/* Funciones de Validacion ---------------------------------------------- */

	
	function hideAllMessageError() {			
		hideMessage("tipoDocErrorID");
		hideMessage("nroDNIErrorID");
		hideMessage("nombreyApellidoErrorID");
		hideMessage("numHistoriaClinicaErrorID");
		hideMessage("fechaNacimientoErrorID");
		hideMessage("telefonoErrorID");
		hideMessage("celularErrorID");
		hideMessage("emailErrorID");
		hideMessage("calleErrorID");	
		hideMessage("localidadErrorID");
		hideMessage("provinciaErrorID");
		hideMessage("codigoPostalErrorID");
		hideMessage("diagnosticoErrorID");	
		hideMessage("severidadErrorID");	
		hideMessage("tipoHemofiliaErrorID");
		hideMessage("grupoSanguineoErrorID");
		hideMessage("factorErrorID");
		hideMessage("obraSocialActualErrorID");
		hideMessage("numAfiliadoOSActualErrorID");	
		
		hideMessage("errorMessageDivID");	
		hideMessage("exitoMessageDivID");
	}
	
	function validarObrasSocialesAdheridas()
	{
		var ok = true;
		var contador = 0; //Iterator del vectos de OS Adheridas
		var os = $('#obraSocialActual\\.codigo').val(); //Obra Social Actual Elegida
		
		while( ok==true && contador<index )
		{
			var osadh = $('#obrasSocialesAdheridasAuto'+contador+'\\.codigo').val();
			
			//Valido que no sea igual a la elegida
			if( osadh==os )
			{
				showMessage( "No puede existir una OS Adherida igual a la actual" , "obrasSocialesAdheridasErrorID" );
				ok = false;
			}
				
			//Si fue todo bien, valido que no este repetida
			if( ok )
			{
				for( var contador2=contador+1 ; contador2<index ; contador2++ )
				{
					var osadhaux = $('#obrasSocialesAdheridasAuto'+contador2+'\\.codigo').val();
					if( osadh == osadhaux )
					{
						ok = false;
					}
				}
				
				if( !ok )
					showMessage( "Existen OS Adheridas repetidas" , "obrasSocialesAdheridasErrorID" );
			}
			
			contador++;
		}
		
		return ok;
	}
	
	function validaryComitearForm()
	{
		var error = false;
		hideAllMessageError();
		
		if( $('#nombreyApellidoID').val()=="" )
		{
			error = true;
			showMessage( "El nombre del paciente es obligatorio" , "nombreyApellidoErrorID" );
		}
		
		if(  $('#numHistoriaClinicaID').val()=="" )
		{
			error = true;
			showMessage( "El n&uacute;mero de HC es obligatorio" , "numHistoriaClinicaErrorID" );
		}
		
		if( $('#numHistoriaClinicaID').val()!="" && !validateNumeric( $('#numHistoriaClinicaID').val() ) )
		{
			error = true;
			showMessage( "El formato del dato es incorrecto" , "numHistoriaClinicaErrorID" );
		}
		
		
		if( $('#fechaNacimientoID').val()!="" && !validarFormato( $('#fechaNacimientoID').val() ) )
		{
			error = true;
			showMessage( "El formato de fecha es incorrecto" , "fechaNacimientoErrorID" );
		}
		
		if( $('#emailID').val()!="" && !validateEmail( $('#emailID').val() ) )
		{
			error = true;
			showMessage( "El formato de Email es incorrecto" , "emailErrorID" );
		}

		if( $('#obraSocialActual\\.codigo').val()=="" || $('#obraSocialActual\\.codigo').val()=="-1")
		{
			error = true;
			showMessage( "Debe elegir una Obra Social" , "obraSocialActualErrorID" );
		}
		
		if( !validarObrasSocialesAdheridas() )
		{
			error = true;
		}
		
		
		if( !error )
		{
			$('#nuevoPacienteForm').submit();
		}
	}

	function volverPacientes()
	{
		 location.href = "pacientes.htm"; 
	}

	
	/*CALLBACKS del form de nuevo usuario-----------------------------------------------------*/
	
	var errorAgregandoPaciente = function errorAgregandoPaciente(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var pacienteAgregado = function pacienteAgregado(json)
	{
		var result = json.data[0];
		
		if( result.error=="true")
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			//Me voy y muestro el mensaje de confirmacion
			$('#errorResultID').val( result.error );
			$('#errorMessageResultID').val( result.errorMessage );
			$('#volverPacientesForm').submit();
		}
	};
	
	
	/* FUNCIONES DE IMPORTACION DESDE LABORATRIO ------------------------*/
	
	function cargarDatoRemotoLeido( dato , donde )
	{
		if( dato!="" && dato!="undefined" && dato!="null" && dato!=null )
		{
			if( $('#'+donde).val()=="" )
				$('#'+donde).val( dato );
		}
	}
	
	function cargarDesdeLabo()
	{
		$('#idSistemaLaboGetDatosID').val("");
		$('#idSistemaLaboGetDatosErrorID').val("");
		hideAllMessageError();
		
		$('#getDatosLaboratorioID').jqmShow();
	}
	
	function obtenerDatosLaboratorio( confirma )
	{
		if( confirma=="true") 
		{
			if( $('#idSistemaLaboGetDatosID').val()!="" && validateNumeric( $('#idSistemaLaboGetDatosID').val() ) )
			{
				$('#getDatosLaboratorioID').jqmHide();
				$( '#getDatosLaboratorioForm' ).submit();
			}
		}
		else
		{
			$('#getDatosLaboratorioID').jqmHide();
		}
	}
	
	var errorGetDatosPacienteLabo = function errorGetDatosPacienteLabo(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var sucessGetDatosPacienteLabo = function sucessGetDatosPacienteLabo(json)
	{
		var respuesta = json.data[0];
		var result = respuesta.result;
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			var paciente = respuesta.paciente;
			
			//Cargo los datos			
			cargarDatoRemotoLeido( paciente.id , 'idSistemaLabo' );
			cargarDatoRemotoLeido( paciente.numHistoriaClinica , 'numHistoriaClinicaID' );
			cargarDatoRemotoLeido( paciente.nombre , 'nombreyApellidoID' );
			cargarDatoRemotoLeido( paciente.domicilio , 'calleID' );
			cargarDatoRemotoLeido( paciente.localidad , 'localidadID' );
			cargarDatoRemotoLeido( paciente.codPostal , 'codigoPostalID' );
			cargarDatoRemotoLeido( paciente.provincia , 'provinciaID' );
			cargarDatoRemotoLeido( paciente.fechaNacimiento , 'fechaNacimientoID' );
			cargarDatoRemotoLeido( paciente.diagnostico , 'diagnosticoID' );
			cargarDatoRemotoLeido( paciente.severidad , 'severidadID' );
			cargarDatoRemotoLeido( paciente.numAfiliado , 'numAfiliadoOSActualID' );
			cargarDatoRemotoLeido( paciente.telefono , 'telefonoID' );
			cargarDatoRemotoLeido( paciente.tipoDoc , 'tipoDocID' );
			cargarDatoRemotoLeido( paciente.numDoc , 'nroDNIID' );
		}
	};
	
	
	/* FUNCIONES DE IMPORTACION DESDE HC ------------------------*/
	
	function cargarDesdeHC()
	{
		$('#numHistoriaClinicaGetDatosID').val("");
		$('#numHistoriaClinicaGetDatosErrorID').val("");
		
		hideMessage("estadoCivilSugeridoID");
		hideMessage("OS_Sugerida");
		hideMessage("sexoSugeridoID");
		hideMessage("usaInibidorSugeridoID");
		hideAllMessageError();
		
		$('#getDatosHCID').jqmShow();
	}
	
	function obtenerDatosHC( confirma )
	{
		if( confirma=="true") 
		{
			if( $('#numHistoriaClinicaGetDatosID').val()!="" && validateNumeric( $('#numHistoriaClinicaGetDatosID').val() ) )
			{
				$('#getDatosHCID').jqmHide();
				$( '#getDatosHCForm' ).submit();
			}			
		}
		else
		{
			$('#getDatosHCID').jqmHide();
		}
		
	}
	
	var errorGetDatosPacienteHC = function errorGetDatosPacienteHC(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var sucessGetDatosPacienteHC = function sucessGetDatosPacienteHC(json)
	{
		var respuesta = json.data[0];
		var result = respuesta.result;
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			var paciente = respuesta.paciente;
			
			//Cargo los datos			
			cargarDatoRemotoLeido( paciente.numHistoriaClinica , 'numHistoriaClinicaID' );
			cargarDatoRemotoLeido( paciente.apellidoNombre , 'nombreyApellidoID' );
			cargarDatoRemotoLeido( paciente.direccion , 'calleID' );
			cargarDatoRemotoLeido( paciente.localidad , 'localidadID' );
			cargarDatoRemotoLeido( paciente.codPostal , 'codigoPostalID' );
			cargarDatoRemotoLeido( paciente.provincia , 'provinciaID' );
			cargarDatoRemotoLeido( paciente.pais , 'paisID' );
			cargarDatoRemotoLeido( paciente.telefono , 'telefonoID' );
			cargarDatoRemotoLeido( paciente.email , 'emailID' );
			cargarDatoRemotoLeido( paciente.DNI , 'nroDNIID' );
			cargarDatoRemotoLeido( paciente.fechaNacimiento , 'fechaNacimientoID' );
			cargarDatoRemotoLeido( paciente.numAfiliado , 'numAfiliadoOSActualID' );
			
			cargarDatoRemotoLeido( paciente.tipoHemofilia , 'tipoHemofiliaID' );
			cargarDatoRemotoLeido( paciente.grupoSanguineo , 'grupoSanguineoID' );
			cargarDatoRemotoLeido( paciente.factor , 'factorID' );
			cargarDatoRemotoLeido( paciente.diagnostico , 'diagnosticoID' );
			cargarDatoRemotoLeido( paciente.severidad , 'severidadID' );
			
			if( paciente.usaInhibidor=="true" )
				showMessage( "Sugerido del sistema de HC: Si" , "usaInibidorSugeridoID" );
			else
				showMessage( "Sugerido del sistema de HC: No" , "usaInibidorSugeridoID" );	
			
			if( paciente.sexo!="" && paciente.sexo!="undefined" && paciente.sexo!="null" && paciente.sexo!=null )
			{
				if( paciente.sexo=="F" )
					showMessage( "Sugerido del sistema de HC: Femenino" , "sexoSugeridoID" );
				else
					showMessage( "Sugerido del sistema de HC: Masculino" , "sexoSugeridoID" );
			}
			
			if( paciente.estadoCivil!="" && paciente.estadoCivil!="undefined" && paciente.estadoCivil!="null" && paciente.estadoCivil!=null )
			{
				var estado = paciente.estadoCivil;
				estado = estado.toUpperCase();
				
				if( estado=="CASADA" || estado=="CASADO" ) showMessage( "Sugerido del sistema de HC: Casado" , "estadoCivilSugeridoID" );
				if( estado=="SOLTERA" || estado=="SOLTERO" ) showMessage( "Sugerido del sistema de HC: Soltero" , "estadoCivilSugeridoID" );
				if( estado=="SEPARADA" || estado=="SEPARADO" ) showMessage( "Sugerido del sistema de HC: Separado" , "estadoCivilSugeridoID" );
				if( estado=="VIUDA" || estado=="VIUDO" ) showMessage( "Sugerido del sistema de HC: Viudo" , "estadoCivilSugeridoID" );
			}
			
			showMessage( "OS del sistema de HC sugerida: " + paciente.obraSocial , "OS_Sugerida" );			
		}
	};
	

	
	
	/* Obras Sociales Adheridas -------------------------------------*/
	
	function mostrarObrasSocialesAdheridas() {
		hideMessage("obrasSocialesAdheridasErrorID");
		$('#obrasSocialesAdheridas').jqmShow();
	}
	
	function ocultarObrasSocialesAdheridas()
	{
		var oss = "";
		
		for( c=0 ; c<index ; c++ )
		{
			if( oss=="" )
				oss += $('#obrasSocialesAdheridasAuto'+c+'\\.nombre').val();
			else
				oss += " - " + $('#obrasSocialesAdheridasAuto'+c+'\\.nombre').val();
		}
		
		$('#obrasSocialesAdicionalesAdheridasID').val( oss );
		$('#obrasSocialesAdheridas').jqmHide();
	}
	
	
	/* Funcion inicial ---------------------------------------------- */
	
	function manejarError()
	{
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
	}
	
	$(document).ready(function()
	{
		//Modales
		$('#getDatosLaboratorioID').jqm({modal: true});
		$('#getDatosHCID').jqm({modal: true});
		$('#obrasSocialesAdheridas').jqm({modal: true});
		
		autocompletar('obraSocialActual');
		
		$("#fechaNacimientoID").datepicker($.datepicker.regional['es']);
		
		//Ajax Forms
		ajaxForm( 'nuevoPacienteForm' , pacienteAgregado , null , errorAgregandoPaciente );
		ajaxForm( 'getDatosLaboratorioForm' , sucessGetDatosPacienteLabo , null , errorGetDatosPacienteLabo );
		ajaxForm( 'getDatosHCForm' , sucessGetDatosPacienteHC , null , errorGetDatosPacienteHC );
		
		manejarError();
	});	
	
</script>

<script type="text/javascript">
	
	function eliminarInput(index){
		var elem =document.getElementById('obrasSocialesAdheridasAuto' + index + '.indice');
		var parentN = elem.parentNode;
		while(parentN.hasChildNodes())
			parentN.removeChild(parentN.firstChild);
		
	}

	function reemplazarInputs(atributo,index,siguiente){
		var elem =document.getElementById('obrasSocialesAdheridasAuto' + index + '.'+atributo);
		var elemSiguiente =document.getElementById('obrasSocialesAdheridasAuto' + siguiente + '.'+atributo);
		elem.value=elemSiguiente.value;
			
	}

	function eliminarOSAdherida(indice) {
		
		var fin=index-1;
		var i;
		for(i=indice;i<fin;i++){
			var siguiente=i+1;
			reemplazarInputs('codigo',i,siguiente);
			reemplazarInputs('nombre',i,siguiente);
		};
		
		index--;
		eliminarInput(index);		
	}
	
	
	$(function() 
	{		
	    index = 0;
	    
	    $("#add").click(function() 
	    {
	        
	    	$("#obrasSocialesAdicionalesAdheridas tr:last").before(function() 
	    	{
	        	var count=index+1;
	        	
	        	var html ='<tr>';
	        	html += '<td valign="middle" id="obrasSocialesAdheridasAuto' + index + '.indice" >'+count+"</td>";
	        	html+='<td><input type="text" id="obrasSocialesAdheridasAuto' + index + '.codigo" name="obrasSocialesAdheridasAuto[' + index + '].codigo" size="10" /></td>';
	        	html += '<td><input type="text" id="obrasSocialesAdheridasAuto' + index + '.nombre" name="obrasSocialesAdheridasAuto[' + index + '].nombre" size="70" /></td>';
	            html += '<td valign="middle"><a href="#" id="obrasSocialesAdheridasAuto' + index + '.eliminar" onclick="eliminarOSAdherida(' + index + ')">eliminar</a></td>';
	            html+='</tr>';
	            return html;
	       	 });
	        
			styleInput('obrasSocialesAdheridasAuto' + index + '.codigo');
			styleInput('obrasSocialesAdheridasAuto' + index + '.nombre');
			autocompletar('obrasSocialesAdheridasAuto' + index);
			
	        index++;
	        return false;
	    });
	});
	
    function styleInput(id){
        
        var elem =document.getElementById(id);
        var oldElem=elem;
        inputText(elem);
        elem.init();
        oldElem.parentNode.replaceChild(elem,oldElem);
        }
	
 </script>



<div class="center_content">
	
		<table style="width: 100%;">
		<tr>
			<td> <h2>Datos del Nuevo Paciente</h2>	 </td>

			<td> <a href="#" class="bt_green" onclick="cargarDesdeHC();">
			 <span class="bt_green_lft"></span><strong>Cargar desde Sistema HC</strong><span class="bt_green_r"></span>
			</a> 
			
			<a href="#" class="bt_blue" onclick="cargarDesdeLabo();">
				<span class="bt_blue_lft"></span><strong>Cargar desde Sistema Laboratorio</strong><span class="bt_blue_r"></span>
			</a>
			</td>
	</tr>
	
	</table>
	
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>
	
	<form:form cssClass="niceform" method="post" name="nuevoPacienteForm" id="nuevoPacienteForm" action="doNuevoPaciente.htm" modelAttribute="nuevoPacienteDTO">
	
		<form:hidden path="id" id="idPacienteID"/>
		
		<div><h4>Datos personales</h4></div>
		
		<fieldset>
			<dl>
				<dt>
					<label for="tipoDoc">Tipo de Documento:</label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="tipoDoc" size="10" id="tipoDocID" /></td></tr>
					<tr> <td><div id="tipoDocErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt>
					<label for="nroDNI">N&uacute;mero de Documento:</label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="nroDNI" size="40" id="nroDNIID"/></td></tr>
					<tr> <td><div id="nroDNIErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="nombreyApellido">Nombre y Apellido:</label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="nombreyApellido" size="40" id="nombreyApellidoID"/></td></tr>
					<tr> <td><div id="nombreyApellidoErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="numHistoriaClinica">N&uacute;mero de HC: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="numHistoriaClinica" size="40" id="numHistoriaClinicaID"/></td></tr>
					<tr> <td><div id="numHistoriaClinicaErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			
			<dl>
				<dt>
					<label for="sexo">Sexo: </label>
				</dt>
				<dd>
					<div id="sexoSugeridoID" class="divError"></div>
					<form:select id="sexoSelect" path="sexo">
						<form:option value="M" label="Masculino" />
						<form:option value="F" label="Femenino" />
					</form:select>
				</dd>
			</dl>			
			
			<dl>
				<dt>
					<label for="estadoCivil">Estado Civil: </label>
				</dt>
				<dd>
				<dd>
					<div id="estadoCivilSugeridoID" class="divError"></div>
					<form:select id="estadoCivilSelect" path="estadoCivil">
						<form:option value="S" label="Soltero" />
						<form:option value="C" label="Casado" />
						<form:option value="V" label="Viudo" />
						<form:option value="D" label="Divorciado" />
						<form:option value="O" label="Otro" />
					</form:select>
				</dd>
			</dl>			
			
			<dl>
				<dt>
					<label for="fechaNacimiento">Fecha de Nacimiento: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="fechaNacimiento" size="20" id="fechaNacimientoID"/></td></tr>
					<tr> <td><div id="fechaNacimientoErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>			
			
			<dl>
				<dt>
					<label for=telefono>Tel&eacute;fono: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="telefono" size="25" id="telefonoID"/></td></tr>
					<tr> <td><div id="telefonoErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt>
					<label for=celular>Celular: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="celular" size="25" id="celularID"/></td></tr>
					<tr> <td><div id="celularErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
						
			<dl>
				<dt>
					<label for=email>Email: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="email" size="35" id="emailID"/></td></tr>
					<tr> <td><div id="emailErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			</fieldset>
			
			<div><h4>Direcci&oacute;n</h4></div>
			
			<fieldset>
			
			<dl>
				<dt>
					<label for="direccion">Direccion: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="direccion" size="30" id="calleID"/></td></tr>
					<tr> <td><div id="calleErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>		
			
			<dl>
				<dt>
					<label for="localidad">Localidad: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="localidad" size="30" id="localidadID"/></td></tr>
					<tr> <td><div id="localidadErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="provincia">Provincia: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="provincia" size="30" id="provinciaID"/></td></tr>
					<tr> <td><div id="provinciaErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="pais">Pa&iacute;s: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="pais" size="30" id="paisID"/></td></tr>
					<tr> <td><div id="paisErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt>
					<label for="codigoPostal">C&oacute;digo Postal: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="codigoPostal" size="15" id="codigoPostalID"/></td></tr>
					<tr> <td><div id="codigoPostalErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			
			</fieldset>
			
			<div><h4>Enfermedad</h4></div>
			
			<fieldset>
			
			<dl>
				<dt>
					<label for=diagnostico>Diagnostico: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="diagnostico" size="15" id="diagnosticoID"/></td></tr>
					<tr> <td><div id="diagnosticoErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>	
			
			<dl>
				<dt>
					<label for=severidad>Severidad: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="severidad" size="15" id="severidadID"/></td></tr>
					<tr> <td><div id="severidadErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>		
			
			<dl>
				<dt>
					<label for=tipoHemofilia>Tipo de Hemofilia: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="tipoHemofilia" size="15" id="tipoHemofiliaID"/></td></tr>
					<tr> <td><div id="tipoHemofiliaErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for=grupoSanguineo>Grupo Sangu&iacute;neo: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="grupoSanguineo" size="15" id="grupoSanguineoID"/></td></tr>
					<tr> <td><div id="grupoSanguineoErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>			
			
			<dl>
				<dt>
					<label for=factor>Factor: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="factor" size="15" id="factorID"/></td></tr>
					<tr> <td><div id="factorErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>	
			
			<dl>
				<dt>
					<label for=usaInhibidor>Usa inhibidor: </label>
				</dt>
				<dd>
					<div id="usaInibidorSugeridoID" class="divError"></div>
				
					<form:select id="usaInibidorID" path="usaInibidor">
						<form:option value="No" label="No" />
						<form:option value="Si" label="Si" />
					</form:select>
				</dd>
			</dl>			
			
			
			
			
	
			
			</fieldset>
			
			<div><h4>Obra Social</h4></div>

			<fieldset>
			
			<dl>
				<dt>
					<label for=numAfiliadoOSActual>Obra Social Actual: </label>
				</dt>
				<dd>
					<div id="OS_Sugerida" class="divError"></div>
					<table style="width: 670px;">
					<tr> <td><form:input path="obraSocialActual.codigo" size="10" id="obraSocialActual.codigo" readonly="true"/></td></tr>
					<tr><td><input type="text" style="width: 630px;" id="obraSocialActual.nombre"/></td>
					</tr>
					<tr> <td><div id="obraSocialActualErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for=numAfiliadoOSActual>N&uacute;mero de Afiliado OS Actual: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="numAfiliadoOSActual" size="40" id="numAfiliadoOSActualID"/></td></tr>
					<tr> <td><div id="numAfiliadoOSActualErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>	
			
			
			<dl>
				<dt>
					<label for=obrasSocialesAdheridas>OS Adicionales Adheridas: </label>
				</dt>
				<dd>
					<table>						
					<tr> <td><input type="text" size="40" id="obrasSocialesAdicionalesAdheridasID" readonly="readonly"/></tr>
					<tr> <td><div id="obrasSocialesAdheridasErrorID" class="divError"></div></td></tr>
					<tr> <td><input type="button" name="cargarOSs" id="cargarOSs" value="Cargar OS adheridas" onclick="mostrarObrasSocialesAdheridas()" /></td></tr>
					</table>
				</dd>
			</dl>
			

			<dl><dt></dt></dl>
			<dl class="submit">
				<dt><input type="button" name="submit" id="submit" value="Cargar" onclick="validaryComitearForm()"/></dt>
				<dt><input type="button" name="volver" id="volver" value="Volver" onclick="volverPacientes()"/></dt>
			</dl>

		</fieldset>
		
		<div id="obrasSocialesAdheridas" style="display: none;" class="jqmWindow obrasSocialesAdheridas">
			<h2>Obras Sociales Adicionales</h2>
			<table id="obrasSocialesAdicionalesAdheridas">
				<tbody>
					<tr><td></td></tr>
				</tbody>
			</table>
			
			<input type="button" value="Agregar" name="agregar" id="add" /><br><br>
			<input type="button" onclick="ocultarObrasSocialesAdheridas()" value="Aceptar" name="submit" id="submit" />
		</div>
		
	</form:form>
	
	
	
	<form:form action="volverPacientes.htm" method="post" id="volverPacientesForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID"/>
		<form:hidden path="errorMessage" id="errorMessageResultID"/>	
	</form:form>
	
	
	<div id="getDatosLaboratorioID" style="display:none;" class="jqmWindow">
		<form:form  method="post" name="getDatosLaboratorioForm" id="getDatosLaboratorioForm" cssClass="niceform" 
					action="getInformacionPacienteLaboratorio.htm" modelAttribute="nuevoPacienteLaboDTO">
		
			<fieldset>
			<dl>
				<dt>
					<label for="id">C&oacute;digo de Paciente: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="id" size="40" id="idSistemaLaboGetDatosID"/></td></tr>
					<tr> <td><div id="idSistemaLaboGetDatosErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl><dt></dt></dl>
			<dl class="submit">
					<dt> <input type="button" value="Obtener Datos" onclick="obtenerDatosLaboratorio('true');"/> </dt>
					<dt> <input type="button" value="Cancelar" onclick="obtenerDatosLaboratorio('false');"/> </dt>			
			</dl>
			</fieldset>

		</form:form>
	</div>
	
	


	<div id="getDatosHCID" style="display:none;" class="jqmWindow">
		<form:form  method="post" name="getDatosHCForm" id="getDatosHCForm" cssClass="niceform" 
					action="getInformacionPacienteHC.htm" modelAttribute="nuevoPacienteHCDTO">
		
			<fieldset>
			<dl>
				<dt>
					<label for="numHistoriaClinica">N&uacute;mero de HC: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="numHistoriaClinica" size="40" id="numHistoriaClinicaGetDatosID"/></td></tr>
					<tr> <td><div id="numHistoriaClinicaGetDatosErrorID" class="divError"></div></td></tr>
					</table>
				</dd>
			</dl>
			
			<dl><dt></dt></dl>
			<dl class="submit">
					<dt> <input type="button" value="Obtener Datos" onclick="obtenerDatosHC('true');"/> </dt>
					<dt> <input type="button" value="Cancelar" onclick="obtenerDatosHC('false');"/> </dt>			
			</dl>
			</fieldset>

		</form:form>
	</div>
	

	
	

</div>

