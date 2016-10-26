'use strict';

angular.module('milkytechApp')
.filter('capitaliseFirstLetter', function() {
    return function(input){
        return input.charAt(0).toUpperCase() + input.slice(1);
    };
  });