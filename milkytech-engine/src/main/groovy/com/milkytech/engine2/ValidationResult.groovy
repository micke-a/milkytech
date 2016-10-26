package com.milkytech.engine2

import groovy.transform.Immutable

@Immutable
class ValidationResult {
    String name
    String description
    
    String toString(){
        return "$name - $description"
    }
}
