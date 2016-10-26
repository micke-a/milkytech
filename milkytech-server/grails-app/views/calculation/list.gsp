<!DOCTYPE html>
<html>
	<head>
        <meta name="layout" content="main"/>
		<title>Show</title>
	</head>
	<body>
	   <table class="table table-bordered table-striped">
	       <thead>
            <tr>
	           <th>Calculator</th>
	           <th> </th>
             </tr>
	       </thead>
	       <tbody>
               <g:each in="${com.milkytech.server.Calculation.list()}" status="i" var="${calculation}">
	            <tr>
	               <td>
	                   <g:link class="btn btn-default" controller="calculator" action="show" id="${calculation.calculator.id }">${calculation.calculator.name }</g:link>
                       <a class="btn btn-default" href="#" data-toggle="collapse" data-target="#artefacts${calculation.id }">Show/Hide Artefacts</a>
                       ${calculation.dateCreated }
                       ${calculation.artefacts.size() } artefacts,
                       ${calculation.artefacts.findAll({it.type == 'OUT'}).size() } Outputs ,
                       ${calculation.artefacts.findAll({it.type == 'INP'}).size() } Inputs ,
                       ${calculation.artefacts.findAll({it.type == 'ERR'}).size() } Errors ,
                       ${calculation.artefacts.findAll({it.type == 'WRN'}).size() } Warnings ,
                       ${calculation.artefacts.findAll({it.type == 'TMP'}).size() } Intermediates
	                </td>
                 </tr>
                 <tr>
	               <td> 
                        <span id="artefacts${calculation.id }" class="collapse">
                        <table class="table table-bordered table-striped">
                        <thead>
                         <tr>
                           <th>Name</th>
                           <th>Type</th>
                           <th>Value</th>
                           <th>Value Type</th>
                         </tr>
                        </thead>
                        <tbody>
                        <g:each in="${calculation.artefacts }" var="artefact">
                            <tr>
                                <td> <!--  --> </td>
                                <td>${artefact.name }</td>
                                <td>${artefact.type }</td>
                                <td>${artefact.value }</td>
                                <td>${artefact.valueType }</td>
                            </tr>
                        </g:each>
                        </tbody>
                        </table>
                        </span>
                   </td>
	            </tr>
	           </g:each>
	       </tbody>
	   </table>
	</body>
</html>
