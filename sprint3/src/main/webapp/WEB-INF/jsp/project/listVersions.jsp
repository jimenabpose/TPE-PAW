<%@ include file="../header.jsp"%>

<em><c:out value="${error}"/></em>
<h2>Versiones del proyecto</h2>
<table>
	<th>Nombre</th>
	<th>Descripcion</th>
	<th>Fecha de liberacion</th>
	<th>Estado</th>
	<c:if test="${listUnreleasedVersion}">
		<th>Progreso</th>
	</c:if>
	<th></th>
	<th></th>
	<c:forEach items="${versions}" var="version">
		<tr>
			<td><c:out value="${version.name}" /></td>
			<td><c:out value="${version.description}" /></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${version.releaseDate}" /></td>
			<td><c:out value="${version.state.name}" /></td>
			<c:if test="${listUnreleasedVersion}">
				<c:if test="${version.progress == -1}">
					<td>Sin tareas asignadas</td>
				</c:if>
				<c:if test="${version.progress != -1}">
					<td><c:out value="${version.progress}" /></td>
				</c:if>
			</c:if>
			<td><a href="editVersion?id=<c:out value="${version.id}"/>">Editar</a></td>
			<td><a href="deleteVersion?id=<c:out value="${version.id}"/>">Eliminar</a></td>
			<td><a href="versionView?id=<c:out value="${version.id}"/>">Ver</a></td>
		</tr>
	</c:forEach>
</table>
<c:if test="${!listUnreleasedVersion}">
<a href="listUnreleasedVersions">Listar sólo versiones no liberadas</a>
</c:if>
<%@ include file="../footer.jsp"%>