<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="./css/style.css" />
</head>
<body>

	<div id="logo">
		<img alt="." src="./img/tasks_icon.png">
	</div>

	<div id="login">
		<h2>
			Seleccione un proyecto
			<c:if test="${user.admin == true}">
			 o <a href="create_project">cree uno nuevo</a>
			 </c:if>
		 </h2>
		<form action="select_project" method="POST">
			<fieldset id="marcoLogin">
				<table>
					<tr>
						<c:if test="${projects == null}">
							<h3>No existe ningún proyecto, debe <a href="create_project">crear uno nuevo</a> para continuar</h3>
						</c:if>
						<c:if test="${projects != null}">
							<select name="project_id"><br>
								<c:forEach items="${projects}" var="inp_project">
									<option value="${inp_project.id}" <c:if test="${inp_project.id == project.id}">selected="selected"</c:if>>
										<span>Codigo: </span> 
										<c:out value="${inp_project.code} |" />
										<span>Nombre: </span> 
										<c:out value="${inp_project.name}"/>
									</option>
								</c:forEach>
							</select>
						</c:if>
					</tr>
					<br/>
					<tr>
						<td><input type="submit" value="Ingresar" /></td>
					</tr>

				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>