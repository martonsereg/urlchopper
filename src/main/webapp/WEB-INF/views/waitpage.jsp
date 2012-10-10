<%@ taglib tagdir="/WEB-INF/tags/util" prefix="util"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:if test="${ not empty param.qa }">
v0.1.0
	<c:forEach items="${cookie}" var="nextCookie">
		<li>${nextCookie.key} = ${nextCookie.value}
	</c:forEach>
</c:if>

<head>
<meta http-equiv="REFRESH" content="5;url=${url }">
</head>
<center>
	<div class="alert alert-block">You will be redirected to the following URL after 5 seconds: ${url}</div>
</center>