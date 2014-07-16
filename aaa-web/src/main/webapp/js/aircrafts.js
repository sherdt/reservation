'use strict';

(function() {

	var app = angular.module('aircraftAllocationApp.aircrafts', []);

	app.directive("aircrafts", function() {
		return {
			restrict : 'E',
			templateUrl : "views/aircrafts.html",
			controller : function($http, $scope) {
				console.log('Initializing the aircraftCtrl.');
				this.aircrafts = [];
				var store = this;
				
				this.deleteAircraft = function(aircraft){
					$http({method: 'DELETE', url: '/aaa-web/rest-api/aircraft/', data : aircraft, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						store.listAircrafts();
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('Delete aircrafts problem', data.errorMessage);
					});
				};
				
				this.listAircrafts = function() {
					$http.get('/aaa-web/rest-api/aircraft/list-aircrafts').success(function(data) {
						store.aircrafts = data;
					}).error(function(data) {
						$scope.tabCtrl.showErrorMessage('List aircrafts problem', data.errorMessage);
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
			controller : function($http, $scope) {
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
						$scope.tabCtrl.showErrorMessage('Create aircraft problem', data.errorMessage);
					});
				};
				
			},
			controllerAs : 'createAircraftCtrl'
		};
	});

})();
