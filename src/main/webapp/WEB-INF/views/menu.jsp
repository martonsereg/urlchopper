<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container" style="width: auto;">
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a> <a class="brand" href="#">URL Chopper</a>
			<div class="nav-collapse">
				<ul class="nav">
					<c:url value="/" var="indexUrl"></c:url>
					<li><a href="${indexUrl }">Generate URL</a></li>					
                    <c:url value="/statistics" var="statUrl"></c:url>
					<li><a href="${statUrl }">Statistics</a></li>			
				</ul>	
			</div>
			<!-- /.nav-collapse -->
		</div>
	</div>
	<!-- /navbar-inner -->
</div>
<!-- /navbar -->