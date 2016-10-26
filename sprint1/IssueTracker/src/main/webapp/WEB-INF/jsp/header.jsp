<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	
	<!-- Si hay un usuario loggeado lo muestro -->
	<c:if test="${!empty user}">
	<div id="user_logged">
		<c:out value="${user.firstName} ${user.lastName} | ${project.name}"/>
	</div>
	</c:if>
	
	<h1>ITrack</h1>
	<div id="navegador">
		<ul>
			<!-- 			Las referencias estas son referencias al url mapping del web.xml -->
			<c:if test="${project != null}">
				<li><a href="issue_list">Ver tareas</a>
				</li>
				<li><a href="create_issue">Crear tarea</a>
				</li>
				<c:if test="${user.admin == true}">
					<li><a href="set_editing_project">Editar proyecto</a>
					</li>
					<li><a href="register_user">Registrar Usuario</a>
					</li>
					<li><a href="select_project">Salir del proyecto</a>
					</li>
				</c:if>
				<li><a href="logout">Desloguearse</a>
				</li>
			</c:if>
			<c:if test="${project == null}">
 				<li><a href="select_project">Seleccionar un proyecto existente</a> 
 				</li> 
			</c:if>
		</ul>
	</div>