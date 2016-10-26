<%@ include file="../header.jsp"%>
<div id="content">

<table>
	<c:forEach items="${issues_types}" var="item">
		<tr>
			<td><strong><c:out value='Tipo ${item.key}:'/></strong></td>
				<table>
					<c:forEach items="${item.value}" var="subitem">
						<tr>
							<td>
								<span style = "padding-left:20px">
									<c:out value='${subitem.code}:' />	
									<c:out value='${subitem.title}' />  
								</span>
							</td>
						</tr>
					</c:forEach>
				</table>
			</tr>
	</c:forEach>
</table>


</div>
<%@ include file="../footer.jsp"%>