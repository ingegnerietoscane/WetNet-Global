<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="btn-group form-group">
    <a href="#" class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'1d'" title="<spring:message code="time.form.today-title" />"><spring:message code="time.form.today" /></a>
    <a href="#" class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'1w'" title="<spring:message code="time.form.1w-title" />"><spring:message code="time.form.1w" /></a>
    <a href="#" class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'1m'" title="<spring:message code="time.form.1m-title" />"><spring:message code="time.form.1m" /></a>
    <a href="#" class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'3m'" title="<spring:message code="time.form.3m-title" />"><spring:message code="time.form.3m" /></a>
    <a href="#" class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'6m'" title="<spring:message code="time.form.6m-title" />"><spring:message code="time.form.6m" /></a>
    <a href="#" class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'1y'" title="<spring:message code="time.form.1y-title" />"><spring:message code="time.form.1y" /></a>
</div>