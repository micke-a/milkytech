<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main"/>
        <title>Show</title>
        <r:require modules="ace" />
    </head>


    <body>
        <form id="playgroundForm" class="form form-inline" method="POST">
        <g:hiddenField name="id" value="${calc?.id}"/>
        
        <div class="row">
            <div class="col-md-12">
                <label for="name">Name</label> <g:textField class="form-control input-sm" name="name" value="${calc?.name }"/>
                <label for="type"> Type</label> <g:textField class="form-control input-sm" name="type" value="${calc?.type }"/>
                <label for="subType"> SubType</label> <g:textField class="form-control input-sm" name="subType" value="${calc?.subType }"/>
            </div>
        </div>
        <div class="row">
            
            <div class="col-md-4">
              <label for="inputs">Inputs</label>
              <g:aceEditor 
                    name="inpEditor" 
                    mode="json"
                    contentField="inputs"
                    contentValue="${inputs }" 
                    minLines="20" 
                    maxLines="42" />
              
            </div>
            <div class="col-md-8">
                <div class="row">
                    <div class="col-md-12">
                        <label for="model">Model</label>
                        
                        <g:aceEditor 
                            name="modelCodeEditor" 
                            mode="groovy"
                            contentField="modelCode"
                            contentValue="${modelCode }" 
                            minLines="20" 
                            maxLines="20" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label for="model">Presentation</label> ${cv?.name } (${cv?.markupType })
                        
                        <g:aceEditor 
                            name="modelPresentationEditor" 
                            mode="html"
                            contentField="modelPresentation"
                            contentValue="${cv?.content ?: modelPresentation }" 
                            minLines="20" 
                            maxLines="20" />
                    </div>
                </div>
            </div>
        </div>
        
       <div class="row">
           <div class="col-md-12">
               <a href="#" class="btn btn-default" onclick="submitFormForCalculation();return false;">Calculate</a>
               <g:if test="${calc }">
                    <g:actionSubmit class="btn btn-default" controller="calculator" action="save" value="Save Model" onclick="populateHiddenFieldValues();"/>
               </g:if>
               <g:actionSubmit class="btn btn-default" controller="calculator" action="create" value="Create As New Model" onclick="populateHiddenFieldValues();"/>
               
                <g:link class="btn btn-default" controller="calculatorView" action="defaultInputs" id="${calc.id }">Meta Data Inputs View</g:link>
           </div>
       </div>
            
       </form>
       
       <h3>Calculator Result</h3>
       
       <div id="calcOutput"></div>
       
       
       <r:script  language="text/javascript">
        <!-- 
            function populateHiddenFieldValues(){
                populateHiddenFieldValues_inpEditor();
                populateHiddenFieldValues_modelCodeEditor();
                populateHiddenFieldValues_modelPresentationEditor();
            }
                
             -->
        </r:script>
        <r:script disposition="head" language="text/javascript">

function submitFormForCalculation(){

    var url = "${g.createLink(controller:'calculate', action:'playground')}";

    populateHiddenFieldValues();
    
    $.ajax({
           type: "POST",
           url: url,
           data: $("#playgroundForm").serialize(), // serializes the form's elements.
           success: function(data)
           {
                // use this to get from script element
                //var source = $("#modelPresentation").html(); 
                // Use this to get from input element
                var source = $("#modelPresentation").val(); 
                var template = Handlebars.compile(source);
                //alert(data);
                
                $('#calcOutput').html(template(data));
                
                var target = $('#calcOutput');
                $('html, body').stop().animate({
                    'scrollTop': target.offset().top
                }, 
                900, 
                'swing', 
                function () {
                    window.location.hash = '#calcOutput';
                });
           }
         });

    return false; // avoid to execute the actual submit of the form.
}
       </r:script>
    </body>
</html>
