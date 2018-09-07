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
</head>
<body>
  
<div class="container-fluid" ng-controller="ManagerMeasureReadingController">

<jsp:include page="../../common/nav.jsp" />

	<form class="form" role="form" name="measureForm">
	
	<div class="panel panel-default">
		<div class="panel-body">
 			  <div class="form-group">
					<jsp:include page="../../common/alerts.jsp" />
					<div class="alert alert-info" role="alert" ng-show="confirmedAction">
					<spring:message code="measureMeterReading.form.save.message.confirm" /><br/>
					<button  ng-click="save()" class="alert-link"> <spring:message code="measure.form.save.button" /></button> - <button ng-click="removeCancelled()" class="alert-link"><spring:message code="measure.form.delete.message.confirm.cancel" /></button>
					</div>
					
			  </div>			  
			  <div class="form-group">
				  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR','ROLE_METER_READER'})">
				      <button class="btn btn-success" ng-click="checkSave()"><spring:message code="measure.form.save.button" /></button>
				  </sec:authorize>  
			      <button class="btn btn-info" ng-click="cancel()"><spring:message code="measure.form.cancel.button" /></button>
			  </div>
		  </div>
	</div>			
	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measureReading.form.panel.general" /></h3>
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
				      <input type="text" class="form-control input-sm" id="name" ng-model="measure.name" disabled/>
				    </div>
				  </div>
			  </div>
			 </div>
 			  
 			   <div class="row"><!-- riga 2 -->

				<div class="col-md-2">
				    <label for="update_timestamp"><spring:message code="measures.table.meterReading.timestamp" /></label>
		            <p class="input-group bootstrap-datepicker-correction">
		              <input required="required" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="reading.timestamp" is-open="openedDatePicker_update_timestamp"  id="update_timestamp"/>
		              <span class="input-group-btn">
		                <button type="button" class="btn btn-default" ng-click="openDatePicker_update_timestamp($event)"><i class="glyphicon glyphicon-calendar"></i></button>
		              </span>
		            </p>
		         </div>
		         <div class="col-md-2">
		            <timepicker ng-model="reading.timestamp"  show-meridian="false"></timepicker>
		         </div>
			 </div>
			 
			 <div class="row">
			  <div class="col-md-2">
				  <div class="form-group form-group-sm">
				    <label for="value"><small><spring:message code="measures.table.meterReading.value.label" /></small></label>
				    <div>
				   <input type="number" required="required" step="0.5" class="form-control input-sm" id="value" ng-model="reading.value" />
				    </div>
				  </div>			  
			  </div>
			  </div>


	 </div>
	 </div>

	<!-- Pannello Database -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="measureReading.form.panel.history" /></h3>
  		</div>
  		<div class="panel-body">
		<div class="row">
  			  <div class="col-md-6">
				    <table class="table table-striped table-condensed">
			  <thead>
			  <tr>
			  <th><spring:message code="measuresMeterReading.table.timestamp" /></th>
			  <th><spring:message code="measuresMeterReading.table.value" /></th>
			  <th><spring:message code="measuresMeterReading.table.user" /></th>
			  </tr>
			  </thead>
			  <tbody>
			  <tr ng-repeat="m in allReading">
			  	<td class="col-md-1">{{m.timestamp | date:'yyyy-MM-dd HH:mm'}}</td>
				<td class="col-md-2">{{m.value}}</td>
				<td class="col-md-3">{{filterUser(m.idUserreader)}}</td>
			</tr>
			  </tbody>
			  </table> 
			  </div>			  
		 </div>
		</div>
	</div>


	
			</form>
			

			
			</div>

<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
