package com.milkytech.engine2

import com.milkytech.graph.Graph
import com.milkytech.service.MilkytechModelParser
import com.milkytech.service.MilkytechModelRunner

class MilkytechModelTest extends GroovyTestCase {
    
    MilkytechModelParser modelParser = new MilkytechModelParser()
    MilkytechModelRunner modelRunner = new MilkytechModelRunner()
    
    @Override
    protected void setUp() {
        super.setUp()
    }

    void testIt(){
        def dslScript = getClass().getResourceAsStream("CalculationOne.model").text
        
        long execCount = 1
        long totalTime = 0
        for(long i =0; i< execCount ; i++){
            long t1 = System.currentTimeMillis()
            runScript(dslScript)
            long t2 = System.currentTimeMillis()-t1
            totalTime += t2
            println("$t2 ms")
        }
        
        println("Avg time = ${totalTime/execCount}")
    }
    
    void runScript(def dslScript){
        
        
        ModelDelegate model = modelParser.parseModel(dslScript)
        
        def result = modelRunner.runModel(model,
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
    
    void testPrintVariables(){
        
        
        ModelDelegate model = modelParser.parseModel(getClass().getResourceAsStream("CalculationOne.model").text)
        model.runConfigPhase(new CalculationContext(), true, false)
        
        model.printVariableDependencies()
    }
    
    
    void testMissingRequiredInput(){
        def dslScript = getClass().getResourceAsStream("CalculationOne.model").text
        
        ModelDelegate model = modelParser.parseModel(dslScript)
        
        // Missing required 'income' input
        CalculationContext result = modelRunner.runModel(model, new CalculationContext() )
        
        assert !result.errors.empty
        println result.outputs
        model.execGraph.traverse(new CalculationContext(), Graph.PRINT )
        
        
    }
    
    void testDependsOnInput(){
                
        ModelDelegate model = modelParser.parseModel('''model(name:"Model Name", version:"1.0") {
    inputs {
        inp()
    }
    /**
     * Variables
     */
    output(name:'result'){
dependsOn('inp')
        evaluate{
            inp*2
            
        }
    }
}
''')
        
        // Missing required 'income' input
        CalculationContext result = modelRunner.runModel(model, new CalculationContext(['inp':123]) )
        
        model.execGraph.traverse( new CalculationContext(), Graph.PRINT )
        
        assert result.errors.empty
        assert result.outputs['result'] == 246
    }
    
    void testDependsHaveInputCall(){
                
        ModelDelegate model = modelParser.parseModel('''model(name:"Model Name", version:"1.0") {
    output(name:'result'){
        evaluate{
            hasInput('inp') ? inp*2 : 1
        }
    }
}
''')
        
        CalculationContext result
        result = modelRunner.runModel(model, new CalculationContext(['inp':123]) )
        assert result.outputs['result'] == 246
        
        result = CalculationEngine.process(model, new CalculationContext() )
        assert result.errors.empty
        assert result.outputs['result'] == 1
    }
    
    void testForNpeWhenRequiredInputIsMissing(){
            
        ModelDelegate model = modelParser.parseModel('''
    final int DEFAULT_M2_PER_HOUR = 100
    final int DEFAULT_COST_PER_HOUR = 8
    
    model(name:"Reg's Lawn", version:"1.0") {
        inputs {
            width()
            length()
        }
        /**
         * Functions
         */
        function(name:"area"){ arg1, arg2 ->
            arg1*arg2
        }
        
        /**
         * Variables
         */
        output(name:'result'){
            dependsOn('width','length')
            evaluate{
                m2PerHour = hasInput('m2PerHour') ? m2PerHour : DEFAULT_M2_PER_HOUR
                costPerHour = hasInput('cost') ? cost : DEFAULT_COST_PER_HOUR
                
                area(width, length)/m2PerHour*costPerHour
            }
        }
    }
        ''')
        
        CalculationContext result
        
        result = modelRunner.runModel(model, new CalculationContext(['width':10, 'length':10]) )
        assert result.outputs['result'] != null
    }
    
    
    /**
     * Don't think 
     */
    void testMandatoryAspectOfInputs(){
                
        ModelDelegate model = modelParser.parseModel('''model(name:"Model Name", version:"1.0") {
            inputs {
                inp()
            }
            /**
        * Variables
        */
            output(name:'result'){
        dependsOn('inp')
                evaluate{
                    inp*2
                    
                }
            }
        }
        ''')
        
        CalculationContext result = modelRunner.runModel(model, new CalculationContext(['inp':123]) )
        
        model.execGraph.traverse( new CalculationContext(), Graph.PRINT )
        
        assert result.errors.empty
        assert result.outputs['result'] == 246
    }
    
    void testInputIterationAndAddOutput(){
                
        ModelDelegate model = modelParser.parseModel('''model(name:"Model Name", version:"1.0") {
    inputs {
        inp1()
        inp2()
        multiplier()
    }
    /**
    * Variables
    */
    output(name:'result'){
        dependsOn('multiplier')
        evaluate{
            inputs.each{name, value ->
                if(name.startsWith('inp')){
                    addOutput("o_${name}", value*multiplier)
                }
            }
            
        }
    }
}
                ''')
        
        CalculationContext result = modelRunner.runModel(model, new CalculationContext(
            ['inp1':2,'inp2':3,multiplier:3]
            ) )
        
        model.execGraph.traverse( new CalculationContext(), Graph.PRINT )
        
        assert result.errors.empty
        assert result.outputs['o_inp1'] == 6
        assert result.outputs['o_inp2'] == 9
    }
}
