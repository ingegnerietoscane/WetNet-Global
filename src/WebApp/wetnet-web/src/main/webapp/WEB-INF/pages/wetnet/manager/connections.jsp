<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
</head>
<body>
  
<div class="container-fluid" ng-controller="ManagerConnectionsController">

	<jsp:include page="../../common/nav.jsp" />
	
	<div class="row">
	 	<div class="col-md-12">
		<form  class="form-inline" role="form" >
		  <div class="form-group">
		    <label class="sr-only" for="searchConnections"><spring:message code="connections.form.searchConnections.label" /></label>
		    <input ng-model="searchConnections" type="text" class="form-control input-sm" id="searchConnections" placeholder="<spring:message code="connections.form.searchConnections.placeholder" />">
		  </div>
		    <button type="submit" class="btn btn-default"><spring:message code="connections.form.searchConnections.button" /></button>
		  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
		  	<a class="btn btn-default" href="<c:url value="/wetnet/manager/connection" />"><spring:message code="connections.form.addConnections.button" /></a>
		  </sec:authorize>
		</form>
		</div>
	</div>

   	<div class="row">
   		<div class="col-md-12">
			<div class="table-responsive">
			  <table class="table table-striped table-condensed">
			  <thead>
			  <tr>
			  <th><spring:message code="connections.table.id_odbcdsn" /></th>
			  <th><spring:message code="connections.table.odbc_dsn" /></th>
			  <th><spring:message code="connections.table.description" /></th>
			  <th></th>
			  </tr>
			  </thead>
			  <tbody>
			  <tr ng-repeat="u in connections | filter:searchConnections">
			  	<td>{{u.id_odbcdsn}}</td>
			  	<td>{{u.odbc_dsn}}</td>
			  	<td>{{u.description}}</td>
			  	<td><a href="<c:url value="/wetnet/manager/connection?id={{u.id_odbcdsn}}" />" type="button" class="btn btn-default">
			  		<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				  		<spring:message code="connections.table.edit" />
			  		</sec:authorize>
			  		<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
				  		<spring:message code="connections.table.view" />
			  		</sec:authorize>
			  	</a></td>
			  </tr>
			  </tbody>
			  </table>
			</div>
   		</div>
   	</div>


</div>

  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
