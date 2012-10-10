<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:bundle basename="messages" />

<c:if test="${ not empty param.qa }">
v0.1.26
	<c:forEach items="${cookie}" var="nextCookie">
		<li>${nextCookie.key} = ${nextCookie.value}
	</c:forEach>
</c:if>

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
					<fmt:message key="index.textbox.url.placeholder" var="urlPlaceholder" />
					<input type="text" name="url" class="input-xxlarge"
						placeholder="${urlPlaceholder }" />
					<button type="submit" class="btn"><fmt:message key="index.button.generate" /></button>
				</div>
			</form>
		</div>


		<c:if test="${not empty shortUrl }">
			<form style="text-align: center;">
				<h2><fmt:message key="index.link.generatedurl" /></h2>
				<c:url value="/${shortUrl }" var="redirectUrl" />
				<a href="${redirectUrl }"><fmt:message key="url.prefix" />${shortUrl}</a>
			</form>
		</c:if>

		<c:if test="${not empty shortUrls}">
			<div class="row-fluid">
				<div class="span12">
					<table class="table">
						<thead>
							<tr>
								<th><fmt:message key="index.tableheader.originalurl" /></th>
								<th><fmt:message key="index.tableheader.shorturl" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${shortUrls }" var="shortUrl">
								<tr class="">
									<td>${shortUrl.originalUrl }</td>
									<c:url value="/${shortUrl.shortUrl }" var="redirectUrl" />
									<td><a href="${redirectUrl }"><fmt:message key="url.prefix" />${shortUrl.shortUrl}</a></td>
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