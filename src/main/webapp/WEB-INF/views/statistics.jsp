<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

v0.1.2
<div class="span8">
	<table class="table">
		<thead>
			<tr>
				<th>Original URLs</th>
				<th>References</th>
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