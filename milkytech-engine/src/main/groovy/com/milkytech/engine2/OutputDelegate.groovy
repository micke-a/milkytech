package com.milkytech.engine2

import com.milkytech.exceptions.VariableDependencyException

class OutputDelegate implements Evaluable{

    def refDataLookupService
    String name
    //List produces = []
    List<String> _dependencies= []
    Closure evalClosure
    
    def result

//    def produces(List produces){
//        this.produces = produces
//    }
//    
    
    def dependsOn(String ... deps){
        this._dependencies.addAll( deps )
    }
    
    List<String> getDependencies(){
        return _dependencies
    }
    
    def evaluate(Closure cl){
        evalClosure = cl
    }
    
    void doEvaluate(CalculationContext calcContext) {
        //evalClosure.delegate = this
        //evalClosure.resolveStrategy = Closure.DELEGATE_FIRST
        
        if(!checkVariableDependencies(calcContext)){
            throw new VariableDependencyException("Not all variables required by $name are available")
        }
        
        
        result = CalculationEngine.with(calcContext, evalClosure)
        calcContext.outputs[name] = result
        
    }
    
    boolean checkVariableDependencies(CalculationContext calcContext){
        boolean retVal = true
        for(dep in _dependencies){
            if(!(calcContext.outputs.containsKey(dep) || calcContext.inputs.containsKey(dep))){
                calcContext.errors << "Dependency $dep for $name not present"
                retVal = false
            }
        }
        
        return retVal
    }
}
