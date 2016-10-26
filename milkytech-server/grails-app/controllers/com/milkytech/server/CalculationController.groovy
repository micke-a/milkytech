package com.milkytech.server

import grails.converters.JSON
import grails.rest.RestfulController

/**
 * CalculationController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CalculationController  extends RestfulController {
    static responseFormats = ['json', 'xml']
    //static namespace = 'v1'
    
    public CalculationController(){
        super(Calculation)
    }
    
    def show(Calculation calculation) {
        
        JSON.use('deep')
        respond( calculation)
    }
}
