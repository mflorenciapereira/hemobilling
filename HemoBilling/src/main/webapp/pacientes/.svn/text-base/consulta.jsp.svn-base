<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<script src="js/jquery/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery/form/jquery.form.js" type="text/javascript"></script>
<script src="js/jquery/modal/jqModal.js" type="text/javascript"></script>
<script src="js/jconfirmaction.hemobilling.js" type="text/javascript"></script>

<script src="js/ajaxForm.js" type="text/javascript"></script>
<script src="js/hemoUtils.js" type="text/javascript"></script>
<script src="js/inputValidators.js" type="text/javascript"></script>


<script type="text/javascript">

	var totalRegistros;
	var regsPorPagina; 
	var paginaActual; 
	var totalPaginas;

	/*CallBacks y acciones de eliminacion y edicion ----------------------------------------*/
	
	function editar( dni )
	{		
		$('#nroDNIEditarID').val(dni); 
		$('#editarForm').submit();
	}
	
	function verDatos( dni )
	{		
		$('#nroDNIDatosID').val(dni); 
		$('#datosForm').submit();
	}
	
	var aceptaEliminar = function aceptaEditar( idSelected )
	{
		var div = "#paciente"+idSelected;
		var dni = $(div).val();
		$('#nroDNIEliminarID').val(dni);
		$('#eliminarForm').submit();
	}
	
	
	/* Administracion de Filtros -------------------------------------------------------- */
	function mostrarEstadoFiltro()
	{
		var mensaje = "Mostrando: Nro de DNI: "+ "${filtroDTO.nroDNI}" + 
					  " | Nro de HC: " + "${filtroDTO.numHistoriaClinica}" + 
					  " | Nombre completo: " + "${filtroDTO.nombreyApellido} ";
					  
		showMessage( mensaje , "descripcionFiltroID" );
	}
	
	function mostrarFiltros()
	{
		$('#filtroBusqueda').jqmShow();
	}
	
	function refinarConsulta( confirma )
	{
		$('#filtroBusqueda').jqmHide();
		if( confirma=="true") 
		{
			if( $('#nroDNIFiltrarID').val()=="" ) $('#nroDNIFiltrarID').val("Todos");
			if( $('#numHistoriaClinicaFiltrarID').val()=="" ) $('#numHistoriaClinicaFiltrarID').val("Todos");
			if( $('#nombreyApellidoFiltrarID').val()=="" ) $('#nombreyApellidoFiltrarID').val("Todos");
			
			$( '#filtrarForm' ).submit();
			
		}
	}
	
	
	/*Manejo de paginado---------------------------------------------------------*/
	
	function mostrarEstadoPaginado()
	{
		totalRegistros =  "${filtroPaginadoDTO.cantTotalRegs}";
		regsPorPagina =  "${filtroPaginadoDTO.regPorPagina}"; 
		paginaActual =  "${filtroPaginadoDTO.numeroPaginaActual}"; 
		totalPaginas =  "${filtroPaginadoDTO.cantMaxPaginas}";
		
		//Manejo del control de paginas anteriores
		if( paginaActual==1 )
		{
			showMessage( "<span class='disabled'>&lt;&lt; Primera p&aacute;gina</span>"              , "paginadoPrimeroID" );
			showMessage( "<span class='disabled'>&lt; Anteriores "+regsPorPagina+" registros</span>" , "paginadoAnteriorID" );
		}
		else
		{
			showMessage( "<a href='#' onclick='irPrimeraPagina();'>&lt;&lt; Primera p&aacute;gina</a>"                , "paginadoPrimeroID" );
			showMessage( "<a href='#' onclick='irAnteriorPagina();'>&lt; Anteriores "+regsPorPagina+" registros</a>"  , "paginadoAnteriorID" );
		}
		
		//Manejo del texto del cuadro del medio
		var primerElementoMostrado = (paginaActual-1)*regsPorPagina+1;
		var ultimoElementoMostrado;
		if( totalRegistros<=(paginaActual*regsPorPagina) )
		{
			//Esta pagina muestra el ultimo elemento
			ultimoElementoMostrado = totalRegistros;
		}
		else
		{
			//Esta pagina no muestra el ultimo elemento
			ultimoElementoMostrado = paginaActual*regsPorPagina;
		}
		
		
		var texto = "<span class='current'>Registros "+primerElementoMostrado+" a "+ultimoElementoMostrado+" de "+totalRegistros +"</a>";
					
		showMessage(texto,"paginadoActualID");
		
		//Manejo del control de paginas anteriores
		if( paginaActual==totalPaginas )
		{
			showMessage( "<span class='disabled'>Siguientes "+regsPorPagina+" registros &gt;</span>" , "paginadoSiguienteID" );
			showMessage( "<span class='disabled'>&Uacute;ltima p&aacute;gina &gt;&gt;</span>"        , "paginadoUltimoID" );
		}
		else
		{
			showMessage( "<a href='#' onclick='irSiguientePagina();'>Siguientes "+regsPorPagina+" registros &gt;</a>"  , "paginadoSiguienteID" );
			showMessage( "<a href='#' onclick='irUltimaPagina();'>&Uacute;ltima p&aacute;gina &gt;&gt;</a>"            , "paginadoUltimoID" );
		}
	}
	
	function irPrimeraPagina()
	{
		$('#numeroPaginaActualID').val("1"); 
		$('#paginarForm').submit();	
	}
	
	function irAnteriorPagina()
	{
		paginaActual--;
		$('#numeroPaginaActualID').val( paginaActual ); 
		$('#paginarForm').submit();	
	}
	
	function irSiguientePagina()
	{
		paginaActual++;
		$('#numeroPaginaActualID').val( paginaActual ); 
		$('#paginarForm').submit();	
	}
	
	function irUltimaPagina()
	{
		$('#numeroPaginaActualID').val( totalPaginas ); 
		$('#paginarForm').submit();	
	}
	
	
	
	/* Callbacks de datos de pacientes ----------------------------------------*/
	
	function armarTablaDetalle( paciente )
	{
		var html = "<table>";
		
		console.log(paciente);
		
		if( paciente.tipoDoc==undefined ) paciente.tipoDoc="";
		if( paciente.nroDNI==undefined ) paciente.nroDNI="";
		if( paciente.numHistoriaClinica==undefined ) paciente.numHistoriaClinica="";
		if( paciente.nombreyApellido==undefined ) paciente.nombreyApellido="";
		if( paciente.sexo==undefined ) paciente.sexo="";
		if( paciente.estadoCivil==undefined ) paciente.estadoCivil="";
		if( paciente.fechaNacimiento==undefined ) paciente.fechaNacimiento="";
		if( paciente.direccion==undefined ) paciente.direccion="";
		if( paciente.localidad==undefined ) paciente.localidad="";
		if( paciente.provincia==undefined ) paciente.provincia="";
		if( paciente.pais==undefined ) paciente.pais="";
		if( paciente.codigoPostal==undefined ) paciente.codigoPostal="";
		if( paciente.telefono==undefined ) paciente.telefono="";
		if( paciente.celular==undefined ) paciente.celular="";
		if( paciente.email==undefined ) paciente.email="";
		if( paciente.diagnostico==undefined ) paciente.diagnostico="";
		if( paciente.severidad==undefined ) paciente.severidad="";
		if( paciente.tipoHemofilia==undefined ) paciente.tipoHemofilia="";
		if( paciente.grupoSanguineo==undefined ) paciente.grupoSanguineo="";
		if( paciente.factor==undefined ) paciente.factor="";
		if( paciente.obraSocialActual.nombre==undefined ) paciente.obraSocialActual.nombre="";
		if( paciente.numAfiliadoOSActual==undefined ) paciente.numAfiliadoOSActual="";
		
		html += "<tr><td>Documento: </td><td><b>"+paciente.tipoDoc + " - " + paciente.nroDNI+"</b></td></tr>";
		html += "<tr><td>Nombre: </td><td><b>"+paciente.nombreyApellido+"</b></td></tr>";
		html += "<tr><td>N&uacute;mero de HC: </td><td><b>"+paciente.numHistoriaClinica+"</b></td></tr>";
		html += "<tr><td>Sexo: </td><td><b>"+paciente.sexo+"</b></td></tr>";
		html += "<tr><td>Estado Civil: </td><td><b>"+paciente.estadoCivil+"</b></td></tr>";
		html += "<tr><td>Fecha de Nacimiento: </td><td><b>"+paciente.fechaNacimiento+"</b></td></tr>";
		html += "<tr><td>Direcci&oacute;n: </td><td><b>"+paciente.direccion+", "+paciente.localidad+", "+paciente.provincia+", "+paciente.pais+". CP: "+paciente.codigoPostal+"</b></td></tr>";
		html += "<tr><td>Tel&eacutefono;: </td><td><b>"+paciente.telefono+"</b></td></tr>";
		html += "<tr><td>Celular: </td><td><b>"+paciente.celular+"</b></td></tr>";
		html += "<tr><td>Email: </td><td><b>"+paciente.email+"</b></td></tr>";
		html += "<tr><td>Diagn&oacute;stico: </td><td><b>"+paciente.diagnostico+"</b></td></tr>";
		html += "<tr><td>Severidad: </td><td><b>"+paciente.severidad+"</b></td></tr>";
		html += "<tr><td>Tipo de Hemofilia: </td><td><b>"+paciente.tipoHemofilia+"</b></td></tr>";
		html += "<tr><td>Grupo y Factor Sangu&iacute;neo: </td><td><b>"+paciente.grupoSanguineo+ " "+paciente.factor+"</b></td></tr>";
		html += "<tr><td>Obra Social Actual: </td><td><b>"+paciente.obraSocialActual.nombre+" - "+paciente.numAfiliadoOSActual+"</b></td></tr>";
		
		html += "<tr><td>Obras Sociales Adheridas: </td><td><b>";
	
		var lista = eval(paciente.obrasSocialesAdheridasAuto);
		if( lista!=undefined )
		{
			var listaOS = eval(lista[0]);
			
			if( listaOS.length!=0 )
			{
				var primero = true;
				for (var i=0, obraSocial ; obraSocial = listaOS[i++];)
				{
					if( primero )
					{
						primero = false;
					}
					else
					{
						html += " <br> ";
					}

					html += obraSocial.nombre;
				}
			}
			else
				html += "Ninguna";
		}
		else
		{
			html += "Ninguna";	
		}

		
		html += "</b></td></tr>";		
		
		html += "</table>";
		
		return html;
	}
	
	function aceptarDatosPaciente()
	{
		$('#datosPaciente').jqmHide();
	}
	
	
	
	var datosPacienteError = function datosPacienteError(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	
	
	var datosPacienteSucess = function datosPacienteSucess(json)
	{
		var result = json.data[0];
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			var datos = armarTablaDetalle( result );
			$('#middleDatosPacienteDiv').html( datos ); 
			$('#datosPaciente').jqmShow();
		}
			 
	};
	
	/*Formulario de Cambio de IDs --------------------------------------------*/
	
	function actualizarIDs()
	{
		$('#idSistemaLaboID').val("");
		$('#numHistoriaClinicaID').val("");
		$('#cambiarIDsID').jqmShow();
	}
	
	function cambiarIDs( confirma )
	{
		if( confirma=="true") 
		{
			if( $('#idSistemaLaboID').val()!="" && validateNumeric( $('#idSistemaLaboID').val() ) &&
				$('#numHistoriaClinicaID').val()!="" && validateNumeric( $('#numHistoriaClinicaID').val() )	)
			{
				$('#cambiarIDsID').jqmHide();
				$( '#cambiarIDsForm' ).submit();
			}			
		}
		else
		{
			$('#cambiarIDsID').jqmHide();
		}
		
	}
	
	var errorCambiarIDs = function errorCambiarIDs(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var sucessCambiarIDs = function sucessCambiarIDs(json)
	{
		var result = json.data[0];
		
		if( result.error=="true")
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
		}
		else
		{
			showMessage( result.errorMessage , "exitoMessageDivID" );
		}
	};
	
	
	/*funcion OnReady---------------------------------------------------------*/
	
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
	
	
	$(document).ready(function() {		
		$('.askDelete').jConfirmAction( aceptaEliminar , null );
		
		ajaxForm( 'datosForm' , datosPacienteSucess , null , datosPacienteError );
		ajaxForm( 'cambiarIDsForm' , sucessCambiarIDs , null , errorCambiarIDs );
		
		$('#filtroBusqueda').jqm({modal: true});
		$('#datosPaciente').jqm({modal: true});
		$('#cambiarIDsID').jqm({modal: true});
		
		manejarError();
		
		mostrarEstadoFiltro();
		mostrarEstadoPaginado();
		
		
	});
	
	
</script>

<div class="center_content">
	
	<table style="width: 100%;">
	<tr>
		<td> <h2>Administraci&oacute;n de Pacientes</h2>	 </td>

		<td> 
		
			<a href="#" class="bt_blue" onclick="actualizarIDs();">
				<span class="bt_blue_lft"></span><strong>Actualizar IDs</strong><span class="bt_blue_r"></span>
			</a>
			
			<a href="nuevoPaciente.htm" class="bt_green">
			 <span class="bt_green_lft"></span><strong>Agregar</strong><span class="bt_green_r"></span>
			</a> 
			
			<a href="#" class="bt_blue" onclick="mostrarFiltros();">
				<span class="bt_blue_lft"></span><strong>Refinar lista</strong><span class="bt_blue_r"></span>
			</a>
						
		</td>
	</tr>
	
	</table>
	
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>
	
	
	<div id="descripcionFiltroID" class="filter_text"></div>
	<table id="rounded-corner" summary="Pacientes">
	<thead>
		<tr>
			<th width="200" class="rounded-company" scope="col" style="text-align: center;">Nro. de DNI</th>
			<th width="100" class="rounded" scope="col" style="text-align: center;">Nro. de HC</th>
			<th width="200" class="rounded" scope="col" style="text-align: center;">Nombre</th>
			<th width="100" class="rounded" scope="col" style="text-align: center;">Telefono</th>
			<th width="30" class="rounded" scope="col" style="text-align: center;">Datos</th>
			<th width="30" class="rounded" scope="col" style="text-align: center;">Editar</th>
			<th width="30" class="rounded-q4" scope="col" style="text-align: center;">Eliminar</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${listapacientesDTO}" var="paciente" varStatus="i">
			<tr>
				<td align="center">${paciente.nroDNI}</td>
				<td align="center" >${paciente.numHistoriaClinica}</td>
				<td align="center" >${paciente.nombreyApellido}</td>
				<td align="center" >${paciente.telefono}</td>
						
				<td align="center"><a href="#" onclick="verDatos('${paciente.id}')"><img src="images/info.png" alt="" title="" border="0" /></a></td>
				<td align="center"><a href="#" onclick="editar('${paciente.id}')"><img src="images/user_edit.png" alt="" title="" border="0" /></a></td>
				
				<td align="center">
					<a href="#" class="askDelete" id="${i.count}"><img src="images/trash.png" alt="" title="" border="0" /></a>
					<input type="hidden" id="paciente${i.count}" value="${paciente.id}" />
				</td>
					
			</tr>			
		</c:forEach>
	</tbody>
		
	<tfoot>
		<tr>
			<td colspan="6" class="rounded-foot-left"></td>
			<td class="rounded-foot-right">&nbsp;</td>
		</tr>
	</tfoot>	
	</table>
	
	<div class="pagination">
        <table>
	        <tr>
		        <td> <div id="paginadoPrimeroID"></div></td>
		        <td> <div id="paginadoAnteriorID"></div> </td>
		        <td> <div id="paginadoActualID"></div> </td>
		        <td> <div id="paginadoSiguienteID"></div> </td>
		        <td> <div id="paginadoUltimoID"></div> </td>
	        </tr>
        </table>
	</div>
	
	
	<form:form  method="post" name="paginarForm" id="paginarForm" action="refreshPacientes.htm" commandName="filtroPaginadoDTO">
		<form:hidden path="cantTotalRegs" id="cantTotalRegsID"/>
		<form:hidden path="regPorPagina" id="regPorPaginaID"/>
		<form:hidden path="numeroPaginaActual" id="numeroPaginaActualID"/>
		<form:hidden path="cantMaxPaginas" id="cantMaxPaginasID"/>
	</form:form>
	
	
	
	<div id="filtroBusqueda" style="display:none;" class="jqmWindow">
		<form:form  method="post" name="filtrarForm" id="filtrarForm" cssClass="niceform" action="refreshPacientes.htm" commandName="filtroDTO">
		
			<fieldset>
				<dl>
					<dt> <label for="nroDNI">Nro de DNI:</label> </dt>
					<dd> <form:input path="nroDNI" id="nroDNIFiltrarID"/> </dd>
				</dl>
				<dl>
					<dt> <label for="numHistoriaClinica">Nro de HC:</label> </dt>
					<dd> <form:input path="numHistoriaClinica" id="numHistoriaClinicaFiltrarID"/> </dd>
				</dl>
				<dl>
					<dt> <label for="nombreyApellido">Nombre:</label> </dt>
					<dd> <form:input path="nombreyApellido" id="nombreyApellidoFiltrarID"/> </dd>
				</dl>
				
				
				<dl><dt></dt></dl>
				<dl class="submit">
					<dt> <input type="button" value="Filtrar" onclick="refinarConsulta('true');"/> </dt>
					<dt> <input type="button" value="Cancelar" onclick="refinarConsulta('false');"/> </dt>			
				</dl>
			</fieldset>

		</form:form>
	</div>
	
	
	
	<div id="datosPaciente" style="display:none;" class="jqmWindow">
		<form:form  method="post" cssClass="niceform">
		
		<div id="topDatosPacienteDiv"></div>
		<div id="middleDatosPacienteDiv"></div>
		<div id="downDatosPacienteDiv">
			<dl class="submit">
				<dt> <input type="button" value="Aceptar" onclick="aceptarDatosPaciente();"/> </dt>		
			</dl>
		</div>
		
		</form:form>
	</div>
	


	<form:form  method="post" name="editarForm" id="editarForm" action="editarPaciente.htm" commandName="pacienteDTO">
		<form:hidden path="id" id="nroDNIEditarID"/>
	</form:form>
	
	<form:form  method="post" name="datosForm" id="datosForm" action="getDetallePaciente.htm" commandName="pacienteDTO">
		<form:hidden path="id" id="nroDNIDatosID"/>
	</form:form>
	
	<form:form  method="post" name="eliminarForm" id="eliminarForm" action="doEliminarPaciente.htm" commandName="pacienteDTO">
		<form:hidden path="id" id="nroDNIEliminarID"/>
	</form:form>
	
	
	<div id="cambiarIDsID" style="display:none;" class="jqmWindow obrasSocialesAdheridas">
		<div>Con esta opci&oacute;n podra cambiar el identificador auxiliar de un paciente en el sistema de Laboratorio <br>
		por el n&uacute;mero de HC correspondiente en el Sistema de HC.</div>
		
		<form:form  method="post" name="cambiarIDsForm" id="cambiarIDsForm" cssClass="niceform" 
					action="cambiarIDSistemaLaboratorio.htm" modelAttribute="pacienteDTO">
		
			<fieldset>
			
			<dl>
				<dt>
					<label for="idSistemaLabo">C&oacute;digo de Laboratorio: </label>
				</dt>
				<dd>
					<table>
					<tr> <td><form:input path="id" size="40" id="idSistemaLaboID"/></td></tr>
					<tr> <td><div id="idSistemaLaboErrorID" class="divError"></div></td></tr>
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
			
			<dl><dt></dt></dl>
			<dl class="submit">
					<dt> <input type="button" value="Cambiar IDs" onclick="cambiarIDs('true');"/> </dt>
					<dt> <input type="button" value="Cancelar" onclick="cambiarIDs('false');"/> </dt>			
			</dl>
			</fieldset>

		</form:form>
	</div>

</div>
