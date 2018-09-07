<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title><spring:message code="contacts.head.title" /></title>
<jsp:include page="../../common/common-head.jsp" />
</head>
<body>

	<jsp:include page="../../common/nav.jsp" />

	<div class="container">

		<div class="form-group">
			<h1>
				<spring:message code="contacts.body.title" />
			</h1>
		</div>

		<div>
			<div class="form-group">
				<img src="<c:url value="/images/IngegnerieToscane-Transp.png" />"
					alt="logo_it" align="middle" />
			</div>

			<div class="form-group">
				<p>
					<span><spring:message code="contacts.body.tel" /> +39 (050) 843207</span>
				</p>
				<p>
					<span><spring:message code="contacts.body.fax" /> +39 (050) 843000</span>
				</p>
				<p>
					<span>
						<a href="http://www.ingegnerietoscane.net/" target="_blank">
							http://www.ingegnerietoscane.net/</a>
					</span>
				</p>
			</div>

		</div>

	</div>

	<jsp:include page="../../common/footer.jsp" />
</body>

</html>
