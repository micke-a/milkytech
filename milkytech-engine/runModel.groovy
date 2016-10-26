import groovy.json.*
import com.milkytech.engine2.*

/*
 * Usage:
 * 1) gradle build
 * 2) groovy --classpath .:build/libs/milkytech-engine.jar runModel.groovy src/test/resources/com/milkytech/engine2/CalculationOne.params
 */


def paramsFile = args[0]

def json = new JsonSlurper().parseText(new File(paramsFile).text)

def dslScript = new File(json.model.file).text

ModelDelegate model= CalculationEngine.processRules(dslScript)

model.printVariableDependencies()

def result = CalculationEngine.process(model,
    new CalculationContext(
        json.inputs
    )
)


println "============================================"
println "== Results ${model.name} v${model.version}"
println()
println "== Intermediate outputs"
result.intermediate.each{
    println it
}
println()
println "== Outputs"
result.outputs.each{
    println it
}
println()
