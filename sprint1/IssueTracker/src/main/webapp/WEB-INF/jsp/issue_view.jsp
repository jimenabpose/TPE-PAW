<%@ include file="header.jsp"%>

<c:out value="${message}"/>

<h2>Tarea</h2>

<table>
<tr>
	<td><strong>C&oacute;digo:</strong></td>
	<td><c:out value="${issue.code}"/></td>
</tr>
<tr>
	<td><strong>T&iacute;tulo:</strong></td>
	<td><c:out value="${issue.title}"/></td>
</tr>
<tr>
	<td><strong>Descripci&oacute;n:</strong></td>
	<td><c:out value="${issue.description}"/></td>
</tr>
<tr>
	<td><strong>Fecha de creaci&oacute;n:</strong></td>
	<td><fmt:formatDate type="date" value="${issue.creationDate}"/></td>
</tr>
<tr>
	<td><strong>Tiempo estimado:</strong></td>
	<td><c:out value="${issue.estimatedTime}"/>
		<c:if test="${issue.estimatedTime != null}">hs</c:if> 
	</td>
</tr>
<tr>
	<td><strong>Asignado a:</strong></td>
	<td><c:out value="${issue.assignee.username}"/></td>
</tr>
<tr>
	<td><strong>Creada por:</strong></td>
	<td><c:out value="${issue.reporter.username}"/></td>
</tr>
<tr>
	<td><strong>Prioridad:</strong></td>
	<td><c:out value="${issue.priority}"/></td>
</tr>
<tr>
	<td><strong>Estado:</strong></td>
	<td><c:out value="${issue.state}"/></td>
</tr>
<tr>
	<td><strong>Resolución:</strong></td>
	<td><c:out value="${issue.resolution}"/></td>
</tr>
</table>

<!-- Para que el usuario resuelva la tarea -->
<c:if test="${ id_for_resolution != null }">
	<div id="issue_resolution">
		Seleccione la resolución de la tarea
		<form action="resolve_issue" method="post">
			<select name="resolution_sel" width="20">
				<c:forEach var="item" items="${list_for_resolution}">
					<option value="<c:out value="${item}" />">
						<c:out value="${item}" />
					</option>
				</c:forEach>
			</select>
			<input type="hidden" name="id_for_resolution" value="<c:out value="${id_for_resolution }"/>">
			<input type="submit" value="Resolver">
		</form>
	</div>
</c:if>


<!-- Para que el usuario se encargue de la tarea -->
<c:if test="${ id_for_asignation != null }">
	<div id="asignation">
		<a href="issue_asign?id=<c:out value="${id_for_asignation}"/>">Asignarme esta tarea</a>
	</div>
</c:if>

<!-- Para que el lider cierre la tarea la tarea -->
<c:if test="${ id_for_closing_issue != null }">
	<div id="asignation">
		<a href="close_issue?id=<c:out value="${id_for_closing_issue}"/>">Cerrar esta tarea</a>
	</div>
</c:if>


<div id="edition">
	<a href="set_editing_issue">Editar esta tarea</a>
</div>



<%@ include file="footer.jsp"%>