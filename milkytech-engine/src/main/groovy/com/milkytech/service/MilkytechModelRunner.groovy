package com.milkytech.service

import com.milkytech.engine2.CalculationContext;
import com.milkytech.engine2.ModelDelegate;

class MilkytechModelRunner implements ModelRunner {

    @Override
    CalculationContext runModel(ModelDelegate model, CalculationContext calculationContext) {
        boolean status = model.runModel(calculationContext)
        
        return calculationContext
    }
}
