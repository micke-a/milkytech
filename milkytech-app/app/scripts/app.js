'use strict';

/**
 * @ngdoc overview
 * @name mytodo2App
 * @description
 * # mytodo2App
 *
 * Main module of the application.
 */
angular
  .module('milkytechApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.sortable',
    'LocalStorageModule',
    'ui.ace',
    'ui.bootstrap'
  ])
  .config(['localStorageServiceProvider', function(localStorageServiceProvider){
      localStorageServiceProvider.setPrefix('ls');
    }])

  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
    })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
    })
      .when('/calculations', {
        templateUrl: 'views/calculations.html',
        controller: 'CalculationCtrl'
    })
    
      .when('/calculator/list', {
        templateUrl: 'views/calculator/list.html',
        controller: 'CalculatorCtrl'
    })
    .when('/calculator/inputs/:calcId', {
        templateUrl: 'views/calculator/inputs.html',
        controller: 'CalcInputsCtrl'
      })
  .when('/calculator/playground/:calcId', {
      templateUrl: 'views/calculator/playground.html',
      controller: 'PlaygroundCtrl'
    })
  .otherwise({
    redirectTo: '/'
  });
  });
