package com.milkytech.graph

import java.util.logging.Level
import java.util.logging.Logger

import com.milkytech.engine2.CalculationContext
import com.milkytech.engine2.Evaluable
import com.milkytech.engine2.ModelInput
import com.milkytech.engine2.Unknown

class Graph {
    static final Logger logger = Logger.getLogger(Graph.class.name)
    
    static Closure PRINT = { n, depth ->
            String nodeName = n.name
            println nodeName.padLeft( nodeName.size()+depth, '-')
        }
    Map nodes = [:]
    
    void addNode(def value){
        addNode(new Node(value:value))
    }
    
    void addLink(String fromName, String toName){
        
        if(!nodes[fromName]){
            nodes[fromName] = new Node(value: new Unknown(name: fromName))
        }
        if(!nodes[toName]){
            nodes[toName] = new Node(value: new Unknown(name:toName))
        }
        
        addLink(nodes[fromName], nodes[toName])
    }
    
    void addLink(Node from, Node to){
        assert from
        assert to
        
        to.incoming << from
        from.outgoing << to
    }
    
    void addNode(Node node){
        if(nodes[node.name]){
            logger.log(Level.INFO, "$node.name already exists")
        }
        nodes[node.name] = node
    }
    
    List findRoots(){
        List roots =[]
        for(Node node : nodes.values()){
            if(node.incoming.empty){
                roots << node
            }
        }
        
        return roots
    }
    
    void traverse(CalculationContext calcContext, Closure cl){
        Map traverseContext = new LinkedHashMap(nodes.size())
        traverse(findRoots(),cl, traverseContext, calcContext)
    }
    
    /**
     * Recursively traverse the Graph "breadth first" and evaluate the closure for each Graph Node.
     *
     * @param nodes
     * @param cl
     * @param traverseContext
     */
    void traverse(List nodes, Closure cl, Map traverseContext, CalculationContext calcContext, int depth=0){
        if(nodes?.empty){
            return
        }
        
        for(Node n : nodes){
            visitNode(cl, n, calcContext, traverseContext, depth)
            logger.log(Level.INFO, "About to visit outgoing nodes for ${n.name} (depth=${depth})")
            traverse(n.outgoing,cl,traverseContext,calcContext, depth+1)
        }
    }
    
    void breadthFirstTraversal(List nodes, Closure cl, Map traverseContext, CalculationContext calcContext, int depth=0){
        
    }
    
    void visitNode(Closure cl, Node n, CalculationContext calcContext, Map traverseContext, int depth){
        String nodeName = n.name
        
        logger.log(Level.INFO, "Visiting node ${n.name} (depth=${depth})")
        
        if(traverseContext[nodeName]){
           logger.log(Level.INFO, "Skipping $nodeName , already visited (depth=${depth})")
        }
        else{
            //Check node dependencies
            List incomingNodesNotActioned = []
            for(Node incomingNode : n.incoming){
                if(!traverseContext[incomingNode.name]){
                    incomingNodesNotActioned << incomingNode
                }
            }
            if(incomingNodesNotActioned.empty){
                //Mark as visited
                if(n.value instanceof ModelInput){
                    //Need to also make sure the input was provided
                    traverseContext[nodeName] = calcContext.hasInput(nodeName)
                }
                else if(n.value instanceof Evaluable){
                    try{
                        logger.log(Level.INFO,"Evaluating closure for node ${n.name} (depth=${depth})")
                        cl(n, depth)
                        traverseContext[nodeName] = true
                    }
                    catch(Exception e){
                        logger.log(Level.SEVERE, e.message)
                        calcContext.errors << e.message
                    }
                    
                }
                else{
                    logger.log(Level.WARNING, "Can this else statement ever be reached with current domain model? node=${n} (depth=${depth})")
                    traverseContext[nodeName] = true
                }
                
            }
            else{
                logger.log(Level.INFO,  "Not invoking closure variable ${n} . Due to following node(s) not being available: ${(incomingNodesNotActioned*.name).join(',')} (depth=${depth})")
            }
        }
    }
}
