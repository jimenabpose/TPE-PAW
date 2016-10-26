<%@ include file="header.jsp"%>
<div id="content">
<c:if test="${user.admin == true}">
	<p>
	<a href="register_user">Registrar otro usuario</a>
	</p>
</c:if>
		<p><c:out value="${message}" /></p>
		<p>Para volver al inicio siga el siguiente <a href="logged_index">link</a></p>
</div>
<%@ include file="footer.jsp"%>