'use strict';

/* Controllers */

function url_base64_decode(str) {
  var output = str.replace('-', '+').replace('_', '/');
  switch (output.length % 4) {
    case 0:
      break;
    case 2:
      output += '==';
      break;
    case 3:
      output += '=';
      break;
    default:
      throw 'Illegal base64url string!';
  }
  //polifyll for atob can be retrieved from: https://github.com/davidchambers/Base64.js
  return window.atob(output); 
}

angular.module('aircraftAllocationApp.controllers', [])
  .controller('tabCtrl', ['$http', '$scope', function($http, $scope) {
	  this.tab = 4;
	  this.errorDialogTitle = "";
	  this.errorDialogMessage = "";
	  $scope.tabCtrl = this;
	  
	  this.setTab = function(selectedTab) {
		  this.tab = selectedTab;
	  };
	  this.isSet = function(tab) {
		  return this.tab === tab;
	  };
	  
	  this.showErrorMessage = function(title, message) {
		  this.errorDialogTitle = title;
		  this.errorDialogMessage = message;
		  $('#errorDialog').modal('show');
	  };
	  
	  $http
		.get('/aaa-web/rest-api/version')
		.success(function (data, status, headers, config) {
			$scope.version = data.version;
		})
		.error(function (data, status, headers, config) {
			$scope.tabCtrl.showErrorMessage('Read application version problem', data.errorMessage);
		});
	  
  }])
  .controller('AuthenticationController', ['$http', '$scope', '$window', function($http, $scope, $window) {
	  this.profile = JSON.parse($window.sessionStorage.profile || '{}');
	  this.isAdmin = $window.sessionStorage.isAdmin || false;
	  this.sessionStorage = $window.sessionStorage;

	  $scope.profile = this.profile;
	  $scope.authCtrl = this;
	  $scope.credentials = {};
	  $scope.credentialsChange = {};

	  $window.sessionStorage.message = '';
	  
	  this.changePassword = function() {
		  
		  $scope.credentialsChange.username = $scope.authCtrl.profile.username;
		  delete $scope.credentialsChange.confirmPassword;
		  
		  $http
			.post('/aaa-web/rest-api/authentication/changePassword', $scope.credentialsChange)
			.success(function (data, status, headers, config) {
				$('#changePasswordDialog').modal('hide');
			})
			.error(function (data, status, headers, config) {
				
				// handle change password errors here
				$window.sessionStorage.message = data;
				$scope.credentialsChange = {};
				$scope.tabCtrl.showErrorMessage('Change password problem', data.errorMessage);
			});
	  };
	  
	  this.login = function() {
		  $http
			.post('/aaa-web/rest-api/authentication/login', $scope.credentials)
			.success(function (data, status, headers, config) {
				$window.sessionStorage.token = data;
				$window.sessionStorage.isAuthenticated = true;
				$window.sessionStorage.profile = url_base64_decode(data.split('.')[1]);
		
				$scope.authCtrl.profile = JSON.parse($window.sessionStorage.profile);
				$window.sessionStorage.isAdmin = $scope.authCtrl.isAdmin = $scope.authCtrl.profile.role === 'ADMIN';
				
				$('#loginDialog').modal('hide');
			})
			.error(function (data, status, headers, config) {
				// Erase the token if the user fails to log in
				delete $window.sessionStorage.token;
				delete $window.sessionStorage.profile;
				$window.sessionStorage.isAuthenticated = false;
				
				// Handle login errors here
				$window.sessionStorage.message = 'Invalid username or password, please provide correct credentials';
			});
	  };
	  
	  this.logout = function() {
		  	this.isAdmin = false;
		  	$window.sessionStorage.isAdmin = false;
			$window.sessionStorage.message = '';
			$window.sessionStorage.isAuthenticated = false;
			$window.sessionStorage.profile = '{}';
			delete $window.sessionStorage.token;
	  };
  }])
  .factory('authInterceptor', function ($q, $window) {
	  return {
	    request: function (config) {
			config.headers = config.headers || {};
			if ($window.sessionStorage.token) {
				config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
			}
			return config;
	    },
	    responseError: function (data) {
			if (data.status === 401) {
				// handle the case where the user is not authenticated
				console.log(data.data.errorMessage);
			}
			return $q.reject(data);
	    }
	  };
  })
  .config(function ($httpProvider) {
	  $httpProvider.interceptors.push('authInterceptor');
  })
;
