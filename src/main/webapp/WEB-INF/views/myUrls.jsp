<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

v0.1.0 myUrls

<div class="span8">
	<table class="table">
		<thead>
			<tr>
				<th>Original URLs</th>
				<th>Short URLs</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${shortUrls }" var="shortUrl">
				<tr>
					<td>${shortUrl.originalUrl }</td>
					<td>${shortUrl.shortUrl }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>