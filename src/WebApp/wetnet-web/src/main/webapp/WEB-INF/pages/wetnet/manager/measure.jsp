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
	
	 <!-- disabilita il tasto enter per evitare l'invio del submit -->
	 <script type="text/javascript" src="${pageContext.request.contextPath}/script/disableEnterKey.js"></script>
	
</head>
<body>
  
<div class="container-fluid" ng-controller="ManagerMeasureController">

<jsp:include page="../../common/nav.jsp" />

	<form class="form" role="form" name="measureForm">
	
	<div class="panel panel-default">
		<div class="panel-body">
 			  <div class="form-group">
					<jsp:include page="../../common/alerts.jsp" />
					<div class="alert alert-info" role="alert" ng-show="confirmedAction">
					<spring:message code="measure.form.delete.message.confirm" /><br/><button  ng-click="remove(measure)" class="alert-link"> <spring:message code="measure.form.delete.message.confirm.ok" /></button> - <button ng-click="removeCancelled()" class="alert-link"><spring:message code="measure.form.delete.message.confirm.cancel" /></button>
					</div>
			  </div>			  
			  <div class="form-group">
				  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				      <button class="btn btn-success" ng-click="save(measure)"><spring:message code="measure.form.save.button" /></button>
				      <button class="btn btn-danger" ng-click="removeConfirm()" ng-show="measure.idMeasures"><spring:message code="measure.form.delete.button" /></button>
			      </sec:authorize>  
			      <button class="btn btn-info" ng-click="cancel()"><spring:message code="measure.form.cancel.button" /></button>
			  </div>
		  </div>
	</div>			
	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row">
  			  <div class="col-md-2"><!-- riga 1 -->
				  <div class="form-group">
				    <label for="name"><small><spring:message code="measure.form.id.label" /></small></label>
				    <div>
				      <p>{{measure.idMeasures}}</p>
				    </div>
				  </div>
			  </div>			  
			  <div class="col-md-4">
   				  <div class="form-group  form-group-sm">
				    <label for="name"><small><spring:message code="measure.form.name.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="name" ng-model="measure.name" required/>
				    </div>
				  </div>
			  </div>
			 </div>
 			  

 			<div class="row"><!-- riga 2 -->
  			  <div class="col-md-6">
  				  <div class="form-group form-group-sm">
				    <label for="description" class="text-nowrap"><small><spring:message code="measure.form.description.label" /></small></label>
				    <div>
				      <textarea rows=3 class="form-control input-sm" id="description" ng-model="measure.description" ></textarea>
				    </div>
				  </div>
			  </div>
			 </div>
 			
 			<div class="row"><!-- riga 3 -->
  			  <div class="col-md-2">
  			  	  <div class="form-group form-group-sm">
				    <label for="type"><small><spring:message code="measure.form.type.label" /></small></label>
				    <div>
				    	<select id="type" ng-model="measure.type" class="form-control input-sm">
				    		<option value="0"><spring:message code="measure.form.type.val.0" /></option>
				      		<option value="1"><spring:message code="measure.form.type.val.1" /></option>
				      		<option value="2"><spring:message code="measure.form.type.val.2" /></option>
				    	</select>
				    </div>
				  </div>  			  
			  </div>
			  <!-- GC - 22/10/2015 -->
			  <!--
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="reliable"><small><spring:message code="measure.form.reliable.label" /></small></label>
				    <div>
				      <input type="checkbox" class="form-control input-sm" id="reliable" ng-model="measure.reliable" />
				    </div>
				  </div>			  
			  </div>
			  -->
			 </div>
			 
  			 <div class="row"><!-- riga 4 -->

			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="fixed_value"><small><spring:message code="measure.form.fixed_value.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="fixed_value" ng-model="measure.fixed_value" />
				    </div>
				  </div>			  
			  </div>
  			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="critical"><small><spring:message code="measure.form.critical.label" /></small></label>
				    <div>
				      <input type="checkbox" class="form-control input-sm" id="critical" ng-model="measure.critical" />
				    </div>
				  </div>			  
			  </div>

			 </div>
			 
   			 <div class="row"><!-- riga 5 -->

				<div class="col-md-2">
				    <label for="update_timestamp"><spring:message code="measure.form.update_timestamp.label" /></label>
		            <p class="input-group bootstrap-datepicker-correction">
		              <input required="required" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="measure.update_timestamp" is-open="openedDatePicker_update_timestamp"  id="update_timestamp"/>
		              <span class="input-group-btn">
		                <button type="button" class="btn btn-default" ng-click="openDatePicker_update_timestamp($event)"><i class="glyphicon glyphicon-calendar"></i></button>
		              </span>
		            </p>
		         </div>
		         <div class="col-md-2">
		            <timepicker ng-model="measure.update_timestamp"  show-meridian="false"></timepicker>
		         </div>
			 </div>
			 
			 <!-- GC 11/11/2015 -->
			 <div class="row"><!-- riga 6 -->
 				 <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="aeeg_code"><small><spring:message code="measure.form.aeeg_code.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="aeeg_code" ng-model="measure.aeeg_code"/>
				    </div>
				  </div>			  
			  </div>
  			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="gis_code"><small><spring:message code="measure.form.gis_code.label" /></small></label>
				    <div>
				     <input type="text" class="form-control input-sm" id="gis_code" ng-model="measure.gis_code"/>
				    </div>
				  </div>			  
			  </div>
			</div>
			
			<div class="row"><!-- riga 7 -->
 				 <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="roughness"><small><spring:message code="measure.form.roughness.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="roughness" ng-model="measure.roughness" />
				    </div>
				  </div>			  
			  </div>
  			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="diameter"><small><spring:message code="measure.form.diameter.label" /></small></label>
				    <div>
				   <input type="number" step="0.01" class="form-control input-sm" id="diameter" ng-model="measure.diameter" />
				    </div>
				  </div>			  
			  </div>
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="multiplication_factor"><small><spring:message code="measure.form.multiplication_factor.label" /></small></label>
				    <div>
				   <input type="number" step="0.01" class="form-control input-sm" id="multiplication_factor" ng-model="measure.multiplication_factor" required/>
				    </div>
				  </div>			  
			  </div>
			 </div>
			 
			 <div class="row"><!-- riga 8 -->
  			  <div class="col-md-2">
  			  	  <div class="form-group form-group-sm">
				    <label for="source"><small><spring:message code="measure.form.source.label" /></small></label>
				    <div>
				    	<select id="source" ng-model="measure.source" class="form-control input-sm">
				    		<option value="0"><spring:message code="measure.form.source.val.0" /></option>
				      		<option value="1"><spring:message code="measure.form.source.val.1" /></option>
				      		<option value="2"><spring:message code="measure.form.source.val.2" /></option>
				      		<option value="3"><spring:message code="measure.form.source.val.3" /></option>
				    	</select>
				    </div>
				  </div>  			  
			  </div>
			 </div>
	 </div>
	 </div>

	<!-- Pannello Database -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.database" /></h3>
  		</div>
  		<div class="panel-body">
		<div class="row"><!-- riga 1 -->
  			  <div class="col-md-2">
				    <label for="connections_id_odbcdsn"><small><spring:message code="measure.form.connections_id_odbcdsn.label" /></small></label>
		            <select class="form-control input-sm"  id="connections_id_odbcdsn" name="connections_id_odbcdsn" ng-model="measure.connections_id_odbcdsn"
                ng-options="c.id_odbcdsn as c.description for c in connections" required ng-required="true"><option/></select>
			  </div>			  
		</div>
		<div class="row"><!-- riga 2 -->
  			  <div class="col-md-2">
				    <label for="table_name"><small><spring:message code="measure.form.table_name.label" /></small></label>
				    <input type="text" class="form-control input-sm" ng-model="measure.table_name"  id="table_name" required/>
			  </div>	
			  <div class="col-md-2">
				    <label for="table_timestamp_column"><small><spring:message code="measure.form.table_timestamp_column.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="table_timestamp_column" ng-model="measure.table_timestamp_column" required/>
				    </div>
			  </div>		  
  			  <div class="col-md-2">
				    <label for="table_value_column"><small><spring:message code="measure.form.table_value_column.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="table_value_column" ng-model="measure.table_value_column" required/>
				    </div>
			  </div>		  
		</div>
		<div class="row"><!-- riga 3 -->
  			  <div class="col-md-2">
				    <label for="table_relational_id_column"><small><spring:message code="measure.form.table_relational_id_column.label" /></small></label>
				    <input type="text" class="form-control input-sm" ng-model="measure.table_relational_id_column"  id="table_relational_id_column"/>
			  </div>	
			  <div class="col-md-2">
				    <label for="table_relational_id_value"><small><spring:message code="measure.form.table_relational_id_value.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="table_relational_id_value" ng-model="measure.table_relational_id_value"/>
				    </div>
			  </div>
  			  <div class="col-md-2">
				    <label for="table_relational_id_type"><small><spring:message code="measure.form.table_relational_id_type.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="table_relational_id_type" ng-model="measure.table_relational_id_type" required/>
				    </div>
			  </div>		  
		</div>
		</div>
	</div>


	<!-- Pannello  Analytics Data -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.analyticsdata" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row">
  			  <div class="col-md-1"><!-- riga 1 -->
				    <label><small><spring:message code="measure.form.min_night_time.label" /></small></label>
			  </div>			  
  			  <div class="col-md-1 text-nowrap">
				    <label for="min_night_start_time"><small><spring:message code="measure.form.min_night_start_time.label" /></small></label>
			        <timepicker ng-model="measure.min_night_start_time" id="min_night_start_time" show-meridian="false" ></timepicker>
			  </div>			  
			  <div class="col-md-1 text-nowrap">
				    <label for="min_night_stop_time"><small><spring:message code="measure.form.min_night_stop_time.label" /></small></label>
				    <timepicker ng-model="measure.min_night_stop_time" id="min_night_stop_time" show-meridian="false"></timepicker>
			  </div>
			</div>
			<!-- riga 2 -->
			<!-- /* GC - 22/10/2015 */
        	<div class="row">
  			  <div class="col-md-1">
				    <label for="max_day_time"><small><spring:message code="measure.form.max_day_time.label" /></small></label>
			  </div>			  
  			  <div class="col-md-1 text-nowrap">
				    <label for="max_day_start_time_1"><small><spring:message code="measure.form.max_day_start_time_1.label" /></small></label>
			        <timepicker ng-model="measure.max_day_start_time_1" id="max_day_start_time_1" show-meridian="false" ></timepicker>
				</div>
				<div class="col-md-1 text-nowrap">
				    <label for="max_day_stop_time_1"><small><spring:message code="measure.form.max_day_stop_time_1.label" /></small></label>
				    <timepicker ng-model="measure.max_day_stop_time_1" id="max_day_stop_time_1" show-meridian="false" ></timepicker>
			  </div>
			 <div class="col-md-1 text-nowrap">
				    <label for="max_day_start_time_2"><small><spring:message code="measure.form.max_day_start_time_2.label" /></small></label>
			        <timepicker ng-model="measure.max_day_start_time_2" id="max_day_start_time_1" show-meridian="false" ></timepicker>
			  </div>			  
			  <div class="col-md-1 text-nowrap">
				    <label for="max_day_stop_time_2"><small><spring:message code="measure.form.max_day_stop_time_2.label" /></small></label>
				    <timepicker ng-model="measure.max_day_stop_time_2" id="max_day_stop_time_2" show-meridian="false" ></timepicker>
			  </div>
  			 <div class="col-md-1 text-nowrap">
				    <label for="max_day_start_time_3"><small><spring:message code="measure.form.max_day_start_time_3.label" /></small></label>
			        <timepicker ng-model="measure.max_day_start_time_3" id="max_day_start_time_3" show-meridian="false" ></timepicker>
			  </div>			  
			  <div class="col-md-1 text-nowrap">
				    <label for="max_day_stop_time_3"><small><spring:message code="measure.form.max_day_stop_time_3.label" /></small></label>
				    <timepicker ng-model="measure.max_day_stop_time_3" id="max_day_stop_time_3" show-meridian="false" ></timepicker>
			  </div>
			 </div>
			 -->
 			
		</div>
	</div>	


	<!-- Pannello Energy -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.energy" /></h3>
  		</div>
  		<div class="panel-body">
		<div class="row"><!-- riga 1 -->
  			  <div class="col-md-3">
				    <label for="energy_category"><small><spring:message code="measure.form.energy_category.label" /></small></label>
				    <select class="form-control input-sm" ng-model="measure.energy_category"  id="energy_category">
				    <option></option>
				    <option value="0"><spring:message code="measure.form.energy_category.val.0" /></option>
				    <option value="1"><spring:message code="measure.form.energy_category.val.1" /></option>
				    <option value="2"><spring:message code="measure.form.energy_category.val.2" /></option>
				    </select>
			  </div>
   			  <div class="col-md-3">
				  <div class="form-group form-group-sm">
				    <label for="energy_specific_content"><small><spring:message code="measure.form.energy_specific_content.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="energy_specific_content" ng-model="measure.energy_specific_content" />
				    </div>
				  </div>			  
			  </div>	
		</div>
	</div>
	</div>
	
	<!-- Pannello Strumentation -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.strumentation" /></h3>
  		</div>
  		<div class="panel-body">
		<div class="row"><!-- riga 1 -->
   			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="strumentation_type"><small><spring:message code="measure.form.strumentation_type.label" /></small></label>
				    <div>
				      <select  class="form-control input-sm" id="strumentation_type" ng-model="measure.strumentation_type" >
				        <option></option>
     				    <option value="0"><spring:message code="measure.form.strumentation_type.val.0" /></option>
				    	<option value="1"><spring:message code="measure.form.strumentation_type.val.1" /></option>
				    	<option value="2"><spring:message code="measure.form.strumentation_type.val.2" /></option>
				    	<option value="3"><spring:message code="measure.form.strumentation_type.val.3" /></option>
				    	<option value="4"><spring:message code="measure.form.strumentation_type.val.4" /></option>
				    	<option value="5"><spring:message code="measure.form.strumentation_type.val.5" /></option>
				      </select>
				    </div>
				  </div>			  
			  </div>	
   			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="strumentation_serial_number"><small><spring:message code="measure.form.strumentation_serial_number.label" /></small></label>
				    <div>
				      <input type="text"  class="form-control input-sm" id="strumentation_serial_number" ng-model="measure.strumentation_serial_number" />
				    </div>
				  </div>			  
			  </div>	
   			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="strumentation_model"><small><spring:message code="measure.form.strumentation_model.label" /></small></label>
				    <div>
				      <input type="text"  class="form-control input-sm" id="strumentation_model" ng-model="measure.strumentation_model" />
				    </div>
				  </div>			  
			  </div>	
   			  <div class="col-md-2">
				    <label for="strumentation_link_type"><small><spring:message code="measure.form.strumentation_link_type.label" /></small></label>
				    <select class="form-control input-sm" ng-model="measure.strumentation_link_type"  id="strumentation_link_type">
				    <option></option>
				    <option value="0"><spring:message code="measure.form.strumentation_link_type.val.0" /></option>
				    <option value="1"><spring:message code="measure.form.strumentation_link_type.val.1" /></option>
				    <option value="2"><spring:message code="measure.form.strumentation_link_type.val.2" /></option>
				    <option value="3"><spring:message code="measure.form.strumentation_link_type.val.3" /></option>
				    <option value="4"><spring:message code="measure.form.strumentation_link_type.val.4" /></option>
				    </select>
			  </div>
			  			  
		</div>
		</div>
	</div>
	
	<!-- Pannello Geolocation -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.geolocation" /></h3>
  		</div>
  		<div class="panel-body">
		<div class="row"><!-- riga 1 -->
   			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="x_position"><small><spring:message code="measure.form.x_position.label" /></small></label>
				    <div>
				      <input type="number" step="0.01"  class="form-control input-sm" id="x_position" ng-model="measure.x_position" />
				    </div>
				  </div>			  
			  </div>	
   			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="y_position"><small><spring:message code="measure.form.y_position.label" /></small></label>
				    <div>
				      <input type="number" step="0.01"  class="form-control input-sm" id="y_position" ng-model="measure.y_position" />
				    </div>
				  </div>			  
			  </div>	
   			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="z_position"><small><spring:message code="measure.form.z_position.label" /></small></label>
				    <div>
				      <input type="number" step="0.01"  class="form-control input-sm" id="z_position" ng-model="measure.z_position" />
				    </div>
				  </div>			  
			  </div>	
			  			  
		</div>
		</div>
	</div>
	
	
	<!-- Pannello Alarms -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.alarms" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row"><!-- riga 1 -->
  			  <div class="col-md-1">
				    <label><small><spring:message code="measure.form.alarm.thresholds" /></small></label>
			  </div>			  
  			  <div class="col-md-1">
				    <label for="alarm_thresholds_enable"><small><spring:message code="measure.alarm_thresholds_enable.label" /></small></label>
				    <input type="checkbox" class="form-control input-sm" id="alarm_thresholds_enable" ng-model="measure.alarm_thresholds_enable" />
			  </div>			  
   			  <div class="col-md-1">
				    <label for="alarm_min_threshold"><small><spring:message code="measure.form.alarm_min_threshold" /></small></label>
				    <input type="number"  step="0.01" class="form-control input-sm" id="alarm_min_threshold" ng-model="measure.alarm_min_threshold" />
			  </div>
   			  <div class="col-md-1">
				    <label for="alarm_max_threshold"><small><spring:message code="measure.form.alarm_max_threshold" /></small></label>
				    <input type="number"  step="0.01" class="form-control input-sm" id="alarm_max_threshold" ng-model="measure.alarm_max_threshold" />
			  </div>
			  <div class="col-md-2">
				    <label for="alarm_threshold_check_time"><small><spring:message code="measure.form.alarm_threshold_check_time" /></small></label>
				    <input type="number"  style="max-width: 70px;" class="form-control input-sm" id="alarm_threshold_check_time" ng-model="measure.alarm_threshold_check_time" />
			  </div>
			</div>
			<div class="row"><!-- riga 2 -->
  			  <div class="col-md-1">
				    <label><small><spring:message code="measure.form.alarm.constantcheck" /></small></label>
			  </div>			  
  			  <div class="col-md-1">
				    <label for="alarm_constant_check_enable"><small><spring:message code="measure.alarm_constant_check_enable.label" /></small></label>
				    <input type="checkbox" class="form-control input-sm" id="alarm_constant_check_enable" ng-model="measure.alarm_constant_check_enable" />
			  </div>			  
   			  <div class="col-md-1">
				    <label for="alarm_constant_hysteresis"><small><spring:message code="measure.alarm_constant_hysteresis" /></small></label>
				    <input type="number"  step="0.01" class="form-control input-sm" id="alarm_constant_hysteresis" ng-model="measure.alarm_constant_hysteresis" />
			  </div>
   			  <div class="col-md-1">
				    <label for="alarm_constant_check_time"><small><spring:message code="measure.alarm_constant_check_time" /></small></label>
				    <input type="number"  class="form-control input-sm" id="alarm_constant_check_time" ng-model="measure.alarm_constant_check_time" />
			  </div>
			</div>
		</div>
	</div>

	<!-- Pannello Epanet -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.epanet" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row"><!-- riga 1 -->
  			  <div class="col-md-2">
				    <label for="epanet_object_id"><small><spring:message code="measure.epanet_object_id.label" /></small></label>
				    <input type="text" class="form-control input-sm" id="epanet_object_id" ng-model="measure.epanet_object_id"/>
			  </div>			  			  
			</div>
	
		</div>
	</div>

	<!-- Pannello Sap -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.sap" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row"><!-- riga 1 -->
  			  <div class="col-md-2">
				    <label for="measure"><small><spring:message code="measure.form.sap_code.label" /></small></label>
				    <input type="text" class="form-control input-sm" id="sap_code" ng-model="measure.sap_code"/>
			  </div>			  
			</div>
	
		</div>
	</div>

	<!-- Pannello Mappa -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.map" /></h3>
  		</div>
  		<div class="panel-body">
  		
  		<div class="row"><!-- riga 7 -->
 				 <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="orientation_degrees"><small><spring:message code="measure.form.orientation_degrees.label" /></small></label>
				    <div>
				      <input type="number" step="0.01" class="form-control input-sm" id="orientation_degrees" ng-model="measure.orientation_degrees" />
				    </div>
				  </div>			  
			  </div>
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
				                <label for="noneToggle"> <spring:message code="measure.form.panel.map.navigate" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="point" id="pointToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="pointToggle"> <spring:message code="measure.form.panel.map.drawpoint" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="line" id="lineToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="lineToggle"> <spring:message code="measure.form.panel.map.drawline" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="polygon" id="polygonToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="polygonToggle"> <spring:message code="measure.form.panel.map.drawpolygon" /></label>
				            </li>
				            <li>
				                <input type="radio" name="type" value="regular" id="regularToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="regularToggle"> <spring:message code="measure.form.panel.map.drawregularpolygon" /></label>
				                <label for="sides"> - <spring:message code="measure.form.panel.map.sides" /></label>
				                <input id="sides" type="text" size="2" maxlength="2"
				                       name="sides" ng-change="update()" ng-model="sides"/>
				                <ul>
				                    <li>
				                        <input id="irregular" type="checkbox"
				                               name="irregular" ng-change="update()" ng-model="irregular"/>
				                        <label for="irregular"> <spring:message code="measure.form.panel.map.irregular" /></label>
				                    </li>
				                </ul>
				            </li>
				            <li>
				                <input type="radio" name="type" value="modify" id="modifyToggle" ng-change="toggleControl()" ng-model="radioItem"/>
				                <label for="modifyToggle"> <spring:message code="measure.form.panel.map.modifyfeature" /></label>
				                <ul>
				                    <li>
				                        <input id="createVertices" type="checkbox"
				                               name="createVertices" ng-change="update()" ng-model="createVertices"/>
				                        <label for="createVertices"> <spring:message code="measure.form.panel.map.allowverticescreation" /></label>
				                    </li>
				                    <li>
				                        <input id="rotate" type="checkbox"
				                               name="rotate" ng-change="update()" ng-model="rotate"/>
				                        <label for="rotate"> <spring:message code="measure.form.panel.map.allowrotation" /></label>
				                    </li>
				                    <li>
				                        <input id="resize" type="checkbox"
				                               name="resize" ng-change="update()" ng-model="resize"/>
				                        <label for="resize"> <spring:message code="measure.form.panel.map.allowresizing" /></label>
				                        (<input id="keepAspectRatio" type="checkbox"
				                               name="keepAspectRatio" ng-change="update()" ng-model="keepAspectRatio"/>
				                        <label for="keepAspectRatio"> <spring:message code="measure.form.panel.map.keepaspectratio" /></label>)
				                    </li>
				                    <li>
				                        <input id="drag" type="checkbox"
				                               name="drag" ng-change="update()" ng-model="drag"/>
				                        <label for="drag"> <spring:message code="measure.form.panel.map.allowdragging" /></label>
				                    </li>
				                </ul>
				            </li>
				            <li><button class="btn btn-danger" ng-click="removeLayer()"><spring:message code="measure.form.panel.map.remove" /></button></li>
				        </ul>
				    </div>
		   		</div>
			</div>
		</div>
	</div>

	<!-- Pannello Note -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.panel.notes" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row"><!-- riga 1 -->
  			  <div class="col-md-8">
				    <label for="notes"><small><spring:message code="measure.form.notes.label" /></small></label>
				    <textarea rows="5" class="form-control input-sm" id="notes" ng-model="measure.notes"></textarea>
			  </div>			  
			</div>
	
		</div>
	</div>
	
	
	
			</form>
			
			<!-- ***RC 06/11/2015*** -->
	<!-- Pannello File upload -->
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measure.form.fileUpload.label" /></h3>
  		</div>
  		<div class="panel-body">
  			<div class="col-md-4">
				<form method="POST" action="measureUpload" id="upForm" name="upForm" enctype="multipart/form-data" class="form-inline" role="form"><!--  -->
					<div class="row">
						<label for="fileDescription"><small><spring:message code="measure.form.fileUpload.description.label" /></small></label>
						<div>
							<input type="text" class="form-control input-sm" id="fileDescription" name="fileDescription"/>
						</div>
					</div>	
					<br>
					<div class="row">
						<label for="fileUri"><small><spring:message code="measure.form.fileUpload.uri.label" /></small></label>
						<div>
							<input type="text" value="file://" class="form-control input-sm" id="fileUri" name="fileUri"/>
						</div>
					</div>	
					<br>
					<div class="row">	
							<input class="btn btn-primary" type="file" name="file" id="fileUp" title="<spring:message code="measure.form.fileUpload.button.browse" />">
				 	</div>
				 	<br>
				 	 <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				 	<div class="row">
						<div class="form-group">
							<a class="btn btn-primary" onclick="check();" ><spring:message code="measure.form.fileUpload.button.upload" /></a>
						</div>
				 	</div>
				 	</sec:authorize>
				 	<input type="hidden" id="hiddenId" name="hiddenId" value="{{measure.idMeasures}}">
				</form>				
			</div>
			<div class="col-md-8">
  					<label for="notes"><small><spring:message code="measure.table.label" arguments="{{measureFiles.length}}"/></small></label>
				    <div class="table-responsive" style="height:250px;overflow-y: scroll;">
				    	<table class="table table-bordered wetnet-table-head" style="background: white;">
				    		<thead>
								<tr>
									<th><spring:message code="measure.table.header.file" /></th>
									<th><spring:message code="measure.table.header.timestamp" /></th>
									<th><spring:message code="measure.table.header.uri" /></th>
									<th><spring:message code="measure.table.header.description" /></th>
									<th></th>
<!-- 								<th></th> -->
								</tr>
						  	</thead>
						  	<tbody>
								<tr ng-repeat="f in measureFiles">
							  		<td align="center">{{f.fileName}}</td>
							    	<td align="center">{{f.loadTimestamp | date:'yyyy-MM-dd HH:mm'}}</td>
							    	<td align="center">{{f.fileUri}}</td>
							    	<td align="center">{{f.description}}</td>
<%-- 							    	<td align="center"><button class="btn btn-success" ng-click="downloadFile(f)"><spring:message code="district.form.save.button"/></button></td> --%>
							  		<td align="center">
							    		 <form id="downloadFile" name="downloadFile" method="get" action="/wetnet/rest/files/getFileById">
                                           <input type="hidden" id="idFile" name="idFile" value="{{f.idFile}}"></input>
                                             <input type="hidden" id="resourceType" name="resourceType" value="measure"></input> 
                                             <input  class="btn btn-success" type="submit" value="<spring:message code="district.form.save.button"/>"/>
<%--                                              <button class="btn btn-success" onClick="this.submit();"><spring:message code="district.form.save.button"/></button> --%>
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
