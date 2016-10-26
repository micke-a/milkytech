package com.milkytech.engine2

import java.util.logging.Level
import java.util.logging.Logger


class ModelInputs extends AbstractMap<String, ModelInput>{
    static final Logger logger = Logger.getLogger(ModelInputs.class.name)
    
    ModelInputValidator modelInputValidator = new ModelInputValidator()
    
    Map<String, ModelInput> modelInputs
    
    ModelInputs(){
        modelInputs = new TreeMap()
    }
    ModelInputs(Map inputs){
        modelInputs = new TreeMap(inputs)
    }
    
    Set<Map.Entry<String, ModelInput>> entrySet(){
        modelInputs?.entrySet()
    }
    
    ModelInput put(String key, ModelInput value){
        modelInputs.put(key, value)
    }
    
    Object coerce(ModelInput mi, Object value){
        if(mi.type){
            switch (mi.type) {
                case Integer : return value as Integer
                case Double : return value as Double
                case BigDecimal : return value as BigDecimal
                case Float : return value as Float
                case Long : return value as Long
                case String : return value as String
                default :
                    logger.log(Level.SEVERE, "Uknown type $mi?.type for model input $mi")
                    throw new RuntimeException("Uknown type $mi?.type for model input $mi")
            }
        }
        else{
            //No type information
            logger.log(Level.WARNING, "Can not coerce input to type, no type specified for input $mi . Keeping  it as is ${value?.class.name}" )
            return value
        }
    }
    
    Map coerceMap(Map rawInputs){
        
        Map coerced = new TreeMap()
        rawInputs.each{key, value ->
            if(containsKey(key)){
                coerced.put(key, coerce(get(key), value))
            }
            else{
                //Not a defined input, keep for now. Perhaps later remove....
                logger.log(Level.WARNING, "Unspecified input $key , keeping it as is for now" )
                coerced.put(key, value)
            }
        }
        
        return coerced
        
    }
    
    ValidationResults validate(Map rawInputs){
        ValidationResults retVal = new ValidationResults()

        for(ModelInput mi : this.modelInputs.values()){
            
            String rawValue = rawInputs[mi.name]
            
            // if we have a defined model input for the actual input
            ValidationResults tmpRes = null
            try{
                // coerce the raw value to its typed value if we have a value
                def coercedValue = rawValue != null ? coerce(mi,rawValue) : rawValue
                tmpRes = modelInputValidator.validate(mi, rawInputs, coercedValue)
                
            }
            catch(Exception e){
                logger.log(Level.SEVERE,"Error during validation for variable ${mi.name}, ${e.class.name} - ${e.message}")
                retVal.addError(mi, "${e.class.name} - ${e.message}")
            }
            
            retVal.add(tmpRes)
        }
        
        Set<String> unmodelledInputs = rawInputs.keySet() - modelInputs.keySet()
        for(String ui : unmodelledInputs){
            retVal.addWarning(ui, "User Input without a defined model input: $ui")
            logger.log(Level.INFO, "User Input without a defined model input: $ui")
        }
        
        return retVal
    }
    
}
