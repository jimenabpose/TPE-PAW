<%@ include file="../header.jsp"%>

<c:out value="${message}" />
<em><c:out value="${error}"/></em>

<h2>Tarea</h2>

<table>
	<tr>
		<td><strong>C&oacute;digo:</strong></td>
		<td><c:out value="${issue.code}" /></td>
	</tr>
	<tr>
		<td><strong>T&iacute;tulo:</strong></td>
		<td><c:out value="${issue.title}" /></td>
	</tr>
	<tr>
		<td><strong>Descripci&oacute;n:</strong></td>
		<td><c:out value="${issue.description}" /></td>
	</tr>
	<tr>
		<td><strong>Fecha de creaci&oacute;n:</strong></td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" type="date"
			value="${issue.creationDate}" /></td>
	</tr>
	<tr>
		<td><strong>Tiempo estimado:</strong></td>
		<td><c:out value="${issue.estimatedTime}" /> <c:if
			test="${issue.estimatedTime != null}">hs</c:if></td>
	</tr>
	<tr>
		<td><strong>Asignado a:</strong></td>
		<td><c:out value="${issue.assignee.username}" /></td>
	</tr>
	<tr>
		<td><strong>Creada por:</strong></td>
		<td><c:out value="${issue.reporter.username}" /></td>
	</tr>
	<tr>
		<td><strong>Prioridad:</strong></td>
		<td><c:out value="${issue.priority.name}" /></td>
	</tr>
	<tr>
		<td><strong>Tipo:</strong></td>
		<td><c:out value="${issue.issueType.name}" /></td>
	</tr>
	<tr>
		<td><strong>Estado:</strong></td>
		<td><c:out value="${issue.state.name}" /></td>
	</tr>
	<tr>
		<td><strong>Resolución:</strong></td>
		<td><c:out value="${issue.resolution.name}" /></td>
	</tr>
	<tr>
		<td><strong>Versiones de resolucion:</strong></td>
		<td>
		<ul>
		<c:forEach var="version" items="${issue.resolutionVersions}">
 			<li><c:out value="${version.name}" />-releaseDate:
 				<fmt:formatDate pattern="dd/MM/yyyy" value="${version.releaseDate}"/>
 			</li>
 		</c:forEach>
 		</ul>
 		</td>
	</tr>
	<tr>
		<td><strong>Versiones afectadas:</strong></td>
		<td>
		<ul>
		<c:forEach var="version" items="${issue.affectedVersions}">
 			<li><c:out value="${version.name}" />-releaseDate:
 				<fmt:formatDate pattern="dd/MM/yyyy" value="${version.releaseDate}"/>
 			</li>
 		</c:forEach>
 		</ul>
 		</td>
	</tr>

</table>

<c:if test="${ id_for_resolution != null }">
	<div id="issue_resolution">Seleccione la resolución de la tarea <form:form
		action="resolve" method="post" commandName="resolveIssueForm">
		<form:select path="resolution">
			<br>
			<form:options items="${resolutions}" itemValue="description"
				itemLabel="name" />
		</form:select>
		<form:hidden path="issue" />
		<input type="hidden" name="id"
			value="<c:out value="${id_for_resolution }"/>">
		<input type="submit" value="Resolver">
	</form:form></div>
</c:if>

<!-- Para marcar la tarea como ONCOURSE o OPEN-->
<c:if test="${changeState == 1}">
	<c:if test="${isOnCourse == 0}">
		<a href="changeState?id=<c:out value="${issue.id}"/>">Cambiar
		estado a ONCOURSE</a>
	</c:if>
	<c:if test="${isOnCourse == 1}">
		<a href="changeState?id=<c:out value="${issue.id}"/>">Cambiar
		estado a OPEN</a>
	</c:if>
</c:if>

<!-- Para que el usuario se encargue de la tarea -->
<c:if test="${ id_for_asignation != null }">
	<div id="asignation"><a
		href="asign?id=<c:out value="${id_for_asignation}"/>">Asignarme
	esta tarea</a></div>
</c:if>

<!-- Para que el lider cierre la tarea la tarea -->
<c:if test="${ id_for_closing_issue != null }">
	<div id="asignation"><a
		href="close?id=<c:out value="${id_for_closing_issue}"/>">Cerrar
	esta tarea</a></div>
</c:if>

<!-- Para editar la tarea -->
	<div id="edition"><a href="edit?id=<c:out value="${issue.id}"/>">Editar
	esta tarea</a></div>

<tr>
	<fieldset>
	<c:choose>
		<c:when test="${empty issue.changes}">
			<h3>Esta tarea no tiene modificaciones</h3>
		</c:when>
		<c:otherwise>
			<h3>Modificaciones:</h3>
				<table border="1">
						<thead>
							<tr>
								<th>Ususario</th>
								<th>Fecha y hora</th>
								<th>Campo</th>
								<th>Valor Anterior</th>
								<th>Valor Nuevo</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${issue.changes}" var="change">
								<tr>
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

<tr>
<fieldset>
<div id="issue_relation"><h3>Seleccione la relación para la tarea</h3> 
	<form:form action="relate" method="post" commandName="relateIssueForm">
		<form:select path="relation">
			<form:options items="${relations}" itemValue="description" itemLabel="name" />
		</form:select>
		<form:select path="relatedIssue">
			<form:options items="${issues}" itemValue="id" itemLabel="title" />
		</form:select>
		<form:hidden path="issue" />
		<input type="submit" value="Relacionar">
		<em><form:errors path="relationError"/></em>
	</form:form>
</div>


	<c:choose>
		<c:when test="${empty issue.dependsOn && empty issue.necessaryFor &&
			empty issue.relatedWith && empty issue.duplicatedWith}">
			<h3>Esta tarea no tiene relaciones</h3>
		</c:when>
		<c:otherwise>
			<c:if test="${!empty issue.dependsOn}">
			
				<td>
				<H3>Tareas de las que depende:</H3>
				</td>
				<table border="1">
					<thead>
						<tr>
							<th>Tarea</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${issue.dependsOn}" var="relIssue">
							<tr>
								<td><a href="view?id=${relIssue.id}"><c:out value="${relIssue.title}"/></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${!empty issue.necessaryFor}">
				<td>
				<H3>Tareas para las que es necesaria:</H3>
				</td>
				<table border="1">
					<thead>
						<tr>
							<th>Tarea</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${issue.necessaryFor}" var="relIssue">
							<tr>
								<td><a href="view?id=${relIssue.id}"><c:out value="${relIssue.title}"/></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${!empty issue.relatedWith}">
				<td>
				<H3>Tareas con las que esta relacionada:</H3>
				</td>
				<table border="1">
					<thead>
						<tr>
							<th>Tarea</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${issue.relatedWith}" var="relIssue">
							<tr>
								<td><a href="view?id=${relIssue.id}"><c:out value="${relIssue.title}"/></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<c:if test="${!empty issue.duplicatedWith}">
				<td>
				<H3>Tareas con las que esta duplicada:</H3>
				</td>
				<table border="1">
					<thead>
						<tr>
							<th>Tarea</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${issue.duplicatedWith}" var="relIssue">
							<tr>
								<td><a href="view?id=${relIssue.id}"><c:out value="${relIssue.title}"/></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</c:otherwise>
	</c:choose>
</fieldset>
</tr>


<tr>
<fieldset>
	<c:choose>
		<c:when test="${empty voters}">
			<td>
			<H3>No hay votos emitidos sobre esta tarea</H3>
			</td>
		</c:when>
		<c:otherwise>
			<td>
			<H3>Votos:</H3>
			</td>
			<table border="1">
				<thead>
					<tr>
						<th>Usuario</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${voters}" var="voter">
						<tr>
							<td><c:out value="${voter.username}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${user != null}">
		<c:choose>
			<c:when test="${!hasVoted}">
				<div id="removeVoter">
					<a href="addVoter?id=<c:out value="${issue.id}"/>">
						Votar</a>
						<em><c:out value="${votingError}"/></em>
				</div>
			</c:when>
			<c:otherwise>
				<div id="addVoter">
					<a href="removeVoter?id=<c:out value="${issue.id}"/>">
					Eliminar mi voto</a>
				</div>
			</c:otherwise>
		</c:choose>
	</c:if>
</fieldset>
</tr>


<tr>
<fieldset>
	<c:choose>
		<c:when test="${empty jobs}">
			<td>
			<H3>No hay trabajos registrados en esta tarea</H3>
			</td>
		</c:when>
		<c:otherwise>
			<td>
			<H3>Trabajos registrados:</H3>
			</td>
			<table border="1">
				<thead>
					<tr>
						<th>Usuario</th>
						<th>Fecha y hora</th>
						<th>Tiempo dedicado</th>
						<th>Descripción</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${jobs}" var="job">
						<tr>
							<td><c:out value="${job.user.username}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"
								value="${job.date}" /></td>
							<td><c:out value="${job.elapsedTime}" /></td>
							<td><c:out value="${job.description}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>

		<h3>Registrar un nuevo trabajo</h3>
		<form:form action="addJob" method="POST" commandName="jobForm">
			<table>
				<tr>
					<td><label>Tiempo dedicado:</label> <form:input
						path="elapsedTime" maxlength="8" /><label>hs</label> <em><form:errors
						path="elapsedTime" /></em></td>
				</tr>
				<tr>
					<td><label>Descripción:</label>
					<form:textarea path="description" maxlength="250" rows="2"
						cols="20"></form:textarea> <em><form:errors
						path="description" /></em></td>
				</tr>
				<tr>
					<td>
					<div class="form-buttons"><input type="submit"
						value="Publicar" /></div>
					</td>
					<form:hidden path="issue" />
				</tr>
			</table>
		</form:form>
</fieldset>
</tr>


<tr>
<fieldset>
	<c:choose>
		<c:when test="${empty comments}">
			<td>
			<H3>No hay comentarios sobre esta tarea</H3>
			</td>
		</c:when>
		<c:otherwise>
			<td>
			<H3>Comentarios:</H3>
			</td>
			<table border="1">
				<thead>
					<tr>
						<th>Usuario</th>
						<th>Comentario</th>
						<th>Fecha</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${comments}" var="comment">
						<tr>
							<td><c:out value="${comment.user.username}" /></td>
							<td><c:out value="${comment.text}" /></td>
							<td><fmt:formatDate pattern="dd/MM/yyyy"
								value="${comment.creationDate}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>

		<h3>Publicar un nuevo comentario</h3>
		<form:form action="addComment" method="POST" commandName="commentForm">
			
			<div class="form-field">
			<label>Comentario:</label> <form:textarea path="text" maxlength="250"
				rows="2" cols="20"></form:textarea> <em><form:errors
				path="text" /></em></div>
			<div class="form-buttons"><input type="submit" value="Publicar" />
			</div>
			<form:hidden path="issue" />
		</form:form>
</fieldset>
</tr>


<%@ include file="../footer.jsp"%>