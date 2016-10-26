<%@ include file="../header.jsp"%>

<c:out value="${message}" />

<h2>Estado del proyecto</h2>

<table>
		<th></th>
		<th>Cantidad</th>
		<th>Tiempo estimado</th>
	<tr>
		<td><strong>Tareas abiertas</strong></td>
		<td><c:out value="${countOpen}" /></td>
		<td><c:out value="${timeOpen}" /></td>
	</tr>
	<tr>
		<td><strong>Tareas en curso</strong></td>
		<td><c:out value="${countOnCourse}" /></td>
		<td><c:out value="${timeOnCourse}" /></td>
	</tr>
	<tr>
		<td><strong>Tareas finalizadas</strong></td>
		<td><c:out value="${countFinished}" /></td>
		<td><c:out value="${timeFinished}" /></td>
	</tr>
	<tr>
		<td><strong>Tareas cerradas</strong></td>
		<td><c:out value="${countClosed}" /></td>
		<td><c:out value="${timeClosed}" /></td>
	</tr>
	<tr>
</table>
<%@ include file="../footer.jsp"%>