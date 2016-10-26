package com.milkytech.engine2

import groovy.lang.Closure;
import java.util.List;

class RangeLookupDelegate {
    String name
    def calcContext
    
    def range(Map map){
        def rls = calcContext.rangeLookups[name]
        if(!rls){
            rls = new RangeLookupList()
            calcContext.rangeLookups[name] = rls
        }
        
        rls.rangeLookups << new RangeLookup(map)
    }
    
    
    class RangeLookup{
        Long max
        Object value
    }
    
    class RangeLookupList{
        List rangeLookups = []
        
        def lookup(def value){
            
            for(def rl in rangeLookups){
                if(value < rl.max){
                    return rl.value
                }
            }
            
            return null
        }
    }
}
