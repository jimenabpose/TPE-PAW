<%@ include file="../header.jsp"%>

<em><c:out value="${error}"/></em>
<h2>Seleccione usuarios para el proyecto</h2>
<table>
	<th>Usuario</th>
	<th>Nombre</th>
	<th>Apellido</th>
	<th></th>
	<c:forEach items="${projectUsers}" var="userx">
		<tr>
			<td><c:out value="${userx.username}" /></td>
			<td><c:out value="${userx.firstName}" /></td>
			<td><c:out value="${userx.lastName}" /></td>
			<td><a href="deleteUser?id=<c:out value="${userx.id}"/>">Eliminar</a></td>
		</tr>
	</c:forEach>
	<c:forEach items="${notProjectUsers}" var="userz">
		<tr>
			<td><c:out value="${userz.username}" /></td>
			<td><c:out value="${userz.firstName}" /></td>
			<td><c:out value="${userz.lastName}" /></td>
			<td><a href="selectUser?id=<c:out value="${userz.id}"/>">Agregar</a></td>
		</tr>
	</c:forEach>
</table>
<%@ include file="../footer.jsp"%>