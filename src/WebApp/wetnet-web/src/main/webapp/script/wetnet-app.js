"use strict";

var wetnetApp = angular.module('wetnetApp', [
                                                       'ngSanitize',
                                                       'ui.bootstrap',
                                                       'wetnetControllers',
                                                       'wetnetManagerControllers',
                                                       'wetnetMiscControllers',
                                                       'wetnetEventsControllers',
                                                       'wetnetDashboardControllers',
                                                       'wetnetAlarmsControllers',
                                                       'wetnetService',
                                                       'angularjs-dropdown-multiselect'
                                                       ])

//We already have a limitTo filter built-in to angular,
//let's make a startFrom filter
//wetnetApp.filter('startFrom', function() {
//    return function(input, start) {
//        start = +start; //parse to int
//        return input.slice(start);
//    }
//});


wetnetApp.filter('startFrom', function() {
	  return function(input, start) {
	    start = parseInt(start, 10);
	    return input.slice(start);
	  };
	});