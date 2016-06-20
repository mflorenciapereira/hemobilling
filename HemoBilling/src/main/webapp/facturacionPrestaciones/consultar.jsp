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

<script src="js/jconfirmaction.hemobilling.js" type="text/javascript"></script>

<script src="js/ajaxForm.js" type="text/javascript"></script>
<script src="js/hemoUtils.js" type="text/javascript"></script>
<script src="js/inputValidators.js" type="text/javascript"></script>

 <script type="text/javascript">
 

	function eliminarSeleccionados() {
		var seleccionados = $(".facturasSeleccionadas").length;
		if (seleccionados == 0) {
			alert('Debe seleccionar al menos una factura.');
			return;
		}

		var matches = new Array();
		$(".facturasSeleccionadas").each(function() {
		    matches.push(this.value);
		});



		$('#seleccionadosParaEliminar').val(matches);
		$('#eliminarFacturasPrestacionID').submit();

	};

		function imprimirSeleccionados() {
			var seleccionados = $("input:checked").length;
			if (seleccionados == 0) {
				alert('Debe seleccionar al menos una factura.');
				return;
			}
			$('#imprimirSeleccionadosForm').submit();

		};

		function vistaPrevia(nro) {

			$('#idFacturaID').val(nro);
			$('#fondoID').val(true);
			$('#vistaPreviaFormID').submit();

		}

		function anular(nro, anulada) {
			$('#idFacturaAnulacionID').val(nro);
			$('#anulacionID').val(anulada);
			$('#anularFormID').submit();
		}

		function imprimir(nro) {

			$('#idFacturaID').val(nro);
			$('#fondoID').val(false);
			$('#vistaPreviaFormID').submit();

		}

		function imprimirDetalle(nro) {

			$('#idDetalleID').val(nro);
			$('#detalleFormID').submit();

		}

		function imprimirResumen(nro) {

			$('#idResumenID').val(nro);
			$('#resumenFormID').submit();

		}

		function imprimirSintesis(nro) {

			$('#idSintesisID').val(nro);
			$('#sintesisFormID').submit();

		}

		function hideAllMessageError() {
			hideMessage("errorInput");

			hideMessage("errorMessageDivID");
			hideMessage("exitoMessageDivID");
		}

		/* CSV ----------------------------------------------------------------------*/

		function doCSV(id) {
			$('#idFacturaCSV').val(id);
			$('#crearCSVForm').submit();
		}

		/* Detalle de Factura -------------------------------------------------------*/

		var errorDatosVerPantalla = function errorDatosVerPantalla(json) {
			showMessage(
					'<spring:message code="general.errorContactoController"/>',
					"errorMessageDivID");
		};

		var sucessDatosVerPantalla = function sucessDatosVerPantalla(json) {
			var result = json.data[0];

			if (result.error == true) {
				//Me quedo y muestro el mensaje de error
				showMessage(result.errorMessage, "errorMessageDivID");
				setTimeout(function() {
					hideMessage("errorMessageDivID");
				}, 5000);
			} else {
				mostrarDetalle(result);
			}
		}

		function verDatos(idFactura) {
			$('#idDatosPantalla').val(idFactura);
			$('#verDatosPantallaFormID').submit();
		}

		function mostrarDetalle(factura) {
			var html = "<h4>Detalle de la Factura</h4><br><br>";

			html += "N&uacute;mero: <b>" + factura.numero + "</b><br>";
			html += "Fecha: <b>" + factura.fechaEmision + "</b><br>";
			html += "OS: <b>" + factura.obraSocial.nombre + " ( "
					+ factura.obraSocial.codigo + " )</b><br>";

			if (factura.obraSocial.tipoFactura == "D") {
				html += "Paciente: <b>" + factura.paciente.nombreyApellido
						+ "</b><br>";
			}

			html += "<br>Items Facturados: <br><br>";

			var items = eval(factura.items[0]);
			var c;
			var totalFactura = 0;

			for (c = 0; c < items.length; c++) {
				var htmlLinea = "";
				var item = items[c];

				htmlLinea += item.fecha + " - ";

				if (factura.obraSocial.tipoFactura == "T") {
					htmlLinea += item.nombrePaciente + " ";
					htmlLinea += item.numeroHC + " - ";
				}

				htmlLinea += item.cantidad + " - ";
				var itemCantidad = item.cantidad;

				htmlLinea += item.descripcionPrestacion + " - ";
				htmlLinea += "$ " + item.precio + " <br> ";

				var importe = item.precio;
				importe = importe.replace(".", "");
				importe = importe.replace(",", ".");

				if (itemCantidad != "")
					totalFactura += parseInt(itemCantidad)
							* (parseFloat(importe));
				else
					totalFactura += parseFloat(importe);

				html += htmlLinea;
			}

			if (factura.anulada == true) {
				html += "<br><br>Total de Factura: <b> ANULADA </b>";
			} else {
				html += "<br><br>Total de Factura: <b>$ " + totalFactura
						+ "</b>";
			}

			$('#middleDatosFacturaDiv').html(html);
			$('#datosFactura').jqmShow();
		}

		function aceptarDetalle() {
			$('#datosFactura').jqmHide();
		}

		/*Manejo de paginado---------------------------------------------------------*/

		function mostrarEstadoPaginado() {
			totalRegistros = "${filtroPaginadoDTO.cantTotalRegs}";
			regsPorPagina = "${filtroPaginadoDTO.regPorPagina}";
			paginaActual = "${filtroPaginadoDTO.numeroPaginaActual}";
			totalPaginas = "${filtroPaginadoDTO.cantMaxPaginas}";

			//Manejo del control de paginas anteriores
			if (paginaActual == 1) {
				showMessage(
						"<span class='disabled'>&lt;&lt; Primera p&aacute;gina</span>",
						"paginadoPrimeroID");
				showMessage("<span class='disabled'>&lt; Anteriores "
						+ regsPorPagina + " registros</span>",
						"paginadoAnteriorID");
			} else {
				showMessage(
						"<a href='#' onclick='irPrimeraPagina();'>&lt;&lt; Primera p&aacute;gina</a>",
						"paginadoPrimeroID");
				showMessage(
						"<a href='#' onclick='irAnteriorPagina();'>&lt; Anteriores "
								+ regsPorPagina + " registros</a>",
						"paginadoAnteriorID");
			}

			//Manejo del texto del cuadro del medio
			var primerElementoMostrado = (paginaActual - 1) * regsPorPagina + 1;
			var ultimoElementoMostrado;
			if (totalRegistros <= (paginaActual * regsPorPagina)) {
				//Esta pagina muestra el ultimo elemento
				ultimoElementoMostrado = totalRegistros;
			} else {
				//Esta pagina no muestra el ultimo elemento
				ultimoElementoMostrado = paginaActual * regsPorPagina;
			}

			var texto = "<span class='current'>Registros "
					+ primerElementoMostrado + " a " + ultimoElementoMostrado
					+ " de " + totalRegistros + "</a>";

			showMessage(texto, "paginadoActualID");

			//Manejo del control de paginas anteriores
			if (paginaActual == totalPaginas) {
				showMessage("<span class='disabled'>Siguientes "
						+ regsPorPagina + " registros &gt;</span>",
						"paginadoSiguienteID");
				showMessage(
						"<span class='disabled'>&Uacute;ltima p&aacute;gina &gt;&gt;</span>",
						"paginadoUltimoID");
			} else {
				showMessage(
						"<a href='#' onclick='irSiguientePagina();'>Siguientes "
								+ regsPorPagina + " registros &gt;</a>",
						"paginadoSiguienteID");
				showMessage(
						"<a href='#' onclick='irUltimaPagina();'>&Uacute;ltima p&aacute;gina &gt;&gt;</a>",
						"paginadoUltimoID");
			}
		}

		function irPrimeraPagina() {
			$('#numeroPaginaActualID').val("1");
			$('#paginarForm').submit();
		}

		function irAnteriorPagina() {
			paginaActual--;
			$('#numeroPaginaActualID').val(paginaActual);
			$('#paginarForm').submit();
		}

		function irSiguientePagina() {
			paginaActual++;
			$('#numeroPaginaActualID').val(paginaActual);
			$('#paginarForm').submit();
		}

		function irUltimaPagina() {
			$('#numeroPaginaActualID').val(totalPaginas);
			$('#paginarForm').submit();
		}

		/* FUNCIONES DE ELIMINACION ------------------------------------------------------------------------------------ */

		var aceptaEliminar = function aceptaEditar(nroSeleccionado) {
			var div = "#idFactura" + nroSeleccionado;
			var codigo = $(div).val();

			$('#idFactura').val(codigo);
			$('#eliminarForm').submit();
		}

		/* FUNCIONES DE FILTRADO --------------------------------------------------------- */

		function vaciarDatosFiltro() {
			$('#clientesElegidosID').html("");
		}

		function mostrarFiltros() {
			vaciarDatosFiltro();

			var html = "<select id='obraSocialSelect' multiple='multiple' class='fuenteChica' size='7' style='width: 100%;' >"

			hideAllMessageError();

			html += "<option value='T'>Todas</option>";

			<c:forEach items="${osPosiblesDTO}" var="os" varStatus="i">
			html += "<option value='${os.codigo}'>${os.nombre}</option>";
			</c:forEach>

			html += "</select>";

			$('#selectOS').html(html);

			$('#fechaDesdeID').attr('disabled', true);
			$('#fechaHastaID').attr('disabled', true);

			$('#filtroBusqueda').jqmShow();

			$('#fechaDesdeID').attr('disabled', false);
			$('#fechaHastaID').attr('disabled', false);
		}

		function mostrarEstadoFiltro() {
			var mensaje = "Mostrando: N&uacute;mero de Factura: "
					+ "${filtroConsultaDTO.numero}";

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

			showMessage(mensaje, "descripcionFiltroID");
		}

		function validarDatosFiltro() {
			var error = false;
			hideAllMessageError();

			if ($('#fechaDesdeID').val() != ""
					&& !validarFormato($('#fechaDesdeID').val())) {
				error = true;
				showMessage("El formato de la fecha desde es incorrecto",
						"errorInput");
			}

			if ($('#fechaHastaID').val() != ""
					&& !validarFormato($('#fechaHastaID').val())) {
				error = true;
				showMessage("El formato de la fecha hasta es incorrecto",
						"errorInput");
			}

			return !error;
		}

		function cargarDatosFiltro() {
			var htmlAnalisis = "";
			var c = 0;
			var filtrar = true;

			$('#obraSocialSelect :selected')
					.each(
							function() {
								if ($(this).val() == "T")
									filtrar = false;

								htmlAnalisis += "<input type='hidden' id='obrasSociales"
										+ c
										+ ".codigo' name='obrasSociales["
										+ c
										+ "].codigo' value='"
										+ $(this).val() + "'/> ";
								c++;
							});

			if (filtrar)
				$('#obraSocialesElegidasID').html(htmlAnalisis);
			else
				$('#obraSocialesElegidasID').html("");
		}

		function refinarConsulta(confirma) {

			if (confirma == "true") {
				if (validarDatosFiltro()) {
					$('#filtroBusqueda').jqmHide();

					cargarDatosFiltro();

					$('#filtroConsultaForm').submit();
				}
			} else {
				$('#filtroBusqueda').jqmHide();
			}
		}

		function manejarError() {
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
		}

		$(document).ready(
				function() {

					$('#filtroBusqueda').jqm({
						modal : true
					});
					$('#datosFactura').jqm({
						modal : true
					});

					$("#fechaDesdeID").datepicker($.datepicker.regional['es']);
					$("#fechaHastaID").datepicker($.datepicker.regional['es']);

					mostrarEstadoPaginado();
					mostrarEstadoFiltro();

					ajaxForm('verDatosPantallaFormID', sucessDatosVerPantalla,
							null, errorDatosVerPantalla);
					
					ajaxForm('eliminarSeleccionadosForm', sucessDatosVerPantalla,
							null, errorDatosVerPantalla);


					manejarError();

					$('.askDelete').jConfirmAction(aceptaEliminar, null);
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
	
	<table style="width: 100%;">
	<tr>
		<td> <h2>Consulta Facturas de Prestaciones Brindadas</h2>	 </td>

		<td>
			<a href="#" class="bt_green" onclick="imprimirSeleccionados();">
				<span class="bt_green_lft"></span><strong>Imprimir seleccionadas</strong><span class="bt_green_r"></span>
			</a> 			
			<a href="#" class="bt_blue" onclick="mostrarFiltros();">
				<span class="bt_blue_lft"></span><strong>Refinar lista</strong><span class="bt_blue_r"></span>
			</a>
			
			<a href="#" class="bt_red" onclick="eliminarSeleccionados();">
				<span class="bt_red_lft"></span><strong>Eliminar seleccionadas</strong><span class="bt_red_r"></span>
			</a>
			
						
		</td>
	</tr>
		
	</table>
	
	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>
		
	<div id="descripcionFiltroID" class="filter_text"></div>
	<form id="imprimirSeleccionadosForm" action="imprimirFacturasPrestacion.htm" method="post">
	<table id="rounded-corner" summary="Facturas">
		
		<thead>
			<tr>
				<th width="10" class="rounded-company" scope="col" style="text-align: center;">Seleccionar <input type="checkbox" class="checkall"></th>
				<th width="50" class="rounded" scope="col" style="text-align: center;">N&uacute;mero</th>
				<th width="50" class="rounded" scope="col" style="text-align: center;">Fecha</th>
				<th width="200" class="rounded" scope="col" style="text-align: center;">Obra Social</th>
				<th width="50" class="rounded" scope="col" style="text-align: center;">Imprimir detalle</th>
				<th width="50" class="rounded" scope="col" style="text-align: center;">Imprimir resumen</th>
				<th width="50" class="rounded" scope="col" style="text-align: center;">Imprimir s&iacute;ntesis</th>
				<th width="50" class="rounded" scope="col" style="text-align: center;">CSV</th>
				<th width="25" class="rounded" scope="col" style="text-align: center;">Ver datos</th>
				<th width="25" class="rounded" scope="col" style="text-align: center;">Vista previa</th>
				<th width="25" class="rounded" scope="col" style="text-align: center;">Imprimir</th>
				<th width="25" class="rounded" scope="col" style="text-align: center;">Anular</th>
				<th width="25" class="rounded-q4" scope="col" style="text-align: center;">Eliminar</th>
			</tr>
		</thead>
			
			
		<tbody>
			<c:forEach items="${resultConsultaDTO}" var="factura" varStatus="i">
				<tr>
					<td align="center"><input type="checkbox" name="facturasSeleccionadas" class="facturasSeleccionadas" value="${factura.id}" /></td>
					<td align="center">${factura.numero}</td>
					<td align="center" >${factura.fechaEmision}</td>
					<td align="center" >${factura.obraSocial.sigla}</td>
					<td align="center">
										<c:if test="${factura.obraSocial.tipoFactura=='T'}">
											<a href="#" onclick="imprimirDetalle('${factura.id}')">Detalle</a>
										</c:if> 
					</td>
					<td align="center">
										<c:if test="${factura.obraSocial.tipoFactura=='T'}">
											<a href="#" onclick="imprimirResumen('${factura.id}')">Resumen</a>
										</c:if> 	
					</td>
					
					<td align="center">
										
							<a href="#" onclick="imprimirSintesis('${factura.id}')">S&iacute;ntesis</a>
										 	
					</td>
					
					<td align="center"><a href="#" onclick="doCSV('${factura.id}')">CSV</a></td>
					
					<td align="center"><a href="#" onclick="verDatos('${factura.id}')"><img src="images/info.png" alt="" title="" border="0" /></a></td>
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
				<td colspan="12" class="rounded-foot-left"></td>
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
	
	
	<form:form  method="post" name="paginarForm" id="paginarForm" action="refreshConsultaFacturasPrestaciones.htm" commandName="filtroPaginadoDTO">
		<form:hidden path="cantTotalRegs" id="cantTotalRegsID"/>
		<form:hidden path="regPorPagina" id="regPorPaginaID"/>
		<form:hidden path="numeroPaginaActual" id="numeroPaginaActualID"/>
		<form:hidden path="cantMaxPaginas" id="cantMaxPaginasID"/>
	</form:form>	

	
	<div id="filtroBusqueda" style="display:none; width: 800px;" class="jqmWindow">
		
		<form:form id="filtroConsultaForm" action="refreshConsultaFacturasPrestaciones.htm" method="post" cssClass="niceform" modelAttribute="filtroConsultaDTO" commandName="filtroConsultaDTO">
         
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
					<td width="10%"> <label for="obraSocialSelect">Obra Social:</label> </td>
					<td colspan="5" width="90%">

						<div id="selectOS"></div>

					</td>
				</tr>
				
				<tr>
				
				<tr><td colspan='7'><div id="obraSocialesElegidasID"></div></td></tr>
				
			</table>
			</div>
			
         </form:form>
	</div>
	
	
	
	<div id="datosFactura" style="display:none;" class="jqmWindow">
		<form:form  method="post" cssClass="niceform">
		
		<table>
		<tr><td><div id="middleDatosFacturaDiv" style="width: 800px; height:400px; overflow-x: none; overflow-y: auto;"></div></td></tr>
		<tr>
			<td align="right"><input type="button" value="Aceptar" onclick="aceptarDetalle();"/></td>	
		</tr>
		</table>
		
		</form:form>
	</div>
	
	
	<form:form method="post" name="eliminarForm" id="eliminarForm" action="doEliminarFacturaPrestaciones.htm" commandName="facturaPrestacionDTO">
		<form:hidden path="id" id="idFactura" />
	</form:form>
	
	<form:form method="post" name="crearCSVForm" id="crearCSVForm" action="doGenerarCSVFacturaPrestacion.htm" commandName="facturaPrestacionDTO">
		<form:hidden path="id" id="idFacturaCSV" />
	</form:form>
	
	<form:form  method="post" name="vistaPreviaForm" id="vistaPreviaFormID" action="vistaPreviaFacturaPrestacion.htm" commandName="facturaPrestacionDTO">
		<form:hidden path="id" id="idFacturaID"/>
		<form:hidden path="fondo" id="fondoID"/>
		
	</form:form>
	
	<form:form  method="post" name="anularFormID" id="anularFormID" action="anularFacturaPrestacion.htm" commandName="facturaPrestacionDTO">
		<form:hidden path="id" id="idFacturaAnulacionID"/>
		<form:hidden path="anulada" id="anulacionID"/>	
	</form:form>
	
	<form:form  method="post" name="detalleForm" id="detalleFormID" action="detalleFacturaPrestacion.htm" commandName="facturaPrestacionDTO">
		<form:hidden path="id" id="idDetalleID"/>
				
	</form:form>
	
	<form:form  method="post" name="resumenForm" id="resumenFormID" action="resumenFacturaPrestacion.htm" commandName="facturaPrestacionDTO">
		<form:hidden path="id" id="idResumenID"/>
				
	</form:form>
	
		<form:form  method="post" name="sintesisForm" id="sintesisFormID" action="sintesisFacturaPrestacion.htm" commandName="facturaPrestacionDTO">
		<form:hidden path="id" id="idSintesisID"/>
				
	</form:form>
	
	<form:form  method="post" name="verDatosPantallaFormID" id="verDatosPantallaFormID" action="verDatosPantalla.htm" commandName="facturaPrestacionDTO">
	<form:hidden path="id" id="idDatosPantalla"/>			
	</form:form>
	
	
    <form:form  method="post" name="eliminarFacturasPrestacionForm" id="eliminarFacturasPrestacionID" action="eliminarFacturasPrestacion.htm" commandName="facturaPrestacionDTO" >
		<form:hidden path="seleccionados" id="seleccionadosParaEliminar"/>
				
	</form:form>
	

	
	
	

	
</div>

	
	
	