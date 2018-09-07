<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="alert alert-success alert-dismissible" ng-show="alert.success" role="alert">
<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
{{alert.success}}</div>
<div class="alert alert-info alert-dismissible" ng-show="alert.info" role="alert">
<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
{{alert.info}}</div>
<div class="alert alert-warning alert-dismissible" ng-show="alert.warning" role="alert">
<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
{{alert.warning}}</div>
<div class="alert alert-danger alert-dismissible" ng-show="alert.danger" role="alert">
<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
{{alert.danger}}</div>