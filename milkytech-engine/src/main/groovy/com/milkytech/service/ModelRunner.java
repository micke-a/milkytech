package com.milkytech.service;

import com.milkytech.engine2.CalculationContext;
import com.milkytech.engine2.ModelDelegate;

public interface ModelRunner {

    CalculationContext runModel(ModelDelegate model, CalculationContext calculationContext);
}
