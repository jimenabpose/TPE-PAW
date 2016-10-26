<%@ include file="header.jsp"%>
<div>
	<div id="content">
			<c:if test="${editingProject == false}">
				<h2>Crear un nuevo proyecto</h2>
			</c:if>
			<c:if test="${editingProject == true}">
				<h2>Editar el proyecto</h2>
			</c:if>
			<form action="create_project" method="post">
				<fieldset>
					<c:forEach items="${errors}" var="error">
						<p>
							<em><c:out value="${error}" /></em>
						</p>
					</c:forEach>
					<br>
					<c:if test="${editingProject == false}">
						<legend>Ingrese los siguientes datos para crear el nuevo proyecto</legend>
					</c:if>
					<c:if test="${editingProject == true}">
						<legend>Ingrese los siguientes datos para editar el proyecto</legend>
					</c:if>
					<ul>
						<li>
							<label>Nombre<em>*</em></label>
									<input type="text" name="name" maxlength=20 value="${name}" />
							<br/>
						</li>
						<li>
							<label>Código<em>*</em></label> 
								<input type="text" name="code" maxlength=10 value="${code}"/>
							<br/>
						</li>
						<li>
						<!-- TODO FALTA CHEQUEAR EL MAXLENGTH -->
							<label>Descripción</label>
								<textarea rows="2" cols="20" name="description">${description}</textarea>
							<br/>
						</li>
						<li>
							<label>Lider<em>*</em></label> 
							<select name="leader"><br>
								<c:set var="it" value="1"/>
								<c:forEach items="${users}" var="user">
										<option value="${user.id}" <c:if test="${leader.id == user.id}"> selected="selected"</c:if>>
										<c:out value="${user.completeName}" />
										</option>
									<c:set var="it" value="${it + 1 }"/>
								</c:forEach>
							</select><br>
							
							<em>Campos requeridos *</em>
							<input type="submit" value="Aceptar" />
						</li>
					</ul>
				</fieldset>
			</form>
	</div>
</div>
<%@ include file="footer.jsp"%>