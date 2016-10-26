<%@ include file="../header.jsp"%>
<div id="content">
<h2>Listado de tareas</h2>
<p><c:out value="${message}" /></p>
<c:if test="${message == null}">
<table id="list">
	<tr>
		<th>Código</th>
		<th>Título</th>
		<th>Fecha de creación</th>
		<th>Asignada a</th>
		<th>Creada por</th>
		<th>Estado</th>
		<th>Resolución</th>
		<th>Prioridad</th>
	</tr>
	<c:forEach items="${issues}" var="issue">
		<tr>
			<td><c:out value="${issue.code}"/></td>
			<td><a href="view_issue?id=${issue.id}"><c:out value="${issue.title}"/></a></td>
			<td><fmt:formatDate type="date" value="${issue.creationDate}"/></td>
			<td><c:out value="${issue.assignee.username}"/></td>
			<td><c:out value="${issue.reporter.username}"/></td>
			<td><c:out value="${issue.state}"/></td>
			<td><c:out value="${issue.resolution}"/></td>
			<td><c:out value="${issue.priority}"/></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<br/>
<a href="<c:out value="${link}"/>"><c:out value="${link_message}"/></a>
</div>
<%@ include file="../footer.jsp"%>