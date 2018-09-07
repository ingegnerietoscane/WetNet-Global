<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container-fluid">
	<div class="text-muted" style="font-weight: 400; font-size: 13px;">
		<a href="http://ec.europa.eu/environment/eco-innovation/index_en.htm"
			target="_blank"> <img src="<c:url value="/images/eu.png" />" class="img-responsive" alt="<spring:message code="footer.eulogo.alt" />">
		</a>
		<spring:message code="footer.it.text" />
	</div>
</div>
