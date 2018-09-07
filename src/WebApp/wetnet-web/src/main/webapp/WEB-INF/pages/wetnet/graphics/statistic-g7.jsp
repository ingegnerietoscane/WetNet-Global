<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp">
    <head>
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<title><spring:message code="statistic-g7.head.title" /></title>
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
<body ng-controller="WetnetControllerG7">
	<div class="container-fluid">
 	<jsp:include page="../../common/nav.jsp" />
	
		<div class="row">
	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="startDate"><spring:message code="statistic-g7.form.startDate" /></label>
	              <input id="startDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="g7Data.startDate" is-open="openedStartDate"  />
	              <span class="input-group-btn">
	                <button type="button" class="btn btn-default" ng-click="openStartDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
	              </span>
	   			</p>
	   		</div>
	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="endDate"><spring:message code="statistic-g7.form.endDate" /></label>
	              <input id="endDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="g7Data.endDate" is-open="openedEndDate"  />
	              <span class="input-group-btn">
	                <button type="button" class="btn btn-default" ng-click="openEndDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
	              </span>
	            </p>
	        </div>

			<div class="col-md-3">
		    	<jsp:include page="../../common/datetime-selector.jsp" />
	    	</div>
	    	<!-- GC 25/11/2015 -->
	    	<div class="col-md-5">
	   					<div class="form-group">
	   					<table>
		   					<tr>
			   					<td><label for="zoneSelect" class="col-sm-1 control-label"><spring:message code="statistic-g7.form.zoneSelect.label" /></label>
			   					</td>
			   					<td>
			   					<!-- <select ng-options="z for z in zones" ng-model="filterValue" ng-change="filterDistricts('zone');"></select>-->
			   					<select  ng-model="filterValueZ" ng-change="filterDistricts('zone');">
			   					<option value = ''>--select all--</option>
        						<option ng-repeat="z in zones">{{z}}</option>
			   					</select>
			   					</td>
			   					<td><label for="waterAuthoritySelect" class="col-sm-1 control-label"><spring:message code="statistic-g7.form.waterAuthoritySelect.label" /></label>
			   					</td>
			   					<td>
			   					<!-- <select ng-options="w for w in waterAuthorities" ng-model="filterValue" ng-change="filterDistricts('waterAuthority');"></select>-->
			   					<select ng-model="filterValueW" ng-change="filterDistricts('waterAuthority');">
			   					<option  value = ''>--select all--</option>
        						<option ng-repeat="w in waterAuthorities">{{w}}</option>
			   					</select>
			   					</td>
		   					</tr>
	   					</table>
				 </div>
			</div>
   		</div>
   		
   		<div class="row">
			<div class="col-md-6">
  				<form class="form-inline">
					<!-- <div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.min-night" />" type="checkbox" ng-model="g7Data.dVariables.minNight"> <spring:message code="statistic-g7.form.min-night" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.avg-day" />" type="checkbox" ng-model="g7Data.dVariables.avgDay"> <spring:message code="statistic-g7.form.avg-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.max-day" />" type="checkbox" ng-model="g7Data.dVariables.maxDay"> <spring:message code="statistic-g7.form.max-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.min-day" />" type="checkbox" ng-model="g7Data.dVariables.minDay"> <spring:message code="statistic-g7.form.min-day" /></label></div>
					-->
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.range" />" type="checkbox" ng-model="g7Data.dVariables.range"> <spring:message code="statistic-g7.form.range" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.standard-deviation" />" type="checkbox" ng-model="g7Data.dVariables.standardDeviation"> <spring:message code="statistic-g7.form.standard-deviation" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.real-leakage" />" type="checkbox" ng-model="g7Data.dVariables.realLeakage"> <spring:message code="statistic-g7.form.real-leakage" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.volume-real-losses" />" type="checkbox" ng-model="g7Data.dVariables.volumeRealLosses"> <spring:message code="statistic-g7.form.volume-real-losses" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.high-band" />" type="checkbox" ng-model="g7Data.dVariables.highBand"> <spring:message code="statistic-g7.form.high-band" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.low-band" />" type="checkbox" ng-model="g7Data.dVariables.lowBand"> <spring:message code="statistic-g7.form.low-band" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.night-use" />" type="checkbox" ng-model="g7Data.dVariables.nightUse"> <spring:message code="statistic-g7.form.night-use" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.ied" />" type="checkbox" ng-model="g7Data.dVariables.ied"> <spring:message code="statistic-g7.form.ied" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.epd" />" type="checkbox" ng-model="g7Data.dVariables.epd"> <spring:message code="statistic-g7.form.epd" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.iela" />" type="checkbox" ng-model="g7Data.dVariables.iela"> <spring:message code="statistic-g7.form.iela" /></label></div>
				</form>
	   		</div>
			<div class="col-md-6">
				<form class="form-inline">
   					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.min-night" />" type="checkbox" ng-model="g7Data.mVariables.minNight"> <spring:message code="statistic-g7.form.min-night" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.avg-day" />" type="checkbox" ng-model="g7Data.mVariables.avgDay"> <spring:message code="statistic-g7.form.avg-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.max-day" />" type="checkbox" ng-model="g7Data.mVariables.maxDay"> <spring:message code="statistic-g7.form.max-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.min-day" />" type="checkbox" ng-model="g7Data.mVariables.minDay"> <spring:message code="statistic-g7.form.min-day" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.range" />" type="checkbox" ng-model="g7Data.mVariables.range"> <spring:message code="statistic-g7.form.range" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.standard-deviation" />" type="checkbox" ng-model="g7Data.mVariables.standardDeviation"> <spring:message code="statistic-g7.form.standard-deviation" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.high-band" />" type="checkbox" ng-model="g7Data.mVariables.highBand"> <spring:message code="statistic-g7.form.high-band" /></label></div>
					<div class="checkbox"><label><input name="<spring:message code="statistic-g7.form.low-band" />" type="checkbox" ng-model="g7Data.mVariables.lowBand"> <spring:message code="statistic-g7.form.low-band" /></label></div>
				</form>
	   		</div>
		</div>
		
   		<div class="row">
			<div class="col-md-6">
  				<!-- GC 25/11/2015 -->
				<!--<div class="form-group">
					<input tooltip="<spring:message code="statistic-g7.form.districts.tooltip" />" placeholder="<spring:message code="statistic-g7.form.districts.placeholder" />" type="text" ng-model="dSelected" typeahead-on-select="districtSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
				</div>-_>
				
				<!-- GC 25/11/2015 -->
				<div>
				<label><spring:message code="statistic-g7.form.selectedDistricts" /></label>
					<div ng-dropdown-multiselect events="filteredDistrictsEvents" options="filteredDistrictsData" selected-model="filteredDistrictsModel" extra-settings="filteredDistrictsSettings"></div>
				</div>
	   		</div>
			<div class="col-md-6">
				<!-- GC 25/11/2015 -->
				<!--<div class="form-group">
					<input tooltip="<spring:message code="statistic-g7.form.measures.tooltip" />"  placeholder="<spring:message code="statistic-g7.form.measures.placeholder" />" type="text" ng-model="mSelected" typeahead-on-select="measureSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in measures | filter:$viewValue | limitTo:measures.length" class="form-control">
		   		</div>-->
		   		
		   		<!-- GC 25/11/2015 -->
				<div>
				<label><spring:message code="statistic-g7.form.selectedMeasures" /></label>
					<div ng-dropdown-multiselect events="filteredMeasuresEvents" options="filteredMeasuresData" selected-model="filteredMeasuresModel" extra-settings="filteredMeasuresSettings"></div>
				</div>
	   		</div>
		</div>
		<br>
	   	<div class="row">
			<div class="col-md-6">
			<!-- GC 25/11/2015 -->
				<!-- <label ng-show="g7Data.districtsSelected.length > 0"><spring:message code="statistic-g7.form.selectedDistricts" /></label> -->
		    	<div  ng-repeat="item in g7Data.districtsSelected">
			    	<dl>
			    	<dt><button type="button" class="btn btn-info" tooltip="<spring:message code="statistic-g7.district.button.remove.tooltip" />"  ng-click="districtSelectedRemove(item)">{{item.name}}</button>
				    		
				    <label ng-show="item.ev_variable_type == 0"><spring:message code="statistic-g7.form.ev_variable_type.val.0" /></label>
					<label ng-show="item.ev_variable_type == 1"><spring:message code="statistic-g7.form.ev_variable_type.val.1" /></label>
					<label ng-show="item.ev_variable_type == 2"><spring:message code="statistic-g7.form.ev_variable_type.val.2" /></label>
					<label ng-show="item.ev_variable_type == 3"><spring:message code="statistic-g7.form.ev_variable_type.val.3" /></label>
					<label ng-show="item.ev_variable_type == 4"><spring:message code="statistic-g7.form.ev_variable_type.val.4" /></label>
					<label><input name="<spring:message code="statistic-g7.form.min-night" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,0)" ng-model="checkboxListTemp[item.idDistricts].minnight"> <spring:message code="statistic-g7.form.min-night" /></label>
					<label><input name="<spring:message code="statistic-g7.form.avg-day" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,1)" ng-model="checkboxListTemp[item.idDistricts].avgday"> <spring:message code="statistic-g7.form.avg-day" /></label>
					<label><input name="<spring:message code="statistic-g7.form.max-day" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,2)" ng-model="checkboxListTemp[item.idDistricts].maxday"> <spring:message code="statistic-g7.form.max-day" /></label>
					<label><input name="<spring:message code="statistic-g7.form.min-day" />" type="checkbox" ng-checked="isChekboxChecked(item.idDistricts,3)" ng-model="checkboxListTemp[item.idDistricts].minday"> <spring:message code="statistic-g7.form.min-day" /></label>
					
					<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
			    			<a href="<c:url value="/wetnet/manager/district?id={{item.idDistricts}}" />" class="btn btn-success" tooltip="<spring:message code="statistic-g7.district.button.modify-title" />"><spring:message code="statistic-g7.district.button.modify" /></a>
		    			</sec:authorize>
		    					<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
			    			<a href="<c:url value="/wetnet/manager/district?id={{item.idDistricts}}" />" class="btn btn-success" tooltip="<spring:message code="statistic-g7.district.button.show-title" />"><spring:message code="statistic-g7.district.button.modify" /></a>
		    			</sec:authorize>
		    		</dt>
			    	<dd><span ng-repeat="m in item.measures"><button type="button" class="btn btn-link" tooltip="<spring:message code="statistic-g7.measure.button.add.tooltip" />" ng-click="measureSelectedAdd(m, m, m.name)" >{{m.name}} ({{checkSign(m.sign)}})</button></span></dd>
			    	</dl>
		    	</div>
	   		</div>
			<div class="col-md-6">
				<!-- GC 25/11/2015 -->
				<!--<label ng-show="g7Data.measuresSelected.length > 0"><spring:message code="statistic-g7.form.selectedMeasures" /></label>-->
			    <div class="form-group">
			    	<div ng-repeat="item in g7Data.measuresSelected">
				    	<button type="button" class="btn btn-info"  tooltip="<spring:message code="statistic-g7.measure.button.remove.tooltip" />" ng-click="measureSelectedRemove(item)">{{item.name}}</button>
			   			<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
			   				<a href="<c:url value="/wetnet/manager/measure?id={{item.idMeasures}}" />" class="btn btn-success" tooltip="<spring:message code="statistic-g7.measure.button.modify-title" />"><spring:message code="statistic-g7.measure.button.modify" /></a>
		   				</sec:authorize>
		    			<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
		    				<a href="<c:url value="/wetnet/manager/measure?id={{item.idMeasures}}" />" class="btn btn-success" tooltip="<spring:message code="statistic-g7.measure.button.show-title" />"><spring:message code="statistic-g7.measure.button.modify" /></a>
		    			</sec:authorize>
		   			</div>
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
	   		<div class="col-md-2">
		   		<div class="form-group">
		   		<!--  	<button ng-click="loadDataForDistrict()" class="btn btn-default" ><spring:message code="statistic-g7.form.button.updateDistrict" /></button>-->
		   			<button ng-click="loadData()" class="btn btn-default" ><spring:message code="statistic-g7.form.button.update" /></button>
		   		
		   		</div>
	   		</div>
	   		<!-- ***RC 01/12/2015*** -->
	   		<div class="col-md-1">
	   			<button class="btn btn-danger" ng-click="showDialog()"><spring:message code="configuration.button.configure" /></button>
	   		</div>
	   		<!-- ***END*** -->
	   		<div class="col-md-4">
				<div class="form-group">
					<div class="btn-group" dropdown is-open="status.isopen">
					      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
					        <spring:message code="statistic-g7.form.button.saveDataAs" /><span class="caret"></span>
					      </button>
					      <ul class="dropdown-menu" role="menu">
								<li><a class="savePNG" id="g7-chart-savePNG" export-chart-id="g7-chart" export-chart="images" href="#"><spring:message code="statistic-g7.form.button.saveDataAsPNG" /></a></li>
					      </ul>
			    	</div>
			    	<div class="btn-group" dropdown is-open="status2.isopen" on-toggle="exportCSV()">
					      <button type="button" class="btn btn-primary dropdown-toggle" ng-disabled="disabled">
					        <spring:message code="statistic-g7.form.button.exportData" /><span class="caret"></span>
					      </button>
					      <ul class="dropdown-menu" role="menu">
					        <li><a id="exportCSV" href="#"><spring:message code="statistic-g7.form.button.exportCSV" /></a></li>
					      </ul>
			     	</div>
		    	</div>
	    	</div>
	   		<div class="col-md-6">
		   		<div class="form-group">
		   		<!-- 	<button ng-click="loadDataForMeasure()" class="btn btn-default" ><spring:message code="statistic-g7.form.button.updateMeasure" /></button> -->
		   		</div>
	   		</div>
	   	</div>

   	<div class="row">
	 <div class="col-md-12 area-grafico-g7">
	   <div id="g7-chart" build-chart></div>
	 </div>
	</div>
</div>
<jsp:include page="../../common/footer.jsp" />

<!-- Utlizzato per l'export del grafico	-->
<canvas id="canvas" width="1072px" height="676px" class="ng-hide"></canvas>
</body>
  
</html>

