'use strict';

/**
 * @ngdoc function
 * @name milkytechApp.controller:PlaygroundCtrl
 * @description
 * # PlaygroundCtrl
 * Controller of the milkytechApp
 */
angular.module('milkytechApp')
   .controller('PlaygroundCtrl',['$scope', '$location', '$routeParams', '$http', '$modal', 'calculatorService', 
           function ($scope, $location, $routeParams, $http, $modal, calculatorService) {
     
       $scope.calcId = $routeParams.calcId;
       
       $http({
           method: 'GET',
           url: 'http://localhost:8080/milkytech-server/calculator/details/'+$scope.calcId +'?format=json'
       }).
       success ( function(data, status, headers, config){
           $scope.view = data.view;
           $scope.modelText = data.modelText;
           $scope.inputsText = data.inputsText;
           $scope.calculator = data.calculator;
       }).
       error(function(data, status, headers, config){
           
       });
       
       $scope.calculate = function(){
           calculatorService.calculateForPlayground(
                   $scope.inputsText,
                   $scope.modelText,
                   function(payload){
                       var template = Handlebars.compile($scope.view.markup);
                       $('#calcOutput').html(
                               template(payload.data)
                               );
                   }
                   );
       };
       
       $scope.saveModel = function(){
           calculatorService.saveModel(
                   {
                   id: $scope.calcId,
                   name: $scope.calculator.name,
                   type: $scope.calculator.type,
                   subType: $scope.calculator.subType,
                   modelCode: $scope.modelText,
                   modelPresentation: $scope.view.markup
                   }
                   );
      };
       
       $scope.saveAsNewModel = function(){
           calculatorService.saveModelAsNew(
                   {
                   name: $scope.calculator.name,
                   type: $scope.calculator.type,
                   subType: $scope.calculator.subType,
                   modelCode: $scope.modelText,
                   modelPresentation: $scope.view.markup
                   }
                   );
       };
       
   }]);
