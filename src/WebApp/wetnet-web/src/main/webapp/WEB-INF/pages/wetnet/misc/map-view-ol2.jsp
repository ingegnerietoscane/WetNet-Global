<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<title><spring:message code="mapview.head.title" /></title>
	<jsp:include page="../../common/common-head.jsp" />
	<link href="<c:url value="/css/map-edit.css" />" type="text/css" rel="stylesheet"/>
	<!-- <script src="https://maps.google.com/maps/api/js?v=3&amp;sensor=false"></script> -->
<script src="https://maps.google.com/maps/api/js?v=3"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/OpenLayer.js"></script>
</head>
<body>
  
<div class="container-fluid" ng-controller="MapViewControllerOl2">

	<jsp:include page="../../common/nav.jsp" />

	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="mapview.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row">
		        <div class="col-md-12">
					<div id="map" class="bigmap"></div>
		   		</div>
			</div>
		</div>
	</div>
	
</div>
  
<jsp:include page="../../common/footer.jsp" />
  
</body>

</html>
