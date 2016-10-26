<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>

	<div id="logo">
		<img alt="." src="./img/tasks_icon.png">
	</div>
	
	<div id="login">
		<h2>Bienvenido a ITrack</h2>
		<form action="index" method="post">
			<fieldset id="marcoLogin">
				<c:forEach items="${errors}" var="error">
					<p>
						<em><c:out value="${error}" /></em>
					</p>
				</c:forEach>
				<br>
				<legend>Ingrese los siguientes datos para el logueo</legend>
					<table>
						<tr>
							<td><strong>Usuario</strong></td>
							<td><input type="text" value="${input_user}" name="username" /></td>
						</tr>
						<tr>
							<td><strong>Contrase&ntilde;a</strong></td>
							<td><input type="password"name="password" /></td>
						</tr>
						<tr>
							<td></td><td><input type="submit" value="Ingresar" /></td>
						</tr>
						
					</table>
			</fieldset>
		</form>
	</div>
</body>
</html>