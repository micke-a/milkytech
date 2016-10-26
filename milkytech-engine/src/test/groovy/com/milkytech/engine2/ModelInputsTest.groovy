package com.milkytech.engine2

import org.junit.Test

class ModelInputsTest {
    
    def mis = [
        str : new ModelInput(name:'str'),
        integer : new ModelInput(name:'integer', type: Integer),
        'float' : new ModelInput(name:'float', type: Float),
        'double' : new ModelInput(name:'double', type: Double),
        'long' : new ModelInput(name:'long', type: Long),
        'bd' : new ModelInput(name:'bd', type: BigDecimal)
    ]
    
    @Test
    public void missingMandatoryInputValidation(){
        
        def modelInputs = new ModelInputs(mis)
        
        ValidationResults vr = modelInputs.validate([str:'a string'])
        
        assert vr.errors.find { it.name == 'integer' }
        assert vr.errors.find { it.name == 'double' }
        assert vr.errors.find { it.name == 'float' }
        assert vr.errors.find { it.name == 'bd' }
        assert vr.errors.find { it.name == 'long' }
        
        assert !vr.errors.find {it.name == 'str' }
    }
    
    @Test
    public void extraUserInputsGenerateWarningDuringValidation(){
        def modelInputs = new ModelInputs(mis)
        
        ValidationResults vr = modelInputs.validate([
            str:'a string',
            integer:'123',
            'double':'123.123',
            'float' : '123.123',
            'bd' : '123.123',
            'long' : '123',
            'micke' : 'hello'
            ])
        
        assert vr.errors.empty
        
        assert vr.warnings.find {it.name == 'micke' }
    }
    @Test
    public void coerceNormalPrimitiveWrapperClasses(){
        
        
        
        def modelInputs = new ModelInputs(mis)
        
        def coercedMap = modelInputs.coerceMap([
            str:'a string', 
            integer:'123', 
            'double':'123.123',
            'float' : '123.123',
            'bd' : '123.123',
            'long' : '123'
            ])
        
        assert coercedMap.str == 'a string'
        
        assert coercedMap.integer.class == Integer
        assert coercedMap.integer == 123i
        
        assert coercedMap.long.class == Long
        assert coercedMap.long == 123l
        
        assert coercedMap.double.class == Double
        assert coercedMap.double == 123.123d
        
        assert coercedMap.float.class == Float
        assert coercedMap.float == 123.123f
        
        assert coercedMap.bd.class == BigDecimal
        assert coercedMap.bd == 123.123
        
    }
}
