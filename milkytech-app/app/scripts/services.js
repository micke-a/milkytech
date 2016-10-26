'use strict';

angular.module('milkytechApp')
.factory('calculatorService',['$http', '$modal', function($http, $modal) {

    var theService = {};
    
    theService.saveModelInternal = function(endPointUrl, data){
        
        if(data.format == null){
            data.format = 'json';
        }
        
        $http({
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            url: endPointUrl
            ,data: $.param(data)
        }).
        success ( function(data, status, headers, config){
            var modalInstance = $modal.open({
                template: data.message,
                size: 'sm'
              });
        }).
        error(function(data, status, headers, config){
            console.log("Error: " +data);
            var modalInstance = $modal.open({
                template: data,
                size: 'sm'
              });
        });
        
    };
    
    
    theService.saveModel = function(data){
        console.log("calculatorService saveModel");
        
        this.saveModelInternal(
                'http://localhost:8080/milkytech-server/calculator/save/',
                data);
    };

    theService.saveModelAsNew = function(data){
        console.log("calculatorService saveModelAsNew");
        
        if(data.id != null){
            delete data[id];
        }
        
        this.saveModelInternal(
                'http://localhost:8080/milkytech-server/calculator/create/',
                data);
    };
    
    theService.calculateInternal = function(calcId, inputs, callback){
        
        var promise = $http({
            method: 'GET',
            url: 'http://localhost:8080/milkytech-server/calculate/calculate/' 
                +calcId 
                +'?format=json&inputs='
                +inputs
            //,data: $.param($scope.formData)
        });
        
        return promise;
    };
        
    theService.calculate = function(calcId, inputs){
        
        var promise = this.calculateInternal(calcId, inputs);
        promise.then(function(payload){
            var modalInstance = $modal.open({
                templateUrl: 'views/modals/modal-calc-results.html',
                controller: 'CalcResultModalCtrl',
                windowClass: 'app-modal-window',
                size: 'lg',
                resolve : { 
                    results : function(){ return payload.data; },
                    title : function(){ return 'Results for Calculator with ID '+calcId; }
                }
              });
        });
    };
    
    theService.calculate2 = function(calcId, inputs, callback){
        
        var promise = this.calculateInternal(calcId, inputs);
        promise.then(callback);
    };
    
    theService.calculateForPlayground = function(inputs, modelCode, callback){
        
        var data = {
                format : 'json',
                inputs : inputs,
                modelCode: modelCode
            };
        
        var promise = $http({
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            url: 'http://localhost:8080/milkytech-server/calculate/playground'
            ,data: $.param(data)
        });
        promise.then(callback);
    };
    
    return theService;
}])
.factory('calculationService',['$http', '$modal', function($http, $modal) {

    var theService = {};
    
    theService.list = function(){
        
    };
    
    return theService;
}]);