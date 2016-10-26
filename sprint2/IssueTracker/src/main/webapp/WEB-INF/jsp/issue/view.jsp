<%@ include file="../header.jsp"%>

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


<%-- <c:if test="${ id_for_resolution != null }"> --%>
<!-- 	<div id="issue_resolution"> -->
<!-- 		Seleccione la resolución de la tarea -->
<!-- 		<form action="resolve" method=get> -->
<!-- 			<select name="resolution_sel" width="20"> -->
<%-- 				<c:forEach var="item" items="${list_for_resolution}"> --%>
<%-- 					<option value="<c:out value="${item}" />"> --%>
<%-- 						<c:out value="${item}" /> --%>
<!-- 					</option> -->
<%-- 				</c:forEach> --%>
<!-- 			</select> -->
<%-- 			<input type="hidden" name="id_for_resolution" value="<c:out value="${id_for_resolution }"/>"> --%>
<!-- 			<input type="submit" value="Resolver"> -->
<!-- 		</form> -->
<!-- 	</div> -->
<%-- </c:if> --%>

<c:if test="${ id_for_resolution != null }">
	<div id="issue_resolution">
		Seleccione la resolución de la tarea
		<form:form action="resolve" method="post" commandName="resolveIssueForm">
			<form:select path="resolution"><br>
		    	<form:options items="${resolutions}" itemValue="description" itemLabel="description"/>
	        </form:select>
			<input type="hidden" name="id" value="<c:out value="${id_for_resolution }"/>">
			<input type="submit" value="Resolver">
		</form:form>
	</div>
</c:if>

<!-- Para marcar la tarea como ONCOURSE o OPEN-->
<c:if test="${changeState == 1}">
	<c:if test="${isOnCourse == 0}">
	<a href="changeState?id=<c:out value="${issue.id}"/>">Cambiar estado a ONCOURSE</a>
	</c:if>
	<c:if test="${isOnCourse == 1}">
	<a href="changeState?id=<c:out value="${issue.id}"/>">Cambiar estado a OPEN</a>
	</c:if>
</c:if>

<!-- Para que el usuario se encargue de la tarea -->
<c:if test="${ id_for_asignation != null }">
	<div id="asignation">
		<a href="asign?id=<c:out value="${id_for_asignation}"/>">Asignarme esta tarea</a>
	</div>
</c:if>

<!-- Para que el lider cierre la tarea la tarea -->
<c:if test="${ id_for_closing_issue != null }">
	<div id="asignation">
		<a href="close?id=<c:out value="${id_for_closing_issue}"/>">Cerrar esta tarea</a>
	</div>
</c:if>

<!-- Para editar la tarea -->
<div id="edition">
	<a href="edit?id=<c:out value="${issue.id}"/>">Editar esta tarea</a>
</div>


<tr>
<c:choose>
	<c:when test="${empty jobs}">
		<td><H3>No hay trabajos registrados en esta tarea</H3></td>
	</c:when>
	<c:otherwise>
		<td><H3>Trabajos registrados:</H3></td>
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
							<td>
								<c:out value="${job.user.username}" />
							</td>
							<td>
								<c:out value="${job.date}" />
							</td>
							<td>
								<c:out value="${job.elapsedTime}" />
							</td>
							<td>
								<c:out value="${job.description}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</c:otherwise>
</c:choose>

<h3>Registrar un nuevo trabajo</h3>
<form:form action="addJob" method="POST" commandName="jobForm">
	<fieldset>
		<table>
			<tr>
				<td>
				<label>Tiempo dedicado:</label>
				<form:input path="elapsedTime" maxlength="8"/><label>hs</label>
				<em><form:errors path="elapsedTime"/></em>
				</td>
			</tr>
			<tr>
				<td>
				<!-- TODO FALTA CHEQUEAR EL MAXLENGTH -->
				<label>Descripción:</label>
				<form:textarea path="description" maxlength="250" rows="2" cols="20"></form:textarea>
				<em><form:errors path="description"/></em>
				</td>
			</tr>
			<tr>
				<td>
				<div class="form-buttons">
					<input type="submit" value="Publicar" />
				</div>
				</td>
				<input type = "hidden" value = ${issue.id} name = "issueId"/>
			</tr>
		</table>
	</fieldset>
</form:form>

</tr>


<tr>
<c:choose>
	<c:when test="${empty comments}">
		<td><H3>No hay comentarios sobre esta tarea</H3></td>
	</c:when>
	<c:otherwise>
		<td><H3>Comentarios:</H3></td>
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
							<td>
								<c:out value="${comment.user.username}" />
							</td>
							<td><c:out value="${comment.text}" /></td>
							<td><fmt:formatDate value="${comment.creationDate}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</c:otherwise>
</c:choose>

<h3>Publicar un nuevo comentario</h3>
<form:form action="addComment" method="POST" commandName="commentForm">
	<fieldset>
		<div class="form-field">
			<!-- TODO FALTA CHEQUEAR EL MAXLENGTH -->
			<label>Comentario:</label>
			<form:textarea path="text" maxlength="250" rows="2" cols="20"></form:textarea>
			<em><form:errors path="text"/></em>
		</div>
		<div class="form-buttons">
			<input type="submit" value="Publicar" />
		</div>
		<input type = "hidden" value = ${issue.id} name = "issueId"/>
	</fieldset>
</form:form>

</tr>



<%@ include file="../footer.jsp"%>