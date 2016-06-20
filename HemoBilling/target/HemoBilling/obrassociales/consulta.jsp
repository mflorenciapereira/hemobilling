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

	/*------------ Elimina y Editar ------------*/

	function editar(codigo) {
		$('#codigoEditarID').val(codigo);
		$('#editarForm').submit();
	}
	
	function corregir(codigo) {
		$('#codigoCorregirID').val(codigo);
		$('#corregirForm').submit();
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
				+ "${filtroDTO.codigo}" + " | Nombre: "
				+ "${filtroDTO.nombre}"
				+ " | C&oacute;digo RNOS: "	+ "${filtroDTO.codigoRNOS}"
				+ " | Sigla: "	+ "${filtroDTO.sigla}"
				+ " | Prestador: "	+ "${filtroDTO.prestador}"
				+ " | CUIT: "	+ "${filtroDTO.cuit}"
				+ " | Localidad: "	+ "${filtroDTO.localidad}"
				+ " | provincia: "	+ "${filtroDTO.provincia}"
				+ " | Tipo IVA: "	+ "${filtroDTO.tipoIVA.descripcion}"
				;

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
			if ($('#codigoRNOSID').val() == "")
				$('#codigoRNOSID').val("Todos");
			if ($('#siglaID').val() == "")
				$('#siglaID').val("Todos");
			if ($('#prestadorID').val() == "")
				$('#prestadorID').val("Todos");
			if ($('#cuitID').val() == "")
				$('#cuitID').val("Todos");
			if ($('#localidadID').val() == "")
				$('#localidadID').val("Todos");
			if ($('#provinciaID').val() == "")
				$('#provinciaID').val("Todos");
			if ($('#tipoIVAID').val() == "")
				$('#tipoIVAID').val("Todos");

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
				<h2>Administraci&oacute;n de Obras Sociales</h2>
			</td>

			<td><a href="nuevaObraSocial.htm" class="bt_green"> <span
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
	<table id="rounded-corner" summary="Pacientes">
		<thead>
			<tr>
				<th class="rounded-company" scope="col" style="text-align: center;">C&oacute;digo</th>
				<th class="rounded" scope="col"	style="text-align: center;">Nombre</th>
				<th class="rounded" scope="col"	style="text-align: center;">Sigla</th>
				<th class="rounded" scope="col"	style="text-align: center;">CUIT</th>
				<th class="rounded" scope="col"	style="text-align: center;">Tel&eacute;fono</th>
				<th class="rounded" scope="col"	style="text-align: center;">Email</th>
				<th class="rounded" scope="col"	style="text-align: center;">Editar</th>
				<th class="rounded" scope="col"	style="text-align: center;">Corregir</th>
				<th class="rounded-q4" scope="col"	style="text-align: center;">Eliminar</th>

			</tr>
		</thead>

		<tbody>
			<c:forEach items="${obrassocialesDTO}" var="obrasocial" varStatus="i">
				<tr>
					<td align="center">${obrasocial.codigo}</td>
					<td align="center">${obrasocial.nombre}</td>
					<td align="center">${obrasocial.sigla}</td>
					<td align="center">${obrasocial.cuit}</td>
					<td align="center">${obrasocial.telefono}</td>
					<td align="center">${obrasocial.email}</td>
					<td align="center"><a href="#"	onclick="editar('${obrasocial.codigo}')"><img src="images/user_edit.png" alt="" title="" border="0" /></a></td>
					<td align="center"><a href="#"	onclick="corregir('${obrasocial.codigo}')"><img src="images/user_edit.png" alt="" title="" border="0" /></a></td>
					<td align="center"><a href="#" class="askDelete"	id="${i.count}"><img src="images/trash.png" alt="" title=""	border="0" /></a> <input type="hidden" id="codigo${i.count}" value="${obrasocial.codigo}" /> </td>

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

	<form:form method="post" name="editarForm" id="editarForm"
		action="editarObraSocial.htm" commandName="obrasocialDTO">
		<form:hidden path="codigo" id="codigoEditarID" />
	</form:form>

	<form:form method="post" name="eliminarForm" id="eliminarForm"
		action="doEliminarObraSocial.htm" commandName="obrasocialDTO">
		<form:hidden path="codigo" id="codigoEliminarID" />
	</form:form>
	
	<form:form method="post" name="corregirForm" id="corregirForm"
		action="verHistoriaListaPrecio.htm" commandName="obrasocialDTO">
		<form:hidden path="codigo" id="codigoCorregirID" />
	</form:form>


<!-- FILTRO -->

<div id="filtroBusqueda" style="display:none;" class="jqmWindow">
		<form:form  method="post" name="filtrarForm" id="filtrarForm" cssClass="niceform" action="refreshObrasSociales.htm" commandName="filtroDTO">
		
			<fieldset>
				<dl>
					<dt> <label for="codigo">C&oacute;digo:</label> </dt>
					<dd> <form:input path="codigo" id="codigoID"/> </dd>
				</dl>
				
				<dl>
					<dt> <label for="nombre">Nombre: </label> </dt>
					<dd> <form:input size="60" path="nombre" id="nombreID"/> </dd>
				</dl>
				
				<dl>
					<dt> <label for="codigoRNOS">Codigo RNOS: </label> </dt>
					<dd> <form:input path="codigoRNOS" id="codigoRNOSID"/> </dd>
				</dl>
				<dl>
					<dt> <label for="sigla">Sigla: </label> </dt>
					<dd> <form:input size="60" path="sigla" id="siglaID"/> </dd>
				</dl>
				<dl>
					<dt> <label for="prestador">Prestador: </label> </dt>
					<dd> <form:input size="60" path="prestador" id="prestadorID"/> </dd>
				</dl>
				<dl>
					<dt> <label for="cuit">CUIT: </label> </dt>
					<dd> <form:input size="20"  path="cuit" id="cuitID"/> </dd>
				</dl>
				<dl>
					<dt> <label for="localidad">Localidad: </label> </dt>
					<dd> <form:input size="60"  path="localidad" id="localidadID"/> </dd>
				</dl>
				<dl>
					<dt> <label for="provincia">Provincia: </label> </dt>
					<dd> <form:input size="60"  path="provincia" id="provinciaID"/> </dd>
				</dl>
				
				<dl>
					<dt> <label for="tipoIVA">Tipo IVA: </label> </dt>
					<dd> <form:select id="tipoIVAid" path="tipoIVA.id">
							<form:option value="-1" label="Seleccionar uno" />
							<form:options  items="${tiposivaDTO}" itemLabel="descripcion" itemValue="id" />
							</form:select> </dd>
				</dl>
				
				
				<dl><dt></dt></dl>
				<dl class="submit">
					<dt> <input type="button" value="Filtrar" onclick="refinarConsulta('true');"/> </dt>
					<dt> <input type="button" value="Cancelar" onclick="refinarConsulta('false');"/> </dt>			
				</dl>
			</fieldset>

		</form:form>
	</div>
	
	
		<form:form  method="post" name="paginarForm" id="paginarForm" action="refreshObrasSociales.htm" commandName="filtroPaginadoDTO">
		<form:hidden path="cantTotalRegs" id="cantTotalRegsID"/>
		<form:hidden path="regPorPagina" id="regPorPaginaID"/>
		<form:hidden path="numeroPaginaActual" id="numeroPaginaActualID"/>
		<form:hidden path="cantMaxPaginas" id="cantMaxPaginasID"/>
	</form:form>



</div>
