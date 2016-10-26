package com.milkytech.engine2

import groovy.lang.Closure;
import java.util.List;

class FunctionDelegate {
    String name
    Closure func
    
    def callFunction(CalculationContext calcContext, Object ... args){
        func.delegate = calcContext
        func.resolveStrategy = Closure.DELEGATE_FIRST
        
        def res
        switch(func.maximumNumberOfParameters){
            case 0: res = func();break;
            case 1: res = func(args[0]);break;
            case 2: res = func(args[0],args[1]);break;
            case 3: res = func(args[0],args[1],args[2]);break;
            case 4: res = func(args[0],args[1],args[2],args[3]);break;
            case 5: res = func(args[0],args[1],args[2],args[3],args[4]);break;
            case 6: res = func(args[0],args[1],args[2],args[3],args[4],args[5]);break;
            default: throw new RuntimeException("To many parameters Closure = $func.maximumNumberOfParameters , args=$args.length");
        }
        
        return res
    }
}
