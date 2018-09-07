<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
</head>
<body>
  
<div class="container-fluid" ng-controller="PasswordModifyController">

<jsp:include page="../../common/nav.jsp" />

	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="passwordModify.form.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
  		
   	<div class="row">
   		<div class="col-md-12">

			<form class="form-horizontal" role="form" name="passwordModifyForm">
			  <div class="form-group">
			    <label for="name" class="col-sm-2 control-label"><spring:message code="user.form.name.label" /></label>
			    <div class="col-sm-5">
			      {{user.name}}
			    </div>
			  </div>
 			  <div class="form-group">
			    <label for="oldPassword" class="col-sm-2 control-label"><spring:message code="passwordModify.form.oldPassword.label" /></label>
			    <div class="col-sm-5">
			      <input type="password" class="form-control" ng-model="oldPassword" id="oldPassword" ng-model="oldPassword" required/>
			    </div>
			  </div>
 			  <div class="form-group">
			    <label for="newPassword" class="col-sm-2 control-label"><spring:message code="passwordModify.form.newPassword.label" /></label>
			    <div class="col-sm-5">
			      <input type="password" class="form-control" ng-model="newPassword" id="newPassword" ng-model="newPassword" required/>
			    </div>
			  </div>
 			  <div class="form-group">
			    <label for="newPasswordConfirm" class="col-sm-2 control-label"><spring:message code="passwordModify.form.newPasswordConfirm.label" /></label>
			    <div class="col-sm-5">
			      <input type="password" class="form-control" ng-model="newPasswordConfirm" id="newPasswordConfirm" ng-model="newPasswordConfirm" required/>
			    </div>
			  </div>
 			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-5">
					<jsp:include page="../../common/alerts.jsp" />
			    </div>
			  </div>			  
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-5">
			      <button class="btn btn-success" ng-click="modifyPassword()"><spring:message code="passwordModify.form.modify.button" /></button>
			      <a class="btn btn-info" href="<c:url value="/wetnet/welcome" />"><spring:message code="user.form.cancel.button" /></a>
			    </div>
			  </div>
			</form>
			</div>
	</div>
	</div>
	</div>
			

</div>

  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
