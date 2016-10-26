package com.milkytech.server

import com.milkytech.engine2.CalculationContext
import com.milkytech.engine2.CalculationEngine
import com.milkytech.engine2.ModelDelegate

/**
 * CalculatorViewController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CalculatorViewController {

	static scaffold = CalculatorView
    
    def defaultInputs(Long id){
        Calculator calc = Calculator.read(id)
        CalculatorCode cc = calc.codes.find { it.type == 'C' }
        
        ModelDelegate model= CalculationEngine.parseModelScript(cc.code)
        model.runConfigPhase(new CalculationContext(), true, false)
        
        [
            inputs: model.modelInputs.values(),
            calc : calc
            ]
    }
    
    def manage(){
        
        CalculatorView cv = CalculatorView.get(params.id)
        if(request.post){
            cv.name = params.name
            cv.content = params.content
            cv.markupType = params.markupType
            cv.save(failOnError:true, flush:true)
        }
        
        [cv: cv]
    }
    
    def create(){
        
        CalculatorView cv = new CalculatorView(
            name: params.name,
            type: params.type,
            content: params.content,
            calculator: Calculator.read(params.long('calculatorId'))
            ).save(failOnError:true)
        
        redirect(action : 'manage', id:cv.id)
    }
    
    def delete(Long id){
        
        CalculatorView.get(id)?.delete()
        
        redirect controller:'calculator', action:'list'
    }
}
