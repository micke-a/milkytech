modules = {
    application {
        resource url:'js/application.js'
    }
    
    ace {
        resource url:'ace/src-noconflict/ace.js', disposition:'head'
        resource url:'ace/src-noconflict/ext-language_tools.js', disposition:'head'
    }
}