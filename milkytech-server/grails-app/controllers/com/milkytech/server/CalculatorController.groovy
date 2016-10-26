package com.milkytech.server

import com.milkytech.engine2.CalculationContext
import com.milkytech.engine2.CalculationEngine
import com.milkytech.engine2.ModelDelegate
import grails.converters.JSON
import com.milkytech.server.command.CalculatorCommand

class CalculatorController {
    
    static String DEFAULT_MODEL = """model(name:"Model Name", version:"1.0") {
    inputs{
        inp()
    }
    
    /**
     * Functions
     */
    function(name:"afunc"){ arg1, arg2 ->
       arg1+arg2
    }
    
    /**
     * Variables
     */
    output(name:'result'){
        evaluate{
            afunc(5,5)+inp
            
        }
    }
}
"""
    static String DEFAULT_PRESENTATION = """<ul>
{{#each errors}}
    <li>{{this}}</li>
{{/each}}
</ul>

<table class="table table-bordered table-striped">
    <thead> 
        <tr>
        <th>Type</th> 
        <th>Name</th> 
        <th>Value</th> 
        </tr>
    </thead> 
    <tbody> 
        {{#each inputs}} 
        <tr> 
            <td>Input</td>
            <td>{{@key}}</td>  
            <td>{{this}}</td>  
        </tr> 
        {{/each}} 

        {{#each intermediate}} 
        <tr> 
            <td>Intermediate</td>
            <td>{{@key}}</td>  
            <td>{{this}}</td>  
        </tr> 
        {{/each}} 

        {{#each outputs}} 
        <tr> 
            <td>Outputs</td>
            <td>{{@key}}</td>  
            <td>{{this}}</td>  
        </tr> 
        {{/each}}
    </tbody> 
</table>
"""
    static String DEFAULT_INPUTS = '''{
    "inp":1000,
    "inp2": "hello"
}'''
    static String INPUTS_TEMPLATE = '''{
REPLACE
}'''
    def test(){
        def modelCode = DEFAULT_MODEL
        def modelPresentation = DEFAULT_PRESENTATION
        [
            modelCode : modelCode,
            modelPresentation : modelPresentation,
            inputs : DEFAULT_INPUTS
            ]
    }
    
    def index(){
        redirect action: 'list'
    }
    
    def list(){
        def calculators = Calculator.list()
        withFormat {
            html calculators : calculators
            json { render (calculators.collect{ calc ->
                [
                    id : calc.id,
                    name : calc.name,
                    type : calc.type,
                    subType : calc.subType,
                    views : calc.views.collect { v ->
                        [
                            id: v.id,
                            name : v.name,
                            type : v.type,
                            markupType : v.markupType
                            ]
                    }
                ]
            } as JSON) }
        }
    }
    
    def show(Long id, String inputsViewName){
        
        
        Calculator calc = Calculator.read(id)
        CalculatorCode cc = calc.codes.find { it.type == 'C' }
        
        CalculatorView cv
        if(inputsViewName){
            cv = calc.views.find{ it.name == inputsViewName}
        }
        else{
            cv = calc.views.find{ it.type == CalculatorView.TYPE_INPUT}
           
        }
        
        
        ModelDelegate model= CalculationEngine.parseModelScript(cc.code)
        model.runConfigPhase(new CalculationContext(), true, false)
        
        StringWriter sw = new StringWriter()
        model.printVariableDependencies(sw)
        
        
        [
            calc: calc,
            code:cc,
            variables: sw.toString(),
            requiredInputs: model.modelInputs,
            calcView : cv
        ]
    }
    
    def describe(Long id){
        
        Calculator calc = Calculator.read(id)
        CalculatorCode cc = calc.codes?.find { it.type == 'C' }
        
        ModelDelegate model= CalculationEngine.parseModelScript(cc.code)
        model.runConfigPhase(new CalculationContext(), true, false)
        
        StringWriter sw = new StringWriter()
        model.printVariableDependencies(sw)
        
        model.modelInputs.each{key, value ->
            println key +' - ' +value
        }
        
        withFormat{
            html  calc: calc, code:cc, variables: sw.toString()
            json { render model.modelInputs.collect {key, value ->
                    [
                        name: value.name,
                        min: value.min,
                        max: value.max,
                        range: value.range,
                        type: value.type?.name,
                        mandatory: value.isMandatory(),
                        widget : value.widget
                        ]
                } as JSON 
            }
        
        }
    }
    
    def create(CalculatorCommand cmd){
        
        Calculator calc = new Calculator(
            name: cmd.name, 
            type: cmd.type,
            subType: cmd.subType
            )
        
        CalculatorCode cc = new CalculatorCode(
            type : cmd.codeType, 
            implType : cmd.implType, 
            code: cmd.modelCode
            )
        
        calc.addToCodes( cc )
        
        CalculatorView cv = new CalculatorView(
            name: CalculatorView.DEFAULT_OUTPUT_VIEW_NAME, 
            type: CalculatorView.TYPE_OUTPUT,
            content: cmd.modelPresentation,
            markupType: MarkupType.HANDLEBARS
            )
        
        calc.addToViews(cv)
        
        calc.save(failOnError: true)
        
        
        withFormat{
            html redirect(controller:'calculator', action:'playground', id:calc.id)
            json { render(['message': 'Saved', 'status': true] as JSON )}
        }
        
    }
    
    def save(CalculatorCommand cmd){
        
        Calculator calc = Calculator.get(cmd.id)
        calc.name = cmd.name
        
        CalculatorCode cc = calc.codes.find { it.type == 'C' }
        cc.code = cmd.modelCode
        
        if(!calc.views?.empty){
            CalculatorView cv = calc.views.find{ it.name } //just find first with a name
            cv.content = cmd.modelPresentation
        }
        
        calc.save(failOnError:true)
        
        withFormat {
            json {render (['message': "Model ${cmd.id} Saved", 'status': true] as JSON) }
            html redirect(controller:'calculator', action:'playground', id:calc.id)
        }
    }
    
    
    def details(Long id){
        def modelCode = DEFAULT_MODEL
        def modelPresentation = DEFAULT_PRESENTATION
        CalculatorView cv
        Calculator calc = null
        calc = Calculator.read(id)
        
        CalculatorCode cc = calc.codes.find { it.type == 'C' }
        modelCode = cc.code
        
        if(!calc.views?.empty){
            cv = calc.views.find {CalculatorView view -> view.type == CalculatorView.TYPE_OUTPUT && view.markupType == MarkupType.HANDLEBARS  }
            if(cv){
                modelPresentation = cv?.content
            }
        }
        
        
        String inputs = ""
        try{
            ModelDelegate model= CalculationEngine.parseModelScript(modelCode)
            boolean first = true
            for(reqInp in model.modelInputs.values()){
                if(!first){
                    inputs += ',\n'
                }
                else{
                    first = false
                }
                
                inputs += "\t\"${reqInp.name}\": 1"
            }
            inputs = INPUTS_TEMPLATE.replace('REPLACE', inputs)
        }
        catch(Exception e){
            e.printStackTrace()
            inputs = e.message
        }
        
        withFormat{
            html calc : calc, modelCode : modelCode, modelPresentation : modelPresentation, cv: cv, inputs : inputs
            json { render (
                    [
                    calculator : calc,
                    inputsText : inputs,
                    modelText : modelCode,
                    view : [
                           markup : modelPresentation,
                           name : cv.name,
                           markupType : cv.markupType
                        ]
                   ] as JSON )
             }
        }
    }
    
    def playground(){
        
        def modelCode = DEFAULT_MODEL
        def modelPresentation = DEFAULT_PRESENTATION
        CalculatorView cv
        Calculator calc = null
        if(params.id){
            calc = Calculator.read(params.id)
            
            println calc.dump()
            CalculatorCode cc = calc.codes.find { it.type == 'C' }
            modelCode = cc.code
            
            if(!calc.views?.empty){
                cv = calc.views.find {CalculatorView view -> view.type == CalculatorView.TYPE_OUTPUT && view.markupType == MarkupType.HANDLEBARS  }
                if(cv){
                    modelPresentation = cv?.content
                }
            }
        }
        
        String inputs = ""
        try{
            ModelDelegate model= CalculationEngine.parseModelScript(modelCode)
            boolean first = true
            for(reqInp in model.modelInputs.values()){
                if(!first){
                    inputs += ',\n'
                }
                else{
                    first = false
                }
                
                inputs += "\t\"${reqInp.name}\": 1"
            }
            inputs = INPUTS_TEMPLATE.replace('REPLACE', inputs)
        }
        catch(Exception e){
            e.printStackTrace()
            inputs = e.message
        }
        [
            calc : calc,
            modelCode : modelCode,
            modelPresentation : modelPresentation,
            cv: cv,
            inputs : inputs
            ]
    }
}
