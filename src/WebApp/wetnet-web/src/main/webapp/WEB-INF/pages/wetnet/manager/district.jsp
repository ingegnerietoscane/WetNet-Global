<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
	<link href="<c:url value="/css/map-edit.css" />" type="text/css" rel="stylesheet"/>
	<!-- <script src="https://maps.google.com/maps/api/js?v=3&amp;sensor=false"></script> -->
<script src="https://maps.google.com/maps/api/js?v=3"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/OpenLayer.js"></script>
	 <script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/crypto_md5.js"></script>
	 
	 <!-- disabilita il tasto enter per evitare l'invio del submit -->
	 <script type="text/javascript" src="${pageContext.request.contextPath}/script/disableEnterKey.js"></script>
	 
</head>
<body>
  
<div class="container-fluid" ng-controller="ManagerDistrictController">

<jsp:include page="../../common/nav.jsp" />

	<form class="form" role="form" name="districtForm">
	
	<div class="panel panel-default">
		<div class="panel-body">
 			  <div class="form-group">
					<jsp:include page="../../common/alerts.jsp" />
					<div class="alert alert-info" role="alert" ng-show="confirmedAction">
					<spring:message code="district.form.delete.messagge.confirm" /><br/>
					<button  ng-click="remove(district)" class="alert-link"> <spring:message code="district.form.delete.messagge.confirm.ok" /></button> - <button ng-click="removeCancelled()" class="alert-link"><spring:message code="district.form.delete.messagge.confirm.cancel" /></button>
					</div>
			  </div>			  
			  <div class="form-group">
			      <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				      <button class="btn btn-success" ng-click="save(district)"><spring:message code="district.form.save.button" /></button>
				      <button class="btn btn-danger" ng-click="removeConfirm()" ng-show="district.idDistricts"><spring:message code="district.form.delete.button" /></button>
			      </sec:authorize>
			      <button class="btn btn-info" ng-click="cancel()"><spring:message code="district.form.cancel.button" /></button>
			  </div>
		  </div>
	</div>			
	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row">
  			  <div class="col-md-5"><!-- riga 1 -->
				  <div class="form-group">
				    <label for="name"><small><spring:message code="district.form.id.label" /></small></label>
				    <div>
				      <p>{{district.idDistricts}}</p>
				    </div>
				  </div>
			  </div>			  
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="unitary_phisiological_nigth_demand" class="text-nowrap"><small><spring:message code="district.form.unitary_phisiological_nigth_demand.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="unitary_phisiological_nigth_demand" ng-model="district.unitary_phisiological_nigth_demand" />
				    </div>
				  </div>
			  </div>
			  <!-- ***RC 03/11/2015*** -->
			  <div class="col-md-2">
				  <div class="form-group  form-group-sm">
				    <label for="alpha_emitter_exponent"><small><spring:message code="district.form.alpha_emitter_exponent.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="alpha_emitter_exponent" ng-model="district.alpha_emitter_exponent" />
				    </div>
				  </div>			  
			  </div>
			  <!-- ***END*** -->
			 </div>
 			  
 			<div class="row"><!-- riga 2 -->
  			  <div class="col-md-5">
 				  <div class="form-group">
				    <label for="name"><small><spring:message code="district.form.name.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="name" ng-model="district.name" required/>
				    </div>
				  </div>
			  </div>
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="properties"><small><spring:message code="district.form.properties.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="properties" ng-model="district.properties" />
				    </div>
				  </div>
			  </div>
			  <!-- ***RC03/11/2015*** -->
			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="aeegCode"><small><spring:message code="district.form.aeegCode.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="aeegCode" ng-model="district.aeegCode" />
				    </div>
				  </div>  			  
			  </div>
			  <!-- ***END*** -->
			 </div>
 			
 			
 			<div class="row"><!-- riga 3 -->
  			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="zone"><small><spring:message code="district.form.zone.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="zone" ng-model="district.zone" required/>
				    </div>
				  </div>  			  
			  </div>
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="rewarded_water"><small><spring:message code="district.form.rewarded_water.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="rewarded_water" ng-model="district.rewarded_water" />
				    </div>
				  </div>			  
			  </div>
			  <!-- ***RC03/11/2015*** -->
			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="gisCode"><small><spring:message code="district.form.gisCode.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="gisCode" ng-model="district.gisCode" />
				    </div>
				  </div>  			  
			  </div>
			  <!-- ***END*** -->
			 </div>
			 
  			 <div class="row"><!-- riga 4 -->
  			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="class"><small><spring:message code="district.form.class.label" /></small></label>
				    <div>
				    	<select id="class" class="form-control" ng-model="district.dClass" >
					    	<option value="0">A</option>
					    	<option value="1">B</option>
					    	<option value="2">C</option>
				    	</select>
				    </div>
				  </div>  			  
			  </div>
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="billing"><small><spring:message code="district.form.billing.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="billing" ng-model="district.billing" />
				    </div>
				  </div>			  
			  </div>
			  <!-- ***RC03/11/2015*** -->
			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="withdrawalOperationalArea"><small><spring:message code="district.form.withdrawalOperationalArea.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="withdrawalOperationalArea" ng-model="district.withdrawalOperationalArea" />
				    </div>
				  </div>  			  
			  </div>
			  <!-- ***END*** -->
			 </div>
			 
			 <!-- ***RC 03/11/2015*** -->
			 <div class="row"><!-- riga 5 -->
  			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="waterAuthority"><small><spring:message code="district.form.waterAuthority.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="waterAuthority" ng-model="district.waterAuthority" />
				    </div>
				  </div>  			  
			  </div>
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="not_household_night_use"><small><spring:message code="district.form.not_household_night_use.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="not_household_night_use" ng-model="district.not_household_night_use" />
				    </div>
				  </div>			  
			  </div>
			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="withdrawalArea"><small><spring:message code="district.form.withdrawalArea.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="withdrawalArea" ng-model="district.withdrawalArea" />
				    </div>
				  </div>  			  
			  </div>
			 </div>			 
			 <!-- ***END*** -->
			 
  			<div class="row"><!-- riga 6 -->
  			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="municipality"><small><spring:message code="district.form.municipality.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="municipality" ng-model="district.municipality" />
				    </div>
				  </div>  			  
			  </div>
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="length_main"><small><spring:message code="district.form.length_main.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="length_main" ng-model="district.length_main" />
				    </div>
				  </div>			  
			  </div>
			  <!-- ***RC03/11/2015*** -->
			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="type"><small><spring:message code="district.form.type.label" /></small></label>
				    <div>
				    	<select id="type" class="form-control" ng-model="district.type" >
					    	<option value="0"><spring:message code="district.form.type.option1.label" /></option>
					    	<option value="1"><spring:message code="district.form.type.option2.label" /></option>
					    	<option value="2"><spring:message code="district.form.type.option3.label" /></option>
				    	</select>
				    </div>
				  </div>  			  
			  </div>
			  <!-- ***END*** -->
			 </div>
			 
			 
  			<div class="row"><!-- riga 7 -->
  			  <div class="col-md-5">
  			  	  <div class="form-group form-group-sm">
				    <label for="inhabitants"><small><spring:message code="district.form.inhabitants.label" /></small></label>
				    <div>
				      <input type="number" class="form-control input-sm" id="inhabitants" ng-model="district.inhabitants" />
				    </div>
				  </div>  			  
			  </div>
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="average_zone_night_pressure"><small><spring:message code="district.form.average_zone_night_pressure.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="average_zone_night_pressure" ng-model="district.average_zone_night_pressure" />
				    </div>
				  </div>			  
			  </div>
			 </div>
			 
  			<div class="row"><!-- riga 8 -->
  			  	<div class="col-md-2">
				    <label for="update_timestamp"><spring:message code="district.form.update_timestamp.label" /></label>
		            <p class="input-group bootstrap-datepicker-correction">
		              <input  type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="district.update_timestamp" is-open="openedDatePicker_update_timestamp"  id="update_timestamp"/>
		              <span class="input-group-btn">
		                <button type="button" class="btn btn-default" ng-click="openDatePicker_update_timestamp($event)"><i class="glyphicon glyphicon-calendar"></i></button>
		              </span>
		            </p>
	            </div>
		        <div class="col-md-3">
		            <timepicker ng-model="district.update_timestamp"  show-meridian="false"></timepicker>
		        </div>
			  
			  <!-- ***RC 03/11/2015*** -->
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="household_night_use" ><small><spring:message code="district.form.household_night_use.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="household_night_use" ng-model="district.household_night_use"  />
				      <button class="btn btn-info" ng-click="compute_household_night_use()"><spring:message code="district.form.compute_household_night_use.button" /></button>
				    </div>
				  </div>			  
			  </div>
			  <!-- ***END*** -->
			 </div>
		</div>
	</div>	
	
	
	<!-- Pannello  Analytics Data -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.panel.analyticsdata" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row">
  			  <!-- ***RC 27/11/2015*** -->
  				<div class="alert alert-success alert-dismissible" ng-show="alertReset.success" role="alert">
					<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<spring:message code="district.analyticsData.reset.success" />
				</div>
				<div class="alert alert-warning alert-dismissible" ng-show="alertReset.warning" role="alert">
					<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
					<spring:message code="district.analyticsData.reset.warning" />
				</div>
  			</div>
  			<div class="row">
	  			<div class="alert alert-info" role="alert" ng-show="confirmedReset">
					<spring:message code="district.form.reset.messagge.confirm" /><br/><button class="alert-link" ng-click="resetDistrict()"><spring:message code="district.form.delete.messagge.confirm.ok" /></button> - <button ng-click="resetCancelled()" class="alert-link"><spring:message code="district.form.delete.messagge.confirm.cancel" /></button>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2">
				    <label for="reset_timestamp"><spring:message code="district.analyticsData.label" /></label>
		            <p class="input-group bootstrap-datepicker-correction">
		              <input  type="text" class="form-control" ng-change="calcMD5()" datepicker-popup="yyyy-MM-dd" ng-model="reset_timestamp" is-open="openedDatePicker_reset_timestamp"  id="reset_timestamp"/>
		              <span class="input-group-btn">
		                <button type="button" class="btn btn-default" ng-click="openDatePicker_reset_timestamp($event)"><i class="glyphicon glyphicon-calendar"></i></button>
		              </span>
		            </p>
		            <br>
		           <button class="btn btn-danger" ng-click="showResetDialog()"><spring:message code="district.analyticsData.button" /></button>
	            </div>
	            
	            
	            <input type="hidden" name="ninjaURL" id="ninjaURL" value="{{resetURL}}?{{resetURLparam1}}={{district.idDistricts}}&{{resetURLparam2}}={{trimmedTimestamp}}&{{resetURLparam3}}={{checkMD5}}">
	            <!-- <input type="hidden" name="ninjaURL" id="ninjaURL" value="<spring:message code="district.reset.url" arguments="{{district.idDistricts}},{{trimmedTimestamp}},{{checkMD5}}" />"> -->
			</div>
			
			
			<div class="row">
  			  <div class="col-md-1">
				    <label><small><spring:message code="district.form.min_night_time.label" /></small></label>
			  </div>			  
  			  <div class="col-md-1 text-nowrap">
				    <label for="min_night_start_time"><small><spring:message code="district.form.min_night_start_time.label" /></small></label>
			        <timepicker ng-model="district.min_night_start_time" id="min_night_start_time" show-meridian="false" ></timepicker>
			  </div>			  
			  <div class="col-md-1 text-nowrap">
				    <label for="min_night_stop_time"><small><spring:message code="district.form.min_night_stop_time.label" /></small></label>
				    <timepicker ng-model="district.min_night_stop_time" id="min_night_stop_time" show-meridian="false"></timepicker>
			  </div>  
			</div>
			
			<!-- riga 2 -->
			<!-- /* GC - 22/10/2015 */
        	<div class="row">
  			  <div class="col-md-1">
				    <label for="max_day_time"><small><spring:message code="district.form.max_day_time.label" /></small></label>
			  </div>			  
  			  <div class="col-md-1 text-nowrap">
				    <label for="max_day_start_time_1"><small><spring:message code="district.form.max_day_start_time_1.label" /></small></label>
			        <timepicker ng-model="district.max_day_start_time_1" id="max_day_start_time_1" show-meridian="false" ></timepicker>
				</div>
				<div class="col-md-1 text-nowrap">
				    <label for="max_day_stop_time_1"><small><spring:message code="district.form.max_day_stop_time_1.label" /></small></label>
				    <timepicker ng-model="district.max_day_stop_time_1" id="max_day_stop_time_1" show-meridian="false" ></timepicker>
			  </div>
			 <div class="col-md-1 text-nowrap">
				    <label for="max_day_start_time_2"><small><spring:message code="district.form.max_day_start_time_2.label" /></small></label>
			        <timepicker ng-model="district.max_day_start_time_2" id="max_day_start_time_1" show-meridian="false" ></timepicker>
			  </div>			  
			  <div class="col-md-1 text-nowrap">
				    <label for="max_day_stop_time_2"><small><spring:message code="district.form.max_day_stop_time_2.label" /></small></label>
				    <timepicker ng-model="district.max_day_stop_time_2" id="max_day_stop_time_2" show-meridian="false" ></timepicker>
			  </div>
  			 <div class="col-md-1 text-nowrap">
				    <label for="max_day_start_time_3"><small><spring:message code="district.form.max_day_start_time_3.label" /></small></label>
			        <timepicker ng-model="district.max_day_start_time_3" id="max_day_start_time_3" show-meridian="false" ></timepicker>
			  </div>			  
			  <div class="col-md-1 text-nowrap">
				    <label for="max_day_stop_time_3"><small><spring:message code="district.form.max_day_stop_time_3.label" /></small></label>
				    <timepicker ng-model="district.max_day_stop_time_3" id="max_day_stop_time_3" show-meridian="false" ></timepicker>
			  </div>
			 </div>
			 -->
 			
		</div>
	</div>	
	
	
	<!-- Pannello Events -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.panel.events" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row"><!-- riga 1 -->
				<div class="col-md-1">
   				  <div class="form-group form-group-sm">
				    <label for="ev_bands_autoupdate"><small><spring:message code="district.form.ev_bands_autoupdate.label" /></small></label>
				    <div>
				      <input type="checkbox" class="form-control input-sm" id="ev_bands_autoupdate" ng-model="district.ev_bands_autoupdate" />
				    </div>
				  </div>			  
			  	</div>
   			  	<div class="col-md-1">
   				  <div class="form-group form-group-sm">
				    <label for="ev_enable"><small><spring:message code="district.form.ev_enable.label" /></small></label>
				    <div>
				      <input type="checkbox" class="form-control input-sm" id="ev_enable" ng-model="district.ev_enable" />
				    </div>
				  </div>			  
			  	</div>
			</div>
			
			<div class="row"><!-- riga 2 -->			
  			  <div class="col-md-1">
   				  <div class="form-group form-group-sm">
				    <label for="ev_high_band"><small><spring:message code="district.form.ev_high_band.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="ev_high_band" ng-model="district.ev_high_band" />
				    </div>
				  </div>			  
			  </div>
   			  <div class="col-md-1">
   				  <div class="form-group form-group-sm">
				    <label for="ev_low_band"><small><spring:message code="district.form.ev_low_band.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="ev_low_band" ng-model="district.ev_low_band" />
				    </div>
				  </div>			  
			  </div>		
  			  <div class="col-md-2">
   				  <div class="form-group form-group-sm">
				      <label for="ev_low_band"><small><spring:message code="district.form.use_statistic_band_values.label" /></small></label>
				      <div>
				      <button class="btn btn-info" ng-click="use_statistic_band_values()"><spring:message code="district.form.use_statistic_band_values.button" /></button>
				      </div>
				  </div>			  
			  </div>			  			  			  
			  	  			  
   			  <div class="col-md-2">
   				  <div class="form-group form-group-sm">
				    <label for="ev_variable_type"><small><spring:message code="district.form.ev_variable_type.label" /></small></label>
				    <div>
				      <select  id="ev_variable_type" ng-model="district.ev_variable_type" class="form-control">
				      	<option value="0"><spring:message code="district.form.ev_variable_type.val.0" /></option>
				      	<option value="1"><spring:message code="district.form.ev_variable_type.val.1" /></option>
				      	<option value="2"><spring:message code="district.form.ev_variable_type.val.2" /></option>
				      	<option value="3"><spring:message code="district.form.ev_variable_type.val.3" /></option>
				      	<option value="4"><spring:message code="district.form.ev_variable_type.val.4" /></option>
				      </select>
				    </div>
				  </div>			  
			  </div>			  			  
 			</div>
			<div class="row"><!-- riga 3 -->
   			
			
  			  <div class="col-md-1">
   				  <div class="form-group form-group-sm">
				    <label for="ev_statistic_high_band"><small><spring:message code="district.form.ev_statistic_high_band.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" disabled="disabled" class="form-control input-sm" id="ev_statistic_high_band" ng-model="district.ev_statistic_high_band" />
				    </div>
				  </div>			  
			  </div>
   			  <div class="col-md-1">
   				  <div class="form-group form-group-sm">
				    <label for="ev_statistic_low_band"><small><spring:message code="district.form.ev_statistic_low_band.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" disabled="disabled" class="form-control input-sm" id="ev_statistic_low_band" ng-model="district.ev_statistic_low_band" />
				    </div>
				  </div>			  
			  </div>	
		      <div class="col-md-2">
			  </div>		  			  
   			  <div class="col-md-2">
   				  <div class="form-group form-group-sm">
				    <label for="ev_last_good_sample_day"><small><spring:message code="district.form.ev_last_good_sample_day.label" /></small></label>
				    <div class="input-group">
			              <input required type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="district.ev_last_good_sample_day" is-open="openedDatePickerev_last_good_sample_day"  close-text="Close" id="ev_last_good_sample_day"/>
			              <span class="input-group-btn">
			                <button type="button" class="btn btn-default" ng-click="openDatePickerev_last_good_sample_day($event)"><i class="glyphicon glyphicon-calendar"></i></button>
			              </span>
				    </div>
				  </div>			  
			  </div>			  			  
   			  <div class="col-md-2">
   				  <div class="form-group form-group-sm">
				    <label for="ev_last_good_samples"><small><spring:message code="district.form.ev_last_good_samples.label" /></small></label>
				    <div>
				      <input type="number"  step="0.01" class="form-control input-sm" id="ev_last_good_samples" ng-model="district.ev_last_good_samples" />
				    </div>
				  </div>			  
			  </div>			  			  			  
 			</div>
			<div class="row"><!-- riga 4 -->
  			  <div class="col-md-4">
			  </div>
		  
   			  <div class="col-md-2">
   				  <div class="form-group form-group-sm">
				    <label for="ev_alpha"><small><spring:message code="district.form.ev_alpha.label" /></small></label>
				    <div>
				      <input type="number"  step="0.01" class="form-control input-sm" id="ev_alpha" ng-model="district.ev_alpha" />
				    </div>
				  </div>			  
			  </div>			  			  
   			  <div class="col-md-2">
   				  <div class="form-group form-group-sm">
				    <label for="ev_samples_trigger"><small><spring:message code="district.form.ev_samples_trigger.label" /></small></label>
				    <div>
				      <input type="number"  step="0.01" class="form-control input-sm" id="ev_samples_trigger" ng-model="district.ev_samples_trigger" />
				    </div>
				  </div>			  
			  </div>			  			  			  
 			</div>
 			
 			
 			<!-- GC 12/11/2015 -->
 			<div class="row"><!-- riga 5 -->
  			  <div class="col-md-2">
   				  <div class="form-group form-group-sm">
				      <div>
				      <button class="btn btn-info"  ng-click="openBandsHistory()"><spring:message code="district.form.open_band_history.button" /></button>
				      </div>
				  </div>			  
			  </div>
			  <div class="col-md-4" ng-show="showBandsHistory && district.idDistricts && districtsBandsHistorySize > 0">
   				  <div class="form-group form-group-sm">
				      <div>
				     <div class="table-responsive" style="overflow-y:scroll;height:350px;">
			  <table class="table table-bordered wetnet-table-head" style="background: white;">
			  <thead>
				  <tr>
					<th><spring:message code="districts.bands.form.table.timestamp" /></th>
					<th><spring:message code="districts.bands.form.table.high_band" /></th>
					<th><spring:message code="districts.bands.form.table.low_band" /></th>
				 </tr>
			  </thead>
			   <tbody>
				  <tr ng-repeat="dB in districtsBandsHistory">	  
				  	<td align="center">{{dB.timestamp  | date:'yyyy-MM-dd HH:mm:ss'}}</td>
				  	<td align="center">{{dB.highBand}}</td>
				  	<td align="center">{{dB.lowBand}}</td>
				  </tr>
				  </tbody>
			  </table>
			  </div>
				      </div>
				  </div>			  
			  </div>	
			  <div class="col-md-3" ng-show="showBandsHistory && district.idDistricts && districtsBandsHistorySize == 0">
			  	<h2>
				 <spring:message code="district.bandsHistory.result" />
				</h2> 
				</div>		  			  
   			</div>
   			
   			
		</div>
	</div>	
	
	<!-- Pannello Sap -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.panel.sap" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row"><!-- riga 1 -->
  			  <div class="col-md-2">
				    <label for="sap_code"><small><spring:message code="district.form.sap_code.label" /></small></label>
				    <input type="text" class="form-control input-sm" id="sap_code" ng-model="district.sap_code"/>
			  </div>			  
			</div>
	
		</div>
	</div>
	
	
	<!-- Pannello Misure -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.panel.measures" /></h3>
  		</div>
  		<div class="panel-body">
  			<div class="row">
  			<div class="alert alert-success alert-dismissible" ng-show="alertMeasures.success" role="alert">
				<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				{{alertMeasures.success}}</div>
				<div class="alert alert-warning alert-dismissible" ng-show="alertMeasures.warning" role="alert">
				<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				{{alertMeasures.warning}}</div>
  			</div>
			<div class="row">
			<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
  			  <div class="col-md-5">
  			  <div class="table-responsive">
  			  	<input type="text" class="form-control input-sm" placeholder="<spring:message code="district.form.search.input.placeholder" />" name="ricercaMisura" id="ricercaMisura" ng-model="ricercaMisura" ></input>
				<table class="table table-condensed">
				  <thead><tr><th><spring:message code="district.form.measures.table.name" /></th></tr></thead>
				  <tbody>
				  <tr ng-repeat="m in measures | startFrom:currentPageMeasures* itemsPerPageMeasures | limitTo:itemsPerPageMeasures | filter:ricercaMisura">
				  <td>{{m.name}}</td>			  
				  	<td><button ng-click="addMeasures(m)" title="<spring:message code="district.form.measures.add" />" alt="<spring:message code="district.form.measures.add" />" type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-chevron-right"></span></button></td>				  
				  </tr>
				  </tbody>
				</table>
  			  	<pagination boundary-links="true" total-items="totalItemsMeasures" items-per-page="itemsPerPageMeasures" ng-model="currentPageMeasures"  class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></pagination>
			  </div>			  
			  </div>
		    
			  <div class="col-md-2">
			  <label><small><spring:message code="district.form.measures.table.sign" /></small></label>
			  <select class="form-control" ng-model="selectedMeasuresSign">
			  	<option value="0"><spring:message code="district.form.measures.table.sign.value.0" /></option>
			  	<option value="1"><spring:message code="district.form.measures.table.sign.value.1" /></option>
			  	<option value="2"><spring:message code="district.form.measures.table.sign.value.2" /></option>
			  </select>		  
			  </div>
			</sec:authorize>  
			
			  <div class="col-md-5">
  			  <div class="table-responsive">
  			  	<label><spring:message code="district.form.measures.table.measuresLinked" /></label>
				<table class="table table-condensed">
				  <thead><tr><th><small><spring:message code="district.form.measures.table.name" /></small></th><th><small><spring:message code="district.form.measures.table.sign" /></small></th><th></th></tr></thead>
				  <tbody>
				  <tr ng-repeat="m in districtMeasures">
				  <td>{{m.measures_name}}</td>
				  <td>{{m.sign}}</td>
				  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				  	<td><button ng-click="removeMeasures(m)" title="<spring:message code="district.form.measures.add" />" alt="<spring:message code="district.form.measures.add" />" type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-trash"></span></button></td>
				  </sec:authorize>  
				  </tr>
				  </tbody>
				</table>
			   </div>			  
			   </div>
			</div>
	
		</div>
	</div>
	
	<!-- Pannello Mappa -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.panel.map" /></h3>
  		</div>
  		<div class="panel-body">
  			<div class="row">
  			<!-- ***RC03/11/2015*** -->
			  <div class="col-md-1">
  			  	  <div class="form-group form-group-sm">
				    <label for="mapLevel"><small><spring:message code="district.form.mapLevel.label" /></small></label>
				    <div>
				    	<select id="mapLevel" class="form-control" ng-model="district.mapLevel" >
					    	<option value="1">1</option>
					    	<option value="2">2</option>
					    	<option value="3">3</option>
					    	<option value="4">4</option>
				    	</select>
				    </div>
				  </div>  			  
			  </div>
			  <!-- ***END*** -->
  			</div>
			<div class="row">
				<div class="col-md-8">
					<div id="map" class="smallmap"></div>
				</div>
		        <div class="col-md-4">
				    <div id="controls">
				        <ul id="controlToggle">
				            <li>
				                <input type="radio" name="type" value="none" id="noneToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="noneToggle"> <spring:message code="district.form.panel.map.navigate" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="point" id="pointToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="pointToggle"> <spring:message code="district.form.panel.map.drawpoint" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="line" id="lineToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="lineToggle"> <spring:message code="district.form.panel.map.drawline" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="polygon" id="polygonToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="polygonToggle"> <spring:message code="district.form.panel.map.drawpolygon" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="regular" id="regularToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="regularToggle"> <spring:message code="district.form.panel.map.drawregularpolygon" /></label>
				                <label for="sides"> - <spring:message code="district.form.panel.map.sides" /></label>
				                <input id="sides" type="text" size="2" maxlength="2"
				                       name="sides" ng-change="update()" ng-model="sides"/>
				                <ul>
				                    <li>
				                        <input id="irregular" type="checkbox"
				                               name="irregular" ng-change="update()" ng-model="irregular"/>
				                        <label for="irregular"> <spring:message code="district.form.panel.map.irregular" /></label>
				                    </li>
				                </ul>
				            </li>
				            <li>
				                <input type="radio" name="type" value="modify" id="modifyToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="modifyToggle"> <spring:message code="district.form.panel.map.modifyfeature" /></label>
				                <ul>
				                    <li>
				                        <input id="createVertices" type="checkbox"
				                               name="createVertices" ng-change="update()" ng-model="createVertices"/>
				                        <label for="createVertices"> <spring:message code="district.form.panel.map.allowverticescreation" /></label>
				                    </li>
				                    <li>
				                        <input id="rotate" type="checkbox"
				                               name="rotate" ng-change="update()" ng-model="rotate"/>
				                        <label for="rotate"> <spring:message code="district.form.panel.map.allowrotation" /></label>
				                    </li>
				                    <li>
				                        <input id="resize" type="checkbox"
				                               name="resize" ng-change="update()" ng-model="resize"/>
				                        <label for="resize"> <spring:message code="district.form.panel.map.allowresizing" /></label>
				                        (<input id="keepAspectRatio" type="checkbox"
				                               name="keepAspectRatio" ng-change="update()" ng-model="keepAspectRatio"/>
				                        <label for="keepAspectRatio"> <spring:message code="district.form.panel.map.keepaspectratio" /></label>)
				                    </li>
				                    <li>
				                        <input id="drag" type="checkbox"
				                               name="drag" ng-change="update()" ng-model="drag"/>
				                        <label for="drag"> <spring:message code="district.form.panel.map.allowdragging" /></label>
				                    </li>
				                </ul>
				            </li>
				            <li><button class="btn btn-danger" ng-click="removeLayer()"><spring:message code="district.form.panel.map.remove" /></button></li>
				        </ul>
				    </div>
		   		</div>
			</div>
		</div>
	</div>

	<!-- Pannello Note -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.panel.notes" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row"><!-- riga 1 -->
  			  <div class="col-md-8">
				    <label for="notes"><small><spring:message code="district.form.notes.label" /></small></label>
				    <textarea rows="5" class="form-control input-sm" id="notes" ng-model="district.notes"></textarea>
			  </div>			  
			</div>
	
		</div>
	</div>
	
			</form>
			
			<!-- ***RC 06/11/2015*** -->
	<!-- Pannello File upload -->
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="district.form.fileUpload.label" /></h3>
  		</div>
  		<div class="panel-body">
  			<div class="col-md-4">
  				<form method="POST" action="districtUpload" id="upForm" name="upForm" enctype="multipart/form-data" class="form-inline" role="form">
				<div class="row">
						<label for="fileDescription"><small><spring:message code="district.form.fileUpload.description.label" /></small></label>
						<div>
							<input type="text" class="form-control input-sm" id="fileDescription" name="fileDescription"/>
						</div>
				</div>
				<br>
				<div class="row">
						<label for="fileUri"><small><spring:message code="district.form.fileUpload.uri.label" /></small></label>
						<div>
							<input type="text" value="file://" class="form-control input-sm" id="fileUri" name="fileUri"/>
						</div>
				</div>	
				<br>
				<div class="row">
						<input class="btn btn-primary" type="file" name="file" id="fileUp" title="<spring:message code="district.form.fileUpload.button.browse" />">
				</div>
				<br>
				<div class="row">
						<div class="form-group">
							<a class="btn btn-primary" onclick="check();" ><spring:message code="district.form.fileUpload.button.upload" /></a>
						</div>
				</div>
				<input type="hidden" id="hiddenId" name="hiddenId" value="{{district.idDistricts}}">
			</form>	
  			</div>
  			<div class="col-md-8">
  					<label for="notes"><small><spring:message code="district.table.label" arguments="{{districtFiles.length}}"/></small></label>
				    <div class="table-responsive" style="height:250px;overflow-y: scroll;">
				    	<table class="table table-bordered wetnet-table-head" style="background: white;">
				    		<thead>
								<tr>
									<th><spring:message code="district.table.header.file" /></th>
									<th><spring:message code="district.table.header.timestamp" /></th>
									<th><spring:message code="district.table.header.uri" /></th>
									<th><spring:message code="district.table.header.description" /></th>
<!-- 									<th></th> -->
									<th></th>
								</tr>
						  	</thead>
						  	<tbody>
								<tr ng-repeat="f in districtFiles">
							  		<td align="center">{{f.fileName}}</td>
							    	<td align="center">{{f.loadTimestamp | date:'yyyy-MM-dd HH:mm'}}</td>
							    	<td align="center">{{f.fileUri}}</td>
							    	<td align="center">{{f.description}}</td>
<%-- 							    	<td align="center"><button class="btn btn-success" ng-click="downloadFile(f)"><spring:message code="district.form.save.button"/></button></td> --%>
							    	<td align="center">
							    		 <form id="downloadFile" name="downloadFile" method="get" action="/wetnet/rest/files/getFileById">
                                           <input type="hidden" id="idFile" name="idFile" value="{{f.idFile}}"></input>
                                             <input type="hidden" id="resourceType" name="resourceType" value="district"></input> 
                                             <input  class="btn btn-success" type="submit" value="<spring:message code="district.form.save.button"/>"/>
<%--                                              <button class="btn btn-success" onClick="submit(this);"><spring:message code="district.form.save.button"/></button> --%>
                                        </form>	
							    	</td>
							  	</tr>
  							</tbody>
						</table>
						
				    </div>
  			</div>
		</div>	  
	</div>
	<!-- ***END*** -->
			
			</div>
<script>
	function check(){
		var fileValue = document.getElementById("fileUp").value;
		if(fileValue.length>0){
			document.getElementById("upForm").submit();
		}
	} 
</script>
  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
