package com.milkytech.server

/**
 * CalculatorPresentation
 * A domain class describes the data object and it's mapping to the database
 */
class CalculatorView {
    public static final String DEFAULT_OUTPUT_VIEW_NAME = 'default-output-view'
    public static final String INPUTS_VIEW_NAME = 'inputs-view'
    
    public static final String TYPE_INPUT = 'INPUT'
    public static final String TYPE_OUTPUT = 'OUTPUT'
    
    String name
    String type
    MarkupType markupType
    String content
    
	static	belongsTo	= [calculator: Calculator]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
	
    static	mapping = {
        content type: 'text'
        sort 'name'
    }
    
	static	constraints = {
    }
	
    @Override 
	public String toString() {
		return "${name}";
	}
}
