<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
<link rel="stylesheet" href="estilos/cupertino/datepicker.css">
<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.datepicker-es.js"></script>
<script src="js/jquery/datepicker.js"></script>


<script src="js/jconfirmaction.hemobilling.js" type="text/javascript"></script>


<link rel="stylesheet" type="text/css" href="estilos/jquery-ui-1.8.22.custom.css" />

<script>

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
	
	
	function hideAllMessageError() {			
		
		
		hideMessage("errorMessageDivID");	
		hideMessage("exitoMessageDivID");
		
	};
	
	
	function armarListaOS(listaOS)
	{
		var html = "";
		html += "<table id='rounded-corner' style='width: 800px;'>";
		html+= "<tr><td><input type='checkbox' class='checkall'></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>";
		
		for( var i=0 ; i<listaOS.length ; i++ )
		{

			var hasta;
			if(listaOS[i].hasta!=undefined)
				hasta= listaOS[i].hasta;
			else hasta='PRESENTE';
			html+='<tr><td><input type="checkbox" id="check'+i+'" name="seleccionados" class="asociacionesSeleccionadas" value="'+listaOS[i].id+'" /></td><td>'+listaOS[i].nombreObraSocial+'</td><td id="desde'+i+'">'+listaOS[i].desde+'</td><td id="hasta'+i+'">'+hasta+'</td></tr>';
			
		};
		
		html+="</table>";
		
		
		$('#listaOSResultado').html( html ); 
	}

	
	
	var sucessPreviasualizacion = function sucessPreviasualizacion( json )
	{
		var result = json.data[0];
		
		if( result.error==true)
		{
		
			showMessage( result.errorMessage , "errorMessageDivID" );
			setTimeout(function(){hideMessage("errorMessageDivID");},5000);
		}
		else
		{
			var lista = eval( result.obrasSociales );
			if( lista[0].length!=0 )
			{
				listaOS = eval( lista[0] );
				armarListaOS(listaOS);
				$('#listaOSResultado').show();
				$('#nohayOS').hide();
			}
			else
			{
				$('#nohayOS').show();
				$('#listaOSResultado').hide();
			}	
			
			
			
		}
		
	};
	
	var errorPreviasualizacion = function errorPreviasualizacion(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	
	
	var sucessActualizar = function sucessActualizar( json )
	{
		var result = json.data[0];
		var error = result.error;
		var message = result.errorMessage;

		
		if( result.error==true)
		{
		
			showMessage( result.errorMessage , "errorMessageDivID" );
			setTimeout(function(){hideMessage("errorMessageDivID");},5000);
		}
		else
		{
			showMessage(message, "exitoMessageDivID");
			setTimeout(function() {	hideMessage("exitoMessageDivID");}, 3000);
			
			
		}
		
	};
	
	var errorActualizar = function errorActualizar(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	function menor(f1,f2){
		var d1 = $.datepicker.parseDate('dd/mm/yy', f1);
		var d2 = $.datepicker.parseDate('dd/mm/yy', f2);
		return (d1.getTime()<d2.getTime());
		
	}

	
	function validarFechas(){
		var fechaActualizar=$("#fechaNueva").val();
		var error=false;
		var i=0;
		var hoy = new Date();
		var mes= hoy.getMonth()+1;
		var stringhoy = hoy.getDate()+'/'+mes+'/'+hoy.getFullYear();
		while((i<listaOS.length)&&(!error)){
			if($("#check"+i).attr('checked')){
			var desde=$("#desde"+i).html();
			var hasta=$("#hasta"+i).html();
			error=menor(fechaActualizar,desde);
			if(!error){
				if(hasta!='PRESENTE'){
					error=menor(hasta,fechaActualizar);
				}else{
					error=menor(stringhoy,fechaActualizar);
				}
			};
			};
			i++ ;
		}
		
		return error;
		
		
	}
	
	
	
	
	function verObrasSocialesActuales()
	{
		var error = false;
		hideAllMessageError();
		
		if( $("#listaActual\\.codigo").val()=="" )
		{
			error = true;
			showMessage( "La lista actual es obligatoria." , "errorMessageDivID" );
		}
		
		if(!error && $("#fechaBusqueda").val()=="" )
		{
			error = true;
			showMessage( "Fecha de búsqueda es obligatorio." , "errorMessageDivID" );
		}
		
		
		
		if( error==false )
		{
			$('#listaNuevaPre').val($('#listaNueva\\.codigo').val());
			$('#listaActualPre').val($('#listaActual\\.codigo').val());
			$('#fechaBusquedaPre').val($('#fechaBusqueda').val());
			$('#verObrasSocialesListaActualForm').submit();
		}
		
	}

	var aceptaActualizar = function aceptaActualizar(nroSeleccionado) {
		var error = false;
		hideAllMessageError();
		
		if( $("#listaActual\\.codigo").val()=="" )
		{
			error = true;
			showMessage( "La lista actual es obligatoria." , "errorMessageDivID" );
		}
		
		
		if( !error && $("#listaNueva\\.codigo").val()=="" )
		{
			error = true;
			showMessage( "La lista nueva es obligatoria." , "errorMessageDivID" );
		}
		
		if((!error)&&( $("#listaNueva\\.codigo").val()==$("#listaActual\\.codigo").val() ))
		{
			error = true;
			showMessage( "Las listas deben ser distintas." , "errorMessageDivID" );
		}
		
		if(!error &&  $("#fechaNueva").val()=="" )
		{
			error = true;
			showMessage( "La fecha a actualizar es obligatoria" , "errorMessageDivID" );
		}
		
		if(!error && validarFechas()){
			error=true;
			showMessage( "La fecha a actualizar debe estár comprendida entre las fechas desde y hasta de la todas las obras sociales." , "errorMessageDivID" );
		}
		
		if($("input:checked").length==0){
			alert("Debe seleccionar al menos una obra social.");
			error=true;
		}
		
		
		
		if( error==false )
		{
			$('#actualizacionMasivaForm').submit();
		}
	}
	
$(document).ready(
	function() {
		listasPosiblesSource=${listasPosibles} ;
		autocompletar('listaActual');
		autocompletar('listaNueva');
		$(".dp").datepicker($.datepicker.regional['es']);
		ajaxForm( 'verObrasSocialesListaActualForm' , sucessPreviasualizacion , null , errorPreviasualizacion );
		ajaxForm( 'actualizacionMasivaForm' , sucessActualizar , null , errorActualizar );
		
		$('.askDelete').jConfirmAction(aceptaActualizar, null);
		
		$('.checkall').live(
				'click',
				function() {
					$(this).parents('table:eq(0)')
							.find(':checkbox').attr('checked',
									this.checked);
				});
		
	});

	
	
</script>
 

<div class="center_content">
	<h2>Actualización masiva de listas de precio</h2>
      
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>

	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>
	
	<div>
         
		<form:form id="actualizacionMasivaForm" name="actualizacionMasivaForm" action="doActualizacionMasivaListaPrecio.htm" method="post" cssClass="niceform" modelAttribute="actualizacionMastivaDTO" commandName="actualizacionMastivaDTO">
         
         <fieldset>
         <dl>
				<dt>
					<label for="listaPrecioActual">Lista de precio actual:</label>
				</dt>
				<dd>
					<table>
						<tr>
						<td><form:input readonly="true" path="listaActual.codigo" size="5"
									id="listaActual.codigo" /> </td>
							<td><form:input path="listaActual.nombre" size="50"
									id="listaActual.descripcion" /></td>
						</tr>
						<tr >
						<td>&nbsp;</td>
							<td ><div id="listaActualErrorID" class="divError"></div></td>
						</tr>
					</table>
				</dd>
			</dl>

				<dl>
					<dt>
						<label for="listaPrecioActual">Lista de precio nueva:</label>
					</dt>
					<dd>
						<table>
							<tr>
								<td><form:input readonly="true" path="listaNueva.codigo"
										size="5" id="listaNueva.codigo" /></td>
								<td><form:input path="listaNueva.nombre" size="50"
										id="listaNueva.descripcion" /></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><div id="listaNuevaErrorID" class="divError"></div></td>
							</tr>
						</table>
					</dd>
				</dl>
				
				<dl>
					<dt>
						<label for="fechaBusqueda">Fecha a buscar:</label>
					</dt>
					<dd>
						<table>
							<tr>
								<td>
								<form:input id="fechaBusqueda" path="fechaBusqueda" readonly="true" cssClass="dp" />
								</td>
								
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><div id="fechaBusquedaID" class="divError"></div></td>
							</tr>
						</table>
					</dd>
				</dl>
				
				<dl>
					<dt>
						<label for="fechaNueva">Fecha a actualizar:</label>
					</dt>
					<dd>
						<table>
							<tr>
								<td>
								<form:input id="fechaNueva" path="fechaNueva" readonly="true" cssClass="dp" />
								</td>
								
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><div id="fechaNuevaID" class="divError"></div></td>
							</tr>
						</table>
					</dd>
				</dl>
				
				
				

				<dl class="submit">
					<dt>
						<input type="button" name="submit" id="submit"
							onclick="verObrasSocialesActuales();" value="Ver OS actuales" />
					</dt>
					<dt>
						<input type="button" name="submit" id="submit" class="askDelete"
							 value="Actualizar lista" />
					</dt>

				</dl>
			

			</fieldset>
			
			<div id="listaOSResultado">
					</div>
					
<div id="nohayOS"> No existen Obras Sociales asociadas a la lista de precio actual.
					</div>
			
         </form:form>
	</div>
	
	
	<form:form method="post" name="verObrasSocialesListaActualForm" id="verObrasSocialesListaActualForm" action="doVerObrasSocialesListaActual.htm" commandName="previsualizarCambioListaDTO">
		<form:hidden path="listaNueva.codigo" id="listaNuevaPre" />
		<form:hidden path="listaActual.codigo" id="listaActualPre" />
		<form:hidden path="fechaBusqueda" id="fechaBusquedaPre" />
	</form:form>
	
	
         

         

         
         
</div>