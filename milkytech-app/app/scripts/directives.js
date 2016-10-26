'use strict';

angular.module('milkytechApp')
.directive('calculatorResults', function() {
    return {
        restrict: 'E',
        scope: {
            results: '='
        },
        
        templateUrl: 'views/directives/calc-results.html'
    };
  })
.directive('calculatorInputs', function() {
    return {
        restrict: 'E',
        scope: {
            inputs: '='
        },
        templateUrl: 'views/directives/calc-inputs.html'
    };
  });