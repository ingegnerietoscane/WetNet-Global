<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp">
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<title><spring:message code="g2.head.title" /></title>
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
	
<body ng-controller="WetnetControllerG2">
	<div class="container-fluid">
 	<jsp:include page="../../common/nav.jsp" />
	
		<div class="row">
	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="startDate"><spring:message code="g2.form.startDate" /></label>
	              <input id="startDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="g2Data.startDate" is-open="openedStartDate"  />
	              <span class="input-group-btn">
	                <button type="button" class="btn btn-default" ng-click="openStartDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
	              </span>
	   			</p>
	   		</div>
	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="endDate"><spring:message code="g2.form.endDate" /></label>
	              <input id="endDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="g2Data.endDate" is-open="openedEndDate"  />
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
			<div class="col-md-6">
  				<div class="form-group">
				<!-- <select class="width-80" id="district" name="district" ng-model="formBean.districtId"  ng-options="c.id as c.name for c in districts" ng-change="loadData()"><option/></select> -->
					<input tooltip="<spring:message code="g2.form.districts.tooltip" />" placeholder="<spring:message code="g2.form.districts.placeholder" />" type="text" ng-model="dSelected" typeahead-on-select="districtSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
				</div>
	   		</div>
			<div class="col-md-6">
				<div class="form-group">
					<input tooltip="<spring:message code="g2.form.measures.tooltip" />"  placeholder="<spring:message code="g2.form.measures.placeholder" />" type="text" ng-model="mSelected" typeahead-on-select="measureSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in measures | filter:$viewValue | limitTo:measures.length" class="form-control">
		   		</div>
	   		</div>
		</div>
	   	<div class="row">
			<div class="col-md-6">
				<div class="form-group form-inline" ng-show="g2Data.districtsSelected.length > 0">
					<label><spring:message code="g2.form.selectedDistricts" /></label>
			    	<div class="checkbox">
			    		<label><input name="<spring:message code="g2.form.show-bands" />" type="checkbox" ng-model="g2Data.showDBands"> <spring:message code="g2.form.show-bands" /></label>
			    		<!-- *** RC 21/10/15 *** -->
							<label ng-show="flagEnergyLosses"><input name="<spring:message code="g2.form.show-energy" />" type="checkbox" ng-model="showEnergy" ng-click="loadData()"> <spring:message code="g2.form.show-energy" /></label>
							<label ng-show="flagEnergyLosses"><input name="<spring:message code="g2.form.show-losses" />" type="checkbox" ng-model="showLosses" ng-click="loadData()"> <spring:message code="g2.form.show-losses" /></label>
					   	<!-- *** END *** -->
			    	</div>
				</div>
		    	<div  ng-repeat="item in g2Data.districtsSelected">
			    	<dl>
			    	<dt><button type="button" class="btn btn-info" tooltip="<spring:message code="g2.district.button.remove.tooltip" />"  ng-click="districtSelectedRemove(item)">{{item.name}}</button>
			    		<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
			    			<a href="<c:url value="/wetnet/manager/district?id={{item.idDistricts}}" />" class="btn btn-success" tooltip="<spring:message code="g2.district.button.modify-title" />"><spring:message code="g2.district.button.modify" /></a>
		    			</sec:authorize>
		    			<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
			    			<a href="<c:url value="/wetnet/manager/district?id={{item.idDistricts}}" />" class="btn btn-success" tooltip="<spring:message code="g2.district.button.show-title" />"><spring:message code="g2.district.button.modify" /></a>
		    			</sec:authorize>
			    	</dt>
			    	<dd><span ng-repeat="m in item.measures"><button tooltip="<spring:message code="g2.measure.button.add.tooltip" />" type="button" class="btn btn-link" ng-click="measureSelectedAdd(m, m, m.name)" >{{m.name}} ({{checkSign(m.sign)}})</button></span></dd>
			    	</dl>
		    	</div>
	   		</div>
			<div class="col-md-6">
				<div class="form-group form-inline" ng-show="g2Data.measuresSelected.length > 0">
					<label><spring:message code="g2.form.selectedMeasures" /></label>
				    <div class="checkbox">
				    	<label><input name="<spring:message code="g2.form.show-bands" />" type="checkbox" ng-model="g2Data.showMBands"> <spring:message code="g2.form.show-bands" /></label>
				    </div>
				</div>
			    <div class="form-group">
			    	<button type="button" class="btn btn-info"  tooltip="<spring:message code="g2.measure.button.remove.tooltip" />"  ng-repeat="item in g2Data.measuresSelected" ng-click="measureSelectedRemove(item)">{{item.name}}</button>
		   		</div>
	   		</div>
		</div>
		<!-- ***RC 01/12/2015*** -->
   		<div class="row">
	  		<div class="alert alert-info" role="alert" ng-show="confDialog">
				<label for="confDesc"><small><spring:message code="configuration.description.label" /></small></label>
				<input type="text" name="confDesc" id="confDesc" class="form-control">
				<button class="alert-link" ng-click="saveConf()"><spring:message code="configuration.button.save" /></button>
				<br/>
				<div>
				    <div class="table-responsive" style="height:250px; overflow-y:scroll;">
				    	<table class="table table-bordered wetnet-table-head" style="background: white;">
				    		<thead>
								<tr>
									<th><spring:message code="configuration.table.date" /></th>
									<th><spring:message code="configuration.table.description" /></th>
									<th><spring:message code="configuration.table.apply" /></th>
									<th><spring:message code="configuration.table.cancel" /></th>
								</tr>
						  	</thead>
						  	<tbody>
								<tr ng-repeat="c in configList">
							  		<td align="center">{{c.save_date | date:'yyyy-MM-dd HH:mm:ss'}}</td>
							    	<td align="center">{{c.description}}</td>
							    	<td align="center"><button class="btn btn-success" ng-click="configSelected(c)"><spring:message code="configuration.button.apply"/></button></td>
							    	<td align="center"><button class="btn btn-danger" ng-click="configDeleted(c)"><spring:message code="configuration.button.cancel"/></button></td>
							  	</tr>
  							</tbody>
						</table>
						
				    </div>
  				</div>				
				<br/>
				<button ng-click="dialogCancelled()" class="alert-link"><spring:message code="district.form.delete.messagge.confirm.cancel" /></button>
			</div>
		</div>
   		<!-- ***END*** -->
	   	<div class="row">
	   		<div class="col-md-1">
		   		<div class="form-group">
		   			<button id="loadData" name="loadData" ng-click="loadData()" class="btn btn-default" ><spring:message code="g2.form.button.update" /></button>
		   		</div>
	   		</div>
	   		<!-- ***RC 01/12/2015*** -->
	   		<div class="col-md-1">
	   			<button class="btn btn-danger" ng-click="showDialog()"><spring:message code="configuration.button.configure" /></button>
	   		</div>
	   		<!-- ***END*** -->
	   		<div class="col-md-3">
	   			<div class="form-group">
				    <div class="btn-group" dropdown is-open="status.isopen">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled" >
				        <spring:message code="g2.form.button.saveDataAs" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
							<li><a class="savePNG" id="chart-savePNG" href="#" export-chart="images" export-chart-id="chart"><spring:message code="g2.form.button.saveDataAsPNG" /></a></li>
							<!--Disabilitato <li><a class="saveSVG" href="#">Salva come SVG (per la stampa)</a></li> -->
				      </ul>
				    </div>
				    <div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				      	<spring:message code="g2.form.button.exportData" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
				        <li><a id="exportCSV" href="#"><spring:message code="g2.form.button.exportCSV" /></a></li>
				      </ul>
			     	</div>
			    </div>
		    </div>
		</div>
   	<div class="row">
	 <div class="col-md-12 area-grafico-g2">
	   <div id="chart" build-chart></div>
	 </div>
	</div>
</div>
<jsp:include page="../../common/footer.jsp" />

<!-- Utlizzato per l'export del grafico	-->
<canvas id="canvas" width="1072px" height="676px" class="ng-hide"></canvas>
</body>
  
</html>

