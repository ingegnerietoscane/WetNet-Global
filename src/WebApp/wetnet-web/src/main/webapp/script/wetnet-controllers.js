"use strict";

var wetnetControllers = angular.module('wetnetControllers', []);



wetnetControllers.controller("WetnetControllerG1", ['$scope', '$http', 'Districts', function($scope, $http, Districts) {
	
	$scope.bean = new Object();
	$scope.districts = Districts.getData();
	
	$scope.changeURL = function changeURL(idDistricts){
		var oldHref = window.location.href;
		var qs = oldHref.indexOf("?idDistricts=");
		if (qs < 0)
			window.location.href = oldHref + "?idDistricts=" + idDistricts;
        else
        	window.location.href = oldHref.substring(0, qs) + "?idDistricts=" + idDistricts;
	}
	
	$scope.districtSelectedShow = function($item, $model, $label){
		$scope.bean.districtsSelected = $model;
		$scope.changeURL($model.idDistricts);
	}
}]);

wetnetControllers.controller("WetnetControllerG2", ['$scope', '$http', '$filter', 'Districts', 'Measures', 'G2', 'G2CONF', 'G2CONFPARAM', 'TimeSelectorRadio', 'RoundNumber', 'GetUrlParameter','$log',
                                                    				function($scope, $http, $filter, Districts, Measures, G2, G2CONF, G2CONFPARAM, TimeSelectorRadio, RoundNumber, GetUrlParameter,$log) {
	
	
	//***RC 03/12/2015***
	$scope.configList = new Object();
	$scope.paramList = new Object();
	G2CONF.getData(function (data) {
		$scope.configList = data;
	});
	G2CONFPARAM.getData(function (data) {
		$scope.paramList = data;
	});
	//***END***
	
	//***RC 30/11/2015***
	$scope.confDialog = false;
	//***END***
	
	/*GC - settaggio $log per debug*/
	$scope.$log = $log;
	
	$scope.config = new Object();
	$scope.config.chartWidth = 2000;
	$scope.measures = Measures.getData(); //carico le misure utlizzate per la ricerca
	$scope.columns = [];
	
	//inizializzazioni
	$scope.g2Data = new Object();
	$scope.g2Data.districtsSelected = new Array();
	$scope.g2Data.measuresSelected = new Array();
	$scope.g2Data.showDBands = false;
	
	//***RC 21/10/2015***
	
	$scope.flagEnergyLosses = false;
	
	$scope.showEnergy = false;
	$scope.showLosses = false;
	//***END***
	
	$scope.g2Data.showMBands = false;
	$scope.isCollapsed = false;
	
	//inizializzo la data
	$scope.date = GetUrlParameter('day');
	if ($scope.date){ //data nella query string da lista eventi
		$scope.duration = parseInt(GetUrlParameter('duration'));
		$scope.year = parseInt($scope.date.substring(0, 4));
		$scope.month = parseInt($scope.date.substring(5, 7)) - 1;
		$scope.day = parseInt($scope.date.substring(8, 10));
		$scope.g2Data.startDate = new Date($scope.year, $scope.month, $scope.day - $scope.duration - 2, 0, 0, 0);
		$scope.g2Data.endDate = new Date($scope.year, $scope.month, $scope.day, 0, 0, 0);
	} else { //comportamento di default
		$scope.g2Data.startDate = new Date();
		$scope.g2Data.endDate = new Date();
		$scope.g2Data.startDate.setDate($scope.g2Data.endDate.getDate() - 30);
	}
	
	//carico i distretti utlizzati per la ricerca
	$scope.id = GetUrlParameter('idDistricts');
	$scope.districts = Districts.getData(null, function (data) {
									//id nella query string da lista eventi
									if ($scope.id){
										for (var i = 0; i < data.length; i++){
											var d = data[i];
											if($scope.id == d.idDistricts){
												$scope.districtSelectedAdd(d, d, '');
												break;
											}
										}
									}
									return data;
								});
	
	//Inizio gestione Selezione Distretti 
	$scope.districtSelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
		var alreadyAdded = false;
		for(var i=0; i< $scope.g2Data.districtsSelected.length; i++){
			var d = $scope.g2Data.districtsSelected[i];
			if(d.name == $item.name){
				alreadyAdded = true;
			}
		}

		if(!alreadyAdded){
			var size = $scope.g2Data.districtsSelected.length;
			
			/* GC - 29/10/2015 */
			$model.measures = Measures.getDataByDistrictId({districtId: $model.idDistricts, withSign:true}, $scope, function () {
																										//id nella query string da lista eventi
																										if ($scope.id){
																											$scope.loadData();
																											$scope.dSelected = '';
																										}
																									});
			$scope.g2Data.districtsSelected[size] = $model;
		}
		$scope.dSelected = new Object();
	}
		
	$scope.districtSelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i=0; i< $scope.g2Data.districtsSelected.length; i++){
			var d = $scope.g2Data.districtsSelected[i];
			if(d.name !== $item.name){
				tempSelected.push(d);
			}
		}
		$scope.g2Data.districtsSelected = tempSelected;
	}
	//Fine gestione Selezione Distretti
	
	//***RC 03/11/2015***
	//carico la misura selezionata dalla dashboard
	$scope.idM = GetUrlParameter('idMeasures');
	$scope.measures = Measures.getData(null, function (data) {
									//id nella query string da lista eventi
									if ($scope.idM){
										for (var i = 0; i < data.length; i++){
											var d = data[i];
											if($scope.idM == d.idMeasures){
												$scope.measureSelectedAdd(d, d, '');
												break;
											}
										}
									}
									return data;
								});
	//***END***
	
	//Inizio gestione Selezione Measures 
	$scope.measureSelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
		var alreadyAdded = false;
		for(var i=0; i< $scope.g2Data.measuresSelected.length; i++){
			var d = $scope.g2Data.measuresSelected[i];
			if(d.name == $item.name){
				alreadyAdded = true;
			}
		}
		if(!alreadyAdded){
			var size = $scope.g2Data.measuresSelected.length; 
			//***RC 03/11/2015***
			if ($scope.idM){
				$scope.loadData();
			}
			//***END***
			$scope.g2Data.measuresSelected[size] = $model;			
		}
	}
		
	$scope.measureSelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i=0; i< $scope.g2Data.measuresSelected.length; i++){
			var d = $scope.g2Data.measuresSelected[i];
			if(d.name !== $item.name){
				tempSelected.push(d);
			}
		}
		$scope.g2Data.measuresSelected = tempSelected;
	}
	//Fine gestione Selezione Measures
	
	//***RC 01/12/2015***
	$scope.showDialog = function () {
		//if($scope.g2Data.districtsSelected.length > 0 || $scope.g2Data.measuresSelected.length > 0){
			$scope.confDialog = true;
		//}
	};
	
	$scope.dialogCancelled = function () {
		$scope.confDialog = false;
	};
	
	$scope.saveConf = function () {
		if($scope.g2Data.districtsSelected.length > 0 || $scope.g2Data.measuresSelected.length > 0){
			
			var desc = document.getElementById("confDesc").value;
			$scope.g2Data.descriptionConfiguration = desc;
			
			G2CONF.saveG2Configuration($scope.g2Data, function (data) {
				
				G2CONF.getData(function (data) {
					$scope.configList = data;
				});
				G2CONFPARAM.getData(function (data) {
					$scope.paramList = data;
				});
			});	
			
		}
	};
	
	$scope.configSelected = function (config) {
		
		var configParamsList = [];
		var districtsParamList = [];
		var measuresParamList = [];
		
		//$scope.configList = new Object ();
		//$scope.paramList = new Object ();
		for(var i = 0; i < $scope.paramList.length; i++) {
			//if($scope.paramList[i].users_cfgs_parent_save_date == config.save_date){
			if(($scope.paramList[i].users_cfgs_parent_save_date == config.save_date) && ($scope.paramList[i].users_cfgs_parent_submenu_function == 2)){
				configParamsList.push($scope.paramList[i]);
			}
		}
		
		for(var i = 0; i < configParamsList.length; i++) {
			if(configParamsList[i].type == 1){	//DISTRETTI
				for(var x = 0; x < $scope.districts.length; x++) {
					if(configParamsList[i].objectid == $scope.districts[x].idDistricts){
						
						
						/* GC - 12/11/2015 */
						var measTemp = Measures.getDataByDistrictId({districtId: $scope.districts[x].idDistricts, withSign:true}, $scope, function () {
																													//id nella query string da lista eventi
																													if ($scope.id){
																														$scope.loadData();
																														$scope.dSelected = '';
																													}
																												});
						$scope.districts[x].measures = measTemp;
						/******/
						
						districtsParamList.push($scope.districts[x]);
						
					}
				}
			}else{	//MISURE
				for(var x = 0; x < $scope.measures.length; x++) {
					if(configParamsList[i].objectid == $scope.measures[x].idMeasures){
						measuresParamList.push($scope.measures[x]);
					}
				}
			}
		}
		$scope.g2Data.districtsSelected = null;
		$scope.g2Data.measuresSelected = null;
		$scope.g2Data.districtsSelected = districtsParamList;
		$scope.g2Data.measuresSelected = measuresParamList;
		//$scope.loadData();
	};
	
	$scope.configDeleted = function (config) {

		var _date = $filter('date')(config.save_date, 'yyyy-MM-dd HH:mm:ss');
		
		G2CONF.remove({parent: _date}, function (data) {
			//alert(data);
			G2CONF.getData(function (data) {
				$scope.configList = data;
			});
			G2CONFPARAM.getData(function (data) {
				$scope.paramList = data;
			});
		});	
	};
	
	//***END***
	
    // callback for ng-click 'loadData':
	$scope.loadData = function () {
		if($scope.g2Data.startDate != undefined && $scope.g2Data.endDate != undefined && $scope.g2Data.districtsSelected != undefined){
			G2.getData($scope.g2Data, function (data) {
				
				//***RC 21/10/2015***
				
				$scope.flagEnergyLosses = true;
				
				//***END***
				
				/*GC 02/11/2015*/
				$scope.g2Data.medie = data.medie;
				
				$scope.g2Data.columns = data.columns;
				$scope.g2Data.districtsSelected = data.districtsSelected;
				$scope.g2Data.measuresSelected = data.measuresSelected;
				var rows = angular.fromJson(data.columns);
				var maxData = data.columns[0][data.columns[0].length - 1 ]
				
				//GC -- modifica per nascondere energy e losses
				var rowsTemp = new Array();
				rowsTemp[0] = rows[0];
				
				var j = 1;
				
					for(var i = 1; i < rows.length; i++)
					{
					var temp = rows[i][0];
					
						if(temp.indexOf("energy [Kw]") > -1)
						{
							if($scope.showEnergy)
								{
							     rowsTemp[j] = rows[i];
							     j++;
							    }
							  continue;
						}
					
						if(temp.indexOf("losses [l/s]") > -1)
						{
								if($scope.showLosses)
								{
								rowsTemp[j] = rows[i];
								j++;
								}
								continue;
						}
					    
						rowsTemp[j] = rows[i];
						j++;
					}
				rows = rowsTemp;
			
				$scope.columns = rows;
				
				var chart = c3.generate({
				    data:{
				    	x: 'x',
				        xFormat: '%Y-%m-%d %I:%M:%S', // 'xFormat' can be used as custom format of 'x'
				        columns: rows
				    },
//				    zoom: {
//				        enabled: true,
//				        rescale: true
//				    },
//				    subchart: {
//				    	  show: true
//				    	},
				    axis: {
				        x: {
				            type: 'timeseries',
				            tick: {
				            	fit: false,
				            	rotate: 15,
				                format: '%a %Y-%m-%d %H:%M'
				            },
				            height: 70
				        },
				        y: {
				        	tick: {
				        		format: function (x) { return RoundNumber(x); }
				            }
				        }
				    },
				    point: { show: false },
				    legend: {
				        show: true
				    },
				    tooltip: {
				    	/*format: {
				    		value: function (x) { return RoundNumber(x); }
				    	}*/
				    	/* GC 02/11/2015 */
				    	contents: function tooltip_contents(d, defaultTitleFormat, defaultValueFormat, color) {
						    var $$ = this, config = $$.config, CLASS = $$.CLASS,
					        titleFormat = config.tooltip_format_title || defaultTitleFormat,
					        nameFormat = config.tooltip_format_name || function (name) { return name; },
					        valueFormat = config.tooltip_format_value || defaultValueFormat,
					        text, i, title, value, name, bgcolor;
					        
					       var dateFormat = d3.time.format('%a %Y-%m-%d %H:%M');
					       
					    for (i = 0; i < d.length; i++) {
					    	
					        
					        if (i==0) {
					            title = dateFormat(d[i].x);
					            text = "<table class='" + CLASS.tooltip + "'>" + (title || title === 0 ? "<tr><th colspan='3'>" + title + "</th></tr>" : "");
					            text += "<tr>";
						        text += "<td></td>";
						        text += "<td>value</td>";
						        text += "<td>media</td>";
						        text += "</tr>";
					        }
					        
					        name = nameFormat(d[i].name);
					        bgcolor = $$.levelColor ? $$.levelColor(d[i].value) : color(d[i].id);
					        
					        var media = "0";
					        
					        for(var z = 0; z < $scope.g2Data.medie.length; z++)
				        	{
				        	var s = $scope.g2Data.medie[z];
				        	
					        	for(var key in s)
					            {
					        		if(key == d[i].name)
					        			{
					        			media = s[key];
					        			break;
					        			}
					            }
				        	}
					        
					        text += "<tr class='" + CLASS.tooltipName + "-" + d[i].id + "'>";
					        text += "<td class='name'><span style='background-color:" + bgcolor + "'></span>" + d[i].name + "</td>";
					        text += "<td>" + RoundNumber(d[i].value) + "</td>";
					        text += "<td>" + RoundNumber(media) + "</td>";
					        text += "</tr>";
					      
					    }
					    return text + "</table>";   
					}
				    },
				    grid: {
				        x: {
				            show: true
				        }
				    },
				    size: { 
				    	height: 520
			    	},
					padding: {
						left: 50,
						right: 100
					}
				});
		    });	
		}
	}
	
	// callback for ng-click 'exportCSV':
	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/g2/csv', data: $scope.g2Data}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart.csv'
		     });
		  }).
		  error(function(data, status, headers, config) {
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
    	if ($scope.radioModel === '1d'){
    		$scope.g2Data.endDate = new Date();
    	}
		$scope.g2Data.startDate = TimeSelectorRadio($scope.radioModel, $scope.g2Data.endDate);
	}
    
	
	/*
	 * GC - 29/10/2015
	 */
    $scope.checkSign = function(elem){
	
	if(elem == '0') return '+';
	else if(elem == '1') return '-';
	else return '*';
	
};
    
}]);


wetnetControllers.controller("WetnetControllerG3", ['$scope', '$http', 'DataDistricts', 'Districts', 'TimeSelectorRadio', 'RoundNumber','RoundNumberToFifth',
                                                      				function($scope,$http, DataDistricts, Districts, TimeSelectorRadio, RoundNumber, RoundNumberToFifth) {
	$scope.config = new Object();
	$scope.config.chartWidth = 2000;
	$scope.columns = [];
	
	$scope.bean = new Object();
	$scope.bean.startDate = new Date();
	$scope.bean.endDate = new Date();
	$scope.bean.startDate.setDate($scope.bean.endDate.getDate() - 30);
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
	
	$scope.formBean = new Object();
	
	$scope.districts = Districts.getData();
	
	
	
	
	$scope.temp = new Object();
	
    // callback for ng-click 'loadData':
    $scope.loadDataG3 = function () {
		$http.post("/wetnet/rest/d3/g3_1/json", $scope.bean).then(function(response){
			$scope.minNightStartTime = response.data.minNightStartTime;
			$scope.minNightStopTime = response.data.minNightStopTime;
			
			/*GC 03/11/2015*/
			$scope.medie_g3_1 = response.data.medie;
			
			
			$scope.temp.g3_1 = response.data.data;
			var rows = angular.fromJson(response.data.data);
			$scope.columns = rows;
			var chart = c3.generate({
				bindto: '#chart',
			    data:{
			    	x: 'x',
			        xFormat: '%Y-%m-%d', // 'xFormat' can be used as custom format of 'x'
			        columns: rows,
			        axes: {
			        	MinNight: 'y',
			        	MinNightPressure: 'y2'
			        }
			    },
			    axis: {
			        x: {
			            type: 'timeseries',
			            tick: {
			            	fit: false,
			            	rotate: 15,
			                format: '%a %Y-%m-%d'
			            },
			            height: 70
			        },
			        y: {
			            label: {
			                text: 'Min Night [l/s]',
			                position: 'outer-top'
			            },
  				        tick: {
  				    		format: function (x) { return RoundNumber(x); }
  				        }
			        },
			        y2: {
			            show: true,
			         	label: {
			         		text: 'Min Night Pressure [bar]',
			                position: 'outer-top' 
			         	},
				        tick: {
				    		format: function (x) { return RoundNumber(x); }
				        }
			        }
			    },
			    tooltip: {
			    	/*format: {
			    		value: function (x) { return RoundNumber(x); }
			    	}*/
			    	/*GC 03/11/2015*/
					contents: function tooltip_contents(d, defaultTitleFormat, defaultValueFormat, color) {
					    var $$ = this, config = $$.config, CLASS = $$.CLASS,
				        titleFormat = config.tooltip_format_title || defaultTitleFormat,
				        nameFormat = config.tooltip_format_name || function (name) { return name; },
				        valueFormat = config.tooltip_format_value || defaultValueFormat,
				        text, i, title, value, name, bgcolor;
				        
				       var dateFormat = d3.time.format('%a %Y-%m-%d');
				       
				   for (i = 0; i < d.length; i++) {
				      
				        if (i==0) {
				            title = dateFormat(d[i].x);
				            text = "<table class='" + CLASS.tooltip + "'>" + (title || title === 0 ? "<tr><th colspan='3'>" + title + "</th></tr>" : "");
				            text += "<tr>";
					        text += "<td></td>";
					        text += "<td>value</td>";
					        text += "<td>media</td>";
					        text += "</tr>";
				        }
				        
				        name = nameFormat(d[i].name);
				        bgcolor = $$.levelColor ? $$.levelColor(d[i].value) : color(d[i].id);
				        
				        var media = "0";
				        
				        for(var z = 0; z < $scope.medie_g3_1.length; z++)
			        	{
			        	var s = $scope.medie_g3_1[z];
			        	
				        	for(var key in s)
				            {
				        		if(key == d[i].name)
				        			{
				        			media = s[key];
				        			break;
				        			}
				            }
			        	}
				        
				        text += "<tr class='" + CLASS.tooltipName + "-" + d[i].id + "'>";
				        text += "<td class='name'><span style='background-color:" + bgcolor + "'></span>" + d[i].name + "</td>";
				        text += "<td>" + RoundNumber(d[i].value) + "</td>";
				        text += "<td>" + RoundNumber(media) + "</td>";
				        text += "</tr>";
				      
				    }
				    return text + "</table>";   
				}
			    },
			    grid: {
			        x: {
			            show: true
			        }
			    },
			    size: { 
			    	height: 520
		    	},
				padding: {
					left: 50,
					right: 100
				}
			});	     
		});
		
		$http.post("/wetnet/rest/d3/g3_2/json", $scope.bean).then(function(response){
			
			/*GC 03/11/2015*/
			//$scope.medie_g3_2 = response.data.medie;
			
			$scope.temp.g3_2 = response.data.data;
			
			var rows = angular.fromJson(response.data.data);
			$scope.columns = rows;
			var chart = c3.generate({
				bindto: '#chart2',
			    data:{
			    	x: 'x',
			        xFormat: '%Y-%m-%d', // 'xFormat' can be used as custom format of 'x'
			        columns: rows,
			        axes: {
			        	MinNight: 'y',
			        	MnfPressure: 'y2'
			        }
			    },
			    axis: {
			        x: {
			            type: 'timeseries',
			            tick: {
			            	fit: false,
			            	rotate: 15,
			                format: '%a %Y-%m-%d'
			            },
			            height: 70
			        },
			        y: {
			            label: {
			                text: 'Min Night [l/s]',
			                position: 'outer-top'
			            },
  				        tick: {
  				    		format: function (x) { return RoundNumber(x); }
  				        }
			        },
			        y2: {
			            show: true,
			         	label: {
			         		text: 'Mnf Pressure [bar]',
			                position: 'outer-top' 
			         	},
				        tick: {
				    		format: function (x) { return RoundNumber(x); }
				        }
			        }
			    },
			    tooltip: {
			    	format: {
			    		value: function (x) { return RoundNumber(x); }
			    	}
			    	/*GC 03/11/2015*/
					/*contents: function tooltip_contents(d, defaultTitleFormat, defaultValueFormat, color) {
					    var $$ = this, config = $$.config, CLASS = $$.CLASS,
				        titleFormat = config.tooltip_format_title || defaultTitleFormat,
				        nameFormat = config.tooltip_format_name || function (name) { return name; },
				        valueFormat = config.tooltip_format_value || defaultValueFormat,
				        text, i, title, value, name, bgcolor;
				        
				       var dateFormat = d3.time.format('%a %Y-%m-%d');
				       
				   for (i = 0; i < d.length; i++) {
				      
				        if (i==0) {
				            title = dateFormat(d[i].x);
				            text = "<table class='" + CLASS.tooltip + "'>" + (title || title === 0 ? "<tr><th colspan='3'>" + title + "</th></tr>" : "");
				            text += "<tr>";
					        text += "<td></td>";
					        text += "<td>value</td>";
					        text += "<td>media</td>";
					        text += "</tr>";
				        }
				        
				        name = nameFormat(d[i].name);
				        bgcolor = $$.levelColor ? $$.levelColor(d[i].value) : color(d[i].id);
				        
				        var media = "0";
				        
				        for(var z = 0; z < $scope.medie_g3_2.length; z++)
			        	{
			        	var s = $scope.medie_g3_2[z];
			        	
				        	for(var key in s)
				            {
				        		if(key == d[i].name)
				        			{
				        			media = s[key];
				        			break;
				        			}
				            }
			        	}
				        
				        text += "<tr class='" + CLASS.tooltipName + "-" + d[i].id + "'>";
				        text += "<td class='name'><span style='background-color:" + bgcolor + "'></span>" + d[i].name + "</td>";
				        text += "<td>" + RoundNumber(d[i].value) + "</td>";
				        text += "<td>" + RoundNumber(media) + "</td>";
				        text += "</tr>";
				      
				    }
				    return text + "</table>";   
				}*/
			    },
			    grid: {
			        x: {
			            show: true
			        }
			    },
			    size: { 
			    	height: 520
		    	},
				padding: {
					left: 50,
					right: 100
				}
			});	     
		});
		
		/* GC 06/11/2015*/
		$http.post("/wetnet/rest/d3/g3_3/json", $scope.bean).then(function(response){
			$scope.minNightStartTime = response.data.minNightStartTime;
			$scope.minNightStopTime = response.data.minNightStopTime;
			
			
			/* GC 06/11/2015 */
			$scope.indiceCorrelazione = response.data.indCorrelazione;
	     	$scope.coeffDeterminazione = response.data.coeffDeterminazione;
			
			
			
			$scope.temp.g3_3 = response.data.data;
			var rows = angular.fromJson(response.data.data);
			$scope.columns = rows;
			var chart = c3.generate({
				bindto: '#chart3',
			    data:{
			    	xs: { MinNightPressure: 'MinNight',
			    		  RettaMinimiQuadrati: 'MinNight'},
			        columns: rows,
			       // type:'scatter'
			        types: {
			        	MinNightPressure: 'scatter',
			        	RettaMinimiQuadrati: 'line'
			        }
			    },
			    axis: {
			    	x: {
			            label: 'MinNight',
			            tick: {
			                fit: false
			            }
			        },
			        y: {
			            label: 'MinNightPressure'
			        }
			    },
			    tooltip: {
			    	/*format: {
			    		value: function (x) { return RoundNumber(x); }
			    	}*/
			    	/*GC 03/11/2015*/
					contents: function tooltip_contents(d, defaultTitleFormat, defaultValueFormat, color) {
					    var $$ = this, config = $$.config, CLASS = $$.CLASS,
				        titleFormat = config.tooltip_format_title || defaultTitleFormat,
				        nameFormat = config.tooltip_format_name || function (name) { return name; },
				        valueFormat = config.tooltip_format_value || defaultValueFormat,
				        text, i, title, value, name, bgcolor;
				       
				   for (i = 0; i < d.length; i++) {
				      
				        if (i==0) {
				            title = 'MinNight: ' + RoundNumberToFifth(d[i].x);
				            text = "<table class='" + CLASS.tooltip + "'>" + (title || title === 0 ? "<tr><th colspan='3'>" + title + "</th></tr>" : "");
				      
				        }
				        
				        name = nameFormat(d[i].name);
				        bgcolor = $$.levelColor ? $$.levelColor(d[i].value) : color(d[i].id);
				        
				   
				        text += "<tr class='" + CLASS.tooltipName + "-" + d[i].id + "'>";
				        text += "<td class='name'><span style='background-color:" + bgcolor + "'></span>" + d[i].name + "</td>";
				        text += "<td>" + RoundNumberToFifth(d[i].value) + "</td>";
				        text += "</tr>";
				      
				    }
				    return text + "</table>";   
				}
			    },
			    grid: {
			        x: {
			            show: true
			        }
			    },
			    size: { 
			    	height: 520
		    	},
				padding: {
					left: 50,
					right: 100
				}
			});	     
		});
		
		
		
		
	}

    $scope.districtSelectedShow= function($item, $model, $label){
    	$scope.bean.districtsSelected = $model;
	}
    
    // callback for ng-click 'exportCSV1':
	$scope.exportCSV1 = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/g3/csv', data: $scope.temp.g3_1}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV1');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart3_1.csv'
		     });
		  }).
		  error(function(data, status, headers, config) {
		    // if there's an error you should see it here
		  });
	}
	
	// callback for ng-click 'exportCSV2':
	$scope.exportCSV2 = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/g3/csv', data: $scope.temp.g3_2}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV2');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart3_2.csv'
		     });
		  }).
		  error(function(data, status, headers, config) {
		    // if there's an error you should see it here
		  });
	}
	
	$scope.exportCSVs = function () {
    	$scope.exportCSV1();
    	$scope.exportCSV2();
	}
	
	$scope.updateDate = function () {
		if ($scope.radioModel === '1d'){
    		$scope.bean.endDate = new Date();
    	}
		$scope.bean.startDate = TimeSelectorRadio($scope.radioModel, $scope.bean.endDate);
	}
	
}]);


wetnetControllers.controller("WetnetControllerG4", ['$scope', '$http', 'Districts', 'Zone', 'Municipality', 'G4BarChart', 'TimeSelectorRadio', 'RoundNumber', 'RoundNumberToThird',
                                      				function($scope, $http, Districts, Zone, Municipality, G4BarChart, TimeSelectorRadio, RoundNumber, RoundNumberToThird) {
	//Config per export PNG
	$scope.config = new Object();
	$scope.config.chartWidth = 2000;
	
	// carico i dati utlizzati per la ricerca
  	$scope.districts = Districts.getData();
  	$scope.zones = Zone.getData();
  	$scope.municipalities = Municipality.getData();
  	
  	// inizializzazioni
  	$scope.formBean = new Object();
  	$scope.formBean.startDate = new Date();
	$scope.formBean.endDate = new Date();
	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 30);
	$scope.radioItem = "district";
  	$scope.formBean.itemFlagged = $scope.radioItem;
  	$scope.columns = [];
  	$scope.columnsSize = -1;
  	
  	$scope.districtSelectedShow = function($item, $model, $label) {
  		$scope.formBean.districtsSelected = $model;
  		$scope.formBean.zoneSelected = null;
  		$scope.formBean.municipalitySelected = null;
  		$scope.formBean.itemFlagged = null;
  		$scope.radioItem = null;
  	}

  	$scope.zoneSelectedShow = function($item, $model, $label) {
  		$scope.formBean.zoneSelected = $model;
  		$scope.formBean.districtsSelected = null;
  		$scope.formBean.municipalitySelected = null;
  		$scope.formBean.itemFlagged = null;
  		$scope.radioItem = null;
  	}

  	$scope.municipalitySelectedShow = function($item, $model, $label) {
  		$scope.formBean.municipalitySelected = $model;
  		$scope.formBean.districtsSelected = null;
  		$scope.formBean.zoneSelected = null;
  		$scope.formBean.itemFlagged = null;
  		$scope.radioItem = null;
  	}
  	
  	// callback for ng-click 'loadChartData':
  	$scope.loadChartData = function() {
  		if ($scope.formBean.startDate != undefined && $scope.formBean.endDate != undefined
  				&& ($scope.formBean.itemFlagged != undefined || $scope.formBean.districtsSelected != undefined
  						|| $scope.formBean.zoneSelected != undefined || $scope.formBean.municipalitySelected != undefined)) {
  			G4BarChart.getData($scope.formBean, function(data) {
  				$scope.columns = data.columns;
  				var rows = angular.fromJson(data.columns);
  				$scope.columnsSize = rows[0].length - 1;
  				if ($scope.columnsSize > 0) {
  					var chart = c3.generate({
  		    			bindto : '#g4-chart',
  					    data: {
  					        x : rows[0][0],
  					        columns: rows,
  					        type: 'bar',
  					        axes: {
  					        	EPD: 'y',
  					        	IELA: 'y',
  					        	IED: 'y2'
	  				        }
  					    },
  					    bar: {
  			  		        width: {
  			  		        	ratio: 0.7
  			  		        }
  			  		    },
  					    axis: {
  					        x: {
  					            type: 'category',
  					            tick: {
  					            	fit: true,
  					            	rotate: 75
  					            },
  					            height: 190
  					        },
  					        y: {
	  				            label: {
	  				                text: rows[1][0] + ', ' + rows[2][0] + ' [Kwh]',
	  				                position: 'inner-top'
	  				            },
		  				        tick: {
		  				    		format: function (x) { return RoundNumber(x); }
		  				        }
	  				        },
  					        y2: {
	  				            show: true,
	  				         	label: {
	  				         		text: rows[3][0] + ' [Kwh/mc]',
	  				                position: 'inner-top' 
	  				         	},
		  				        tick: {
		  				    		format: function (x) { return RoundNumberToThird(x); }
		  				        }
	  				        }
  					    },
  					    tooltip: {
  					    	format: {
	  				    		value: function (x) { return RoundNumberToThird(x); }
	  				    	}
	  				    },
  					    size: { 
  					    	height: 520
  				    	},
  						padding: {
  							left: 50,
  							right: 100
						}
  					});
  				}
  			});
  		}
  	}
  	
  	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/g4/csv', data: $scope.columns}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart4.csv'
		     });
		  }).
		  error(function(data, status, headers, config) {
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

  	$scope.updateDate = function() {
  		if ($scope.radioModel === '1d'){
    		$scope.formBean.endDate = new Date();
    	}
  		$scope.formBean.startDate = TimeSelectorRadio($scope.radioModel, $scope.formBean.endDate);
  	}
  	
  	$scope.updateItem = function() {
  		$scope.formBean.itemFlagged = $scope.radioItem;
  		$scope.formBean.districtsSelected = null;
  		$scope.formBean.zoneSelected = null;
  		$scope.formBean.municipalitySelected = null;
  	}
}]);


wetnetControllers.controller("WetnetControllerG5", ['$scope', '$http', 'Districts', 'Zone', 'Municipality', 'G5PieChart', 'TimeSelectorRadio', 'RoundNumber',
                                      				function($scope, $http, Districts, Zone, Municipality, G5PieChart, TimeSelectorRadio, RoundNumber) {
	//Config per export PNG
	$scope.config = new Object();
	$scope.config.chartWidth = 2000;
	
	// carico i dati utlizzati per la ricerca
  	$scope.districts = Districts.getData();
  	$scope.zones = Zone.getData();
  	$scope.municipalities = Municipality.getData();
  	
  	// inizializzazioni
  	$scope.formBean = new Object();
  	$scope.formBean.startDate = new Date();
	$scope.formBean.endDate = new Date();
	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 30);
	$scope.columns = [];
  	$scope.columnsSize = -1;

  	$scope.districtSelectedShow = function($item, $model, $label) {
  		$scope.formBean.districtsSelected = $model;
  		$scope.formBean.zoneSelected = null;
  		$scope.formBean.municipalitySelected = null;
  	}

  	$scope.zoneSelectedShow = function($item, $model, $label) {
  		$scope.formBean.zoneSelected = $model;
  		$scope.formBean.districtsSelected = null;
  		$scope.formBean.municipalitySelected = null;
  	}

  	$scope.municipalitySelectedShow = function($item, $model, $label) {
  		$scope.formBean.municipalitySelected = $model;
  		$scope.formBean.districtsSelected = null;
  		$scope.formBean.zoneSelected = null;
  	}
  	
  	// callback for ng-click 'loadChartData':
  	$scope.loadChartData = function() {
  		if ($scope.formBean.startDate != undefined && $scope.formBean.endDate != undefined
  				&& ($scope.formBean.districtsSelected != undefined || $scope.formBean.zoneSelected != undefined || $scope.formBean.municipalitySelected != undefined)) {
  			G5PieChart.getData($scope.formBean, function(data) {
  				$scope.columns = data.columns;
  				var rows = angular.fromJson(data.columns);
  				$scope.columnsSize = rows.length;
  				if ($scope.columnsSize > 0) {
  					var chart = c3.generate({
  						bindto : '#g5-chart',
  						data : {
  							columns : rows,
  							type : 'pie',
  							onmouseover : function(d, i) {},
  							onmouseout : function(d, i) {}
  						},
  						legend : {
  							position : 'right'
  						},
  						tooltip: {
  					    	format: {
  					    		value: function (x) { return RoundNumber(x) + ' Kwh'; }
  					    	}
  					    },
  					    pie: {
					    	label: {
					    		format: function (x) { return RoundNumber(x) + ' Kwh'; }
					    	}
					    },
					    size: { 
					    	height: 520
				    	},
  						padding: {
  							left: 30,
  							right: 30,
  							top: 30,
  							bottom: 30
						},
				    	color: {
							pattern: ['#ff7f0e','#1f77b4']
						}
  					});
  				}
  			});
  		}
  	}
  	
  	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/g5/csv', data: $scope.columns}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart5.csv'
		     });
		  }).
		  error(function(data, status, headers, config) {
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

  	$scope.updateDate = function() {
  		if ($scope.radioModel === '1d'){
    		$scope.formBean.endDate = new Date();
    	}
  		$scope.formBean.startDate = TimeSelectorRadio($scope.radioModel, $scope.formBean.endDate);
  	}
}]);


wetnetControllers.controller("WetnetControllerG6", ['$scope', '$http', 'Districts', 'Zone', 'Municipality', 'G6LineChart', 'TimeSelectorRadio', 'RoundNumber',
                                      				function($scope, $http, Districts, Zone, Municipality, G6LineChart, TimeSelectorRadio, RoundNumber) {
	//Config per export PNG
	$scope.config = new Object();
	$scope.config.chartWidth = 2000;
	
	// carico i dati utlizzati per la ricerca
  	$scope.districts = Districts.getData();
  	$scope.zones = Zone.getData();
  	$scope.municipalities = Municipality.getData();
  	
  	// inizializzazioni
  	$scope.formBean = new Object();
  	$scope.formBean.districtsSelected = new Array();
  	$scope.formBean.zoneSelected = new Array();
  	$scope.formBean.municipalitySelected = new Array();
  	$scope.formBean.startDate = new Date();
	$scope.formBean.endDate = new Date();
	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 30);
	$scope.columns = [];
  	$scope.columnsSize = -1;
  	
  	//add a district
  	$scope.districtSelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
  		var size = $scope.formBean.districtsSelected.length;
		var alreadyAdded = false;
		for(var i = 0; i < size; i++){
			var d = $scope.formBean.districtsSelected[i];
			if (d.name == $item.name){
				alreadyAdded = true;
			}
		}

		if(!alreadyAdded)
			$scope.formBean.districtsSelected[size] = $model;
		
		$scope.formBean.zoneSelected = new Array();
	  	$scope.formBean.municipalitySelected = new Array();
	}
		
  	//remove a district
	$scope.districtSelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i = 0; i < $scope.formBean.districtsSelected.length; i++){
			var d = $scope.formBean.districtsSelected[i];
			if(d.name !== $item.name){
				tempSelected.push(d);
			}
		}
		$scope.formBean.districtsSelected = tempSelected;
	}
	
	//add a municipality
	$scope.municipalitySelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
  		var size = $scope.formBean.municipalitySelected.length;
		var alreadyAdded = false;
		for(var i = 0; i < size; i++){
			var d = $scope.formBean.municipalitySelected[i];
			if (d == $item){
				alreadyAdded = true;
			}
		}

		if(!alreadyAdded)
			$scope.formBean.municipalitySelected[size] = $model;
		
		$scope.formBean.zoneSelected = new Array();
	  	$scope.formBean.districtsSelected = new Array();
	}
		
	//remove a municipality
	$scope.municipalitySelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i = 0; i < $scope.formBean.municipalitySelected.length; i++){
			var d = $scope.formBean.municipalitySelected[i];
			if(d !== $item){
				tempSelected.push(d);
			}
		}
		$scope.formBean.municipalitySelected = tempSelected;
	}
	
	//add a zone
	$scope.zoneSelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
  		var size = $scope.formBean.zoneSelected.length;
		var alreadyAdded = false;
		for(var i = 0; i < size; i++){
			var d = $scope.formBean.zoneSelected[i];
			if (d == $item){
				alreadyAdded = true;
			}
		}

		if(!alreadyAdded)
			$scope.formBean.zoneSelected[size] = $model;
		
		$scope.formBean.municipalitySelected = new Array();
	  	$scope.formBean.districtsSelected = new Array();
	}
		
	//remove a zone
	$scope.zoneSelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i = 0; i < $scope.formBean.zoneSelected.length; i++){
			var d = $scope.formBean.zoneSelected[i];
			if(d !== $item){
				tempSelected.push(d);
			}
		}
		$scope.formBean.zoneSelected = tempSelected;
	}
  	
  	// callback for ng-click 'loadChartData':
  	$scope.loadChartData = function() {
  		if ($scope.formBean.startDate != undefined && $scope.formBean.endDate != undefined
  				&& ($scope.formBean.districtsSelected != undefined || $scope.formBean.zoneSelected != undefined || $scope.formBean.municipalitySelected != undefined)) {
  			G6LineChart.getData($scope.formBean, function(data) {
  				$scope.columns = data.columns;
  				var rows = angular.fromJson(data.columns);
  				$scope.columnsSize = rows[0].length - 1;
  				if ($scope.columnsSize > 0) {
  					var chart = c3.generate({
  						bindto : '#g6-chart',
  						data : {
  							x : 'x',
  							xFormat : '%Y-%m-%d %I:%M:%S', // 'xFormat' can be used as custom format of 'x'
  							columns : rows
  						},
  						axis : {
  							x : {
  								type : 'timeseries',
  								tick : {
  									fit : false,
  									rotate : 15,
  									format : '%a %Y-%m-%d'
  								},
  								height : 70
  							},
  						    y: {
  						    	tick: {
  						    		format: function (x) { return RoundNumber(x); }
  						        }
  						    }
  						},
  						point : {
  							show : true
  						},
  						legend : {
  							show : true
  						},
  						tooltip : {
  							format : {
  								value : function(x) {
  									return RoundNumber(x);
  								}
  							}
  						},
  						grid : {
  							x : {
  								show : true
  							}
  						},
  						size : {
  							height : 520
  						},
  						padding: {
  							left: 50,
  							right: 100
						}
  					});
  				}
  			});
  		}
  	}
  	
  	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/g6/csv', data: $scope.columns}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart6.csv'
		     });
		  }).
		  error(function(data, status, headers, config) {
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

  	$scope.updateDate = function() {
  		if ($scope.radioModel === '1d'){
    		$scope.formBean.endDate = new Date();
    	}
  		$scope.formBean.startDate = TimeSelectorRadio($scope.radioModel, $scope.formBean.endDate);
  	}

}]);


wetnetControllers.controller("WetnetControllerG7", ['$scope', '$http', '$filter', 'Districts', 'Measures', 'G7', 'G7CONF', 'G7CONFPARAM', 'TimeSelectorRadio', 'RoundNumber', 'RoundNumberToThird', 'GetUrlParameter','Zone', 'WaterAuthority','$log','MeasuresHasDistricts',
                                    				function($scope, $http, $filter, Districts, Measures, G7, G7CONF, G7CONFPARAM, TimeSelectorRadio, RoundNumber, RoundNumberToThird, GetUrlParameter, Zone, WaterAuthority,$log,MeasuresHasDistricts) {
	
	//***RC 03/12/2015***
	$scope.configList = new Object();
	$scope.paramList = new Object();
	G7CONF.getData(function (data) {
		$scope.configList = data;
	});
	G7CONFPARAM.getData(function (data) {
		$scope.paramList = data;
	});
	//***END***
	
	//***RC 30/11/2015***
	$scope.confDialog = false;
	//***END***
	
	$scope.$log = $log;

	/*************************** ZONA E SOCIETA*************************/
	
	 	$scope.zones = Zone.getData();
	  	$scope.waterAuthorities = WaterAuthority.getData();
	  	$scope.filterValueZ = '';
	  	$scope.filterValueW = '';
	  	$scope.filteredDistrictsModel = [];
	  	$scope.filteredDistrictsData = [];
	  	$scope.filteredDistrictsSettings = {enableSearch: true, closeOnBlur: true, showCheckAll: false, showUncheckAll:false, scrollable:true};
	  	
	  	$scope.filteredMeasuresModel = [];
	  	$scope.filteredMeasuresData = [];
	  	$scope.filteredMeasuresSettings = {enableSearch: true, closeOnBlur: true, showCheckAll: false, showUncheckAll:false, scrollable:true};
	
	  	$scope.districtMeasures = [];
	  	
	  	/****** GC 14/12/2015*/
	  	$scope.filteredMeasuresDataMap = {};
	/****************************************************/
	
	
	
	//Config per export PNG
	$scope.config = new Object();
	$scope.config.chartWidth = 2000;
	
	// carico i distretti utlizzati per la ricerca
	$scope.measures = Measures.getData(null, function (data) {
		
		/******GC 25/11/2015*/
		$scope.filteredMeasuresData = [];
		for(var c = 0; c < $scope.measures.length; c++){
			var comboMeas = {id:$scope.measures[c].idMeasures, label:$scope.measures[c].name};
			$scope.filteredMeasuresData.push(comboMeas);
		}
		/*******************/
		return data;
	});
	
	
	$scope.columns = [];
	
	// inizializzazioni
	$scope.g7Data = new Object();
	$scope.g7Data.districtsSelected = new Array();
	$scope.g7Data.measuresSelected = new Array();
	$scope.g7Data.dVariables = new Object();
	$scope.g7Data.mVariables = new Object();
	
	$scope.g7Data.dVariables.range = false;
	$scope.g7Data.dVariables.standardDeviation = false;
	$scope.g7Data.dVariables.realLeakage = false;
	$scope.g7Data.dVariables.volumeRealLosses = false;
	$scope.g7Data.dVariables.highBand = false;
	$scope.g7Data.dVariables.lowBand = false;
	$scope.g7Data.dVariables.nightUse = false;
	$scope.g7Data.dVariables.ied = false;
	$scope.g7Data.dVariables.epd = false;
	$scope.g7Data.dVariables.iela = false;
	$scope.g7Data.mVariables.minNight = true;
	$scope.g7Data.mVariables.avgDay = false;
	$scope.g7Data.mVariables.maxDay = false;
	$scope.g7Data.mVariables.minDay = false;
	$scope.g7Data.mVariables.range = false;
	$scope.g7Data.mVariables.standardDeviation = false;
	$scope.g7Data.mVariables.highBand = false;
	$scope.g7Data.mVariables.lowBand = false;
	$scope.isCollapsed = false;
	
	
	/****************GC 27/11/2015***********/
	$scope.listVariables = {};
	$scope.g7Data.checkboxList = new Array();
	$scope.checkboxListTemp = {};
	
	$scope.isChekboxChecked = function(idDistricts,check)
	{
		var res = $scope.listVariables[''+idDistricts][check];
		
		var a = $scope.checkboxListTemp[''+idDistricts];
		
		if(a)
			{
			if(check == 0) res = a.minnight;
			if(check == 1) res = a.avgday;
			if(check == 2) res = a.maxday;
			if(check == 3) res = a.minday;
			}
		
		return res;
	};
	/****************/

	//inizializzo la data
	$scope.date = GetUrlParameter('day');
	if ($scope.date){ //data nella query string da lista eventi
		$scope.duration = parseInt(GetUrlParameter('duration'));
		$scope.year = parseInt($scope.date.substring(0, 4));
		$scope.month = parseInt($scope.date.substring(5, 7)) - 1;
		$scope.day = parseInt($scope.date.substring(8, 10));
		$scope.g7Data.startDate = new Date($scope.year, $scope.month, $scope.day - $scope.duration - 2, 0, 0, 0);
		$scope.g7Data.endDate = new Date($scope.year, $scope.month, $scope.day, 0, 0, 0);
	} else { //comportamento di default
		$scope.g7Data.startDate = new Date();
		$scope.g7Data.endDate = new Date();
		$scope.g7Data.startDate.setDate($scope.g7Data.endDate.getDate() - 30);
	}
	
	//carico i distretti utilizzati per la ricerca
	$scope.id = GetUrlParameter('idDistricts');
	$scope.districts = Districts.getData(null, function (data) {
									//id nella query string da lista eventi
									if ($scope.id){
										for (var i = 0; i < data.length; i++){
											var d = data[i];
											if($scope.id == d.idDistricts){
												$scope.districtSelectedAdd(d, d, '');
												break;
											}
										}
										
									
									}
									
									/******GC 25/11/2015*/
									$scope.filteredDistrictsData = [];
									for(var c = 0; c < $scope.districts.length; c++){
										var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
										$scope.filteredDistrictsData.push(comboDistricts);
									
										MeasuresHasDistricts.getByDistrictsId({districts_id: $scope.districts[c].idDistricts}, function(data2){
							        		$scope.districtMeasures.push(data2.data.measures);
							        	 });
									
									}
									/*******************/
									
									
									/************* GC 26/11/2015****/
									for(var c = 0; c < $scope.districts.length; c++){
										//min_night,avg_day,max_day,min_day
										var ar = [false,false,false,false];
										
										if($scope.districts[c].ev_variable_type == 0) ar[0] = true;
										if($scope.districts[c].ev_variable_type == 3) ar[1] = true;
										if($scope.districts[c].ev_variable_type == 2) ar[2] = true;
										if($scope.districts[c].ev_variable_type == 1) ar[3] = true;
										if($scope.districts[c].ev_variable_type == 4) ar[0] = true;
										
										$scope.listVariables[''+$scope.districts[c].idDistricts] = ar;
										
										var map = {"minnight":ar[0],"avgday":ar[1],"maxday":ar[2],"minday":ar[3]};
										$scope.checkboxListTemp[''+$scope.districts[c].idDistricts] = map;
									}
									
									
									if ($scope.id)
										{
											/*GC 27/11/2015 */
											var tempCheck = new Array();
											$scope.g7Data.checkboxList = new Array();
											for(var c = 0; c < $scope.g7Data.districtsSelected.length; c++){
												var a = $scope.checkboxListTemp[''+$scope.g7Data.districtsSelected[c].idDistricts];
												var t = new Array();
												t[0] = ''+$scope.g7Data.districtsSelected[c].idDistricts;
												t[1] = a;
												tempCheck.push(t);
											}
											$scope.g7Data.checkboxList = tempCheck;
										}
									
									return data;
								});
	

	
	
	/*************************************************************/
	//Inizio gestione Selezione Distretti 
	$scope.districtSelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
		var alreadyAdded = false;
		for(var i=0; i< $scope.g7Data.districtsSelected.length; i++){
			var d = $scope.g7Data.districtsSelected[i];
			if(d.name == $item.name){
				alreadyAdded = true;
			}
		}

		if(!alreadyAdded){
			var size = $scope.g7Data.districtsSelected.length;
			
			/* GC - 29/10/2015 */
			$model.measures = Measures.getDataByDistrictId({districtId: $model.idDistricts, withSign:true}, $scope, function () {
																										//id nella query string da lista eventi
																										if ($scope.id){
																											$scope.loadData();
																											$scope.dSelected = '';
																										}
																									});
			$scope.g7Data.districtsSelected[size] = $model;
		}
		$scope.dSelected = new Object();
		
		var tempCheck = new Array();
		$scope.g7Data.checkboxList = new Array();
		for(var c = 0; c < $scope.g7Data.districtsSelected.length; c++){
			var a = $scope.checkboxListTemp[''+$scope.g7Data.districtsSelected[c].idDistricts];
			var t = new Array();
			t[0] = ''+$scope.g7Data.districtsSelected[c].idDistricts;
			t[1] = a;
			tempCheck.push(t);
		}
		$scope.g7Data.checkboxList = tempCheck;
	};
		
	$scope.districtSelectedRemove = function($item){
			var tempSelected = new Array();
		for(var i=0; i< $scope.g7Data.districtsSelected.length; i++){
			var d = $scope.g7Data.districtsSelected[i];
			if(d.name !== $item.name){
				tempSelected.push(d);
			}
		}
		$scope.g7Data.districtsSelected = tempSelected;
		
		
		/* GC 25/22/2015*/
		var tempfilteredDistrictsModel = [];
		for(var i = 0; i < $scope.filteredDistrictsModel.length; i++)
			{
				if($scope.filteredDistrictsModel[i].id !== $item.idDistricts)
					{
					tempfilteredDistrictsModel.push($scope.filteredDistrictsModel[i]);
					}
			}
		$scope.filteredDistrictsModel = tempfilteredDistrictsModel;
		
		
		/*GC 27/11/2015 */
		var tempCheck = new Array();
		$scope.g7Data.checkboxList = new Array();
		for(var c = 0; c < $scope.g7Data.districtsSelected.length; c++){
			var a = $scope.checkboxListTemp[''+$scope.g7Data.districtsSelected[c].idDistricts];
			var t = new Array();
			t[0] = ''+$scope.g7Data.districtsSelected[c].idDistricts;
			t[1] = a;
			tempCheck.push(t);
		}
		$scope.g7Data.checkboxList = tempCheck;
		
		
	};
	//Fine gestione Selezione Distretti
/*****************************************************************/
	
	//***RC 01/12/2015***
	$scope.showDialog = function () {
		//if($scope.g2Data.districtsSelected.length > 0 || $scope.g2Data.measuresSelected.length > 0){
			$scope.confDialog = true;
		//}
	};
	
	$scope.dialogCancelled = function () {
		$scope.confDialog = false;
	};
	
	$scope.saveConf = function () {
		if($scope.g7Data.districtsSelected.length > 0 || $scope.g7Data.measuresSelected.length > 0){
			
			var desc = document.getElementById("confDesc").value;
			$scope.g7Data.descriptionConfiguration = desc;
			
			G7CONF.saveG7Configuration($scope.g7Data, function (data) {
				
				G7CONF.getData(function (data) {
					$scope.configList = data;
				});
				G7CONFPARAM.getData(function (data) {
					$scope.paramList = data;
				});
			});	
			
		}
	};
	
	$scope.configSelected = function (config) {
		
		var configParamsList = [];
		var districtsParamList = [];
		var measuresParamList = [];
		
		for(var i = 0; i < $scope.paramList.length; i++) {
			if(($scope.paramList[i].users_cfgs_parent_save_date == config.save_date) && ($scope.paramList[i].users_cfgs_parent_submenu_function == 7)){
				configParamsList.push($scope.paramList[i]);
			}
		}
		
		for(var i = 0; i < configParamsList.length; i++) {
			if(configParamsList[i].type == 1){	//DISTRETTI
				for(var x = 0; x < $scope.districts.length; x++) {
					if(configParamsList[i].objectid == $scope.districts[x].idDistricts){
						
						/* GC - 12/11/2015 */
						var measTemp = Measures.getDataByDistrictId({districtId: $scope.districts[x].idDistricts, withSign:true}, $scope, function () {
																													//id nella query string da lista eventi
																													if ($scope.id){
																														$scope.loadData();
																														$scope.dSelected = '';
																													}
																												});
						$scope.districts[x].measures = measTemp;
						/******/
						
						
						
						districtsParamList.push($scope.districts[x]);
					}
				}
			}else{	//MISURE
				for(var x = 0; x < $scope.measures.length; x++) {
					if(configParamsList[i].objectid == $scope.measures[x].idMeasures){
						measuresParamList.push($scope.measures[x]);
					}
				}
			}
		}
		$scope.g7Data.districtsSelected = null;
		$scope.g7Data.measuresSelected = null;
		$scope.g7Data.districtsSelected = districtsParamList;
		$scope.g7Data.measuresSelected = measuresParamList;
	
		var tempCheck = new Array();
		$scope.g7Data.checkboxList = new Array();
		for(var c = 0; c < $scope.g7Data.districtsSelected.length; c++){
			var a = $scope.checkboxListTemp[''+$scope.g7Data.districtsSelected[c].idDistricts];
			var t = new Array();
			t[0] = ''+$scope.g7Data.districtsSelected[c].idDistricts;
			t[1] = a;
			tempCheck.push(t);
		}
		$scope.g7Data.checkboxList = tempCheck;
	};
	
	$scope.configDeleted = function (config) {

		var _date = $filter('date')(config.save_date, 'yyyy-MM-dd HH:mm:ss');
		
		G7CONF.remove({parent: _date}, function (data) {
			//alert(data);
			G7CONF.getData(function (data) {
				$scope.configList = data;
			});
			G7CONFPARAM.getData(function (data) {
				$scope.paramList = data;
			});
		});	
	};
	
	//***END***
	
	
	/********************************************************************/
	
	//Inizio gestione Selezione Measures 
	$scope.measureSelectedAdd = function($item, $model, $label){
		//prima di aggiungerlo, verifico se e' gia stato aggiunto
		var alreadyAdded = false;
		for(var i=0; i< $scope.g7Data.measuresSelected.length; i++){
			var d = $scope.g7Data.measuresSelected[i];
			if(d.name == $item.name){
				alreadyAdded = true;
			}
		}
		if(!alreadyAdded){
			var size = $scope.g7Data.measuresSelected.length; 
			
			$scope.g7Data.measuresSelected[size] = $model;			
		}
	}
		
	$scope.measureSelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i=0; i< $scope.g7Data.measuresSelected.length; i++){
			var d = $scope.g7Data.measuresSelected[i];
			if(d.name !== $item.name){
				tempSelected.push(d);
			}
		}
		$scope.g7Data.measuresSelected = tempSelected;
		
		/* GC 25/22/2015*/
		var tempfilteredMeasuresModel = [];
		for(var i = 0; i < $scope.filteredMeasuresModel.length; i++)
			{
				if($scope.filteredMeasuresModel[i].id !== $item.idMeasures)
					{
					tempfilteredMeasuresModel.push($scope.filteredMeasuresModel[i]);
					}
			}
		$scope.filteredMeasuresModel = tempfilteredMeasuresModel;
	}
	//Fine gestione Selezione Measures
	
	
	/********************************************************************/
	
	/****************filtro distretti e misure da zona e societa******/
	
	$scope.updateMeasuresSelectByDistrictsFiltered = function ()
	{
		$scope.filteredMeasuresData = [];
		$scope.filteredMeasuresModel = [];

		/****** GC 14/12/2015*/
		$scope.filteredMeasuresDataMap = {};
		/*******/
		
		for(var j = 0; j < $scope.districtMeasures.length; j++)
			{
			var temp = $scope.districtMeasures[j];
			
			for(var z = 0; z < temp.length; z++)
				{
				var idDistr = temp[z].districts_id_districts;
				var idMeas = temp[z].measures_id_measures;
				
				var nameMeas = temp[z].measures_name;
				var nameDistr = temp[z].districts_name;
				
				for(var y = 0; y < $scope.filteredDistrictsData.length; y++)
					{
					
					var idD=$scope.filteredDistrictsData[y].id;
						if(idD === idDistr){
							/****** GC 14/12/2015*/
							if($scope.filteredMeasuresDataMap[""+idMeas] == null)
							{	
								/*******/
								var comboMeasures = {id:idMeas, label:nameMeas};
								$scope.filteredMeasuresData.push(comboMeasures);
								/****** GC 14/12/2015*/
								$scope.filteredMeasuresDataMap[""+idMeas] = comboMeasures;
								/*******/
								break;
							}
						}
					}
				
				
				}
			}
		
		/****** GC 14/12/2015*/
		//ordino alfabeticamente
		$scope.filteredMeasuresData.sort(function(a, b) {
		    return a.label.localeCompare(b.label);
		});
		/*******/
		
	};
	
	
	   $scope.filterDistricts = function(filterType) {
	    	
	    	$scope.filteredDistrictsData = [];
	    	$scope.filteredDistrictsModel = [];
		  	
			if(filterType == 'zone'){
				
				$scope.filterValueW = '';
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
			}else{
				$scope.filterValueZ = '';
				if($scope.filterValueW === '')
				{
					for(var c = 0; c < $scope.districts.length; c++){
							var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
							$scope.filteredDistrictsData.push(comboDistricts);
					}
				}
				else {
					for(var c = 0; c < $scope.districts.length; c++){
						var waterAuthority = $scope.districts[c].waterAuthority;
						if(waterAuthority != null && waterAuthority.trim().length>0 && waterAuthority.toUpperCase() == $scope.filterValueW.toUpperCase()){
							var comboDistricts = {id:$scope.districts[c].idDistricts, label:$scope.districts[c].name};
							$scope.filteredDistrictsData.push(comboDistricts);
						}
					}
				}
			}
			
			$scope.updateMeasuresSelectByDistrictsFiltered();
		};
		
		$scope.getDistrictById = function(id)
		{
			var d = null;
			for(var i = 0; i < $scope.districts.length;i++)
				{
					if($scope.districts[i].idDistricts === id)
						{
						d = $scope.districts[i];
						break;
						}
				}
			
			return d;
		};
		
		$scope.getMeasuresById = function(id)
		{
			var d = null;
			for(var i = 0; i < $scope.measures.length;i++)
				{
					if($scope.measures[i].idMeasures === id)
						{
						d = $scope.measures[i];
						break;
						}
				}
			
			return d;
		};
		
		$scope.filteredDistrictsEvents = {
				onItemSelect: 
					function(item) {
					var d = $scope.getDistrictById(item.id);
					$scope.districtSelectedAdd(d, d, '');
					
					/*GC 27/11/2015*/
						var a = $scope.checkboxListTemp[''+item.id];
						var t = new Array();
	        			t[0] = ''+item.id;
	        			t[1] = a;
						$scope.g7Data.checkboxList.push(t);
					},
	            onItemDeselect: 
	            	function(item) {
	            	  	var tempSelected = new Array();
		        		for(var i=0; i< $scope.g7Data.districtsSelected.length; i++){
		        			var d = $scope.g7Data.districtsSelected[i];
		        			if(d.idDistricts !== item.id){
		        				tempSelected.push(d);
		        			}
		        		}
		        		$scope.g7Data.districtsSelected = tempSelected;
		        		
		        		var tempCheck = new Array();
		        		for(var c = 0; c < $scope.g7Data.districtsSelected.length; c++){
		        			var a = $scope.checkboxListTemp[''+$scope.g7Data.districtsSelected[c].idDistricts];
		        			var t = new Array();
		        			t[0] = ''+$scope.g7Data.districtsSelected[c].idDistricts;
		        			t[1] = a;
		        			tempCheck.push(t);
		        		}
		        		$scope.g7Data.checkboxList = tempCheck;
		        		
	               	}
	    };
		
		$scope.filteredMeasuresEvents = {
				onItemSelect: 
					function(item) {
					var d = $scope.getMeasuresById(item.id);
					$scope.measureSelectedAdd(d, d, '');
	            	},
	            onItemDeselect: 
	            	function(item) {
		            	var tempSelected = new Array();
		        		for(var i=0; i< $scope.g7Data.measuresSelected.length; i++){
		        			var d = $scope.g7Data.measuresSelected[i];
		        			if(d.idMeasures !== item.id){
		        				tempSelected.push(d);
		        			}
		        		}
		        		$scope.g7Data.measuresSelected = tempSelected;
	               	}
	    };
	  	//***END***
	
		/*************************************/

	// callback for ng-click 'loadData':
	$scope.loadData = function() {
		if ($scope.g7Data.startDate != undefined && $scope.g7Data.endDate != undefined
				&& ($scope.g7Data.districtsSelected != undefined || $scope.g7Data.measuresSelected != undefined)) {
			G7.getData($scope.g7Data, function(data) {
				
				/*GC 02/11/2015*/
				$scope.g7Data.medie = data.medie;
				
				$scope.g7Data.columns = data.columns;
				$scope.columns = data.columns;
				var rows = angular.fromJson(data.columns);
				var maxData = data.columns[0][data.columns[0].length - 1];
				var chart = c3.generate({
					bindto : '#g7-chart',
					data : {
						x : 'x',
						xFormat : '%Y-%m-%d %I:%M:%S', // 'xFormat' can be used as custom format of 'x'
						columns : rows
					},
					axis : {
						x : {
							type : 'timeseries',
							tick : {
								fit : false,
								rotate : 15,
								format : '%a %Y-%m-%d'
							},
							height : 70
						},
						y : {
							tick : {
								format : function(x) {
									return RoundNumberToThird(x);
								}
							}
						}
					},
					point : {
						show : true
					},
					legend : {
						show : true
					},
					tooltip : {
						/*format : {
							value : function(x) {
								return RoundNumberToThird(x);
							}
						}*/
						/* GC 02/11/2015 */
				    	contents: function tooltip_contents(d, defaultTitleFormat, defaultValueFormat, color) {
						    var $$ = this, config = $$.config, CLASS = $$.CLASS,
					        titleFormat = config.tooltip_format_title || defaultTitleFormat,
					        nameFormat = config.tooltip_format_name || function (name) { return name; },
					        valueFormat = config.tooltip_format_value || defaultValueFormat,
					        text, i, title, value, name, bgcolor;
					        
					       var dateFormat = d3.time.format('%a %Y-%m-%d');
					       
					    for (i = 0; i < d.length; i++) {
					    	
					        if (i==0) {
					            title = dateFormat(d[i].x);
					            text = "<table class='" + CLASS.tooltip + "'>" + (title || title === 0 ? "<tr><th colspan='3'>" + title + "</th></tr>" : "");
					            text += "<tr>";
						        text += "<td></td>";
						        text += "<td>value</td>";
						        text += "<td>media</td>";
						        text += "</tr>";
					        }
					        
					        name = nameFormat(d[i].name);
					        bgcolor = $$.levelColor ? $$.levelColor(d[i].value) : color(d[i].id);
					        
					        var media = "0";
					        
					        for(var z = 0; z < $scope.g7Data.medie.length; z++)
				        	{
				        	var s = $scope.g7Data.medie[z];
				        	
					        	for(var key in s)
					            {
					        		if(key == d[i].name)
					        			{
					        			media = s[key];
					        			break;
					        			}
					            }
				        	}
					        
					        text += "<tr class='" + CLASS.tooltipName + "-" + d[i].id + "'>";
					        text += "<td class='name'><span style='background-color:" + bgcolor + "'></span>" + d[i].name + "</td>";
					        text += "<td>" + RoundNumberToThird(d[i].value) + "</td>";
					        text += "<td>" + RoundNumberToThird(media) + "</td>";
					        text += "</tr>";
					      
					    }
					    return text + "</table>";   
					}
					},
					grid : {
						x : {
							show : true
						}
					},
					size : {
						height : 520
					},
					padding: {
						left: 50,
						right: 100
					}
				});
			});
		}
	}

	// callback for ng-click 'exportCSV':
	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/g7/csv', data: $scope.columns}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart7.csv'
		     });
		  }).
		  error(function(data, status, headers, config) {
		    // if there's an error you should see it here
		  });
	}

	// configurazioni datepicker
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

	$scope.updateDate = function() {
		if ($scope.radioModel === '1d'){
    		$scope.g7Data.endDate = new Date();
    	}
		$scope.g7Data.startDate = TimeSelectorRadio($scope.radioModel, $scope.g7Data.endDate);
	}
	

	/*
	 * GC - 29/10/2015
	 */
    $scope.checkSign = function(elem){
	
	if(elem == '0') return '+';
	else if(elem == '1') return '-';
	else return '*';
	
};
	
}]);


/*GC 16/11/2015*/
wetnetControllers.controller("WetnetControllerG8", ['$scope', '$http', '$filter', 'Districts', 'Measures', 'G8', 'G8CONF', 'G8CONFPARAM', 'TimeSelectorRadio', 'RoundNumber', 'GetUrlParameter','$log',
                                    				function($scope, $http, $filter, Districts, Measures, G8, G8CONF, G8CONFPARAM, TimeSelectorRadio, RoundNumber, GetUrlParameter,$log) {

	//***RC 03/12/2015***
	$scope.configList = new Object();
	$scope.paramList = new Object();
	G8CONF.getData(function (data) {
		$scope.configList = data;
	});
	G8CONFPARAM.getData(function (data) {
		$scope.paramList = data;
	});
	//***END***
	
/*GC - settaggio $log per debug******************************************************************************************/
$scope.$log = $log;

$scope.config = new Object();
$scope.config.chartWidth = 2000;
$scope.measures = Measures.getData(); //carico le misure utlizzate per la ricerca
$scope.columns = [];

//inizializzazioni
$scope.g8Data = new Object();
$scope.g8Data.districtsSelected = new Array();
$scope.g8Data.measuresSelected = new Array();
$scope.g8Data.dVariables = new Object();
$scope.g8Data.mVariables = new Object();
$scope.g8Data.dVariables.range = false;
$scope.g8Data.dVariables.standardDeviation = false;
$scope.g8Data.dVariables.realLeakage = false;
$scope.g8Data.dVariables.volumeRealLosses = false;
$scope.g8Data.dVariables.highBand = false;
$scope.g8Data.dVariables.lowBand = false;
$scope.g8Data.dVariables.nightUse = false;
$scope.g8Data.dVariables.ied = false;
$scope.g8Data.dVariables.epd = false;
$scope.g8Data.dVariables.iela = false;
$scope.g8Data.dVariables.value = false;
$scope.g8Data.dVariables.energy=false;
$scope.g8Data.dVariables.losses=false;
$scope.g8Data.mVariables.minNight = true;
$scope.g8Data.mVariables.avgDay = false;
$scope.g8Data.mVariables.maxDay = false;
$scope.g8Data.mVariables.minDay = false;
$scope.g8Data.mVariables.range = false;
$scope.g8Data.mVariables.standardDeviation = false;
$scope.g8Data.mVariables.highBand = false;
$scope.g8Data.mVariables.lowBand = false;
$scope.g8Data.mVariables.value = false;
$scope.g8Data.timebase = '0';
$scope.g8Data.granularity = '1';

$scope.isCollapsed = false;

//inizializzo la data
$scope.date = GetUrlParameter('day');
if ($scope.date){ //data nella query string da lista eventi
	$scope.duration = parseInt(GetUrlParameter('duration'));
	$scope.year = parseInt($scope.date.substring(0, 4));
	$scope.month = parseInt($scope.date.substring(5, 7)) - 1;
	$scope.day = parseInt($scope.date.substring(8, 10));
	$scope.g8Data.startDate = new Date($scope.year, $scope.month, $scope.day - $scope.duration - 2, 0, 0, 0);
	$scope.g8Data.endDate = new Date($scope.year, $scope.month, $scope.day, 0, 0, 0);
} else { //comportamento di default
	$scope.g8Data.startDate = new Date();
	$scope.g8Data.endDate = new Date();
	$scope.g8Data.startDate.setDate($scope.g8Data.endDate.getDate() - 30);
}

//carico i distretti utlizzati per la ricerca
$scope.id = GetUrlParameter('idDistricts');
$scope.districts = Districts.getData(null, function (data) {
					//id nella query string da lista eventi
					if ($scope.id){
						for (var i = 0; i < data.length; i++){
							var d = data[i];
							if($scope.id == d.idDistricts){
								$scope.districtSelectedAdd(d, d, '');
								break;
							}
						}
					}
					
					
					/************* GC 26/11/2015****/
					for(var c = 0; c < $scope.districts.length; c++){
						//min_night,avg_day,max_day,min_day
						var ar = [false,false,false,false];
						
						if($scope.districts[c].ev_variable_type == 0) ar[0] = true;
						if($scope.districts[c].ev_variable_type == 3) ar[1] = true;
						if($scope.districts[c].ev_variable_type == 2) ar[2] = true;
						if($scope.districts[c].ev_variable_type == 1) ar[3] = true;
						if($scope.districts[c].ev_variable_type == 4) ar[0] = true;
						
						$scope.listVariables[''+$scope.districts[c].idDistricts] = ar;
						
						var map = {"minnight":ar[0],"avgday":ar[1],"maxday":ar[2],"minday":ar[3]};
						$scope.checkboxListTemp[''+$scope.districts[c].idDistricts] = map;
					}
					
					if($scope.id)
						{
							var tempCheck = new Array();
							$scope.g8Data.checkboxList = new Array();
							for(var c = 0; c < $scope.g8Data.districtsSelected.length; c++){
								var a = $scope.checkboxListTemp[''+$scope.g8Data.districtsSelected[c].idDistricts];
								var t = new Array();
								t[0] = $scope.g8Data.districtsSelected[c].idDistricts;
								t[1] = a;
								tempCheck.push(t);
							}
							$scope.g8Data.checkboxList = tempCheck;
						}
					
					return data;
				});

//Inizio gestione Selezione Distretti 
$scope.districtSelectedAdd = function($item, $model, $label){
	//prima di aggiungerlo, verifico se e' gia stato aggiunto
	var alreadyAdded = false;
	for(var i=0; i< $scope.g8Data.districtsSelected.length; i++){
		var d = $scope.g8Data.districtsSelected[i];
			if(d.name == $item.name){
			alreadyAdded = true;
			}
	}
	
		if(!alreadyAdded){
		var size = $scope.g8Data.districtsSelected.length;
		
		/* GC - 29/10/2015 */
		$model.measures = Measures.getDataByDistrictId({districtId: $model.idDistricts, withSign:true}, $scope, function () {
																								//id nella query string da lista eventi
																								if ($scope.id){
																									$scope.loadData();
																									$scope.dSelected = '';
																								}
																							});
		$scope.g8Data.districtsSelected[size] = $model;
		}
	$scope.dSelected = new Object();

	/*GC 27/11/2015*/
//	var a = $scope.checkboxListTemp[''+$model.idDistricts];
//	var t = new Array();
//	t[0] = ''+$model.idDistricts;
//	t[1] = a;
//	$scope.g8Data.checkboxList.push(t);
	
	var tempCheck = new Array();
	$scope.g8Data.checkboxList = new Array();
	for(var c = 0; c < $scope.g8Data.districtsSelected.length; c++){
		var a = $scope.checkboxListTemp[''+$scope.g8Data.districtsSelected[c].idDistricts];
		var t = new Array();
		t[0] = $scope.g8Data.districtsSelected[c].idDistricts;
		t[1] = a;
		tempCheck.push(t);
	}
	$scope.g8Data.checkboxList = tempCheck;
};

$scope.districtSelectedRemove = function($item){
	var tempSelected = new Array();
	for(var i=0; i< $scope.g8Data.districtsSelected.length; i++){
		var d = $scope.g8Data.districtsSelected[i];
			if(d.name !== $item.name){
			tempSelected.push(d);
			}
	}
	$scope.g8Data.districtsSelected = tempSelected;
	
	
	/*GC 27/11/2015 */
	var tempCheck = new Array();
	$scope.g8Data.checkboxList = new Array();
	for(var c = 0; c < $scope.g8Data.districtsSelected.length; c++){
		var a = $scope.checkboxListTemp[''+$scope.g8Data.districtsSelected[c].idDistricts];
		var t = new Array();
		t[0] = $scope.g8Data.districtsSelected[c].idDistricts;
		t[1] = a;
		tempCheck.push(t);
	}
	$scope.g8Data.checkboxList = tempCheck;
	
	
};
//Fine gestione Selezione Distretti

//***RC 03/11/2015***
//carico la misura selezionata dalla dashboard
$scope.idM = GetUrlParameter('idMeasures');
$scope.measures = Measures.getData(null, function (data) {
					//id nella query string da lista eventi
					if ($scope.idM){
						for (var i = 0; i < data.length; i++){
							var d = data[i];
							if($scope.idM == d.idMeasures){
								$scope.measureSelectedAdd(d, d, '');
								break;
							}
						}
					}
					return data;
				});
//***END***

//***RC 01/12/2015***
$scope.showDialog = function () {
		$scope.confDialog = true;
};

$scope.dialogCancelled = function () {
	$scope.confDialog = false;
};

$scope.saveConf = function () {
	if($scope.g8Data.districtsSelected.length > 0 || $scope.g8Data.measuresSelected.length > 0){
		
		var desc = document.getElementById("confDesc").value;
		$scope.g8Data.descriptionConfiguration = desc;
		
		G8CONF.saveG8Configuration($scope.g8Data, function (data) {
			
			G8CONF.getData(function (data) {
				$scope.configList = data;
			});
			G8CONFPARAM.getData(function (data) {
				$scope.paramList = data;
			});
		});	
		
	}
};

$scope.configSelected = function (config) {
	
	var configParamsList = [];
	var districtsParamList = [];
	var measuresParamList = [];
	
	for(var i = 0; i < $scope.paramList.length; i++) {
		if(($scope.paramList[i].users_cfgs_parent_save_date == config.save_date) && ($scope.paramList[i].users_cfgs_parent_submenu_function == 8)){
			configParamsList.push($scope.paramList[i]);
		}
	}
	
	for(var i = 0; i < configParamsList.length; i++) {
		if(configParamsList[i].type == 1){	//DISTRETTI
			for(var x = 0; x < $scope.districts.length; x++) {
				if(configParamsList[i].objectid == $scope.districts[x].idDistricts){
					
					/* GC - 12/11/2015 */
					var measTemp = Measures.getDataByDistrictId({districtId: $scope.districts[x].idDistricts, withSign:true}, $scope, function () {
																												//id nella query string da lista eventi
																												if ($scope.id){
																													$scope.loadData();
																													$scope.dSelected = '';
																												}
																											});
					$scope.districts[x].measures = measTemp;
					/******/
					
					
					districtsParamList.push($scope.districts[x]);
				}
			}
		}else{	//MISURE
			for(var x = 0; x < $scope.measures.length; x++) {
				if(configParamsList[i].objectid == $scope.measures[x].idMeasures){
					measuresParamList.push($scope.measures[x]);
				}
			}
		}
	}
	$scope.g8Data.districtsSelected = null;
	$scope.g8Data.measuresSelected = null;
	$scope.g8Data.districtsSelected = districtsParamList;
	$scope.g8Data.measuresSelected = measuresParamList;
	
	if(config.time_base == 0){
		$scope.selectedTimeBase = '0';
	}
	if(config.time_base == 1){
		$scope.selectedTimeBase = '1';
	}
	if(config.time_base == 2){
		$scope.selectedTimeBase = '2';
	}
	if(config.time_base == 3){
		$scope.selectedTimeBase = '3';
	}
	
	if(config.granularity == 0){
		$scope.selectedGranularity = '0';
	}
	if(config.granularity == 1){
		$scope.selectedGranularity = '1';
	}
	if(config.granularity == 2){
		$scope.selectedGranularity = '2';
	}
	if(config.granularity == 3){
		$scope.selectedGranularity = '3';
	}
	
	$scope.g8Data.timebase = $scope.selectedTimeBase;
	$scope.g8Data.granularity = $scope.selectedGranularity;
	$scope.showViewConfigurator = true;
	
	var tempCheck = new Array();
	$scope.g8Data.checkboxList = new Array();
	for(var c = 0; c < $scope.g8Data.districtsSelected.length; c++){
		var a = $scope.checkboxListTemp[''+$scope.g8Data.districtsSelected[c].idDistricts];
		var t = new Array();
		t[0] = $scope.g8Data.districtsSelected[c].idDistricts;
		t[1] = a;
		tempCheck.push(t);
	}
	$scope.g8Data.checkboxList = tempCheck;
	
};

$scope.configDeleted = function (config) {

	var _date = $filter('date')(config.save_date, 'yyyy-MM-dd HH:mm:ss');
	
	G8CONF.remove({parent: _date}, function (data) {
		//alert(data);
		G8CONF.getData(function (data) {
			$scope.configList = data;
		});
		G8CONFPARAM.getData(function (data) {
			$scope.paramList = data;
		});
	});	
};

//***END***

//Inizio gestione Selezione Measures 
	$scope.measureSelectedAdd = function($item, $model, $label){
	//prima di aggiungerlo, verifico se e' gia stato aggiunto
	var alreadyAdded = false;
		for(var i=0; i< $scope.g8Data.measuresSelected.length; i++){
		var d = $scope.g8Data.measuresSelected[i];
		if(d.name == $item.name){
		alreadyAdded = true;
		}
		}
		if(!alreadyAdded){
		var size = $scope.g8Data.measuresSelected.length; 
		//***RC 03/11/2015***
			if ($scope.idM){
			$scope.loadData();
			}
		//***END***
		$scope.g8Data.measuresSelected[size] = $model;			
		}
}

	$scope.measureSelectedRemove = function($item){
		var tempSelected = new Array();
		for(var i=0; i< $scope.g8Data.measuresSelected.length; i++){
		var d = $scope.g8Data.measuresSelected[i];
		if(d.name !== $item.name){
		tempSelected.push(d);
		}
	}
	$scope.g8Data.measuresSelected = tempSelected;
}
//Fine gestione Selezione Measures

	
	
	/****************GC 27/11/2015***********/
	$scope.listVariables = {};
	$scope.g8Data.checkboxList = new Array();
	$scope.checkboxListTemp = {};

	$scope.isChekboxChecked = function(idDistricts,check)
	{
		var res = $scope.listVariables[''+idDistricts][check];
		
		var a = $scope.checkboxListTemp[''+idDistricts];
		
		if(a)
			{
			if(check == 0) res = a.minnight;
			if(check == 1) res = a.avgday;
			if(check == 2) res = a.maxday;
			if(check == 3) res = a.minday;
			}
		
		return res;
	};
	/****************/


	
	
	
//gestione timebase granularity
var gran_timebaseDay=[
 {id:'0', name:'timestamp'},
 {id:'1', name:'media oraria'}
];

var gran_timebaseWeeklyMonthly=[
                      {id:'0', name:'timestamp'},
                      {id:'1', name:'media oraria'},
                      {id:'2', name:'media giornaliera'}
                     ];

var gran_timebaseAnnual=[
                            {id:'0', name:'timestamp'},
                            {id:'1', name:'media oraria'},
                            {id:'2', name:'media giornaliera'},
                            {id:'3', name:'media mensile'}
                            // {id:'4', name:'media annuale'}
                             ];

$scope.timebase=['0','1','2','3'];
$scope.granularity=gran_timebaseDay;

$scope.selectedTimeBase='0';
$scope.selectedGranularity='1';

$scope.filterGranularity = function(item){
	if(item === '0')
		{
		$scope.granularity = gran_timebaseDay;
		$scope.selectedGranularity='1';
		}
	if(item === '1' || item === '2')
		{
		$scope.granularity = gran_timebaseWeeklyMonthly;
		$scope.selectedGranularity='2';
		}
	if(item === '3')
		{
		$scope.granularity = gran_timebaseAnnual;
		$scope.selectedGranularity='3';
		}
};

$scope.applySelectTimebaseGranularity = function(){
	$scope.g8Data.timebase = $scope.selectedTimeBase;
	$scope.g8Data.granularity = $scope.selectedGranularity;
};

$scope.showViewConfigurator = false;

$scope.showViewConfiguratorMethod = function(){
	$scope.showViewConfigurator = true;
};

$scope.closeViewConfiguratorMethod = function(){
	$scope.showViewConfigurator = false;
};

//fine gestione timebased granularity


// callback for ng-click 'loadData':
$scope.loadData = function () {
if($scope.g8Data.startDate != undefined && $scope.g8Data.endDate != undefined && $scope.g8Data.districtsSelected != undefined){
	
	/*  
	 var config = {
			 timeout:120000
         }

	$http.post('/wetnet/rest/d3/g8', $scope.g8Data, config).
	  success(function(data, status, headers, config) {*/
	
G8.getData($scope.g8Data, function (data) {

/*GC 02/11/2015*/
$scope.g8Data.medie = data.medie;

$scope.g8Data.columns = data.columns;
$scope.g8Data.districtsSelected = data.districtsSelected;
$scope.g8Data.measuresSelected = data.measuresSelected;
var rows = angular.fromJson(data.columns);
var maxData = data.columns[0][data.columns[0].length - 1 ];

$scope.columns = rows;

var tickForm = '%Y-%m-%d %I:%M:%S';
var tooltipForm = '%a %Y-%m-%d %H:%M';
var xForm = '%Y-%m-%d %I:%M:%S';


//giornaliera
if($scope.selectedTimeBase ==='0') 
	{
	if($scope.selectedGranularity === '0')
		{
		tickForm = '%H:%M:%S';
		tooltipForm = '%H:%M:%S';
		xForm = '%H:%M:%S';
		}
	if($scope.selectedGranularity === '1')
		{
		tickForm = '%H:00:00';
		tooltipForm = '%H:00:00';
		xForm = '%H';
		}
	}
else if($scope.selectedTimeBase ==='1')
{
	//settimanale
	
	//timestamp
	if($scope.selectedGranularity === '0')
	{
	tickForm = '%a %H:%M:%S';
	tooltipForm = '%a %H:%M:%S';
	xForm = '%d %H:%M:%S';
	}
	
	if($scope.selectedGranularity === '1')
	{
	tickForm = '%a %H';
	tooltipForm = '%a %H';
	xForm = '%d %H';
	}
	
	if($scope.selectedGranularity === '2')
	{
	tickForm = '%a';
	tooltipForm = '%a';
	xForm = '%d';
	}
}
else if($scope.selectedTimeBase ==='2') 
{
		
	if($scope.selectedGranularity === '0')
	{
	tickForm = '%d %H:%M:%S';
	tooltipForm = '%d %H:%M:%S';
	xForm = '%d %H:%M:%S';
	}
	
	if($scope.selectedGranularity === '1')
	{
	tickForm = '%d %H:00:00';
	tooltipForm = '%d %H:00:00';
	xForm = '%d %H';
	}
	
	if($scope.selectedGranularity === '2')
	{
	tickForm = '%d 00:00:00';
	tooltipForm = '%d 00:00:00';
	xForm = '%d';
	}
	
}
else if($scope.selectedTimeBase ==='3') 
{
	if($scope.selectedGranularity === '0')
	{
	tickForm = '%b %d %H:%M:%S';
	tooltipForm = '%b %d %H:%M:%S';
	xForm = '%m %d %H:%M:%S';
	}
	
	if($scope.selectedGranularity === '1')
	{
	tickForm = '%b %d %H:00:00';
	tooltipForm = '%b %d %H:00:00';
	xForm = '%m %d %H';
	}
	
	if($scope.selectedGranularity === '2')
	{
	tickForm = '%b %d 00:00:00';
	tooltipForm = '%b %d 00:00:00';
	xForm = '%m %d';
	}
	
	if($scope.selectedGranularity === '3')
	{
	tickForm = '%b';
	tooltipForm = '%b';
	xForm = '%m';
	}
	
	if($scope.selectedGranularity === '4')
	{
	tickForm = '%Y';
	tooltipForm = '%Y';
	xForm = '%Y';
	}
}

var chart = c3.generate({
	bindto : '#g8-chart',
    data:{
    	x: 'x',
        xFormat: xForm, // 'xFormat' can be used as custom format of 'x'
        columns: rows
    },
    axis: {
        x: {
            type: 'timeseries',
            localtime: true,
            tick: {
            	fit: true,
            	rotate: 15,
                format: tickForm
            },
            height: 70
        },
        y: {
        	tick: {
        		format: function (x) { return RoundNumber(x); }
            }
        }
    },
    point: { show: false },
    legend: {
        show: true
    },
    tooltip: {
    	/*format: {
    		value: function (x) { return RoundNumber(x); }
    	}*/
    	/* GC 02/11/2015 */
    	contents: function tooltip_contents(d, defaultTitleFormat, defaultValueFormat, color) {
		    var $$ = this, config = $$.config, CLASS = $$.CLASS,
	        titleFormat = config.tooltip_format_title || defaultTitleFormat,
	        nameFormat = config.tooltip_format_name || function (name) { return name; },
	        valueFormat = config.tooltip_format_value || defaultValueFormat,
	        text, i, title, value, name, bgcolor;
	        
	       var dateFormat = d3.time.format(tooltipForm);
	      
	    for (i = 0; i < d.length; i++) {
	    	
	      
	        if (i==0) {
	            title = dateFormat(d[i].x);
	           
	            text = "<table class='" + CLASS.tooltip + "'>" + (title || title === 0 ? "<tr><th colspan='3'>" + title + "</th></tr>" : "");
	            text += "<tr>";
		        text += "<td></td>";
		        text += "<td>value</td>";
		        text += "<td>media</td>";
		        text += "</tr>";
	        }
	        
	        name = nameFormat(d[i].name);
	        bgcolor = $$.levelColor ? $$.levelColor(d[i].value) : color(d[i].id);
	        
	        var media = "0";
	        
	        for(var z = 0; z < $scope.g8Data.medie.length; z++)
        	{
        	var s = $scope.g8Data.medie[z];
        	
	        	for(var key in s)
	            {
	        		if(d[i].name.indexOf(key) > -1)
	        			{
	        			media = s[key];
	        			break;
	        			}
	            }
        	}
	        
	        text += "<tr class='" + CLASS.tooltipName + "-" + d[i].id + "'>";
	        text += "<td class='name'><span style='background-color:" + bgcolor + "'></span>" + d[i].name + "</td>";
	        text += "<td>" + RoundNumber(d[i].value) + "</td>";
	        text += "<td>" + RoundNumber(media) + "</td>";
	        text += "</tr>";
	      
	    }
	    return text + "</table>";   
	}
    },
    grid: {
        x: {
            show: true
        }
    },
    size: { 
    	height: 520
	},
	padding: {
		left: 50,
		right: 100
	}
});

});	


	}//if g8data
}//loadData

// callback for ng-click 'exportCSV':
$scope.exportCSV = function () {
$http({method: 'POST', url: '/wetnet/rest/d3/g8/csv', data: $scope.g8Data}).
success(function(data, status, headers, config) {
var element = angular.element('#exportCSV');
element.attr({
 href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
 target: '_blank',
 download: 'chart.csv'
});
}).
error(function(data, status, headers, config) {
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
if ($scope.radioModel === '1d'){
$scope.g8Data.endDate = new Date();
}
$scope.g8Data.startDate = TimeSelectorRadio($scope.radioModel, $scope.g8Data.endDate);
};


/*
* GC - 29/10/2015
*/
$scope.checkSign = function(elem){

if(elem == '0') return '+';
else if(elem == '1') return '-';
else return '*';

};

}]);
