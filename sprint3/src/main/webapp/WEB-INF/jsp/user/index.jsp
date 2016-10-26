<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../../css/style.css" />
</head>
<body>

	<div id="logo">
		<img alt="." src="../../img/tasks_icon.png">
	</div>
	
	<div id="login">
		<h2>Bienvenido a ITrack</h2>
		<form:form action="index" method="post" commandName="loginUserForm">
			<fieldset id="marcoLogin">
				<br>
				<legend>Ingrese los siguientes datos para el logueo</legend>
					<table>
						<tr>
							<td><strong>Usuario</strong></td>
							<td><form:input path="username" /></td>
							<td><em><form:errors path="username"/></em></td>
						</tr>
						<tr>
							<td><strong>Contrase&ntilde;a</strong></td>
							<td><form:password path="password" /></td>
							<td><em><form:errors path="password"/></em></td>
						</tr>
						<tr>
							<td></td><td><input type="submit" value="Ingresar" /></td>
						</tr>
						
					</table>
			</fieldset>
		</form:form>
		<br></br>
		<div>
			<a href="../project/select"><h3>Ingresar como usuario público</h3></a>
		</div>
		<div>
			<a href="../user/register"><h3>Solicitar registro</h3></a>
		</div>
	</div>
</body>
</html>