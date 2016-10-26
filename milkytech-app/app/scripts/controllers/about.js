'use strict';

/**
 * @ngdoc function
 * @name milkytechApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the milkytechApp
 */
angular.module('milkytechApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
