package com.milkytech.graph

import com.milkytech.engine2.CalculationContext

class GraphTest extends GroovyTestCase {

    void testIt(){
        
        Graph g = new Graph()
        
        g.addNode([name:'v1'])
        g.addNode([name:'v2'])
        g.addNode([name:'v3'])
        g.addNode([name:'v4'])
        g.addNode([name:'v5'])
        
        g.addLink('v2', 'v3')
        g.addLink('v4', 'v3')
        g.addLink('v5', 'v4')
        g.addLink('v5', 'v3')
        
        assert g.findRoots().size() == 3
        
        CalculationContext cc = new CalculationContext()
        
        g.traverse(cc, Graph.PRINT) 
    }
}
