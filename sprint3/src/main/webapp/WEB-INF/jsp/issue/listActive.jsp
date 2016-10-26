<%@ include file="../header.jsp"%>
<div id="content">
<h2>Listado de tareas</h2>
<p><c:out value="${message}" /></p>
<c:if test="${message == null}">
<table id="list">
	<tr>
		<th>C�digo</th>
		<th>T�tulo</th>
		<th>Fecha de creaci�n</th>
		<th>Asignada a</th>
		<th>Creada por</th>
		<th>Estado</th>
		<th>Resoluci�n</th>
		<th>Prioridad</th>
		<th>Tipo</th>
	</tr>
	<c:forEach items="${issues}" var="issue">
		<tr>
			<td><c:out value="${issue.code}"/></td>
			<td><a href="view?id=${issue.id}"><c:out value="${issue.title}"/></a></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" value="${issue.creationDate}"/></td>
			<td><c:out value="${issue.assignee.username}"/></td>
			<td><c:out value="${issue.reporter.username}"/></td>
			<td><c:out value="${issue.state.name}"/></td>
			<td><c:out value="${issue.resolution.name}"/></td>
			<td><c:out value="${issue.priority.name}"/></td>
			<td><c:out value="${issue.issueType.name}"/></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
<br/>
<a href="<c:out value="${link}"/>"><c:out value="${link_message}"/></a>
</div>
<%@ include file="../footer.jsp"%>