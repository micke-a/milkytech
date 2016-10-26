<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Show</title>
	</head>


	<body>
	   <table class="table table-bordered table-striped">
	       <thead>
	           <th>Name</th>
	           <th>Type</th>
	           <th>SubType</th>
	        <th>Actions</th>
	       </thead>
	       <tbody>
	           <g:each in="${calculators}" var="calc">
	            <tr>
	               <td>
	                   <g:link class="btn btn-default" action="show" id="${calc.id }">${calc.name }</g:link>
	                </td>
	               <td>${calc.type }</td>
	               <td>${calc.subType }</td>
	               <td>
                       <g:link class="btn btn-default" action="playground" id="${calc.id }">Playground</g:link> 
                       <g:link class="btn btn-default" controller="calculatorView" action="defaultInputs" id="${calc.id }">Default Inputs</g:link>
	               </td>
	            </tr>
                <g:each in="${calc.views }" var="cv">
                <tr>
                    <td colspan="2">&nbsp;</td>
                    
                    <td>View (${cv.type }): ${cv.name }</td>
                    <td>
                        <g:link class="btn btn-default" controller="calculatorView" action="manage" id="${cv.id }">Manage</g:link>
                        <g:link class="btn btn-warning" controller="calculatorView" action="delete" id="${cv.id }">Remove</g:link>
                    </td>
                </tr>
                </g:each>
	           </g:each>
	       </tbody>
	   </table>
	</body>
</html>
