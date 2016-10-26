'use strict';

/**
 * @ngdoc function
 * @name milkytechApp.controller:CalcResultModalCtrl
 * @description
 * # CalcResultModalCtrl
 * Controller of the milkytechApp
 */
angular.module('milkytechApp')
  .controller('CalcResultModalCtrl', function ($scope, $modalInstance, results, title) {
      
      $scope.results = results;
      $scope.title = title;
  });
