package com.milkytech.service
import groovy.lang.Closure;
import groovy.lang.ExpandoMetaClass;

import com.milkytech.engine2.CalculationContext;
import com.milkytech.engine2.ModelDelegate;

public class MilkytechModelParser implements ModelParser{
    
    @Override
    ModelDelegate parseModel(String dsl) {
        return createModel(new GroovyShell().parse(dsl))
    }

    @Override
    ModelDelegate parseModel(File file) {
        return createModel(new GroovyShell().parse(file))
    }

    private ModelDelegate createModel(Script dslScript) {
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
    
    private ExpandoMetaClass createEMC(Class clazz, Closure cl) {
        ExpandoMetaClass emc = new ExpandoMetaClass(clazz, false)
        cl(emc)
        emc.initialize()
        return emc
    }
}

