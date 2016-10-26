package com.milkytech.engine2

/**
  inputs {
   variable1(
       type : String,
       nullable : true,
       min : 1,
       max : 5,
       validator : {...},
       mandatory : { ... }
   )
   variable2(nullable:true, type: String)
   variable3(nullable:true, type: String, mandatory : { inputs, value -> inputs.variable2 != null}, validator : {inputs, value -> inputs.variable2 != val } )
   
  }
 * @author User
 *
 */
class InputsDelegate {
    
    Map<String,ModelInput> inputs = new TreeMap()
    
    def methodMissing(String name, args){
        ModelInput mi 
        if(args){
            mi = new ModelInput(args[0])
        }
        else{
            mi = new ModelInput()
        }
        mi.name = name
        
        inputs.put(name, mi)
    }

    
}
