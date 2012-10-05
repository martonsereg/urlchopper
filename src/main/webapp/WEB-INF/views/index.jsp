<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- v0.1.7 -->

<%-- <c:forEach items="${cookie}" var="nextCookie"> --%>
<%--     <li>${nextCookie.key} = ${nextCookie.value} --%>
<%-- </c:forEach> --%>

<center>
	<c:if test="${not empty errorMsg }">
		<div class="alert alert-error">
			<button type="button" class="close" data-dismiss="alert">×</button>
			<c:out value="${errorMsg}"/>
		</div>
	</c:if>

	<c:url value="/generateUrl" var="generateUrl" />
	<form class="form-inline" action="${generateUrl }">
		<input type="text" name="url" class="input-xxlarge"
			placeholder="Type url..." required />
		<button type="submit" class="btn">Generate</button>
	</form>


	<c:if test="${not empty shortUrl }">
		<form>
			<h2>Generated URL:</h2>
			<c:url value="/${shortUrl }" var="redirectUrl" />
			<a href="${redirectUrl }">http://localhost:8080/urlchopper/${shortUrl}</a>
		</form>
	</c:if>

	<c:if test="${not empty lastUrls }">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>Original URL</th>
					<th>Short URL</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="lastUrls" var="lastUrl">
					<tr>
						<td>${lastUrl.originalUrl }</td>
						<td>${lastUrl.shortUrl }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</center>