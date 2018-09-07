<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp">
    <head>
    	<title><spring:message code="alarms-active.head.title" /></title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
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
<body ng-controller="WetnetControllerAlarms" >
<div class="container-fluid">	
	<jsp:include page="../../common/nav.jsp" />
  		
   	<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<input tooltip="<spring:message code="alarms-active.form.measures.tooltip" />"  placeholder="<spring:message code="alarms-active.form.measures.placeholder" />" type="text" ng-model=mSelected typeahead-on-select="measureSelectedShow($item, $model, $label)" typeahead="d as d.name for d in measures | filter:$viewValue | limitTo:measures.length" class="form-control">
		   		</div>
	   		</div>
	   		<div class="col-md-4" ng-show="mSelected">
    		<div class="form-group">
    			<button type="button" class="btn btn-info" tooltip="<spring:message code="alarms-active.measure.remove.tooltip" />" ng-click="removeMeasure()">{{mSelected.name}}</button>
   			</div>
   		</div>
		</div>
   	
   	
   	<div class="row">
   		<div class="col-md-1">
   			<div class="form-group">
   				<button id="loadData" name="loadData" ng-click="loadData()" class="btn btn-default" ><spring:message code="alarms-active.form.button.update" /></button>
   			</div>
   		</div>
   		<div class="col-md-2">
			<div class="form-group">
		    	<div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				        <spring:message code="alarms-active.form.button.exportData" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
				        <li><a id="exportCSV" href="#"><spring:message code="alarms-active.form.button.exportCSV" /></a></li>
				      </ul>
		     	</div>
	    	</div>
    	</div>
   		
   	</div>	
   	
   	<div class="row">
   		<div class="col-md-10">
			<div class="table-responsive">
			  <table class="table table-bordered wetnet-table-head" style="background: white;">
			  <thead>
				  <tr>
					<th><spring:message code="alarms-active.form.table.measure" /></th>
					<th><spring:message code="alarms-active.form.table.type" /></th>
					<th><spring:message code="alarms-active.form.table.value" /></th>
					<th><spring:message code="alarms-active.form.table.reference-value" /></th>
					<th><spring:message code="alarms-active.form.table.duration" /></th>
				 </tr>
			  </thead>
			   <tbody>
				  <tr ng-repeat="a in alarms">	  
				  	<td align="center">{{a.measures_name}}</td>
				  	<td align="center">
				  		<div ng-show="a.alarm_type == 0" ><spring:message code="alarms.type.0"/></div>
				  		<div ng-show="a.alarm_type == 1" ><spring:message code="alarms.type.1" /></div>
				  		<div ng-show="a.alarm_type == 2" ><spring:message code="alarms.type.2" /></div>
				  		<div ng-show="a.alarm_type == 3" ><spring:message code="alarms.type.3"/></div>
				  		<div ng-show="a.alarm_type == 4"><spring:message code="alarms.type.4"  /></div>
				  	</td>
				  	<td align="center">{{a.alarm_value}}</td>
				  	<td align="center">{{a.reference_value}}</td>
				  	<td align="center">{{a.duration}}</td>
				  </tr>
				  </tbody>
			  </table>
			  </div>
			 </div>
		</div>
   	<div class="row" ng-show="alarmsSize == 0">
		<div class="col-md-12">
			<h2><spring:message code="alarms-active.form.no-result" /></h2>
		</div>
	</div>
</div>

<jsp:include page="../../common/footer.jsp" />
</body>
</html>

