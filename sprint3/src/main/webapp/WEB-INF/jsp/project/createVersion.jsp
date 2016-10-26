<%@ include file="../header.jsp"%>
<form:form action="createVersion" method="post" commandName="createVersionForm">
	<h2>Ingrese los datos de la version</h2>
	<p>Aclaración: las fechas son de la forma AAAA-MM-DD. Ej: 2011-05-01. </p>
	<table>
		<tr>
			<td>Nombre<em>*</em></td>
			<td><form:input path="verName" maxlength="20"/></td>
			<td><em><form:errors path="verName"/></em></td>
		</tr>
		
		<tr>
			<td>Fecha estimada de liberación<em>*</em></td>
			<td><form:input path="verDate" maxlength="20"/></td>
			<td><em><form:errors path="verDate"/></em></td>
		</tr>
		
		<tr>
			<td>Descripción</td>
			<td><form:input path="verDescription" maxlength="256"/></td>
			<td><em><form:errors path="verDescription"/></em></td>
			<td></td>
		</tr>
		
		<tr>
			<c:if test="${createVersionForm.version != null}">
				<td>Estado<em>*</em></td>
			<td>
				<form:select path="verState">
					<form:options items="${states}" itemValue="description"
						itemLabel="name" />
				</form:select>
			</td>
			</c:if>
			<c:if test="${createVersionForm.version == null}">
				<form:hidden path="verState" />
			</c:if>
		</tr>
		<tr>
			<td><em>Campos obligatorios *</em></td>
			<td><input type="submit" value="Aceptar" /></td>
		</tr>
		<form:hidden path="version"/>
	</table>
</form:form>
<%@ include file="../footer.jsp"%>