'use strict';


// Declare app level module which depends on filters, and services
angular.module('aircraftAllocationApp', [
  'ngRoute',
  'aircraftAllocationApp.filters',
  'aircraftAllocationApp.services',
  'aircraftAllocationApp.aircrafts',
  'aircraftAllocationApp.pilots',
  'aircraftAllocationApp.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view2', {templateUrl: 'partials/partial2.html', controller: 'MyCtrl2'});
  $routeProvider.otherwise({redirectTo: '/view2'});
}]);
