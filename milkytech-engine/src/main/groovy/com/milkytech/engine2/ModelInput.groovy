package com.milkytech.engine2

import groovy.transform.ToString

@ToString(includeNames=true)
class ModelInput {
    public static final Closure TRUE = {true}
    public static final Closure FALSE = {false}
    Class type
    
    String name
    /***
     * Closure parameters: Map allInputs, theValue
     */
    Closure validator
    
    /**
     * Closure parameters: Map allInputs
     */
    private Closure mandatory = TRUE
    Long min
    Long max
    Range range
    String widget
    
    void setMandatory(Object value){
        if(value instanceof Boolean){
            mandatory = value ? TRUE : FALSE
        }
        else if(value instanceof Closure){
            mandatory = value
        }
        else{
            throw new IllegalArgumentException("Illegal type or value for the mandatory field. ${value} of type ${value?.class?.name}")
        }
    }
    
    Boolean isMandatory(){
        return mandatory.call()
    }
    
    Boolean isMandatory(Map inputMap){
        return mandatory.call(inputMap)
    }
    
    
}
