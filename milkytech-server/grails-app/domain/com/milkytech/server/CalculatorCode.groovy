package com.milkytech.server

class CalculatorCode {

    String type
    ModelImplType implType
    String code
    
    static belongsTo = [calculator:Calculator]
    static mapping = {
        code type: 'text'
    }
    
    static constraints = {
    }
}
