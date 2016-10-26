<%@ include file="../header.jsp"%>
	<div id="content">
		<h2>Registración</h2>
		<form:form action="register" method="post" commandName="registerUserForm">
			<fieldset>
				<br>
				<legend>Ingrese los siguientes datos para registrar el nuevo usuario</legend>
				<ul>
					<li>
						<label>Nombre de usuario <em>*</em></label>
						<form:input path="username" maxlength="50"/>
						<em><form:errors path="username"/></em>
					</li>
					<li>
						<label>Contraseña <em>*</em></label>
						<form:password path="password" maxlength="10"/><br />
						<em><form:errors path="password"/></em>
					</li>
					<li>
						<label>Repita la contraseña <em>*</em></label>
						<form:password path="rep_password" maxlength="10"/><br />
						<em><form:errors path="rep_password"/></em>
					</li>
					<li>
						<label>Nombre <em>*</em></label>
						<form:input path="firstName" maxlength="50"/><br />
						<em><form:errors path="firstName"/></em>
					</li>
					<li>
						<label>Apellido <em>*</em></label>
						<form:input path="lastName" maxlength="50"/><br />
						<em><form:errors path="lastName"/></em>
						<em>Campos requeridos *</em>
						<input type="submit" value="Aceptar" />
					</li>
				</ul>
			</fieldset>
		</form:form>
	</div>
<%@ include file="../footer.jsp"%>