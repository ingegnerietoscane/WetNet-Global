<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
</head>
<body>
  
<div class="container-fluid" ng-controller="ManagerUsersController">

<jsp:include page="../../common/nav.jsp" />

<div class="row">
	 	<div class="col-md-12">
		<form  class="form-inline" role="form" >
		  <div class="form-group">
		    <label class="sr-only" for="searchUsers"><spring:message code="users.form.searchUser.label" /></label>
		    <input ng-model="searchUsers" type="text" class="form-control input-sm" id="searchUsers" placeholder="<spring:message code="users.form.searchUser.placeholder" />">
		  </div>
		    <button type="submit" class="btn btn-default"><spring:message code="users.form.searchUser.button" /></button>
		  <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
  				<a class="btn btn-default" href="<c:url value="/wetnet/manager/user" />"><spring:message code="users.form.addUser.button" /></a>
			</sec:authorize>
		</form>
		</div>
	</div>

   	<div class="row">
   		<div class="col-md-12">
			<div class="table-responsive">
			  <table class="table table-striped table-condensed">
			  <thead>
			  <tr>
			  <th><spring:message code="users.table.firstnane" /></th>
			  <th><spring:message code="users.table.surname" /></th>
			  <th><spring:message code="users.table.username" /></th>
			  <th><spring:message code="users.table.role" /></th>
			  <th><spring:message code="users.table.email" /></th>
			  <th><spring:message code="users.table.phone" /></th>
			  <th><spring:message code="users.table.sms_enabled" /></th>
			  <th><spring:message code="users.table.mail_enabled" /></th>
			  <th></th>
			  </tr>
			  </thead>
			  <tbody>
			  <tr ng-repeat="u in users | filter:searchUsers">
			  	<td>{{u.name}}</td>
			  	<td>{{u.surname}}</td>
			  	<td>{{u.username}}</td>
			  	<td>
			  		<div ng-show="u.role == 0"><spring:message code="user.form.role.val.0" /></div>
		      		<div ng-show="u.role == 1"><spring:message code="user.form.role.val.1" /></div>
		      		<div ng-show="u.role == 2"><spring:message code="user.form.role.val.2" /></div>
		      		<div ng-show="u.role == 3"><spring:message code="user.form.role.val.3" /></div>
			  	</td>
			  	<td>{{u.email}}</td>
			  	<td>{{u.telephone_number}}</td>
			  	<td align="left">
			  		<img ng-show="u.sms_enabled" alt="ok" src="<c:url value="/images/ok-icon.png" />" height="25" width="25"/>
		      		<img ng-show="!u.sms_enabled" alt="ko" src="<c:url value="/images/ko-icon.png" />" height="25" width="25"/>
			  	</td>
			  	<td align="left">
			  		<img ng-show="u.email_enabled" alt="ok" src="<c:url value="/images/ok-icon.png" />" height="25" width="25"/>
		      		<img ng-show="!u.email_enabled" alt="ko" src="<c:url value="/images/ko-icon.png" />" height="25" width="25"/>
			  	</td>
			  	<td><a href="<c:url value="/wetnet/manager/user?id={{u.idusers}}" />" type="button" class="btn btn-default">
			  		<sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR'})">
				  		<spring:message code="users.table.edit" />
			  		</sec:authorize>
			  		<sec:authorize access="hasAnyRole({'ROLE_SUPERVISOR'})">
				  		<spring:message code="users.table.view" />
			  		</sec:authorize>
			  	</a></td>
			  </tr>
			  </tbody>
			  </table>
			</div>
   		</div>
   	</div>


</div>

  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
