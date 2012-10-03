<%@ tag language="java" pageEncoding="ISO-8859-1"%>



<%@ attribute name="url" required="true" type="java.lang.String" %>
<%@ attribute name="delayTime" required="true" type="java.lang.String" %>

<%	
	System.out.println(url + ", " + delayTime);
	Thread.sleep(Long.valueOf(delayTime));
	System.out.println("delay elapsed");
	response.sendRedirect(url);	
%>
<jsp:forward page="${url }" />