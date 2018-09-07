<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp">
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<title><spring:message code="g8.head.title" /></title>
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
	
<body ng-controller="WetnetControllerG8">
	<div class="container-fluid">
 	<jsp:include page="../../common/nav.jsp" />
	
		<div class="row">
	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="startDate"><spring:message code="g8.form.startDate" /></label>
	              <input id="startDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="g8Data.startDate" is-open="openedStartDate"  />
	              <span class="input-group-btn">
	                <button type="button" class="btn btn-default" ng-click="openStartDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
	              </span>
	   			</p>
	   		</div>
	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="endDate"><spring:message code="g8.form.endDate" /></label>
	              <input id="endDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="g8Data.endDate" is-open="openedEndDate"  />
	              <span class="input-group-btn">
	                <button type="button" class="btn btn-default" ng-click="openEndDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
	              </span>
	            </p>
	        </div>

			<div class="col-md-4">
		    	<jsp:include page="../../common/datetime-selector.jsp" />
	    	</div>
	    	<div class="col-md-2">
		    	<button type="button" class="btn btn-info"  ng-click="showViewConfiguratorMethod()"><spring:message code="g8.district.button.configureView.title" /></button>
	    	</div>
   		</div>
   		
   		<div class="row">
   		 <div class="form-group" ng-show="showViewConfigurator">
					<jsp:include page="../../common/alerts.jsp" />
					<div class="alert alert-info" role="alert" ng-show="showViewConfigurator">
				<div class="col-md-2">
				  <label><small><spring:message code="g8.form.configurator.timeBase" /></small></label>
					   <select class="form-control" ng-model="selectedTimeBase" ng-change="filterGranularity(selectedTimeBase)">
					  	<option value="0"><spring:message code="g8.form.configurator.timeBase.value.0" /></option>
					  	<option value="1"><spring:message code="g8.form.configurator.timeBase.value.1" /></option>
					  	<option value="2"><spring:message code="g8.form.configurator.timeBase.value.2" /></option>
					  	<option value="3"><spring:message code="g8.form.configurator.timeBase.value.3" /></option>
					  </select>		  
				  </div>
					<div class="col-md-2">
			 		 <label><small><spring:message code="g8.form.configurator.granularity" /></small></label>
			  			<select ng-options="item.id as item.name for item in granularity" class="form-control" ng-model="selectedGranularity">
			  			</select>
			  			</div>
			  			
					  <br/>
					<button  ng-click="applySelectTimebaseGranularity()" class="alert-link"> <spring:message code="g8.form.button.viewConfigurator.apply" /></button> - <button ng-click="closeViewConfiguratorMethod()" class="alert-link"><spring:message code="g8.form.button.viewConfigurator.close" /></button>
				</div>
			  </div>
   		</div>
		
   		<div class="row">
			<div class="col-md-6">
  				<form class="form-inline">
					<!-- <div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.min-night" />" type="checkbox" ng-model="g8Data.dVariables.minNight"> <spring:message code="statistic-g8.form.min-night" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.avg-day" />" type="checkbox" ng-model="g8Data.dVariables.avgDay"> <spring:message code="statistic-g8.form.avg-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.max-day" />" type="checkbox" ng-model="g8Data.dVariables.maxDay"> <spring:message code="statistic-g8.form.max-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.min-day" />" type="checkbox" ng-model="g8Data.dVariables.minDay"> <spring:message code="statistic-g8.form.min-day" /></label></div>
					 -->
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.range" />" type="checkbox" ng-model="g8Data.dVariables.range"> <spring:message code="statistic-g8.form.range" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.standard-deviation" />" type="checkbox" ng-model="g8Data.dVariables.standardDeviation"> <spring:message code="statistic-g8.form.standard-deviation" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.real-leakage" />" type="checkbox" ng-model="g8Data.dVariables.realLeakage"> <spring:message code="statistic-g8.form.real-leakage" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.volume-real-losses" />" type="checkbox" ng-model="g8Data.dVariables.volumeRealLosses"> <spring:message code="statistic-g8.form.volume-real-losses" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.high-band" />" type="checkbox" ng-model="g8Data.dVariables.highBand"> <spring:message code="statistic-g8.form.high-band" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.low-band" />" type="checkbox" ng-model="g8Data.dVariables.lowBand"> <spring:message code="statistic-g8.form.low-band" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.night-use" />" type="checkbox" ng-model="g8Data.dVariables.nightUse"> <spring:message code="statistic-g8.form.night-use" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.ied" />" type="checkbox" ng-model="g8Data.dVariables.ied"> <spring:message code="statistic-g8.form.ied" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.epd" />" type="checkbox" ng-model="g8Data.dVariables.epd"> <spring:message code="statistic-g8.form.epd" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.iela" />" type="checkbox" ng-model="g8Data.dVariables.iela"> <spring:message code="statistic-g8.form.iela" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.value" />" type="checkbox" ng-model="g8Data.dVariables.value"> <spring:message code="statistic-g8.form.value" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.energy" />" type="checkbox" ng-model="g8Data.dVariables.energy"> <spring:message code="statistic-g8.form.energy" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.losses" />" type="checkbox" ng-model="g8Data.dVariables.losses"> <spring:message code="statistic-g8.form.losses" /></label></div>
				</form>
	   		</div>
			<div class="col-md-6">
				<form class="form-inline">
   					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.min-night" />" type="checkbox" ng-model="g8Data.mVariables.minNight"> <spring:message code="statistic-g8.form.min-night" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.avg-day" />" type="checkbox" ng-model="g8Data.mVariables.avgDay"> <spring:message code="statistic-g8.form.avg-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.max-day" />" type="checkbox" ng-model="g8Data.mVariables.maxDay"> <spring:message code="statistic-g8.form.max-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.min-day" />" type="checkbox" ng-model="g8Data.mVariables.minDay"> <spring:message code="statistic-g8.form.min-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.range" />" type="checkbox" ng-model="g8Data.mVariables.range"> <spring:message code="statistic-g8.form.range" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.standard-deviation" />" type="checkbox" ng-model="g8Data.mVariables.standardDeviation"> <spring:message code="statistic-g8.form.standard-deviation" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.high-band" />" type="checkbox" ng-model="g8Data.mVariables.highBand"> <spring:message code="statistic-g8.form.high-band" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.low-band" />" type="checkbox" ng-model="g8Data.mVariables.lowBand"> <spring:message code="statistic-g8.form.low-band" /></label></div>
				    <div class="checkbox"><label><input name="<spring:message code="statistic-g8.form.value" />" type="checkbox" ng-model="g8Data.mVariables.value"> <spring:message code="statistic-g8.form.value" /></label></div>
				 </form>
	   		</div>
		</div>
   		<div class="row">
			<div class="col-md-6">
  				<div class="form-group">
					<input tooltip="<spring:message code="g8.form.districts.tooltip" />" placeholder="<spring:message code="g8.form.districts.placeholder" />" type="text" ng-model="dSelected" typeahead-on-select="districtSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
				</div>
	   		</div>
			<div class="col-md-6">
				<div class="form-group">
					<input tooltip="<spring:message code="g8.form.measures.tooltip" />"  placeholder="<spring:message code="g8.form.measures.placeholder" />" type="text" ng-model="mSelected" typeahead-on-select="measureSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in measures | filter:$viewValue | limitTo:measures.length" class="form-control">
		   		</div>
	   		</div>
		</div>
	   	<div class="row">
			<div class="col-md-6">
		
		    	<div  ng-repeat="item in g8Data.districtsSelected">
			    	<dl>
			    	<dt><button type="button" class="btn btn-info" tooltip="<spring:message code="g8.district.button.remove.tooltip" />"  ng-click="districtSelectedRemove(item)">{{item.name}}</button>
				    	
				    	<label ng-show="item.ev_variable_type == 0"><spring:message code="statistic-g8.form.ev_variable_type.val.0" /></label>
						<label ng-show="item.ev_variable_type == 1"><spring:message code="statistic-g8.form.ev_variable_type.val.1" /></label>
						<label ng-show="item.ev_variable_type == 2"><spring:message code="statistic-g8.form.ev_variable_type.val.2" /></label>
						<label ng-show="item.ev_variable_type == 3"><spring:message code="statistic-g8.form.ev_variable_type.val.3" /></label>
						<label ng-show="item.ev_variable_type == 4"><spring:message code="statistic-g8.form.ev_variable_type.val.4" /></label>
						<label><input name="<spring:message code="statistic-g8.form.min-night" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,0)" ng-model="checkboxListTemp[item.idDistricts].minnight"> <spring:message code="statistic-g8.form.min-night" /></label>
						<label><input name="<spring:message code="statistic-g8.form.avg-day" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,1)" ng-model="checkboxListTemp[item.idDistricts].avgday"> <spring:message code="statistic-g8.form.avg-day" /></label>
						<label><input name="<spring:message code="statistic-g8.form.max-day" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,2)" ng-model="checkboxListTemp[item.idDistricts].maxday"> <spring:message code="statistic-g8.form.max-day" /></label>
						<label><input name="<spring:message code="statistic-g8.form.min-day" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,3)" ng-model="checkboxListTemp[item.idDistricts].minday"> <spring:message code="statistic-g8.form.min-day" /></label>
						
					<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
			    			<a href="<c:url value="/wetnet/manager/district?id={{item.idDistricts}}" />" class="btn btn-success" tooltip="<spring:message code="g8.district.button.modify-title" />"><spring:message code="g8.district.button.modify" /></a>
		    			</sec:authorize>
		    			<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
			    			<a href="<c:url value="/wetnet/manager/district?id={{item.idDistricts}}" />" class="btn btn-success" tooltip="<spring:message code="g8.district.button.show-title" />"><spring:message code="g8.district.button.modify" /></a>
		    			</sec:authorize>
			    	</dt>
			    	<dd><span ng-repeat="m in item.measures"><button tooltip="<spring:message code="g8.measure.button.add.tooltip" />" type="button" class="btn btn-link" ng-click="measureSelectedAdd(m, m, m.name)" >{{m.name}} ({{checkSign(m.sign)}})</button></span></dd>
			    	</dl>
		    	</div>
	   		</div>
			<div class="col-md-6">
			    <div class="form-group">
			    	<button type="button" class="btn btn-info"  tooltip="<spring:message code="g8.measure.button.remove.tooltip" />"  ng-repeat="item in g8Data.measuresSelected" ng-click="measureSelectedRemove(item)">{{item.name}}</button>
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
		   			<button id="loadData" name="loadData" ng-click="loadData()" class="btn btn-default" ><spring:message code="g8.form.button.update" /></button>
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
				        <spring:message code="g8.form.button.saveDataAs" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
							<li><a class="savePNG" id="g8-chart-savePNG" href="#" export-chart="images" export-chart-id="g8-chart"><spring:message code="g8.form.button.saveDataAsPNG" /></a></li>
							<!--Disabilitato <li><a class="saveSVG" href="#">Salva come SVG (per la stampa)</a></li> -->
				      </ul>
				    </div>
				    <div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
				      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
				      	<spring:message code="g8.form.button.exportData" /><span class="caret"></span>
				      </button>
				      <ul class="dropdown-menu" role="menu">
				        <li><a id="exportCSV" href="#"><spring:message code="g8.form.button.exportCSV" /></a></li>
				      </ul>
			     	</div>
			    </div>
		    </div>
		</div>
   	<div class="row">
	 <div class="col-md-12 area-grafico-g8">
	   <div id=g8-chart build-chart></div>
	 </div>
	</div>
</div>
<jsp:include page="../../common/footer.jsp" />

<!-- Utlizzato per l'export del grafico	-->
<canvas id="canvas" width="1072px" height="676px" class="ng-hide"></canvas>
</body>
  
</html>

