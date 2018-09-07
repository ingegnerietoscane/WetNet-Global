<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
  <div class="navbar navbar-default" role="navigation" ng-controller="WetnetControllerNavigation">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<c:url value="/wetnet/welcome" />" title="<spring:message code="nav.link_home.text" />">
      	<img class="img-responsive wetnet-logo" alt="<spring:message code="nav.link_home.text" />" src="<c:url value="/images/wetnet_logo_nav.png" />" height="40" width="130">
      </a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        
        
        
         <sec:authorize access="!hasRole('ROLE_METER_READER')">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle"><spring:message code="nav.link_chart.text" /> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="<c:url value="/wetnet/graphics/statistic" />" ><spring:message code="dashboard.graphics.g1.title" /></a></li>
            <li><a href="<c:url value="/wetnet/graphics/statistic-g2" />" ><spring:message code="dashboard.graphics.g2.title" /></a></li>
            <li><a href="<c:url value="/wetnet/graphics/statistic-g3_1" />" ><spring:message code="dashboard.graphics.g3.title" /></a></li>
            <li><a href="<c:url value="/wetnet/graphics/statistic-g4" />" ><spring:message code="dashboard.graphics.g4.title" /></a></li>
            <li><a href="<c:url value="/wetnet/graphics/statistic-g5" />" ><spring:message code="dashboard.graphics.g5.title" /></a></li>
            <li><a href="<c:url value="/wetnet/graphics/statistic-g7" />" ><spring:message code="dashboard.graphics.g7.title" /></a></li>
           <li><a href="<c:url value="/wetnet/graphics/statistic-g8" />" ><spring:message code="dashboard.graphics.g8.title" /></a></li>
          </ul>
        </li>
        </sec:authorize>
        
         <sec:authorize access="!hasRole('ROLE_METER_READER')">
         <li class="dropdown">
          <a href="#" class="dropdown-toggle"><spring:message code="nav.link_events.text" /> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li class="" ng-class="{'active': (currUrl == '/wetnet/events/events')}" ><a href="<c:url value="/wetnet/events/events" />"><spring:message code="nav.link_events-list.text" /></a></li>
            <li class="divider"></li>
            <li><a href="<c:url value="/wetnet/events/events-pie-chart" />" ><spring:message code="events.form.link.piechart" /></a></li>
            <li><a href="<c:url value="/wetnet/events/events-scatter-chart" />" ><spring:message code="events.form.link.scatterchart" /></a></li>
            <li><a href="<c:url value="/wetnet/events/events-gantt-chart" />" ><spring:message code="events.form.link.ganttchart" /></a></li>
            <li><a href="<c:url value="/wetnet/events/events-cat-chart" />" ><spring:message code="events.form.link.catchart" /></a></li>
          </ul>
        </li>
        </sec:authorize>
        
         <sec:authorize access="!hasRole('ROLE_METER_READER')">
       <!--  GC 09/11/2015 -->
         <li class="dropdown">
          <a href="#" class="dropdown-toggle"><spring:message code="nav.link_alarms.text" /> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="<c:url value="/wetnet/alarms/alarms-active" />" ><spring:message code="nav.link_alarms.active-alarms" /></a></li>
            <li><a href="<c:url value="/wetnet/alarms/alarms-history" />" ><spring:message code="nav.link_alarms.alarms-history" /></a></li>
          </ul>
        </li>
        </sec:authorize>
        
       
        
         <sec:authorize access="!hasRole('ROLE_METER_READER')">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle"><spring:message code="nav.link_map-view.text" /> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li class="" ng-class="{'active': (currUrl == '/wetnet/misc/map-view-ol3')}"><a href="<c:url value="/wetnet/misc/map-view-ol3" />"><spring:message code="nav.link_map-view.show" /></a></li>
<%--             <li class="" ng-class="{'active': (currUrl == '/wetnet/misc/map-view-ol2')}"><a href="<c:url value="/wetnet/misc/map-view-ol2" />"><spring:message code="nav.link_map-view.show" /> ol2</a></li> --%>
            <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})">
	            <li class="divider"></li>
	            <li><a href="<c:url value="/wetnet/misc/kml-upload" />" ><spring:message code="nav.link_map-view.import" /></a></li>
            </sec:authorize>
          </ul>
        </li>
        </sec:authorize>
        
        <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR', 'ROLE_OPERATOR'})">
        	<li class="" ng-class="{'active': (currUrl == '/wetnet/misc/epanet')}" ><a href="<c:url value="/wetnet/misc/epanet" />"><spring:message code="nav.link_epanet.text" /></a></li>
        	<li class="" ng-class="{'active': (currUrl == '/wetnet/manager/districts')}" ><a href="<c:url value="/wetnet/manager/districts" />"><spring:message code="nav.link_districts.text" /></a></li>
	    </sec:authorize>
	    <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR', 'ROLE_OPERATOR','ROLE_METER_READER'})">
	        <li class="" ng-class="{'active': (currUrl == '/wetnet/manager/measures')}" ><a href="<c:url value="/wetnet/manager/measures" />"><spring:message code="nav.link_measures.text" /></a></li>
        </sec:authorize>
        
        <sec:authorize access="hasAnyRole({'ROLE_ADMINISTRATOR', 'ROLE_SUPERVISOR'})">
	        <li class="" ng-class="{'active': (currUrl == '/wetnet/manager/connections')}" ><a href="<c:url value="/wetnet/manager/connections" />"><spring:message code="nav.link_connections.text" /></a></li>
	        <li class="" ng-class="{'active': (currUrl == '/wetnet/manager/users')}" ><a href="<c:url value="/wetnet/manager/users" />"><spring:message code="nav.link_users.text" /></a></li>
        </sec:authorize>
        
        <li class="dropdown">
          <a href="#" class="dropdown-toggle"><spring:message code="nav.info.text" /> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="<c:url value="/wetnet/misc/info" />" ><spring:message code="nav.menu.info" /></a></li>
            <li><a href="<c:url value="/wetnet/misc/contacts" />" ><spring:message code="nav.menu.contacts" /></a></li>
          </ul>
        </li>
        
      </ul>
      <ul class="nav navbar-nav navbar-right">

        <li class="dropdown">
          <a href="#" class="dropdown-toggle"><spring:message code="logout.text.username"/> <sec:authentication property="principal.username" /> <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="<c:url value="/wetnet/misc/user-password-modify" />"><spring:message code="nav.link_passwordmodify.text"/></a></li>
            <li class="divider"></li>
            <li><a href="<c:url value="/j_spring_security_logout" />" ><spring:message code="logout.logout.button" /></a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</div>
  
<!-- 
<div class="nav-box">
  <nav class="navbar fullwidth" ng-controller="WetnetControllerNavigation">

      <ul>
      <li class="" ng-class="{'active': (currUrl == '/wetnet/welcome')}" ><a id="testlink" href="<c:url value="/wetnet/welcome" />"><spring:message code="dashboard.link_home.text" text="Wetnet" /></a></li>
      <li class="" ng-class="{'active': (currUrl =='/wetnet/graphics/statistic')}"><a id="testlink" href="<c:url value="/wetnet/graphics/statistic" />"><spring:message code="dashboard.link_g1.text" text="Statistic graphics" /></a></li>
      <li class="" ng-class="{'active': (currUrl =='/wetnet/graphics/statistic-g2')}"><a id="testlink" href="<c:url value="/wetnet/graphics/statistic-g2" />"><spring:message code="dashboard.link_g2.text" text="Statistica Grafico 2" /></a></li>
      <li class="" ng-class="{'active': (currUrl =='/wetnet/graphics/statistic-g3_1')}"><a id="testlink" href="<c:url value="/wetnet/graphics/statistic-g3_1" />"><spring:message code="dashboard.link_g3_1.text" text="Statistica Grafico 3" /></a></li>
      <li><a><spring:message code="logout.text.username" text="Username:"/>${username}</a></li>
      <li><a href="<c:url value="/j_spring_security_logout" />" ><spring:message code="logout.logout.button" text="Logout"/></a></li>
      </ul>
  </nav>
</div> -->