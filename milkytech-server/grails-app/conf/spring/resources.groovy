// Place your Spring DSL code here
import grails.rest.render.*
import grails.rest.render.json.*
import com.milkytech.server.*

beans = {
    
    calculationRenderer(JsonRenderer, Calculation){
        
    }
    
    calculationCollectionRenderer(JsonCollectionRenderer, Calculation){
    
    }
    
    calculationArtefactRenderer(JsonRenderer, CalculationArtefact){
        includes = ['id', 'name', 'type', 'valueType','value' ]
    }
    
    calculationArtefactCollectionRenderer(JsonCollectionRenderer, CalculationArtefact){
        includes = ['id', 'name', 'type', 'valueType','value' ]
    }
}
