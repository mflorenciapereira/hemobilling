<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" href="estilos/cupertino/datepicker.css">
<script src="js/jquery/ui/jquery.ui.core.js"></script>
<script src="js/jquery/ui/jquery.ui.widget.js"></script>
<script src="js/jquery/ui/jquery.ui.datepicker-es.js"></script>
<script src="js/jquery/datepicker.js"></script>

<script src="js/ajaxForm.js" type="text/javascript"></script>
<script src="js/hemoUtils.js" type="text/javascript"></script>
<script src="js/inputValidators.js" type="text/javascript"></script>

<script>
	
	var nroMaxItems = 1;
	
	function styleInput(id)
	{
		var elem =document.getElementById(id);
		var oldElem=elem;
		inputText(elem);
		elem.init();
		oldElem.parentNode.replaceChild(elem,oldElem);
	}
	   
	function agregarItem()
	{
		nroMaxItems++;
		
		var html = "<tr id='itemFacturado"+nroMaxItems+"'>";
		html +="<td> <input type='text' id='fechaItemFacturado"+nroMaxItems+"' name='fechaItemFacturado"+nroMaxItems+"' size='15'/> </td>";
		html +="<td> <input type='text' id='codigoItemFacturado"+nroMaxItems+"' name='codigoItemFacturado"+nroMaxItems+"' size='15'/> </td>";
		html +="<td> <input type='text' id='cantidadItemFacturado"+nroMaxItems+"' name='cantidadItemFacturado"+nroMaxItems+"' size='10'/> </td>";
		html +="<td> <input type='text' id='descripcionItemFacturado"+nroMaxItems+"' name='descripcionItemFacturado"+nroMaxItems+"' size='50'/> </td>";
		html +="<td> <input type='text' id='precioItemFacturado"+nroMaxItems+"' name='precioItemFacturado"+nroMaxItems+"' size='15'/> </td>";
		html +="<td> <a href='#' onclick='eliminarItem(\"itemFacturado"+nroMaxItems+"\");'> <img src='images/menos.png' width='24' height='24' alt='Eliminar Item'/></a> </td>";
		html +="</tr>";
		
		$('#serviciosFacturadosTable').append( html );
		
		styleInput( 'fechaItemFacturado'+nroMaxItems );
		styleInput( 'codigoItemFacturado'+nroMaxItems );
		styleInput( 'cantidadItemFacturado'+nroMaxItems );
		styleInput( 'descripcionItemFacturado'+nroMaxItems );
		styleInput( 'precioItemFacturado'+nroMaxItems );
		
		$( '#fechaItemFacturado'+nroMaxItems ).datepicker($.datepicker.regional['es']);
	}
	
	function eliminarItem( nombre )
	{	
		$('#'+nombre).remove();
	}

	
	/* FUNCIONES DE VALIDACION ---------------------------------------------------------------------------------- */
	
	function hideAllMessageError()
	{			
		hideMessage("errorItemsFactura");
		
		hideMessage("errorFecha");
		hideMessage("errorNumero");
		hideMessage("errorCliente");
		
		hideMessage("errorMessageDivID");	
		hideMessage("exitoMessageDivID");
	}
	
	function validarItems()
	{
		var nroItemMal = 1;
		var error = false;
		
		while( nroItemMal<=nroMaxItems && !error )
		{
			if( $('#fechaItemFacturado'+nroItemMal).val()!=undefined )
			{				
				if( !error && $('#cantidadItemFacturado'+nroItemMal).val()!="" && !isNumber( $('#cantidadItemFacturado'+nroItemMal).val() ) )
				{
					error = true;
					showMessage( "La cantidad de servicios debe ser un valor num&eacute;rico en el item "+nroItemMal , "errorItemsFactura" );
				}
				
				if( !error && $('#descripcionItemFacturado'+nroItemMal).val()=="" )
				{
					error = true;
					showMessage( "La descripcion del servicio es obligatoria en el item "+nroItemMal , "errorItemsFactura" );
				}
				
				if( !error && $('#precioItemFacturado'+nroItemMal).val()=="" )
				{
					error = true;
					showMessage( "El precio del servicio brindado es obligatorio en el item "+nroItemMal , "errorItemsFactura" );
				}
				
				if( !error && !isNumber( $('#precioItemFacturado'+nroItemMal).val() ) )
				{
					error = true;
					showMessage( "El precio del servicio debe ser un valor num&eacute;rico en el item "+nroItemMal , "errorItemsFactura" );
				}
			}
			
			nroItemMal++;
		}
		
		return error;
	}
	
	function validarDatosFacturas()
	{
		var error = false;
		hideAllMessageError();
		
		if( $("#numeroID").val()=="" )
		{
			error = true;
			showMessage( "El n&uacute;mero de factura es obligatorio" , "errorNumero" );
		}
		
		if( !error && !isNumber( $('#numeroID').val() ) )
		{
			error = true;
			showMessage( "El n&uacute;mero de factura debe ser un valor num&eacute;rico " , "errorNumero" );
		}
		
		if( !error && $("#fechaID").val()=="" )
		{
			error = true;
			showMessage( "La fecha de facturaci&oacute;n es obligatoria" , "errorFecha" );
		}
		
		if( !error && $("#clienteID").val()=="" )
		{
			error = true;
			showMessage( "Debe elegir un cliente" , "errorCliente" );
		}
		
		if( !error )
			error = validarItems();
		
		return !error;		
	}
	
	/* FUNCIONES DE FACTURACION ---------------------------------------------------------------------------------- */
	
	function armarColeccionItems()
	{
		var nroItemReal = 0;
		var nroItemMal;
		var HTML = "";
		
		for( nroItemMal = 1 ; nroItemMal<=nroMaxItems ; nroItemMal++ )
		{
			if( $('#fechaItemFacturado'+nroItemMal).val()!=undefined )
			{
				HTML += "<input type='hidden' id='items" + nroItemReal + ".fecha' name='items[" + nroItemReal + "].fecha' value='"+ $('#fechaItemFacturado'+nroItemMal).val()+"'/> ";
				HTML += "<input type='hidden' id='items" + nroItemReal + ".codigo' name='items[" + nroItemReal + "].codigo' value='"+ $('#codigoItemFacturado'+nroItemMal).val()+"'/> ";
				HTML += "<input type='hidden' id='items" + nroItemReal + ".cantidad' name='items[" + nroItemReal + "].cantidad' value='"+ $('#cantidadItemFacturado'+nroItemMal).val()+"'/> ";
				HTML += "<input type='hidden' id='items" + nroItemReal + ".descripcion' name='items[" + nroItemReal + "].descripcion' value='"+ $('#descripcionItemFacturado'+nroItemMal).val()+"'/> ";
				HTML += "<input type='hidden' id='items" + nroItemReal + ".importeUnitario' name='items[" + nroItemReal + "].importeUnitario' value='"+ $('#precioItemFacturado'+nroItemMal).val()+"'/> ";
				
				nroItemReal++;
			}
		}
		
		$('#itemsFacturasFacturados').html( HTML );
	}
	
	
	
	function doFacturarServicios()
	{
		if( validarDatosFacturas() )
		{
			armarColeccionItems();
			$('#ingresarFacturasServiciosForm').submit();
		}
	}
	
	
	/* CALLBACKS DE FACTURACION ---------------------------------------------------------------------------------- */
	
	var sucessCarga = function sucessCarga( json )
	{
		var result = json.data[0];
		if( result.error==true)
		{
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
	
	var errorCarga = function errorCarga(json)
	{
		showMessage( '<spring:message code="general.errorContactoController"/>' , "errorMessageDivID" );
	};
	
	
	
	/* FUNCION READY ---------------------------------------------------------------------------------- */
	
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
		$( '#fechaID' ).datepicker($.datepicker.regional['es']);
		$( '#fechaItemFacturado'+nroMaxItems ).datepicker($.datepicker.regional['es']);
		
		ajaxForm( 'ingresarFacturasServiciosForm' , sucessCarga , null , errorCarga );
		
		manejarError();
	});




</script>
 

<div class="center_content">
	<h2>Facturar Servicios Brindados</h2>
      
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>

	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>
	
	<form:form action="doFacturarServicio.htm" id="ingresarFacturasServiciosForm" cssClass="niceform" name="ingresarFacturasServiciosForm" method="post" modelAttribute="facturaServicioDTO" >
			
		<table style="width: 800px;">
		<tr>
			<td style="text-align: left;"> 
				N&uacute;mero: 
			</td>
			<td style="text-align: left;"> 
				<form:input path="numero" id="numeroID" size="20" />
			</td>
			<td style="text-align: left;"> 
				Fecha
			</td>
			<td style="text-align: left;"> 
				<form:input path="fecha" id="fechaID" size="20" />
			</td>
		</tr>
	
		<tr>
			<td style="text-align: left;">  
			</td>
			<td style="text-align: left;"> 
				<div id="errorNumero" class="divError"></div>
			</td>
			<td style="text-align: left;"> 
			
			</td>
			<td style="text-align: left;"> 
				<div id="errorFecha" class="divError"></div>
			</td>
		</tr>
		
		<tr>
			<td>  
				Cliente: 
			</td>
			<td colspan="3">  
				<form:select id="clienteID" path="cliente.codigo">
					<form:option value="" label="" />
					<form:options  items="${clientesPosiblesDTO}" itemLabel="nombre" itemValue="codigo" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td>  
			</td>
			<td colspan="3">  
				<div id="errorCliente" class="divError"></div>
			</td>
		</tr>
		</table>
		
		<br> <br> <h4>Servicios Facturados</h4> <br><br><div id="errorItemsFactura" class="divError"></div>  
		
		<table id="serviciosFacturadosTable" style="width: 800px;"> 
		<tr >
			<td style="text-align: center;" style="width: 10%;"> 
				Fecha				
			</td>
			<td style="text-align: center;" style="width: 10%;"> 
				C&oacute;digo
			</td >
			<td style="text-align: center;" style="width: 5%;"> 
				Cantidad
			</td>
			<td style="text-align: center;" style="width: 40%;"> 
				Descripci&oacute;n
			</td>
			<td style="text-align: center;" style="width: 10%;"> 
				Precio
			</td>
			<td style="text-align: center;" style="width: 5%;"> 
			</td>
		</tr>
		<tr id="itemFacturado1">
			<td> 
				<input type="text" id="fechaItemFacturado1" name="fechaItemFacturado1" size="15" />					
			</td>
			<td > 
				<input type="text" id="codigoItemFacturado1" name="fechaItemFacturado1" size="15"/>
			</td>
			<td > 
				<input type="text" id="cantidadItemFacturado1" name="cantidadItemFacturado1" size="10"/>
			</td>
			<td > 
				<input type="text" id="descripcionItemFacturado1" name="descripcionItemFacturado1" size="50"/>
			</td>
			<td > 
				<input type="text" id="precioItemFacturado1" name="precioItemFacturado1" size="15"/>
			</td>
			<td > 
				<a href="#" onclick="eliminarItem('itemFacturado1')"> <img src="images/menos.png" width="24" height="24" alt="Eliminar Item"/></a>
			</td>
		</tr>
		</table>
		
		<table style="width: 800px;">
		<tr>
			<td width="88%">  				
			</td>
			<td width="12%" align="right"> 
				<a href="#" onclick="agregarItem()"><img src="images/mas.png" width="24" height="24" alt="Agregar Item"/></a>
			</td>
		</tr>
		<tr>		
			<td colspan="2" > 
				<div align="right"> <input type="button" name="submitFacturar" id="submitFacturar" onclick="doFacturarServicios()" value="Facturar Servicios" /> </div>
			</td>
		</tr>
		</table>
		
		
		<div id="itemsFacturasFacturados"></div>
	
	</form:form>
	

</div>
