<%@ include file="../header.jsp"%>
<div id="content">
<strong>Aclaración:</strong> las fechas son de la forma AAAA-MM-DD. Ej: 2011-05-01.

<div id="issue_filter">
<form:form action="sumJobReport" method="post" commandName="jobReportForm">
	
<div id="issue_reporter_filter" class="filter_field">
		Usuario:	<form:select path="user">
						<form:options items="${users}" itemValue="id"
							itemLabel="completeName" />
					</form:select>
</div>

<div id="issue_st_creation_filter" class="filter_field">
Desde:<form:input path="date_st" maxlength="10"/>
</div>

<div id="issue_et_creation_filter" class="filter_field">
Hasta:<form:input path="date_et" maxlength="10"/>
</div>

<div class="filter_field">
<input type="submit" value="Generar Reporte" />
</div>


<div class="filter_field" style="width:100%">
	<strong><em><form:errors path="*"/></em></strong>
</div>
</form:form>

<c:if test="${duration_reported != null}">
<br/>
<h3>La cantidad de horas es reportadas para el usuario son: <c:out value="${duration_reported}"/></h3>
</c:if>


<%@ include file="../footer.jsp"%>