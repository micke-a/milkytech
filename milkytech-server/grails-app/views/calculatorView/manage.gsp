<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Edit</title>
		<r:require modules="ace" />
	</head>


	<body>
        <g:hiddenField name="id" value="${calc?.id}"/>
        
        <div class="row">
            <div class="col-md-12">
                <g:form class="form-horizontal" role="form" action="manage" method="post">
                    <g:hiddenField name="calculatorId" value="${cv?.calculator?.id }"/>
                    <g:hiddenField name="id" value="${cv?.id }"/>
                    
                    <div class="form-group">
                        <label for="type" class="col-sm-2 control-label">Type</label>
                        <div class="col-sm-10">
                            <g:select class="form-control" name="type" from="${['OUTPUT','INPUT']}" value="${cv?.type }"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">Name</label>
                        <div class="col-sm-10">
                            <g:textField class="form-control" size="50" name="name" value="${cv?.name }"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="markupType" class="col-sm-2 control-label">Markup Type</label>
                        <div class="col-sm-10">
                            
                            <g:select class="form-control" name="markupType" from="${com.milkytech.server.MarkupType.values()}" value="${cv?.markupType }"/>
                        </div>
                    </div>
                    
                    <br/>
                    
                    <g:aceEditor 
                        name="modelPresentationEditor" 
                        mode="html"
                        contentField="content"
                        contentValue="${cv?.content }" 
                        minLines="20" 
                        maxLines="50" />
                        
                    <br/>
                    
                    
                    <div class="form-group">
                        <g:submitButton class="btn btn-default" name="submit" value="Save" onclick="populateHiddenFieldValues_modelPresentationEditor();"/>
                        <g:actionSubmit class="btn btn-default" value="Create As New" controller="calculatorView" action="create"/>
                        <g:actionSubmit class="btn btn-warning" value="Remove" controller="calculatorView" action="delete"/>
                    </div>
                </g:form>
            </div>
        </div>

	</body>
</html>
