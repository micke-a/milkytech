'use strict';

/**
 * @ngdoc function
 * @name milkytechApp.controller:CalculatorCtrl
 * @description
 * # CalculatorCtrl
 * Controller of the milkytechApp
 */
angular.module('milkytechApp')
   .controller('CalculatorCtrl', function ($scope, $location, $http) {
     
       $http({
           method: 'GET',
           url: 'http://localhost:8080/milkytech-server/calculator/list/?format=json'
       }).
       success ( function(data, status, headers, config){
           $scope.calculators = data;
       }).
       error(function(data, status, headers, config){
           
       });
   });
