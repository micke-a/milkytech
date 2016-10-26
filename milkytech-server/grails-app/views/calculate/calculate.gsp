<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Show</title>
	</head>
	<body>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>Type</th>
				<th>Name</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<g:each in="${calcContext.inputs }" var="item">
				<tr>
					<td>Input</td>
					<td>
						${item.key}
					</td>
					<td>${item.value }</td>
				</tr>
			</g:each>

			<g:each in="${calcContext.intermediate }" var="item">
				<tr>
					<td>Intermediate</td>
					<td>
						${item.key}
					</td>
					<td>
						${item.value }
					</td>
				</tr>
			</g:each>

			<g:each in="${calcContext.outputs}" var="item">
				<tr>
					<td>Model Variable</td>
					<td>
						${item.key}
					</td>
					<td>
						${item.value }
					</td>
				</tr>
			</g:each>
		</tbody>
	</table>
</body>
</html>
