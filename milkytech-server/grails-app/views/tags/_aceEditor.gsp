


<r:require modules="ace" />

<g:hiddenField name="${contentField }" value="${contentValue}" />
<pre id="editor_${name }"></pre>

<r:script  language="text/javascript">

    function configureEditor( editor,  mode,  source, minLines, maxLines){
        
    }
    
    function populateHiddenFieldValues_${name}(){
        $("#${contentField}").val(editor_${name}.getSession().getValue());
    }
    
    
    //Not sure what setting this multiple times will do...
    ace.require("ace/ext/language_tools");
    ace.config.set('basePath', '${request.contextPath}/ace/src-noconflict/');
    
    var editor_${name} = ace.edit("editor_${name}");
    
    //editor_${name}.setTheme("ace/theme/twilight");
    editor_${name}.getSession().setMode("ace/mode/${mode}");
    editor_${name}.getSession().setValue($("#${contentField }").val() );
    editor_${name}.setAutoScrollEditorIntoView();
    editor_${name}.setOptions({
        maxLines: ${maxLines},
        minLines: ${minLines},
        enableBasicAutocompletion: true,
        enableSnippets: true
    });

</r:script>

