<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


 

<header>
	
	
	<script src="js/jquery/form/jquery.form.js" type="text/javascript"></script>

	<script src="js/ajaxForm.js" type="text/javascript"></script>
	
	<script src="js/jquery/jquery.jclock-1.2.0.js.txt" type="text/javascript"></script>
<script src="js/clockp.js" type="text/javascript" ></script>


<script type="text/javascript">

$(function($) {
    $('.jclock').jclock();
});
</script>

	<script type="text/javascript">
	
		var sucessGetUsername = function sucessGetUsername(json)
		{
			var nombre = json.data[0];
			
			if( nombre!=null && nombre!="null" && nombre !="" )
			{
				$('#nombreUsuarioDiv').html( "Bienvenido "+nombre);
			}
			else
				$('#nombreUsuarioDiv').html( "Bienvenido" );
				

		};
		
		var errorGetUsername = function errorGetUsername(respuesta)
		{
			$('#nombreUsuarioDiv').html( "Bienvenido" );
		};
	
		$(document).ready(function(){
			ajaxForm( 'getUserNameForm' , sucessGetUsername , null , errorGetUsername );
			$('#getUserNameForm').submit();
		});
	
	
	</script>


</header>

<div class="header">

	<table style="width: 100%;">
	<tr>
	
	<td>
		<div class="logo"><img src="images/logo.gif" alt="" title="" border="0"/></div>
	</td>
	
	<td>
		<div class="jclock"></div>
	</td>
	
	<td style="vertical-align: middle; width: 35%;">
		<div class="right_header"><div id="nombreUsuarioDiv"></div> <a href="<c:url value="/j_spring_security_logout" />" class="logout">Logout</a></div>
	</td>
	
	</tr>	
	</table>
    
</div>

<form:form method="post" name="getUserNameForm" id="getUserNameForm" action="getUserName.htm">
</form:form>