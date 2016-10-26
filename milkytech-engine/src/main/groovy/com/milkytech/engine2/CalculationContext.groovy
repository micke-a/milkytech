package com.milkytech.engine2

import com.milkytech.RefDataService
import java.util.Map;

class CalculationContext {

    RefDataService refData
    List errors = []
    Map inputs = [:]
    Map intermediate = [:]
    Map outputs = [:]
    
    Map<String,EnumerationDelegate> enumerations = [:]
    Map<String,RangeLookupDelegate> rangeLookups = [:]
    Map<String,FunctionDelegate> functions = [:]
    
    CalculationContext(Map inputs){
        this.inputs = inputs?: [:]
    }
    
    boolean hasInput(String inpName){
        return inputs.containsKey(inpName)
    }
    Map getInputs(){
        return Collections.unmodifiableMap(inputs)
    }

    void addOutput(String key, Object value){
        outputs.put(key, value)
    }
     
    def methodMissing(String name, args){
        
        FunctionDelegate fd = functions[name]
        if(fd){
            try{
                return fd.callFunction(this, args)
            }
            catch(Exception e){
                errors << e.message
                throw new RuntimeException(e)
            }
        }
        else{
            errors << "No function called $name"
        }
        
    }
    
    def propertyMissing(String name, value){
        if(intermediate[name]){
            println "Overriding $name with $value"
            intermediate[name] = value
        }
        else{
            intermediate[name] = value
        }
        
    }
    
    def propertyMissing(String name){
        if(outputs[name]){
            return outputs[name]
        }
        else if(inputs[name]){
            return inputs[name]
        }
        else if(intermediate[name]){
            return intermediate[name]
        }
    }
    
    String toString(){
        return this.dump()
    }
}
