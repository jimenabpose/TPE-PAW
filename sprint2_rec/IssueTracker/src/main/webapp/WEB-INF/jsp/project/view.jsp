<%@ include file="../header.jsp"%>

<c:out value="${message}" />

<h2>Proyecto</h2>

<table>
	<tr>
		<td><strong>C&oacute;digo:</strong></td>
		<td><c:out value="${project.code}" /></td>
	</tr>
	<tr>
		<td><strong>Nombre:</strong></td>
		<td><c:out value="${project.name}" /></td>
	</tr>
	<tr>
		<td><strong>Descripción:</strong></td>
		<td><c:out value="${project.description}" /></td>
	</tr>
	<tr>
		<td><strong>Líder:</strong></td>
		<td><c:out value="${project.leader.username}" /></td>
	</tr>
</table>
<%-- 	<c:if test="${user.admin == true}"> --%>
<%-- 		<c:if test="${project.public == false}"> --%>
<!-- 			<p>El proyecto es privado</p> -->
<%-- 			<p><a href="changeState?id=<c:out value="${project.id}"/>">Marcar proyecto como público</a></p> --%>
<%-- 		</c:if> --%>
<%-- 		<c:if test="${project.public == true}"> --%>
<!-- 			<p>El proyecto es público</p> -->
<%-- 			<p><a href="changeState?id=<c:out value="${project.id}"/>">Marcar proyecto como privado</a></p> --%>
<%-- 		</c:if> --%>
<%-- 	</c:if> --%>
	<p>Participantes del proyecto</p>
	<li>
	<c:forEach items="${users}" var="user">
		<ul>
			<c:out value="${user.username}" />
			<c:out value="${user.firstName}" />
			<c:out value="${user.lastName}" />
		</ul>
	</c:forEach>
	<li>
		<p><a href="consultState">Consultar estado del proyecto</a></p>
		<p><a href="listUsers">Seleccionar participantes del proyecto</a></p>
<%@ include file="../footer.jsp"%>