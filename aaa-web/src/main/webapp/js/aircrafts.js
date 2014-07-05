'use strict';

(function() {

	var app = angular.module('aircraftAllocationApp.aircrafts', []);

	app.directive("aircrafts", function() {
		return {
			restrict : 'E',
			templateUrl : "views/aircrafts.html",
			controller : function($http, $window) {
				console.log('Initializing the aircraftCtrl.');
				this.aircrafts = [];
				this.aircraftTypes = $window.aircraftTypes;
				var store = this;
				
				this.updateAircraftTypes = function() {
					this.aircraftTypes = $window.aircraftTypes;
				};

				this.deleteAircraft = function(aircraft){
					$http({method: 'DELETE', url: '/aaa-web/rest-api/aircraft/', data : aircraft, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						store.listAircrafts();
					})
					.error(function(data) {
						console.log(data);
					});
				};
				
				this.listAircrafts = function() {
					$http.get('/aaa-web/rest-api/aircraft/list-aircrafts').success(function(data) {
						store.aircrafts = data;
					}).error(function(data) {
						console.log(data);
					});
				};
				
				// initial aircrafts loading.
				store.listAircrafts();
			},
			controllerAs : 'aircraftCtrl'
		};
	});
	
	app.directive("createAircraft", function() {
		return {
			restrict : 'E',
			templateUrl : "views/createAircraft.html",
			controller : function($http, $scope, $window) {
				console.log('Initializing the createAircraftCtrl.');
				this.aircraft = {};
				var store = this;
				store.errorMessage = '';
				
				this.createAircraft = function(){
					$http({method: 'POST', url: '/aaa-web/rest-api/aircraft/', data : store.aircraft, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						$('#createAircraftDialog').modal('hide');
						store.aircraft = {};
						$scope.aircraftCtrl.listAircrafts();
					})
					.error(function(data) {
						console.log(data);
						store.errorMessage = data;
					});
				};
				
			},
			controllerAs : 'createAircraftCtrl'
		};
	});

})();
