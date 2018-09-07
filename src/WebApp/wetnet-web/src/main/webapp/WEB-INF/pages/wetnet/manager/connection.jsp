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
<body>
  
<div class="container-fluid" ng-controller="ManagerConnectionController">

<jsp:include page="../../common/nav.jsp" />

	<form class="form" role="form" name="connectionForm">
	
	<div class="panel panel-default">
		<div class="panel-body">
 			  <div class="form-group">
					<jsp:include page="../../common/alerts.jsp" />
					<div class="alert alert-info" role="alert" ng-show="confirmedAction">
					<spring:message code="connection.form.delete.messagge.confirm" /><br/><button  ng-click="remove(connection)" class="alert-link"> <spring:message code="connection.form.delete.messagge.confirm.ok" /></button> - <button ng-click="removeCancelled()" class="alert-link"><spring:message code="connection.form.delete.messagge.confirm.cancel" /></button>
					</div>
			  </div>			  
			  <div class="form-group">
			  	  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				      <button class="btn btn-success" ng-click="save(connection)"><spring:message code="connection.form.save.button" /></button>
				      <button class="btn btn-danger" ng-click="removeConfirm()" ng-show="connection.id_odbcdsn"><spring:message code="connection.form.delete.button" /></button>
			      </sec:authorize>
			      <button class="btn btn-info" ng-click="cancel()"><spring:message code="connection.form.cancel.button" /></button>
			  </div>
		  </div>
	</div>			
	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="connection.form.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
			<div class="row">
  			  <div class="col-md-2"><!-- riga 1 -->
				  <div class="form-group">
				    <label for="name"><small><spring:message code="connection.form.id.label" /></small></label>
				    <div>
				      <p>{{connection.id_odbcdsn}}</p>
				    </div>
				  </div>
			  </div>			  
			  <div class="col-md-5">
   				  <div class="form-group  form-group-sm">
				    <label for="odbc_dsn"><small><spring:message code="connection.form.odbc_dsn.label" /></small></label>
				    <div>
				      <input type="text" class="form-control input-sm" id="odbc_dsn" ng-model="connection.odbc_dsn" required/>
				    </div>
				  </div>
			  </div>
			 </div>
 			  
 			  <!-- GC - 26/10/2015 Username e password-->
			 <div class="row"><!-- riga 2 -->
  			  <div class="col-md-7">
  				  <div class="form-group form-group-sm">
				    <label for="description" class="text-nowrap"><small><spring:message code="connection.form.username.label" /></small></label>
				    <div>
				    <input type="text" class="form-control input-sm" id="username" ng-model="connection.username"/>
				    </div>
				  </div>
			  </div>
			 </div>
			 
			 <div class="row"><!-- riga 3 -->
  			  <div class="col-md-7">
  				  <div class="form-group form-group-sm">
				    <label for="description" class="text-nowrap"><small><spring:message code="connection.form.password.label" /></small></label>
				    <div>
				     <input type="text" class="form-control input-sm" id="password" ng-model="connection.password"/>
				    </div>
				  </div>
			  </div>
			 </div>
			 
			 <div class="row"><!-- riga 4 -->
  			  <div class="col-md-4">
  				  <div class="form-group form-group-sm">
				    <label for="description" class="text-nowrap"><small><spring:message code="connection.form.type.label" /></small></label>
				    <div>
					    <select class="form-control" ng-model="connection.dbType" id="dbType">
						  <option value="0"><spring:message code="connection.form.select.type1.label" /></option> 
						  <option value="1"><spring:message code="connection.form.select.type2.label" /></option>
						  <option value="2"><spring:message code="connection.form.select.type3.label" /></option>
						  <option value="3"><spring:message code="connection.form.select.type4.label" /></option> 
						  <option value="4"><spring:message code="connection.form.select.type5.label" /></option>
						</select>
				    </div>
				  </div>
			  </div>
			 </div>
			 
 			<div class="row"><!-- riga 5 -->
  			  <div class="col-md-7">
  				  <div class="form-group form-group-sm">
				    <label for="description" class="text-nowrap"><small><spring:message code="connection.form.description.label" /></small></label>
				    <div>
				      <textarea rows=3 class="form-control input-sm" id="description" ng-model="connection.description" ></textarea>
				    </div>
				  </div>
			  </div>
			 </div>
		</div>
		</div> 			
	
	
	
			</form>
			</div>

  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
