<%@ include file="../header.jsp"%>
<div id="content">
<span>Aclaración:</span> las fechas son de la forma AAAA-MM-DD. Ej: 2011-05-01.
<h2>Listado de tareas</h2>
<p><c:out value="${message}" /></p>
<c:if test="${message == null}">

<div id="issue_filter">
<form:form action="list" method="post" commandName="issueFilter">
	
<fieldset>
<label><h3>Buscar por:</h3></label>
<table>
	<tr>
		<td>Código</td>
		<td><form:input path="code" maxlength="30"/></td>
		<td>Título:</td>
		<td><form:input path="title" maxlength="30"/></td>
		<td>Descripción:</td>
		<td><form:input path="description" maxlength="256"/></td>
	</tr>
	<tr>
		<td>Reportado por:</td>
		<td>
			<form:select path="reporter">
				<form:option value="" label="Todos"></form:option>
				<form:options items="${users}" itemValue="id"
					itemLabel="completeName" />
			</form:select>
		</td>
		<td>Asignado a:</td>
		<td>
			<form:select path="asignee">
				<form:option value="" label="Todos"></form:option>
				<form:options items="${users}" itemValue="id"
					itemLabel="completeName" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td>Estado:</td>
		<td>
			<form:select path="state"><br>
				<form:option value="" label="Todos"></form:option>
	            <form:options items="${states}" itemValue="description" itemLabel="name"/>
	        </form:select>
		</td>
		<td>Resolución:</td>
		<td>	
			<form:select path="resolution"><br>
				<form:option value="" label="Todas"></form:option>
	            <form:options items="${resolutions}" itemValue="description" itemLabel="name"/>
	        </form:select>
		</td>
		<td>Tipo:</td>
		<td>	
			<form:select path="issueType"><br>
				<form:option value="" label="Todos"></form:option>
	            <form:options items="${issueTypes}" itemValue="description" itemLabel="name"/>
	        </form:select>
		</td>
	</tr>
	<tr>
		<td>Versiones de resolucion:</td>
		<td>	
			<form:select path="resolutionVersion"><br>
				<form:option value="" label="Todas"></form:option>
	            <form:options items="${resolutionVersions}" itemValue="id" itemLabel="name"/>
	        </form:select>
		</td>
		<td>Versiones afectadas:</td>
		<td>	
			<form:select path="affectedVersion"><br>
				<form:option value="" label="Todas"></form:option>
	            <form:options items="${affectedVersions}" itemValue="id" itemLabel="name"/>
	        </form:select>
		</td>
	</tr>
	<tr>
		<td>Desde:</td>
		<td><form:input path="date_st" maxlength="10"/></td>
		<td>Hasta:</td>
		<td><form:input path="date_et" maxlength="10"/></td>
	</tr>
</table>

<div class="filter_field">
<input type="submit" value="Buscar" />
</div>

<div class="filter_field" style="width:100%">
	<strong><em><form:errors path="*"/></em></strong>
</div>
</fieldset>
</form:form>
</div>

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
		<th>Tipo</th>
	</tr>
	<c:forEach items="${issues}" var="issue">
		<tr>
			<td><c:out value="${issue.code}"/></td>
			<td><a href="view?id=${issue.id}"><c:out value="${issue.title}"/></a></td>
			<td><fmt:formatDate pattern="dd/MM/yyyy" type="date" value="${issue.creationDate}"/></td>
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
<a href="listActive">Ver mis tareas activas</a>
<c:if test="${am_i_leader == true}">
<br/>
<a href="sumJobReport">Ver Reporte de horas trabajadas</a>
</c:if>

</div>
<%@ include file="../footer.jsp"%>