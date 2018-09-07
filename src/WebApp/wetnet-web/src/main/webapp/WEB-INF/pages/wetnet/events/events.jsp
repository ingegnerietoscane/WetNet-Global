<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp">
    <head>
    	<title><spring:message code="events.head.title" /></title>
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
<body ng-controller="WetnetControllerEvents" >

<div class="container-fluid">	
	<jsp:include page="../../common/nav.jsp" />

	<div class="row">
        <div class="col-md-2">
            <p class="input-group bootstrap-datepicker-correction">
              <label class="sr-only" for="startDate"><spring:message code="events.form.startDate" /></label>
              <input id="startDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="formBean.startDate" is-open="openedStartDate"  />
              <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="openStartDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
              </span>
   			</p>
   		</div>
        <div class="col-md-2">
            <p class="input-group bootstrap-datepicker-correction">
              <label class="sr-only" for="endDate"><spring:message code="events.form.endDate" /></label>
              <input id="endDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="formBean.endDate" is-open="openedEndDate"  />
              <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="openEndDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
              </span>
            </p>
        </div>

		<div class="col-md-4">
	    	<jsp:include page="../../common/datetime-selector.jsp" />
    	</div>
	</div>
  		
   	<div class="row">
 	   <div class="col-md-4">
 	   		<div class="form-group">
   				<input tooltip="<spring:message code="events.form.districts.tooltip" />" placeholder="<spring:message code="events.form.districts.placeholder" />" type="text" ng-model="formBean.districtsSelected" typeahead-on-select="districtSelectedShow($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
   			</div>
   		</div>
   		<div class="col-md-4" ng-show="formBean.districtsSelected">
    		<div class="form-group">
    			<button type="button" class="btn btn-info" tooltip="<spring:message code="events.district.remove.tooltip" />" ng-click="removeDistrict()">{{formBean.districtsSelected.name}}</button>
   			</div>
   		</div>
   	</div>
   	
   	<div class="row">
   		<div class="col-md-1">
   			<div class="form-group">
   				<button id="loadData" name="loadData" ng-click="loadData()" class="btn btn-default" ><spring:message code="events.form.button.update" /></button>
   			</div>
   		</div>
   		<div class="col-md-2">
			<div class="form-group">
		    	<div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				        <spring:message code="events.form.button.exportData" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
				        <li><a id="exportCSV" href="#"><spring:message code="events.form.button.exportCSV" /></a></li>
				      </ul>
		     	</div>
	    	</div>
    	</div>
   		<div class="col-md-5">
	   		<div class="btn-group form-group">
			  <a class="btn btn-info" href="<c:url value="/wetnet/events/events-pie-chart" />" title="<spring:message code="events.form.link.piechart" />"><spring:message code="events.form.link.piechart" /></a>
			  <a class="btn btn-info" href="<c:url value="/wetnet/events/events-scatter-chart" />" title="<spring:message code="events.form.link.scatterchart" />"><spring:message code="events.form.link.scatterchart" /></a>
			  <a class="btn btn-info" href="<c:url value="/wetnet/events/events-cat-chart" />" title="<spring:message code="events.form.link.catchart" />"><spring:message code="events.form.link.catchart" /></a>
			</div>	
   		</div>
   	</div>	
    	
   	<div class="row">
   		<div class="col-md-10">
			<div class="table-responsive">
			  <table class="table table-bordered wetnet-table-head" style="background: white;">
			  <thead>
				  <tr>
					  <th><a href="#" ng-click="sortEventsList('type')"><spring:message code="events.form.table.type" /></a></th>
					  <th><a href="#" ng-click="sortEventsList('duration')" tooltip="<spring:message code="events.tooltip.duration" />"><spring:message code="events.form.table.duration" /></a></th>
					  <th><a href="#" ng-click="sortEventsList('value')" tooltip="<spring:message code="events.tooltip.value" />"><spring:message code="events.form.table.value" /></a></th>
					  <th><a href="#" ng-click="sortEventsList('ranking')"><spring:message code="events.form.table.ranking" /></a></th>
					  <th><a href="#" ng-click="sortEventsList('day')"><spring:message code="events.form.table.day" /></a></th>
					  <th><a href="#" ng-click="sortEventsList('district_name')"><spring:message code="events.form.table.district" /></a></th>
					  <th><a href="#" ng-click="sortEventsList('delta_value')" tooltip="<spring:message code="events.tooltip.delta_value" />"><spring:message code="events.form.table.delta" /></a></th>
					  <th><a href="#" ng-click="sortEventsList('type')"><spring:message code="events.form.table.description" /></a></th>
					  <th><spring:message code="events.form.table.goToChart" /></th>
				  </tr>
			  </thead>
			  <tbody>
				  <tr ng-repeat="e in events | orderBy: orderByField">	  	
				  	<td align="center">{{e.type}}</td>
				  	<td align="center">{{e.duration}}</td>
				  	<td align="center">{{e.value}}</td>
				  	<td align="center">{{e.ranking}}&#37;</td>
				  	<td align="center">{{e.day | date:'yyyy-MM-dd' }}</td>
				  	<td>
				  		<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				  			<a href="<c:url value="/wetnet/manager/district?id={{e.districts_id_districts}}" />" tooltip="<spring:message code="events.form.table.district.modify-title" /> - {{e.districts_id_districts}}" >{{e.district_name}}</a>
				  		</sec:authorize>
				  		<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
				  			<a href="<c:url value="/wetnet/manager/district?id={{e.districts_id_districts}}" />" tooltip="<spring:message code="events.form.table.district.show-title" /> - {{e.districts_id_districts}}" >{{e.district_name}}</a>
				  		</sec:authorize>
				  		<sec:authorize access="hasAnyRole({'ROLE_OPERATOR', 'ROLE_USER'})">
				  			{{e.district_name}}
				  		</sec:authorize>
				  	</td>
				  	<td align="center">{{e.delta_value}}</td>
				  	<td align="center">
				  		<img ng-show="e.type == 1" tooltip="{{e.description}}" class="img-responsive" alt="{{e.description}}" src="<c:url value="/images/anomal_increase_found.png" />" height="40" width="40">
					  	<img ng-show="e.type == 2" tooltip="{{e.description}}" class="img-responsive" alt="{{e.description}}" src="<c:url value="/images/possible_water_loss.png" />" height="40" width="40">
					  	<img ng-show="e.type == 3" tooltip="{{e.description}}" class="img-responsive" alt="{{e.description}}" src="<c:url value="/images/anomal_decrease_found.png" />" height="40" width="40">
					  	<img ng-show="e.type == 4" tooltip="{{e.description}}" class="img-responsive" alt="{{e.description}}" src="<c:url value="/images/possible_water_gain.png" />" height="40" width="40">
					  	<img ng-show="e.type == 5" tooltip="{{e.description}}" class="img-responsive" alt="{{e.description}}" src="<c:url value="/images/out_of_control.png" />" height="40" width="40">
				  	</td>
				  	<td style="font-size: 12px;">
				  		<a href="<c:url value="/wetnet/graphics/statistic?idDistricts={{e.districts_id_districts}}" />" tooltip="<spring:message code="events.form.table.g1-title" />" ><spring:message code="events.form.table.g1" /></a>			  		
				  		 <br><a href="<c:url value="/wetnet/graphics/statistic-g2?idDistricts={{e.districts_id_districts}}&day={{e.day | date:'yyyy-MM-dd' }}&duration={{e.duration}}" />" tooltip="<spring:message code="events.form.table.g2-title" />" ><spring:message code="events.form.table.g2" /></a>
				  		 <br><a href="<c:url value="/wetnet/graphics/statistic-g7?idDistricts={{e.districts_id_districts}}&day={{e.day | date:'yyyy-MM-dd' }}&duration={{e.duration}}" />" tooltip="<spring:message code="events.form.table.g7-title" />" ><spring:message code="events.form.table.g7" /></a>
				  	</td>
				  </tr>
			  </tbody>
			  </table>
			</div>
   		</div>
   	</div>
   	<div class="row" ng-show="eventsSize == 0">
		<div class="col-md-12">
			<h2><spring:message code="events-pie-chart.chart.result" /></h2>
		</div>
	</div>
</div>

<jsp:include page="../../common/footer.jsp" />
</body>
</html>

