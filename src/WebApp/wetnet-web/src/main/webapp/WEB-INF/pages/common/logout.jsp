<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	
<spring:message code="logout.text.username" text="Username:"/> ${username}	<a href="<c:url value="/j_spring_security_logout" />" ><spring:message code="logout.logout.button" text="Logout"/></a>
