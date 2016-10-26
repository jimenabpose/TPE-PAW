<%@ include file="../header.jsp"%>
<div id="content">
<span>Aclaración:</span> las fechas son de la forma AAAA-MM-DD. Ej: 2011-05-01.
<h2>Listado de tareas</h2>
<p><c:out value="${message}" /></p>
<c:if test="${message == null}">

<c:if test="${am_i_leader == true}">
<div id="job_report" >
<h4>Reporte de trabajo</h4>
<form:form action="list" method="post" commandName="job_report">
		Usuario:	<form:select path="user">				
						<form:options items="${all_users}" itemValue="id"
							itemLabel="completeName" />
					</form:select>
		
Desde:<form:input path="st" maxlength="10"/>

Hasta:<form:input path="et" maxlength="10"/>

<!-- <input type="submit" value="Procesar" />		 -->
</form:form>
</div>

</c:if>

<div id="issue_filter">
<form:form action="list" method="post" commandName="issue_filter">

	<div id="issue_code_filter" class="filter_field">
	Código:<form:input path="code" maxlength="30"/>
	</div>

	<div id="issue_title_filter" class="filter_field">
	Título:<form:input path="title" maxlength="30"/>
	</div>

	<div id="issue_description_filter" class="filter_field">
	Descripción:<form:input path="description" maxlength="256"/>
	</div>
<div id="issue_reporter_filter" class="filter_field">
		Reportado por:	<form:select path="reporter">
						
						<form:options items="${users}" itemValue="id"
							itemLabel="completeName" />
					</form:select>
</div>
<div id="issue_asignee_filter" class="filter_field">
		Asignado a:	<form:select path="asignee">
						
						<form:options items="${users}" itemValue="id"
							itemLabel="completeName" />
					</form:select>
</div>

<div id="issue_state_filter" class="filter_field">
		Estado:	<form:select path="state">
						
						<form:options items="${states}"  />
				</form:select>
</div>

<div id="issue_state_filter" class="filter_field">
		Resolución:	<form:select path="resolution">
						<form:options items="${resolutions}"  />
				</form:select>
</div>

<div id="issue_st_creation_filter" class="filter_field">
Desde:<form:input path="st" maxlength="10"/>
</div>

<div id="issue_et_creation_filter" class="filter_field">
Hasta:<form:input path="et" maxlength="10"/>
</div>

<div class="filter_field">
<input type="submit" value="Buscar" />
</div>
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
	</tr>
	<c:forEach items="${issues}" var="issue">
		<tr>
			<td><c:out value="${issue.code}"/></td>
			<td><a href="view?id=${issue.id}"><c:out value="${issue.title}"/></a></td>
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
<a href="listActive">Ver mis tareas activas</a>
</div>
<%@ include file="../footer.jsp"%>