<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp" >
    <head>
		<title><spring:message code="statistic.head.title" /></title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="<c:url value="/css/hsd.css" />" type="text/css" rel="stylesheet">
		<link href="<c:url value="/css/finance.css" />" type="text/css" rel="stylesheet">
		<link href="<c:url value="/css/wetnet.css" />" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/jquery.js"></script>
		<jsp:include page="../../common/common-head.jsp" />
		<script>
			var jsonData = ${jsonResponse};
			var avgDay = ${jsonAvgDay};
			var minNight = ${jsonMinNight};
			var realLeakage = ${jsonRealLeakage};
			var flagData = ${jsonFlags};
		</script>
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
	<body ng-controller="WetnetControllerG1">
	
	<div class="container-fluid">
	  <jsp:include page="../../common/nav.jsp" />
	  			
	<div class="row">
        <div class="col-md-4">
	        <div class="form-group">
				<input name="<spring:message code="statistic.input.placeholder" />" tooltip="<spring:message code="statistic.input.tooltip" />"
					placeholder="<spring:message code="statistic.input.placeholder" />" type="text" ng-model="bean.districtsSelected"
					typeahead-on-select="districtSelectedShow($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
	       	</div>
       	</div>			
       	<c:if test="${not empty districtsName and districtsName != ''}">
	    	<div class="col-md-4" >
		    	<div class="form-group">
	    			<div class="btn btn-info">${districtsName}</div>
	   			</div>
   			</div>
    	</c:if>
	</div>
	  
	<div class="large-12 columns">	

        <h2><spring:message code="statistic.body.title" text="Statistic graphic" /></h2>
        <div id="content">
			<div id="finance">
				<div id="labels">
					<div id="financeTitle"><spring:message code="statistic.graphic.title" /></div>
					<div id="time">
						<a onclick="HumbleFinance.zoom(6);"><spring:message code="statistic.graphic.1w" text="1w" /></a> <a
							onclick="HumbleFinance.zoom(30);"><spring:message code="statistic.graphic.1m" text="1m" /></a> <a
							onclick="HumbleFinance.zoom(91);"><spring:message code="statistic.graphic.3m" text="3m" /></a> <a
							onclick="HumbleFinance.zoom(183);"><spring:message code="statistic.graphic.6m" text="6m" /></a> <a
							onclick="HumbleFinance.zoom(365);"><spring:message code="statistic.graphic.1y" text="1y" /></a> <a
							onclick="HumbleFinance.zoom(1824);"><spring:message code="statistic.graphic.5y" text="5y" /></a>
					</div>
					<div id="dateRange"></div>
				</div>
				<div id="priceGraph"
					style="width: 100%; height: 240px; position: relative; cursor: default;">
					<canvas class="flotr-canvas"
						style="position: absolute; left: 0px; top: 0px; width: 798px; height: 240px;"
						width="798" height="240"></canvas>
					<canvas class="flotr-overlay"
						style="position: absolute; left: 0px; top: 0px; width: 798px; height: 240px;"
						width="798" height="240"></canvas>
					<div class="flotr-labels"
						style="font-size: smaller; color: #545454;">
						<div class="flotr-grid-label"
							style="position: absolute; top: 232px; left: 0; width: auto; text-align: right;"></div>
						<div class="flotr-grid-label"
							style="position: absolute; top: 172px; left: 0; width: auto; text-align: right;"></div>
						<div class="flotr-grid-label"
							style="position: absolute; top: 112px; left: 0; width: auto; text-align: right;"></div>
						<div class="flotr-grid-label"
							style="position: absolute; top: 52px; left: 0; width: auto; text-align: right;"></div>
					</div>
					<div class="flotr-titles" style="color: #545454;"></div>
					<div style="font-weight: bold;" class="flotr-axis-title"></div>
					<div
						style="opacity: 0.7; background-color: rgb(0, 0, 0); color: rgb(255, 255, 255); position: absolute; padding: 2px 8px; border-radius: 4px; white-space: nowrap; top: 5px; bottom: auto; right: 5px; left: auto;"
						class="flotr-mouse-value"></div>
				</div>
				<div id="volumeGraph"
					style="width: 100%; height: 80px; position: relative; cursor: default;">
					<canvas class="flotr-canvas"
						style="position: absolute; left: 0px; top: 0px; width: 798px; height: 80px;"
						width="798" height="80"></canvas>
					<canvas class="flotr-overlay"
						style="position: absolute; left: 0px; top: 0px; width: 798px; height: 80px;"
						width="798" height="80"></canvas>
					<div class="flotr-labels"
						style="font-size: smaller; color: #545454;"></div>
					<div class="flotr-titles" style="color: #545454;"></div>
					<div style="font-weight: bold;" class="flotr-axis-title"></div>
					<div
						style="opacity: 0.7; background-color: rgb(0, 0, 0); color: rgb(255, 255, 255); position: absolute; padding: 2px 8px; border-radius: 4px; white-space: nowrap; top: 5px; bottom: auto; right: 5px; left: auto; display: none;"
						class="flotr-mouse-value"></div>
				</div>
				<div id="summaryGraph"
					style="width: 100%; height: 60px; position: relative; cursor: default;">
					<canvas class="flotr-canvas"
						style="position: absolute; left: 0px; top: 0px; width: 798px; height: 60px;"
						width="798" height="60"></canvas>
					<canvas class="flotr-overlay"
						style="position: absolute; left: 0px; top: 0px; width: 798px; height: 60px;"
						width="798" height="60"></canvas>
					<div class="flotr-labels"
						style="font-size: smaller; color: #545454;">
						<div class="flotr-grid-label"
							style="position: absolute; top: 60px; left: 62.395295794725584px; width: 159.6px; text-align: center;"></div>
						<div class="flotr-grid-label"
							style="position: absolute; top: 60px; left: 204.59059158945115px; width: 159.6px; text-align: center;"></div>
						<div class="flotr-grid-label"
							style="position: absolute; top: 60px; left: 346.78588738417676px; width: 159.6px; text-align: center;"></div>
						<div class="flotr-grid-label"
							style="position: absolute; top: 60px; left: 488.9811831789023px; width: 159.6px; text-align: center;"></div>
						<div class="flotr-grid-label"
							style="position: absolute; top: 60px; left: 631.176478973628px; width: 159.6px; text-align: center;"></div>
					</div>
					<div class="flotr-titles" style="color: #545454;"></div>
					<div style="font-weight: bold;" class="flotr-axis-title"></div>
				</div>
				<c:if test="${not empty flagData and flagData != ''}">
					<div id="flagContainer">
						<div class="flag"
							style="position: absolute; top: 102px; left: 223px; z-index: 10;"></div>
						<div class="flagpole"
							style="position: absolute; top: 102px; left: 223px; z-index: 10; height: 40px;"></div>
					</div>
				</c:if>
				<div id="leftHandle" class="handle zoomHandle"
					style="position: absolute; left: -3px; top: 339px;"></div>
				<div id="rightHandle" class="handle zoomHandle"
					style="position: absolute; left: 54px; top: 339px;"></div>
				<div id="scrollHandle" class="handle scrollHandle"
					style="position: absolute; left: -4px; top: 378px; width: 64px;"></div>
			</div>
		</div>      
    </div>
   		
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/prototype.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/Finance.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/excanvas.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/base64.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/canvas2image.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/canvastext.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/flotr.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/lib/HumbleFinance.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/script/wetnet.js"></script>
		
   		<jsp:include page="../../common/footer.jsp" />
	</div>
	
	</body>
</html>
