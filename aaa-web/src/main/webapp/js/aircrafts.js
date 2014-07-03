'use strict';

(function() {

	var app = angular.module('aircraftAllocationApp.aircrafts', []);

	app.directive("aircrafts", function() {
		return {
			restrict : 'E',
			templateUrl : "partials/aircrafts.html",
			controller : function($http) {
				this.aircrafts = [];
				var store = this;

				$http.get('/aaa-web/rest-api/aircraft/list-aircrafts').success(function(data) {
					store.aircrafts = data;
				}).error(function(data) {
					console.log(data);
				});
			},
			controllerAs : 'aircraftCtrl'
		};
	});

	app.directive('appVersion', [ 'version', function(version) {
		return function(scope, elm, attrs) {
			elm.text(version);
		};
	} ]);

})();
