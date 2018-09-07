"use strict";

var wetnetMiscControllers = angular.module('wetnetMiscControllers', []);


wetnetMiscControllers.controller("WetnetControllerNavigation", ['$scope', '$http', '$location', 'DataDistricts', function($scope, $http, $location, DataDistricts) {
	$scope.currUrl = window.location.pathname;
}]);

wetnetMiscControllers.controller("WetnetInfoController", ['$scope', '$http', '$location', 'Info', function($scope, $http, $location, Info) {
		
	Info.getWetnetVersion($scope, function (data) {
			$scope.wetnetVersion = data.wetnetVersion;
	}); 
}]);

wetnetMiscControllers.controller("PasswordModifyController", ['$scope', '$http', '$location', function($scope, $http, $location) {
	
	
	$http({method: 'GET', url: '/wetnet/rest/users/current'}).
	  success(function(data, status, headers, config) {
			  	$scope.user = data;
	  }).
	  error(function(data, status, headers, config) {
	    // if there's an error you should see it here
	});

	
	$scope.modifyPassword = function(){
		if($scope.passwordModifyForm.$valid){
		$http({method: 'POST', url: '/wetnet/rest/users/checkPassword', data: $scope.oldPassword}).
		  success(function(data, status, headers, config) {
			  if(data.status == 'success'){
				  	//TODO verifico che le due pwd siano uguali, necessario i18n per questo alert 
					if($scope.newPassword != $scope.newPasswordConfirm){
						console.log($scope.newPassword);
						$scope.alert.danger = "le password non corrispondono";
						return;
					}
					$http({method: 'POST', url: '/wetnet/rest/users/passwordModify', data: $scope.newPassword}).
					  success(function(data, status, headers, config) {
						  if(data.status == 'success'){
								$scope.alert = new Object();
								$scope.alert.success = data.message;
								$scope.newPassword = "";
								$scope.newPasswordConfirm = "";
								$scope.oldPassword = "";
							}
							else{
								$scope.alert = new Object();
					            $scope.alert.danger = data.message;	
							}
					  }).
					  error(function(data, status, headers, config) {
						  $scope.alert.danger = data.message;
						  return;
					  });
				}
				else{
					$scope.alert = new Object();
					$scope.alert.danger = data.message;
		            	return;
				}
		  }).
		  error(function(data, status, headers, config) {
			  $scope.alert.danger = data.message;
			  return;
		});
		
		};// fine if principale
	};

}]);



wetnetControllers.controller("EpanetController", ['$scope', '$http', 'Districts', 'Measures', 'TimeSelectorRadio', 'RoundNumber', '$filter',
                                    				function($scope,$http, Districts, Measures, TimeSelectorRadio, RoundNumber, $filter) {
	//carico i distretti utlizzati per la ricerca
	$scope.districts = Districts.getData();
	$scope.measures = Measures.getData();
	$scope.columns = [];
	
	//inizializzazioni
	$scope.epanetData = new Object();
	$scope.epanetData.districtsSelected = new Object();
	$scope.epanetData.measuresSelected = new Array();
	$scope.epanetData.startDate = new Date();
	$scope.epanetData.endDate = new Date();
	$scope.epanetData.startDate.setDate($scope.epanetData.endDate.getDate() - 1);
	$scope.isCollapsed = false;

	//Inizio gestione Selezione Distretti (solo un distretto puo essere selezionatao)
	$scope.districtSelectedAdd = function($item, $model, $label){
		
		/* GC - 29/10/2015*/
		$model.measures = Measures.getDataByDistrictId({districtId: $model.idDistricts, withSign:false});
		$scope.epanetData.districtsSelected = $model;
		$scope.dSelected = new Object();
	}

	$scope.districtSelectedRemove = function($item){
		$scope.epanetData.districtsSelected = new Object();
	}
	//Fine gestione Selezione Distretti

	//Inizio gestione Selezione Measures 
	$scope.measureSelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
		var alreadyAdded = false;
		for(var i=0; i< $scope.epanetData.measuresSelected.length; i++){
			var d = $scope.epanetData.measuresSelected[i];
			if(d.name == $item.name){
				alreadyAdded = true;
			}
		}
		if(!alreadyAdded){
			var size = $scope.epanetData.measuresSelected.length; 
			$scope.epanetData.measuresSelected[size] = $model;			
		}
	}

	$scope.measureSelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i=0; i< $scope.epanetData.measuresSelected.length; i++){
			var d = $scope.epanetData.measuresSelected[i];
			if(d.name !== $item.name){
				tempSelected.push(d);
			}
		}
		$scope.epanetData.measuresSelected = tempSelected;
	}
	//Fine gestione Selezione Measures


	// callback for ng-click 'exportPAT':
	$scope.exportPAT = function () {
		$scope.alert = new Object();
		$scope.filePATToDownload = null;
		$http({method: 'POST', url: '/wetnet/rest/epanet/pat', data: $scope.epanetData}).
		success(function(data, status, headers, config) {
			if(data.status == 'success'){
				var nomeFile = data.data.data.responseFileName;
			    var element = angular.element('#downloadPATFile');
			    element.attr({
			         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data.data.data.responseDataFile),
			         target: '_blank',
			         download: nomeFile
			    });
			    $scope.filePATToDownload = nomeFile;
			    $scope.alert.info = "file genarato e pronto per essere scaricato"	
			}
			
		}).
		error(function(data, status, headers, config) {
			console.log("errore" + data);
			// if there's an error you should see it here
		});
	}
	
	// callback for ng-click 'exportDAT':
	$scope.exportDAT = function () {
		$scope.alert = new Object();
		$scope.fileDATToDownload = null;
		$http({method: 'POST', url: '/wetnet/rest/epanet/dat', data: $scope.epanetData}).
		success(function(data, status, headers, config) {
			var nomeFile = data.data.data.responseFileName;
		    var element = angular.element('#downloadDATFile');
		    element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data.data.data.responseDataFile),
		         target: '_blank',
		         download: nomeFile
		    });
		    $scope.fileDATToDownload = nomeFile;
		    $scope.alert.info = "file genarato e pronto per essere scaricato"
		}).
		error(function(data, status, headers, config) {
			console.log("errore" + data);
			// if there's an error you should see it here
		});
	}

	//configurazioni datepicker
	$scope.openStartDate = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.openedStartDate = true;
	};
	$scope.openEndDate = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.openedEndDate = true;
	};

	//configurazioni timepicker
	$scope.hstep = 1;
	$scope.mstep = 10;

	$scope.updateDate = function () {
		$scope.epanetData.startDate = TimeSelectorRadio($scope.radioModel, $scope.epanetData.endDate);
	}

}]);


wetnetMiscControllers.controller("MapViewControllerOl2", ['$scope', '$http', 'Districts', 'Measures',
                                     				function($scope, $http, Districts, Measures) {
   	
	Districts.getData(null, function (data) {
		 							$scope.districts = data;	 							
		 							$scope.createMap();
									return data;
								});
	
	$scope.measures = Measures.getData();
   	
	$scope.geoJSONParser = new OpenLayers.Format.GeoJSON();
	$scope.map = new Object(), $scope.vectors = new Object(), $scope.controls = new Object();
	
	$scope.createMap = function(){
    	$scope.map = new OpenLayers.Map('map'); 
    	var osm = new OpenLayers.Layer.OSM();
    	var googleTerrain = new OpenLayers.Layer.Google("Google Physical",
		                {type: google.maps.MapTypeId.TERRAIN}
		            );
    	var googleStreets = new OpenLayers.Layer.Google("Google Streets",
		                {numZoomLevels: 20}
		            );
    	var googleHybrid = new OpenLayers.Layer.Google("Google Hybrid",
		                {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20}
		            );
    	var googleSatellite = new OpenLayers.Layer.Google("Google Satellite",
		                {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22}
		            );
        
    	OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';

        // allow testing of specific renderers via "?renderer=Canvas", etc
        var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
        renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;

        $scope.vectors = new OpenLayers.Layer.Vector("Districts Layer", {
            renderers: renderer
        });

        for (var i=0; i < $scope.districts.length; i++){
	        if ($scope.districts[i].map)
	        	$scope.vectors.addFeatures($scope.geoJSONParser.read($scope.districts[i].map));
        }
        
        $scope.map.addLayers([osm, googleTerrain, googleStreets, googleSatellite, googleHybrid, $scope.vectors]);
        $scope.map.addControl(new OpenLayers.Control.LayerSwitcher());
        //$scope.map.addControl(new OpenLayers.Control.MousePosition());
        
        $scope.map.setCenter(new OpenLayers.LonLat(10.6, 43.5).transform(new OpenLayers.Projection("EPSG:4326"), $scope.map.getProjectionObject()), 8);
    }
   	
}]);

wetnetMiscControllers.controller("MapViewControllerOl3", ['$scope', '$http', 'Districts', 'Measures',
                                          				function($scope, $http, Districts, Measures) {
        	
	Districts.getData(null, function (data) { //carica i distretti
	 							$scope.districts = data;
	 							$scope.addDestrictLayer();
	 							$scope.districtsLoaded = true;
	 							$scope.getFeatureOverlay();
							});
	Measures.getData(null, function (data) { //carica le misure
								$scope.measures = data;
								$scope.addMeasureLayer();
								$scope.measuresLoaded = true;
								$scope.getFeatureOverlay();
							});
    	
	$scope.arealLayer = function () {
						var layer = new ol.layer.Vector({
								source : new ol.source.KML({
									projection : ol.proj.get('EPSG:3857'),
									url : '/wetnet/rest/map-view/areal-kml'
								})
						});
						return layer;
					}
	
	$scope.linearLayer = function () {
						var layer = new ol.layer.Vector({
								source : new ol.source.KML({
									projection : ol.proj.get('EPSG:3857'),
									url : '/wetnet/rest/map-view/linear-kml'
								})
						});
						return layer;
					}
	
	$scope.punctualLayer = function () {
						var layer = new ol.layer.Vector({
								source : new ol.source.KML({
									projection : ol.proj.get('EPSG:3857'),
									url : '/wetnet/rest/map-view/punctual-kml'
								})
						});
						return layer;
					}
	
	//inizializza scope fields
 	$scope.radioItem = '0';
 	$scope.showDistricts = true;
 	$scope.showMeasures = false;
 	$scope.showAreal = false;
 	$scope.showLinear = false;
 	$scope.showPunctual = false;
 	$scope.districtLayer = new Object();
 	$scope.measureLayer = new Object();
 	$scope.baseLayers = [];
 	$scope.geoJSONParser = new ol.format.GeoJSON();
 	$scope.districtSource = new ol.source.GeoJSON();
 	$scope.measureSource = new ol.source.GeoJSON();
 	$scope.map = new ol.Map({
					  	  target: 'map',
					  	  controls: ol.control.defaults().extend([ new ol.control.ScaleLine({ units:'metric' }) ]),
					  	  view: new ol.View({
						    		  center: ol.proj.transform([10.6, 43.5], 'EPSG:4326', 'EPSG:3857'),
						    		  zoom: 8
						    	  })
						});
 	
 	//crea la mappa
 	$scope.createMap = function(){
 			
 		$scope.baseLayers[0] = new ol.layer.Tile({ source: new ol.source.OSM() });
 		$scope.baseLayers[1] = new ol.layer.Tile({ source: new ol.source.MapQuest({layer: 'osm'}), visible: false });
 		$scope.baseLayers[2] = new ol.layer.Tile({ source: new ol.source.MapQuest({layer: 'sat'}), visible: false });
 		$scope.baseLayers[3] = new ol.layer.Group({ layers: [ new ol.layer.Tile({ source: new ol.source.MapQuest({layer: 'sat'})}),
                                                               new ol.layer.Tile({ source: new ol.source.MapQuest({layer: 'hyb'})}) ] });
         
 		$scope.baseLayers[3].setVisible(false);
 		
 		$scope.map.addLayer($scope.baseLayers[0]);
 		$scope.map.addLayer($scope.baseLayers[1]);
 		$scope.map.addLayer($scope.baseLayers[2]);
 		$scope.map.addLayer($scope.baseLayers[3]);
 		      
         //registra l'onMouseMove sulla mappa
         $($scope.map.getViewport()).on('mousemove', function(evt) {
        	 $scope.displayFeatureInfo($scope.map.getEventPixel(evt.originalEvent));
     	 });
         
         //registra l'onSingleClick sulla mappa
         $scope.map.on('singleclick', function(evt) {
        	 $scope.goToLocation($scope.map.getEventPixel(evt.originalEvent));
     	 });
     }
 	
 	$scope.createMap(); //crea la mappa
 	
 	//aggiunge il layer dei distretti alla mappa
 	$scope.addDestrictLayer = function(){
 		 for (var i=0, len=$scope.districts.length; i < len; i++){
	        if ($scope.districts[i].map)
	        	$scope.districtSource.addFeatures($scope.geoJSONParser.readFeatures($scope.districts[i].map));
	     }
         
 		var styleCacheD = {};
        $scope.districtLayer = new ol.layer.Vector({
            source: $scope.districtSource,
            style : function(feature, resolution) {
				var text = resolution < 50 ? feature.get('name') : '';
				if (!styleCacheD[text]) {
					styleCacheD[text] = [ new ol.style.Style({
						fill : new ol.style.Fill({
							color : 'rgba(255, 255, 255, 0.6)'
						}),
						stroke : new ol.style.Stroke({
							color : '#319FD3',
							width : 2
						}),
						text : new ol.style.Text({
							font : '13px Calibri,sans-serif',
							text : text,
							textAlign: 'center',
							textBaseline: 'bottom',
							fill : new ol.style.Fill({
								color : '#000'
							}),
							stroke : new ol.style.Stroke({
								color : '#00BFFF',
								width : 1
							})
						})
					}) ];
				}
				return styleCacheD[text];
			}
        });
        
        $scope.map.addLayer($scope.districtLayer);
 	}
 	
 	//aggiunge il layer delle misure alla mappa
 	$scope.addMeasureLayer = function(){
 		 for (var i=0, len = $scope.measures.length; i < len; i++){
  	        if ($scope.measures[i].map)
  	        	$scope.measureSource.addFeatures($scope.geoJSONParser.readFeatures($scope.measures[i].map));
         }
 		 
 		var styleCacheM = {};
        $scope.measureLayer = new ol.layer.Vector({
            source: $scope.measureSource,
            visible: false,
            style : function(feature, resolution) {
				var text = resolution < 50 ? feature.get('name') : '';
				if (!styleCacheM[text]) {
					styleCacheM[text] = [ new ol.style.Style({
						image: new ol.style.Circle({
				               radius: 6,
				               fill: new ol.style.Fill({color: 'rgba(255, 0, 0, 0.3)'}),
				               stroke: new ol.style.Stroke({color: 'rgba(255, 0, 0, 0.6)', width: 2})
				             }),
						text : new ol.style.Text({
							font : '13px Calibri,sans-serif',
							text : text,
							textAlign: 'center',
							textBaseline: 'bottom',
							fill : new ol.style.Fill({
								color : '#000'
							}),
							stroke : new ol.style.Stroke({
								color : 'red',
								width : 1
							})
						})
					}) ];
				}
				return styleCacheM[text];
			}
        });
        
        $scope.measureLayer.setVisible(false);
        $scope.map.addLayer($scope.measureLayer);
 	}
 	
 	//costruisce l'Overlay per tutte le features della mappa
 	$scope.getFeatureOverlay = function(){
 		if ($scope.districtsLoaded && $scope.measuresLoaded){
	        $scope.highlightStyleCache = {};
	 		 $scope.featureOverlay = new ol.FeatureOverlay({
	    		map : $scope.map,
	    		style : function(feature, resolution) {
	    			var text = resolution < 1500 ? feature.get('name') : '';
	    			if (!$scope.highlightStyleCache[text]) {
	    				$scope.highlightStyleCache[text] = [ new ol.style.Style({
	    					stroke : new ol.style.Stroke({
	    						color : 'rgba(0, 0, 255, 0.6)',
	    						width : 2
	    					}),
	    					fill : new ol.style.Fill({
	    						color : 'rgba(0, 0, 255, 0.3)'
	    					}),
	    					image: new ol.style.Circle({
					               radius: 6,
					               fill: new ol.style.Fill({color: 'rgba(255, 0, 0, 0.5)'}),
					               stroke: new ol.style.Stroke({color: 'rgba(255, 0, 0, 0.9)', width: 3})
					             }),
	    					text : new ol.style.Text({
	    						font : '13px Calibri,sans-serif',
	    						text : text,
	    						textAlign: 'center',
								textBaseline: 'bottom',
	    						fill : new ol.style.Fill({
	    							color : '#000'
	    						}),
	    						stroke : new ol.style.Stroke({
	    							color : '#000',
	    							width : 1
	    						})
	    					})
	    				}) ];
	    			}
	    			return $scope.highlightStyleCache[text];
	    		}
	    	 });
 		}
 	}
 	
 	$scope.highlight;
 	$scope.displayFeatureInfo = function(pixel) { //evidenzia la feature selezionata col mouse

		var feature = $scope.map.forEachFeatureAtPixel(pixel, function(feature, layer) {
			return feature;
		});
		
		if (feature !== $scope.highlight) {
			if ($scope.highlight) {
				$scope.featureOverlay.removeFeature($scope.highlight);
			}
			if (feature) {
				$scope.featureOverlay.addFeature(feature);
			}
			$scope.highlight = feature;
		}
	};
	
	//va alla sezione modifica del configuratore per distretti e misure
	$scope.goToLocation = function(pixel) {
		var feature = $scope.map.forEachFeatureAtPixel(pixel, function(feature, layer) {
			return feature;
		});
		if (feature) {
			var type = feature.get('type');
			var id = feature.get('id');
			if (type){
				if (type === 'measure') {
					
					/* GC 11/11/2015*/
					var currentDate = new Date();
					var dd = currentDate.getDate();
					var mm = currentDate.getMonth()+1;
					var yyyy = currentDate.getFullYear();

					if(dd<10) {
					    dd='0'+dd;
					} 
					if(mm<10) {
					    mm='0'+mm;
					} 
					
					currentDate = yyyy +'-' +mm +'-' +dd;
					window.location.href = '/wetnet/graphics/statistic-g2M?idMeasures=' +id +'&day=' +currentDate +'&duration=4';
					
					
					
					//window.location.href = '/wetnet/manager/measure?id=' + id;
				} else if (type === 'district') {
					window.location.href = '/wetnet/manager/district?id=' + id;
				}
			}
		}
	};
	
	//gestisce il menu di cambio del base layer
 	$scope.switchLayer = function (){
 		 for (var i = 0, len = $scope.baseLayers.length; i < len; i++){
 			 $scope.baseLayers[i].setVisible(i == $scope.radioItem);
 		 }
 	}
 	
 	//mostra nasconde i layers di distretti e misure
 	$scope.updateView = function (){
 		$scope.districtLayer.setVisible($scope.showDistricts);
 		$scope.measureLayer.setVisible($scope.showMeasures); 		
 	}
 	
 	//aggiunge toglie il layer areale
 	$scope.updateAreal = function (){
 		if ($scope.showAreal){
 			$scope.areal = $scope.arealLayer();
 			$scope.map.addLayer($scope.areal); 
 		} else {
 			$scope.map.removeLayer($scope.areal);
 		}
 	}
 	
 	//aggiunge toglie il layer lineare
 	$scope.updateLinear = function (){
 		if ($scope.showLinear) {
 			$scope.linear = $scope.linearLayer();
 			$scope.map.addLayer($scope.linear); 
 		} else {
 			$scope.map.removeLayer($scope.linear);
 		}
 	}

 	//aggiunge toglie il layer puntuale
 	$scope.updatePunctual = function (){
 		if ($scope.showPunctual) {
 			$scope.punctual = $scope.punctualLayer();
 			$scope.map.addLayer($scope.punctual);
 		} else {
 			$scope.map.removeLayer($scope.punctual);
 		}
	}
 	
 	//fa lo zoom sul distretto
 	$scope.districtSelectedZoom = function($item, $model, $label) {
 		var feature;
 		var features = $scope.districtSource.getFeatures();
	  	for (var i = 0, len = features.length; i < len; i++){
	  		if ($model.idDistricts === features[i].get('id')){
	  			feature = features[i];
	  			break;
	  		}
	  	}
		var polygon = feature.getGeometry();
		var size = $scope.map.getSize();
		$scope.map.getView().fitGeometry(polygon, size, {
			padding : [ 70, 70, 70, 70 ]
		});
	}
 	
 	//fa lo zoom sulla misura
 	$scope.measureSelectedZoom = function($item, $model, $label) {
		var feature;
 		var features = $scope.measureSource.getFeatures();
	  	for (var i = 0, len = features.length; i < len; i++){
	  		if ($model.idMeasures === features[i].get('id')){
	  			feature = features[i];
	  			break;
	  		}
	  	}
		var point = feature.getGeometry();
		var size = $scope.map.getSize();
		$scope.map.getView().fitGeometry(point, size, {
			padding : [ 70, 70, 70, 70 ],
			minResolution: 20
		});
	}
 	
 }]);

wetnetMiscControllers.controller("KmlUploadController", ['$scope', '$http',
                                     				function($scope, $http) {

}]);
