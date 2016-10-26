package com.milkytech.server

import com.milkytech.service.*;
/**
 * ModelService
 * A service class encapsulates the core business logic of a Grails application
 */
class ModelService {

    static transactional = false
    
    ModelRunner modelRunner
    ModelParser modelParser
    
}
