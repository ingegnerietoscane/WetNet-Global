<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
</head>
<body>
  
<div class="container-fluid" ng-controller="ManagerDistrictsController">

	<jsp:include page="../../common/nav.jsp" />
	
	<div class="row">
	 	<div class="col-md-12">
		<form  class="form-inline" role="form" >
		  <div class="form-group">
		    <label class="sr-only" for="searchDistricts"><spring:message code="districts.form.searchDistricts.label" /></label>
		    <input ng-model="searchDistricts" type="text" class="form-control input-sm" id="searchDistricts" placeholder="<spring:message code="districts.form.searchDistricts.placeholder" />">
		  </div>
		 <button type="submit" class="btn btn-default"><spring:message code="districts.form.searchDistricts.button" /></button> 
		  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
		  	<a class="btn btn-default" href="<c:url value="/wetnet/manager/district" />"><spring:message code="districts.form.addDistricts.button" /></a>
		  </sec:authorize>
		  
		  <!-- ***RC 25/11/2015*** -->
		  	<div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
				<button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
					<spring:message code="statistic-g7.form.button.exportData" /><span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li><a id="exportCSV" href="#"><spring:message code="statistic-g7.form.button.exportCSV" /></a></li>
				</ul>
			</div>
		  <!-- ***END*** -->
		  
		</form>
		</div>
	</div>

   	<div class="row">
   		<div class="col-md-12">
			<div class="table-responsive">
			  <table class="table table-striped table-condensed">
			  <thead>
			  <tr>
			  <th><spring:message code="districts.table.name" /></th>
			  <th><spring:message code="districts.table.zone" /></th>
			  <th><spring:message code="districts.table.municipality" /></th>
			  <th></th>
			  </tr>
			  </thead>
			  <tbody>
			  <tr ng-repeat="u in districts | filter:searchDistricts">
			  	<td>{{u.name}}</td>
			  	<td>{{u.zone}}</td>
			  	<td>{{u.municipality}}</td>
			  	<td><a href="<c:url value="/wetnet/manager/district?id={{u.idDistricts}}" />" type="button" class="btn btn-default">
			  		<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				  		<spring:message code="districts.table.edit" />
			  		</sec:authorize>
			  		<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR', 'ROLE_OPERATOR'})">
				  		<spring:message code="districts.table.view" />
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
