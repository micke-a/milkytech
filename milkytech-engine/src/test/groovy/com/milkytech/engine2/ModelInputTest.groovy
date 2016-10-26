package com.milkytech.engine2

import org.junit.Test

class ModelInputTest {
    
    @Test
    void defaultMandatoryNessOfInput(){
        ModelInput mi  = new ModelInput(name:'name')
        
        assert mi.mandatory
        assert mi.mandatory instanceof Boolean
    }
    
    @Test
    void notMandatory(){
        ModelInput mi  = new ModelInput(name:'name', mandatory: {false})
        assert mi.mandatory == false
        
        mi  = new ModelInput(name:'name', mandatory: false)
        assert mi.mandatory == false
    }
    
    @Test
    void notConditionalMandatoryness(){
        //only mandatory if there is an input variable 'var1' with a value greater than 100
        ModelInput mi  = new ModelInput(name:'name', mandatory: { inpMap -> inpMap?.var1 > 100 })
        
        assert mi.isMandatory(['var1': 10]) == false
        assert mi.isMandatory(['var2':123]) == false
        assert mi.isMandatory(['var1':123]) == true
    }
    
    @Test
    void validator(){
        
        ModelInput mi  = new ModelInput(name:'name', mandatory: {false},
            validator: {Map allInputs, value ->
                if(allInputs?.var1 > 100){
                    return value >= allInputs?.var1*2
                } 
                return true
            }
            )
        
        assert mi.validator([var1:100], 10) == true
        assert mi.validator([var1:101], 10) == false
        assert mi.validator([var1:101], 202) == true
    }

}
