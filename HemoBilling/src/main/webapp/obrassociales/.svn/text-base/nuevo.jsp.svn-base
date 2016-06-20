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


<script type="text/javascript">

function volverObrasSociales() {
	location.href = "volverObrasSociales.htm";
}

<!-- AUTOCOMPLETAR -->

function mostrarasociacionListaPrecio() {
	$('#asociacionListaPrecio').jqmShow();
}

function ocultarasociacionListaPrecio() {
	$('#asociacionListaPrecio').jqmHide();
}
function autocompletar(divid){

	$( "#"+divid+"\\.descripcion" ).autocomplete({
		minLength: 0,
		source: listasPosiblesSource,
		select: function( event, ui ) {
			$( "#"+divid+"\\.descripcion" ).val( ui.item.desc );
			$( "#"+divid+"\\.codigo" ).val( ui.item.value );
			

			return false;
		},
	
	change:function( event, ui ) {
        var data=$.data(this);//Get plugin data for 'this'
        if(data.autocomplete.selectedItem==undefined){
        	$( "#"+divid+"\\.codigo" ).val( '' );
        	$( "#"+divid+"\\.descripcion" ).val( '' );
        };      
    }

	
		
	})
	.focus(function(){     
        
        $(this).data("autocomplete").search($(this).val());
    })
	.data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
			.data( "item.autocomplete", item )
			.append( "<a>" + item.label +"</a>" )
			.appendTo( ul );
	};


	};

	
	function unstyleInput(id){
	    
	    var elem =document.getElementById(id);
	    var oldElem=elem;
	    inputText(elem);
	    
	    oldElem.parentNode.replaceChild(elem,oldElem);
	    }
	    


	/* ------ Validacion ------ */
	
	function hideAllMessageError() {
		hideMessage("codigoErrorID");
		hideMessage("nombreErrorID");
		hideMessage("codigoRNOSErrorID");
		hideMessage("siglaErrorID");
		hideMessage("prestadorErrorID");
		hideMessage("cuitErrorID");
		hideMessage("telefonoErrorID");
		hideMessage("telefonoGratuitoErrorID");
		hideMessage("emailErrorID");
		hideMessage("websiteErrorID");
		
		hideMessage("listaPrecioActualErrorID");
		

		hideMessage("errorMessageDivID");
		hideMessage("exitoMessageDivID");
	}

	function validaryComitearForm() {
		var error = false;
		hideAllMessageError();

		
		if (($('#codigoID').val() != "")&&(!isNumber($('#codigoID').val()))) {
			error = true;
			showMessage("El codigo debe ser un n&uacutemero.", "codigoErrorID");
		}

		if ($('#nombreID').val() == "") {
			error = true;
			showMessage("El nombre es obligatorio.", "nombreErrorID");
		}
		
		if ($("#listaPrecio\\.descripcion").val() == "") {
			error = true;
			showMessage("La lista de precios es obligatoria.", "listaPrecioActualErrorID");
		}
		
		
		
		


		if (!error) {
			$('#nuevoForm').submit();
		}
	}

	/*-------- CALLBACKS --------*/

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
			$('#volverObrasSocialesForm').submit();
		}

	};

	
	
	function eliminarPlan(indice) {
		
		var fin=index-1;
		var i;
		for(i=indice;i<fin;i++){
			var siguiente=i+1;
			reemplazarInputs('codigo',i,siguiente);
			reemplazarInputs('nombre',i,siguiente);
			reemplazarInputs('eliminar',i,siguiente);
			
		};
		index--;
		eliminarInput(index,'codigo');
		eliminarInput(index,'nombre');
		eliminarInput(index,'eliminar');
		
		

		
		
		
	}
	
	function eliminarInput(index,atributo){
		var elem =document.getElementById('planesAuto' + index + '.'+atributo);
		var parentN = elem.parentNode;
		while(parentN.hasChildNodes())
			parentN.removeChild(parentN.firstChild);
		
	}
	
	function reemplazarInputs(atributo,index,siguiente){
		var elem =document.getElementById('planesAuto' + index + '.'+atributo);
		var elemSiguiente =document.getElementById('planesAuto' + siguiente + '.'+atributo);
		elem.value=elemSiguiente.value;
			
	}

	

	$(document).ready(
			function() {
				
				
				listasPosiblesSource=${listasPosibles} ;
				autocompletar('listaPrecio');
				
				
				
				$('#asociacionListaPrecio').jqm({modal: true});
				
			     index = 0;
				
		        $("#add").click(function() {
		            $("#rounded-corner-small tr:last").before(function() {
		            	
		                var html = '<tr>';
		                html+='<td ><input type="text" id="planesAuto' + index + '.codigo" name="planesAuto[' + index + '].codigo" size="20" /></td>';
		                html += '<td ><input type="text" id="planesAuto' + index + '.nombre" name="planesAuto[' + index + '].nombre" size="50" /></td>';
		                html += '<td  halign="center" valign="middle"><a id="planesAuto' + index + '.eliminar" onclick="eliminarPlan(' + index + ')"><img src="images/trash.png" alt="" title="" border="0" /></a></td>';
		                
		                
		                html+='</tr>';
		                return html;
		            });
		            
		            index++;
		            return false;
		        });
				
			

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

	<h2>Datos de la nueva Obra Social</h2>

	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


	<form:form cssClass="niceform" method="post" name="nuevoForm"
		id="nuevoForm" action="doNuevaObraSocial.htm"
		modelAttribute="nuevaobrasocialDTO">

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
					<label for="gerenciadora">Gerenciadora:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:textarea path="gerenciadora" cols="50" rows="6"
									id="gerenciadoraID" /></td>
						</tr>
						<tr>
							<td><div id="gerenciadoraErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt>
					<label for="codigoRNOS">C&oacute;odigo RNOS:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="codigoRNOS" size="50"
									id="codigoRNOSID" /></td>
						</tr>
						<tr>
							<td><div id="codigoRNOSErrorID" class="divError"></div></td>
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
							<td><form:input path="codigoContable" size="50"
									id="codigoContableID" /></td>
						</tr>
						<tr>
							<td><div id="codigoContableErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>


			<dl>
				<dt>
					<label for="sigla">Sigla:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:input path="sigla" size="50" id="siglaID" /></td>
						</tr>
						<tr>
							<td><div id="siglaErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			<dl>
				<dt>
					<label for="prestador">Prestador:</label>
				</dt>
				<dd>
					<table>
						<tr>
							<td><form:textarea path="prestador" cols="50" rows="6" id="prestadorID" /></td>
						</tr>
						<tr>
							<td><div id="prestadorErrorID" class="divError"></div></td>
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
							<td><form:input path="cuit" size="20" id="cuitID" /></td>
						</tr>
						<tr>
							<td><div id="cuitErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>

			<dl>
				<dt>
					<label for="tipoIVA">Tipo de IVA:</label>
				</dt>

				<dd>


					<form:select id="tipoIVAid" path="tipoIVA.id">
						<form:option value="-1" label="Seleccionar uno" />
						<form:options items="${tiposivaDTO}" itemLabel="descripcion"
							itemValue="id" />
					</form:select>


				</dd>
			</dl>
			
						<dl>
				<dt>
					<label for="tipoFactura">Tipo de Facturaci&oacute;n:</label>
				</dt>

				<dd>

				<form:select id="tipoFactura" path="tipoFactura">
					<form:option value="" label="" />
					<form:options  items="${tiposFacturacionDTO}" itemLabel="descripcion" itemValue="claveTipoFacturacion" />
				</form:select>

				</dd>
			</dl>
			
			<dl>
				<dt>
					<label for="contabilizaFactura">Contabiliza facturas:</label>
				</dt>

				<dd>

					<form:checkbox path="contabilizaFactura" />

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
							<td><form:textarea path="direccion" cols="50" rows="2" id="calleID" /></td>
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
							<td><form:input path="localidad" size="50" id="localidadID" /></td>
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
							<td><form:input path="codigoPostal" size="50"
									id="codigoPostalID" /></td>
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
							<td><form:input path="pais" size="50" id="paisID" /></td>
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
							<td><form:input path="telefono" size="50" id="telefonoID" /></td>
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
							<td><form:input path="telefonoGratuito" size="50"
									id="telefonoGratuitoID" /></td>
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
							<td><form:input path="email" size="50" id="emailID" /></td>
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
							<td><form:input path="website" size="50" id="websiteID" /></td>
						</tr>
						<tr>
							<td><div id="websiteErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			



			</fieldset>
			
			<div><h4>Lista de Precio</h4></div>
			
			<fieldset>


			<dl>
				<dt>
					<label for="listaPrecioActual">Lista de precio actual:</label>
				</dt>
				<dd>
					<table>
						<tr>
						<td><form:input readonly="true" path="asociacionActual.listaPrecio.codigo" size="5"
									id="listaPrecio.codigo" /> </td>
							<td><form:input path="asociacionActual.listaPrecio.nombre" size="50"
									id="listaPrecio.descripcion" /></td>
						</tr>
						<tr >
						<td>&nbsp;</td>
							<td ><div id="listaPrecioActualErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>
			
			</fieldset>
			
			<div><h4>Planes</h4></div>
			
			<fieldset>


			<dl>
				<dt></dt>
				<dd>
					<table id="rounded-corner-small" summary="Planes">
						<thead>

							<tr>

								<th width="350" class="rounded-company" scope="col">Código</th>
								<th width="600" class="rounded" scope="col">Nombre</th>

								<th width="60" class="rounded-q4" scope="col">Eliminar</th>
							</tr>
						</thead>

						<tbody>



						</tbody>
						<tfoot>
							<tr>
								<td colspan="2" class="rounded-foot-left"><em><a
										href="" id="add">Agregar</a></em></td>
								<td class="rounded-foot-right">&nbsp;</td>
							</tr>
						</tfoot>
					</table>
				</dd>
			</dl>

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
						onclick="volverObrasSociales()" />
				</dt>
			</dl>




		</fieldset>
		
		




	</form:form>



	<form:form action="volverObrasSociales.htm" method="post"
		id="volverObrasSocialesForm" modelAttribute="resultOperacionDTO">
		<form:hidden path="error" id="errorResultID" />
		<form:hidden path="errorMessage" id="errorMessageResultID" />
	</form:form>







</div>

