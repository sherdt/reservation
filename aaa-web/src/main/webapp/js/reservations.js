'use strict';

(function() {

	var app = angular.module('aircraftAllocationApp.reservations', []);

	app.directive("reservations", function() {
		return {
			restrict : 'E',
			templateUrl : "views/reservations.html",
			controller : function($http, $scope) {
				console.log('Initializing the reservationCtrl.');
				this.myReservations = [];
				this.otherReservations = [];
				this.allReservations = [];
				var store = this;
				
				this.deleteReservation = function(reservation){
					$http({method: 'DELETE', url: '/aaa-web/rest-api/reservation/' + reservation.id, data : reservation, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						store.listReservations();
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('Delete reservation problem', data.errorMessage);
					});
				};
				
				this.changeReservation = function(reservation, action){
					$http({method: 'PUT', url: '/aaa-web/rest-api/reservation/' + $scope.authCtrl.profile.username + '/' + action, data : reservation, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						store.listReservations();
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('Change reservation problem', data.errorMessage);
					});
				};
				
				this.listReservations = function() {
					store.myReservations = [];
					store.otherReservations = [];
					store.allReservations = [];
					
					$http.get('/aaa-web/rest-api/reservation/list-reservations')
					.success(function(data) {
						store.allReservations = data;
					}).error(function(data) {
						$scope.tabCtrl.showErrorMessage('List all reservations problem', data.errorMessage);
					});
					
					if (!$scope.authCtrl.profile || !$scope.authCtrl.profile.username || $scope.authCtrl.profile.role === 'ADMIN') {
						return;
					}
					
					$http.get('/aaa-web/rest-api/reservation/' + $scope.authCtrl.profile.username)
					.success(function(data) {
						store.myReservations = data;
					}).error(function(data) {
						$scope.tabCtrl.showErrorMessage('List my reservations problem', data.errorMessage);
					});
					
					$http.get('/aaa-web/rest-api/reservation/other-than/' + $scope.authCtrl.profile.username)
					.success(function(data) {
						store.otherReservations = data;
					}).error(function(data) {
						$scope.tabCtrl.showErrorMessage('List other reservations problem', data.errorMessage);
					});
					
					
				};
				
				// initial reservations loading.
				store.listReservations();
			},
			controllerAs : 'reservationCtrl'
		};
	});
	
	app.directive("createReservation", function() {
		return {
			restrict : 'E',
			templateUrl : "views/createReservation.html",
			controller : function($http, $scope) {
				console.log('Initializing the createReservationCtrl.');
				this.reservation = {};
				var store = this;
				store.errorMessage = '';
				
				this.createReservation = function(){
					
					$http({method: 'POST', url: '/aaa-web/rest-api/reservation/' + $scope.authCtrl.profile.username, data : store.reservation, headers : {'Content-Type' : 'application/json;charset=utf-8'}})
					.success(function(data) {
						$('#createReservationDialog').modal('hide');
						store.reservation = {};
						$scope.reservationCtrl.listReservations();
					})
					.error(function(data) {
						$scope.tabCtrl.showErrorMessage('Create reservation problem', data.errorMessage);
					});
				};
				
			},
			controllerAs : 'createReservationCtrl'
		};
	});

})();
