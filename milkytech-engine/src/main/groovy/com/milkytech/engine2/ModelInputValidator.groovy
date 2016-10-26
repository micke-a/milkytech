package com.milkytech.engine2

class ModelInputValidator {

    /**                                                                      
     * 
     * @param mi
     * @param allValues raw values
     * @param value coerced to its proper type
     * @return 
     */
    ValidationResults validate(ModelInput mi, Map allValues, def value){
        
        ValidationResults retVal = new ValidationResults()
        if(mi.validator && !mi.validator(allValues, value)){
            retVal.addError(mi, "Failed validator check")
        }
        
        if(mi.isMandatory(allValues) && value == null){
            retVal.addError(mi, "Failed mandatory check")
        }
        
        if(mi.range && !mi.range.contains(value)){
            retVal.addError(mi, "Failed range check $mi.range")
        }
        
        if(mi.min && mi.min > value){
            retVal.addError(mi, "Failed min check, $mi.min")
        }
        
        if(mi.max && mi.max < value){
            retVal.addError(mi, "Failed max check, $mi.max")
        }
        
        return retVal
    }
}
