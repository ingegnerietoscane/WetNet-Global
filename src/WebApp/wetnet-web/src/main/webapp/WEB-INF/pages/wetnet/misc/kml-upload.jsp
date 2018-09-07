<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<title><spring:message code="kml-upload.head.title" /></title>
	<jsp:include page="../../common/common-head.jsp" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/bootstrap.file-input.js"></script>
</head>
<body>
<div class="container-fluid" ng-controller="KmlUploadController">

<jsp:include page="../../common/nav.jsp" />

	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="kml-upload.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
  		
  			<!-- Areal upload -->
			<div class="row">
		        <div class="col-md-6">
		        	<jsp:include page="../../common/alerts-spring.jsp" />
		        	<form method="POST" action="areal-upload" enctype="multipart/form-data" class="form-inline" role="form">
			            <div class="form-group">
			            	<input class="btn-default" type="file" name="file" accept="application/vnd.google-earth.kml+xml" title="<spring:message code="kml-upload.form.title" />">
			            </div>
		                <div class="form-group">
		                	<input class="btn btn-primary" type="submit" value="<spring:message code="kml-upload.form.value-areal" />" />
			            </div>
			        </form>
		   		</div>
	   		</div>
	   		
	   		<!-- Linear upload -->
	   		<div class="row">
		        <div class="col-md-6">
		        	<form method="POST" action="linear-upload" enctype="multipart/form-data" class="form-inline" role="form">
			            <div class="form-group">
			            	<input class="btn-default" type="file" name="file" accept="application/vnd.google-earth.kml+xml" title="<spring:message code="kml-upload.form.title" />">
			            </div>
		                <div class="form-group">
		                	<input class="btn btn-primary" type="submit" value="<spring:message code="kml-upload.form.value-linear" />" />
			            </div>
			        </form>
		   		</div>
	   		</div>
	   		
	   		<!-- Punctual upload -->
	   		<div class="row">
		        <div class="col-md-6">
		        	<form method="POST" action="punctual-upload" enctype="multipart/form-data" class="form-inline" role="form">
			            <div class="form-group">
			            	<input class="btn-default" type="file" name="file" accept="application/vnd.google-earth.kml+xml" title="<spring:message code="kml-upload.form.title" />">
			            </div>
		                <div class="form-group">
		                	<input class="btn btn-primary" type="submit" value="<spring:message code="kml-upload.form.value-punctual" />" />
			            </div>
			        </form>
		   		</div>
	   		</div>

		</div>
	</div>
</div>

  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>

<script>$(document).ready(function(){$('input[type=file]').bootstrapFileInput();});</script>
</html>
