package com.milkytech.server

import com.milkytech.engine2.ModelInput
import com.milkytech.engine2.ModelInputs

/**
 * CalculationArtefact
 * A domain class describes the data object and it's mapping to the database
 */
class CalculationArtefact {

	/* Default (injected) attributes of GORM */
//	Long	id
//	Long	version
	
	/* Automatic timestamping of GORM */
//	Date	dateCreated
//	Date	lastUpdated
	
//	static	belongsTo	= []	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
//	static	hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping
//	static	hasMany		= []	// tells GORM to associate other domain objects for a 1-n or n-m mapping
//	static	mappedBy	= []	// specifies which property should be used in a mapping 
	
    String name
    String type
    String valueType
    String value
    
    static	mapping = {
    }
    
	static	constraints = {
        name blank:false
        type 'in':['I','O','E','W']
        valueType()
        value()
    }
	
    public static CalculationArtefact build(ModelInput mi, Object value){
        return new CalculationArtefact(
                name: mi.name,
                type: 'I',
                valueType : mi.type?.name,
                value : value as String
            )
    }
    
    public static List<CalculationArtefact> build(ModelInputs mis, Map userInputs){
        
        List<CalculationArtefact> retVal = new ArrayList<CalculationArtefact>(mis.values.size())
        
        for(ModelInput mi : mis.values()){
            retVal << build(mi, userInputs[mi.name])
        }
        
        return retVal
    }
    
    static List<CalculationArtefact> build(Map dataMap, String type){
        if(!dataMap || dataMap?.empty){
            return []
        }
        
        List<CalculationArtefact> retVal = new ArrayList<CalculationArtefact>(dataMap.size())

        for(Map.Entry entry : dataMap.entrySet()){
            retVal << new CalculationArtefact(
                name: entry.key,
                type: type,
                valueType : entry.value?.class.name,
                value : entry.value as String
                )
        }
        
        return retVal
    }
    
	/*
	 * Methods of the Domain Class
	 */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}
