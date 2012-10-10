<%@ taglib tagdir="/WEB-INF/tags/util" prefix="util"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="waitscript" value="/resources/js/wait.js" />

<script src="${waitscript}"></script>

<c:if test="${ not empty param.qa }">
v0.1.0
	<c:forEach items="${cookie}" var="nextCookie">
		<li>${nextCookie.key} = ${nextCookie.value}
	</c:forEach>
</c:if>

<center>
	<div class="alert alert-block">You will be redirected to the following URL after <span class="countdown">5</span> seconds: ${url}</div>
	<input type="hidden" id="url" value="${url}">	
</center>