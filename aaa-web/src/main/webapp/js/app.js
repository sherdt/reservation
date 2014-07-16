'use strict';


// Declare app level module which depends on filters, and services
angular.module('aircraftAllocationApp', [
  'ui.bootstrap.datetimepicker',
  'aircraftAllocationApp.aircraftTypes',
  'aircraftAllocationApp.aircrafts',
  'aircraftAllocationApp.pilots',
  'aircraftAllocationApp.reservations',
  'aircraftAllocationApp.controllers'
]).filter("asDate", function () {
    return function (input) {
        return new Date(input);
    };
});
