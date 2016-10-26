package com.milkytech.engine2

import com.milkytech.graph.Graph

class CalculationEngineInputsTest extends GroovyTestCase {
    
    @Override
    protected void setUp() {
        super.setUp()
    }
    
    void runScript(def dslScript){
        
        
        ModelDelegate model = CalculationEngine.parseModelScript(dslScript)
        
        def result = CalculationEngine.process(model,
            new CalculationContext([
                income :100,
                expenses: 101
            ])
        )
        
        println result.dump()
        println "Errors:"
        result.errors.each{
            println it
        }
        println "Outputs (variables):"
        result.outputs.each{
            println "$it.key = $it.value"
        }
    }
    
    void testInputs(){
                
        ModelDelegate model = CalculationEngine.parseModelScript('''model(name:"Model Name", version:"1.0") {
    inputs {
        variable1(mandatory:true, type: Integer, min: 0, max:10)
        variable2(mandatory:true, type: Integer, min: 0, max:10)
    }
    /**
     * Variables
     */
    output(name:'result'){
dependsOn('variable1')
        evaluate{
            variable1*2
            
        }
    }
}
''')
        
        // Missing required 'income' input
        CalculationContext result = CalculationEngine.process(model, new CalculationContext(['variable1':123, 'variable2':2]) )
        
        model.execGraph.traverse( new CalculationContext(), Graph.PRINT )
        
        assert result.errors.empty
        assert result.outputs['result'] == 246
    }
}
