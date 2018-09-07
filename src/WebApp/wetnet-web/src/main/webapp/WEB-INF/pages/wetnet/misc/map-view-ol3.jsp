<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<title><spring:message code="mapview.head.title" /></title>
	<jsp:include page="../../common/common-head.jsp" />
	<link href="<c:url value="/css/ol.css" />" type="text/css" rel="stylesheet"/>
	<link href="<c:url value="/css/map.css" />" type="text/css" rel="stylesheet"/>
	<!-- <script src="https://maps.google.com/maps/api/js?v=3&amp;sensor=false"></script> -->
<script src="https://maps.google.com/maps/api/js?v=3"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/ol.js"></script>
</head>

<style>
	/* GC - 29/10/2015 
	*  per dimensionare i campi di ricerca distretti /misure per angular js
	* posizionato qui per non essere sovrascritto da bootstrap
	*/
		.dropdown-menu {
		        max-height: 250px;
		        overflow-y: auto;
		}
</style>
<body>
  
<div class="container-fluid" ng-controller="MapViewControllerOl3">

	<jsp:include page="../../common/nav.jsp" />

	<div class="row">
		<div class="col-md-6">
 				<div class="form-group">
				<input tooltip="<spring:message code="mapview.form.districts.tooltip" />" placeholder="<spring:message code="mapview.form.districts.placeholder" />" type="text" ng-model="dSelected" typeahead-on-select="districtSelectedZoom($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
			</div>
   		</div>
		<div class="col-md-6">
			<div class="form-group">
				<input tooltip="<spring:message code="mapview.form.measures.tooltip" />"  placeholder="<spring:message code="mapview.form.measures.placeholder" />" type="text" ng-model="mSelected" typeahead-on-select="measureSelectedZoom($item, $model, $label)" typeahead="d as d.name for d in measures | filter:$viewValue | limitTo:measures.length" class="form-control">
	   		</div>
   		</div>
	</div>

	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="mapview.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row">
		        <div class="col-md-12">
					<div id="map" class="bigmap"></div>
					<div id="toolbox" class="dropdown">
						<a href="#" class="dropdown-toggle map-link" title="<spring:message code="mapview.panel.head.title" />"><spring:message code="mapview.panel.head.menu" /></a>
						<ul id="layerswitcher" class="dropdown-menu left" role="menu">
							<li><label><input type="radio" name="layer" value="0" ng-change="switchLayer()" ng-model="radioItem"> <spring:message code="mapview.panel.general.osmmap" /></label></li>
							<li><label><input type="radio" name="layer" value="1" ng-change="switchLayer()" ng-model="radioItem"> <spring:message code="mapview.panel.general.osmmapquest" /></label></li>
							<li><label><input type="radio" name="layer" value="2" ng-change="switchLayer()" ng-model="radioItem"> <spring:message code="mapview.panel.general.satellitemap" /></label></li>
							<li><label><input type="radio" name="layer" value="3" ng-change="switchLayer()" ng-model="radioItem"> <spring:message code="mapview.panel.general.hybridmap" /></label></li>
							<li class="divider"></li>
							<li><label><input type="checkbox" name="layer" ng-change="updateView()" ng-model="showDistricts"> <spring:message code="mapview.panel.check.districts" /></label></li>
							<li><label><input type="checkbox" name="layer" ng-change="updateView()" ng-model="showMeasures"> <spring:message code="mapview.panel.check.measures" /></label></li>
							<li><label><input type="checkbox" name="layer" ng-change="updateAreal()" ng-model="showAreal"> <spring:message code="mapview.panel.check.areal" /></label></li>
							<li><label><input type="checkbox" name="layer" ng-change="updateLinear()" ng-model="showLinear"> <spring:message code="mapview.panel.check.linear" /></label></li>
							<li><label><input type="checkbox" name="layer" ng-change="updatePunctual()" ng-model="showPunctual"> <spring:message code="mapview.panel.check.punctual" /></label></li>
						</ul>
					</div>
		   		</div>
			</div>
		</div>
	</div>
	
</div>
  
<jsp:include page="../../common/footer.jsp" />
  
</body>

</html>