
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
<!-- Fechas -->
function calcularSiguiente(nro){
	var ant=nro-1;
	 var hastaant = $("#hasta"+ant).val();
	 if(hastaant=="") return;
	 var d = $.datepicker.parseDate('dd/mm/yy', hastaant);
	 d.setDate(d.getDate() + 1);
	  
	 return $.datepicker.formatDate('dd/mm/yy', d);
	 
	 
}

function setearDesdeSiguiente(nro){
	var sig=nro+1;
	if(nro==-1) return;
	  var res= calcularSiguiente(sig);
	  
	  $('#desde'+sig).val(res);
	
}

function agregarEventoCalculoFechas(nro){
	$("#hasta"+nro).change( function() {
			setearDesdeSiguiente(nro);
		});
}



<!-- AUTOCOMPLETAR -->


function autocompletar(divid){

	$( "#lista"+divid ).autocomplete({
		minLength: 0,
		source: listasPosiblesSource,
		select: function( event, ui ) {
			$( "#lista"+divid ).val( ui.item.desc );
			$( "#codigo"+divid ).val( ui.item.value );
			

			return false;
		},
	
	change:function( event, ui ) {
        var data=$.data(this);//Get plugin data for 'this'
        if(data.autocomplete.selectedItem==undefined){
        	$( "#codigo"+divid ).val( '' );
        	$( "#lista"+divid).val( '' );
        };      
    }
	})
	.data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
			.data( "item.autocomplete", item )
			.append( "<a>" + item.label +"</a>" )
			.appendTo( ul );
	};


	};


<!-- agregar y eliminar periodos -->

function hacerEditablePrimero(){
	var ultimo=index-1;
	$('#desde0').removeAttr("readonly");
	$("#desde0").datepicker($.datepicker.regional['es']);
	
}

function eliminarPeriodo( nro )
{	
	restarUltimo();
	subirSiguientes(nro);
	var menos=index-2;
	var ant=nro-1;
	$("#periodo"+menos).remove();
	setearDesdeSiguiente(ant);
	index=index-1;
	hacerEditablePrimero();
	
	
};

function agregarFila(nro){
	var sig=nro+1;
	var fila='<tr id="periodo'+sig+'"><td><input id="desde'+sig+'" name="asociacionListasAuto['+sig+'].desde" id="desde${i.index}" readonly="true" type="text"class="dp" /><td><input name="asociacionListasAuto['+sig+'].hasta" id="hasta'+sig+'" type="text" class="dp" /></td><td><input type="hidden" id="codigo'+sig+'" name="asociacionListasAuto['+sig+'].listaPrecio.codigo" value="-1" /><input name="asociacionListasAuto['+sig+'].listaPrecio.nombre" id="lista'+sig+'" type="text" size="55" </td><td><a href="#" onclick="eliminarPeriodo('+sig+')"> <img src="images/menos.png" width="24" height="24" alt="Eliminar" /></a> <a href="#" onclick="agregarPeriodo('+sig+')"><img src="images/mas.png" width="24" height="24" alt="Agregar" /></a></td></tr>';
	$('#periodo'+nro).after(fila);
	$("#hasta"+sig).datepicker($.datepicker.regional['es']);
	autocompletar(sig);
	agregarEventoCalculoFechas(sig);
	
}

function agregarFilaAntes(nro){
	var ant=nro-1;
	var fila='<tr id="periodo'+ant+'"><td><input id="desde'+ant+'" name="asociacionListasAuto['+ant+'].desde" id="desde${i.index}" readonly="true" type="text"class="dp" /><td><input name="asociacionListasAuto['+ant+'].hasta" id="hasta'+ant+'" type="text" class="dp" /></td><td><input type="hidden" id="codigo'+ant+'" name="asociacionListasAuto['+ant+'].listaPrecio.codigo" value="-1" /><input name="asociacionListasAuto['+ant+'].listaPrecio.nombre" id="lista'+ant+'" type="text" size="55" </td><td><a href="#" onclick="eliminarPeriodo('+ant+')"><img src="images/menos.png" width="24" height="24" alt="Eliminar" /></a><a href="#" onclick="agregarPeriodo('+ant+')"><img src="images/mas.png" width="24" height="24" alt="Agregar" ></a></td></tr>';
	$('#periodo'+nro).before(fila);
	
	$("#hasta"+ant).datepicker($.datepicker.regional['es']);
	autocompletar(ant);
	agregarEventoCalculoFechas(ant);
	
}

function inicializarFila(nro){
	
	var ant=nro-1;
	$('#desde'+nro).val('');
	$('#hasta'+nro).val('');
	$('#codigo'+nro).val('');
	$('#lista'+nro).val('');
	setearDesdeSiguiente(ant);
}

function borrarFila(nro){
	inicializarFila(nro);
	var sig=nro+1;
	$('#desde'+sig).val('');
	
}

function sumarUltimo(){
	var nuevo=index;
	var act=index-1;
	$('#desde'+act).attr('name', 'asociacionListasAuto['+nuevo+'].desde');
	$('#hasta'+act).attr('name', 'asociacionListasAuto['+nuevo+'].hasta');
	$('#lista'+act).attr('name', 'asociacionListasAuto['+nuevo+'].listaPrecio.nombre');
	$('#codigo'+act).attr('name', 'asociacionListasAuto['+nuevo+'].listaPrecio.codigo');
	
	$('#periodo'+act).attr('id', 'periodo'+nuevo);
	$('#desde'+act).attr('id', 'desde'+nuevo);
	$('#hasta'+act).attr('id', 'hasta'+nuevo);
	$('#lista'+act).attr('id', 'lista'+nuevo);
	$('#codigo'+act).attr('id', 'codigo'+nuevo);
	
	autocompletar(nuevo);

	
}


function restarUltimo(){
	var nuevo=index-2;
	var act=index-1;
	$('#desde'+act).attr('name', 'asociacionListasAuto['+nuevo+'].desde');
	$('#hasta'+act).attr('name', 'asociacionListasAuto['+nuevo+'].hasta');
	$('#lista'+act).attr('name', 'asociacionListasAuto['+nuevo+'].listaPrecio.nombre');
	$('#codigo'+act).attr('name', 'asociacionListasAuto['+nuevo+'].listaPrecio.codigo');
	
	$('#periodo'+act).attr('id', 'periodo'+nuevo);
	$('#desde'+act).attr('id', 'desde'+nuevo);
	$('#hasta'+act).attr('id', 'hasta'+nuevo);
	$('#lista'+act).attr('id', 'lista'+nuevo);
	$('#codigo'+act).attr('id', 'codigo'+nuevo);
	
	autocompletar(nuevo);

	
}




function agregarPeriodo( nro )
{	
	
	sumarUltimo();
	$("#rounded-corner tr:last").after(function() {
		var ultimo=index-2;
    	agregarFila(ultimo);
    });

	
	bajarSiguientes(nro);
	var sig=nro+1;
	borrarFila(sig);
	agregarEventoCalculoFechas(nro);
	
	index=index+1;
	
	hacerEditablePrimero();
	
	
}

function agregarUltimoPeriodo()
{	
	
	sumarUltimo();
	$("#rounded-corner tr:last").after(function() {
		var ultimo=index;
		agregarFilaAntes(ultimo);
    });

	var anteultimo=index-1;
	inicializarFila(anteultimo);
	index=index+1;
	agregarEventoCalculoFechas(index-2);
	hacerEditablePrimero();
	
	
}

function subirSiguientes(nro){
	var i=nro;
	var menos=index-1;
	while(i<menos){
		var sig=i+1; 
		
		$('#periodo'+i).attr('value', $('#periodo'+sig).attr('value'));
		$('#desde'+i).attr('value', $('#desde'+sig).attr('value'));
		$('#hasta'+i).attr('value', $('#hasta'+sig).attr('value'));
		$('#lista'+i).attr('value', $('#lista'+sig).attr('value'));
		$('#codigo'+i).attr('value', $('#codigo'+sig).attr('value'));
		
		i=i+1;
		
	};
	
	
}


function bajarSiguientes(nro){
	var i=index-2;
	while(i>nro){
		var sig=i+1; 
		
		$('#periodo'+sig).attr('value', $('#periodo'+i).attr('value'));
		$('#desde'+sig).attr('value', $('#desde'+i).attr('value'));
		$('#hasta'+sig).attr('value', $('#hasta'+i).attr('value'));
		$('#lista'+sig).attr('value', $('#lista'+i).attr('value'));
		$('#codigo'+sig).attr('value', $('#codigo'+i).attr('value'));
		
		i=i-1;
		
	};
	
	
}






function cambiarBordesCommit(over){
	var left = document.getElementById("leftSubmit");
	if(over==1)left.className="submitNiceFormLeftOver";
	else left.className="submitNiceFormLeft";
	var right = document.getElementById("rightSubmit");
	if(over==1)right.className="submitNiceFormRightOver";
	else right.className="submitNiceFormRight";
} 

function cambiarBordesVolver(over){
	var left = document.getElementById("leftSubmit2");
	if(over==1)left.className="submitNiceFormLeftOver";
	else left.className="submitNiceFormLeft";
	var right = document.getElementById("rightSubmit2");
	if(over==1)right.className="submitNiceFormRightOver";
	else right.className="submitNiceFormRight";
} 

function volverObrasSociales()
{
	 location.href = "volverObrasSociales.htm"; 
}


function checkAll()
{
	var c;
	var todos = $('#seleccionarTodos').is(':checked');
	
	for( c=0; c<totalPrestaciones ; c++ )
	{
		var id = '#incluidoItem'+c;
		$( id ).attr('checked', todos);
	}
	
}



function menor(f1,f2){
	var d1 = $.datepicker.parseDate('dd/mm/yy', f1);
	var d2 = $.datepicker.parseDate('dd/mm/yy', f2);
	return (d1.getTime()<d2.getTime());
	
}

function hideAllMessageError() {
	
	hideMessage("errorMessageDivID");
	hideMessage("exitoMessageDivID");
}



function menor(f1,f2){
	var d1 = $.datepicker.parseDate('dd/mm/yy', f1);
	var d2 = $.datepicker.parseDate('dd/mm/yy', f2);
	return (d1.getTime()<d2.getTime());
	
};


function validaryComitearForm() 
{
	var error = false;
	hideAllMessageError();
	if (!validarFechas()) {
		error = true;
		showMessage("Falta ingresar alguna fecha, lista de precio o alguna fecha final es posterior a la inicial.", "errorMessageDivID");
	}else{
		$('#cantidadPeriodosID').val(index);
		$('#historiaForm').submit();
	};
};

function validarFechas(){
	var error=false;
	var i=0;
	var menos=index-1;
	while((i<menos)&&(!error)){
		if(($('#desde'+i).val()=='')||($('#hasta'+i).val()=='')||($('#lista'+i).val()=='')) error=true;
		else if(menor($('#hasta'+i).val(),$('#desde'+i).val())) error=true;
		i++;
	};
	
	if(error) return !error;
	
	//ULTIMO
	var hoy = new Date();
	var mes= hoy.getMonth()+1;
	var stringhoy = hoy.getDate()+'/'+mes+'/'+hoy.getFullYear();
	error=menor(stringhoy,$('#desde'+i).val());
	
	return !error;
	
	

}














var errorAgregando= function errorAgregando(json) {
	showMessage('<spring:message code="general.errorContactoController"/>',
			"errorMessageDivID");
};

var preciosAgregados = function preciosAgregados(json) {
	var result = json.data[0];

	if (result.error == true) {

		showMessage(result.errorMessage, "errorMessageDivID");
	} else {

		$('#errorResultID').val(result.error);
		$('#errorMessageResultID').val(result.errorMessage);
		$('#volverObrasSocialesForm').submit();
	}

};






/* Funcion inicial ---------------------------------------------- */

$(document).ready(
		
		function() {	
			listasPosiblesSource=${listasPosibles} ;
			index = ${fn:length(actualizarobrasocialDTO.asociacionListasAuto)};
			$(".dp").datepicker($.datepicker.regional['es']);
			ajaxForm('historiaForm', preciosAgregados, null,	errorAgregando);
			for(var i=0;i<index;i++){
				agregarEventoCalculoFechas(i);
				autocompletar(i);
			}
			
			
		});


 </script>

<div class="center_content">

	<h2>Historia de asignaci&oacute;n de listas de precio de la obra
		social: ${actualizarobrasocialDTO.sigla}</h2>
	<br>


	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


	<form:form action="doHistoriaListaPrecio.htm" id="historiaForm" modelAttribute="actualizarobrasocialDTO"
		method="post">

		<table style="width: 400px;">
			<tr>
				<td style="width: 200px;" align="left"><img id="leftSubmit"
					src="img/0.png" class="submitNiceFormLeft" /><input
					class="submitNiceForm" onmouseout="cambiarBordesCommit(0)"
					onmouseover="cambiarBordesCommit(1)" type="button" name="submit"
					id="submit" value="Aplicar cambios"
					onclick="validaryComitearForm()" /><img src="img/0.png"
					class="submitNiceFormRight" id="rightSubmit" /></td>
				<td style="width: 200px;" align="left"><img id="leftSubmit2"
					src="img/0.png" class="submitNiceFormLeft" /><input
					class="submitNiceForm" onmouseout="cambiarBordesVolver(0)"
					onmouseover="cambiarBordesVolver(1)" type="button" name="submit"
					id="submit" value="Volver" onclick="volverObrasSociales()" /><img
					src="img/0.png" class="submitNiceFormRight" id="rightSubmit2" /></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>

		<table id="rounded-corner" summary="Precio" style="width: 800px;">
			<thead>
				<tr>
					<th style="width: 40px;" class="rounded-company" scope="col">Fecha
						desde</th>
					<th style="width: 30px;" class="rounded" scope="col">Fecha
						hasta</th>
					<th style="width: 500px;" class="rounded" scope="col">Lista de
						precio</th>
					<th style="width: 350px;" class="rounded-q4" scope="col"></th>

				</tr>
			</thead>

			<tbody>
				<c:forEach items="${actualizarobrasocialDTO.asociacionListasAuto}" var="item" varStatus="i">
					<tr id="periodo${i.index}">

						<td><c:choose>
								<c:when test="${i.index == '0'}">
									<form:input  cssClass="dp" id="desde${i.index}" path="asociacionListasAuto[${i.index}].desde"/>
									
								</c:when>

								<c:otherwise>
									<form:input readonly="true" 
										 path="asociacionListasAuto[${i.index}].desde" id="desde${i.index}" />
								</c:otherwise>
							</c:choose></td>

						<td>
						<c:choose>
						<c:when test="${item.hasta!=null}">
						<form:input cssClass="dp" id="hasta${i.index}" path="asociacionListasAuto[${i.index}].hasta" />
						</c:when>
						<c:otherwise>
						<span> PRESENTE</span><input type="hidden" id="hasta${i.index}" value=""/>
						</c:otherwise>
						</c:choose>
						</td>
						
						<td>
						
						<form:hidden id="codigo${i.index}" path="asociacionListasAuto[${i.index}].listaPrecio.codigo"/><form:input size="55"  id="lista${i.index}" path="asociacionListasAuto[${i.index}].listaPrecio.nombre"
							 />
						
						</td>
						
						<td>
						<c:if test="${(i.index != fn:length(actualizarobrasocialDTO.asociacionListasAuto)-1)}">
								
						<a href="#"
							onclick="eliminarPeriodo(${i.index})"> <img
								src="images/menos.png" width="24" height="24"
								alt="Eliminar" /></a>
						</c:if>
						<c:choose>
						<c:when test="${(i.index != fn:length(actualizarobrasocialDTO.asociacionListasAuto)-1)}">
								<a href="#"
							onclick="agregarPeriodo(${i.index})"> <img
								src="images/mas.png" width="24" height="24"
								alt="Agregar" /></a>
						</c:when>
						<c:when test="${(i.index == fn:length(actualizarobrasocialDTO.asociacionListasAuto)-1)}">
								<a href="#"
							onclick="agregarUltimoPeriodo()"> <img
								src="images/mas.png" width="24" height="24"
								alt="Agregar" /></a>
						</c:when>
						</c:choose>
								</td>
						
					</tr>
				</c:forEach>

			</tbody>
		</table>
	<input type="hidden" id="cantidadPeriodosID" name="cantidadPeriodos"/>
	<div id="periodosIncluidos"></div>
	</form:form>

	<form:form action="volverObrasSociales.htm" method="post"
		id="volverObrasSocialesForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID" />
		<form:hidden path="errorMessage" id="errorMessageResultID" />
	</form:form>

</div>

