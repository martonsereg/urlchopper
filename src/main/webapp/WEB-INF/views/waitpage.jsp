<%@ taglib tagdir="/WEB-INF/tags/util" prefix="util" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

v0.1.0
You have redirected to: 

<c:set value="/" var="redirectUrl" />

<util:waitAndRedirect url="${redirectUrl }" delayTime="1000" />