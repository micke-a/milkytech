<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>ACE Editor Test</title>
<style type="text/css" media="screen">
body {
	overflow: hidden;
}

#editor {
	margin: 0;
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
}
</style>
<r:require modules="ace" />
</head>


<body>
	<div class="row">
		<div class="col-md-4">
		  <label for="inputs">Model Inputs</label>
          <g:hiddenField name="inputs" value="${inputs}" />
          <pre id="inputsEditor"></pre>
		</div>
		<div class="col-md-8">
			<div class="row">
				<div class="col-md-12">
				    <label for="model">Model Code</label>
	                <g:hiddenField name="modelCode" value="${modelCode }" />
	                <pre id="modelCodeEditor"></pre>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
				    <label for="model">Presentation</label>
                    <g:hiddenField name="modelPresentation" value="${modelPresentation }" />
                    <pre id="modelPresentationEditor"></pre>
				</div>
			</div>
		</div>
	</div>
	<r:script lang="JavaScript">
        function configureEditor( editor,  mode,  source, maxLines, minLines){
	        //inputsEditor.setTheme("ace/theme/twilight");
	        editor.getSession().setMode("ace/mode/"+mode);
	        editor.getSession().setValue($(source).val() );
	        editor.setAutoScrollEditorIntoView();
	        editor.setOptions({
	            maxLines: maxLines,
	            minLines: minLines,
	            enableBasicAutocompletion: true,
                enableSnippets: true
	        });
        }
        
        ace.require("ace/ext/language_tools");
        var inputsEditor = ace.edit("inputsEditor");
        var modelEditor = ace.edit("modelCodeEditor");
        var presentationEditor = ace.edit("modelPresentationEditor");
        
        configureEditor(inputsEditor, 'json', '#inputs', 50,50);
        configureEditor(modelEditor, 'groovy', '#modelCode', 23, 23);
        configureEditor(presentationEditor, 'handlebars', '#modelPresentation', 23, 23);
        
    </r:script>
</body>
</html>
