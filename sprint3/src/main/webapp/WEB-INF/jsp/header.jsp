<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../../css/style.css" />
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
				<li><a href="../issue/list">Ver tareas</a>
				</li>
				<c:if test="${user != null}">
					<li><a href="../issue/create">Crear tarea</a>
					</li>
				</c:if>
				<li><a href="../project/view">Detalle del proyecto</a>
				</li>
				<c:if test="${user.admin == true}">
					<li><a href="../project/edit">Editar proyecto</a>
					</li>
					<li><a href="../user/register">Registrar usuario</a>
					</li>
					<li><a href="../user/list">Borrar usuarios</a>
					</li>
				</c:if>
				<c:if test="${user != null}">
					<li><a href="../user/logout">Desloguearse</a>
					</li>
				</c:if>
			</c:if>
 				<li><a href="../project/select">Seleccionar proyecto</a> 
 				</li>
 			<c:if test="${user == null}">
 				<li><a href="../user/index">Loguearse</a> 
 				</li>
 			</c:if>	
		</ul>
	</div>