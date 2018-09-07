<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../common/common-head.jsp" />
	<link href="<c:url value="/css/signin.css" />" type="text/css" rel="stylesheet"/>
	
	<!-- Include Analytics -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/wetnet-google-analytics.js"></script>
	
<title><spring:message code="login.head.title" text="Wetnet Login Page" /></title>
</head>

<body onload='document.f.j_username.focus();'>

	 <div class="container">

		<!--        <p>
            <a href="?lang=en"><img src="<c:url value="/images/en.gif" />" /></a> <a href="?lang=it"><img src="<c:url value="/images/it.jpg" />" /></a>
        </p>-->

		<form class="form-signin" role="form" name='f' action="<c:url value='../j_spring_security_check' />" method='POST'>
			<div class="form-group">
				<h2>
					<img class="img-responsive" alt="<spring:message code="login.body.title"/>" src="<c:url value="/images/wetnet_logo_login.png" />" height="40" width="298">
				</h2>
			</div>
			<c:if test="${not empty error}">
				<div class="form-group errorblock">
					<spring:message code="login.error.message" />
<%-- 					${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} --%>
				</div>
			</c:if>
			<div class="form-group">
				<label class="sr-only"><spring:message code="login.label.username" text="Username:" /></label>
			    <input type='text' name='j_username' value='' class="form-control" placeholder="username" />
			</div>
			<div class="form-group">
				<label class="sr-only"><spring:message code="login.label.password" text="Password:" /></label>
				<input type='password' name='j_password' class="form-control" placeholder="password" required />
			</div>
			<div class="form-group">
				<input class="btn btn-lg btn-primary btn-block" name="<spring:message code="login.submit.button" text="Submit"/>" type="submit" value="<spring:message code="login.submit.button" text="Submit"/>" />
			
				<input class="btn btn-lg btn-link btn-block" name="<spring:message code="login.reset.button" text="Reset"/>" type="reset" value="<spring:message code="login.reset.button" text="Reset"/>" />
					
			</div>
		</form>

	</div>
	<div class="footer-logo">
		<jsp:include page="../common/footer-logo.jsp" />
	</div>
</body>
</html>