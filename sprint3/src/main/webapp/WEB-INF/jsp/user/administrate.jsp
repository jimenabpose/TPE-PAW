<%@ include file="../header.jsp"%>
<p><c:out value="${message}" /></p>
<em><c:out value="${error}"/></em>

<h2>Listado de usuarios para aceptar:</h2>

<table id="list" border="1">
	<tr>
		<th>Nombre de usuario</th>
		<th>Nombre</th>
		<th>Apellido</th>
		<th>Acciones</th>
	</tr>
	<c:forEach items="${solicitations}" var="solicitation">
		<tr>
			<td><c:out value="${solicitation.username}"/></td>
			<td><c:out value="${solicitation.firstName}"/></td>
			<td><c:out value="${solicitation.lastName}"/></td>
			<td><a href="accept?id=${solicitation.id}">Aceptar</a>,
			<a href="decline?id=${solicitation.id}">Rechazar</a></td>
		</tr>
	</c:forEach>
</table>

<%@ include file="../footer.jsp"%>