package com.milkytech.server

/**
 * MilkyTechTagLib
 * A taglib library provides a set of reusable tags to help rendering the views.
 */
class MilkyTechTagLib {
    
    def aceEditor = {attrs, body ->
        assert attrs.mode
        assert attrs.maxLines
        assert attrs.minLines
        assert attrs.name
        assert attrs.contentField
        assert attrs.contentValue
        
        out << render (template:'/tags/aceEditor', model: attrs)
    }

    def defaultInputs = {attrs, body ->
        assert attrs.inputs
        
        def inputsList = attrs.inputs instanceof Map ? attrs.inputs.values(): attrs.inputs
        
        out << render (template:'/tags/defaultInputs', model: [inputs: inputsList])
    }
}
