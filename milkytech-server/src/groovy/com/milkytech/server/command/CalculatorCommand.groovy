package com.milkytech.server.command

import grails.validation.Validateable

import com.milkytech.server.CalculatorView
import com.milkytech.server.ModelImplType

@Validateable
class CalculatorCommand {

    Long id
    String name
    
    String type
    String subType
    
    ModelImplType implType = ModelImplType.MILKYTECH
    String codeType = 'C'
    String viewName = CalculatorView.DEFAULT_OUTPUT_VIEW_NAME
    
    String modelCode
    String modelPresentation
}
