'use strict';

/**
 * @ngdoc function
 * @name milkytechApp.controller:CalculationCtrl
 * @description
 * # CalculationCtrl
 * Controller of the milkytechApp
 */
angular.module('milkytechApp')
  .controller('CalculationCtrl', function ($scope) {
    $scope.calculations = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
