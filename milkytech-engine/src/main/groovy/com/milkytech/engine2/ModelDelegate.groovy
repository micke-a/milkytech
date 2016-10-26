package com.milkytech.engine2

import com.milkytech.RefDataService
import com.milkytech.exceptions.RequiredInputMissingException
import com.milkytech.graph.Graph

class ModelDelegate {
    boolean abortOnFail = false
    String name
    String version
    
    RefDataService refData = new RefDataService()
    
    List<Closure> outputs = []
    List<Closure> enumerations = []
    List<Closure> rangeLookups = []
    List<FunctionDelegate> functions = []
    List<Map> tests = []
    ModelInputs modelInputs = new ModelInputs()
    
    Graph execGraph

    boolean configPhaseExecuted

    def inputs(Closure cl){
        InputsDelegate id = new InputsDelegate()
        cl.delegate = id
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        
        cl()
        
        modelInputs = id.inputs
    }
    
    def function(Map attribs, Closure cl){
        FunctionDelegate fd = new FunctionDelegate(attribs)
        cl.delegate = fd
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        fd.func = cl
        
        functions.add(fd) 
    }
    
    def rangeLookup(String name, Closure cl){
        cl.delegate = new RangeLookupDelegate(name: name)
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        rangeLookups.add(cl)
    }
    
    def enumeration(String name, Closure cl){
        cl.delegate = new EnumerationDelegate(name: name)
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        enumerations.add(cl)
    }

    def output(Map attribs, Closure cl) {
        cl.delegate = new OutputDelegate(attribs)
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        outputs.add(cl)
    }

    /**
     * Runs 
     * <ol>
     * <li>Config phase if not run before</> 
     * <li>Processing phase</>
     * </ol>
     * 
     * @param calcContext
     * @return
     */
     boolean runModel(CalculationContext calcContext, boolean debug = false) {
         boolean allGood = true
         if(!configPhaseExecuted){
             allGood = runConfigPhase(calcContext, debug)
             
         }
         if(!allGood){
             return allGood
         }
         
         //printVariableDependencies()
         return runProcessingPhase(calcContext,debug)
     }
     
     /**
      * <ul>
      * <li>Builds up the input/output dependency graph</li>
      * <li>Sets up <strong>functions</strong> on the calculation context</li>
      * <li>Runs validations</li>
      * <li>checks that require model inputs are available</li>
      * <ul/>
      * @param calcContext
      * @params debug defaults to false
      * @params doCheckRequired check that all mandatory model inputs are available, defaults to true (TODO:change to false?)
      * @return
      */
    boolean runConfigPhase(CalculationContext calcContext, boolean debug = false, boolean doCheckRequired = true) {
        configPhaseExecuted = true
        
        
        calcContext.refData = refData
        /*
        for (Closure cl in enumerations) {
            calcContext.outputDelegates[cl.delegate.name] = cl.delegate
            cl.calcContext = calcContext
            cl()
        }
        
        for (Closure cl in rangeLookups) {
            calcContext.outputDelegates[cl.delegate.name] = cl.delegate
            cl.calcContext = calcContext
            cl()
        }
        
        */
        
        for (Closure cl in outputs) {
             //variableClosure.refDataLookup = refDataLookupService
            cl()
        }
        for(FunctionDelegate fd in functions){
            calcContext.functions[fd.name] = fd
            
        }
        
        execGraph = new Graph()
        for (Closure variableClosure in outputs) {
            OutputDelegate vd = variableClosure.delegate
            execGraph.addNode(vd)
        }
        
        for(reqInp in modelInputs?.values()){
            execGraph.addNode(reqInp)
        }
        
        for (Closure variableClosure in outputs) {
            OutputDelegate vd = variableClosure.delegate
            for(String depName : vd.dependencies){
                execGraph.addLink(depName, vd.name)
            }
        }
        
        boolean requiredCheckPassed = true
        if(doCheckRequired){
             return checkRequired(calcContext)
        }
        else {
            return true
        }
    }
    
    /**
     * Executes a model by evaluating (calling doEvaluate) all the Evaluable typed nodes in the Model Graph.
     * This step requires the Config phase to have been executed.
     * @param calcContext
     * @params debug defaults to false
     * @return
     */
    boolean runProcessingPhase(CalculationContext calcContext, boolean debug = false) {
        
        execGraph.traverse(calcContext) { node, level ->
            if(node.value instanceof Evaluable){
                //Todo: Remove since it is unecessary the Graph will only evaluate Evaluable  node values
                Evaluable vd = node.value
                try{
                    vd.doEvaluate(calcContext)
                }
                catch(Exception e){
                    calcContext.errors << e.message
                }
                finally{
                    if (!(vd.result) && abortOnFail) {
                        println "-- failing: $vd.name result false + fail on error set"
                    }
                }
            }
        }
        return true
    }
    
    
    void printVariableDependencies(){
        System.out.withWriter{w->
            printVariableDependencies(w)
        }
        
    }
    void printVariableDependencies(Writer writer){
        for (Closure variableClosure in outputs) {
            def vd = variableClosure.delegate
            writer.write("$vd.name depends on ")
            vd.dependencies.each{
                writer.write(it)
                writer.write(", ")
            }
            writer.write("\n")
        }
    }

    /**
     * Verifies that all model inputs are in the user provided inputs.
     * 
     * TOD: make use of modelInputs mandatory property.
     * 
     * @param calcContext
     * @return
     */
    boolean checkRequired(CalculationContext calcContext) {
        boolean status = true
        if(!modelInputs?.empty && !calcContext.inputs){
            String errMsg = "No inputs specified but there are required inputs for this model"
            status = false
            if(abortOnFail){  
                throw new RequiredInputMissingException(errMsg)
            }
            else{
                calcContext.errors << errMsg
            }
        }
        
        modelInputs.each {
            if (calcContext.inputs[it.key] == null) {
                status = false
                calcContext.errors << "Required input $it.key not found." 
            }
        }
        
        return status
    }

    def propertyMissing(String name) {
        //ignore missing properties since they will be referenced against facts
        println "Missing property $name ignored."
    }

    def test(Map map, Closure testClosure) {
        testClosure.delegate = new TestDelegate()
        testClosure.resolveStrategy = Closure.DELEGATE_FIRST
        tests.add([input: Collections.unmodifiableMap(map), expect: testClosure ])
    }
}
