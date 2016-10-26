<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Show</title>
		<r:require modules="ace" />
	</head>


	<body>
        <g:hiddenField name="id" value="${calc?.id}"/>
        
        <div class="row">
            <div class="col-md-12">
                <g:link class="btn btn-default" controller="calculator" action="playground" id="${calc.id }">Back to Calculator Playground</g:link>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-12">
                <dl class="dl-horizontal">
                    <dt>Name</dt>
                    <dd>${calc?.name }</dd>
                    <dt>Type</dt>
                    <dd>${calc?.type }</dd>
                    <dt>SubType</dt>
                    <dd>${calc?.subType }</dd>
                </dl>
                
                <hr/>
                
                <g:form class="form-horizontal" role="form" controller="calculate" action="calculateWithFormInputs">
                    <g:hiddenField name="id" value="${calc.id }"/>
                    
                    <g:defaultInputs inputs="${inputs }"/>
                    
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <g:actionSubmit class="btn btn-default" value="Calculate" controller="calculate" action="calculateWithFormInputs"/>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
	</body>
</html>
