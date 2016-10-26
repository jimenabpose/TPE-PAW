<%@ include file="../header.jsp"%>

<c:out value="${message}" />

<h2>Proyecto</h2>

<table>
	<tr>
		<td><strong>C&oacute;digo:</strong></td>
		<td><c:out value="${project.code}" /></td>
	</tr>
	<tr>
		<td><strong>Nombre:</strong></td>
		<td><c:out value="${project.name}" /></td>
	</tr>
	<tr>
		<td><strong>Descripción:</strong></td>
		<td><c:out value="${project.description}" /></td>
	</tr>
	<tr>
		<td><strong>Lider:</strong></td>
		<td><c:out value="${project.leader.username}" /></td>
	</tr>
	<tr>
		<td><strong>Público:</strong></td>
		<td><c:if test="${project.public}"><c:out value="Sí"/></c:if></td>
		<td><c:if test="${!project.public}"><c:out value="No"/></c:if></td>
	</tr>
</table>


<p>Participantes del proyecto</p>
<li><c:forEach items="${users}" var="user">
	<ul>
		<c:out value="${user.username}" />
		<c:out value="${user.firstName}" />
		<c:out value="${user.lastName}" />
	</ul>
</c:forEach>
<li>
<p><a href="createVersion">Crear version del proyecto</a></p>
<p><a href="listVersions">Listar versiones del proyecto</a></p>
<p><a href="consultState">Consultar estado del proyecto</a></p>
<p><a href="listUsers">Seleccionar participantes del proyecto</a></p>


<tr>
	<fieldset>
	<c:choose>
		<c:when test="${empty changes}">
			<h3>Las tareas de este proyecto no tienen modificaciones</h3>
		</c:when>
		<c:otherwise>
			<h3>Últimas 5 modificaciones en las tareas del proyecto:</h3>
				<table border="1">
						<thead>
							<tr>
								<th>Tarea</th>
								<th>Ususario</th>
								<th>Fecha y hora</th>
								<th>Campo</th>
								<th>Valor Anterior</th>
								<th>Valor Nuevo</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${changes}" var="change">
								<tr>
									<td><c:out value="${change.issue.title}"></c:out></td>
									<td><c:out value="${change.changer}" /></td>
									<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"
										value="${change.changeDate}" /></td>
									<td><c:out value="${change.field}" /></td>
									<td><c:out value="${change.oldValue}" /></td>
									<td><c:out value="${change.newValue}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
			</c:otherwise>
		</c:choose>
	</fieldset>
</tr>
