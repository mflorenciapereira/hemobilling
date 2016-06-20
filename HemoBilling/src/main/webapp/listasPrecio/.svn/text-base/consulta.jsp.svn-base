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


<script type="text/javascript">
	var totalRegistros;
	var regsPorPagina;
	var paginaActual;
	var totalPaginas;
	
	function hideAllMessageError() {			
		hideMessage("nombreErrorID");
		
		hideMessage("errorMessageDivID");
		hideMessage("exitoMessageDivID");
	}
	

	/*------------ Elimina y Editar ------------*/

	function editar(codigo) {
		$('#codigoEditarID').val(codigo);
		$('#editarForm').submit();
	}
	
	function editarPrecios(codigo) {
		$('#codigoVerListaPreciosID').val(codigo);
		$('#verPreciosForm').submit();
	}
	
	
	// DUPLICACION -----------------------------------------------------------
	function duplicarLista(codigo) 
	{
		$('#codigoDuplicarID').val(codigo);
		$('#duplicarListaModal').jqmShow();
	}
	
	
	function doDuplicar(confirma) 
	{
		hideAllMessageError();
		
		if (confirma == "true") 
		{
			if ($('#nombreDuplicarID').val() == "")
				showMessage("Debe ingresar un nombre", "nombreErrorID");
			else
			{
				$('#duplicarListaModal').jqmHide();
				$('#duplicarForm').submit();
			}
		}
		else
			$('#duplicarListaModal').jqmHide();
	}
	
	

	var aceptaEliminar = function aceptaEditar(nroSeleccionado) {
		var div = "#codigo" + nroSeleccionado;
		var codigo = $(div).val();

		$('#codigoEliminarID').val(codigo);
		$('#eliminarForm').submit();
	}

	
	
	/* ------------ Filtros ------------*/
	function mostrarEstadoFiltro() {
		var mensaje = "Mostrando: C&oacute;digo: "
				+ "${filtroDTO.codigo}" + " | Descripcion: "
				+ "${filtroDTO.nombre}" ;

		showMessage(mensaje, "descripcionFiltroID");
	}

	function mostrarFiltros() {
		$('#filtroBusqueda').jqmShow();
	}

	function refinarConsulta(confirma) {
		$('#filtroBusqueda').jqmHide();
		if (confirma == "true") {
			if ($('#codigoID').val() == "")
				$('#codigoID').val("Todos");
			if ($('#descripcionID').val() == "")
				$('#descripcionID').val("Todos");
			

			$('#filtrarForm').submit();

		}
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
					+ regsPorPagina + " registros</span>", "paginadoAnteriorID");
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

		var texto = "<span class='current'>Registros " + primerElementoMostrado
				+ " a " + ultimoElementoMostrado + " de " + totalRegistros
				+ "</a>";

		showMessage(texto, "paginadoActualID");

		//Manejo del control de paginas anteriores
		if (paginaActual == totalPaginas) {
			showMessage("<span class='disabled'>Siguientes " + regsPorPagina
					+ " registros &gt;</span>", "paginadoSiguienteID");
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

	/*------------ on ready ------------*/

	$(document).ready(function() {
		$('.askDelete').jConfirmAction(aceptaEliminar, null);
		$('#filtroBusqueda').jqm({
			modal : true
		});

		$('#duplicarListaModal').jqm({
			modal : true
		});
		
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

		mostrarEstadoFiltro();
		mostrarEstadoPaginado();

	});
</script>

<div class="center_content">

	<table style="width: 100%;">
		<tr>
			<td>
				<h2>Administraci&oacute;n de Listas de Precio</h2>
			</td>

			<td><a href="actualizacionMasivaListaPrecio.htm" class="bt_red"> <span
					class="bt_red_lft"></span><strong>Actualizaci&oacute;n masiva</strong><span
					class="bt_red_r"></span>
			</a>
			<a href="nuevaListaPrecio.htm" class="bt_green"> <span
					class="bt_green_lft"></span><strong>Agregar</strong><span
					class="bt_green_r"></span>
			</a> <a href="#" class="bt_blue" onclick="mostrarFiltros();"> <span
					class="bt_blue_lft"></span><strong>Refinar lista</strong><span
					class="bt_blue_r"></span>
			</a></td>
		</tr>

	</table>

	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


	<div id="descripcionFiltroID" class="filter_text"></div>
	<table id="rounded-corner" summary="Lista Precio">
		<thead>
			<tr>
				<th width="80" class="rounded-company" scope="col"
					style="text-align: center;">C&oacute;digo</th>
				<th width="280" class="rounded" scope="col"
					style="text-align: center;">Nombre</th>
				
					
				<th width="80" class="rounded" scope="col"
					style="text-align: center;">Editar</th>
				<th width="80" class="rounded" scope="col"
					style="text-align: center;">Editar Precios</th>
				<th width="80" class="rounded" scope="col"
					style="text-align: center;">Duplicar</th>
				<th width="80" class="rounded-q4" scope="col"
					style="text-align: center;">Eliminar</th>

			</tr>
		</thead>

		<tbody>
			<c:forEach items="${listaspreciodto}" var="listaprecio" varStatus="i">
				<tr>
					<td align="center">${listaprecio.codigo}</td>
					<td align="center">${listaprecio.nombre}</td>
					<td align="center"><a href="#"
						onclick="editar('${listaprecio.codigo}')"><img
							src="images/user_edit.png" alt="" title="" border="0" /></a></td>
					<td align="center"><a href="#"
						onclick="editarPrecios('${listaprecio.codigo}')"><img
							src="images/user_edit.png" alt="" title="" border="0" /></a></td>	
					<td align="center"><a href="#"
						onclick="duplicarLista('${listaprecio.codigo}')"><img
							src="images/user_edit.png" alt="" title="" border="0" /></a></td>						
					<td align="center"><a href="#" class="askDelete"
						id="${i.count}"><img src="images/trash.png" alt="" title=""
							border="0" /></a> <input type="hidden" id="codigo${i.count}" value="${listaprecio.codigo}" /> </td>
					




				</tr>
			</c:forEach>
		</tbody>

		<tfoot>
			<tr>
				<td colspan="5" class="rounded-foot-left"></td>
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
	
	

<!-- FORMULARIOS DE EDICION Y ELIMINACIÓN ---- --> 

	<form:form method="post" name="editarForm" id="editarForm" action="editarListaPrecio.htm" commandName="listapreciodto">
		<form:hidden path="codigo" id="codigoEditarID" />
	</form:form>

	<form:form method="post" name="eliminarForm" id="eliminarForm" action="doEliminarListaPrecio.htm" commandName="listapreciodto">
		<form:hidden path="codigo" id="codigoEliminarID" />
	</form:form>
	
	<form:form method="post" name="verPreciosForm" id="verPreciosForm" action="verPrecios.htm" commandName="listapreciodto">
	<form:hidden path="codigo" id="codigoVerListaPreciosID" />
	</form:form>

	<!-- FILTRO -->

	<div id="filtroBusqueda" style="display:none;" class="jqmWindow">
		<form:form  method="post" name="filtrarForm" id="filtrarForm" cssClass="niceform" action="refreshListasPrecio.htm" commandName="filtroDTO">
		
			<fieldset>
				<dl>
					<dt> <label for="codigo">C&oacute;digo:</label> </dt>
					<dd> <form:input path="codigo" id="codigoID"/> </dd>
				</dl>
				
				<dl>
					<dt> <label for="descripcion">Nombre:</label> </dt>
					<dd> <form:input path="nombre" id="nombreID"/> </dd>
				</dl>
				
				
				<dl><dt></dt></dl>
				<dl class="submit">
					<dt> <input type="button" value="Filtrar" onclick="refinarConsulta('true');"/> </dt>
					<dt> <input type="button" value="Cancelar" onclick="refinarConsulta('false');"/> </dt>			
				</dl>
			</fieldset>

		</form:form>
	</div>
	
	
	
	<!-- DUPLICAR -->
		
	<div id="duplicarListaModal" style="display:none;" class="jqmWindow">
		<form:form method="post" name="duplicarForm" cssClass="niceform" id="duplicarForm" action="doDuplicarListaPrecio.htm" commandName="listapreciodto">
		
			<fieldset>
				<dl>
					<dt> <label for="descripcion">Nombre de la lista duplicada:</label> </dt>
					<dd> 
						<form:input path="nombre" id="nombreDuplicarID"/>
						<div id="nombreErrorID" class="divError"></div>
						<form:hidden path="codigo" id="codigoDuplicarID" /> 
					</dd>
				</dl>
				
				
				<dl><dt></dt></dl>
				<dl class="submit">
					<dt> <input type="button" value="Duplicar" onclick="doDuplicar('true');"/> </dt>
					<dt> <input type="button" value="Cancelar" onclick="doDuplicar('false');"/> </dt>			
				</dl>
			</fieldset>

		</form:form>
	</div>
	
	
		<form:form  method="post" name="paginarForm" id="paginarForm" action="refreshListasPrecio.htm" commandName="filtroPaginadoDTO">
		<form:hidden path="cantTotalRegs" id="cantTotalRegsID"/>
		<form:hidden path="regPorPagina" id="regPorPaginaID"/>
		<form:hidden path="numeroPaginaActual" id="numeroPaginaActualID"/>
		<form:hidden path="cantMaxPaginas" id="cantMaxPaginasID"/>
	</form:form>



</div>
