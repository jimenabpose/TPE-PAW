<%@ include file="../header.jsp"%>
<form:form action="create" method="post" commandName="createProjectForm">
	<h2>Ingrese los datos del proyecto</h2>
	<table>
		<tr>
			<td>Nombre<em>*</em></td>
			<td><form:input path="name" maxlength="20"/></td>
			<td><em><form:errors path="name"/></em></td>
		</tr>
		
		<tr>
			<td>Código<em>*</em></td>
			<td><form:input path="code" maxlength="20"/></td>
			<td><em><form:errors path="code"/></em></td>
		</tr>
		
		<tr>
			<td>Descripción</td>
			<td><form:input path="description" maxlength="256"/></td>
			<td></td>
		</tr>
		
		<tr>
			<td>Lider</td>
			<td>
				<form:select path="user">
					<form:options items="${users}" itemValue="id"
						itemLabel="completeName" />
				</form:select>
			</td>
		</tr>
		
		<tr>
			<td>Público</td>
			<td><form:checkbox path="is_public"/></td>
		</tr>
		
		<tr>
			<td></td>
			<td><input type="submit" value="Aceptar" /></td>
		</tr>
		<form:hidden path="project"/>
	</table>
</form:form>
<%@ include file="../footer.jsp"%>