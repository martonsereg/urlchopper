<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

v0.1.25

<%-- <c:forEach items="${cookie}" var="nextCookie"> --%>
<%--     <li>${nextCookie.key} = ${nextCookie.value} --%>
<%-- </c:forEach> --%>

<div class="row-fluid">
	<div class="span3"></div>
	<div class="span6">
		<c:if test="${not empty errorMsg }">
			<div class="alert alert-error">
				<button type="button" class="close" data-dismiss="alert">×</button>
			</div>
		</c:if>

		<c:url value="/generateUrl" var="generateUrl" />
		<div class="row-fluid">
			<form class="bs-docs-example" action="${generateUrl }"
				style="text-align: center;">
				<div class="input-append">
					<input type="text" name="url" class="input-xxlarge"
						placeholder="Type url..." />
					<button type="submit" class="btn">Generate</button>
				</div>
			</form>
		</div>


		<c:if test="${not empty shortUrl }">
			<form style="text-align: center;">
				<h2>Generated URL:</h2>
				<c:url value="/${shortUrl }" var="redirectUrl" />
				<a href="${redirectUrl }">${shortUrl}</a>
			</form>
		</c:if>

		<c:if test="${not empty shortUrls}">
			<div class="row-fluid">
				<div class="span12">
					<table class="table">
						<thead>
							<tr>
								<th>Original URLs</th>
								<th>Short URLs</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${shortUrls }" var="shortUrl">
								<tr class="">
									<td>${shortUrl.originalUrl }</td>
									<c:url value="/${shortUrl.originalUrl }" var="redirectUrl" />
									<td><a href="${redirectUrl }">${shortUrl.shortUrl}</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
	</div>
	<div class="span3"></div>
</div>