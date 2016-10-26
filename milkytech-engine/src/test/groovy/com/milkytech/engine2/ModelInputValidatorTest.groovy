package com.milkytech.engine2;

import static org.junit.Assert.*;

import org.junit.Test;

class ModelInputValidatorTest {

    ModelInputValidator miv = new ModelInputValidator()
    
    @Test
    public void notMandatory() {
        ModelInput mi  = new ModelInput(name:'name', mandatory: {false})
        
        assertFalse(miv.validate(mi, [:], null).hasErrors())
        
    }
    
    @Test
    public void mandatory() {
        ModelInput mi  = new ModelInput(name:'name', mandatory: {true})
        
        assertTrue(miv.validate(mi, [:], null).hasErrors())
        assertFalse(miv.validate(mi, [:], "v").hasErrors())
        
    }
   
}
