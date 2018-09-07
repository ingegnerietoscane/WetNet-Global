'use strict';

/* Services */

var wetnetService = angular.module('wetnetService', ['ngResource']);


wetnetService.factory('DataDistricts', ['$resource',
                                 function ($resource) {
                                     return $resource('/wetnet/rest/d3/test/:id', {}, {
                                    	 getData: { method: 'GET', params: {id: '@id'}}//id = district_id_districts
                                     });
                                 }]);

wetnetService.factory('Districts', ['$resource',
                                function ($resource) {
                                    return $resource('/wetnet/rest/d3/districts', {}, {
                                   	 getData: { method: 'GET', isArray: true }
                                    });
                                }]);

/*
	 * GC - 29/10/2015
	 */
wetnetService.factory('Measures', ['$resource',
                                    function ($resource) {
                                        return $resource('/wetnet/rest/d3/measures/:districtId', {}, {
										getData: { method: 'GET', isArray: true },
                                       //	getDataByDistrictId: { method: 'GET', params: {districtId: '@districtId'}, isArray: true }
                                     	getDataByDistrictId: { method: 'GET', params: {districtId: '@districtId',withSign: '@withSign'}, isArray: true }
                                       	  });
                                    }]);

wetnetService.factory('MeasuresCRUD', ['$resource',
                                       function ($resource) {
                                           return $resource('/wetnet/rest/measures/:id', {}, {
                                               getById: { method: 'GET', params: {id: '@id'} },
                                               save: { method: 'POST' },
                                               remove: { method: 'DELETE', params: {id: '@id'} },
                                               get: { method: 'GET'}
                                           });
                                       }]);

wetnetService.factory('G2', ['$resource',
                                    function ($resource) {
                                        return $resource('/wetnet/rest/d3/g2', {}, {
                                       	 getData: { method: 'POST'}
                                        });
                                    }]);

//***RC 02/12/2015***
wetnetService.factory('G2CONF', ['$resource',
                                function ($resource) {
                                    return $resource('/wetnet/rest/d3/g2/sconf', {}, {
                                    	saveG2Configuration: { method: 'POST'},
                                    	getData: { method: 'GET', isArray: true },
                                    	remove: { method: 'DELETE', params: {parent: '@parent'}}
                                    });
                                }]);

wetnetService.factory('G2CONFPARAM', ['$resource',
                                 function ($resource) {
                                     return $resource('/wetnet/rest/d3/g2/pconf', {}, {
                                     	getData: { method: 'GET', isArray: true }
                                     });
                                 }]);

wetnetService.factory('G7CONF', ['$resource',
                                 function ($resource) {
                                     return $resource('/wetnet/rest/d3/g7/sconf', {}, {
                                     	saveG7Configuration: { method: 'POST'},
                                     	getData: { method: 'GET', isArray: true },
                                     	remove: { method: 'DELETE', params: {parent: '@parent'}}
                                     });
                                 }]);

 wetnetService.factory('G7CONFPARAM', ['$resource',
                                  function ($resource) {
                                      return $resource('/wetnet/rest/d3/g7/pconf', {}, {
                                      	getData: { method: 'GET', isArray: true }
                                      });
                                  }]);
 
 wetnetService.factory('G8CONF', ['$resource',
                                  function ($resource) {
                                      return $resource('/wetnet/rest/d3/g8/sconf', {}, {
                                      	saveG8Configuration: { method: 'POST'},
                                      	getData: { method: 'GET', isArray: true },
                                      	remove: { method: 'DELETE', params: {parent: '@parent'}}
                                      });
                                  }]);

  wetnetService.factory('G8CONFPARAM', ['$resource',
                                   function ($resource) {
                                       return $resource('/wetnet/rest/d3/g8/pconf', {}, {
                                       	getData: { method: 'GET', isArray: true }
                                       });
                                   }]);
//***END***

wetnetService.factory('G2CSV', ['$resource',
                             function ($resource) {
                                 return $resource('/wetnet/rest/d3/g2/csv', {}, {
                                	 getData: { method: 'POST'}
                                 });
                             }]);

wetnetService.factory('G4BarChart', ['$resource',
                                     function ($resource) {
                                         return $resource('/wetnet/rest/d3/g4', {}, {
                                        	 getData: { method: 'POST'}
                                         });
                                     }]);

wetnetService.factory('G5PieChart', ['$resource',
                                     function ($resource) {
                                         return $resource('/wetnet/rest/d3/g5', {}, {
                                        	 getData: { method: 'POST'}
                                         });
                                     }]);

wetnetService.factory('G6LineChart', ['$resource',
                                     function ($resource) {
                                         return $resource('/wetnet/rest/d3/g6', {}, {
                                        	 getData: { method: 'POST'}
                                         });
                                     }]);

wetnetService.factory('G7', ['$resource',
                             function ($resource) {
                                 return $resource('/wetnet/rest/d3/g7', {}, {
                                	 getData: { method: 'POST'}
                                 });
                             }]);

wetnetService.factory('Events', ['$resource',
                                   function ($resource) {
                                       return $resource('/wetnet/rest/events/data', {}, {
                                    	   getEvents: { method: 'POST', isArray: true }
                                       });
                                   }]);

wetnetService.factory('EventsPieChart', ['$resource',
                                function ($resource) {
                                    return $resource('/wetnet/rest/events/pie-chart', {}, {
                                   	 getData: { method: 'POST'}
                                    });
                                }]);

wetnetService.factory('EventsScatterChart', ['$resource',
                                         function ($resource) {
                                             return $resource('/wetnet/rest/events/scatter-chart', {}, {
                                            	 getData: { method: 'POST'}
                                             });
                                         }]);

wetnetService.factory('EventsGanttChart', ['$resource',
                                             function ($resource) {
                                                 return $resource('/wetnet/rest/events/gantt-chart', {}, {
                                                	 getData: { method: 'POST'}
                                                 });
                                             }]);

wetnetService.factory('EventsCatChart', ['$resource',
                                             function ($resource) {
                                                 return $resource('/wetnet/rest/events/cat-chart', {}, {
                                                	 getData: { method: 'POST'}
                                                 });
                                             }]);

wetnetService.factory('Zone', ['$resource',
                               function ($resource) {
                                   return $resource('/wetnet/rest/events/zone', {}, {
                                  	 getData: { method: 'GET', isArray: true }
                                   });
                               }]);

wetnetService.factory('Municipality', ['$resource',
                               function ($resource) {
                                   return $resource('/wetnet/rest/events/municipality', {}, {
                                  	 getData: { method: 'GET', isArray: true }
                                   });
                               }]);

//***RC 04/11/2015***
wetnetService.factory('WaterAuthority', ['$resource',
                                       function ($resource) {
                                           return $resource('/wetnet/rest/events/waterAuthority', {}, {
                                          	 getData: { method: 'GET', isArray: true }
                                           });
                                       }]);
//***END***

wetnetService.factory('TimeSelectorRadio', [function () {
									return function(radioModel, endDate) {
											var oneDayMillis = 1000*60*60*24;
											if (radioModel === '1d'){
												return endDate - oneDayMillis;
											} else if (radioModel === '1w'){
												return endDate - 7*oneDayMillis;
											} else if (radioModel === '1m'){
												return endDate - 30*oneDayMillis;
											} else if (radioModel === '3m'){
												return endDate - 92*oneDayMillis;
											} else if (radioModel === '6m'){
												return endDate - 183*oneDayMillis;
											} else if (radioModel === '1y'){
												return endDate - 365*oneDayMillis;
											}
									    };
                                 }]);

wetnetService.factory('RoundNumber', [function () {
									return function (number) { 
										return Math.round(number*100)/100;
									};
								 }]);

wetnetService.factory('RoundNumberToThird', [function () {
									return function (number) { 
										return Math.round(number*1000)/1000;
									};
								 }]);

/* GC 06/11/2015*/
wetnetService.factory('RoundNumberToFifth', [function () {
									return function (number) { 
										return Math.round(number*100000)/100000;
									};
 }]);


wetnetService.factory('GetUrlParameter', [function () {
									return function (sParam) {
										var sPageURL = window.location.search.substring(1);
									    var sURLVariables = sPageURL.split('&');
									    for (var i = 0; i < sURLVariables.length; i++) {
									        var sParameterName = sURLVariables[i].split('=');
									        if (sParameterName[0] == sParam) {
									            return sParameterName[1];
									        }
									    }
									};
								 }]);

wetnetService.factory('Users', ['$resource',
                                 function ($resource) {
                                     return $resource('/wetnet/rest/users/:id', {}, {
                                         getById: { method: 'GET', params: {id: '@id'} },
                                         save: { method: 'POST' },
                                         remove: { method: 'DELETE', params: {id: '@id'} },
                                         get: { method: 'GET', isArray: true }
                                     });
                                 }]);

wetnetService.factory('UsersHasDistricts', ['$resource',
                                function ($resource) {
                                    return $resource('/wetnet/rest/users/districts', {}, {
                                        getByUsersId: { method: 'GET', params: {usersid: '@usersid'}},
                                        update: { method: 'PUT' },
                                        add: { method: 'POST', params: {usersid: '@usersid', districts_id: '@districts_id'} },
                                        remove: { method: 'DELETE', params: {usersid: '@usersid', districts_id: '@districts_id'} }
                                    });
                                }]);

wetnetService.factory('DistrictsCRUD', ['$resource',
                                function ($resource) {
                                    return $resource('/wetnet/rest/districts/:id', {}, {
                                        getById: { method: 'GET', params: {id: '@id'} },
                                        save: { method: 'POST' },
                                        remove: { method: 'DELETE', params: {id: '@id'} },
                                        get: { method: 'GET'}
                                    });
                                }]);

//***RC 18/11/2015***
wetnetService.factory('FilesCRUD', ['$resource',
                                        function ($resource) {
                                            return $resource('/wetnet/rest/files/:id', {}, {
                                                getFilesById: { method: 'GET', params: {id: '@id', resourceType: '@resourceType'} },
                                            });
                                        }]);
//***END***

wetnetService.factory('MeasuresHasDistricts', ['$resource',
                                function ($resource) {
                                    return $resource('/wetnet/rest/districts/measures', {}, {
                                        getByDistrictsId: { method: 'GET', params: {districts_id: '@districts_id'}},
                                        update: { method: 'PUT' },
                                        add: { method: 'POST', params: {measures_id: '@measures_id', districts_id: '@districts_id', sign: '@sign', connections_id_odbcdsn: '@connections_id_odbcdsn'} },
                                        remove: { method: 'DELETE', params: {measures_id: '@measures_id', districts_id: '@districts_id'} }
                                    });
                                }]);



wetnetService.factory('ConnectionsCRUD', ['$resource',
                                       function ($resource) {
                                           return $resource('/wetnet/rest/connections/:id', {}, {
                                               getById: { method: 'GET', params: {id: '@id'} },
                                               save: { method: 'POST' },
                                               remove: { method: 'DELETE', params: {id: '@id'} },
                                               get: { method: 'GET'}
                                           });
                                       }]);

wetnetService.factory('UsersPreferences', ['$resource',
	                                 function ($resource) {
	                                     return $resource('/wetnet/rest/preferences', {}, {
	                                    	 getAllPrefs: { method: 'GET' },
                                     		 setAllPrefs: { method: 'POST' }
	                                     });
	                                 }]);

wetnetService.factory('Info', ['$resource',
  	                                 function ($resource) {
  	                                     return $resource('/wetnet/rest/info/version', {}, {
  	                                    	 getWetnetVersion: { method: 'GET' }
  	                                     });
  	                                 }]);


/* GC 09/11/2015*/
wetnetService.factory('Alarms', ['$resource',
                                    function ($resource) {
	 									return $resource('/wetnet/rest/alarms/data', {}, {
	 									   getData: { method: 'POST', isArray: true }
                                        });
                                    }]);

/* GC 12/11/2015*/
wetnetService.factory('DistrictsBandsHistory', ['$resource',
                                    function ($resource) {
                                        return $resource('/wetnet/rest/districtsBandsHistory/:id', {}, {
                                       	getById: { method: 'GET', params: {id: '@id'} },
                                        });
                                    }]);


/*GC 16/11/2015*/
wetnetService.factory('G8', ['$resource',
                             function ($resource) {
                                 return $resource('/wetnet/rest/d3/g8', {}, {
                                	 getData: { method: 'POST'}
                                 });
                             }]);

wetnetService.factory('G8CSV', ['$resource',
                      function ($resource) {
                          return $resource('/wetnet/rest/d3/g8/csv', {}, {
                         	 getData: { method: 'POST'}
                          });
                      }]);


wetnetService.factory('MeasuresMeterReadingCRUD', ['$resource',
                                       function ($resource) {
                                           return $resource('/wetnet/rest/measures/measuresMeterReading/:id', {}, {
                                               save: { method: 'POST' },
                                               getAll: { method: 'GET', params: {id: '@id'}, isArray: true }
                                           });
                                       }]);

