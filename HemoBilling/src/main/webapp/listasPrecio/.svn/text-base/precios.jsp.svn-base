
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

<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.button.js"></script>
<script src="js/jquery/ui/jquery.ui.position.js"></script>
<script src="js/jquery/ui/jquery.ui.autocomplete.js"></script>
<script type="text/javascript">

var totalPrestaciones = ${fn:length(listapreciodto.itemsAuto)};

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

function volverListaPrecios()
{
	 location.href = "volverListasPrecio.htm"; 
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



function validaryComitearForm() 
{
	armarListaPrestacionesSeleccionadas();
		
	$('#preciosForm').submit();
}

function armarListaPrestacionesSeleccionadas()
{
	var pos;
	var html = "";
	
	for( pos=0; pos<totalPrestaciones ; pos++ )
	{
		var seleccionado = $('#incluidoItem'+pos).is(':checked');
		
		html+='<input type="hidden" id="itemsAuto' + pos + '.id" name="itemsAuto[' + pos + '].id" value="'+ $('#idItem'+pos).val() +'"/><br>';
		html+='<input type="hidden" id="itemsAuto' + pos + '.incluido" name="itemsAuto[' + pos + '].incluido" value="'+ seleccionado +'"/><br>';
		html+='<input type="hidden" id="itemsAuto' + pos + '.prestacion.codigo" name="itemsAuto[' + pos + '].prestacion.codigo" value="'+ $('#codigoPrestacion'+pos).val() +'"/><br>';
		html+='<input type="hidden" id="itemsAuto' + pos + '.prestacion.descripcion" name="itemsAuto[' + pos + '].prestacion.descripcion" value="'+ $('#nombrePrestacion'+pos).val() +'"/><br>';
		html+='<input type="hidden" id="itemsAuto' + pos + '.precio" name="itemsAuto[' + pos + '].precio" value="'+ $('#precioItem'+pos).val() +'"/><br>';
		html+='<input type="hidden" id="itemsAuto' + pos + '.codigo" name="itemsAuto[' + pos + '].codigo" value="'+ $('#codigoFacturacionItem'+pos).val() +'"/><br><br>';		
	}
	
	$('#prestacionesSeleccionadas').html( html );
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
		$('#volverListasPrecioForm').submit();
	}

};






/* Funcion inicial ---------------------------------------------- */

$(document).ready(
		function() {	
			ajaxForm('preciosForm', preciosAgregados, null,	errorAgregando);
		});


 </script>

<div class="center_content">

<h2>Editar precios de la lista: ${listapreciodto.nombre}</h2> <br>


<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


<form action="doActualizarPrecios.htm" id="preciosForm" method="post">
				
	<table style="width: 400px;">
		<tr>
			<td style="width: 200px;" align="left">
			
				<img id="leftSubmit" src="img/0.png" class="submitNiceFormLeft"/><input class="submitNiceForm" onmouseout="cambiarBordesCommit(0)" onmouseover="cambiarBordesCommit(1)" type="button" name="submit" id="submit" value="Cambiar precios" onclick="validaryComitearForm()" /><img src="img/0.png" class="submitNiceFormRight" id="rightSubmit" />
				
			</td>
			<td style="width: 200px;" align="left">
				
				<img id="leftSubmit2" src="img/0.png" class="submitNiceFormLeft"/><input class="submitNiceForm" onmouseout="cambiarBordesVolver(0)" onmouseover="cambiarBordesVolver(1)" type="button" name="submit" id="submit" value="Volver" onclick="volverListaPrecios()"/><img src="img/0.png" class="submitNiceFormRight" id="rightSubmit2" />
				
			</td>
		</tr>
		<tr><td></td></tr>
	</table>

	<table id="rounded-corner" summary="Precio" style="width: 800px;">
	<thead>
		<tr>
			<th style="width: 40px;" class="rounded-company" scope="col">Todas <input type="checkbox" id="seleccionarTodos" class="checkall" onclick="checkAll();"></th>
			<th style="width: 30px;" class="rounded" scope="col">C&oacute;digo</th>
			<th style="width: 600px;" class="rounded" scope="col">Prestaci&oacute;n</th>
			<th style="width: 70px;" class="rounded" scope="col">Precio</th>
			<th style="width: 60px;" class="rounded-q4" scope="col">C&oacute;digo facturaci&oacute;n</th>
		</tr>
	</thead>

	<tbody>
		<c:forEach items="${listapreciodto.itemsAuto}" var="item" varStatus="i">
		<tr>
			<td >
				<input type="checkbox" id="incluidoItem${i.index}" <c:if test='${item.incluido==true}'> checked="checked" </c:if> />
			</td>
			<td >
				${item.prestacion.codigo}
				<input type="hidden" id="codigoPrestacion${i.index}" value="${item.prestacion.codigo}" />
			</td>
			<td>
				${item.prestacion.descripcion}
				<input type="hidden" id="nombrePrestacion${i.index}" value="${item.prestacion.descripcion}" />
			</td>
			<td >
				<input type="text" id="precioItem${i.index}" size="20" value="${item.precio}"/>
			</td>
			<td >
				<input type="text" id="codigoFacturacionItem${i.index}" size="20" value="${item.codigo}"/>
				
				<input type="hidden" id="idItem${i.index}" value="${item.id}" />
			</td>
			
		</tr>
		</c:forEach>

	</tbody>
</table>

<div id="prestacionesSeleccionadas" style="display: none;"></div>

</form>

	<form:form action="volverListasPrecio.htm" method="post" id="volverListasPrecioForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID" />
		<form:hidden path="errorMessage" id="errorMessageResultID" />
	</form:form>

</div>

