<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
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
  
<div class="container-fluid" ng-controller="ManagerMeasuresController">

	<jsp:include page="../../common/nav.jsp" />
	
	<div class="row">
	 	<div class="col-md-12">
		<form  class="form-inline" role="form" >
		  <div class="form-group">
		    <label class="sr-only" for="searchMeasures"><spring:message code="measures.form.searchMeasures.label" /></label>
		    <input ng-model="searchMeasures" type="text" class="form-control input-sm" id="searchMeasures" placeholder="<spring:message code="measures.form.searchMeasures.placeholder" />">
		  </div>
		   <button type="submit" class="btn btn-default"><spring:message code="measures.form.searchMeasures.button" /></button> 
		  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
		  	<a class="btn btn-default" href="<c:url value="/wetnet/manager/measure" />"><spring:message code="measures.form.addMeasures.button" /></a>
		  </sec:authorize>
		  
		  <!-- ***RC 25/11/2015*** -->
		   <sec:authorize access="!hasRole('ROLE_METER_READER')">
		  	<div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
				<button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
					<spring:message code="statistic-g7.form.button.exportData" /><span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li><a id="exportCSV" href="#"><spring:message code="statistic-g7.form.button.exportCSV" /></a></li>
				</ul>
			</div>
			</sec:authorize>
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
			  <th><spring:message code="measures.table.name" /></th>
			  <th><spring:message code="measures.table.type" /></th>
			  <th><spring:message code="measures.table.description" /></th>
			  <th></th>
			  </tr>
			  </thead>
			  <tbody>
			  <tr ng-repeat="u in measures | filter:searchMeasures">
			  	<td>{{u.name}}</td>
				<td>
					<select disabled="disabled" class="form-control input-sm disabled" ng-model="u.type" >
							<option value="0"><spring:message code="measure.form.type.val.0" /></option>
				      		<option value="1"><spring:message code="measure.form.type.val.1" /></option>
				      		<option value="2"><spring:message code="measure.form.type.val.2" /></option>
		      		</select>
	      		</td>
			  	<td>{{u.description}}</td>
			  	<td><a href="<c:url value="/wetnet/manager/measure?id={{u.idMeasures}}" />" type="button" class="btn btn-default">
			  		<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				  		<spring:message code="measures.table.edit" />
			  		</sec:authorize>
			  		<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR', 'ROLE_OPERATOR','ROLE_METER_READER'})">
				  		<spring:message code="measures.table.view" />
			  		</sec:authorize>
			  	</a>
			  	<!-- funzione letturista da completare -->
<%-- 			  	 <a href="<c:url value="/wetnet/manager/measureMeterReading?id={{u.idMeasures}}" />" type="button" class="btn btn-default"> --%>
<%-- 			  	 <spring:message code="measures.table.meterReading" /> --%>
<!-- 			  	 </a> -->
			  	</td>
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
