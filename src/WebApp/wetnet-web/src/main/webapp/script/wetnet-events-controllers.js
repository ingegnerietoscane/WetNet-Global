"use strict";

var wetnetEventsControllers = angular.module('wetnetEventsControllers', []);

wetnetEventsControllers.controller("WetnetControllerEvents", [ '$scope', '$http', 'Districts', 'Events', 'TimeSelectorRadio',
                                                         	function($scope, $http, Districts, Events, TimeSelectorRadio) {
	$scope.config = new Object();

	// carico i dati utlizzati per la ricerca
	$scope.districts = Districts.getData();

	// inizializzazioni
	$scope.formBean = new Object();
	$scope.formBean.startDate = new Date();
	$scope.formBean.endDate = new Date();
	$scope.formBean.endDate.setDate($scope.formBean.endDate.getDate() - 1);
	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 1);
	$scope.eventsSize = -1;
	$scope.orderByField = '-day';

	// carico gli eventi
	$scope.events = Events.getEvents($scope.formBean);

	$scope.districtSelectedShow = function($item, $model, $label) {
		$scope.formBean.districtsSelected = $model;
	}

	// callback for ng-click 'loadData':
	$scope.loadData = function() {
		if ($scope.formBean.startDate != undefined && $scope.formBean.endDate != undefined) {
			$scope.orderByField = '-day';
			Events.getEvents($scope.formBean, function(data) {
				$scope.events = data;
				$scope.eventsSize = data.length;
			});
		}
	}

	// callback for ng-click 'exportCSV':
	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/events/csv', data: $scope.events}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'events-list.csv'
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

	$scope.updateDate = function() {
		if ($scope.radioModel === '1d'){
    		$scope.formBean.endDate = new Date();
    	}
		$scope.formBean.startDate = TimeSelectorRadio($scope.radioModel, $scope.formBean.endDate);
	}

	$scope.removeDistrict = function() {
		$scope.formBean.districtsSelected = null;
	}
	
	//ordina la lista degli eventi al click
	$scope.sortEventsList = function(fieldToSortBy){
		var order = ($scope.orderByField.charAt(0) == '+') ? '-' : '+';
		$scope.orderByField = order + fieldToSortBy;
	}
	
}]);


wetnetEventsControllers.controller("WetnetControllerEventsG1", ['$scope', '$http', 'Districts', 'EventsScatterChart', 'TimeSelectorRadio', 'Zone', 'Municipality',
                                    						function($scope, $http, Districts, EventsScatterChart, TimeSelectorRadio, Zone, Municipality) {
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
	$scope.formBean.endDate.setDate($scope.formBean.endDate.getDate() - 1);
	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 1);
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
			EventsScatterChart.getData($scope.formBean, function(data) {
				$scope.columns = data.columns;
				var rows = angular.fromJson(data.columns);
				var labels = angular.fromJson(data.groups);
				var pattern = angular.fromJson(data.pattern);
				$scope.columnsSize = rows[0].length - 1;
				if ($scope.columnsSize > 0) {
		  		var chart = c3.generate({
		  			bindto : '#scatter-chart',
		  		    data: {
		  		    	x: rows[0][0],
		  		    	xFormat: '%Y-%m-%d',
		  		        columns: rows,
		  		        type: 'bar',
		  		        groups: labels
		  		    },
		  		    bar: {
		  		        width: 10
		  		    },
			  		axis: {
					    x: {
					    	type: 'timeseries',
					    	tick: {
				            	fit: true,
				            	rotate: 15,
				                format: '%a %Y-%m-%d'
				            },
				            height: 70
				        },
				        y: {
				            show: false,
				            min: -3,
				            max: 6
				        }
				    },
					grid : {
						x : {
							show : true
						}
					},
				    tooltip: {
				    	format: {
				    		value: function (x) { return ""; }
				    	}
				    },
				    size: { 
				    	height: 320
			    	},
					padding: {
						left: 50,
						right: 100
					},
					color: {
				        pattern: pattern
				    }
		  		});
				}
			});
		}
	}
	
	// callback for ng-click 'exportCSV':
	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/eventsG1/csv', data: $scope.columns}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart-events1.csv'
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


wetnetEventsControllers.controller("WetnetControllerEventsGantt", ['$scope', '$http', 'Districts', 'EventsGanttChart', 'TimeSelectorRadio', 'Zone', 'Municipality',
                                        						function($scope, $http, Districts, EventsGanttChart, TimeSelectorRadio, Zone, Municipality) {
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
    	$scope.formBean.endDate.setDate($scope.formBean.endDate.getDate() - 1);
    	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 1);
    	$scope.tasks = [];
    	$scope.tasksSize = -1;

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
    			EventsGanttChart.getData($scope.formBean, function(data) {
    				$scope.columns = data.tasks;
    				$scope.tasks = data.tasks;
    				var tasks = angular.fromJson(data.tasks);
    				var taskNames = angular.fromJson(data.taskNames);
    				
    				$scope.tasksSize = tasks.length;
    				if ($scope.tasksSize > 0) {
	    				
	    				var taskStatus = {
	    					"1" : "bar-1",
	    					"2" : "bar-2",
	    					"3" : "bar-3",
	    					"4" : "bar-4",
	    					"5" : "bar-5"
	    				};
    					
    					tasks.sort(function(a, b) {
    						return a.endDate - b.endDate;
    					});
    					var maxDate = tasks[tasks.length - 1].endDate;
    					tasks.sort(function(a, b) {
    						return a.startDate - b.startDate;
    					});
    					var minDate = tasks[0].startDate;

    					var format = "%b %d";

    					var gantt = d3.gantt('c-gantt').taskTypes(taskNames).taskStatus(taskStatus).tickFormat(format).width(1100).height(500);
    					gantt(tasks);
    				}
    			});
    		}
    	}
    	
    	// callback for ng-click 'exportCSV':
    	$scope.exportCSV = function () {
    		$http({method: 'POST', url: '/wetnet/rest/d3/eventsGantt/csv', data: $scope.tasks}).
    		  success(function(data, status, headers, config) {
    		     var element = angular.element('#exportCSV');
    		     element.attr({
    		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
    		         target: '_blank',
    		         download: 'chart-events-gantt.csv'
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

wetnetEventsControllers.controller("WetnetControllerEventsG2", ['$scope', '$http', 'Districts', 'EventsCatChart', 'TimeSelectorRadio', 'Zone', 'Municipality',
                                  								function($scope, $http, Districts, EventsCatChart, TimeSelectorRadio, Zone, Municipality) {
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
  	$scope.formBean.endDate.setDate($scope.formBean.endDate.getDate() - 1);
  	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 1);
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
  			EventsCatChart.getData($scope.formBean, function(data) {
  				$scope.columns = data.columns;
  				var rows = angular.fromJson(data.columns);
  				var labels = angular.fromJson(data.groups);
  				var pattern = angular.fromJson(data.pattern);
  				$scope.columnsSize = rows[0].length - 1;
  				if ($scope.columnsSize > 0) {
  					var chart = c3.generate({
  		    			bindto : '#cat-chart',
  					    data: {
  					        x : rows[0][0],
  					        columns: rows,
  					        groups: labels,
  					        type: 'bar'
  					    },
  					    bar: {
  			  		        width: 15
  			  		    },
  					    axis: {
  					        x: {
  					            type: 'category',
  					            tick: {
  					            	fit: true,
  					            	rotate: 75
  					            },
  					            height: 190
  					        }
  					    },
  					    size: { 
  					    	height: 520
  				    	},
  						padding: {
  							left: 50,
  							right: 100
						},
						color: {
					        pattern: pattern
					    }
  					});
  				}
  			});
  		}
  	}
  	
  	// callback for ng-click 'exportCSV':
	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/eventsG2/csv', data: $scope.columns}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart-events2.csv'
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


wetnetEventsControllers.controller("WetnetControllerEventsG3", ['$scope', '$http', 'Districts', 'EventsPieChart', 'TimeSelectorRadio', 'Zone', 'Municipality',
                                  						function($scope, $http, Districts, EventsPieChart, TimeSelectorRadio, Zone, Municipality) {
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
  	$scope.formBean.endDate.setDate($scope.formBean.endDate.getDate() - 1);
  	$scope.formBean.startDate.setDate($scope.formBean.endDate.getDate() - 1);
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
  			EventsPieChart.getData($scope.formBean, function(data) {
  				$scope.columns = data.columns;
  				var rows = angular.fromJson(data.columns);
  				var pattern = angular.fromJson(data.pattern);
  				$scope.columnsSize = rows.length;
  				if ($scope.columnsSize > 0) {
  					var chart = c3.generate({
  						bindto : '#pie-chart',
  						data : {
  							columns : rows,
  							type : 'pie',
  							onmouseover : function(d, i) {},
  							onmouseout : function(d, i) {}
  						},
  						legend : {
  							position : 'right'
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
					        pattern: pattern
					    }
  					});
  				}
  			});
  		}
  	}

  	// callback for ng-click 'exportCSV':
	$scope.exportCSV = function () {
		$http({method: 'POST', url: '/wetnet/rest/d3/eventsG3/csv', data: $scope.columns}).
		  success(function(data, status, headers, config) {
		     var element = angular.element('#exportCSV');
		     element.attr({
		         href: 'data:attachment/csv;charset=utf-8,' + encodeURI(data),
		         target: '_blank',
		         download: 'chart-events3.csv'
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

