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
		<h2>
			Seleccione un proyecto
			<c:if test="${user.admin == true}">
			 o <a href="create">cree uno nuevo</a>
			 </c:if>
		 </h2>
		<form:form action="select" method="post" commandName="selectProjectForm">
			<fieldset id="marcoLogin">
				<table>
					<tr>
						<c:if test="${projects == null}">
							<h3>No existe ningún proyecto, debe <a href="create_project">crear uno nuevo</a> para continuar</h3>
						</c:if>
						<c:if test="${projects != null}">
							<form:select path="project"><br>
								<form:options items="${projects}" itemValue="id" itemLabel="fullDescription"/>
							</form:select>
						</c:if>
					</tr>
					<br/>
					<tr>
						<td><input type="submit" value="Ingresar" /></td>
					</tr>

				</table>
			</fieldset>
		</form:form>
		<a href="../user/index"><h3>Desloguearse</h3></a>
		<a href="../user/administrate"><h3>Administrar usuarios</h3></a>
	</div>
</body>
</html>