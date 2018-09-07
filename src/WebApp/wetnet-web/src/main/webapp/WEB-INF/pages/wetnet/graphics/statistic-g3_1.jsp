<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
   	<title><spring:message code="g3.head.title" /></title>
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

<body ng-controller="WetnetControllerG3">

<div class="container-fluid">
  <jsp:include page="../../common/nav.jsp" />
 
	<div class="row">
        <div class="col-md-2">
            <p class="input-group">
              <label class="sr-only" for="startDate"><spring:message code="g3.form.startDate" /></label>
              <input type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="bean.startDate" is-open="openedStartDate" close-text="Chiudi" />
              <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="openStartDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
              </span>
            </p>
        </div>
        <div class="col-md-2">
            <p class="input-group">
              <label class="sr-only" for="endDate"><spring:message code="g3.form.endDate" /></label>
              <input type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="bean.endDate" is-open="openedEndDate" close-text="Chiudi" />
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
				<input tooltip="<spring:message code="g3.form.districts.tooltip" />" placeholder="<spring:message code="g3.form.districts.placeholder" />" type="text" ng-model="dSelected" typeahead-on-select="districtSelectedShow($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
	       	</div>
       	</div>
    	<div class="col-md-4" ng-show="bean.districtsSelected != null">
	    	<div class="form-group">
	    		<button type="button" class="btn btn-info">{{bean.districtsSelected.name}}</button>
	   		</div>
   		</div>
	</div>

	<div class="row">
		<div class="col-md-1">
			<div class="form-group">
				<button id="loadDataG3" name="loadDataG3" ng-click="loadDataG3()" class="btn btn-default" value="<spring:message code="g3.form.button.update" />" ><spring:message code="g3.form.button.update" /></button>
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="btn-group" dropdown is-open="status.isopen">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				        <spring:message code="g3.form.button.saveDataAs" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
							<li><a class="savePNG" id="chart-savePNG" export-chart-id="chart" export-chart="images" href="#"><spring:message code="g3.form.button.saveDataAsPNG1" /></a></li>
							<li><a class="savePNG" id="chart2-savePNG" export-chart-id="chart2"  export-chart="images" href="#"><spring:message code="g3.form.button.saveDataAsPNG2" /></a></li>
				      </ul>
		    	</div>
		    	<div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSVs()">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				        <spring:message code="g3.form.button.exportData" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
				        <li><a id="exportCSV1" href="#"><spring:message code="g3.form.button.exportCSV1" /></a></li>
				        <li><a id="exportCSV2" href="#"><spring:message code="g3.form.button.exportCSV2" /></a></li>
				      </ul>
		     	</div>
	    	</div>
    	</div>
	</div> 
  
  	<div class="row" ng-show="columns.length > 0">
  		<div class="col-md-12">
   			<h4><spring:message code="g3.chart1.title" arguments="{{minNightStartTime}},{{minNightStopTime}}" /></h4>
   		</div>
  	</div>
  
  	<div class="row">
	 	<div class="col-md-12 area-grafico-g3">
     		<div id="chart" build-chart></div>
   		</div>
   	</div>
  
  	<div class="row" ng-show="columns.length > 0">
  		<div class="col-md-12">
   			<h4><spring:message code="g3.chart2.title" /></h4>
   		</div>
  	</div>
  
  	<div class="row">
	 	<div class="col-md-12 area-grafico-g3">
     		<div id="chart2" build-chart></div>
   		</div>
   	</div>
   	
   	<div class="row" ng-show="columns.length > 0">
  		<div class="col-md-12">
   			<h4><spring:message code="g3.chart3.title" arguments="{{minNightStartTime}},{{minNightStopTime}}" /></h4>
   		</div>
  	</div>
  
  	<div class="row">
	 	<div class="col-md-12 area-grafico-g3">
     		<div id="chart3" build-chart></div>
     		<div ng-show="indiceCorrelazione && coeffDeterminazione">
     		<spring:message code="g3.chart3.indiceCorrelazione" arguments="{{indiceCorrelazione}}" />
     		<br>
     		<spring:message code="g3.chart3.coefficienteDeterminazione" arguments="{{coeffDeterminazione}}" />
     		</div>
     		
   		</div>
   	</div>
   	
   	
 </div>
  
<jsp:include page="../../common/footer.jsp" />

<!-- Utlizzato per l'export del grafico	-->
<canvas id="canvas" width="1072px" height="676px" class="ng-hide"></canvas>
</body>

</html>

