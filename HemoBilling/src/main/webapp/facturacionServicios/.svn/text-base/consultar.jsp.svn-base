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

<script src="js/jconfirmaction.hemobilling.js" type="text/javascript"></script>

 <script type="text/javascript">
 
	function imprimirSeleccionados(){
 		var seleccionados = $("input:checked").length;
 		if(seleccionados==0) {
 			alert('Debe seleccionar al menos una factura.');
 			return;
 		}
 		$('#imprimirSeleccionadosForm').submit();
 		
 	};
 
	function vistaPrevia(nro){
 		
 		$('#idFacturaID').val(nro);
 		$('#fondoID').val(true);
 		$('#vistaPreviaFormID').submit();
 		
 		
 	}

 	function imprimir(nro){
 		
 		$('#idFacturaID').val(nro);
 		$('#fondoID').val(false);
 		$('#vistaPreviaFormID').submit();
 	}
 	
 	function anular( nro , anulada )
 	{
		$('#idFacturaAnulacionID').val(nro);
 		$('#anulacionID').val(anulada);
 		$('#anularFormID').submit();		
 	}
 	

 
	/* Funciones de armado de listas-------------------------------------------- */
	var listaResultados = new Array();
	
	function armarListaResultados()
	{
		var cantidad = 0;
		
		<c:forEach items="${resultConsultaDTO}" var="factura" varStatus="f">
		
			var resultado = new Array();
			var totalFactura = 0;
			
			resultado[0] = "${factura.numero}";
			resultado[1] = "${factura.fecha}";
			resultado[2] = "${factura.cliente.nombre}";
			
			var listaItems = "";
			<c:forEach items="${factura.items}" var="item" varStatus="i">
				
				var itemNuevo = "${i.index} - ";
				itemNuevo += "${item.fecha} - ";
				
				<c:if test='${item.codigo!=null}'>
					itemNuevo += "${item.codigo} - ";
				</c:if>
			
				itemNuevo += "${item.cantidad} - ";
				var itemCantidad = "${item.cantidad}";
				
				itemNuevo += "${item.descripcion} - ";
				itemNuevo += "$ ${item.importeUnitario}";
				
				listaItems += itemNuevo + "<br>";
				
				//Aca los importes vienen como 4.578,23
				var importe = "${item.importeUnitario}";
				importe = importe.replace(".","");
				importe = importe.replace(",",".");
				
				if( itemCantidad!="" )
					totalFactura += parseInt(itemCantidad)*(parseFloat( importe ));
				else
					totalFactura += parseFloat( importe );
			</c:forEach>
			
			resultado[3] = listaItems;
			
			<c:choose>
			<c:when test='${factura.anulada==false}'>
				resultado[4] = totalFactura;
			</c:when>
			<c:otherwise>
				resultado[4] = "0 - ANULADA";
			</c:otherwise>
			</c:choose>	
		
			
			
			listaResultados[cantidad] = resultado;
			cantidad++;
			
		</c:forEach>
	}
 
	/* Detalle de Factura -------------------------------------------------------*/
	
	function verDatos( posicion )
	{
		var resultado = listaResultados[ posicion ];
		var html = "<h4>Detalle de la Factura</h4><br><br>";
		
		html += "N&uacute;mero: <b>" + resultado[0] + "</b><br>";
		html += "Fecha: <b>" + resultado[1] + "</b><br>";
		html += "Cliente: <b>" + resultado[2] + "</b><br>";
		
		html += "<br>Items Facturados: <br><br>";
		html += resultado[3];
		html += "<br><br>Total de Factura: <b>$ "+resultado[4]+"</b>"
		
		$('#middleDatosFacturaDiv').html( html );
		
		$('#datosFactura').jqmShow();
	}
	
	function aceptarDetalle()
	{
		$('#datosFactura').jqmHide();
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
	
	
	/* Funciones de Validacion ------------------------------------------------------------------------------------ */
	
	function hideAllMessageError() {			
		hideMessage("errorInput");
		
		hideMessage("errorMessageDivID");	
		hideMessage("exitoMessageDivID");
	}
	
	/* Funciones de Filtrado -------------------------------------------------------------------------------------- */
	
	function mostrarEstadoFiltro()
	{
		var mensaje = "Mostrando: N&uacute;mero de Factura: "+ "${filtroConsultaDTO.numero}";
		
		<c:choose>
		<c:when test='${filtroConsultaDTO.fechaDesde==null}'>
			
			mensaje += " | Fecha desde: Todos";
			
		</c:when>
		<c:otherwise>
			
			mensaje += " | Fecha desde: " + "${filtroConsultaDTO.fechaDesde}";

		</c:otherwise>
		</c:choose>	
		
		<c:choose>
		<c:when test='${filtroConsultaDTO.fechaHasta==null}'>
			
			mensaje += " | Fecha hasta: Todos";
			
		</c:when>
		<c:otherwise>
			
			mensaje += " | Fecha hasta: " + "${filtroConsultaDTO.fechaHasta} ";
			
		</c:otherwise>
		</c:choose>	
		  
		showMessage( mensaje , "descripcionFiltroID" );
	}
	
	function mostrarFiltros()
	{
		vaciarDatosFiltro();
		
		var html = "<select id='clientesSelect' multiple='multiple' size='7' style='width: 90%;' >"
			
		hideAllMessageError();
			
		<c:forEach items="${clientesPosiblesDTO}" var="cliente" varStatus="i">
			html += "<option value='${cliente.codigo}'>${cliente.nombre}</option>";
		</c:forEach>
		
		html +="</select>";
		
		$('#selectClientes').html( html );
		
		$('#fechaDesdeID').attr('disabled',true);
		$('#fechaHastaID').attr('disabled',true);

		$('#filtroBusqueda').jqmShow();
		
		$('#fechaDesdeID').attr('disabled',false);
		$('#fechaHastaID').attr('disabled',false);
	}
	
	
	function cargarDatosFiltro()
	{
		var htmlAnalisis = "";
		var c = 0;
		$('#clientesSelect :selected').each(function () {
			htmlAnalisis += "<input type='hidden' id='clientes" + c + ".codigo' name='clientes[" + c + "].codigo' value='"+$(this).val()+"'/> ";
			c++;
          });
		
		$('#clientesElegidosID').html(htmlAnalisis);
	}

	function vaciarDatosFiltro()
	{
		$('#clientesElegidosID').html("");
	}
	
	function refinarConsulta( confirma )
	{
		
		if( confirma=="true") 
		{
			$('#filtroBusqueda').jqmHide();
				
			cargarDatosFiltro();
				
			$( '#filtroConsultaForm' ).submit();
		}
		else
		{
			$('#filtroBusqueda').jqmHide();
		}
	}
	
	
	/* FUNCIONES DE ELIMINACION ------------------------------------------------------------------------------------ */
	
	var aceptaEliminar = function aceptaEditar(nroSeleccionado) {
		var div = "#idFactura" + nroSeleccionado;
		var codigo = $(div).val();

		$('#idFactura').val(codigo);
		$('#eliminarForm').submit();
	}

	
	
	/* Funciones principales -------------------------------------------------------------------------------------- */
 
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
 
	$(document).ready(function(){
		
		$('#filtroBusqueda').jqm({modal: true});
		$('#datosFactura').jqm({modal: true});
		
		$("#fechaDesdeID").datepicker($.datepicker.regional['es']);
		$("#fechaHastaID").datepicker($.datepicker.regional['es']);
		
		mostrarEstadoPaginado();
		mostrarEstadoFiltro();
		manejarError();
		
		armarListaResultados();
		
		$('.askDelete').jConfirmAction(aceptaEliminar, null);
		$('.checkall').live('click',function () {
		    $(this).parents('table:eq(0)').find(':checkbox').attr('checked', this.checked);
		});
		
	});
 
 
 
 </script>


<div class="center_content">
	
	<table style="width: 100%;">
	<tr>
		<td> <h2>Consulta Facturas de Servicios Brindados</h2>	 </td>

		<td> 			
			<a href="#" class="bt_green" onclick="imprimirSeleccionados();">
				<span class="bt_green_lft"></span><strong>Imprimir las facturas seleccionadas</strong><span class="bt_green_r"></span>
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
	<form id="imprimirSeleccionadosForm" action="imprimirFacturasServicios.htm" method="post">
	<table id="rounded-corner" summary="Facturas">
		
		<thead>
			<tr>
				<th width="10" class="rounded-company" scope="col" style="text-align: center;">Seleccionar <input type="checkbox" class="checkall"></th>
				<th width="200" class="rounded" scope="col" style="text-align: center;">N&uacute;mero</th>
				<th width="100" class="rounded" scope="col" style="text-align: center;">Fecha</th>
				<th width="200" class="rounded" scope="col" style="text-align: center;">Cliente</th>
				<th width="30" class="rounded" scope="col" style="text-align: center;">Ver datos</th>
				<th width="25" class="rounded" scope="col" style="text-align: center;">Vista previa</th>
				<th width="25" class="rounded" scope="col" style="text-align: center;">Imprimir</th>
				<th width="25" class="rounded" scope="col" style="text-align: center;">Anular</th>
				<th width="30" class="rounded-q4" scope="col" style="text-align: center;">Eliminar</th>
			</tr>
		</thead>
			
			
		<tbody>
			<c:forEach items="${resultConsultaDTO}" var="factura" varStatus="i">
				<tr>
				
					<td align="center"><input type="checkbox" name="facturasSeleccionadas" class="facturasSeleccionadas" value="${factura.id}" /></td>
					<td align="center">${factura.numero}</td>
					<td align="center" >${factura.fecha}</td>
					<td align="center" >${factura.cliente.nombre}</td>
							
					<td align="center"><a href="#" onclick="verDatos('${i.index}')"><img src="images/info.png" alt="" title="" border="0" /></a></td>
					<td align="center"><a href="#" onclick="vistaPrevia('${factura.id}')"><img src="images/pdf.gif" alt="" title="" border="0" /></a></td>
					
					<td align="center">
						<c:if test='${factura.anulada==false}'>
						<a href="#" onclick="imprimir('${factura.id}')"><img src="images/printer.gif" alt="" title="" border="0" /></a>
						</c:if>
					</td>
					
					<td align="center">
					
						<c:choose>
							<c:when test='${factura.anulada==false}'>
								<a href="#" onclick="anular('${factura.id}' , true)">Anular</a>	
							</c:when>
							<c:otherwise>
								<a href="#" onclick="anular('${factura.id}' , false)">Desanular</a>
							</c:otherwise>
						</c:choose>	
					</td>
					
					<td align="center">
						<a href="#" class="askDelete" id="${i.count}"> 
							<img src="images/trash.png" alt="" title="" border="0" />
							<input type="hidden" id="idFactura${i.count}" value="${factura.id}" />
						</a>
					</td>
				</tr>			
			</c:forEach>
		</tbody>
		
		<tfoot>
			<tr>
				<td colspan="8" class="rounded-foot-left"></td>
				<td class="rounded-foot-right">&nbsp;</td>
			</tr>
		</tfoot>
		
	</table>
	</form>
	
	
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
	
	
	<form:form  method="post" name="paginarForm" id="paginarForm" action="refreshConsultaFacturasServicios.htm" commandName="filtroPaginadoDTO">
		<form:hidden path="cantTotalRegs" id="cantTotalRegsID"/>
		<form:hidden path="regPorPagina" id="regPorPaginaID"/>
		<form:hidden path="numeroPaginaActual" id="numeroPaginaActualID"/>
		<form:hidden path="cantMaxPaginas" id="cantMaxPaginasID"/>
	</form:form>


	
	<div id="filtroBusqueda" style="display:none; width: 800px;" class="jqmWindow">
		
		<form:form id="filtroConsultaForm" action="refreshConsultaFacturasServicios.htm" method="post" cssClass="niceform" modelAttribute="filtroConsultaDTO" commandName="filtroConsultaDTO">
         
			<div style="width=500px; align=left;">
			<table style="width=100%;" >
				<tr>
					<td width="10%"> <label for=numero>Numero:</label> </td>
					<td width="15%" align="left" colspan="5"> <form:input path="numero" id="numeroID" size="30" tabindex="4" /> </td>
				</tr>
				<tr>
					<td width="10%"> <label for="desde">Desde:</label> </td>
					<td width="15%" align="left"> <form:input path="fechaDesde" id="fechaDesdeID" size="10" tabindex="3" /> </td>
					<td width="10%"> <label for="hasta">Hasta:</label> </td>
					<td width="15%" align="left"> <form:input path="fechaHasta" id="fechaHastaID" size="10" tabindex="2" /> </td>
					<td width="25%" align="right"> <input type="button" name="aceptarBtn" id="aceptarBtn" onclick="refinarConsulta('true');" value="Filtrar" tabindex="0" /> </td>
					<td width="25%" align="left"> <input type="button" name="cancelarBtn" id="cancelarBtn" onclick="refinarConsulta('false');" value="Cancelar" tabindex="1" /> </td>
				</tr>
				<tr><td colspan='6'><div id="errorInput" class="divError"></div></td></tr>
				<tr>
					<td width="10%"> <label for="cliente">Cliente:</label> </td>
					<td colspan="5" width="90%">
						<div id="selectClientes"></div>
					</td>
				</tr>
				
				<tr>
				
				<tr><td colspan='6'><div id="clientesElegidosID"></div></td></tr>
				
			</table>
			</div>
			
         </form:form>
	</div>
	
	<form:form method="post" name="eliminarForm" id="eliminarForm" action="doEliminarFacturaServicios.htm" commandName="facturaServicioDTO">
		<form:hidden path="id" id="idFactura" />
	</form:form>
	
	<div id="datosFactura" style="display:none;" class="jqmWindow">
		<form:form  method="post" cssClass="niceform">
		
		<table>
		<tr><td><div id="middleDatosFacturaDiv" style="width: 600px; height:300px; overflow-x: none; overflow-y: auto;"></div></td></tr>
		<tr>
			<td align="right"><input type="button" value="Aceptar" onclick="aceptarDetalle();"/></td>	
		</tr>
		</table>
		
		</form:form>
	</div>
	
		<form:form  method="post" name="vistaPreviaForm" id="vistaPreviaFormID" action="vistaPreviaFacturaServicio.htm" commandName="facturaServicioDTO">
		<form:hidden path="id" id="idFacturaID"/>
		<form:hidden path="fondo" id="fondoID"/>
		
	</form:form>
	
	<form:form  method="post" name="anularFormID" id="anularFormID" action="anularFacturaServicio.htm" commandName="facturaServicioDTO">
		<form:hidden path="id" id="idFacturaAnulacionID"/>
		<form:hidden path="anulada" id="anulacionID"/>
	</form:form>
	
	
</div>
	
	
	
	