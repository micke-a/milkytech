<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Show</title>
	</head>
	<body>
        
       <h1>${calc.name }</h1>
       
       <h2> Required Inputs </h2>
       <ul>
        <g:each in="${requiredInputs }" var="inp">
            <li>${inp }</li>    
        </g:each>
       </ul>
       
       <br/>
       <g:form role="form" class="form" method="post" controller="calculate" action="calculateWithFormInputs">
            <g:hiddenField name="id" value="${calc.id }"/>
            
            <g:if test="${calcView}">
                ${calcView.content }
            </g:if>
            <g:else>
                <h4>Inputs view rendered from meta-data</h4>
                <br/>
                <g:defaultInputs inputs="${requiredInputs }"/>
            </g:else>
            <g:submitButton class="btn btn-default" name="button" value="Calculate"/>
       </g:form>
	</body>
</html>
