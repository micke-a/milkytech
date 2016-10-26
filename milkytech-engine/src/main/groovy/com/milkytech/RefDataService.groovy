package com.milkytech

class RefDataService {

    def lookup(String dataSetName, def ... args){
        if(args){
            return args[args.length-1]
        }
        else{
            return 42
        }
    }
}
