'use strict';

/**
 * @ngdoc function
 * @name milkytechApp.controller:HeaderCtrl
 * @description
 * # HeaderCtrl
 * Controller of the milkytechApp
 */

angular.module('milkytechApp')
 .controller('HeaderCtrl', function ($scope, $location) {
       $scope.isActive = function (viewLocation) {
           return $location.path().indexOf( viewLocation) != -1;
       };

       $scope.isActiveExact = function (viewLocation) {
           return viewLocation === $location.path();
       };
 });
