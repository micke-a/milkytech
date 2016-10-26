package com.milkytech.server

class Calculator {
    String name
    String type
    String subType
    
    static hasMany = [
        codes:CalculatorCode,
        views:CalculatorView
        ]
    
    static mapping = {
        codes lazy:false
        views lazy:false
    }
    
    static constraints = {
        views(nullable:true)
    }
}
