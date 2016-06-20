<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<title>:: HemoBilling :: Iniciar Sesi&oacute;n</title>
	
	<link rel="stylesheet" type="text/css" media="all" href="estilos/niceforms-default.css" />
	<link rel="stylesheet" type="text/css" href="estilos/estilo.css" />
	
	<script type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/jconfirmaction.jquery.js"></script>
	<script type="text/javascript" src="js/niceforms.js"></script>
	
	<script type="text/javascript">
	
	function mostrarError( mensaje )
	{
		$('#errorMsg').html(mensaje);
		$('#errorDiv').show();	
	}
	
	function mostrarSucess( mensaje )
	{
		$('#sucessMsg').html(mensaje);
		$('#sucesDiv').show();
	}
	
	$(document).ready(function(){
		var login_error = "<%=request.getParameter("login_error")%>";
		var logout_sucess = "<%=request.getParameter("logout_sucess")%>";
		
		if( login_error!=null && login_error!="null" && login_error!="" )
		{
			if( login_error=="1" )
			{
				//Acceso denegado - ver spring_security.xml
				mostrarError( <spring:message code="login.accesoDenegado.mensaje"></spring:message> );
				
			} else if( login_error=="2" )
			{
				//Usuario y/o Contrasenia erroneos - ver spring_security.xml
				mostrarError( <spring:message code="login.datosIncorrectos.mensaje"></spring:message> );
			}
			else
			{
				//Error desconocido. Mensaje generico
				mostrarError( <spring:message code="login.errorGenerico.mensaje"></spring:message> );
			}			
		}
		
		if( logout_sucess!=null && logout_sucess!="null" && logout_sucess!="" )
		{
			if( logout_sucess=="1" )
			{
				//logout exitoso - ver spring_security.xml
				mostrarSucess( <spring:message code="logout.exitoso.mensaje"></spring:message> );
			}
			else
			{
				//logout desconocido. Mensaje generico
				mostrarSucess( <spring:message code="logout.mensajeGenerico.mensaje"></spring:message> );
			}
		}
	});
	
	function myLoadFunction(){
		
		NFInit();
		document.f.j_username.focus();
	}
	
	</script>
	

</head>

<body onload='myLoadFunction()'>

	<div id="main_container">

		<div class="header_login">
			<div class="logo"><img src="images/logo.png" alt="" title="" border="0" /></div>
		</div>

		<div class="login_form">
	         
	         <h3>HemoBilling - Iniciar Sesi&oacute;n</h3>
         
         <form class="niceform" name='f' action="<c:url value='j_spring_security_check' />" method='post'>
         
                <fieldset>
                    <dl>
                        <dt><label for="email">Usuario:</label></dt>
                        <dd><input type='text' name='j_username' size="54"/></dd>
                    </dl>
                    <dl>
                        <dt><label for="password">Contrase&ntilde;a:</label></dt>
                        <dd><input type='password' name='j_password' size="54" /></dd>
                    </dl>
                    
                     <dl class="submit">
                    	<dd><input type="submit" name="submit" id="submit" value="Entrar"/></dd>
                     </dl>
                    
                </fieldset>
                
         </form>
			
			<div  id="errorDiv" align="center" style="display: none;">
				<div id="errorMsg" class="error_box">&nbsp;</div>  
			</div>
			
			<div id="sucesDiv" align="center" style="display: none;">
				<div id="sucessMsg" class="valid_box">&nbsp;</div>  
			</div>

		</div>

	</div>		

</body>

</html>