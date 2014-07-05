'use strict';

(function() {

	var app = angular.module('aircraftAllocationApp.pilots', []);

	app.directive("pilots", function() {
		return {
			restrict : 'E',
			templateUrl : "views/pilots.html",
			controller : function($http) {
				console.log('Initializing the pilotCtrl.');
				this.pilots = [];
				var store = this;
				
				this.deletePilot = function(pilot){
					$http({method: 'DELETE', url: '/aaa-web/rest-api/pilot/', data : pilot, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						store.listPilots();
					})
					.error(function(data) {
						console.log(data);
					});
				};
				
				this.listPilots = function() {
					$http({method: 'GET', url: '/aaa-web/rest-api/pilot/list-pilots'})
					.success(function(data) {
						store.pilots = data;
					})
					.error(function(data) {
						console.log(data);
					});	
				};
				
				
				// initial pilots loading.
				store.listPilots();
			},
			controllerAs : 'pilotCtrl'
		};
	});
	app.directive("createPilot", function() {
		return {
			restrict : 'E',
			templateUrl : "views/createPilot.html",
			controller : function($http, $scope) {
				console.log('Initializing the createPilotCtrl.');
				this.pilot = {};
				var store = this;
				store.errorMessage = '';

				this.createPilot = function(){
					$http({method: 'POST', url: '/aaa-web/rest-api/pilot/', data : store.pilot, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						$('#createPilotDialog').modal('hide');
						store.pilot = {};
						$scope.pilotCtrl.listPilots();
					})
					.error(function(data) {
						console.log(data);
						store.errorMessage = data;
					});
				};
				
			},
			controllerAs : 'createPilotCtrl'
		};
	});
	
})();
