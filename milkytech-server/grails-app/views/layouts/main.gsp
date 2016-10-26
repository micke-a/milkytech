<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
	    <r:require modules="jquery, jquery-ui, handlebars, bootstrap-js, bootstrap-css" />
	    
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">

		<title><g:layoutTitle default="Grails"/></title>
		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
	
        <!-- Fixed navbar -->
	    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
	      <div class="container">
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="#">MilkyTech Server</a>
	        </div>
	        <div class="collapse navbar-collapse">
	          <ul class="nav navbar-nav">
	            <li class="active"><g:link url="/milkytech-server">Home</g:link></li>
	            <li><g:link controller="calculator" action="index">Calculators</g:link></li>
                <li><g:link controller="calculation" action="index">Calculations</g:link></li>
	            <li><a href="#about">About</a></li>
	            
	          </ul>
	        </div><!--/.nav-collapse -->
	      </div>
	    </div>
	    <!-- Begin page content -->
	    <div class="container">
	       <br/>
	       <br/>
	       <br/>
	      <g:layoutBody/>
	    </div>
        
        <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	
	    <div id="footer">
	      <div class="container">
	        <p class="text-muted">FOOTER</p>
	      </div>
	    </div>
	    
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
