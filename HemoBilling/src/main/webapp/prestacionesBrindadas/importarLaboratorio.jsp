<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
    
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="js/modal.js"></script>
<script src="js/ajaxForm.js" type="text/javascript"></script>
<script src="js/hemoUtils.js" type="text/javascript"></script>
<script src="js/inputValidators.js" type="text/javascript"></script>

<link rel="stylesheet" href="estilos/cupertino/datepicker.css">
<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.datepicker-es.js"></script>
<script src="js/jquery/datepicker.js"></script>
<script src="js/jquery.multiple.select.js"></script>

<script type="text/javascript">
	
	//FUNCIONES DE VALIDACION ----------------------------------------------------------------------------
	var cantidadAceptados;
	
	function validarInput()
	{
		var ok = true;
		hideMessage("errorInput");
		
		if( $('#fechaInicioID').val()=="" )
		{
			ok = false;
			showMessage( "Ambas fechas son obligatorias" , "errorInput" );
		}
		else
		{
			if( !validarFormato( $('#fechaInicioID').val() ) )
			{
				ok = false;
				showMessage( "La fecha desde tiene formato inv&aacute;lido" , "errorInput" );
			}	
		}
		
		
		if( $('#fechaFinalID').val()=="" )
		{
			ok = false;
			showMessage( "Ambas fechas son obligatorias" , "errorInput" );
		}
		else
		{
			if( !validarFormato( $('#fechaFinalID').val() ) )
			{
				ok = false;
				showMessage( "La fecha hasta tiene formato inv&aacute;lido" , "errorInput" );
			}	
		}
		
		return ok;
	}
	
	
	//FUNCIONES DE ARMADO DE TABLAS RESULTADOS ----------------------------------------------------------------
	
	function actualizarContadorAceptados()
	{
		$('#tituloAceptados').html( "<h4>Estudios de Laboratorio Aceptados ("+cantidadAceptados+")</h4>" );
		$('#cantidadPrestacionesAprobadas').val(cantidadAceptados);
	}
	
	function eliminarFila( nroFila )
	{
		//Elimino la fila
		var idFila = "#fila"+nroFila;		
		$(idFila).remove();		
	
		
		cantidadAceptados--;
		actualizarContadorAceptados();
	}
	
	function armarTablaListaResultadoBusqueda( result )
	{
		var lista = eval( result );

		var html = "<table id='rounded-corner' style='width: 800px;'> <thead> ";
		html += "<tr> <th class='rounded-company' scope='col'>N&uacute;mero HC</th>";
		html += "<th class='rounded' scope='col'>Nombre</th>";
		html += "<th class='rounded' scope='col'>Fecha</th>";
		html += "<th class='rounded' scope='col'>Estudio</th>";
		html += "<th class='rounded' scope='col'>Resultado</th>";
		html += "<th class='rounded-q4' scope='col'>Eliminar</th>";
		html += "</tr>";
		html += "</thead> <tfoot> <tr>";
		html += "<td colspan='5' class='rounded-foot-left'>&nbsp;</td> <td class='rounded-foot-right'>&nbsp;</td>";
		html += "</tr> </tfoot> <tbody>";
		
		for( var c=0 ; c<lista.length ; c++ )
		{
			var analisis = lista[c];
			
			
			var idFila = "fila"+c;
			
			var htmlAnalisis = "<tr class='textoVerde' id='"+idFila+"'>";
			
			htmlAnalisis += "<input type='hidden' id='analisisAprobados" + c + ".fecha' name='analisisAprobados[" + c + "].fecha' value='"+analisis.fecha+"'/> ";			
			htmlAnalisis += "<input type='hidden' id='analisisAprobados" + c + ".observaciones' name='analisisAprobados[" + c + "].observaciones' value='"+analisis.observaciones+"'/> ";
			htmlAnalisis += "<input type='hidden' id='analisisAprobados" + c + ".nroHCPaciente' name='analisisAprobados[" + c + "].nroHCPaciente' value='"+analisis.nroHCPaciente+"'/> ";			
			htmlAnalisis += "<input type='hidden' id='analisisAprobados" + c + ".idPaciente' name='analisisAprobados[" + c + "].idPaciente' value='"+analisis.idPaciente+"'/> ";	
			htmlAnalisis += "<input type='hidden' id='analisisAprobados" + c + ".observaciones' name='analisisAprobados[" + c + "].codPrestacion' value='"+analisis.codPrestacion+"'/> ";

			if(analisis.moduloAgregado!=true){
				htmlAnalisis += "<td >"+analisis.nroHCPaciente+"</td>";
				htmlAnalisis += "<td >"+analisis.nombrePaciente +"</td>";
				htmlAnalisis += "<td >"+analisis.fecha+"</td>";
				htmlAnalisis += "<td >"+analisis.nombrePrestacion+"</td>";
				htmlAnalisis += "<td >"+analisis.observaciones+"</td>"
				htmlAnalisis += "<td ><a href='#' class='askDelete' onClick='eliminarFila( \""+c+"\" )'><img src='images/trash.png' border='0'/></a></td></tr>";
			}else{
				htmlAnalisis += "<td ><span class='textoVerde'>"+analisis.nroHCPaciente+"</span></td>";
				htmlAnalisis += "<td ><span class='textoVerde'>"+analisis.nombrePaciente +"</span></td>";
				htmlAnalisis += "<td ><span class='textoVerde'>"+analisis.fecha+"</span></td>";
				htmlAnalisis += "<td ><span class='textoVerde'>"+analisis.nombrePrestacion+"</span></td>";
				htmlAnalisis += "<td ><span class='textoVerde'>"+analisis.observaciones+"</span></td>"
				htmlAnalisis += "<td ><span class='textoVerde'><a href='#' class='askDelete' onClick='eliminarFila( \""+c+"\" )'><img src='images/trash.png' border='0'/></a></span></td></tr>";
				
				
			}
			html += htmlAnalisis;
		}
	
		html += "</tbody>";
		
		$('#listaResultadoBusquedaImportacion').html( html ); 
	}
	
	
	function armarTablaListaRechazados( lista )
	{		
		var html = "<table id='rounded-corner' style='width: 600px;'> <thead><tr>  ";
		html += "<th width='50' class='rounded-company' scope='col'>N&uacute;mero HC</th>";
		html += "<th width='50' class='rounded' scope='col'>Fecha</th>";
		html += "<th width='150' class='rounded' scope='col'>C&oacute;digo de Estudio</th>";
		html += "<th width='100' class='rounded-q4' scope='col'>Resultado</th>";
		html += "</tr>";
		html += "</thead> <tfoot> <tr>";
		html += "<td colspan='3' class='rounded-foot-left'>&nbsp;</td> <td class='rounded-foot-right'>&nbsp;</td>";
		html += "</tr> </tfoot> <tbody>";
		
		for( var c=0 ; c<lista.length ; c++ )
		{
			var estudio = lista[c];
			
			var htmlAnalisis = "<tr><td>"+estudio.nroHC+"</td>";
			htmlAnalisis += "<td>"+estudio.fechaRealizacion+"</td>";
			htmlAnalisis += "<td>"+estudio.idEstudio+"</td>";
			htmlAnalisis += "<td>"+estudio.resultado+"</td></tr>"
			
			html += htmlAnalisis;
		}
		
		html += "</tbody>";
		
		return html;		
	}
	
	
	//CALLBACKS DEL FORMULARIO DE CONSULTA --------------------------------------------------------------------------------
	
	var errorBusquedaImportacion = function errorBusquedaImportacion(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var sucessBusquedaImportacion = function sucessBusquedaImportacion(json)
	{
		var result = json.data[0];
		hideEspera('esperaConsulta');
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
			setTimeout(function(){hideMessage("errorMessageDivID");},5000);
		}
		else
		{
			var lista = eval( result.analisisAprobados[0] );
			var html_lista;
			
			if( lista.length!=0 )
			{
				cantidadAceptados = lista.length;
				
				hideMessage("errorImportacionDiv");
				$('#listaResultadoBusquedaImportacion').html( "" ); 
				actualizarContadorAceptados();
				
				armarTablaListaResultadoBusqueda( result.analisisAprobados[0] );
				
				$('#estudiosAceptados').show();
			}
			else
			{
				$('#estudiosAceptados').hide();
				showMessage( "No existen estudios que importar en el rango de fechas indicado" , "errorImportacionDiv" );
			}
			
			lista = eval( result.analisisRechazados );
			if( lista.length!=0 )
			{
				$('#lineaDivisoria').show();
				
				$('#listaEstudiosSinPrestaciones').html( "" ); 
				$('#tituloRechazadosPrestacion').html( "<h4>Estudios rechazados* ("+lista.length+")</h4>" );
				
				html_lista = armarTablaListaRechazados( lista );
				$('#listaEstudiosSinPrestaciones').html( html_lista ); 
				$('#estudiosSinPrestacion').show();
			}
			else
			{
				$('#estudiosSinPrestacion').hide();
			}
		}
			 
	};
	
	//CALLBACKS DE IMPORTACION --------------------------------------------------------------------------
	
	function doImportar()
	{
		showEspera('esperaImportacion');
		$('#submitImportar').attr('disabled', true);
		$('#importarEstudiosForm').submit();
	}
	
	var errorImportacion = function errorImportacion(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	var sucessImportacion = function sucessImportacion(json)
	{
		hideEspera('esperaImportacion');
		
		var result = json.data[0];
		
		if( result.error==true)
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "errorMessageDivID" );
			setTimeout(function(){hideMessage("errorMessageDivID");},5000);
		}
		else
		{
			//Me quedo y muestro el mensaje de error
			showMessage( result.errorMessage , "exitoMessageDivID" );
			setTimeout(function(){hideMessage("exitoMessageDivID");},10000);		
		}
	}
	
	//FUNCIONES DE ACCIONES EL JSP -----------------------------------------------------------------------
	
	function submitFormConsulta()
	{
		$('#lineaDivisoria').hide();
		$('#estudiosAceptados').hide();
		$('#estudiosSinPrestacion').hide();
		$('#estudiosSinPacientes').hide();
		
		hideMessage("errorMessageDivID");
		hideMessage("exitoMessageDivID");
		hideMessage("errorImportacionDiv");
		
		if( validarInput() )
		{
			showEspera('esperaConsulta');
			$('#previsualizarImportacionForm').submit();
		}

	}

	
	//FUNCIONES PRINCIPALES ---------------------------------------------------------------------------------------------
	
	$(document).ready(function(){
		ajaxForm( 'previsualizarImportacionForm' , sucessBusquedaImportacion , null , errorBusquedaImportacion );
		ajaxForm( 'importarEstudiosForm' , sucessImportacion , null , errorImportacion );
		
		$("#fechaInicioID").datepicker($.datepicker.regional['es']);
		$("#fechaFinalID").datepicker($.datepicker.regional['es']);
	});
	

</script>



	
<div class="center_content">
	<h2>Importar desde laboratorio</h2>
	
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>

	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>

	<div class="form" >
		<form:form action="doPrevisualizarImportacion.htm" id="previsualizarImportacionForm" cssClass="niceform" name="previsualizarImportacionForm" method="post" modelAttribute="filtroConsultaAnalisisLaboratorioDTO" >
		
			<div style="width=500px; align=left;">
			<table style="width=100%;">
				<tr>
					<td width="10%"> <label for="desde">Desde:</label> </td>
					<td width="20%" align="left"> <input type="text" name="fechaInicio" id="fechaInicioID" size="10" /> </td>
					<td width="10%"> <label for="hasta">Hasta:</label> </td>
					<td width="20%" align="left"> <form:input path="fechaFinal" id="fechaFinalID" size="10" /> </td>
					<td width="30%" align="right"> <input type="button" name="submit" id="submit" onclick="submitFormConsulta();" value="Previsualizar" /> </td>
					<td width="30%" align="right"> <div id='esperaConsulta'></div> </td>
				</tr>
				
				
				<tr><td colspan='6'><div id="errorInput" class="divError"></div></td></tr>
				<tr><td>&nbsp;</td></tr>
				
			</table>
			</div>
			<input type="checkbox" name="omitirImportadas" value="true"> Omitir las prestaciones brindadas ya importadas </input>
			
		</form:form>
	</div>
	
	
	<div id="errorImportacionDiv" class="error_box" style="display: none;"></div>

	<div id="estudiosAceptados" style="display: none;">
	
		<div>
			<table>
				<tr><td> <div id='tituloAceptados'></div> </td></tr>
				<tr><td>Estos son los estudios que se importar&aacute;n. Los estudios marcados en <span class='textoVerde'>verde</span> se agregaron como módulos por la presencia de determinaciones asociadas.</td></tr>
			</table>  
		</div>
		
		<form:form action="doImportarLaboratorio.htm" id="importarEstudiosForm" cssClass="niceform" name="importarEstudiosForm" method="post" modelAttribute="resultadosPrevisualizacion" >
			
			<form:hidden path="cantidadPrestacionesAprobadas"/>

			<table>
			<tr >
				<td colspan="2">
					<div id="listaResultadoBusquedaImportacion" style="height:300px; overflow-x: none; overflow-y: auto;">
					</div>
				</td>
			</tr>
			<tr>
				<td width="70%"> 
					<div id='esperaImportacion' align="right"></div>					
				</td>
				<td  width="30%"> 
					<div align="right"> <input type="button" name="submitImportar" id="submitImportar" onclick="doImportar()" value="Importar Estudios" /> </div>
				</td>
			</tr>
			</table>
	
		</form:form>		
	</div>
	
	<div id="lineaDivisoria"  style="display: none;">
	<hr>
	</div>
	
	<div id="estudiosSinPrestacion" style="display: none;" >
	<br>
		<div>
			<table>
				<tr><td> <div id='tituloRechazadosPrestacion'></div> </td></tr>
				<tr><td>Estos estudios no se importar&aacute;n</td></tr>
			</table>  
		</div>
		
		<div id="listaEstudiosSinPrestaciones" style="width: 620px; height:300px; overflow-x: none; overflow-y: auto;"></div>
	
	</div>

	
	
	<div id="estudiosSinPacientes" style="display: none;">
	<br>
		<div>
			<table>
				<tr><td> <div id='tituloRechazadosPaciente'></div> </td></tr>
				<tr><td>Estos estudios no se importar&aacute;n</td></tr>
			</table>  
		</div>
		
		<div id="listaEstudiosSinPacientes"  style="width: 620px; height:300px; overflow-x: none; overflow-y: auto;"></div>
	
	
	</div>
	
	
	<div id="estudiosSinPrecio" style="display: none;">
	<br>
		<div>
			<table>
				<tr><td> <div id='tituloRechazadosPrecio'></div> </td></tr>
				<tr><td>Estos estudios no se importar&aacute;n</td></tr>
			</table>  
		</div>
		
		<div id="listaEstudiosSinPrecio"  style="width: 620px; height:300px; overflow-x: none; overflow-y: auto;"></div>
	
	
	</div>	
         
</div>
         
         
     
	