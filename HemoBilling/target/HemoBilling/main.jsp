<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 style="padding-top: 10px; padding-bottom: 20px;">Bienvenido a HemoBilling - Sistema de Facturaci&oacute;n</h2>

<table style="width: 100%; margin-left:auto; margin-right:auto; text-align:center;">
 
  <tr style="height: 200px; width: 100%; vertical-align: top; margin-left:auto; margin-right:auto;" >
  
    <td style="width: 33%;">
    
		<!--Tabla de Administracion-->
		<table id="rounded-corner" style="width: 253px; margin-left:auto; margin-right:auto;">
			<thead>
				<tr>
					<td colspan="2" style="background-image:url('images/top_menu_home.png'); height: 36px; background-color:#ffffff; font-size: small; color:#FFFFFF; font-weight: bold; vertical-align: bottom;"> Administraci&oacute;n</td>
				</tr>
			</thead>
	
			<tbody>
			
			<tr>
				<td colspan="2" style="font-size: small;"><a href="pacientes.htm" title="" style="text-decoration: none;">Pacientes</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="obrasSociales.htm" title="" style="text-decoration: none;">Obras Sociales</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="prestacionesBrindadas.htm" title="" style="text-decoration: none;">Prestaciones Brindadas</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="importarLaboratorio.htm" title="" style="text-decoration: none;">Importar de Laboratorio</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="listasPrecio.htm" title="" style="text-decoration: none;">Listas de Precios</a></td>
			</tr>
	
			</tbody>
	
			<tfoot>
				<tr>
					<td class="rounded-foot-left">&nbsp;</td>
					<td class="rounded-foot-right">&nbsp;</td>
				</tr>
			</tfoot>
		</table>
		
    </td>
  
    <td style="width: 33%;"> 
    
    	<!--Tabla de Facturacion -->
		<table id="rounded-corner" style="width: 253px; margin-left:auto; margin-right:auto;">
			<thead>
				<tr>
					<td colspan="2" style="background-image:url('images/top_menu_home.png'); height: 36px; background-color:#ffffff; font-size: small; color:#FFFFFF; font-weight: bold; vertical-align: bottom;"> Facturaci&oacute;n</td>
				</tr>
			</thead>
	
			<tbody>
			
			<tr>
				<td colspan="2" style="font-size: small"><a href="facturarPrestaciones.htm" title="" style="text-decoration: none;">Facturar Prestaciones</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="consultarFacturasPrestaciones.htm" title="" style="text-decoration: none;">Consultar Facturas Prestaciones</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="facturarServicios.htm" title="" style="text-decoration: none;">Facturar Servicios</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="consultarFacturasServicios.htm" title="" style="text-decoration: none;">Consulta Facturas Servicios</a></td>
			</tr>
	
			</tbody>
	
			<tfoot>
				<tr>
					<td class="rounded-foot-left">&nbsp;</td>
					<td class="rounded-foot-right">&nbsp;</td>
				</tr>
			</tfoot>
		</table>

    </td>
    
    <td style="width: 33%;">
    
    	<!--Tabla de Facturacion -->
		<table id="rounded-corner" style="width: 253px; margin-left:auto; margin-right:auto;">
			<thead>
				<tr>
					<td colspan="2" style="background-image:url('images/top_menu_home.png'); height: 36px; background-color:#ffffff; font-size: small; color:#FFFFFF; font-weight: bold; vertical-align: bottom;"> Resultados</td>
				</tr>
			</thead>
	
			<tbody>
			
			<tr>
				<td colspan="2" style="font-size: small"><a href="generarInformeFacturasEmitidas.htm" title="" style="text-decoration: none;">Listado de Facturas Emitidas</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="generarInformeCartaFacturas.htm" title="" style="text-decoration: none;">Carta de Facturas</a></td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: small"><a href="consultarFacturasPrestaciones.htm" title="" style="text-decoration: none;">Reportes de Facturaci&oacute;n</a></td>
			</tr>
	
			</tbody>
	
			<tfoot>
				<tr>
					<td class="rounded-foot-left">&nbsp;</td>
					<td class="rounded-foot-right">&nbsp;</td>
				</tr>
			</tfoot>
		</table>

    </td>
    
 </tr>
  
</table>
