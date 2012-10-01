<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

v0.1.2

<c:if test="${not empty errorMsg }">
	<div class="alert alert-error">${errorMsg }</div>
</c:if>

<c:url value="/generateUrl" var="generatedUrl" />
<form class="form-inline" action="${generatedUrl }">
	<input type="text" class="input-xxlarge" placeholder="Type url..."
		required />
	<button type="submit" class="btn">Generate</button>
</form>


<c:if test="${not empty generatedUrl }">
	<form>
		<h2>Generated URL:</h2>
		${generatedUrl }
	</form>
</c:if>
