"use strict";

var wetnetManagerControllers = angular.module('wetnetManagerControllers', []);


wetnetManagerControllers.controller("ManagerUsersController", ['$scope', '$http', '$location', 'Users', function($scope, $http, $location, Users) {
	$scope.users = Users.get();
}]);

wetnetManagerControllers.controller("ManagerUserController", ['$scope', '$http', '$location', 'Users', 'Districts', 'UsersHasDistricts', 'Zone', 'Municipality', 'WaterAuthority', function($scope, $http, $location, Users, Districts, UsersHasDistricts, Zone, Municipality, WaterAuthority) {
	$scope.alert = new Object();
    var parnaNameId = "id";
    var regex = new RegExp("[\\?&]" + parnaNameId + "=([^&#]*)"), results = regex.exec(location.search);
    var idValue = results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    
    $scope.user = new Object();
   // $scope.districts = Districts.getData();
    
   
    
    //***RC 04/11/2015***
    $scope.zones = Zone.getData();
  	$scope.municipalities = Municipality.getData();
  	$scope.waterAuthorities = WaterAuthority.getData();
  	$scope.filterValue = '';

  	$scope.filterValueZ = '';
  	$scope.filterValueW = '';
  	$scope.filterValueM = '';
  	$scope.filteredDistrictsModel = [];
  	$scope.filteredDistrictsData = [];
  	$scope.filteredDistrictsSettings = {enableSearch: true, closeOnBlur: true, showCheckAll: false, showUncheckAll:false};
  	
  	 Districts.getData(null, function (data) { //carica i distretti
		    $scope.districts = data;
		   
			for(var c = 0; c < $scope.districts.length; c++){
					var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
					$scope.filteredDistrictsData.push(comboDistricts);
			}
		});
 
 
  	
  	
  	//***END***
    
    if(idValue != ""){
    	$scope.user = Users.getById({id: idValue});
    	 UsersHasDistricts.getByUsersId({usersid: idValue}, function(data){
    		 $scope.usersHasDistricts = data.data.districts;
    	});
    }
	
    $scope.districtSelectedAdd = function($item, $model, $label){
    	UsersHasDistricts.add({usersid: $scope.user.idusers, districts_id: $model.idDistricts}, function(data){
	    	UsersHasDistricts.getByUsersId({usersid: $scope.user.idusers}, function(data){
	   		 $scope.usersHasDistricts = data.data.districts;
	    	});
    	});
    }
    
    $scope.districtSelectedRemove = function($item){
    	UsersHasDistricts.remove({usersid: $scope.user.idusers, districts_id: $item.districts_id_districts}, function(data){
	    	UsersHasDistricts.getByUsersId({usersid: $scope.user.idusers}, function(data){
	      		 $scope.usersHasDistricts = data.data.districts;
	       	});
    	});
    }
    
    $scope.districtSelectedUpdate = function($item){
    	UsersHasDistricts.update($item, function(data){
        	UsersHasDistricts.getByUsersId({usersid: $scope.user.idusers}, function(data){
         		 $scope.usersHasDistricts = data.data.districts;
          	});    		
    	});
    }
    
    //***RC 04/11/2015***
    $scope.filterDistricts = function(filterType) {
    	
    	$scope.filteredDistrictsData = [];
    	
		if(filterType == 'zone'){
			
			
			$scope.filterValueW = '';
			$scope.filterValueM = '';
			if($scope.filterValueZ === '')
				{
					for(var c = 0; c < $scope.districts.length; c++){
						  	var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
							$scope.filteredDistrictsData.push(comboDistricts);
						
					}
				}
			else{
			
			
			for(var c = 0; c < $scope.districts.length; c++){
				var zone = $scope.districts[c].zone;
				if(zone != null && zone.trim().length>0 && zone.toUpperCase() == $scope.filterValueZ.toUpperCase()){
				  	var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
					$scope.filteredDistrictsData.push(comboDistricts);
				}
			}
		  }
		}else if(filterType == 'municipality'){
			
			$scope.filterValueW = '';
			$scope.filterValueZ = '';
			if($scope.filterValueM === '')
				{
					for(var c = 0; c < $scope.districts.length; c++){
						  	var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
							$scope.filteredDistrictsData.push(comboDistricts);
					}
				}
			else{
			
			
			
			for(var c = 0; c < $scope.districts.length; c++){
				var municipality = $scope.districts[c].municipality;
				if(municipality != null && municipality.trim().length>0 && municipality.toUpperCase() == $scope.filterValueM.toUpperCase()){
					var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
					$scope.filteredDistrictsData.push(comboDistricts);
				}
			}
		  }
		}else{
			
			$scope.filterValueM = '';
			$scope.filterValueZ = '';
			if($scope.filterValueW === '')
				{
					for(var c = 0; c < $scope.districts.length; c++){
						  	var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
							$scope.filteredDistrictsData.push(comboDistricts);
					}
				}
			else{
			
			
			for(var c = 0; c < $scope.districts.length; c++){
				var waterAuthority = $scope.districts[c].waterAuthority;
				if(waterAuthority != null && waterAuthority.trim().length>0 && waterAuthority.toUpperCase() == $scope.filterValueW.toUpperCase()){
					var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
					$scope.filteredDistrictsData.push(comboDistricts);
				}
			}
		  }
		}
	};
	
	$scope.filteredDistrictsEvents = {
			onItemSelect: 
				function(item) {
                	UsersHasDistricts.add({usersid: $scope.user.idusers, districts_id: item.id}, function(data){
            	    	UsersHasDistricts.getByUsersId({usersid: $scope.user.idusers}, function(data){
            	   		 $scope.usersHasDistricts = data.data.districts;
            	    	});
                	});
            	},
            onItemDeselect: 
            	function(item) {
	            	UsersHasDistricts.remove({usersid: $scope.user.idusers, districts_id: item.id}, function(data){
	        	    	UsersHasDistricts.getByUsersId({usersid: $scope.user.idusers}, function(data){
	        	      		 $scope.usersHasDistricts = data.data.districts;
	        	       	});
	            	});
               	}
    };
  	//***END***
	
	$scope.save = function () {
		Users.save($scope.user, 
		function (data) {
			if(data.status == 'success'){
				$scope.alert = new Object();
                $scope.alert.success = data.message;
                $scope.user = data.data.user;
			}
			else{
				$scope.alert = new Object();
	            $scope.alert.danger = data.message;;	
			}
            }
		)
	};
	
	$scope.cancel = function(){
		window.location.href = "/wetnet/manager/users";
	}
		
	$scope.removeCancelled = function () {
		$scope.confirmedAction = false;
	};
	
	$scope.removeConfirm = function () {
		$scope.confirmedAction = true;
	};
	
	$scope.remove = function () {
		Users.remove({id: $scope.user.idusers}, 
				function (data) {
					if(data.status == 'success'){
						$scope.alert = new Object();
		                $scope.alert.success = data.message;
		                $scope.user = new Object();
		                $scope.usersHasDistricts = new Object();
					}
					else{
						$scope.alert = new Object();
			            $scope.alert.danger = data.message;
					}
					$scope.confirmedAction = false;
		            }
				);			
	};
	
}]);
                              
wetnetManagerControllers.controller("ManagerDistrictsController", ['$scope', '$http', '$location', 'DistrictsCRUD', function($scope, $http, $location, DistrictsCRUD) {
    
    DistrictsCRUD.get(function(data){
    	$scope.districts = data.data.districts;
    });
    
    //***RC 25/11/2015***
	$scope.exportCSV = function () {
	    	$http({method: 'POST', url: '/wetnet/rest/districts/csv', data: $scope.districts}).
			  success(function(data, status, headers, config) {
			     var element = angular.element('#exportCSV');
			     element.attr({
			         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
			         target: '_blank',
			         download: 'districts.csv'
			     });
			  }).
			  error(function(data, status, headers, config) {
			  });
	};
	//***END***

}]);

wetnetManagerControllers.controller("ManagerDistrictController", ['$scope', '$http', '$location', '$filter', 'DistrictsCRUD', 'Measures', 'MeasuresHasDistricts','DistrictsBandsHistory','$log','FilesCRUD','Districts', function($scope, $http, $location, $filter, DistrictsCRUD, Measures, MeasuresHasDistricts,DistrictsBandsHistory,$log,FilesCRUD,Districts) {
	
	//***RC 30/11/2015***
	$scope.checkMD5 = "";
	$scope.trimmedTimestamp = "";
	$scope.reset_timestamp = "";
	$scope.alertReset = new Object();
	$scope.confirmedReset = false;
	//***END***
	
	
	//***RC 24/11/2015***
	$scope.$log = $log;
	$scope.districtLayer = new Object();
	$scope.districtsList = new Object();
	//***END***	
	
	$scope.alert = new Object();
    var parnaNameId = "id";
    var regex = new RegExp("[\\?&]" + parnaNameId + "=([^&#]*)"), results = regex.exec(location.search);
    var idValue = results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));

    $scope.currentPageMeasures = 0;

    $scope.itemsPerPageMeasures = 15;
    $scope.selectedMeasuresSign = 0;

    $scope.measures = Measures.getData(function(){
        $scope.totalItemsMeasures = $scope.measures.length;
    });
    
    if(idValue != ""){
        DistrictsCRUD.getById({id:idValue}, function(data){
        	$scope.district = data.data.district;
        	$scope.district.old_ev_high_band = $scope.district.ev_high_band;
    		$scope.district.old_ev_low_band = $scope.district.ev_low_band;
    		$scope.createMap();
        	MeasuresHasDistricts.getByDistrictsId({districts_id: $scope.district.idDistricts}, function(data){
        		$scope.districtMeasures = data.data.measures;
        	});
        	
        	//***RC 04/12/2015***
        	$scope.resetURL = data.data.resetURL;
        	$scope.resetURLparam1 = data.data.resetURLparam1;
        	$scope.resetURLparam2 = data.data.resetURLparam2;
        	$scope.resetURLparam3 = data.data.resetURLparam3;
        	//***END***
        	
        	//***RC 18/11/2015***
        	FilesCRUD.getFilesById({id:idValue, resourceType:'district'}, function(data){
            	$scope.districtFiles = data.data.districtFiles;});
            //***END***
        });    	
    }
    else{
    	$http({method: 'GET', url: '/wetnet/rest/districts/emptyDistrict'}).
		  success(function(data, status, headers, config) {
			  $scope.district = data.data.district;
			  $scope.district.old_ev_high_band = $scope.district.ev_high_band;
			  $scope.district.old_ev_low_band = $scope.district.ev_low_band;
			  $scope.createMap();
		  });
    }
    
	$scope.save = function () {
		if($scope.districtForm.$valid){
					
			$scope.radioItem = 'none';
			$scope.toggleControl();
			
			if ($scope.vectors.features && $scope.vectors.features.length > 0){
				var attributes = new Object();
				attributes.id = $scope.district.idDistricts;
				attributes.name = $scope.district.name;
				attributes.type = 'district';
				for (var i=0; i < $scope.vectors.features.length; i++){
					$scope.vectors.features[i].attributes = attributes;
				}
				$scope.district.map = $scope.geoJSONParser.write($scope.vectors.features, false);
			}
            
			DistrictsCRUD.save($scope.district, 
			function (data) {
				if(data.status == 'success'){
					$scope.alert = new Object();
	                $scope.alert.success = data.message;
	                $scope.district = data.data.district;
				}
				else{
					$scope.alert = new Object();
		            $scope.alert.danger = data.message;;	
				}
	            }
			);	
		}
		
	};
	
	//***RC 19/11/2015***
	function b64toBlob(b64Data, contentType, sliceSize) {
		
		contentType = contentType || '';
	    sliceSize = sliceSize || 512;
	    var byteCharacters = atob(b64Data);
	    var byteArrays = [];

	    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
	    	var slice = byteCharacters.slice(offset, offset + sliceSize);
	        var byteNumbers = new Array(slice.length);
	        
	        for (var i = 0; i < slice.length; i++) {
	        	byteNumbers[i] = slice.charCodeAt(i);
	        }
	        var byteArray = new Uint8Array(byteNumbers);
	        byteArrays.push(byteArray);
	    }
	    var blob = new Blob(byteArrays, {type: contentType});
	    return blob;
	};
	
//	$scope.downloadFile = function (file) {		
////		var bytes = [];
////		bytes = file.file;
////		var fileData = b64toBlob(bytes);//, 'application/pdf'
////		var objectUrl = URL.createObjectURL(fileData);
////        window.open(objectUrl);
//		
//	}
	//***END***

	$scope.cancel = function(){
		window.location.href = "/wetnet/manager/districts";
	}
	
    $scope.openDatePickerev_last_good_sample_day = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedDatePickerev_last_good_sample_day = true;
    };
    
    $scope.alertMeasures = new Object();
    $scope.addMeasures = function(m){
    	MeasuresHasDistricts.add({measures_id: m.idMeasures, districts_id: $scope.district.idDistricts, sign: $scope.selectedMeasuresSign, connections_id_odbcdsn: m.connections_id_odbcdsn}, function(data){
    		$scope.alertMeasures = new Object();
    		if(data.status == 'success'){
    			$scope.alertMeasures.success = data.message;
    		}
    		else if(data.status == 'fail'){
    			$scope.alertMeasures.warning = data.message;
    		}
        	MeasuresHasDistricts.getByDistrictsId({districts_id: $scope.district.idDistricts}, function(data){
        		$scope.districtMeasures = data.data.measures;
        	});
    	});	
    }
    
    $scope.removeMeasures = function(measuresHasDistricts){
    	MeasuresHasDistricts.remove({measures_id: measuresHasDistricts.measures_id_measures, districts_id: measuresHasDistricts.districts_id_districts}, function(data){
    		$scope.alertMeasures = new Object();
    		if(data.status == 'success'){
    			$scope.alertMeasures.success = data.message;
    		}
        	MeasuresHasDistricts.getByDistrictsId({districts_id: $scope.district.idDistricts}, function(data){
        		$scope.districtMeasures = data.data.measures;
        	});
    	});	
    }
    
	$scope.removeCancelled = function () {
		$scope.confirmedAction = false;
	};
	
	$scope.removeConfirm = function () {
		$scope.confirmedAction = true;
	};
	
	$scope.remove = function () {
		DistrictsCRUD.remove({id: $scope.district.idDistricts}, 
				function (data) {
					if(data.status == 'success'){
						$scope.alert = new Object();
		                $scope.alert.success = data.message;
		                $scope.district = new Object();
		                $scope.districtMeasures = new Object();
					}
					else{
						$scope.alert = new Object();
			            $scope.alert.danger = data.message;
					}
					$scope.confirmedAction = false;
		            }
				);			
	};

	$scope.openedDatePicker_update_timestamp = false;
    $scope.openDatePicker_update_timestamp = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedDatePicker_update_timestamp = true;
    };
    
    //***RC 27/11/2015***
    $scope.openedDatePicker_reset_timestamp = false;
    $scope.openDatePicker_reset_timestamp = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedDatePicker_reset_timestamp = true;
    };
    
    $scope.showResetDialog = function () {
    	document.getElementById('reset_timestamp').style.border = '';
    	var datePicker = document.getElementById('reset_timestamp').value;
		if(datePicker != ''){
			$scope.confirmedReset = true;
		}else{
			document.getElementById('reset_timestamp').style.border = '1px solid red';
		}
		
	};
	
	$scope.resetCancelled = function () {
		$scope.confirmedReset = false;
	};
    
    $scope.calcMD5 = function () {
    	
    	var timestamp = $scope.reset_timestamp;

    	var truncatedTimestamp = $filter('date')(timestamp, 'yyyy-MM-dd');
    	
    	$scope.trimmedTimestamp = truncatedTimestamp;
		
		var hash = CryptoJS.MD5(truncatedTimestamp).toString();
		$scope.checkMD5 = hash;
	}
    
	$scope.resetDistrict = function () {
		$scope.confirmedReset = false;
		$scope.alert = new Object();
		document.getElementById('reset_timestamp').style.border = '';
		var datePicker = document.getElementById('reset_timestamp').value;
		if(datePicker != ''){
				var url = document.getElementById('ninjaURL').value;
				
				$http({method: 'GET', url: '/wetnet/rest/districts/ResetDistrictData', params: {url: url}})
				.success(function(data) {
					
					var n = data.indexOf("<Return>1</Return>");
					if(n > -1){
		                $scope.alertReset.success = " ";
					}else{
						n = data.indexOf("<Return>0</Return>");
						if(n > -1){
							 $scope.alertReset.warning = " ";
						}
					}
				});
		}else{
			document.getElementById('reset_timestamp').style.border = '1px solid red';
		}
	};
    //***END***
    
    /**
     * calcolo di night flow customer use:
	 * inhabitants * unitary_phisiological_nigth_demand / 3600
     */
	$scope.compute_household_night_use = function(){
		 var household_night_use = $scope.district.inhabitants * $scope.district.unitary_phisiological_nigth_demand / 3600;
		 //arrotondo alla seconda cifra decimale
		 $scope.district.household_night_use = Number((household_night_use).toFixed(2));
	}
	
	/**
	 * copia i valori statistici/calcola dal sistema. su eventi: high band, low band  
	 */
	$scope.use_statistic_band_values = function(){
		$scope.district.ev_high_band = $scope.district.ev_statistic_high_band;
		$scope.district.ev_low_band = $scope.district.ev_statistic_low_band;
	}

	/**
	 * MAP SECTION BEGINS HERE
	 */	
	$scope.map = new Object();
	$scope.vectors = new Object();
	$scope.controls = new Object();
	$scope.geoJSONParser = new OpenLayers.Format.GeoJSON();
	$scope.latLongProjection = new OpenLayers.Projection("EPSG:4326");
	$scope.radioItem = 'none';
	$scope.sides = 3;
	$scope.irregular = false;
	$scope.createVertices = true;
	$scope.rotate = false;
	$scope.resize = false;
	$scope.keepAspectRatio = true;
	$scope.drag = false;
	
	// crea la mappa
	$scope.createMap = function(){
    	$scope.map = new OpenLayers.Map('map');
        var osm = new OpenLayers.Layer.OSM();
        var googleTerrain = new OpenLayers.Layer.Google("Google Physical", {type: google.maps.MapTypeId.TERRAIN});
		var googleStreets = new OpenLayers.Layer.Google("Google Streets", {numZoomLevels: 20});
		var googleHybrid = new OpenLayers.Layer.Google("Google Hybrid", {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20});
		var googleSatellite = new OpenLayers.Layer.Google("Google Satellite", {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22});
    	
    	OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';

        // allow testing of specific renderers via "?renderer=Canvas", etc
        var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
        renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;

        $scope.vectors = new OpenLayers.Layer.Vector($scope.district.name, {
            renderers: renderer
        });
        
        //***RC 24/11/2015***
    	DistrictsCRUD.get(function(data){
        	$scope.districtsList = data.data.districts;
        	$scope.districtLayer = new OpenLayers.Layer.Vector();
        	for (var i=0, len=$scope.districtsList.length; i < len; i++){
        		var current = new Districts();
        		current = $scope.districtsList[i];
        		//alert("" +i +" di " +len +" " +current.name +" " +$scope.district.name);
        		if (current.map && (current.name !== $scope.district.name)){
        			$scope.districtLayer.addFeatures($scope.geoJSONParser.read(current.map));
        		}
        	}
        	$scope.map.addLayer($scope.districtLayer);
    	});
    	//***END***

        // carica il layer del distretto se gia' esistente
        if ($scope.district.map)
        	$scope.vectors.addFeatures($scope.geoJSONParser.read($scope.district.map));
        
        $scope.map.addLayers([osm, googleTerrain, googleStreets, googleSatellite, googleHybrid, $scope.vectors]);
        $scope.map.addControl(new OpenLayers.Control.LayerSwitcher()); // switcher dei layers con le varie mappe
        var mousePos = new OpenLayers.Control.MousePosition();
        mousePos.displayProjection = $scope.latLongProjection; //mostra lat e long muovendo il mouse sulla mappa
        $scope.map.addControl(mousePos);
        
        $scope.controls = {
            point: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.Point),
            line: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.Path),
            polygon: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.Polygon),
            regular: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.RegularPolygon, {handlerOptions: {sides: 3}}),
            modify: new OpenLayers.Control.ModifyFeature($scope.vectors)
        };
        
        for(var key in $scope.controls) {
        	$scope.map.addControl($scope.controls[key]);
        }
        
        if ($scope.district.map){
	        var features = $scope.vectors.getFeaturesByAttribute('id', $scope.district.idDistricts);
			var polygon = features[0].geometry;
			polygon.calculateBounds();
			$scope.map.zoomToExtent(polygon.bounds, false); //fa lo zoom sull'estensione del distretto se gia' esistente
        } else {    
        	$scope.map.setCenter(new OpenLayers.LonLat(10.6, 43.5).transform($scope.latLongProjection, $scope.map.getProjectionObject()), 8);
        }
        
        //necessario per l'onclick event sulla mappa
//        var click = new OpenLayers.Control.Click();
//        $scope.map.addControl(click);
//        click.activate();
    }
	
	// check boxes
	$scope.update = function() {
        // reset modification mode
        $scope.controls.modify.mode = OpenLayers.Control.ModifyFeature.RESHAPE;
        if($scope.rotate) {
            $scope.controls.modify.mode |= OpenLayers.Control.ModifyFeature.ROTATE;
        }
        if($scope.resize) {
            $scope.controls.modify.mode |= OpenLayers.Control.ModifyFeature.RESIZE;
            if ($scope.keepAspectRatio) {
                $scope.controls.modify.mode &= ~OpenLayers.Control.ModifyFeature.RESHAPE;
            }
        }
        if($scope.drag) {
            $scope.controls.modify.mode |= OpenLayers.Control.ModifyFeature.DRAG;
        }
        if ($scope.rotate || $scope.drag) {
            $scope.controls.modify.mode &= ~OpenLayers.Control.ModifyFeature.RESHAPE;
        }
        $scope.controls.modify.createVertices = $scope.createVertices;
        var sides = $scope.sides;
        sides = Math.max(3, isNaN(sides) ? 0 : sides);
        $scope.controls.regular.handler.sides = sides;
        $scope.controls.regular.handler.irregular = $scope.irregular;
    }

	// radio buttons
	$scope.toggleControl = function() {
        for(var key in $scope.controls) {
            var control = $scope.controls[key];
            if($scope.radioItem && $scope.radioItem == key) {
                control.activate();
            } else {
                control.deactivate();
            }
        }
    }
	
	// pulisci mappa
	$scope.removeLayer = function() {
		$scope.vectors.removeAllFeatures();
		$scope.district.map = null;
    }
	
	//onclick event sulla mappa
	OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {                
        defaultHandlerOptions: {
            'single': true,
            'double': false,
            'pixelTolerance': 0,
            'stopSingle': false,
            'stopDouble': false
        },

        initialize: function(options) {
            this.handlerOptions = OpenLayers.Util.extend({}, this.defaultHandlerOptions);
            OpenLayers.Control.prototype.initialize.apply(this, arguments); 
            this.handler = new OpenLayers.Handler.Click(
                this, {
                    'click': this.trigger
                }, this.handlerOptions
            );
        }, 

        trigger: function(e) {
//        	  var features = $scope.vectors.features;
//            var writer = new OpenLayers.Format.GeoJSON();
//            var str = writer.write(features, true);
//            console.log(str);
        }

    });
	
	
	  /*GC 12/11/2015*/
    $scope.showBandsHistory=false;
    $scope.districtsBandsHistory = [];
    $scope.districtsBandsHistorySize = 0;
    
    
    if(idValue != ""){
    	DistrictsBandsHistory.getById({id:idValue}, function(data){
    		$scope.districtsBandsHistory = data.data.districtsBandsHistory;
    		$scope.districtsBandsHistorySize = $scope.districtsBandsHistory.length;
       });    	
    }
    
    
    $scope.openBandsHistory = function(){
    	
    	if($scope.showBandsHistory){
    		$scope.showBandsHistory=false;
    	}
    	else $scope.showBandsHistory=true;
    };
    
}]);



wetnetManagerControllers.controller("ManagerMeasuresController", ['$scope', '$http', '$location', 'MeasuresCRUD', function($scope, $http, $location, MeasuresCRUD) {
    
	MeasuresCRUD.get(function(data){
    	$scope.measures = data.data.measures;
    });
	
	//***RC 25/11/2015***
	$scope.exportCSV = function () {
	    	$http({method: 'POST', url: '/wetnet/rest/measures/csv', data: $scope.measures}).
			  success(function(data, status, headers, config) {
			     var element = angular.element('#exportCSV');
			     element.attr({
			         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
			         target: '_blank',
			         download: 'measures.csv'
			     });
			  }).
			  error(function(data, status, headers, config) {
			  });
	};
	//***END***

}]);


wetnetManagerControllers.controller("ManagerMeasureReadingController", ['$scope', '$http', '$location', 'MeasuresCRUD', 'Measures', 'MeasuresMeterReadingCRUD','Users', function($scope, $http, $location, MeasuresCRUD, Measures, MeasuresMeterReadingCRUD,Users) {
	$scope.alert = new Object();
    var parnaNameId = "id";
    var regex = new RegExp("[\\?&]" + parnaNameId + "=([^&#]*)"), results = regex.exec(location.search);
    var idValue = results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    
    $scope.reading = new Object();
    $scope.allReading = [];
    $scope.users = [];
    $scope.lastReading = new Object();
    $scope.confirmedAction = false;
    
    if(idValue != ""){
    	
    	$scope.reading.idMeasures=idValue;

    	MeasuresCRUD.getById({id:idValue}, function(data){
        	if(data)$scope.measure = data.data.measure;
        }); 
    	
    	MeasuresMeterReadingCRUD.getAll({id:idValue}, function(data){
    		if(data)
    			{
    			$scope.allReading = data;
    			if($scope.allReading.length>0)$scope.lastReading = $scope.allReading[0];
    			}
        });
    	
    	Users.get( function(data){
    		if(data)
			{
			$scope.users = data;
			}
    });
    }
    
    
    $scope.filterUser = function(idUser)
    {
    	for(var i = 0; i < $scope.users.length; i++)
    		{
    		var u = $scope.users[i];
    		if(u.idusers == idUser)
    			{
    			return u.surname + ' ' + u.name;
    			}
    		}
    };

	$scope.save = function () {
		if($scope.measureForm.$valid){
			
			MeasuresMeterReadingCRUD.save($scope.reading, 
			function (data) {
					if(data.status == 'success'){
						$scope.alert = new Object();
		                $scope.alert.success = data.message;
		                MeasuresMeterReadingCRUD.getAll({id:idValue}, function(data){
		                	if(data)
		        			{
		        			$scope.allReading = data;
		        			if($scope.allReading.length>0)$scope.lastReading = $scope.allReading[0];
		        			$scope.removeCancelled();
		        			}
		                });
					}
					else{
						$scope.alert = new Object();
			            $scope.alert.danger = data.message;	
			            $scope.removeCancelled();
					}
	            }
			);
		}
		
	};

	$scope.cancel = function(){
		window.location.href = "/wetnet/manager/measures";
	}
	    
	$scope.removeCancelled = function () {
		$scope.confirmedAction = false;
	};
	
	$scope.openedDatePicker_update_timestamp = false;
    $scope.openDatePicker_update_timestamp = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedDatePicker_update_timestamp = true;
    };
    
    
   $scope.checkSave = function () {
	   
	   //controllo se ci sono letture e se il valore inserito è inferiore alla precedente
	   if($scope.allReading.length>0)
		   {
		   	if($scope.lastReading.value > $scope.reading.value)
		   		{
		   		$scope.confirmedAction = true;
		   		return;
		   		}
		   }
		$scope.save();
	};
	
}]);


wetnetManagerControllers.controller("ManagerMeasureController", ['$scope', '$http', '$location', 'MeasuresCRUD', 'Measures', 'ConnectionsCRUD', 'FilesCRUD', function($scope, $http, $location, MeasuresCRUD, Measures, ConnectionsCRUD, FilesCRUD) {
	$scope.alert = new Object();
    var parnaNameId = "id";
    var regex = new RegExp("[\\?&]" + parnaNameId + "=([^&#]*)"), results = regex.exec(location.search);
    var idValue = results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));

    
    if(idValue != ""){
    	MeasuresCRUD.getById({id:idValue}, function(data){
        	$scope.measure = data.data.measure;
        	$scope.createMap();
        	
        	/* GC 11/11/2015*/
        	if($scope.measure.multiplication_factor == 0)
        		{
        		$scope.measure.multiplication_factor = 1.0;
        		}
        	
        	//***RC 18/11/2015***
        	FilesCRUD.getFilesById({id:idValue, resourceType:'measure'}, function(data){
            	$scope.measureFiles = data.data.measureFiles;});
            //***END***
        });    	
    }
    else{
    	$http({method: 'GET', url: '/wetnet/rest/measures/emptyMeasure'}).
		  success(function(data, status, headers, config) {
			  $scope.measure = data.data.measure;
			  $scope.createMap();
			  
			  /* GC 11/11/2015*/
	        	if($scope.measure.multiplication_factor == 0)
	        		{
	        		$scope.measure.multiplication_factor = 1.0;
	        		}
		  });
    }

	ConnectionsCRUD.get(function(data){
    	$scope.connections = data.data.connections;
    });
	
	//***RC 23/11/2015***
	function b64toBlob(b64Data, contentType, sliceSize) {
		
		contentType = contentType || '';
	    sliceSize = sliceSize || 512;
	    var byteCharacters = atob(b64Data);
	    var byteArrays = [];

	    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
	    	var slice = byteCharacters.slice(offset, offset + sliceSize);
	        var byteNumbers = new Array(slice.length);
	        
	        for (var i = 0; i < slice.length; i++) {
	        	byteNumbers[i] = slice.charCodeAt(i);
	        }
	        var byteArray = new Uint8Array(byteNumbers);
	        byteArrays.push(byteArray);
	    }
	    var blob = new Blob(byteArrays, {type: contentType});
	    return blob;
	};
	
	$scope.downloadFile = function (file) {

		var bytes = [];
		bytes = file.file;
		var fileData = b64toBlob(bytes);//, 'application/pdf'
		var objectUrl = URL.createObjectURL(fileData);
        window.open(objectUrl);
		
	}
	//***END***
    
	$scope.save = function () {
		console.log($scope.measureForm.$valid);
		if($scope.measureForm.$valid){
			
			$scope.radioItem = 'none';
			$scope.toggleControl();
			
			if ($scope.vectors.features && $scope.vectors.features.length > 0){
				var attributes = new Object();
				attributes.id = $scope.measure.idMeasures;
				attributes.name = $scope.measure.name;
				attributes.type = 'measure';
				for (var i=0; i < $scope.vectors.features.length; i++){
					$scope.vectors.features[i].attributes = attributes;
				}
				$scope.measure.map = $scope.geoJSONParser.write($scope.vectors.features, false);
			}
			
			MeasuresCRUD.save($scope.measure, 
			function (data) {
				if(data.status == 'success'){
					$scope.alert = new Object();
	                $scope.alert.success = data.message;
	                $scope.measure = data.data.measure;
				}
				else{
					$scope.alert = new Object();
		            $scope.alert.danger = data.message;	
				}
	            }
			)	
		}
		
	};

	$scope.cancel = function(){
		window.location.href = "/wetnet/manager/measures";
	}
	    
    
	$scope.removeCancelled = function () {
		$scope.confirmedAction = false;
	};
	
	$scope.removeConfirm = function () {
		$scope.confirmedAction = true;
	};
	
	$scope.remove = function () {
		MeasuresCRUD.remove({id: $scope.measure.idMeasures}, 
				function (data) {
					if(data.status == 'success'){
						$scope.alert = new Object();
		                $scope.alert.success = data.message;
		                $scope.measure = new Object();
					}
					else{
						$scope.alert = new Object();
			            $scope.alert.danger = data.message;
					}
					$scope.confirmedAction = false;
		            }
				);			
	};
	
	$scope.openedDatePicker_update_timestamp = false;
    $scope.openDatePicker_update_timestamp = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedDatePicker_update_timestamp = true;
    };

    /**
	 * MAP SECTION BEGINS HERE
	 */	
	$scope.map = new Object();
	$scope.vectors = new Object();
	$scope.controls = new Object();
	$scope.geoJSONParser = new OpenLayers.Format.GeoJSON();
	$scope.latLongProjection = new OpenLayers.Projection("EPSG:4326");
	$scope.radioItem = 'none';
	$scope.sides = 3;
	$scope.irregular = false;
	$scope.createVertices = true;
	$scope.rotate = false;
	$scope.resize = false;
	$scope.keepAspectRatio = true;
	$scope.drag = false;
	
	// crea la mappa
	$scope.createMap = function(){
    	$scope.map = new OpenLayers.Map('map');
        var osm = new OpenLayers.Layer.OSM();
        var googleTerrain = new OpenLayers.Layer.Google("Google Physical", {type: google.maps.MapTypeId.TERRAIN});
		var googleStreets = new OpenLayers.Layer.Google("Google Streets", {numZoomLevels: 20});
		var googleHybrid = new OpenLayers.Layer.Google("Google Hybrid", {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20});
		var googleSatellite = new OpenLayers.Layer.Google("Google Satellite", {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 22});
    	
    	OpenLayers.Feature.Vector.style['default']['strokeWidth'] = '2';

        // allow testing of specific renderers via "?renderer=Canvas", etc
        var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
        renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;

        $scope.vectors = new OpenLayers.Layer.Vector($scope.measure.name, {
            renderers: renderer
        });

        // carica il layer del distretto se gia' esistente
        if ($scope.measure.map)
        	$scope.vectors.addFeatures($scope.geoJSONParser.read($scope.measure.map));
        
        $scope.map.addLayers([osm, googleTerrain, googleStreets, googleSatellite, googleHybrid, $scope.vectors]);
        $scope.map.addControl(new OpenLayers.Control.LayerSwitcher()); // switcher dei layers con le varie mappe
        var mousePos = new OpenLayers.Control.MousePosition();
        mousePos.displayProjection = $scope.latLongProjection; //mostra lat e long muovendo il mouse sulla mappa
        $scope.map.addControl(mousePos);
        
        $scope.controls = {
            point: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.Point),
            line: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.Path),
            polygon: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.Polygon),
            regular: new OpenLayers.Control.DrawFeature($scope.vectors, OpenLayers.Handler.RegularPolygon, {handlerOptions: {sides: 3}}),
            modify: new OpenLayers.Control.ModifyFeature($scope.vectors)
        };
        
        for(var key in $scope.controls) {
        	$scope.map.addControl($scope.controls[key]);
        }
        
        if ($scope.measure.map){
	        var features = $scope.vectors.getFeaturesByAttribute('id', $scope.measure.idMeasures);
			var polygon = features[0].geometry;
			polygon.calculateBounds();
			$scope.map.zoomToExtent(polygon.bounds, false); //fa lo zoom sull'estensione del distretto se gia' esistente
        } else {    
        	$scope.map.setCenter(new OpenLayers.LonLat(10.6, 43.5).transform($scope.latLongProjection, $scope.map.getProjectionObject()), 8);
        }
    }
	
	// check boxes
	$scope.update = function() {
        // reset modification mode
        $scope.controls.modify.mode = OpenLayers.Control.ModifyFeature.RESHAPE;
        if($scope.rotate) {
            $scope.controls.modify.mode |= OpenLayers.Control.ModifyFeature.ROTATE;
        }
        if($scope.resize) {
            $scope.controls.modify.mode |= OpenLayers.Control.ModifyFeature.RESIZE;
            if ($scope.keepAspectRatio) {
                $scope.controls.modify.mode &= ~OpenLayers.Control.ModifyFeature.RESHAPE;
            }
        }
        if($scope.drag) {
            $scope.controls.modify.mode |= OpenLayers.Control.ModifyFeature.DRAG;
        }
        if ($scope.rotate || $scope.drag) {
            $scope.controls.modify.mode &= ~OpenLayers.Control.ModifyFeature.RESHAPE;
        }
        $scope.controls.modify.createVertices = $scope.createVertices;
        var sides = $scope.sides;
        sides = Math.max(3, isNaN(sides) ? 0 : sides);
        $scope.controls.regular.handler.sides = sides;
        $scope.controls.regular.handler.irregular = $scope.irregular;
    }

	// radio buttons
	$scope.toggleControl = function() {
        for(var key in $scope.controls) {
            var control = $scope.controls[key];
            if($scope.radioItem && $scope.radioItem == key) {
                control.activate();
            } else {
                control.deactivate();
            }
        }
    }
	
	// pulisci mappa
	$scope.removeLayer = function() {
		$scope.vectors.removeAllFeatures();
		$scope.measure.map = null;
    }
    
}]);


wetnetManagerControllers.controller("ManagerConnectionsController", ['$scope', '$http', '$location', 'ConnectionsCRUD', function($scope, $http, $location, ConnectionsCRUD) {
    
	ConnectionsCRUD.get(function(data){
    	$scope.connections = data.data.connections;
    });

}]);

wetnetManagerControllers.controller("ManagerConnectionController", ['$scope', '$http', '$location', 'ConnectionsCRUD', function($scope, $http, $location, ConnectionsCRUD) {
	$scope.alert = new Object();
    var parnaNameId = "id";
    var regex = new RegExp("[\\?&]" + parnaNameId + "=([^&#]*)"), results = regex.exec(location.search);
    var idValue = results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    
    $scope.connection = new Object();
    
    if(idValue != ""){
    	ConnectionsCRUD.getById({id:idValue}, function(data){
        	$scope.connection = data.data.connection;
        });    	
    }
    
	$scope.save = function () {
		console.log($scope.connectionForm.$valid);
		if($scope.connectionForm.$valid){
			
			ConnectionsCRUD.save($scope.connection, 
			function (data) {
				if(data.status == 'success'){
					$scope.alert = new Object();
	                $scope.alert.success = data.message;
	                $scope.connection = data.data.connection;
				}
				else{
					$scope.alert = new Object();
		            $scope.alert.danger = data.message;	
				}
	            }
			)	
		}
	};

	$scope.cancel = function(){
		window.location.href = "/wetnet/manager/connections";
	}
	    
    
	$scope.removeCancelled = function () {
		$scope.confirmedAction = false;
	};
	
	$scope.removeConfirm = function () {
		$scope.confirmedAction = true;
	};
	
	$scope.remove = function () {
		ConnectionsCRUD.remove({id: $scope.connection.id_odbcdsn}, 
				function (data) {
					if(data.status == 'success'){
						$scope.alert = new Object();
		                $scope.alert.success = data.message;
		                $scope.connection = new Object();
					}
					else{
						$scope.alert = new Object();
			            $scope.alert.danger = data.message;
					}
					$scope.confirmedAction = false;
		            }
				);			
	};
	
    
}]);



