package com.milkytech.engine2

import groovy.transform.ToString

//@ToString(includeNames=true)
class ValidationResults {
    //TODO: change from string to an object which holds information about which field the error/warning is for
    List<ValidationResult> errors = []
    List<ValidationResult> warnings = []
    
    //Hacky means of having something returned back
    Object extra
    
    ValidationResults addError(String name, String error){
        errors << new ValidationResult(name:name, description:error)
        return this
    }
    
    ValidationResults addError(ModelInput mi, String error){
        errors << new ValidationResult(name:mi.name, description:error)
        return this
    }
    
    ValidationResults addWarning(ModelInput mi, String warning){
        warnings << new ValidationResult(name:mi.name, description:warning)
        return this
    }
    ValidationResults addWarning(String  name, String warning){
        warnings << new ValidationResult(name:name, description:warning)
        return this
    }
    
    boolean hasErrors(){
        return !errors.empty
    }
    
    ValidationResults add(ValidationResults vr){
        if(vr != null){
            errors.addAll(vr.errors)
            warnings.addAll(vr.warnings)
        }
        return this
    }

}
