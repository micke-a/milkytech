package com.milkytech.server

import grails.converters.JSON
import groovy.json.JsonSlurper

import com.milkytech.engine2.CalculationContext
import com.milkytech.engine2.CalculationEngine
import com.milkytech.engine2.ModelDelegate
import com.milkytech.engine2.ValidationResult
import com.milkytech.engine2.ValidationResults

class CalculateController {

    def index() { 
        
    }
    
    private Map calculateInternal(Calculator calc, Map rawInputs){
        
        
        CalculatorCode cc = calc.codes.find { it.type == 'C' }
        ModelDelegate model= CalculationEngine.parseModelScript(cc.code)
        
        ValidationResults valRes = model.modelInputs.validate(rawInputs)
        if(valRes.hasErrors()){
            log.error(valRes.errors)
        }
        
        Calculation calculation = new Calculation(calculator: calc)
        CalculationContext result = null
        try{
            def inputMap = model.modelInputs.coerceMap(rawInputs)
            CalculationContext ctx = new CalculationContext(inputMap)
            
            result= CalculationEngine.process(model, ctx)
            
            CalculationArtefact.build(inputMap, 'INP')?.each{
                calculation.addToArtefacts( it )
            }
            
            CalculationArtefact.build(result.outputs, 'OUT')?.each{
                calculation.addToArtefacts( it )
            }
            
            CalculationArtefact.build(result.intermediate, 'TMP')?.each{
                calculation.addToArtefacts( it )
            }
            
            int i=0
            for(String error : result.errors){
                i++
                calculation.addToArtefacts(  new CalculationArtefact(
                    name: "error_${i}",
                    type: "ERR",
                    value : error,
                    valueType: "java.lang.String"
                    ))
            }
        }
        catch(Exception ignore){
            log.error("Ignoring error", ignore)
        }
        
        for(ValidationResult vr : valRes.errors){
            calculation.addToArtefacts(
                new CalculationArtefact(
                    name: vr.name,
                    type: "ERR",
                    value : vr.description,
                    valueType: "java.lang.String"
                    )
                )
        }
        for(ValidationResult vr : valRes.warnings){
            calculation.addToArtefacts(
                new CalculationArtefact(
                    name: vr.name,
                    type: "WRN",
                    value : vr.description,
                    valueType: "java.lang.String"
                    )
                )
        }
        
        calculation.save(failOnError:true, flush:true)
        
        return [
            'calculation': calculation,
            'result' : result
            ]
    }
    
    def calculateWithFormInputs(){
        Calculator calc = Calculator.read(params.id as Long)
        
        Map output = calculateInternal(calc, params)
        
         [
             calcContext: output.result
         ]
         
    }
    
    
    
    def calculate(Long id, String inputs){
        
        
        Map rawInputs
        try{
            rawInputs = new JsonSlurper().parseText(inputs)
        }
        catch(Exception e){
            log.error('Error parsing JSON: ' +inputs)
            log.error(e)
        }
        
        Calculator calc = Calculator.read(id)
        
        Map output = calculateInternal(calc, rawInputs)
        
        CalculationContext result = output.result
        
        withFormat{
            html calcContext: result
            json {render ([
                    outputs : result.outputs,
                    intermediate : result.intermediate,
                    inputs : result.inputs,
                    errors : result.errors
                ] as JSON)
            } 
        }
    }
    
    def playground(String inputs, String modelCode){
        
        println params.dump()
        
        def jsonData
        try{
            jsonData = new JsonSlurper().parseText(inputs)
            log.info jsonData
        }
        catch(Exception e){
            e.printStackTrace()
        }
        
        
        ModelDelegate model= CalculationEngine.parseModelScript(modelCode)
        ValidationResults valRes = model.modelInputs.validate(jsonData)
        if(valRes.hasErrors()){
            log.error(valRes.errors)
        }
        CalculationContext ctx = new CalculationContext(jsonData)
        
        def retVal = [:]
        retVal.inputs = [:]
        retVal.outputs = [:]
        retVal.intermediate = [:]
        retVal.errors = valRes.errors.collect {"$it.name - $it.description" }
                     
        try{                           
            def result = CalculationEngine.process(model, ctx)
           
            result.inputs.each{
                retVal.inputs[it.key] = it.value
            }
            result.intermediate.each{
                retVal.intermediate[it.key] = it.value
            }
            result.outputs.each{
                retVal.outputs[it.key] = it.value
            }
            retVal.errors.addAll(result.errors)
        }
        catch( Exception e){
            println "..." +e.dump()
            retVal.errors << e.message
        }
        render retVal as JSON
            
    }
}
