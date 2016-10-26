<%@ include file="header.jsp"%>
	<div id="content">
		<h2>Registración</h2>
		<form action="register_user" method="post">
			<fieldset>
				<c:forEach items="${errors}" var="error">
					<p>
						<em><c:out value="${error}" /></em>
					</p>
				</c:forEach>
				<br>
				<legend>Ingrese los siguientes datos para registrar el nuevo usuario</legend>
				<ul>
					<li>
						<label>Nombre de usuario <em>*</em></label>
						<input type="text" value="${input_username}" name="username" maxlength=50/>
					</li>
					<li>
						<label>Contraseña <em>*</em></label>
						<input type="password" name="password" maxlength=10/><br />
					</li>
					<li>
						<label>Repita la contraseña <em>*</em></label>
						<input type="password" name="rep_password" maxlength=10/><br />
					</li>
					<li>
						<label>Nombre <em>*</em></label>
						<input type="text" value="${input_firstname}" name="firstname" maxlength=50/><br />
					</li>
					<li>
						<label>Apellido <em>*</em></label>
						<input type="text" value="${input_lastname}" name="lastname" maxlength=50/><br />
						<em>Campos requeridos *</em>
						<input type="submit" value="Aceptar" />
					</li>
				</ul>
			</fieldset>
		</form>
	</div>
<%@ include file="footer.jsp"%>