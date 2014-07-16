'use strict';

(function() {

	var app = angular.module('aircraftAllocationApp.pilots', []);

	app.directive("pilots", function() {
		return {
			restrict : 'E',
			templateUrl : "views/pilots.html",
			controller : function($http, $scope) {
				console.log('Initializing the pilotCtrl.');
				this.pilots = [];
				var store = this;
				
				this.deletePilot = function(pilot){
					$http({method: 'DELETE', url: '/aaa-web/rest-api/pilot/' + pilot.username, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						store.listPilots();
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('Delete pilot problem', data.errorMessage);
					});
				};
				
				this.listPilots = function() {
					$http({method: 'GET', url: '/aaa-web/rest-api/pilot/list-pilots'})
					.success(function(data) {
						store.pilots = data;
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('List pilots problem', data.errorMessage);
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
						$scope.tabCtrl.showErrorMessage('Create pilot problem', data.errorMessage);
					});
				};
				
			},
			controllerAs : 'createPilotCtrl'
		};
	});
	app.directive("createLicense", function() {
		return {
			restrict : 'E',
			templateUrl : "views/createLicense.html",
			controller : function($http, $scope) {
				console.log('Initializing the createLicenseCtrl.');
				this.license = {};
				var store = this;
				store.errorMessage = '';
				
				this.setSelectedPilot = function(pilot, ctrl) {
					this.pilot = pilot;
				};
				
				this.createLicense = function(){
					
					$http({method: 'POST', url: '/aaa-web/rest-api/license/', data : store.license, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(createdLicense) {
						
						$http({method: 'PUT', url: '/aaa-web/rest-api/pilot/' + store.pilot.username + '/add-license/', data : createdLicense, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
						.success(function(updatedPilot) {
							$('#createLicenseDialog').modal('hide');
							store.license = {};
							$scope.pilotCtrl.listPilots();
						})
						.error(function(data) {
							$scope.tabCtrl.showErrorMessage('Create license problem', data.errorMessage);
						});
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('Create license problem', data.errorMessage);
					});
				};
				
				this.deleteLicense = function(pilot, license){
					
					$http({method: 'DELETE', url: '/aaa-web/rest-api/pilot/' + pilot.username + '/delete-license/' + license.id, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						
						$http({method: 'DELETE', url: '/aaa-web/rest-api/license/' + license.id, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
						.success(function(createdLicense) {
							store.license = {};
							$scope.pilotCtrl.listPilots();
						})
						.error(function(data) {
							$scope.tabCtrl.showErrorMessage('Delete license problem', data.errorMessage);
						});
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('Delete license problem', data.errorMessage);
					});
				};
				
			},
			controllerAs : 'createLicenseCtrl'
		};
	});
	
})();
