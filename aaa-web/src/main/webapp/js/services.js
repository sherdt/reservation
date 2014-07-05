'use strict';

/* Services */

var baseUrl = '/aaa-web/rest-api/';

angular.module('aircraftAllocationApp')
.factory('aircraftTypeService', [ '$resource', function($resource) {
	return $resource(baseUrl + '/aircraft-type/:userId', {
		userId : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});
} ]);