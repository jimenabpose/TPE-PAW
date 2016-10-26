<%@ include file="../header.jsp"%>
<div id="content">
		<c:if test="${createIssueForm.id == -1}">
			<h2>Reportar una nueva tarea</h2>
		</c:if>
		<c:if test="${createIssueForm.id != -1}">
			<h2>Editar la tarea</h2>
		</c:if>
		<form:form action="create" method="post" commandName="createIssueForm">
			<fieldset>
				<br>
				<c:if test="${createIssueForm.id == -1}">
					<legend>Ingrese los siguientes datos para reportar la nueva tarea</legend>
				</c:if>
				<c:if test="${createIssueForm.id != -1}">
					<legend>Edite los datos que desee</legend>
				</c:if>
				<ul>
					<li>
						<label>Título<em>*</em></label> 
						<form:input path="title" maxlength="30"/><br/>
						<em><form:errors path="title"/></em>
					</li>
					
					<li>
					<!-- TODO FALTA CHEQUEAR EL MAXLENGTH -->
						<label>Descripción</label>
						<form:textarea path="description" maxlength="250" rows="2" cols="20"></form:textarea>
						<em><form:errors path="description"/></em>
					</li>
					
					<li>
						<label>Tiempo Estimado</label> 
						<form:input path="estimatedTime" maxlength="4"/><label>hs</label><br/>
						<em><form:errors path="estimatedTime"/></em>
					</li>
					
					<li>
						<label>Asignada a</label> 
						<form:select path="assignee"><br>
							<form:option value="${-1}" label="No asignar"></form:option>
                            <form:options items="${users}" itemValue="id" itemLabel="completeName"/>
                        </form:select>
					</li>
					<li>
						<c:if test="${createIssueForm.id == -1}">
							<label>Prioridad <em>*</em></label> 
							<form:select path="priority"><br>
	                             <form:options items="${priorities}" itemValue="description" itemLabel="description"/>
                       		 </form:select>
						</c:if>
						<c:if test="${createIssueForm.id != -1}">
							<form:hidden path="priority" />
						</c:if>
						
						<form:hidden path="id" />
						<form:hidden path="creationDate" />
						<form:hidden path="reporter" />
						<form:hidden path="state" />
						<form:hidden path="resolution" />
						<form:hidden path="project" />
						
						<em>Campos requeridos *</em>
						<input type="submit" value="Aceptar" />
					</li>
				</ul>
			</fieldset>
		</form:form>
</div>
<%@ include file="../footer.jsp"%>