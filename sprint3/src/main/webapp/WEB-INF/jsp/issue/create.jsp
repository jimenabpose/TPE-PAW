<%@ include file="../header.jsp"%>
<div id="content">
		<c:if test="${createIssueForm.issue == null}">
			<h2>Reportar una nueva tarea</h2>
		</c:if>
		<c:if test="${createIssueForm.issue != null}">
			<h2>Editar la tarea</h2>
		</c:if>
		<form:form action="create" method="post" commandName="createIssueForm">
			<fieldset>
				<br>
				<c:if test="${createIssueForm.issue == null}">
					<legend>Ingrese los siguientes datos para reportar la nueva tarea</legend>
				</c:if>
				<c:if test="${createIssueForm.issue != null}">
					<legend>Edite los datos que desee</legend>
				</c:if>
				<ul>
					<li>
						<label>Título<em>*</em></label> 
						<form:input path="title" maxlength="30"/><br/>
						<em><form:errors path="title"/></em>
					</li>
					
					<li>
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
							<form:option value="" label="No asignar"></form:option>
                            <form:options items="${users}" itemValue="id" itemLabel="completeName"/>
                        </form:select>
					</li>
						<c:if test="${createIssueForm.issue == null}">
						<li>
							<label>Prioridad <em>*</em></label> 
							<form:select path="priority"><br>
	                             <form:options items="${priorities}" itemValue="description" itemLabel="name"/>
                       		 </form:select>
						</li>
						</c:if>
						<c:if test="${createIssueForm.issue != null}">
							<form:hidden path="priority" />
						</c:if>
					<li>
						<label>Tipo</label> 
							<form:select path="issueType"><br>
								<form:option value="" label="Ninguno"></form:option>
	                             <form:options items="${issueTypes}" itemValue="description" itemLabel="name"/>
                       		 </form:select>
					</li>
					<li>
						<label>Versiones de resolucion </label>
							<form:select path="resolutionVersions"><br>
	                             <form:options items="${versions}" itemValue="id" itemLabel="name"/>
                       		 </form:select>
					</li>
					<li>
						<label>Versiones afectadas </label>
														<form:select path="affectedVersions"><br>
	                             <form:options items="${versions}" itemValue="id" itemLabel="name"/>
                       		 </form:select>

					</li>
						
						<form:hidden path="issue" />
						<form:hidden path="reporter" />
						<form:hidden path="project" />
						<form:hidden path="loggedUser" />
						<br></br>
						<input type="submit" value="Aceptar" />
						<em>Campos requeridos *</em>
				</ul>
			</fieldset>
		</form:form>
</div>
<%@ include file="../footer.jsp"%>