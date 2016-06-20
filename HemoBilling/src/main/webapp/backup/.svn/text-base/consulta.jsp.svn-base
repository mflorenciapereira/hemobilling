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

<script src="js/hemoUtils.js" type="text/javascript"></script>

<script type="text/javascript">

	$(document).ready(function() 
	{		

		<c:choose>
			<c:when test='${resultOperacionDTO.error==true}'>
				
				showMessage( ${resultOperacionDTO.errorMessage} , "errorMessageDivID" );
				setTimeout(function(){hideMessage("errorMessageDivID");},5000);
				
			</c:when>
			<c:otherwise>
				
				<c:if test='${resultOperacionDTO.errorMessage!=""}'>
					showMessage( "${resultOperacionDTO.errorMessage}" , "exitoMessageDivID" );
					setTimeout(function(){hideMessage("exitoMessageDivID");},10000);
				</c:if>

			</c:otherwise>
		</c:choose>	
		
	});
	
	
</script>


<div class="center_content">

	<table style="width: 100%;">
		<tr>
			<td>
				<h2>Backups Realizados al Sistema</h2>
			</td>

			<td><a href="doBackup.htm" class="bt_green"> <span
					class="bt_green_lft"></span><strong>Realizar Backup</strong><span
					class="bt_green_r"></span>
			</a> </td>
		</tr>

	</table>

	<div id="errorMessageDivID" class="error_box" style="display: none;"></div>
	<div id="exitoMessageDivID" class="valid_box" style="display: none;"></div>


	<table id="rounded-corner" summary="Clientes">
		<thead>
			<tr>
				<th class="rounded-company" scope="col" style="text-align: center;">N&uacute;mero</th>
				<th class="rounded" scope="col"	style="text-align: center;">Fecha</th>
				<th class="rounded-q4" scope="col"	style="text-align: center;"></th>

			</tr>
		</thead>

		<tbody>
			<c:forEach items="${backupsRealizadosDTO}" var="backup" begin="0" varStatus="i">
				<tr>
					<c:choose>
						<c:when test='${i.index==0}'>
							<td align="center"><b>${backup.id}</b></td>
							<td align="center"><b>${backup.fechaRealizado}</b></td>
							<td align="center"></td>
						</c:when>
						<c:otherwise>
							<td align="center">${backup.id}</td>
							<td align="center">${backup.fechaRealizado}</td>
							<td align="center"></td>
						</c:otherwise>
					</c:choose>		
				

				</tr>
			</c:forEach>
		</tbody>

		<tfoot>
			<tr>
				<td colspan="2" class="rounded-foot-left"></td>
				<td class="rounded-foot-right">&nbsp;</td>
			</tr>
		</tfoot>
	</table>


</div>
