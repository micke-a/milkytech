'use strict';

/**
 * @ngdoc function
 * @name milkytechApp.controller:CalcInputsCtrl
 * @description
 * # CalcInputsCtrl
 * Controller of the milkytechApp
 */
angular.module('milkytechApp')
  .controller('CalcInputsCtrl',['$scope','$routeParams', '$http','$modal',
      function ($scope, $routeParams, $http, $modal) {
          
          $scope.calcId = $routeParams.calcId;
          
          $scope.formData = {
                  'id' : $scope.calcId
          }; 
          
          $http({
              method: 'GET',
              url: 'http://localhost:8080/milkytech-server/calculator/describe/' +$routeParams.calcId +'?format=json'
          }).
          success ( function(data, status, headers, config){
              $scope.calcInputs = data;
              
          }).
          error(function(data, status, headers, config){
              
          });
          
          
          $scope.calculate = function(){
            
            $http({
                method: 'GET',
                url: 'http://localhost:8080/milkytech-server/calculate/calculate/' 
                    +$routeParams.calcId 
                    +'?format=json&inputs='
                    +angular.toJson($scope.formData)
                //,data: $.param($scope.formData)
            }).
            success ( function(data, status, headers, config){
                $scope.calcResult = data;
                
                var modalInstance = $modal.open({
                    templateUrl: 'views/modals/modal-calc-results.html',
                    controller: 'CalcResultModalCtrl',
                    windowClass: 'app-modal-window',
                    size: 'lg',
                    resolve : {
                        results : function(){ return $scope.calcResult; },
                        title : function(){ return 'Results for Calculator with ID '+$scope.calcId; }
                    }
                  });
            }).
            error(function(data, status, headers, config){
                console.log("Error: " +data);
            });
          };
      }
  ]);
