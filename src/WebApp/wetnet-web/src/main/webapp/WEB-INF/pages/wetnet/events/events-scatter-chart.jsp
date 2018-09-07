<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp">
    <head>
    <title><spring:message code="events-scatter-chart.head.title" /></title>
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
<body ng-controller="WetnetControllerEventsG1">

<div class="container-fluid">	
	<jsp:include page="../../common/nav.jsp" />

	<div class="row">
        <div class="col-md-2">
            <p class="input-group bootstrap-datepicker-correction">
              <label class="sr-only" for="startDate"><spring:message code="events-scatter-chart.form.startDate" /></label>
              <input id="startDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="formBean.startDate" is-open="openedStartDate"  />
              <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="openStartDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
              </span>
   			</p>
   		</div>
        <div class="col-md-2">
            <p class="input-group bootstrap-datepicker-correction">
              <label class="sr-only" for="endDate"><spring:message code="events-scatter-chart.form.endDate" /></label>
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
	   			<input tooltip="<spring:message code="events-scatter-chart.form.districts.tooltip" />" placeholder="<spring:message code="events-scatter-chart.form.districts.placeholder" />" type="text" ng-model="formBean.districtsSelected" typeahead-on-select="districtSelectedShow($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
	   		</div>
   		</div>
   	</div>
   	
   	<div class="row">
   		<div class="col-md-4" >
	   		<div class="form-group">
	    		<div ng-show="formBean.districtsSelected" class="btn btn-info">{{formBean.districtsSelected.name}}</div>
	   		</div>
   		</div>
   		<div class="col-md-4">
	   		<div class="form-group">
	    		<div ng-show="formBean.municipalitySelected" class="btn btn-info">{{formBean.municipalitySelected}}</div>
	   		</div>
   		</div>
   		<div class="col-md-4">
	   		<div class="form-group">
	    		<div ng-show="formBean.zoneSelected" class="btn btn-info">{{formBean.zoneSelected}}</div>
	   		</div>
   		</div>
   	</div>
   	<div class="row">
   		<div class="col-md-1">
	   		<div class="form-group">
	   			<button id="loadChartData" name="loadChartData" ng-click="loadChartData()" class="btn btn-default" ><spring:message code="events-scatter-chart.form.button.update" /></button>
	   		</div>
   		</div>
   		<div class="col-md-5">
			<div class="form-group">
				<div class="btn-group" dropdown is-open="status.isopen">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				        <spring:message code="events-scatter-chart.form.button.saveDataAs" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
							<li><a class="savePNG" id="scatter-chart-savePNG" export-chart-id="scatter-chart" export-chart="images" href="#"><spring:message code="events-scatter-chart.form.button.saveDataAsPNG" /></a></li>
				      </ul>
		    	</div>
		    	<div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				        <spring:message code="events-scatter-chart.form.button.exportData" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
				        <li><a id="exportCSV" href="#"><spring:message code="events-scatter-chart.form.button.exportCSV" /></a></li>
				      </ul>
		     	</div>
	    	</div>
    	</div>
   	</div>

	<div class="row" ng-show="columnsSize > 0">
		<div class="col-md-12 area-grafico-scatter">
			<div id="scatter-chart" build-chart></div>
		</div>
	</div>
	<div class="row" ng-show="columnsSize == 0">
		<div class="col-md-12">
			<h2><spring:message code="events-scatter-chart.chart.result" /></h2>
		</div>
	</div>

	<jsp:include page="../../common/footer.jsp" />
</div>

	<!-- Utlizzato per l'export del grafico	-->
	<canvas id="canvas" width="1072px" height="676px" class="ng-hide"></canvas>
</body>
</html>

