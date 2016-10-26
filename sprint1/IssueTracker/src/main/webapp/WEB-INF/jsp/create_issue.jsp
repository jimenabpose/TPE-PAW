<%@ include file="header.jsp"%>
<div id="content">
		<c:if test="${editingIssue == false}">
			<h2>Reportar una nueva tarea</h2>
		</c:if>
		<c:if test="${editingIssue == true}">
			<h2>Editar la tarea</h2>
		</c:if>
		<form action="create_issue" method="post">
			<fieldset>
				<c:forEach items="${errors}" var="error">
					<p>
						<em><c:out value="${error}" /></em>
					</p>
				</c:forEach>
				<br>
				<c:if test="${editingIssue == false}">
					<legend>Ingrese los siguientes datos para reportar la nueva tarea</legend>
				</c:if>
				<c:if test="${editingIssue == true}">
					<legend>Edite los datos que desee</legend>
				</c:if>
				<ul>
					<li>
						<label>Título<em>*</em></label> 
						<input type="text" name="title" maxlength=30 value="${title}" />
						<br/>
					</li>
					<li>
					<!-- TODO FALTA CHEQUEAR EL MAXLENGTH -->
						<label>Descripción</label>
						<textarea rows="2" cols="20" name="description" maxlength=250>${description}</textarea>
						<br/>
					</li>
					<li>
						<label>Tiempo Estimado</label> 
						<input type="text" name="estimatedTime" maxlength=4 value="${estimatedTime}" />
						<label>hs</label>
						<br />
					</li>
					<li>
						<label>Asignada a</label> 
						<select name="assignee"><br>
							<c:set var="it" value="1"/>
							<option value="${-1}" <c:if test="${assignee.id == -1}"> selected="selected"</c:if>>
								<c:out value="No asignar" />
							</option>
							<c:forEach items="${users}" var="user">
								<option value="${user.id}" <c:if test="${assignee.id == user.id}"> selected="selected"</c:if>>
								<c:out value="${user.completeName}" />
								</option>
								<c:set var="it" value="${it + 1 }"/>
							</c:forEach>
						</select><br>
					</li>
					<li>
						<c:if test="${editingIssue == false}">
							<label>Prioridad <em>*</em></label> 
							
						    <select name="priority"><br>
								<c:set var="it" value="1"/>
								<c:forEach items="${priorities}" var="priority">
									<option <c:if test="${selectedPriority == priority}"> selected="selected"</c:if>>
									<c:out value="${priority}" />
									</option>
									<c:set var="it" value="${it + 1 }"/>
								</c:forEach>
							</select><br>
						</c:if>
						
						<input type="hidden" name="editingIssue" value="${editingIssue}" />
						
						<em>Campos requeridos *</em>
						<input type="submit" value="Aceptar" />
					</li>
				</ul>
			</fieldset>
		</form>
</div>
<%@ include file="footer.jsp"%>