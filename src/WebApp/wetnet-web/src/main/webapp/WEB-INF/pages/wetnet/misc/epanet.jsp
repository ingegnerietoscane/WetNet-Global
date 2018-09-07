<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html ng-app="wetnetApp" >
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1"/>
	<jsp:include page="../../common/common-head.jsp" />
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
  
<div class="container-fluid" ng-controller="EpanetController">

<jsp:include page="../../common/nav.jsp" />

	<!-- Pannello Generale -->			
	<div class="panel panel-default">
  		<div class="panel-heading">
    		<h3 class="panel-title"><spring:message code="epanet.form.panel.general" /></h3>
  		</div>
  		<div class="panel-body">
  		
		<div class="row">
	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="startDate"><spring:message code="epanet.form.startDate" /></label>
	              <input id="startDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="epanetData.startDate" is-open="openedStartDate"  />
	              <span class="input-group-btn">
	                <button type="button" class="btn btn-default" ng-click="openStartDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
	              </span>
	   			</p>
	   		</div>
<!-- 	        <div class="col-md-2">
	            <p class="input-group bootstrap-datepicker-correction">
	              <label class="sr-only" for="endDate"><spring:message code="epanet.form.endDate" /></label>
	              <input id="endDate" type="text" class="form-control" datepicker-popup="yyyy-MM-dd" ng-model="epanetData.endDate" is-open="openedEndDate"  />
	              <span class="input-group-btn">
	                <button type="button" class="btn btn-default" ng-click="openEndDate($event)"><i class="glyphicon glyphicon-calendar"></i></button>
	              </span>
	            </p>
	        </div>

			<div class="col-md-4">
		    	<div class="btn-group form-group">
			        <label class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'1w'" title="<spring:message code="epanet.form.1w-title" />"><spring:message code="epanet.form.1w" /></label>
			        <label class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'1m'" title="<spring:message code="epanet.form.1m-title" />"><spring:message code="epanet.form.1m" /></label>
			        <label class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'3m'" title="<spring:message code="epanet.form.3m-title" />"><spring:message code="epanet.form.3m" /></label>
			        <label class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'6m'" title="<spring:message code="epanet.form.6m-title" />"><spring:message code="epanet.form.6m" /></label>
			        <label class="btn btn-success" ng-change="updateDate()" ng-model="radioModel" btn-radio="'1y'" title="<spring:message code="epanet.form.1y-title" />"><spring:message code="epanet.form.1y" /></label>
	   			</div>
	    	</div> -->
   		</div>
   		<div class="row">
			<div class="col-md-6">
  				<div class="form-group">
				<!-- <select class="width-80" id="district" name="district" ng-model="formBean.districtId"  ng-options="c.id as c.name for c in districts" ng-change="loadData()"><option/></select> -->
					<input tooltip="<spring:message code="epanet.form.districts.tooltip" />" placeholder="<spring:message code="epanet.form.districts.placeholder" />" type="text" ng-model="dSelected" typeahead-on-select="districtSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in districts | filter:$viewValue | limitTo:districts.length" class="form-control">
				</div>
	   		</div>
			<div class="col-md-6">
				<div class="form-group">
					<input tooltip="<spring:message code="epanet.form.measures.tooltip" />"  placeholder="<spring:message code="epanet.form.measures.placeholder" />" type="text" ng-model="mSelected" typeahead-on-select="measureSelectedAdd($item, $model, $label)" typeahead="d as d.name for d in measures | filter:$viewValue | limitTo:measures.length" class="form-control">
		   		</div>
	   		</div>
		</div>
	   	<div class="row">
			<div class="col-md-6">
				<label ng-show="epanetData.districtsSelected.name"><spring:message code="epanet.form.selectedDistricts" /></label>
		    	<div  ng-show="epanetData.districtsSelected.name">
			    	<dl>
			    	<dt><button type="button" class="btn btn-info" tooltip="Click per rimuovere"  ng-click="districtSelectedRemove(epanetData.districtsSelected)">{{epanetData.districtsSelected.name}}</button></dt>
			    	<dd><span ng-repeat="m in epanetData.districtsSelected.measures"><button type="button" class="btn btn-link" ng-click="measureSelectedAdd(m, m, m.name)" >{{m.name}}</button></span></dd>
			    	</dl>
		    	</div>
	   		</div>
			<div class="col-md-6">
				<label ng-show="epanetData.measuresSelected.length > 0"><spring:message code="epanet.form.selectedMeasures" /></label>
			    <div class="form-group">
			    	<button type="button" class="btn btn-info"  tooltip="Click per rimuovere"  ng-repeat="item in epanetData.measuresSelected" ng-click="measureSelectedRemove(item)">{{item.name}}</button>
		   		</div>
	   		</div>
		</div>
   	
   		<div class="row">
	   		<div class="col-md-12">
  			
			<div class="alert alert-info alert-dismissible" ng-show="alert.info" role="alert">
			<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
			{{alert.info}}</div>
   			
	   		</div>
	   		
   		</div>
   		 <div class="form-group has-success has-feedback">
    
   	
	   	<div class="row">
	   		<div class="col-md-12">
		   		<div class="form-group">
			      <button class="btn btn-default" ng-click="exportPAT()">
				      	<spring:message code="epanet.form.button.generateFilePAT" />
			      </button>
			      <label ng-show="filePATToDownload" class="control-label has-success" for="downloadPATFile">
  			      <a href="#" class="btn btn-success" id="downloadPATFile" ng-show="filePATToDownload"><spring:message code="epanet.form.button.downloadFilePAT" /></a>
			      {{filePATToDownload}}
			      </label>
		   		</div>
	   		</div>
	   		</div>
	   	<div class="row">
	   		<div class="col-md-12">	   		
		   		<div class="form-group">
			      <button class="btn btn-default" ng-click="exportDAT()">
				      	<spring:message code="epanet.form.button.generateFileDAT" />
			      </button>
  			      <label ng-show="fileDATToDownload" class="control-label has-success" for="downloadDATFile">
  			      <a href="#" class="btn btn-success" id="downloadDATFile" ng-show="fileDATToDownload"><spring:message code="epanet.form.button.downloadFileDAT" /></a>
			      {{fileDATToDownload}}
			      </label>
		   		</div>
	   		</div>
	    </div>






	</div>
	</div>

			

</div>

  
<jsp:include page="../../common/footer.jsp" />
  
  
</body>


</html>
