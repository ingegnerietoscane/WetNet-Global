"use strict";

var wetnetDashboardControllers = angular.module('wetnetDashboardControllers', []);


wetnetDashboardControllers.controller("DashboardController", ['$scope', '$http', 'Districts', 'Measures', 'Events', 'RoundNumber', 'UsersPreferences','$log',
                                          				function($scope, $http, Districts, Measures, Events, RoundNumber, UsersPreferences,$log) {
        		
	
	/*GC - settaggio $log per debug*/
	$scope.$log = $log;
	
	Districts.getData(null, function (data) { //carica i distretti
								$scope.districts = data;
	 							$scope.addDestrictLayer();
	 							$scope.districtsLoaded = true;
	 							$scope.getFeatureOverlay();
	 							$scope.getEventsOverlay();
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
 	/*$scope.showDistricts = true;
 	$scope.showMeasures = false;
 	$scope.showEvents = true;*/
 	
 	/* GC 05/11/2015 */	
 	$scope.showDistrictsLevels = false;
 	$scope.showDistrictsLevels1 = false;
 	$scope.showDistrictsLevels2 = true;
 	$scope.showDistrictsLevels3 = false;
 	$scope.showDistrictsLevels4 = false;
 	
 	$scope.showMeasuresTypes = false;
 	$scope.showMeasuresTypes0 = false;
 	$scope.showMeasuresTypes1 = false;
 	$scope.showMeasuresTypes2 = false;
 	$scope.showMeasuresTypes3 = false;
 	$scope.showMeasuresTypes4 = false;
 	
 	$scope.showEventsTypes = true;
 	$scope.showEventsTypes1 = true;
 	$scope.showEventsTypes2 = true;
 	$scope.showEventsTypes3 = true;
 	$scope.showEventsTypes4 = true;
 	$scope.showEventsTypes5 = true;
 	
 	$scope.showEventsMarkers = false;
 	$scope.showEventsMarkersRanking = true;
 	$scope.showEventsMarkersValue = false;
 	$scope.showEventsMarkersDuration = false;
 	$scope.showEventsMarkersDelta = false;
 	/* END GC */
 	
 	$scope.showAreal = false;
 	$scope.showLinear = false;
 	$scope.showPunctual = false;
 	$scope.out = 0;
 	//***RC 26/10/2015***
 	//$scope.orderByField = '-ranking';
 	$scope.orderByField = '-delta_value';
 	//***END***
 	
 	
 	//$scope.districtLayer = new Object();
 	//$scope.measureLayer = new Object();
 	$scope.eventLayer = new Object();
 	
 	
 	/*GC 05/11/2015*/
 	$scope.districtLevelsLayer1 = new Object();
 	$scope.districtLevelsLayer2 = new Object();
 	$scope.districtLevelsLayer3 = new Object();
 	$scope.districtLevelsLayer4 = new Object();
 	
 	$scope.districtLevels1Source = new ol.source.GeoJSON();
 	$scope.districtLevels2Source = new ol.source.GeoJSON();
 	$scope.districtLevels3Source = new ol.source.GeoJSON();
 	$scope.districtLevels4Source = new ol.source.GeoJSON();
 	
 	$scope.measureTypeLayer0 = new Object();
 	$scope.measureTypeLayer1 = new Object();
 	$scope.measureTypeLayer2 = new Object();
 	$scope.measureTypeLayer3 = new Object();
 	$scope.measureTypeLayer4 = new Object();
 	
 	$scope.measureType0Source = new ol.source.GeoJSON();
 	$scope.measureType1Source = new ol.source.GeoJSON();
 	$scope.measureType2Source = new ol.source.GeoJSON();
 	$scope.measureType3Source = new ol.source.GeoJSON();
 	$scope.measureType4Source = new ol.source.GeoJSON(); 	
 	/* END GC */
 	
 	$scope.popup = new Object();
 	$scope.baseLayers = [];
 	$scope.geoJSONParser = new ol.format.GeoJSON();
 	$scope.districtSource = new ol.source.GeoJSON();
 	$scope.measureSource = new ol.source.GeoJSON();
 	$scope.map = new ol.Map({
					  	  target: 'map',
					  	  controls: ol.control.defaults().extend([ new ol.control.ScaleLine({ units:'metric' }) ]),
					  	  view: new ol.View({
						    		  center: ol.proj.transform([10.6, 43.7], 'EPSG:4326', 'EPSG:3857'),
						    		  zoom: 10
						    	  })
						});
 	 	
 	$scope.eventBean = new Object();
	$scope.tempDate = new Date();
	$scope.eventBean.endDate = new Date($scope.tempDate.getFullYear(), $scope.tempDate.getMonth(), $scope.tempDate.getDate() - 1, 0, 0, 0);
	$scope.eventBean.startDate = new Date($scope.tempDate.getFullYear(), $scope.tempDate.getMonth(), $scope.tempDate.getDate() - 1, 0, 0, 0);
 	$scope.eventsSize = -1;

	$scope.loadEvents = function() { //carica gli eventi
		if ($scope.eventBean.startDate != undefined && $scope.eventBean.endDate != undefined) {
			
			//***RC 26/10/2015***
		 	//$scope.orderByField = '-ranking';
			$scope.orderByField = '-delta_value';
		 	//***END***
			$scope.eventBean.endDate = new Date($scope.eventBean.endDate.getFullYear(), $scope.eventBean.endDate.getMonth(), $scope.eventBean.endDate.getDate(), 0, 0, 0);
			$scope.eventBean.startDate = new Date($scope.eventBean.endDate.getFullYear(), $scope.eventBean.endDate.getMonth(), $scope.eventBean.endDate.getDate(), 0, 0, 0);
			
			Events.getEvents($scope.eventBean, function(data) {
												$scope.events = data;
												$scope.eventsSize = data.length;
												$scope.eventsLoaded = true;
												
												
												/*GC 05/11/2015*/
												/*
												 * filtro gli eventi in base ai check selezionati per aggiornare la tabella
												 
												var eventsTemp = [];
												for(var j = 0; j < $scope.events.length; j++)
												{
													var e = $scope.events[j];
													
													// se l'evento non rientra in nessuno dei check selezionati non lo aggiungo
									 				if(!(($scope.showEventsTypes1 && e.type ==1) || ($scope.showEventsTypes2 && e.type ==2) 
									 						|| ($scope.showEventsTypes3 && e.type ==3) || ($scope.showEventsTypes4 && e.type ==4) 
									 						|| ($scope.showEventsTypes5 && e.type ==5)))
									 					{
									 					continue;
									 					}
									 				
									 				eventsTemp.push(e);
												}	
												
												$scope.events = eventsTemp;
												$scope.eventsSize = eventsTemp.length;
												*/
												/* end GC*/
												
												$scope.getEventsOverlay();
											});
		}
	}
	
	$scope.loadEvents(); //carica gli eventi
	
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
         
         $scope.popup = new ol.Overlay({
				           element: $("#popup"),
				           positioning: 'bottom-center',
				           stopEvent: false
				         });
         $scope.map.addOverlay($scope.popup);
     }
 	
 	$scope.createMap(); //crea la mappa
 	
 	/* GC 05/11/2015*/
 	/*
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
							offsetY: -17,
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
 	}*/
 	
 	//aggiunge il layer dei distretti alla mappa
 	$scope.addDestrictLayer = function(){
 		$scope.addDistrictsTypesLayer();
 	}
 	
 	$scope.addDistrictsTypesLayer = function(){
 		 for (var i=0, len=$scope.districts.length; i < len; i++){
	        if ($scope.districts[i].map && $scope.districts[i].mapLevel)
	        {
	        	$scope.districtSource.addFeatures($scope.geoJSONParser.readFeatures($scope.districts[i].map));
	        
		        	if($scope.districts[i].mapLevel == 1)
		        	{
		        		$scope.districtLevels1Source.addFeatures($scope.geoJSONParser.readFeatures($scope.districts[i].map));
		        	}
		        	else if($scope.districts[i].mapLevel == 2)
		        	{
		        		$scope.districtLevels2Source.addFeatures($scope.geoJSONParser.readFeatures($scope.districts[i].map));
		        	}
		        	else if($scope.districts[i].mapLevel == 3)
		        	{
		        		$scope.districtLevels3Source.addFeatures($scope.geoJSONParser.readFeatures($scope.districts[i].map));
		        	}
		        	else if($scope.districts[i].mapLevel == 4)
		        	{
		        		$scope.districtLevels4Source.addFeatures($scope.geoJSONParser.readFeatures($scope.districts[i].map));
		        	}
 		      }
	       }
         
 		 //Districts Level 1
 		var styleCacheD = {};
        $scope.districtLevel1Layer = new ol.layer.Vector({
            source: $scope.districtLevels1Source,
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
							offsetY: -17,
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
        
        $scope.map.addLayer($scope.districtLevel1Layer);
        
        
        
        //Districts Level 2
 		styleCacheD = {};
        $scope.districtLevel2Layer = new ol.layer.Vector({
            source: $scope.districtLevels2Source,
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
							offsetY: -17,
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
        
        $scope.map.addLayer($scope.districtLevel2Layer);
        
        //Districts Level 3
 		styleCacheD = {};
        $scope.districtLevel3Layer = new ol.layer.Vector({
            source: $scope.districtLevels3Source,
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
							offsetY: -17,
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
        
        $scope.map.addLayer($scope.districtLevel3Layer);
        
        //Districts Level 4
 		styleCacheD = {};
        $scope.districtLevel4Layer = new ol.layer.Vector({
            source: $scope.districtLevels4Source,
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
							offsetY: -17,
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
        
        $scope.map.addLayer($scope.districtLevel4Layer);
        
        $scope.updateDistricstView();      
 	}
 	
 	
 	/* END GC*/
 	
 	/* GC 05/11/2015*/
 	//aggiunge il layer delle misure alla mappa
 	$scope.addMeasureLayer = function(){
 		$scope.addMeasuresTypesLayer();
 	}
 	
 	
 	$scope.addMeasuresTypesLayer = function(){
		 for (var i=0, len = $scope.measures.length; i < len; i++){
 	        if ($scope.measures[i].map)
 	        	{
	 	        	$scope.measureSource.addFeatures($scope.geoJSONParser.readFeatures($scope.measures[i].map));
	 	        	
	 	        	if($scope.measures[i].type == 0)
		        	{
	 	        		$scope.measureType0Source.addFeatures($scope.geoJSONParser.readFeatures($scope.measures[i].map));
		        	}
	 	        	else if($scope.measures[i].type == 1)
		        	{
	 	        		$scope.measureType1Source.addFeatures($scope.geoJSONParser.readFeatures($scope.measures[i].map));
		        	}
		        	else if($scope.measures[i].type == 2)
		        	{
		        		$scope.measureType2Source.addFeatures($scope.geoJSONParser.readFeatures($scope.measures[i].map));
		        	}
		        	else if($scope.measures[i].type == 3)
		        	{
		        		$scope.measureType3Source.addFeatures($scope.geoJSONParser.readFeatures($scope.measures[i].map));
		        	}
		        	else if($scope.measures[i].type == 4)
		        	{
		        		$scope.measureType4Source.addFeatures($scope.geoJSONParser.readFeatures($scope.measures[i].map));
		        	}
 	        	}
        }
		 
		 
		 //measure type 0 
		var styleCacheM = {};
		$scope.measureTypeLayer0 = new ol.layer.Vector({
           source: $scope.measureType0Source,
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
       
	   $scope.measureTypeLayer0.setVisible(false);
       $scope.map.addLayer($scope.measureTypeLayer0);
       
       
       //measure type 1 
		styleCacheM = {};
		$scope.measureTypeLayer1 = new ol.layer.Vector({
          source: $scope.measureType1Source,
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
      
	   $scope.measureTypeLayer1.setVisible(false);
      $scope.map.addLayer($scope.measureTypeLayer1);
      
      //measure type 2 
		styleCacheM = {};
		$scope.measureTypeLayer2 = new ol.layer.Vector({
         source: $scope.measureType2Source,
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
     
	   $scope.measureTypeLayer2.setVisible(false);
     $scope.map.addLayer($scope.measureTypeLayer2);
       
   //measure type 3 
		styleCacheM = {};
		$scope.measureTypeLayer3 = new ol.layer.Vector({
      source: $scope.measureType3Source,
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
  
	   $scope.measureTypeLayer3.setVisible(false);
	   $scope.map.addLayer($scope.measureTypeLayer3);
	   

	 //measure type 4 
		styleCacheM = {};
		$scope.measureTypeLayer4 = new ol.layer.Vector({
        source: $scope.measureType4Source,
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
    
	$scope.measureTypeLayer4.setVisible(false);
    $scope.map.addLayer($scope.measureTypeLayer4);
    
    
    $scope.updateMeasuresView();    
}
 	
 	
 	
 	//costruisce l'Overlay per tutte le features della mappa
 	$scope.getFeatureOverlay = function(){
 		if ($scope.districtsLoaded && $scope.measuresLoaded){
	        $scope.highlightStyleCache = {};
	 		 $scope.featureOverlay = new ol.FeatureOverlay({
	    		map : $scope.map,
	    		style : function(feature, resolution) {
	    			var type = feature.get('type');
	    			if (type !== 'event'){
	    				var offsetY = (type === 'district') ? -17 : 0;
	    				//***RC 04/11/2015***
	    				
	    				if (type !== 'measure'){
	    					var text = resolution < 1500 ? feature.get('name') : '';
	    				}else{
	    					var text = '';
	    				}
	    				//***END***
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
									offsetY: offsetY,
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
	    		}
	    	 });
 		}
 	}
 	
 	//***RC 02/11/2015***
 	$scope.retrieveOrientationDegrees = function(name) {
 		for (var i=0, len = $scope.measures.length; i < len; i++){
  	        if ($scope.measures[i].name == name)
  	        	{
  	        	return $scope.measures[i].orientation_degrees;
  	        	}
         }
 	} 	
 	//***END***	
 	
 	$scope.highlight;
 	$scope.displayFeatureInfo = function(pixel) { //evidenzia la feature selezionata col mouse

 		var feature = $scope.map.forEachFeatureAtPixel(pixel, function(feature, layer) {
			return feature;
		});
		
		if (feature !== $scope.highlight) {
			if ($scope.highlight) {
				$scope.featureOverlay.removeFeature($scope.highlight);
				
				//***RC 02/11/2015***
				$("#popup").popover('destroy');
				//***END***	
			}
			if (feature) {
				$scope.featureOverlay.addFeature(feature);
				
				//***RC 02/11/2015***
				
    			if (feature.get('type') === 'measure') {
    				var od = $scope.retrieveOrientationDegrees(feature.get('name'));
    				var coord = feature.getGeometry().getCoordinates();
				    $scope.popup.setPosition(coord);
				    $("#popup").popover({
				      'placement': 'top',
				      'html': true,
				      'title': '<strong>' + feature.get('name') + '</strong>',
				      'content':'<div><img src="../images/cassa.png" style="position:absolute; width:80px; height:auto;"><img id="image_canv" src="../images/puntatore.png" style="width:80px; height:auto; -webkit-transform: rotate(' +od +'deg); -moz-transform: rotate(' +od +'deg); -o-transform: rotate(' +od +'deg); -ms-transform: rotate(' +od +'deg); transform: rotate(' +od +'deg);"></div>'
				    });
				    $("#popup").popover('show');
				}
    			//***END***	
			}
			$scope.highlight = feature;
		}
	};
	
	//va alla sezione modifica del configuratore per distretti e misure e mostra la popup per gli eventi
	$scope.goToLocation = function(pixel) {
		var feature = $scope.map.forEachFeatureAtPixel(pixel, function(feature, layer) {
			return feature;
		});
		if (feature) {
			var type = feature.get('type');
			var id = feature.get('id');
			if (type){
				if (type === 'measure') {
					//***RC 03/11/2015***
					//window.location.href = '/wetnet/manager/measure?id=' + id;
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
					//***END***
				} else if (type === 'district') {
				 	$scope.eventBean.districtsSelected = new Object();
					$scope.eventBean.districtsSelected.idDistricts = id;
					$scope.eventBean.districtsSelected.name = feature.get('name');;
					$scope.loadEvents();
				} 
				if (type === 'event') {
					var coord = feature.getGeometry().getCoordinates();
				    $scope.popup.setPosition(coord);
				    $("#popup").popover({
				      'placement': 'top',
				      'html': true,
				      'title': '<strong>' + feature.get('district') + '</strong>',
				      'content': '<p>' + feature.get('description') + '</p>' + '<p><i>Ranking = ' +  feature.get('ranking') + '</i></p>' + '</p>' + '<p><i>Delta value = ' +  feature.get('deltavalue') + '</i></p>'
				    });
				    $("#popup").popover('show');
				} else {
					$("#popup").popover('destroy');
				}
			}
		} else {
			$("#popup").popover('destroy');
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
 		
 		/*GC 05/11/2015*/
 		$scope.updateDistricstView();
 		$scope.updateMeasuresView();
 		$scope.getEventsOverlay();
 	}
 	
 	
 	/*GC 05/11/2015*/
 	$scope.updateDistricstView = function (){
 		$scope.districtLevel1Layer.setVisible($scope.showDistrictsLevels1);
 		$scope.districtLevel2Layer.setVisible($scope.showDistrictsLevels2);
 		$scope.districtLevel3Layer.setVisible($scope.showDistrictsLevels3);
 		$scope.districtLevel4Layer.setVisible($scope.showDistrictsLevels4);
 	}
 	
 	$scope.updateMeasuresView = function (){
 		$scope.measureTypeLayer0.setVisible($scope.showMeasuresTypes0);
 		$scope.measureTypeLayer1.setVisible($scope.showMeasuresTypes1);
 		$scope.measureTypeLayer2.setVisible($scope.showMeasuresTypes2);
 		$scope.measureTypeLayer3.setVisible($scope.showMeasuresTypes3);
 		$scope.measureTypeLayer4.setVisible($scope.showMeasuresTypes4);
 	}
 	
 	$scope.updateDistrictsLevelsCheck = function()
 	{
 		$scope.showDistrictsLevels1 = $scope.showDistrictsLevels;
 	 	$scope.showDistrictsLevels2 = $scope.showDistrictsLevels;
 	 	$scope.showDistrictsLevels3 = $scope.showDistrictsLevels;
 	 	$scope.showDistrictsLevels4 = $scope.showDistrictsLevels;
 	 	
 	 	$scope.updateView();
 	}
 	
 	$scope.updateMeasuresTypesCheck = function()
 	{
 		$scope.showMeasuresTypes0 = $scope.showMeasuresTypes;
 	 	$scope.showMeasuresTypes1 = $scope.showMeasuresTypes;
 	 	$scope.showMeasuresTypes2 = $scope.showMeasuresTypes;
 	 	$scope.showMeasuresTypes3 = $scope.showMeasuresTypes;
 	 	$scope.showMeasuresTypes4 = $scope.showMeasuresTypes;
 	 	
 	 	$scope.updateView();
 	}
 	
 	$scope.updateEventsTypesCheck = function()
 	{
 		$scope.showEventsTypes1 = $scope.showEventsTypes;
 	 	$scope.showEventsTypes2 = $scope.showEventsTypes;
 	 	$scope.showEventsTypes3 = $scope.showEventsTypes;
 	 	$scope.showEventsTypes4 = $scope.showEventsTypes;
 	 	$scope.showEventsTypes5 = $scope.showEventsTypes;
 	 	
 	 	$scope.updateView();
 	}
 	
 	$scope.updateEventsMarkersCheck = function()
 	{
 		$scope.showEventsMarkersRanking = $scope.showEventsMarkers;
 	 	$scope.showEventsMarkersValue = $scope.showEventsMarkers;
 	 	$scope.showEventsMarkersDuration = $scope.showEventsMarkers;
 	 	$scope.showEventsMarkersDelta = $scope.showEventsMarkers;
 	 	
 	 	$scope.getEventsOverlay();
 	}
 	/** end GC ***/
 	
 	
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
 	
 	//fa lo zoom sul distretto e carica i suoi eventi
 	$scope.districtSelectedZoom = function($item, $model, $label) {
 		var feature;
 		var features = $scope.districtSource.getFeatures();
	  	for (var i = 0, len = features.length; i < len; i++){
	  		if ($model.idDistricts === features[i].get('id')){
	  			feature = features[i];
	  			break;
	  		}
	  	}
	  	if (feature){
			var polygon = feature.getGeometry();
			var size = $scope.map.getSize();
			$scope.map.getView().fitGeometry(polygon, size, {
				padding : [ 70, 70, 70, 70 ]
			});
			
			$scope.eventBean.districtsSelected = $model;
			$scope.loadEvents();
	  	}
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
	  	if (feature){
			var point = feature.getGeometry();
			var size = $scope.map.getSize();
			$scope.map.getView().fitGeometry(point, size, {
				padding : [ 70, 70, 70, 70 ],
				minResolution: 20
			});
	  	}
	}
 	
 	//individua il distretto cliccando sulla lista eventi
 	$scope.fromListToMap = function(id){
 		var feature;
 		var features = $scope.districtSource.getFeatures();
	  	for (var i = 0, len = features.length; i < len; i++){
	  		if (id === features[i].get('id')){
	  			feature = features[i];
	  			break;
	  		}
	  	}
	  	if (feature){
			var polygon = feature.getGeometry();
			var size = $scope.map.getSize();
			$scope.map.getView().fitGeometry(polygon, size, {
				padding : [ 70, 70, 70, 70 ]
			});
	  	}
 	}
 	
 	/* GC 05/11/2015*/
 	
 	
 	//crea layer con icone degli eventi e ranking
 	$scope.getEventsOverlay = function(){
 		if ($scope.eventsLoaded && $scope.districtsLoaded){
 			
 			var vectorSource = new ol.source.Vector();
 			
 			if (!$scope.eventBean.districtsSelected) $scope.out = 0;
 			for (var i=0, len=$scope.events.length; i < len; i++){
 				var e = $scope.events[i];
 				
 				if (!$scope.eventBean.districtsSelected && e.type == 5 ) $scope.out++;
 				
 				/* GC 05/11/2015*/
 			// se l'evento non rientra in nessuno dei check selezionati non lo aggiungo
 				if(!(($scope.showEventsTypes1 && e.type ==1) || ($scope.showEventsTypes2 && e.type ==2) 
 						|| ($scope.showEventsTypes3 && e.type ==3) || ($scope.showEventsTypes4 && e.type ==4) 
 						|| ($scope.showEventsTypes5 && e.type ==5)))
 					{
 					continue;
 					}
 				
 					
 				
 				var feature = $scope.getFeatureByAttributeId($scope.districtSource, e.districts_id_districts);
 				if (feature){
 					var coor = ol.extent.getCenter(feature.getGeometry().getExtent());
	 				var imgsrc = '';
	 				var eventColor = '#000000';
	 				if (e.type == 1) {
	 					imgsrc = '../../images/anomal_increase_found.png';
	 					eventColor = '#FF9900';
	 				} else if (e.type == 2) {
	 					imgsrc = '../../images/possible_water_loss.png';
	 					eventColor = '#FF0000';
	 				} else if (e.type == 3) {
	 					imgsrc = '../../images/anomal_decrease_found.png';
	 					eventColor = '#99CC33';
	 				} else if (e.type == 4) {
	 					imgsrc = '../../images/possible_water_gain.png';
	 					eventColor = '#3399FF';
	 				} else if (e.type == 5) {
	 					imgsrc = '../../images/out_of_control.png';
	 					eventColor = '#000000';
	 				}
	 				
	 				//var rPerCent = e.ranking + '%';
	 				
	 				
	 				/*GC 05/11/2015*/
	 				/*
	 				 * disegno label per gli evento a seconda dei check selezionati
	 				 */
	 				var rPerCent = '';
	 				if($scope.showEventsMarkersRanking)
	 					{
	 					rPerCent = e.ranking + '% ';
	 					}
	 				if($scope.showEventsMarkersValue)
	 					{
	 					rPerCent = rPerCent + e.value + '[l/s] ';
	 					}
	 				if($scope.showEventsMarkersDuration)
 						{
	 					rPerCent = rPerCent + e.duration + '[gg] ';
 						}
	 				if($scope.showEventsMarkersDelta)
	 					{
	 					rPerCent = rPerCent + e.delta_value + '[l/s] ';
	 					}
	 				
 					var iconFeature = new ol.Feature({ //crea punto evento e associa properties
						 				  geometry: new ol.geom.Point(coor),
						 				  type:'event',
						 				  description: e.description,
						 				  district: e.district_name,
						 				  ranking: rPerCent,
						 				  deltavalue: e.delta_value
					 					});

	 				var iconStyle = new ol.style.Style({ //associa icona evento e ranking
					 					  	image: new ol.style.Icon(({
									 					    anchor: [0.5, 0.5],
									 					    anchorXUnits: 'fraction',
									 					    anchorYUnits: 'fraction',
									 					    scale: 0.4,
									 					    src: imgsrc
					 					  				})),
		 					  				text: new ol.style.Text({
					 										font : '14px Calibri,sans-serif',
					 										text : rPerCent,
					 										textAlign: 'center',
					 										textBaseline: 'top',
					 										offsetX: 16,
					 										offsetY: 11,
					 										fill : new ol.style.Fill({
					 											color : eventColor
					 										}),
					 										stroke : new ol.style.Stroke({
					 											color : eventColor,
					 											width : 1
					 										})
					 									})
					 								});
	 				
	 				iconFeature.setStyle(iconStyle);
	
	 				vectorSource.addFeature(iconFeature);
 				}
 			}
 			
 			$scope.map.removeLayer($scope.eventLayer);
 			
 			$scope.eventLayer = new ol.layer.Vector({
									  source: vectorSource
									});
 			
 			$scope.map.addLayer($scope.eventLayer);
 			
 			$scope.loadChartData($scope.districts.length, $scope.out);
 		}
 	}
 	
 	
 	
 	
 	
 	
 	
 	$scope.getFeatureByAttributeId = function(source, id){
 		var features = source.getFeatures();
 		for (var i=0, len=features.length; i<len; i++){
 			if (id === features[i].get('id')){
 				return features[i];
 			}
 		}
 	}
 	
	//rimuove il distretto selezionato
	$scope.removeDistrict = function() {
		$scope.eventBean.districtsSelected = null;
		$scope.loadEvents();
	}
	
	//genera grafico a torta
	$scope.loadChartData = function(ok, out) {
		var rows = [["OK (" + ok + ")", ok],["OUT (" + out + ")", out]];
		var chart = c3.generate({
			bindto : '#pie-chart',
			data : {
				columns : rows,
				type : 'pie'
			},
			legend : {
				position : 'right'
			},
		    size: { 
		    	height: 240
	    	},
			padding: {
				left: 5,
				right: 5,
				top: 40,
				bottom: 5
			},
			color: {
		        pattern: ['#33CC33', '#330000']
		    }
		});
  	}
	
	//ordina la lista degli eventi al click
	$scope.sortEventsList = function(fieldToSortBy){
		var order = ($scope.orderByField.charAt(0) == '+') ? '-' : '+';
		$scope.orderByField = order + fieldToSortBy;
	}
	
 	$scope.openEndDate = function($event) {
		$event.preventDefault();
		$event.stopPropagation();
		$scope.openedEndDate = true;
	};
	
}]);
