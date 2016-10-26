package com.milkytech.graph

import com.milkytech.engine2.OutputDelegate

class Node {
    def value
    
    List<Node> incoming = []
    List<Node> outgoing = []
    
    String getName(){
        return value.name
    }
    
    
    String toString(){
        String outgoingStr = outgoing? outgoing*.name.join(',') : "empty"
        String incomingStr = incoming? incoming*.name.join(',') : "empty"
        
        return "${value?.class?.simpleName}:${value?.name}, Depends on: ${incomingStr} . Depended on: ${outgoingStr}." 
    }

}
