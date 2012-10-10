<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:bundle basename="messages" />

<c:if test="${ not empty param.qa }">
v0.1.2
	<c:forEach items="${cookie}" var="nextCookie">
		<li>${nextCookie.key} = ${nextCookie.value}
	</c:forEach>
</c:if>


<div class="span8">
	<table class="table">
		<thead>
			<tr>
				<th><fmt:message key="statistics.tableheader.originalurl" /></th>
				<th><fmt:message key="statistics.tableheader.references" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${urls }" var="url">
				<tr>
					<td>${url.url }</td>
					<td>${url.referenceCount }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>