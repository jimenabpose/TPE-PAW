<%@ include file="../header.jsp"%>
<div id="content">

<table>
	<tr>
		<td><strong>Nombre:</strong></td>
		<td><c:out value="${version.name}" /></td>
	</tr><tr>
		<td><strong>Estado:</strong></td>
		<td><c:out value="${version.state.name}" /></td>
	</tr><tr>
		<td><strong>Fecha de liberación:</strong></td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${version.releaseDate}"/></td>
	</tr>
	
	<c:forEach items="${states}" var="item">
		<tr>
			<td><strong><c:out value='Tareas en estado ${item.key}:'/></strong></td>
			<td><c:out value='${item.value}'/></td>
		</tr>
	</c:forEach>
	<tr>
		<td><strong>Tiempo Total Estimado:</strong></td>
		<td><c:out value='${tte} hs'/></td>
	</tr>
	<tr>
		<td><strong>Tiempo Total Trabajado:</strong></td>
		<td><c:out value='${ttt} hs'/></td>
	</tr>
	<tr>
		<td><strong>Tiempo Estimado para finalizar version:</strong></td>
		<td><c:out value='${tefv} hs'/></td>
	</tr>
</table>
<br>
<a href="versionNotas?id=<c:out value="${version.id}"/>">Notas de la versión</a>

</div>
<%@ include file="../footer.jsp"%>