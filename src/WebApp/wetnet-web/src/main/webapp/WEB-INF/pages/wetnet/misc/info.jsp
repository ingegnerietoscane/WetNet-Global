<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title><spring:message code="info.head.title" /></title>
	<jsp:include page="../../common/common-head.jsp" />
</head>
<body>

	<jsp:include page="../../common/nav.jsp" />

	<div class="container" ng-controller="WetnetInfoController">

		<div>
			<h1><spring:message code="info.body.title" /></h1>
			<h5 ng-show="wetnetVersion"><spring:message code="info.body.version" /> {{wetnetVersion}}</h5>
		</div>

		<blockquote>
			<p style="text-align: justify;">
				<strong><a href="http://www.wetnet.it/en"
						target="_blank"><img alt="logo_wetnet" src="<c:url value="/images/wetnet_logo_login.png" />" height="60" width="180"
						border="0" style="border: 0; float: left; margin-right: 20px;"></a>
							<spring:message code="info.wetnet.message" />
			   </strong>
			</p>
		</blockquote>
		
		<blockquote>
			<p style="text-align: justify;">
				<a href="http://ec.europa.eu/environment/eco-innovation/index_en.htm"
					target="_blank">
				<img src="<c:url value="/images/co-funded-ei-vert.jpg" />" border="0"
					width="200" style="border: 0; float: left;" alt="logo_eu" /></a>
				<a href="http://www.wetnet.it" target="_blank" style="text-decoration: none;">
					<spring:message code="info.wetnet.project" />
				</a>
				<spring:message code="info.wetnet.text" />
			</p>
		</blockquote>
		
		<table width="100%">
			<tbody>
				<tr>
					<td style="text-align: center;" width="25%"><a
						href="http://www.bre.pisa.it/" target="_blank"><img
							src="<c:url value="/images/BRE-Transp.png" />" alt="logo_bre" border="0" width="179"
							style="border: 0;" /></a></td>
					<td width="20%"><a
						href="http://www.ingegnerietoscane.net/" target="_blank"><img
							src="<c:url value="/images/IngegnerieToscane-Transp.png" />" border="0" alt="logo_it" width="242" height="51"
							style="display: block; margin-left: 20px; margin-right: auto;" /></a></td>
					<td style="text-align: center;" width="20%"><a
						href="http://lnx.bimatik.it/site/" target="_blank"><img
							src="<c:url value="/images/bimatik-Transp.png" />" alt="logo_bimatik" border="0" width="80"
							style="border: 0; margin-left: 20px;" /></a></td>
					<td style="text-align: center;" width="20%"><a
						href="http://www.itg.es/" target="_blank"><img
							src="<c:url value="/images/ITG_116x85_transp_0a.png" />" alt="logo_itg" border="0"
							style="border: 0;" /></a></td>
				</tr>
			</tbody>
		</table>

	</div>

	<jsp:include page="../../common/footer.jsp" />

</body>

</html>
