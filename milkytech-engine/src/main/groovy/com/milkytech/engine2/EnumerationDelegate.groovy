package com.milkytech.engine2

class EnumerationDelegate {

    def calcContext
    String name
    
    
    def value(Map m){
        if(!calcContext.enumerations[name]){
            calcContext.enumerations[name] = []
        }
        calcContext.enumerations[name] << m
    }
}
