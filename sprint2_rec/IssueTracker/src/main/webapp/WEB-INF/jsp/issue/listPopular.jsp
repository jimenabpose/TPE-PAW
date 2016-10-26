<%@ include file="../header.jsp"%>
<div id="content">
<h2>Listado de tareas populares</h2>
<p><c:out value="${message}" /></p>
<c:if test="${message == null}">

<div id="issue_resolution">
	Seleccione un rango para mostrar las mejores tareas
	<form:form action="listPopular" method="post" commandName="rangeForm">
		<form:select path="range"><br>
		    <form:options items="${ranges}" itemValue="description" itemLabel="name"/>
	    </form:select>
		<input type="submit" value="Mostrar">
		</form:form>
</div>

<table id="list">
	<tr>
		<th>Código</th>
		<th>Título</th>
		<th>Cantidad de accesos</th>
	</tr>
	<c:forEach items="${issueAccesses}" var="access">
		<tr>
			<td><a href="view?id=${access.issue.id}"><c:out value="${access.issue.code}"/></a></td>
			<td><c:out value="${access.issue.title}"/></td>
			<td><c:out value="${access.quantity}"/></td>
		</tr>
	</c:forEach>
</table>
	
</c:if>
<br/>
</div>
<%@ include file="../footer.jsp"%>