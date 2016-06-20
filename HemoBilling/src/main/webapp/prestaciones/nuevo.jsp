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

function isNumber(n) {
	  if((parseFloat(n) == parseInt(n)) && !isNaN(n)){
	      return true;
	  } else { 
	      return false;
	  } 
}


<!-- AUTOCOMPLETAR -->

function autocompletar(divid){

$( "#"+divid+"\\.descripcion" ).autocomplete({
	minLength: 0,
	source: prestacionesPosiblesSource,
	select: function( event, ui ) {
		$( "#"+divid+"\\.descripcion" ).val( ui.item.desc );
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






	function eliminarPrestacionAsociada(indice) {
		
		var fin=index-1;
		var i;
		for(i=indice;i<fin;i++){
			var siguiente=i+1;
			reemplazarInputs('codigo',i,siguiente);
			reemplazarInputs('descripcion',i,siguiente);
			reemplazarInputs('eliminar',i,siguiente);
			
		};
		index--;
		eliminarInput(index);

		
		
		
	}
	
	function eliminarInput(index){
		var elem =document.getElementById('prestacionesAsociadasAuto' + index + '.indice');
		var parentN = elem.parentNode;
		while(parentN.hasChildNodes())
			parentN.removeChild(parentN.firstChild);
		
	}
	
	function reemplazarInputs(atributo,index,siguiente){
		var elem =document.getElementById('prestacionesAsociadasAuto' + index + '.'+atributo);
		var elemSiguiente =document.getElementById('prestacionesAsociadasAuto' + siguiente + '.'+atributo);
		elem.value=elemSiguiente.value;
			
	}


	function mostrarPrestacionesAsociadas() {
		$('#prestacionesAsociadas').jqmShow();
	}
	
	function ocultarPrestacionesAsociadas() {
		$('#prestacionesAsociadas').jqmHide();
	}

	function volverPrestaciones() {
		location.href = "volverPrestaciones.htm";
	}

	/* ------ Validacion ------ */

	function hideAllMessageError() {
		hideMessage("codigoErrorID");
		hideMessage("descripcionErrorID");

		hideMessage("errorMessageDivID");
		hideMessage("exitoMessageDivID");
	}

	function validaryComitearForm() {
		var error = false;
		hideAllMessageError();

		if ($('#codigoID').val() == "") {
			error = true;
			showMessage("El codigo es obligatorio.", "codigoErrorID");
		}
		
		if (!isNumber($('#codigoID').val())) {
			error = true;
			showMessage("El codigo debe ser un n&uacutemero.", "codigoErrorID");
		}

		if ($('#descripcionID').val() == "") {
			error = true;
			showMessage("La descripcion es obligatoria.", "descripcionErrorID");
		}
		
		var i=0;
		
		while((i<index)&&(!error)){
			var elemCodigo =document.getElementById('prestacionesAsociadasAuto' + i + '.codigo');
			var elemDesc =document.getElementById('prestacionesAsociadasAuto' + i + '.descripcion');
			if(((elemCodigo.value=="")&&(elemDesc.value!=""))||((elemCodigo.value!="")&&(elemDesc.value==""))) {
				error=true;
				showMessage("Falta el c&oacutedigo o la descripci&oacute;n de alguna prestaci&oacute;n.", "prestacionesAsociadasErrorID");
			}
			
					
			
			i++;
		}
		
		

		if (!error) {
			cambiarNullValues();
			$('#cantidadPrestacionesAsociadasID').val(index);
			$('#nuevaPrestacionForm').submit();
		}
	}

	
	function cambiarNullValues(){
		var i;
		for(i=0;i<index;i++){
			var elemCodigo =document.getElementById('prestacionesAsociadasAuto' + i + '.codigo');
			if(elemCodigo.value==""){
				
				elemCodigo.value=-1;
		}
		}
	}
	/*-------- CALLBACKS --------*/

	var errorAgregandoPrestacion = function errorAgregandoPrestacion(json) {
		showMessage('<spring:message code="general.errorContactoController"/>',
				"errorMessageDivID");
	};

	var prestacionAgregada = function prestacionAgregada(json) {
		var result = json.data[0];

		if (result.error == true) {

			showMessage(result.errorMessage, "errorMessageDivID");
		} else {

			$('#errorResultID').val(result.error);
			$('#errorMessageResultID').val(result.errorMessage);
			$('#volverPrestacionesForm').submit();
		}

	};

	/* Funcion inicial ---------------------------------------------- */

	$(document).ready(
			function() {
				$('#prestacionesAsociadas').jqm({modal: true});
				
				
				
				
				ajaxForm('nuevaPrestacionForm', prestacionAgregada, null,
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



<script type="text/javascript">
    $(function() {
    	
    	prestacionesPosiblesSource=${prestacionesPosibles};
    	
        index = 0;
        
        
        
        $("#add").click(function() {
            $("#prestacionesAsociadas tr:last").before(function() {
            	var count=index+1;
                var html = '<tr>';
                html+='<td valign="middle" id="prestacionesAsociadasAuto' + index + '.indice">'+count+"</td>";
                html+='<td><input type="text" id="prestacionesAsociadasAuto' + index + '.codigo" name="prestacionesAsociadasAuto[' + index + '].codigo" size="20" /></td>';
                html += '<td><input type="text" id="prestacionesAsociadasAuto' + index + '.descripcion" name="prestacionesAsociadasAuto[' + index + '].descripcion" size="50" /></td>';
                html += '<td valign="middle"><a href="#" id="prestacionesAsociadasAuto' + index + '.eliminar" onclick="eliminarPrestacionAsociada(' + index + ')">eliminar</a></td>';
                html+='</tr>';
                return html;
            });
            
            
			styleInput('prestacionesAsociadasAuto' + index + '.codigo');
			styleInput('prestacionesAsociadasAuto' + index + '.descripcion');
			autocompletar('prestacionesAsociadasAuto' + index);
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

	<h2>Datos de la nueva Prestacion</h2>

	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


	<form:form cssClass="niceform" method="post" name="nuevaPrestacionForm"
		id="nuevaPrestacionForm" action="doNuevaPrestacion.htm"
		modelAttribute="nuevaPrestacionDTO">

		<fieldset>
			<dl>
				<dt>
					<label for="codigo">Codigo:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="codigo" size="20" id="codigoID" /></td>
						</tr>
						<tr>
							<td><div id="codigoErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>


			<dl>
				<dt>
					<label for="descripcion">Descripcion:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="descripcion" size="50"
									id="descripcionID" /></td>
						</tr>
						<tr>
							<td><div id="descripcionErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt></dt>
				<dd>
					<table>
						<tr>
							<td><input type="button" name="cargarPrestaciones"
								id="cargarPrestaciones" value="Cargar Prestaciones Asociadas"
								onclick="mostrarPrestacionesAsociadas()" /></td>
						</tr>
						<tr>
							<td><div id="prestacionesAsociadasErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
	<form:hidden path="cantidadPrestacionesAsociadas" id="cantidadPrestacionesAsociadasID" />

			<dl>
				<dt></dt>
			</dl>
			<dl class="submit">
				<dt>
					<input type="button" name="submit" id="submit" value="Cargar"
						onclick="validaryComitearForm()" />
				</dt>
				<dt>
					<input type="button" name="volver" id="volver" value="Volver"
						onclick="volverPrestaciones()" />
				</dt>
			</dl>

		</fieldset>

		<div id="prestacionesAsociadas" style="display: none;"
			class="jqmWindow prestcionesAsociadas">
			<h2>Prestaciones asociadas</h2>
			<table id="prestacionesAsociadas">
				<tbody>
				<tr><td></td></tr>

				</tbody>
			</table>
			<input type="button" value="Agregar" name="agregar" id="add" /><br><br>
			<input type="button" onclick="ocultarPrestacionesAsociadas()"
				value="Aceptar" name="submit" id="submit" />
		</div>
	</form:form>



	<form:form action="volverPrestaciones.htm" method="post"
		id="volverPrestacionesForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID" />
		<form:hidden path="errorMessage" id="errorMessageResultID" />
	</form:form>







</div>

