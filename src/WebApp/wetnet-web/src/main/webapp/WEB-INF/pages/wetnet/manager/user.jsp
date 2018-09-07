<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
	
	 <!-- disabilita il tasto enter per evitare l'invio del submit -->
	 <script type="text/javascript" src="${pageContext.request.contextPath}/script/disableEnterKey.js"></script>
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
<body>
  
<div class="container-fluid" ng-controller="ManagerUserController">

<jsp:include page="../../common/nav.jsp" />

	<form class="form-horizontal" role="form">
			 	<div class="panel panel-default">
				<div class="panel-body">
 			  <div class="form-group">
 			    	<jsp:include page="../../common/alerts.jsp" />
					<div class="alert alert-info" role="alert" ng-show="confirmedAction">
					<spring:message code="user.form.delete.messagge.confirm" /><br/><button  ng-click="remove(user)" class="alert-link"> <spring:message code="user.form.delete.messagge.confirm.ok" /></button> - <button ng-click="removeCancelled()" class="alert-link"><spring:message code="user.form.delete.messagge.confirm.cancel" /></button>
				</div>
			  </div>			  
			  <div class="form-group">
			      <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				      &nbsp;&nbsp; &nbsp;&nbsp;<button class="btn btn-success" ng-click="save(user)"><spring:message code="user.form.save.button" /></button>
				      <button class="btn btn-danger" ng-click="removeConfirm()" ng-show="user.idusers"><spring:message code="user.form.delete.button" /></button>
			      </sec:authorize>  
			      <button class="btn btn-info" ng-click="cancel()"><spring:message code="user.form.cancel.button" /></button>
			  </div>
			  </div>
			  </div> 
			
			<div class="panel panel-default">
  			<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="user.form.role.label" /></h3>
  		  </div>
  		<div class="panel-body">
			
			  <div class="form-group">
			    <label for="role" class="col-sm-2 control-label"><spring:message code="user.form.role.label" /></label>
			    <div class="col-sm-5">
		          <select id="type" ng-model="user.role" class="form-control input-sm">
			    		<option value="0"><spring:message code="user.form.role.val.0" /></option>
			      		<option value="1"><spring:message code="user.form.role.val.1" /></option>
			      		<option value="2"><spring:message code="user.form.role.val.2" /></option>
			      		<option value="3"><spring:message code="user.form.role.val.3" /></option>
			      		<!-- <option value="4"><spring:message code="user.form.role.val.4" /></option>-->
				  </select>
			    </div>
			  </div>			
			  <div class="form-group">
			    <label for="name" class="col-sm-2 control-label"><spring:message code="user.form.name.label" /></label>
			    <div class="col-sm-5">
			      <input type="text" class="form-control" id="name" ng-model="user.name" />
			    </div>
			  </div>
 			  <div class="form-group">
			    <label for="surname" class="col-sm-2 control-label"><spring:message code="user.form.surname.label" /></label>
			    <div class="col-sm-5">
			      <input type="text" class="form-control" id="surname" ng-model="user.surname" />
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="username" class="col-sm-2 control-label"><spring:message code="user.form.username.label" /></label>
			    <div class="col-sm-5">
			      <input type="text" class="form-control" id="username" ng-model="user.username" />
			    </div>
			  </div>
 			  <div class="form-group" ng-hide="user.idusers"><!-- visualizzo password solo se nuovo inserimento -->
			    <label for="password" class="col-sm-2 control-label"><spring:message code="user.form.password.label" /></label>
			    <div class="col-sm-5">
			      <input type="password" class="form-control" ng-model="user.password" id="password" ng-model="user.password" />
			    </div>
			  </div>
  			  <div class="form-group">
			    <label for="email" class="col-sm-2 control-label"><spring:message code="user.form.email.label" /></label>
			    <div class="col-sm-5">
			      <input type="email" class="form-control" id="email" value="" ng-model="user.email" />
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="telephone_number" class="col-sm-2 control-label"><spring:message code="user.form.telephone_number.label" /></label>
			    <div class="col-sm-5">
			      <input type="text" class="form-control" id="telephone_number" value="" ng-model="user.telephone_number" />
			    </div>
			  </div>
			  <div class="form-group">
   			    <label for="sms_enabled" class="col-sm-2 control-label"><spring:message code="user.form.sms_enabled.label" /></label>
			    <div class="col-sm-5">
			    	<input id="sms_enabled" type="checkbox" ng-model="user.sms_enabled"/>
			    </div>
			  </div>
			  <div class="form-group">
   			    <label for="email_enabled" class="col-sm-2 control-label"><spring:message code="user.form.email_enabled.label" /></label>
			    <div class="col-sm-5">
			    	<input id="email_enabled" type="checkbox" ng-model="user.email_enabled"/>
			    </div>
			  </div>
			  	
		<!-- 	  	<div class="panel panel-default">
				<div class="panel-body">
 			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-5">
					<jsp:include page="../../common/alerts.jsp" />
					<div class="alert alert-info" role="alert" ng-show="confirmedAction">
					<spring:message code="user.form.delete.messagge.confirm" /><br/><button  ng-click="remove(user)" class="alert-link"> <spring:message code="user.form.delete.messagge.confirm.ok" /></button> - <button ng-click="removeCancelled()" class="alert-link"><spring:message code="user.form.delete.messagge.confirm.cancel" /></button>
					</div>
			    </div>
			  </div>			  
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-5">
			      <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				      <button class="btn btn-success" ng-click="save(user)"><spring:message code="user.form.save.button" /></button>
				      <button class="btn btn-danger" ng-click="removeConfirm()" ng-show="user.idusers"><spring:message code="user.form.delete.button" /></button>
			      </sec:authorize>  
			      <button class="btn btn-info" ng-click="cancel()"><spring:message code="user.form.cancel.button" /></button>
			    </div>
			  </div>
			  </div>
			  </div>
			-->  
			  
			  
			  </div>
			  </div>
			  
			</form>
	
			
			
		 
			<div class="panel panel-default">
			  <!-- Default panel contents -->
			  <div class="panel-heading"><spring:message code="user.panel.districts.linked.title" /></div>
			  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				  <div class="panel-body">
				  	<!-- ***RC 04/11/2015*** --> 
				  	<!-- 
					<p><input tooltip="<spring:message code="g2.form.districts.tooltip" />" placeholder="<spring:message code="g2.form.districts.placeholder" />" type="text" ng-model="dSelected" typeahead-on-select="districtSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control"></p>
				 	-->
					<div class="row">
						<div class="form-group">
					    	<label for="zoneSelect" class="col-sm-1 control-label"><spring:message code="user.form.zoneSelect.label" /></label>
					    	<div class="col-sm-3">
					      	<!-- <select ng-options="z for z in zones" ng-model="filterValue" ng-change="filterDistricts('zone');"></select>-->
					      		
					      		
					      		<select  ng-model="filterValueZ" ng-change="filterDistricts('zone');">
			   					<option value = ''>--select all--</option>
        						<option ng-repeat="z in zones">{{z}}</option>
					      		</select>
					    	</div>
					    	<label for="municipalitySelect" class="col-sm-1 control-label"><spring:message code="user.form.municipalitySelect.label" /></label>
					    	<div class="col-sm-3">
					      		<!-- 	<select style="max-width:70%;" ng-options="m for m in municipalities" ng-model="filterValue" ng-change="filterDistricts('municipality');"></select>
					      		 -->
					      		<select  style="max-width:70%;" ng-model="filterValueM" ng-change="filterDistricts('municipality');">
			   					<option value = ''>--select all--</option>
			   					<option ng-repeat="m in municipalities">{{m}}</option>
			   					</select>
					    	</div>
					    	<label for="waterAuthoritySelect" class="col-sm-1 control-label"><spring:message code="user.form.waterAuthoritySelect.label" /></label>
					    	<div class="col-sm-3">
					      		<!-- <select style="max-width:70%;" ng-options="w for w in waterAuthorities" ng-model="filterValue" ng-change="filterDistricts('waterAuthority');"></select>
					    	 -->	
					    	<select  style="max-width:70%;" ng-model="filterValueW" ng-change="filterDistricts('waterAuthority');">
			   					<option value = ''>--select all--</option>
			   					<option ng-repeat="w in waterAuthorities">{{w}}</option>
			   					</select>
					    	</div>
					  	</div>
					</div>
					<div class="row">
						<br>
					</div>
					<div style="width:500px; height:45px; margin-left:auto; margin-right:auto;">
						<div style="width:300px; height:40px; margin-left:auto; margin-right:auto; text-align:center;">
							<div ng-dropdown-multiselect events="filteredDistrictsEvents" options="filteredDistrictsData" selected-model="filteredDistrictsModel" extra-settings="filteredDistrictsSettings"></div>
						</div>
					</div>
					<!-- , onItemDeselect: function itemHasBeenUnchecked() { alert('U*' +filteredDistrictsModel.length +'*') } -->
				 	<!-- ***END*** -->
				  </div>
			  </sec:authorize> 
						<div class="table-responsive">
						  <table class="table table-striped table-condensed">
						  <thead>
						  <tr>
						  <th><spring:message code="user.table.districts.name" /></th>
						  <th class="text-center"><spring:message code="user.table.districts.notification" /></th>
						  <th class="text-center"><spring:message code="user.table.districts.actions" /></th>
						  <th></th>
						  </tr>
						  </thead>
						  <tbody>
						  <tr ng-repeat="u in usersHasDistricts">
						  	<td>{{u.districts_name}}</td>
						  	<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
						  		<td class="text-center"><input type="checkbox" title="Notifiche Attive" ng-model="u.events_notification" ng-disabled="'true'" /></td>
						  		<td></td>
						  	</sec:authorize>
						  	<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
						  		<td class="text-center"><input type="checkbox" title="Notifiche Attive" ng-model="u.events_notification" ng-change="districtSelectedUpdate(u)" /></td>
						  		<td class="text-center"><button type="button" class="btn btn-info" tooltip="Click per rimuovere"  ng-click="districtSelectedRemove(u)">X</button></td>
						  	</sec:authorize>
						  </tr>
						  </tbody>
						  </table>
						</div>
			   		</div>
			

</div>

  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
