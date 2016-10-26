import com.milkytech.server.*
import grails.converters.*

class BootStrap {

    def init = { sc ->
        
        JSON.use('deep')
        
        setupCalc(sc, [
            calcType:'testType', 
            calcSubType:'testSubType', 
            modelName:'Mr T', 
            modelResource:'WEB-INF/models/CalculationOne.model'
            ])
        
        setupCalc(sc, [
            calcType:'testType', 
            calcSubType:'testSubType', 
            modelName:'Reg\'s Lawn', 
            modelResource:'WEB-INF/models/RegsLawn.model',
            inputsViewName : 'RegsLawn Default Inputs View',
            inputsView:'WEB-INF/models/RegsLawn.html',
            inputsViewMarkupType : MarkupType.HTML
            ])
        
        setupCalc(sc, [
            calcType:'testType', 
            calcSubType:'testSubType', 
            modelName:'Lots Of Inputs', 
            modelResource:'WEB-INF/models/LotsOfInputs.model'
            ])
        
        
    }
    
    def setupCalc(def sc, Map data){
        Calculator c1 = new Calculator(type:data.calcType, subType:data.calcSubType, name: data.modelName)
        CalculatorCode cc1 = new CalculatorCode(
            type:'C', 
            implType:ModelImplType.MILKYTECH, 
            code:  sc.getResourceAsStream(data.modelResource).text
            )
        
        c1.addToCodes(cc1)
        c1.addToViews(new CalculatorView(
            type: CalculatorView.TYPE_OUTPUT, 
            name:CalculatorView.DEFAULT_OUTPUT_VIEW_NAME, 
            content: CalculatorController.DEFAULT_PRESENTATION,
            markupType : MarkupType.HANDLEBARS
            ))
        
        if(data.inputsView){
            c1.addToViews(new CalculatorView(
                type: CalculatorView.TYPE_INPUT, 
                name: data.inputsViewName, 
                content: sc.getResourceAsStream(data.inputsView).text,
                markupType: data.inputsViewMarkupType
                )
            )
            
        }
        
        c1.save(failOnError:true)
    }
    def destroy = {
    }
}
