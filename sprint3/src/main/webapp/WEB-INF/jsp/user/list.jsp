<%@ include file="../header.jsp"%>
<em><c:out value="${error}"/></em>
<h2>Listado de usuarios para borrar</h2>
<p><c:out value="${message}" /></p>
<c:if test="${message == null}">
<table id="list">
	<tr>
		<th>Nombre de usuario</th>
		<th>Nombre</th>
		<th>Apellido</th>
		<th>Tipo</th>
	</tr>
	<c:forEach items="${users}" var="user">
		<tr>
			<td><c:out value="${user.username}"/></td>
			<td><c:out value="${user.firstName}"/></td>
			<td><c:out value="${user.lastName}"/></td>
			<td><c:out value="${user.type.name}"/></td>
			<td><a href="delete?id=${user.id}">Borrar</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<%@ include file="../footer.jsp"%>