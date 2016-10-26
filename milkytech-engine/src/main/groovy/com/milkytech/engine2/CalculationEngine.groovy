package com.milkytech.engine2

import groovy.lang.Closure;
import groovy.lang.ExpandoMetaClass;
import groovy.lang.Script;

import java.io.File;
import java.util.List;
import java.util.Map;

@Deprecated()
class CalculationEngine {

    static def with(Object target, Closure cl) {
        cl.delegate = target
        cl.resolveStrategy = Closure.DELEGATE_FIRST

        return cl()
    }

    static CalculationContext process(ModelDelegate model, CalculationContext calculationContext) {
        model.runModel(calculationContext)

        return calculationContext
    }

    static ModelDelegate parseModelScript(String dsl) {
        return processRuleScript(new GroovyShell().parse(dsl))
    }

    static ModelDelegate parseModelScript(File file) {
        return processRuleScript(new GroovyShell().parse(file))
    }

    private static ModelDelegate processRuleScript(Script dslScript) {
        dslScript.metaClass = createEMC(dslScript.class) {
            ExpandoMetaClass emc ->
            emc.models = []
            emc.model = { Map modelAttributes, Closure model ->
                model.delegate = new ModelDelegate(modelAttributes)
                models.add(model.delegate)

                model.resolveStrategy = Closure.DELEGATE_FIRST

                model()
            }
        }
        dslScript.run()

        return dslScript.models[0]
    }

    static List testRuleset(ModelDelegate model) {
        List<String> fails = []
        int i = 0
        model.tests.each { Map testData ->
            i++
            Map copy = new HashMap(testData.input)
            CalculationContext calcContext = new CalculationContext(copy)
            if (model.checkRequired(calcContext )) {

                model.runModel(calcContext)
                Closure testClosure = testData.expect
                testClosure.calcContext = calcContext
                testClosure.errors = [] //reset errors before run
                try {
                    testClosure()
                    if (testClosure.errors) {
                        fails.add("\n-----\nTest $i failed" as String)
                        fails.addAll(testClosure.errors)
                        fails.add("Facts: $copy" as String)
                    }
                } catch (e) {
                    fails.add("Test $i failed\n$e.message" as String)
                }

            } else {

                fails.addAll(calcContext.errors)
            }
        }
        return fails
    }

    static ExpandoMetaClass createEMC(Class clazz, Closure cl) {
        ExpandoMetaClass emc = new ExpandoMetaClass(clazz, false)
        cl(emc)
        emc.initialize()
        return emc
    }

}
