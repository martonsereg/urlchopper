<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

v0.1.2

<c:if test="${not empty errorMsg }">
	<div class="alert alert-error">${errorMsg }</div>
</c:if>

<c:url value="/generateUrl" var="generateUrl" />
<form class="form-inline" action="${generateUrl }">
	<input type="text" name="url" class="input-xxlarge" placeholder="Type url..."
		required />
	<button type="submit" class="btn">Generate</button>
</form>


<c:if test="${not empty shortUrl }">
	<form>
		<h2>Generated URL:</h2>
		<c:url value="/${shortUrl }" var="redirectUrl" />
		<a href="${redirectUrl }">http://localhost:8080/urlchopper/${shortUrl }</a>
	</form>
</c:if>
